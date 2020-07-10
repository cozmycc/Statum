package com.klasowy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;

public final class Main extends JavaPlugin {

    final int build = 22;
    String[] teams = {"wild", "pergamon", "molkograd", "vallis", "nether", "end"};
    String[] teamNames = {"Dzicz", "Pergamon", "Molkograd", "Vallis Civitatis", "Nether", "The End"};

    @Override
    public void onEnable() {
        getServer().getOnlinePlayers().forEach(p -> p.sendMessage("[Plugin] " + ChatColor.GREEN + "KlasowyPlugin v1.2.3 (" + build + ") is enabled!"));

        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        for(int i = 0; i < teams.length; i++) {
            sb.registerNewTeam(teams[i]);
            sb.getTeam(teams[i]).setDisplayName(teamNames[i]);
        }

        Bukkit.getPluginManager().registerEvents(new KlasowyListener(this, sb), this);

        final boolean[] timeShown = {false};

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {

            long time = getServer().getWorld("world").getTime();

            if(!timeShown[0]) {
                if (time > 50 && time < 400) {
                    timeShown[0] = true;
                    int day = (int) (Math.floor(getServer().getWorld("world").getFullTime()/24000)+1);
                    int year = (int) Math.floor(day/360);
                    day -= year*360;
                    year++;
                    getServer().broadcastMessage(ChatColor.GOLD + "Jest " + ChatColor.GREEN + day + ChatColor.GOLD + " dzień, roku " + ChatColor.GREEN + year + ChatColor.GOLD + ".");
                }
            }

            if(time > 500) timeShown[0] = false;

            }, 0L, 50L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
