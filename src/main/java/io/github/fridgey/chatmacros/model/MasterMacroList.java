package io.github.fridgey.chatmacros.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MasterMacroList
{
    private final Map<String, Macro> macros;
    
    public MasterMacroList()
    {
        this.macros = new HashMap<>();
    }
    
    public void addNewMacro(Macro macro)
    {
        macros.put(macro.getName(), macro);
    }
    
    public void deleteMacro(String name)
    {
        macros.remove(name);
    }
    
    public boolean search(String name)
    {
        return macros.containsKey(name);
    }
    
    public Set<String> getMacroNames()
    {
        return macros.keySet();
    }
    
    public Set<Macro> getMacros()
    {
        return new HashSet<>(macros.values());
    }
    
    public Macro getMacro(String name)
    {
        return macros.get(name);
    }
    
    public int getSize()
    {
        return macros.size();
    }
}
