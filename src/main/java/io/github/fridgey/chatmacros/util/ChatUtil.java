package io.github.fridgey.chatmacros.util;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.google.common.base.Optional;

import net.md_5.bungee.api.ChatColor;

public class ChatUtil
{
    private static final Pattern CHANNEL_PATTERN = Pattern.compile(
            "(\\[(G|W|H|T|L|F|S|P|A|Spy|PM){1}\u25AA?(B1|B2|B3|B4|S|G|N|M|MG|PvP|SP)?\\]){1}");
    private static final Pattern NAME_PATTERN = Pattern.compile("(\\[(\\S)+\\]){1}");

    public static Optional<String> parseChat(String message)
    {
        String noColorMessage = ChatColor.stripColor(message);
        if (noColorMessage.length() == 0 || !noColorMessage.contains(" "))
        {
            return Optional.absent();
        }
        String delimiter = "";
        String tags = noColorMessage.substring(0, noColorMessage.indexOf(" "));
        if (tags.contains("="))
        {
            delimiter = "=";
        } else if (tags.contains("|"))
        {
            delimiter = "\\|";
        } else if (tags.equals("[PM]"))
        {
            delimiter = " ";
            int startIndex = noColorMessage.indexOf(" ") + 1;
            int endIndex = noColorMessage.substring(startIndex).indexOf(" ");

            tags = noColorMessage.substring(0, endIndex + startIndex);
        }
        String[] firstParts = tags.split(delimiter);
        // If the length isn't at least 2 then this is not a chat message.
        if (firstParts.length <= 1)
        {
            return Optional.absent();
        }
        // If the first part isn't a channel and/or world name then this is not a chat message.
        if (!CHANNEL_PATTERN.matcher(firstParts[0]).matches())
        {
            return Optional.absent();
        }
        // If the last part isn't a name then this is not a chat message.
        String checkName = firstParts[firstParts.length - 1];
        if (!checkName.endsWith("]"))
        {
            checkName += "]";
        }
        if (!NAME_PATTERN.matcher(checkName).matches())
        {
            return Optional.absent();
        }
        System.out.println(Arrays.toString(firstParts));
        String name = firstParts[firstParts.length - 1].replaceAll("\\[|\\]", "");
        System.out.println("Name: " + name);
        if (name.startsWith("\u2666") || name.startsWith("\u2022") || name.startsWith("\u25bc"))
        {
            name = name.substring(1);
        }
        if (name.contains(" "))
        {
            name = name.substring(0, name.indexOf(" "));
        }
        if (name.length() < 3)
        {
            return Optional.absent();
        }
        return Optional.of(name);
    }

    public static String stripTags(String message)
    {
        String[] firstParts = message.substring(0, message.indexOf(" ")).split("\\|");
        String chat = message.substring(message.indexOf(" "));

        if (firstParts.length < 3)
        {
            return message;
        }
        return firstParts[0] + "|" + firstParts[firstParts.length - 1] + chat;
    }
}
