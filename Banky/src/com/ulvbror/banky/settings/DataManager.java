package com.ulvbror.banky.settings;

import com.ulvbror.banky.data.BankData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private DataManager() {}

    static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    FileConfiguration dataconfig;
    File file;

    public List<BankData> bankData = new ArrayList<>();

    public void setup(Plugin p) {
        if(!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }

        file = new File(p.getDataFolder(), "players.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "ERROR: Unable to create players.yml");
            }
        }

        dataconfig = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getData() {
        return dataconfig;
    }

    public void saveData() {
        try {
            dataconfig.save(file);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "ERROR: Unable to save players.yml");
        }
    }

    public void reloadData() {
        dataconfig = YamlConfiguration.loadConfiguration(file);
    }

    public BankData joined(Player player) {
        BankData bd = new BankData(player);
        if(this.getData().get("banky."+player.getUniqueId()+".balance") != null) {
            bd.setBalance((int)this.getData().get("banky."+player.getUniqueId()+".balance"));
        }
        if(this.getData().get("banky."+player.getUniqueId()+".wallet") != null) {
            bd.setWallet((int)this.getData().get("banky."+player.getUniqueId()+".wallet"));
        }
        bd.save();
        this.bankData.add(bd);
        return bd;
    }

    public void left(Player player) {
        BankData bd = BankData.find(this.bankData, player);
        if(bd != null) {
            bd.save();
            this.bankData.remove(bd);
        }
    }
}
