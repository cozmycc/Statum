package com.statum;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class StatumListener implements Listener {
    static ArrayList<City> cities = new ArrayList<>();
    Scoreboard scoreboard;
    JavaPlugin plugin;
    Vector prev = new Vector(0, 0, 0);

    StatumListener(JavaPlugin plugin, Scoreboard scoreboard) {
        this.plugin = plugin;
        this.scoreboard = scoreboard;

        //TODO: Register scoreboards

        //        cities.put("wild", new HashMap<>());
    }

    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ())
            return;

        if (calcDistance(e.getFrom().getBlockX(), e.getFrom().getBlockZ(), prev.getBlockX(), prev.getBlockZ()) < 5)
            return;
        prev = new Vector(e.getFrom().getBlockX(), 0, e.getFrom().getBlockZ());

        Player player = e.getPlayer();
        Location loc = player.getLocation();

        //TODO: Detect if player is in the city, is leaving the city or is entering the city

    }

    double calcDistance(int x1, int y1, int x2, int y2) {
        return (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
    }

    void setPlayerCity(UUID playerId, int cityId) {
        cities.forEach(city -> city.setVisitor(playerId, cities.get(cityId).name.equals(city.name)));
    }

    boolean isInCity(Player player, int cityId) {
        return cities.get(cityId).visitors.contains(player.getUniqueId());
    }

    void setPlayerTeam(Player player, String teamName) {
        scoreboard.getTeams().forEach(team -> {
            if (team.hasEntry(player.getName()))
                team.removeEntry(player.getName());

            if (team.getName().equals(teamName))
                team.addEntry(player.getName());
        });
        player.setScoreboard(scoreboard);
    }

    boolean isInRect(Location loc, double x1, double x2, double y1, double y2) {
        return loc.getX() > x1 && loc.getX() < x2 && loc.getZ() > y1 && loc.getZ() < y2;
    }

    void sendGreeting(Player player, String city) {
        player.sendMessage(ChatColor.GOLD + "Welcome to " + ChatColor.GREEN + city + ChatColor.GOLD + "!");
    }

    void sendGoodbye(Player player, String city) {
        player.sendMessage(ChatColor.GOLD + "You are now leaving " + ChatColor.GREEN + city + ChatColor.GOLD + "!");
    }
}