package me.bixgamer707.breeds.breed.user.manager;

import me.bixgamer707.breeds.InsaneBreeds;
import me.bixgamer707.breeds.breed.Breed;
import me.bixgamer707.breeds.breed.user.User;
import me.bixgamer707.breeds.file.YamlFile;
import me.bixgamer707.breeds.user.Data;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;

public class UserManager {

    private final Map<UUID, User> userMap;
    private final InsaneBreeds plugin;

    public UserManager(InsaneBreeds plugin){
        this.plugin = plugin;
        this.userMap = new HashMap<>();
    }


    public Map<UUID, User> getUserMap(){
        return userMap;
    }

    public void addUser(User user){
        userMap.put(user.getUuid(), user);
    }

    public void addUser(UUID uuid){
        userMap.put(uuid, new User(uuid));
    }

    public void removeUser(User user){
        userMap.remove(user.getUuid());
    }

    public void removeUser(UUID uuid){
        userMap.remove(uuid);
    }

    public User getUser(UUID uuid){
        return userMap.get(uuid);
    }

    public void loadUsers(YamlFile file){

    }

    public void loadUser(YamlFile file, UUID uuid){
        ConfigurationSection section = file.get().getConfigurationSection("Users");

        if(section == null){
            addUser(uuid);
            return;
        }

        if(section.get(uuid.toString()) == null){
            addUser(uuid);
            return;
        }

        User user = new User(uuid);

        user.getPoints().set(section.getInt(uuid + ".points"));

        for(String id : section.getConfigurationSection(uuid + ".breeds").getKeys(false)){
            Breed breed;

            if(section.contains(id+ ".uuid")) {
                breed = plugin.getBreedManager().getBreed(UUID.fromString(section.getString(id + ".uuid")));
            }else{
                breed = plugin.getBreedManager().getBreed(id);
            }

            if(breed == null){
                section.set(uuid + ".breeds." + id, null);
                file.save();
                continue;
            }

            user.addBreed(breed);
        }

        for(String stat : section.getConfigurationSection(uuid + ".stats").getKeys(false)){
            user.addStat(stat, section.getInt(uuid + ".stats." + stat));
        }

        addUser(user);
    }

    public void saveUsers(YamlFile file){
        ConfigurationSection section = file.get().getConfigurationSection("Users");

        if(section == null){
            section = file.get().createSection("Users");
        }

        for(User user : userMap.values()){
            section.set(user.getUuid() + ".points", user.getPoints().get());
            for(Breed breed : user.getBreeds().values()) {
                section.set(user.getUuid() + ".breeds." + breed.getName() + ".uuid", breed.getUuid().toString());
            }
            for (Map.Entry<String, Data<Integer>> entry : user.getStats().entrySet()) {
                section.set(user.getUuid() + ".stats." + entry.getKey(), entry.getValue().get());
            }
        }

        file.save();
    }
}
