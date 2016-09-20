package io.github.fridgey.chatmacros.controller;

import io.github.fridgey.chatmacros.model.Macro;
import io.github.fridgey.chatmacros.model.MasterMacroList;

public class MacroController implements IMacroController
{
    private final MasterMacroList macros;
    
    public MacroController(MasterMacroList macros)
    {
        this.macros = macros;
    }

    public void parseAndRunCommand(String name, String player)
    {
        Macro macro = macros.getMacro(name);
        String command = macro.getCommand().replaceAll("(%p|%P)", player);
        if (!command.startsWith("/"))
        {
            command = "/" + command;
        }
        MC.thePlayer.sendChatMessage(command);
    }
    
    public boolean addMacro(Macro macro)
    {
        if (macros.search(macro.getName()))
        {
            return false;
        }
        macros.addNewMacro(macro);
        return true;
    }
    
    public boolean deleteMacro(String name)
    {
        if (!macros.search(name))
        {
            return false;
        }
        macros.deleteMacro(name);
        return true;
    }

    public MasterMacroList getMacros()
    {
        return macros;
    }
}
