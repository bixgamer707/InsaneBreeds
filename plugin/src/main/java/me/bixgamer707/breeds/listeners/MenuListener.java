package me.bixgamer707.breeds.listeners;

import me.bixgamer707.breeds.menu.MenuBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getInventory().getHolder() instanceof MenuBuilder menuBuilder){
            menuBuilder.onClick(event);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(event.getInventory().getHolder() instanceof MenuBuilder menuBuilder){
            menuBuilder.onClose(event);
        }
    }
}
