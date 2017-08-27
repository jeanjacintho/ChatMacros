package io.github.fridgey.chatmacros;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.ChatListener;
import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.JoinGameListener;
import com.mumfrey.liteloader.OutboundChatFilter;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.ExposableOptions;

import io.github.fridgey.chatmacros.config.GuiConfig;
import io.github.fridgey.chatmacros.config.MacroConfig;
import io.github.fridgey.chatmacros.gui.menu.MacroConfigMenu;
import io.github.fridgey.chatmacros.gui.menu.MacroMenu;
import io.github.fridgey.chatmacros.gui.menu.MainGui;
import io.github.fridgey.chatmacros.gui.menu.SettingsMenu;
import io.github.fridgey.chatmacros.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;

import static net.md_5.bungee.api.ChatColor.*;

@ExposableOptions(strategy = ConfigStrategy.Versioned, filename = "chatmacros.json")
public class LiteModChatMacros implements Tickable, JoinGameListener, ChatListener,
        OutboundChatFilter, InitCompleteListener
{
    private static LiteModChatMacros instance;

    private MainGui mainOverview;
    private MacroMenu macroMenu;
    private MacroConfigMenu macroConfigMenu;
    private SettingsMenu settingsMenu;
    private KeyBinding activateKeyBinding;
    private GuiConfig guiConfig;
    private MacroConfig macroConfig;

    public LiteModChatMacros()
    {
        instance = this;
    }

    @Override
    public void init(File configPath)
    {
        activateKeyBinding = new KeyBinding(
                translateAlternateColorCodes('&', "&6&l&n&oActivate Chat Macros Mod"),
                Keyboard.CHAR_NONE, translateAlternateColorCodes('&', "&a&l&n&oFridgey"));
        LiteLoader.getInput().registerKeyBinding(activateKeyBinding);

        this.guiConfig = new GuiConfig();
        this.guiConfig.init();

        this.macroConfig = new MacroConfig();
        this.macroConfig.init();
    }

    @Override
    public void onInitCompleted(Minecraft minecraft, LiteLoader loader)
    {
        this.mainOverview = new MainGui();
        this.macroMenu = new MacroMenu();
        this.macroConfigMenu = new MacroConfigMenu(this.macroConfig.getList());
        this.settingsMenu = new SettingsMenu();
    }

    @Override
    public String getName()
    {
        return DARK_GREEN.toString() + BOLD + "ChatMacros";
    }

    @Override
    public String getVersion()
    {
        return AQUA.toString() + BOLD + "2.5";
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {}

    @Override
    public void onChat(ITextComponent chat, String message)
    {
        String newMessage = message.replace(String.valueOf(COLOR_CHAR), "&").replace("&r", "");

        chat.getStyle().setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/cm copy " + newMessage));
        if (ChatUtil.parseChat(message.replace(RESET.toString(), "")).isPresent())
        {
            mainOverview.addChatLine(newMessage);
        }
    }

    @Override
    public boolean onSendChatMessage(String message)
    {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        String[] args = message.split(" ");
        if (args.length == 0 || !args[0].equalsIgnoreCase("/cm"))
        {
            return true;
        }
        if (args[0].startsWith("/"))
        {
            args[0] = args[0].substring(1);
        }

        if (args.length > 1)
        {
            if (args[1].equalsIgnoreCase("copy"))
            {
                int lCtrl = Keyboard.KEY_LCONTROL;
                int rCtrl = Keyboard.KEY_RCONTROL;
                if (!Keyboard.isKeyDown(lCtrl) && !Keyboard.isKeyDown(rCtrl))
                {
                    return false;
                }
                String toCopy = args[2];
                for (int i = 3; i < args.length; i++)
                {
                    toCopy += " " + args[i];
                }
                toCopy = toCopy.trim();
                if (!guiConfig.copyWithColors())
                {
                    toCopy = stripColor(translateAlternateColorCodes('&', toCopy));
                }
                if (!guiConfig.displayFactionAndRankTags())
                {
                    toCopy = ChatUtil.stripTags(toCopy);
                }
                GuiScreen.setClipboardString(toCopy.trim());
            } else if (args[1].equalsIgnoreCase("help"))
            {
                ITextComponent comp1 = new TextComponentString(translateAlternateColorCodes('&',
                        "&8[&a!&8] &aChat Macros Help &8[&a!&8]"));
                ITextComponent comp2 = new TextComponentString(translateAlternateColorCodes('&',
                        "&9- &7To copy chat messages from normal chat window: Hold ctrl + left click."));
                comp1.getStyle().setColor(TextFormatting.GRAY);
                comp2.getStyle().setColor(TextFormatting.GRAY);
                player.addChatMessage(comp1);
                player.addChatMessage(comp2);
            }
        }
        return false;
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
        mainOverview.clearChatLines();
        Minecraft.getMinecraft().thePlayer
                .addChatMessage(new TextComponentString(translateAlternateColorCodes('&',
                        "&8[&a!&8] &aChat Macros &8[&a!&8] &7For more info type: &f/cm help")));
    }

    public static LiteModChatMacros getInstance()
    {
        return instance;
    }

    public MainGui getMainOverview()
    {
        return mainOverview;
    }

    public MacroMenu getMacroMenu()
    {
        return macroMenu;
    }

    public MacroConfigMenu getMacroConfigMenu()
    {
        return macroConfigMenu;
    }

    public SettingsMenu getSettingsMenu()
    {
        return settingsMenu;
    }

    public GuiConfig getGuiConfig()
    {
        return guiConfig;
    }

    public MacroConfig getMacroConfig()
    {
        return macroConfig;
    }
}
