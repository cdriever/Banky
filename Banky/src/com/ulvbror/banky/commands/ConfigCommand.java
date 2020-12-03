package com.ulvbror.banky.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        } //IS PLAYER & NOT OP

        if(command.getName().equalsIgnoreCase("banky")) {
            if(args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Invalid argument count for '/banky' command. " +
                        "Please use '/banky help' for usable commands and arguments");
                return true;
            }

            if(args[0].equalsIgnoreCase("help")) {
                help(sender, args);
            }
            else if(args[0].equalsIgnoreCase("config")) {

            }
        }

        return true;
    }

    private void help(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.AQUA + "-- [Banky Command Helper] --");
        if(args.length == 1) {
            sender.sendMessage(ChatColor.YELLOW + "Enter /help <command> for specific instructions");
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "- List of usable banky commands -");
            sender.sendMessage( (ChatColor.LIGHT_PURPLE + "- Commands in ")+
                    (ChatColor.BLUE + "BLUE")+
                    (ChatColor.LIGHT_PURPLE + " require OP privileges -"));
            //bank
            sender.sendMessage(ChatColor.YELLOW + "> /bank balance");
            sender.sendMessage(ChatColor.YELLOW + "> /bank deposit <amount>");
            sender.sendMessage(ChatColor.YELLOW + "> /bank withdraw <amount>");
            //config
            sender.sendMessage(ChatColor.BLUE + "> /bankyconfig drops <entity> [set|reset|vary] <amount>");
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
                case "bankyconfig":
                    switch (args[2]) {
                        case "drops":
                            sender.sendMessage(ChatColor.LIGHT_PURPLE + "/bankyconfig drops <entity> [set|reset|vary] <amount>");
                            sender.sendMessage(ChatColor.YELLOW + "- Change the amount of money a mob drops, change");
                            sender.sendMessage(ChatColor.YELLOW + "  how much the amount can vary up or down, or");
                            sender.sendMessage(ChatColor.YELLOW + "  reset the mob to drop nothing");
                            break;
                        default:
                            sender.sendMessage(ChatColor.RED + "Unrecognized /help bankyconfig argument, please refer to");
                            sender.sendMessage(ChatColor.RED + "list of usable arguments from /help");
                            break;
                    }
                default:
                    sender.sendMessage(ChatColor.RED + "Unrecognized /help argument, please refer to");
                    sender.sendMessage(ChatColor.RED + "list of usable arguments from /help");
                    break;
            }
        }
    }
}
