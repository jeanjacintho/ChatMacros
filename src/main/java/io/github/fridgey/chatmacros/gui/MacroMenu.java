package io.github.fridgey.chatmacros.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.fridgey.chatmacros.LiteModChatMacros;
import io.github.fridgey.chatmacros.macro.Macro;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MacroMenu extends GuiScreen
{
    private LiteModChatMacros mod = LiteModChatMacros.getInstance();
    
    private String title = "Pre-Init";
    private List<String> macros;
    private Map<Position, String> currentMacroPositions;
    
    public MacroMenu()
    {
        this.macros = new ArrayList<>(mod.getMacroConfig().getList().getMacroNames());
        this.currentMacroPositions = new HashMap<>();
    }
    
    @Override
    public void initGui()
    {
        super.initGui();
        int c = 0;
        int buttonsPerRow = this.width / 70;
        int row = 0;
        int column = 0;
        System.out.println(buttonsPerRow);
        for (String name : macros)
        {
            Macro macro = mod.getMacroConfig().getList().getMacro(name);
            this.buttonList.add(new GuiButton(c++, 15 + (70 * column++), (this.height / 2) + (30 * row), 50, 20, macro.getColor() + name));
            if (column > buttonsPerRow)
            {
                column = 0;
                row++;
            }
        }
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, title, this.width / 2, 10, 0xFFFFFFFF);
        
        for (int i = 0; i < this.buttonList.size(); i++)
        {
            ((GuiButton) this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
        }
        
        
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    private class Position
    {
        public int x;
        public int y;
        
        public Position(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
