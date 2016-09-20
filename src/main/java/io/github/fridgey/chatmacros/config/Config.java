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

import net.minecraft.client.Minecraft;

public class Config implements Cloneable
{

    private static final File dir = new File(Minecraft.getMinecraft().mcDataDir,
            "liteconfig" + File.separator + "config.1.10.2" + File.separator + "ChatMacros");
    private static final File path = new File(dir, "config.json");
    
    private boolean copyWithColors = true;
    private boolean displayFactionTags = true;
    private boolean displayRankTags = true;
    private int chatLines = 10;
    private int scrollSpeed = 1;
    
    public Config() {}

    public Config(boolean copyWithColors,
            boolean displayFactionTags, boolean displayRankTags, int chatLines, int scrollSpeed)
    {
        this.copyWithColors = copyWithColors;
        this.displayFactionTags = displayFactionTags;
        this.displayRankTags = displayRankTags;
        this.chatLines = chatLines;
        this.scrollSpeed = scrollSpeed;
    }

    public void init()
    {
        if (!path.exists())
        {
            dir.mkdirs();
            try
            {
                save();
                LiteLoaderLogger.info("Successfully created config file.");
            } catch (Exception e)
            {
                LiteLoaderLogger.warning("Could not save config to file. Error: " + e.getMessage());
            }
        }

        try
        {
            load();
            LiteLoaderLogger.info("Successfully loaded config file.");
        } catch (Exception e)
        {
            LiteLoaderLogger.warning("Could not load config file. Error: " + e.getMessage());
        }
    }

    private void importSettings(Config base, Config copy) throws CloneNotSupportedException
    {
        base = (Config) copy.clone();
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
            Config config = gson.fromJson(reader, Config.class);
            importSettings(this, config);
        }
    }    

    public boolean copyWithColors()
    {
        return copyWithColors;
    }


    public void setCopyWithColors(boolean copyWithColors)
    {
        this.copyWithColors = copyWithColors;
    }


    public boolean displayFactionTags()
    {
        return displayFactionTags;
    }


    public void setDisplayFactionTags(boolean displayFactionTags)
    {
        this.displayFactionTags = displayFactionTags;
    }


    public boolean displayRankTags()
    {
        return displayRankTags;
    }


    public void setDisplayRankTags(boolean displayRankTags)
    {
        this.displayRankTags = displayRankTags;
    }

    public int getChatLines()
    {
        return chatLines;
    }
    
    public void setChatLines(int chatLines)
    {
        this.chatLines = chatLines;
    }

    public int getScrollSpeed()
    {
        return scrollSpeed;
    }


    public void setScrollSpeed(int scrollSpeed)
    {
        this.scrollSpeed = scrollSpeed;
    }
}
