package me.bixgamer707.breeds.listeners;

import me.bixgamer707.breeds.InsaneBreeds;
import me.bixgamer707.breeds.breed.user.User;
import me.bixgamer707.breeds.file.YamlFile;
import me.bixgamer707.breeds.menu.MenuFiles;
import me.bixgamer707.breeds.user.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final InsaneBreeds plugin;

    public PlayerJoinListener(InsaneBreeds plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncJoin(AsyncPlayerPreLoginEvent event){
        plugin.getUserManager().loadUser(plugin.getUsers(), event.getUniqueId());
    }

    @EventHandler
    public void onJoinGui(PlayerJoinEvent event) {
        YamlFile config = plugin.getConfigFile();

        if (!config.get().getBoolean("Settings.gui.send-on-join")) {
            return;
        }

        Player player = event.getPlayer();

        if(plugin.getUserManager().getUser(player.getUniqueId()) == null){
            plugin.getUserManager().loadUser(plugin.getUsers(), player.getUniqueId());
        }

        int delay = config.get().contains("Settings.gui.delay") ? config.get().getInt("Settings.gui.delay") : 10;

        String id = config.get().contains("Settings.gui.id") ? config.get().getString("Settings.gui.id") : "select-breed";

        MenuFiles menuFiles = new MenuFiles(plugin, id);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> menuFiles.open(player), delay);
    }

    @EventHandler
    public void onJoinStat(PlayerJoinEvent event){
        Player player = event.getPlayer();

        User user = plugin.getUserManager().getUser(player.getUniqueId());

        if(user == null){
            return;
        }

        Data<Integer> speed = user.getStat("speed");
        Data<Integer> health = user.getStat("health");

        if(speed == null){
            return;
        }

        player.setWalkSpeed(speed.get().floatValue());

        if(health == null){
            return;
        }

        player.setMaxHealth(health.get().doubleValue());
    }
}
