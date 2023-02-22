package me.bixgamer707.breeds.breed.manager;

import me.bixgamer707.breeds.breed.Breed;
import me.bixgamer707.breeds.file.YamlFile;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BreedManager {

    private final Map<UUID, Breed> breedMap;

    public BreedManager(){
        this.breedMap = new HashMap<>();
    }


    public Map<UUID, Breed> getBreedMap(){
        return breedMap;
    }

    public void addBreed(Breed breed){
        breedMap.put(breed.getUuid(), breed);
    }

    public void addBreed(String name, Color color){
        Breed breed = new Breed(name, color);

        breedMap.put(breed.getUuid(), breed);
    }

    public void addBreed(String name, UUID uuid, Color color){
        Breed breed = new Breed(name, uuid, color);

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

    public Breed getBreed(String name){
        for(Breed breed : breedMap.values()){
            if(breed.getName().equalsIgnoreCase(name)){
                return breed;
            }
        }
        return null;
    }

    public void loadBreeds(YamlFile file){
        ConfigurationSection section = file.get().getConfigurationSection("Breeds");

        if(section == null){
            return;
        }

        for(String key : section.getKeys(false)){
            UUID uuid = UUID.fromString(
                    section.contains(key+".uuid") ? section.getString(key+".uuid") : UUID.randomUUID().toString());

            Color color = section.contains(key+".color") ? Color.fromRGB(section.getInt(key+".color")) : Color.WHITE;

            String displayName = section.contains(key+".displayName") ? section.getString(key+".displayName") : "";

            Breed breed = new Breed(key, uuid, color);

            for(String stat : section.getConfigurationSection(key+".multipliers").getKeys(false)){
                breed.addMultiplier(stat, section.getInt(key+".multipliers."+stat));
            }

            breed.setDisplayName(displayName);

            addBreed(breed);
        }
    }

    public void saveBreeds(YamlFile file){
        ConfigurationSection section = file.get().getConfigurationSection("Breeds");

        if(section == null){
            section = file.get().createSection("Breeds");
        }

        for(Breed breed : breedMap.values()){
            section.set(breed.getName()+".uuid", breed.getUuid().toString());

            section.set(breed.getName()+".color", breed.getColor().asRGB());

            section.set(breed.getName()+".displayName", breed.getDisplayName());

            for(Map.Entry<String, Integer> entry : breed.getMultiplierStats().entrySet()){
                section.set(breed.getName()+".multipliers."+entry.getKey(), entry.getValue());
            }
        }

        file.save();
    }

    public void reloadBreeds(YamlFile file){
        breedMap.clear();
        loadBreeds(file);
    }
}
