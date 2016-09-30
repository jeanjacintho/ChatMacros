package io.github.fridgey.chatmacros.macro;

import java.util.ArrayList;
import java.util.List;

public class MasterMacroList
{
    private final List<Macro> macros;
    
    public MasterMacroList()
    {
        this.macros = new ArrayList<>();
    }
    
    public void addNewMacro(Macro macro)
    {
        macros.add(macro);
    }
    
    public void deleteMacro(int index)
    {
        macros.remove(index);
    }
    
    public boolean search(String name)
    {
        for (Macro macro : macros)
        {
            if (macro.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }
    
    public List<String> getMacroNames()
    {
        List<String> list = new ArrayList<>();
        for (Macro macro : getMacros())
        {
            list.add(macro.getName());
        }
        return list;
    }
    
    public List<Integer> getMacroIndicies()
    {
        List<Integer> indicies = new ArrayList<>();
        for (int i = 0; i < macros.size(); i++)
        {
            indicies.add(i);
        }
        return indicies;
    }
    
    public List<Macro> getMacros()
    {
        return macros;
    }
    
    public Macro getMacro(int index)
    {
        return macros.get(index);
    }
    
    public int getSize()
    {
        return macros.size();
    }
}
