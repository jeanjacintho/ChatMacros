package io.github.fridgey.chatmacros.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Mouse;

import io.github.fridgey.chatmacros.LiteModChatMacros;
import io.github.fridgey.chatmacros.util.ChatUtil;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MainGui extends GuiScreen
{
    private LiteModChatMacros mod = LiteModChatMacros.getInstance();

    private List<GuiLine> guiLines;
    private Map<Integer, GuiLine> currentChatLinePositions;
    private int index = 0;

    public MainGui()
    {
        this.guiLines = new ArrayList<>();
        this.currentChatLinePositions = new HashMap<>();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        GuiButton macroConfigButton = new GuiButton(0, this.width / 2 - 75, 40, 70, 20, ChatColor.GREEN + "Macro Config");
        GuiButton guiConfigButton = new GuiButton(1, this.width / 2 + 5, 40, 70, 20, ChatColor.GREEN + "Settings");
        this.buttonList.add(macroConfigButton);
        this.buttonList.add(guiConfigButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        drawDefaultBackground();
        drawCenteredString(this.getFontRenderer(), "Chat Macros", this.width / 2, 10, 0xFFFFFFFF);
        
        int heightAddition = this.getShownChatLines() * 10;
        GuiScreen.drawRect(15,  this.height - 24 - heightAddition,  this.width - 15,  this.height - 24, 0xA1000000);
        
        int c = 0;
        for (int i = index; i < Math.min(mod.getGuiConfig().getChatLines(), guiLines.size()) + index; i++)
        {
            GuiLine line = guiLines.get(i);
            line.draw(this, (this.height - 33) - (10 * c));
            currentChatLinePositions.put((this.height - 33) - (10 * c), line);
            c++;
        }
        
        for (int i = 0; i < this.buttonList.size(); i++)
        {
            ((GuiButton) this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        switch (button.id)
        {
            case 0:
                mc.displayGuiScreen(mod.getMacroConfigMenu());
                break;
            case 1:
                mc.displayGuiScreen(mod.getSettingsMenu());
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        /*
         * 1: Escape
         * 28: Return, 156: Enter on keypad
         * 200: Up
         * 208: Down
         */
        if (keyCode == 1)
        {
            this.mc.setIngameFocus();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseX < 15 || mouseX > this.width - 15
                || mouseY > this.height - 24 
                || mouseY < (this.height - 24) - (10 * getShownChatLines()))
        {
            return;
        }
        
        for (Integer pos : currentChatLinePositions.keySet())
        {
            if (mouseY >= pos && mouseY <= pos + 10)
            {
                String clicked = currentChatLinePositions.get(pos).getLine();
                String name = ChatUtil.parseChat(clicked).get();
                mod.getMacroMenu().setName(name);
                mc.displayGuiScreen(mod.getMacroMenu());
                break;
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        int scroll = Mouse.getDWheel();
        if (scroll > 0)
        {
            if (guiLines.size() < mod.getGuiConfig().getChatLines())
            {
                return;
            }
            if (index + mod.getGuiConfig().getScrollSpeed() >= guiLines.size() - mod.getGuiConfig().getChatLines() + 1)
            {
                index = guiLines.size() - mod.getGuiConfig().getChatLines();
                return;
            }
            index += mod.getGuiConfig().getScrollSpeed();
        }
        else if (scroll < 0)
        {
            if (index - mod.getGuiConfig().getScrollSpeed() < 0)
            {
                index = 0;
                return;
            }
            index -= mod.getGuiConfig().getScrollSpeed();
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    @Override
    public void onGuiClosed()
    {
        index = 0;
    }
    
    public void addChatLine(String string)
    {
        guiLines.add(0, new GuiLine(ChatColor.translateAlternateColorCodes('&', string)));
    }
    
    public void clearChatLines()
    {
        guiLines.clear();
    }
    
    public int getShownChatLines()
    {
        return Math.min(mod.getGuiConfig().getChatLines(), guiLines.size());
    }
    
    public FontRenderer getFontRenderer()
    {
        return this.fontRendererObj;
    }
}
