package io.github.fridgey.chatmacros;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.ChatListener;
import com.mumfrey.liteloader.JoinGameListener;
import com.mumfrey.liteloader.OutboundChatFilter;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

import io.github.fridgey.chatmacros.config.Config;
import io.github.fridgey.chatmacros.config.MacroConfig;
import io.github.fridgey.chatmacros.util.ChatUtil;
import io.github.fridgey.chatmacros.view.MainOverview;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;

@ExposableOptions(strategy = ConfigStrategy.Versioned, filename = "chatmacros.json")
public class LiteModChatMacros
        implements Tickable, JoinGameListener, ChatListener, OutboundChatFilter
{
    private static LiteModChatMacros instance;

    private MainOverview mainOverview;
    private KeyBinding activateKeyBinding;
    private Config config;
    private MacroConfig macroConfig;

    public LiteModChatMacros()
    {
        instance = this;
    }

    @Override
    public void init(File configPath)
    {
        activateKeyBinding = new KeyBinding(
                ChatColor.translateAlternateColorCodes('&', "&6&l&n&oActivate Chat Macros Mod"),
                Keyboard.CHAR_NONE, ChatColor.translateAlternateColorCodes('&', "&a&l&n&oFridgey"));
        LiteLoader.getInput().registerKeyBinding(activateKeyBinding);

        this.mainOverview = new MainOverview();
        this.config = new Config();
        this.macroConfig = new MacroConfig();

        this.config.init();
        this.macroConfig.init();
    }

    @Override
    public String getName()
    {
        return ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "ChatMacros";
    }

    @Override
    public String getVersion()
    {
        return ChatColor.AQUA + "2.0";
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath){}

    @Override
    public void onChat(ITextComponent chat, String message)
    {
        String newMessage = message.replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "&").replace("&r", "");

        chat.getStyle().setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/cm copy " + newMessage));
        if (ChatUtil.parseChat(message.replace(ChatColor.RESET.toString(), "")).isPresent())
        {
            mainOverview.addChatLine(newMessage);
        }
    }

    @Override
    public boolean onSendChatMessage(String message)
    {
        String[] args = message.split(" ");
        if (args.length == 0 || !args[0].equalsIgnoreCase("/cm"))
        {
            return true;
        }
        if (args[0].startsWith("/"))
        {
            args[0] = args[0].substring(1);
        }

        if (args.length == 1)
        {
            // help info
            return false;
        }

        if (args.length > 1)
        {
            if (args[1].equalsIgnoreCase("copy"))
            {
                String toCopy = args[2];
                for (int i = 3; i < args.length; i++)
                {
                    toCopy += " " + args[i];
                }
                GuiScreen.setClipboardString(toCopy.trim());

                return false;
            }
        }
        return true;
    }

    @Override
    public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
    {
        if (inGame && minecraft.currentScreen == null && this.activateKeyBinding.isPressed())
        {
            minecraft.displayGuiScreen(this.mainOverview);
        }
    }

    @Override
    public void onJoinGame(INetHandler netHandler, SPacketJoinGame joinGamePacket,
            ServerData serverData, RealmsServer realmsServer)
    {

    }

    public static LiteModChatMacros getInstance()
    {
        return instance;
    }

    public MainOverview getMainOverview()
    {
        return mainOverview;
    }

    public Config getConfig()
    {
        return config;
    }

    public MacroConfig getMacroConfig()
    {
        return macroConfig;
    }
}
