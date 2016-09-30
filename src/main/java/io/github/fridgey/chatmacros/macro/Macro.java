package io.github.fridgey.chatmacros.macro;

import net.md_5.bungee.api.ChatColor;

public class Macro
{
    public static final Macro BLANK = new Macro("", "", ChatColor.WHITE);
    private String name;
    private String message;
    private ChatColor color;

    public Macro(String name, String message, ChatColor color)
    {
        this.name = name;
        this.message = message;
        this.color = color;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String command)
    {
        this.message = command;
    }

    public ChatColor getColor()
    {
        return color;
    }

    public void setColor(ChatColor color)
    {
        this.color = color;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Macro other = (Macro) obj;
        if (color != other.color)
            return false;
        if (message == null)
        {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
