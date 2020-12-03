package com.ulvbror.banky.settings;

import com.ulvbror.banky.data.BankData;
import com.ulvbror.banky.data.DropData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsManager {

    private SettingsManager() {}

    static SettingsManager instance = new SettingsManager();

    public static SettingsManager getInstance() {
        return instance;
    }

    FileConfiguration dataconfig;
    File file;

    public List<DropData> dropData = new ArrayList<>();

    public void setup(Plugin p) {
        if(!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        file = new File(p.getDataFolder(), "config.yml");
        boolean newFile = false;
        if(!file.exists()) {
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "ERROR: Unable to create config.yml");
            }
        }
        dataconfig = YamlConfiguration.loadConfiguration(file);
        if(newFile) {
            initDropData();
        }
    }

    public FileConfiguration getData() {
        return dataconfig;
    }

    public void saveData() {
        try {
            dataconfig.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "ERROR: Unable to save config.yml");
        }
    }

    public void reloadData() {
        dataconfig = YamlConfiguration.loadConfiguration(file);
    }

    private void initConfigFile() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "~ No config.yml detected, initializing one ~");
        initDropData();
    }

    public void loadDropData() {
        DropData d = null;
        for(int i = 0; i < DropData.TYPE_LIST.size(); i++) {
            String name = DropData.TYPE_LIST.get(i);
            EntityType type = DropData.findType(DropData.TYPE_LIST.get(i));
            if((int)this.getData().get("banky.drops."+name+".canDrop") == 1) {
                d = new DropData(
                        type,
                        (int)this.getData().get("banky.drops."+name+".drop")
                );
                if((int)this.getData().get("banky.drops."+name+".canVary") == 1) {
                    d.addVariance((int)this.getData().get("banky.drops."+name+".variance"));
                }
            } else {
                d = new DropData(type);
            }
            this.dropData.add(d);
        }
    }

    private void initDropData() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + ("~~ Initializing Drop Data ~~"));
        DropData.TYPE_LIST.forEach( t -> {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + (">>> Adding " + t));
            DropData d = new DropData(DropData.findType(t));
            d.save();
        });
    }

}
