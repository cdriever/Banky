package com.ulvbror.banky;

import com.ulvbror.banky.commands.BankCommand;
import com.ulvbror.banky.events.BankEvents;
import com.ulvbror.banky.settings.DataManager;
import com.ulvbror.banky.settings.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Banky extends JavaPlugin {

    public static final String PLUGIN_ID = "[Banky]";
    private final DataManager dataManager = DataManager.getInstance();
    private final SettingsManager settings = SettingsManager.getInstance();

    @Override
    public void onEnable() {

        //Events
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + (Banky.PLUGIN_ID + " Setting events:") );
        getServer().getPluginManager().registerEvents(new BankEvents(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + (Banky.PLUGIN_ID + " >> bank events set") );

        //Settings
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + (Banky.PLUGIN_ID + "  Fetching plugin settings:"));
        dataManager.setup(this);
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + (Banky.PLUGIN_ID + " >> DataManager initialized"));
        settings.setup(this);
        settings.loadDropData();
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + (Banky.PLUGIN_ID + " >> SettingsManager initialized"));
        //Commands
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + (Banky.PLUGIN_ID + " Setting commands:") );
        getCommand("bank").setExecutor(new BankCommand());
        getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + (Banky.PLUGIN_ID + " >> /bank command set") );

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + (Banky.PLUGIN_ID +  " Plugin enabled!") );
    }

    @Override
    public void onDisable() {

        dataManager.saveData();
        getServer().getConsoleSender().sendMessage(ChatColor.RED + (Banky.PLUGIN_ID +  " Plugin disabled!") );

    }

    public Banky getInstance() {
        return this;
    }
}
