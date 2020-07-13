package com.klasowy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;

public final class Main extends JavaPlugin {

    final int build = 23;
    final static String[] teams = {"wild", "pergamon", "molkograd", "vallis", "nether", "end"};
    final static String[] teamNames = {"Dzicz", "Pergamon", "Molkograd", "Vallis Civitatis", "Nether", "The End"};
    boolean timeShown = false;

    @Override
    public void onEnable() {
        getServer().getOnlinePlayers().forEach(p -> p.sendMessage("[Plugin] " + ChatColor.GREEN + "KlasowyPlugin v1.2.4 (" + build + ") is enabled!"));

        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        for(int i = 0; i < teams.length; i++) {
            sb.registerNewTeam(teams[i]);
            sb.getTeam(teams[i]).setDisplayName(teamNames[i]);
        }

        Bukkit.getPluginManager().registerEvents(new KlasowyListener(this, sb), this);

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            long time = getServer().getWorld("world").getTime();

            if(!timeShown) {
                if (time > 50 && time < 400) {
                    timeShown = true;
                    double day = Math.floor(getServer().getWorld("world").getFullTime()/24000)+1;
                    double year = Math.floor(day/360);
                    day -= year*360;
                    year++;
                    getServer().broadcastMessage(ChatColor.GOLD + "Jest " + ChatColor.GREEN + day + ChatColor.GOLD + " dzień, roku " + ChatColor.GREEN + year + ChatColor.GOLD + ".");
                }
            }

            if(time > 500) timeShown = false;

            }, 0L, 50L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
