package io.github.fridgey.chatmacros.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiMacroButton extends GuiButton
{

    public GuiMacroButton(int buttonId, int x, int y, String buttonText)
    {
        super(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiMacroButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRendererObj;
            int j = 14737632;

            if (!this.enabled)
            {
                j = 10526880;
            } else if (this.hovered)
            {
                j = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString,
                    this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
        }
    }
}
