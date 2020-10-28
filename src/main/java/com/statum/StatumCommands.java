package com.statum;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class StatumCommands {

    public static void registerCommands(JavaPlugin plugin) {
        plugin.getCommand("clock").setExecutor(new ClockCommand());
        plugin.getCommand("statum").setExecutor(new StatumCommand());
    }

    private static class StatumCommand implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
            if(args.length > 0) {
                switch (args[0]) {
                    //TODO: Add remove, owner, boundaries, dimension etc.
                    case "create":
                        if(args.length == 2) {
                            sender.sendMessage(ChatColor.GREEN + args[1] + ChatColor.RESET +" was created!");
                        } else {
                            sender.sendMessage("You need to specify a name");
                        }
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED + "Unknown command");
                        break;
                }
            }
            return true;
        }
    }

    private static class ClockCommand implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                long fullTime = getServer().getWorld("world").getFullTime();
                int day = (int) (Math.floor(fullTime / 24000) + 1);
                int year = (int) Math.floor(day / 360);
                day -= year * 360;
                year++;
                player.sendMessage(ChatColor.GOLD + "It is " + ChatColor.GREEN + day + ChatColor.GOLD + " day of the year " + ChatColor.GREEN + year + ChatColor.GOLD + ".");
            }
            return true;
        }
    }
}
