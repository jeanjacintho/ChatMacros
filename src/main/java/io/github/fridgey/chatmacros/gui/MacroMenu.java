package io.github.fridgey.chatmacros.gui;

import java.util.ArrayList;
import java.util.List;

import io.github.fridgey.chatmacros.LiteModChatMacros;
import io.github.fridgey.chatmacros.macro.Macro;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class MacroMenu extends GuiScreen
{
    private LiteModChatMacros mod = LiteModChatMacros.getInstance();
    
    private String name = "";
    private List<Integer> macros;
    
    @Override
    public void initGui()
    {
        super.initGui();
        this.macros = new ArrayList<>(mod.getMacroConfig().getList().getMacroIndicies());
        int buttonsPerRow = this.width / 80;
        int row = 0;
        int column = 0;
        for (int index : macros)
        {
            Macro macro = mod.getMacroConfig().getList().getMacro(index);
            String name = macro.getName();
            this.buttonList.add(new GuiButton(index, 15 + (70 * column++), 30 + (30 * row), 50, 20, macro.getColor() + name));
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
        drawCenteredString(fontRendererObj, "Choose a macro for: " + name, this.width / 2, 10, 0xFFFFFFFF);
        
        for (int i = 0; i < this.buttonList.size(); i++)
        {
            ((GuiButton) this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
        }
    }
    
    @Override
    public void actionPerformed(GuiButton button)
    {
        int index = button.id;
        String cmd = mod.getMacroConfig().getList().getMacro(index).getMessage();
        sendCommand(cmd);
        mc.setIngameFocus();
    }
    
    private void sendCommand(String command)
    {
        sendChatMessage(command.replaceAll("%p|%P", name));
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
