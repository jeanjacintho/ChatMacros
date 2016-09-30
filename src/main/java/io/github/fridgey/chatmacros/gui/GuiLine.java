package io.github.fridgey.chatmacros.gui;

public class GuiLine
{
    private String line;

    public GuiLine(String line)
    {
        this.line = line;
    }

    public void draw(MainGui screen, int y)
    {
        screen.drawString(screen.getFontRenderer(), line, 16, y, 0xFFFFFFFF);
    }

    public String getLine()
    {
        return line;
    }
}
