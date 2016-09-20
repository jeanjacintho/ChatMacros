package io.github.fridgey.chatmacros.util;

import java.util.regex.Pattern;

import com.google.common.base.Optional;

import net.md_5.bungee.api.ChatColor;

public class ChatUtil
{
    private static final Pattern CHANNEL_PATTERN = Pattern.compile("(\\[(G|W|H|T|L|F|S|P|A){1}\u25AA?(B1|B2|B3|B4|S|G|N|M|MG|PvP)?\\]){1}");
    private static final Pattern NAME_PATTERN = Pattern.compile("(\\[(\\S)+\\]){1}");
    
    public static Optional<String> parseChat(String message)
    {
        String noColorMessage = ChatColor.stripColor(message);
        String[] firstParts = noColorMessage.substring(0, noColorMessage.indexOf(" ")).split("=");
        // If the length isn't at least 2 then this is not a chat message.
        if (firstParts.length <= 1)
        {
            return Optional.absent();
        }
        // If the first part isnt a channel and/or world name then this is not a chat message.
        if (!CHANNEL_PATTERN.matcher(firstParts[0]).matches())
        {
            return Optional.absent();
        }
        // If the last part isnt a name then this is not a chat message.
        if (!NAME_PATTERN.matcher(firstParts[firstParts.length - 1]).matches())
        {
            return Optional.absent();
        }
        String name = firstParts[firstParts.length - 1].replaceAll("\\[|\\]", "");
        System.out.println(name);
        return Optional.of(name);
    }
}
