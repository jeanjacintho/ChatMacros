package io.github.fridgey.chatmacros.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import io.github.fridgey.chatmacros.LiteModChatMacros;
import io.github.fridgey.chatmacros.macro.Macro;
import io.github.fridgey.chatmacros.macro.MasterMacroList;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class MacroConfigMenu extends GuiScreen
{
    private LiteModChatMacros mod = LiteModChatMacros.getInstance();

    private MasterMacroList list;
    private int index = 0;
    private GuiTextField nameField;
    private GuiTextField macroField;
    private GuiToggleField colorField;

    public MacroConfigMenu(MasterMacroList list)
    {
        this.list = list;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        index = 0;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 55, 50, 20, 20, "<"));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 35, 50, 20, 20, ">"));
        this.buttonList
                .add(new GuiButton(2, this.width / 2 - 55, 75, 50, 20, ChatColor.GREEN + "Add"));
        this.buttonList
                .add(new GuiButton(3, this.width / 2 + 5, 75, 50, 20, ChatColor.RED + "Delete"));
        this.buttonList.add(new GuiButton(4, this.width - 105, this.height - 25, 100, 20,
                ChatColor.WHITE + "Save"));
        this.nameField = new GuiTextField(0, fontRendererObj, this.width / 2 - 50, 120, 100, 15);
        this.macroField = new GuiTextField(0, fontRendererObj, this.width / 2 - 50, 170, 100, 15);
        this.colorField =
                new GuiToggleField(0, this.width / 2 - 50, 220, 100, 15, ChatColor.getColors());
        initMacros();
        reloadMacro();
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "Macro Config", this.width / 2, 10, 0xFFFFFFFF);
        drawCenteredString(fontRendererObj, "Macro " + (index + 1), this.width / 2, 55, 0xFFFFFFFF);
        drawCenteredString(fontRendererObj, "Name", this.width / 2, 105, 0xFFFFFFFF);
        drawCenteredString(fontRendererObj, "Message (Use %p for player name)", this.width / 2, 155, 0xFFFFFFFF);
        drawCenteredString(fontRendererObj, "Color", this.width / 2, 205, 0xFFFFFFFF);

        for (int i = 0; i < this.buttonList.size(); i++)
        {
            ((GuiButton) this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
        }

        nameField.drawTextBox();
        macroField.drawTextBox();
        colorField.drawToggleBox();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        nameField.textboxKeyTyped(typedChar, keyCode);
        macroField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        nameField.mouseClicked(mouseX, mouseY, mouseButton);
        macroField.mouseClicked(mouseX, mouseY, mouseButton);
        colorField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException
    {
        int id = button.id;
        if (id == 0 && index > 0)
        {
            index--;
            reloadMacro();
        } else if (id == 1 && index < list.getSize() - 1)
        {
            index++;
            reloadMacro();
        } else if (id == 2)
        {
            Macro macro = Macro.BLANK.clone();
            list.addNewMacro(macro);
            index = list.getSize() - 1;
            reloadMacro();
            mod.getMacroConfig().setList(list);
            mod.getMacroConfig().save();
        } else if (id == 3)
        {
            if (index == 0)
            {
                list.getMacro(0).setColor(ChatColor.WHITE);
                list.getMacro(0).setName("");
                list.getMacro(0).setMessage("");
                return;
            }
            list.deleteMacro(index);
            if (index >= list.getSize())
            {
                index = list.getSize() - 1;
            }
            reloadMacro();
            mod.getMacroConfig().setList(list);
            mod.getMacroConfig().save();
        } else if (id == 4)
        {
            Macro macro = list.getMacro(index);
            macro.setName(nameField.getText());
            macro.setMessage(macroField.getText());
            macro.setColor(colorField.getColor());
            mod.getMacroConfig().setList(list);
            mod.getMacroConfig().save();
        }
    }

    public void reloadMacro()
    {
        if (index == -1)
        {
            this.nameField.setText("");
            this.macroField.setText("");
            this.colorField.setIndex(15);
        }

        if (list.getSize() > 0 && index >= 0)
        {
            this.nameField.setText(list.getMacro(index).getName());
        }

        if (list.getSize() > 0 && index >= 0)
        {
            this.macroField.setText(list.getMacro(index).getMessage());
        }

        if (list.getSize() > 0 && index >= 0)
        {
            this.colorField.setColor(list.getMacro(index).getColor());
        }
    }

    public void initMacros()
    {
        this.nameField.setMaxStringLength(20);
        this.nameField.setEnableBackgroundDrawing(true);
        this.nameField.setFocused(true);
        this.nameField.setCanLoseFocus(true);

        this.macroField.setMaxStringLength(100);
        this.macroField.setEnableBackgroundDrawing(true);
        this.macroField.setFocused(false);
        this.macroField.setCanLoseFocus(true);
    }
}
