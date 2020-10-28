package com.statum;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        StatumData data = StatumData.loadData("data.bin");
        //TODO: Load data to scoreboards
//        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
//        for (int i = 0; i < teams.length; i++) {
//            sb.registerNewTeam(teams[i]);
//            sb.getTeam(teams[i]).setDisplayName(teamNames[i]);
//        }

        StatumCommands.registerCommands(this);
//        Bukkit.getPluginManager().registerEvents(new StatumListener(this, sb), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
