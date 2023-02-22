package me.bixgamer707.breeds.breed.user;

import me.bixgamer707.breeds.breed.Breed;
import me.bixgamer707.breeds.breed.user.data.Points;
import me.bixgamer707.breeds.breed.user.data.Stats;
import me.bixgamer707.breeds.user.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private final Data<Integer> points;
    private final Map<String, Data<Integer>> stats;

    private final Map<UUID, Breed> breedMap;

    public User(UUID uuid){
        this.uuid = uuid;
        this.points = new Points(0);
        this.breedMap = new HashMap<>();
        this.stats = new HashMap<>();

        this.stats.put("strength", new Stats(0));
        this.stats.put("resistance", new Stats(0));
        this.stats.put("health", new Stats(0));
        this.stats.put("speed", new Stats(0));
    }

    public UUID getUuid(){
        return uuid;
    }

    public Data<Integer> getPoints(){
        return points;
    }

    public Map<UUID, Breed> getBreeds(){
        return breedMap;
    }

    public void addBreed(Breed breed){
        breedMap.put(breed.getUuid(), breed);
    }

    public void removeBreed(Breed breed){
        breedMap.remove(breed.getUuid());
    }

    public void removeBreed(UUID uuid){
        breedMap.remove(uuid);
    }

    public Breed getBreed(UUID uuid){
        return breedMap.get(uuid);
    }

    public Map<String, Data<Integer>> getStats(){
        return stats;
    }

    public Data<Integer> getStat(String stat){
        return stats.get(stat);
    }

    public void setStat(String stat, Integer value){
        stats.put(stat, new Stats(value));
    }

    public void addStat(String statName, Integer value){
        Data<Integer> stat = stats.get(statName);

        if(stat == null){
            stats.put(statName, new Stats(value));
            return;
        }

        stat.add(value);
    }
}
