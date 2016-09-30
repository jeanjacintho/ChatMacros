package io.github.fridgey.chatmacros.gui;

import java.io.IOException;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;

import io.github.fridgey.chatmacros.LiteModChatMacros;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiSlider.FormatHelper;

public class SettingsMenu extends GuiScreen
{
    private LiteModChatMacros mod = LiteModChatMacros.getInstance();

    private GuiCheckbox colorsBox;
    private GuiCheckbox factionBox;
    private GuiSlider chatLines;
    private GuiSlider scrollSpeed;

    @Override
    public void initGui()
    {
        super.initGui();
        this.colorsBox =
                new GuiCheckbox(0, this.width / 2 - 50, this.height / 4, "Copy With Colors");
        this.factionBox = new GuiCheckbox(1, this.width / 2 - 90, this.height / 4 + 16,
                "Copy With Faction And Rank Tags");
        this.chatLines = new GuiSlider(getResponder(), 2, this.width / 2 - 75, this.height / 4 + 36,
                "Chat Lines", 5f, 100f, (float) mod.getGuiConfig().getChatLines(), getFormatter());
        this.scrollSpeed = new GuiSlider(getResponder(), 3, this.width / 2 - 75,
                this.height / 4 + 64, "Scroll Speed", 1f, 10f,
                (float) mod.getGuiConfig().getScrollSpeed(), getFormatter());

        this.colorsBox.checked = mod.getGuiConfig().copyWithColors();
        this.factionBox.checked = mod.getGuiConfig().displayFactionAndRankTags();

        this.buttonList.add(this.colorsBox);
        this.buttonList.add(this.factionBox);
        this.buttonList.add(this.chatLines);
        this.buttonList.add(this.scrollSpeed);
        System.out.println(this.scrollSpeed.getButtonWidth());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, "Settings", this.width / 2, 10, 0xFFFFFFFF);

        for (int i = 0; i < this.buttonList.size(); i++)
        {
            this.buttonList.get(i).drawButton(this.mc, mouseX, mouseY);
        }
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException
    {
        if (button instanceof GuiCheckbox)
        {
            GuiCheckbox check = (GuiCheckbox) button;
            check.checked = !check.checked;
            if (button.id == 0)
            {
                mod.getGuiConfig().setCopyWithColors(check.checked);
            } else if (button.id == 1)
            {
                mod.getGuiConfig().setDisplayFactionAndRankTags(check.checked);
            }
            mod.getGuiConfig().save();
        }
    }

    private GuiResponder getResponder()
    {
        return new GuiResponder()
        {

            @Override
            public void setEntryValue(int id, String value)
            {
                // derp
            }

            @Override
            public void setEntryValue(int id, float value)
            {
                if (id == 2)
                {
                    mod.getGuiConfig().setChatLines((int) value);
                }
                if (id == 3)
                {
                    mod.getGuiConfig().setScrollSpeed((int) value);
                }
                try
                {
                    mod.getGuiConfig().save();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void setEntryValue(int id, boolean value)
            {
                // derp
            }
        };
    }

    private FormatHelper getFormatter()
    {
        return new FormatHelper()
        {
            @Override
            public String getText(int id, String name, float value)
            {
                return String.valueOf(name + ": " + (int) value);
            }
        };
    }
}
