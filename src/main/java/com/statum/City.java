package com.statum;

import java.util.ArrayList;
import java.util.UUID;

public class City {
    public final String name;
    public final double x1;
    public final double x2;
    public final double y1;
    public final double y2;
    public final UUID owner;
    public ArrayList<UUID> visitors;

    public City(String name, double x1, double x2, double y1, double y2, UUID owner) {
        this.name = name;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.owner = owner;
    }

    public void setVisitor(UUID uuid, boolean inCity) {
        if(inCity)
            visitors.remove(uuid);
        else
            visitors.add(uuid);
    }
}
