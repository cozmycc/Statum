package com.statum;

import org.bukkit.Bukkit;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StatumData implements Serializable {
    private static transient final long serialVersionUID = -1681012206529286330L;

    public final HashMap<String, HashMap<UUID, Boolean>> cities;

    public StatumData(HashMap<String, HashMap<UUID, Boolean>> cities) {
        this.cities = cities;
    }

    public StatumData(StatumData loadedData) {
        this.cities = loadedData.cities;
    }

    public boolean saveData(String filePath) {
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public static StatumData loadData(String filePath) {
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            StatumData data = (StatumData) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public static void saveCities(HashMap<String, HashMap<UUID, Boolean>> cities) {
        new StatumData(cities).saveData("Statum.data");
        Bukkit.getServer().getLogger().log(Level.INFO, "Data Saved");
    }
}
