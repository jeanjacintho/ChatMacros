package io.github.fridgey.chatmacros.gui.component;

import io.github.fridgey.chatmacros.gui.menu.MainGui;

public class GuiSelection
{
    public static void draw(MainGui screen, int minX, int minY, int maxX, int maxY)
    {
        MainGui.drawRect(minX, minY, maxX, maxY, 0x3000FF00);
    }
}
