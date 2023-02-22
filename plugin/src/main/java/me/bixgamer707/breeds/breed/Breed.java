package me.bixgamer707.breeds.breed;

import org.bukkit.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Breed{

    private final String name;
    private final UUID uuid;

    private String displayName;
    private Color color;

    private Map<String, Integer> multiplierStats;
    public Breed(String name, UUID uuid, Color color){
        this.name = name;
        this.uuid = uuid;
        this.color = color;
        this.displayName = "";

        this.multiplierStats = new HashMap<>();

        this.multiplierStats.put("strength", 1);
        this.multiplierStats.put("speed", 1);
        this.multiplierStats.put("health", 1);
        this.multiplierStats.put("resistance", 1);

    }

    public Breed(String name, Color color){
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.color = color;
        this.displayName = "";
    }

    public String getName(){
        return name;
    }

    public UUID getUuid(){
        return uuid;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public String getDisplayName(){
        return displayName;
    }

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    public Map<String, Integer> getMultiplierStats(){
        return multiplierStats;
    }

    public void setMultiplier(String stat, Integer value){
        multiplierStats.put(stat, value);
    }

    public Integer getMultiplier(String stat){
        return multiplierStats.get(stat);
    }

    public void addMultiplier(String stat, Integer value){
        multiplierStats.put(stat, multiplierStats.get(stat) + value);
    }

    public void removeMultiplier(String stat, Integer value){
        multiplierStats.put(stat, multiplierStats.get(stat) - value);
    }
}
