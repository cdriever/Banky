package com.ulvbror.banky.data;

import com.ulvbror.banky.settings.DataManager;
import org.bukkit.entity.Player;

import java.util.List;

public class BankData {
    Player player;
    int balance;
    int wallet;

    DataManager dataManager = DataManager.getInstance();

    public BankData () {
        player = null;
        balance = 0;
        wallet = 0;
    }
    public BankData (Player p) {
        player = p;
        balance = 0;
        wallet = 0;
    }

    public void give(int amount) {
        this.balance += amount;
        this.wallet -= amount;
    }

    public void take(int amount) {
        this.balance -= amount;
        if(this.balance < 0) {
            this.wallet += amount + this.balance;
            this.balance = 0;
        }
    }

    public void save() {
        dataManager.getData().set("banky."+this.player.getUniqueId()+".balance", this.balance);
        dataManager.getData().set("banky."+this.player.getUniqueId()+".wallet", this.wallet);
        dataManager.saveData();
    }

    public void setBalance(int amount) { this.balance = amount; }
    public void setWallet(int amount) { this.wallet = amount; }

    public int balance() {
        return this.balance;
    }

    public int wallet() {
        return this.wallet;
    }

    public boolean equals(Player p) {
        return this.player.getUniqueId() == p.getUniqueId();
    }

    public static BankData find(List<BankData> bd, Player p) {
        for(BankData data : bd) {
            if(data.equals(p))
                return data;
        }
        return null;
    }
}
