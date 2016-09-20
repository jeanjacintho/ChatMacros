package io.github.fridgey.chatmacros.view;

import net.minecraft.client.gui.GuiScreen;

public class ChatWindow
{
    public void draw(MainOverview screen)
    {
        screen.drawCenteredString(screen.getFontRenderer(), "Chat Macros", screen.width / 2, 10, 0xFFFFFFFF);
        int heightAddition = screen.getShownChatLines() * 10;
        GuiScreen.drawRect(15,  screen.height - 24 - heightAddition,  screen.width - 15,  screen.height - 24, 0xA1000000);
    }
}
