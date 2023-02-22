package me.bixgamer707.breeds.listeners;

import me.bixgamer707.breeds.InsaneBreeds;
import me.bixgamer707.breeds.breed.user.User;
import me.bixgamer707.breeds.user.Data;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    private final InsaneBreeds plugin;

    public DamageListener(InsaneBreeds plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
    public void onDamageResistance(EntityDamageEvent event){
        Entity entity = event.getEntity();

        if(!(entity instanceof Player player)){
            return;
        }

        User user = plugin.getUserManager().getUser(player.getUniqueId());

        if(user == null){
            return;
        }

        Data<Integer> resistance = user.getStat("resistance");

        if(resistance == null){
            return;
        }

        event.setDamage((event.getDamage() / resistance.get()));
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent event){
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if(!(entity instanceof Player player)){
            return;
        }

        if(!(damager instanceof Player damagerPlayer)){
            return;
        }

        User user = plugin.getUserManager().getUser(player.getUniqueId());

        if(user == null){
            return;
        }

        User damagerUser = plugin.getUserManager().getUser(damagerPlayer.getUniqueId());

        if(damagerUser == null){
            return;
        }

        Data<Integer> strength = damagerUser.getStat("strength");
        Data<Integer> resistance = user.getStat("resistance");

        if(resistance == null){
            return;
        }

        if(strength == null){
            return;
        }

        event.setDamage((event.getDamage() * strength.get()) / resistance.get());
    }
}
