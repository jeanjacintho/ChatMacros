package io.github.fridgey.chatmacros.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import io.github.fridgey.chatmacros.LiteModChatMacros;
import io.github.fridgey.chatmacros.util.ChatUtil;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MainOverview extends GuiScreen
{
    private LiteModChatMacros mod = LiteModChatMacros.getInstance();

    private ChatWindow chat;
    private List<GuiLine> guiLines;
    private Map<Integer, GuiLine> currentChatLinePositions;
    private int index = 0;

    public MainOverview()
    {
        super();
        this.chat = new ChatWindow();
        this.guiLines = new ArrayList<>();
        this.currentChatLinePositions = new HashMap<>();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        index = 0;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        drawDefaultBackground();
        chat.draw(this);
        int c = 0;
        for (int i = index; i < Math.min(mod.getConfig().getChatLines(), guiLines.size()) + index; i++)
        {
            GuiLine line = guiLines.get(i);
            line.draw(this, (this.height - 33) - (10 * c));
            currentChatLinePositions.put((this.height - 33) - (10 * c), line);
            c++;
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        
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
                // TODO : Open macro gui
                System.out.println(name);
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
            if (guiLines.size() < mod.getConfig().getChatLines())
            {
                return;
            }
            if (index + mod.getConfig().getScrollSpeed() >= guiLines.size() - mod.getConfig().getChatLines() + 1)
            {
                return;
            }
            index += mod.getConfig().getScrollSpeed();
        }
        else if (scroll < 0)
        {
            if (index - mod.getConfig().getScrollSpeed() <= 0)
            {
                return;
            }
            index -= mod.getConfig().getScrollSpeed();
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    public void addChatLine(String string)
    {
        guiLines.add(0, new GuiLine(ChatColor.translateAlternateColorCodes('&', string)));
    }
    
    public int getShownChatLines()
    {
        return Math.min(mod.getConfig().getChatLines(), guiLines.size());
    }
    
    public FontRenderer getFontRenderer()
    {
        return this.fontRendererObj;
    }
}
