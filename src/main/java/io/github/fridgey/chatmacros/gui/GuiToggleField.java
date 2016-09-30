package io.github.fridgey.chatmacros.gui;

import io.github.fridgey.chatmacros.util.StringUtil;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class GuiToggleField extends Gui
{
    private final int id;
    private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;
    public int xPosition;
    public int yPosition;
    private final int width;
    private final int height;

    private boolean visible = true;

    private ChatColor[] items;
    private int index = 0;

    public GuiToggleField(int id, int x, int y, int width, int height, ChatColor[] items)
    {
        this.id = id;
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.items = items;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        boolean flag = mouseX >= this.xPosition && mouseX < this.xPosition + this.width
                && mouseY >= this.yPosition && mouseY < this.yPosition + this.height;
        if (!flag)
        {
            return;
        }
        int change = mouseButton == 0 ? 1 : -1;
        index += change;
        if (index >= items.length)
        {
            index = 0;
        } else if (index < 0)
        {
            index = items.length - 1;
        }
    }

    public void drawToggleBox()
    {
        if (!visible)
        {
            return;
        }
        ChatColor color = items[index];
        drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
        drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
        drawCenteredString(fontRenderer, StringUtil.capitalizeAndSpace(color.getName()),
                xPosition + (width / 2), yPosition + (height / 2) - 4, color.getHex());
    }

    public int getId()
    {
        return id;
    }

    public ChatColor getColor()
    {
        return items[index];
    }

    public void setColor(ChatColor color)
    {
        int i = 0;
        for (i = 0; i < items.length; i++)
        {
            ChatColor chatColor = items[i];
            if (chatColor == color)
            {
                break;
            }
        }
        index = i;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int i)
    {
        this.index = i;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
}
