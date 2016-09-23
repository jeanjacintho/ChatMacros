package io.github.fridgey.chatmacros.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

import io.github.fridgey.chatmacros.macro.MasterMacroList;
import net.minecraft.client.Minecraft;

public class MacroConfig
{
    private static final File dir = new File(Minecraft.getMinecraft().mcDataDir,
            "liteconfig" + File.separator + "config.1.10.2" + File.separator + "ChatMacros");
    private static final File path = new File(dir, "macros.json");
    
    private MasterMacroList list = new MasterMacroList();
    
    public void init()
    {
        if (!path.exists())
        {
            dir.mkdirs();
            try
            {
                save();
                LiteLoaderLogger.info("Successfully created macro config file.");
            } catch (Exception e)
            {
                LiteLoaderLogger.warning("Could not save macro config to file. Error: " + e.getMessage());
            }
        }

        try
        {
            load();
            LiteLoaderLogger.info("Successfully loaded macro config file.");
        } catch (Exception e)
        {
            LiteLoaderLogger.warning("Could not load macro config file. Error: " + e.getMessage());
        }
    }
    
    private void importSettings(MacroConfig copy)
    {
        this.list = copy.list;
    }

    private void save() throws IOException
    {
        try (Writer writer = new FileWriter(path))
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(this, writer);
        }
    }

    private void load() throws FileNotFoundException, IOException, CloneNotSupportedException
    {
        try (Reader reader = new FileReader(path))
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            MacroConfig config = gson.fromJson(reader, MacroConfig.class);
            importSettings(config);
        }
    }

    public MasterMacroList getList()
    {
        return list;
    }

    public void setList(MasterMacroList list)
    {
        this.list = list;
    }
}
