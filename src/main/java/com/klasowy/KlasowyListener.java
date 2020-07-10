package com.klasowy;

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

import java.util.HashMap;
import java.util.UUID;

public class KlasowyListener implements Listener {

    HashMap<String, HashMap<UUID, Boolean>> cities = new HashMap<>();
    Scoreboard sb;
    JavaPlugin plugin;
    Vector prev = new Vector(0,0,0);

    KlasowyListener(JavaPlugin pl, Scoreboard scoreboard) {
        sb = scoreboard;
        plugin = pl;

        cities.put("wild", new HashMap<>());
        cities.put("molkograd", new HashMap<>());
        cities.put("vallis", new HashMap<>());
        cities.put("pergamon", new HashMap<>());
        cities.put("nether", new HashMap<>());
        cities.put("end", new HashMap<>());
    }

    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ())
            return;

        if(calcDistance(e.getFrom().getBlockX(), e.getFrom().getBlockZ(), prev.getBlockX(), prev.getBlockZ()) < 5)
            return;
        prev = new Vector(e.getFrom().getBlockX(),0, e.getFrom().getBlockZ());

        Player player = e.getPlayer();
        Location loc = player.getLocation();

        if (player.getWorld().getName().equals("world")) {
            // Pergamon
            // X: 865 | 1000  Z: 331 | 453
            if (isInRect(loc, 865, 1000, 331, 453)) {
                if (!isInCity(player, "pergamon", true)) {
                    sendGreeting(player, "Pergamonie");
                    setPlayerTeam(player, "pergamon");
                    setPlayerCity(player.getUniqueId(), "pergamon");
                }

                // Molkograd
                // X: 825 | 1035  Z: -170 | 100
            } else if (isInRect(loc, 825, 1035, -170, 100)) {
                if (!isInCity(player, "molkograd", true)) {
                    sendGreeting(player, "Molkogradzie");
                    setPlayerTeam(player, "molkograd");
                    setPlayerCity(player.getUniqueId(), "molkograd");
                }
                // Vallis Civitatis
                // X: 560 | 752  Z: 1000 | 1340
            } else if (isInRect(loc, 560, 752, 1000, 1340)) {
                if (!isInCity(player, "vallis", true)) {
                    sendGreeting(player, "Vallis Civitatis");
                    setPlayerTeam(player, "vallis");
                    setPlayerCity(player.getUniqueId(), "vallis");
                }
            } else {
                if (isInCity(player, "pergamon")) sendGoodbye(player, "Pergamon");
                else if (isInCity(player, "molkograd")) sendGoodbye(player, "Molkograd");
                else if (isInCity(player, "vallis")) sendGoodbye(player, "Vallis Civitatis");
                setPlayerTeam(player, "wild");
                setPlayerCity(player.getUniqueId(), "wild");
            }
        } else if (player.getWorld().getName().equals("world_nether")) {
            setPlayerTeam(player, "nether");
            setPlayerCity(player.getUniqueId(), "nether");
        } else if (player.getWorld().getName().equals("world_the_end")) {
            setPlayerTeam(player, "end");
            setPlayerCity(player.getUniqueId(), "end");
        }
    }

    double calcDistance(int x1,int y1,int x2,int y2)
    {
        return (Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));
    }

    void setPlayerCity(UUID playerId, String cityName) {
        cities.forEach((name, city) -> city.put(playerId, name.equals(cityName)));
    }

    boolean isInCity(Player player, String cityName) {
        return cities.get(cityName).getOrDefault(player.getUniqueId(), false);
    }

    boolean isInCity(Player player, String cityName, boolean defaultVal) {
        return cities.get(cityName).getOrDefault(player.getUniqueId(), defaultVal);
    }

    void setPlayerTeam(Player player, String teamName) {
        sb.getTeams().forEach(team -> {
            if (team.hasEntry(player.getName()))
                team.removeEntry(player.getName());

            if (team.getName().equals(teamName))
                team.addEntry(player.getName());
        });
        player.setScoreboard(sb);
    }

    boolean isInRect(Location loc, double x1, double x2, double y1, double y2) {
        return loc.getX() > x1 && loc.getX() < x2 && loc.getZ() > y1 && loc.getZ() < y2;
    }

    void sendGreeting(Player player, String city) {
        player.sendMessage(ChatColor.GOLD + "Witamy w " + ChatColor.GREEN + city + ChatColor.GOLD + "!");
    }

    void sendGoodbye(Player player, String city) {
        player.sendMessage(ChatColor.GREEN + city + ChatColor.GOLD + " żegna!");
    }
}