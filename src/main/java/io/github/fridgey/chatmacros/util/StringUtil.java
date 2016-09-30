package io.github.fridgey.chatmacros.util;

public class StringUtil
{
    public static String capitalizeAndSpace(String input)
    {
        if (input.length() == 0)
        {
            return input;
        }

        String[] words = input.split("_");
        int c = 0;
        for (String word : words)
        {
            char firstLetter = word.charAt(0);
            words[c] = Character.toUpperCase(firstLetter) + word.substring(1);
            c++;
        }
        return String.join(" ", words);
    }

    public static String lowerAndUnderscore(String input)
    {
        return input.toLowerCase().replace(" ", "_");
    }
}
