package com.ulvbror.banky.commands;

import com.ulvbror.banky.data.BankData;
import com.ulvbror.banky.settings.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {

    DataManager dataManager = DataManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.AQUA + "The '/bank' commands are client only... for now!");
            return true;
        } //IS CONSOLE

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("bank")) {
            if(args.length < 1 || args.length > 2) {
                sender.sendMessage(ChatColor.RED + "Invalid argument count for '/bank' command. " +
                        "Please use '/bank help' for usable commands and arguments");
                return true;
            }

            if(args[0].equalsIgnoreCase("help")) {
                help(sender, args);
            }
            else if(args[0].equalsIgnoreCase("balance")) {
                displayBalance(sender, player);
            }
            else if(args.length >= 2 && args[0].equalsIgnoreCase("deposit")) {
                try {
                    deposit(sender, player, Integer.parseInt(args[1]));
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "/bank deposit <amount> : <amount> must be a valid number");
                }
            }
            else if(args.length >= 2 && args[0].equalsIgnoreCase("withdraw")) {
                try {
                    withdraw(sender, player, Integer.parseInt(args[1]));
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "/bank withdraw <amount> : <amount> must be a valid number");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "Unknown argument given, use /bank help for valid arguments");
            }
        }

        return true;
    }

    private void help(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.AQUA + "-- [Banky Command Helper] --");
        if(args.length == 1) {
            sender.sendMessage(ChatColor.YELLOW + "Enter /help <command> for specific instructions");
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "- List of usable /bank arguments -");
            sender.sendMessage(ChatColor.YELLOW + "> /bank balance");
            sender.sendMessage(ChatColor.YELLOW + "> /bank deposit <amount>");
            sender.sendMessage(ChatColor.YELLOW + "> /bank withdraw <amount>");
        } else {
            switch(args[1]) {
                case "balance":
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bank balance");
                    sender.sendMessage(ChatColor.YELLOW + "- Displays your current bank balance");
                    break;
                case "deposit":
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bank deposit <amount>");
                    sender.sendMessage(ChatColor.YELLOW + "- Deposits the amount given into your bank");
                    sender.sendMessage(ChatColor.YELLOW + "  balance from your personal balance");
                    break;
                case "withdraw":
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bank withdraw <amount>");
                    sender.sendMessage(ChatColor.YELLOW + "- Withdraws the amount given into your");
                    sender.sendMessage(ChatColor.YELLOW + "  personal balance from your bank balance");
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Unrecognized /help argument, please refer to");
                    sender.sendMessage(ChatColor.RED + "list of usable arguments from /help");
                    break;
            }
        }
    }

    private void displayBalance(CommandSender sender, Player player) {
        BankData bd = BankData.find(dataManager.bankData, player);
        if(bd != null) {
            sender.sendMessage((ChatColor.YELLOW + "Your current bank balance is $") +
                    (ChatColor.AQUA + Integer.toString(bd.balance()) ) );
        } else {
            sender.sendMessage(ChatColor.RED + "Unable to find your account! Please try again and");
            sender.sendMessage(ChatColor.RED + "if this problem continues, contact an administrator");
        }
    }

    private void deposit(CommandSender sender, Player player, int amount) {
        BankData bd = BankData.find(dataManager.bankData, player);
        if (bd != null) {
            if(amount <= 0) {
                sender.sendMessage( (ChatColor.RED + "The <amount> given must be greater than 0") );
            }
            else if( amount <= bd.wallet() ) {
                bd.give(amount);
                bd.save();
                player.getScoreboard().getObjective("wallet").getScore(ChatColor.GREEN + "$").setScore( bd.wallet() );
                sender.sendMessage( (ChatColor.YELLOW + "You have deposited $") + (ChatColor.GREEN + Integer.toString(amount)) );
            }
            else {
                sender.sendMessage( (ChatColor.RED + "You do not have enough in your wallet to deposit that much") );
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Unable to find your account! Please try again and");
            sender.sendMessage(ChatColor.RED + "if this problem continues, contact an administrator");
        }
    }

    private void withdraw(CommandSender sender, Player player, int amount) {
        BankData bd = BankData.find(dataManager.bankData, player);
        if (bd != null) {
            if(amount <= 0) {
                sender.sendMessage( (ChatColor.RED + "The <amount> given must be greater than 0") );
            }
            else if( amount <= bd.balance() ) {
                bd.take(amount);
                bd.save();
                player.getScoreboard().getObjective("wallet").getScore(ChatColor.GREEN + "$").setScore( bd.wallet() );
                sender.sendMessage( (ChatColor.YELLOW + "You have withdrawn $") + (ChatColor.RED + Integer.toString(amount)) );
            }
            else {
                sender.sendMessage( (ChatColor.RED + "You do not have enough in your bank to withdraw that much") );
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Unable to find your account! Please try again and");
            sender.sendMessage(ChatColor.RED + "if this problem continues, contact an administrator");
        }
    }
}
