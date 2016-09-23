package io.github.fridgey.chatmacros.gui;

import net.minecraft.client.gui.GuiScreen;

public class MacroConfigMenu extends GuiScreen
{
    
    @Override
    public void initGui()
    {
        super.initGui();
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "Macro Config", this.width / 2, 10, 0xFFFFFFFF);
        
        
    }
}
