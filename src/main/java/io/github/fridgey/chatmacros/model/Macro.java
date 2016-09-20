package io.github.fridgey.chatmacros.model;

import net.md_5.bungee.api.ChatColor;

public class Macro
{
    private String name;
    private String command;
    private ChatColor color;

    public Macro(String name, String command, ChatColor color)
    {
        this.name = name;
        this.command = command;
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

    public String getCommand()
    {
        return command;
    }

    public void setCommand(String command)
    {
        this.command = command;
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
        result = prime * result + ((command == null) ? 0 : command.hashCode());
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
        if (command == null)
        {
            if (other.command != null)
                return false;
        } else if (!command.equals(other.command))
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
