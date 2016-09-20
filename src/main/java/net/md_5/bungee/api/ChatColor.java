/*
 * The code in this file is from the BungeeCord and Spigot API and I do
 * not claim any ownership of this code. I am using it because the
 * code is easy and simple to use and I am very familiar with it. Some
 * parts of the code have been edited to meet my needs.
 */
package net.md_5.bungee.api;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Simplistic enumeration of all supported color values for chat.
 */
public enum ChatColor
{

    /**
     * Represents black.
     */
    BLACK( '0', "black", 0x000000 ),
    /**
     * Represents dark blue.
     */
    DARK_BLUE( '1', "dark_blue", 0x0000AA ),
    /**
     * Represents dark green.
     */
    DARK_GREEN( '2', "dark_green", 0x00AA00 ),
    /**
     * Represents dark blue (aqua).
     */
    DARK_AQUA( '3', "dark_aqua", 0x00AAAA ),
    /**
     * Represents dark red.
     */
    DARK_RED( '4', "dark_red", 0xAA0000 ),
    /**
     * Represents dark purple.
     */
    DARK_PURPLE( '5', "dark_purple", 0xAA00AA ),
    /**
     * Represents gold.
     */
    GOLD( '6', "gold", 0xFFAA00 ),
    /**
     * Represents gray.
     */
    GRAY( '7', "gray", 0xAAAAAA  ),
    /**
     * Represents dark gray.
     */
    DARK_GRAY( '8', "dark_gray", 0x555555 ),
    /**
     * Represents blue.
     */
    BLUE( '9', "blue", 0x5555FF ),
    /**
     * Represents green.
     */
    GREEN( 'a', "green", 0x55FF55 ),
    /**
     * Represents aqua.
     */
    AQUA( 'b', "aqua", 0x55FFFF ),
    /**
     * Represents red.
     */
    RED( 'c', "red", 0xFF5555 ),
    /**
     * Represents light purple.
     */
    LIGHT_PURPLE( 'd', "light_purple", 0xFF55FF ),
    /**
     * Represents yellow.
     */
    YELLOW( 'e', "yellow", 0xFFFF55 ),
    /**
     * Represents white.
     */
    WHITE( 'f', "white", 0xFFFFFF ),
    /**
     * Represents magical characters that change around randomly.
     */
    MAGIC( 'k', "obfuscated", -1 ),
    /**
     * Makes the text bold.
     */
    BOLD( 'l', "bold", -1 ),
    /**
     * Makes a line appear through the text.
     */
    STRIKETHROUGH( 'm', "strikethrough", -1 ),
    /**
     * Makes the text appear underlined.
     */
    UNDERLINE( 'n', "underline", -1 ),
    /**
     * Makes the text italic.
     */
    ITALIC( 'o', "italic", -1 ),
    /**
     * Resets all previous chat colors or formats.
     */
    RESET( 'r', "reset", -1 );
    /**
     * The special character which prefixes all chat colour codes. Use this if
     * you need to dynamically convert colour codes from your custom format.
     */
    public static final char COLOR_CHAR = '\u00A7';
    public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";
    /**
     * Pattern to remove all colour codes.
     */
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile( "(?i)" + String.valueOf( COLOR_CHAR ) + "[0-9A-FK-OR]" );
    /**
     * Colour instances keyed by their active character.
     */
    private static final Map<Character, ChatColor> BY_CHAR = new HashMap<Character, ChatColor>();
    /**
     * The code appended to {@link #COLOR_CHAR} to make usable colour.
     */
    private final char code;
    /**
     * This colour's colour char prefixed by the {@link #COLOR_CHAR}.
     */
    private final String toString;
    private final String name;
    private final int hex;

    static
    {
        for ( ChatColor colour : values() )
        {
            BY_CHAR.put( colour.code, colour );
        }
    }

    private ChatColor(char code, String name, int hex)
    {
        this.code = code;
        this.name = name;
        this.hex = hex;
        this.toString = new String( new char[]
        {
            COLOR_CHAR, code
        } );
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getHex()
    {
        return hex;
    }

    @Override
    public String toString()
    {
        return toString;
    }

    /**
     * Strips the given message of all color codes
     *
     * @param input String to strip of color
     * @return A copy of the input string, without any coloring
     */
    public static String stripColor(final String input)
    {
        if ( input == null )
        {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher( input ).replaceAll( "" );
    }

    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate)
    {
        char[] b = textToTranslate.toCharArray();
        for ( int i = 0; i < b.length - 1; i++ )
        {
            if ( b[i] == altColorChar && ALL_CODES.indexOf( b[i + 1] ) > -1 )
            {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase( b[i + 1] );
            }
        }
        return new String( b );
    }

    /**
     * Get the colour represented by the specified code.
     *
     * @param code the code to search for
     * @return the mapped colour, or null if non exists
     */
    public static ChatColor getByChar(char code)
    {
        return BY_CHAR.get( code );
    }
}
