package io.github.fridgey.chatmacros.macro;

import net.minecraft.client.Minecraft;

public interface IMacroController
{
    Minecraft MC = Minecraft.getMinecraft();
    
    void parseAndRunCommand(String name, String player);
    
    boolean addMacro(Macro macro);
    
    boolean deleteMacro(String name);
}
