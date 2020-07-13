package com.klasowy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public final class Main extends JavaPlugin {

    final int build = 24;
    final static String[] teams = {"wild", "pergamon", "molkograd", "vallis", "maginia", "nether", "end"};
    final static String[] teamNames = {"Dzicz", "Pergamon", "Molkograd", "Vallis Civitatis", "Maginia", "Nether", "The End"};

    @Override
    public void onEnable() {
        getServer().getOnlinePlayers().forEach(p -> p.sendMessage("[Plugin] " + ChatColor.GREEN + "KlasowyPlugin v1.2.5 (" + build + ") is enabled!"));

        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        for(int i = 0; i < teams.length; i++) {
            sb.registerNewTeam(teams[i]);
            sb.getTeam(teams[i]).setDisplayName(teamNames[i]);
        }

        Bukkit.getPluginManager().registerEvents(new KlasowyListener(this, sb), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
