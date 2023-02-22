package me.bixgamer707.breeds.menu;

import me.bixgamer707.breeds.InsaneBreeds;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class MenuBuilder implements InventoryHolder {

    public final InsaneBreeds plugin;
    public final Map<Integer, ItemStack> itemMap;
    public Inventory inventory;

    public MenuBuilder(InsaneBreeds plugin){
        this.plugin = plugin;
        this.itemMap = new HashMap<>();
    }

    public MenuBuilder createItem(Integer slot, ItemStack itemStack){
        itemMap.put(slot, itemStack);
        return this;
    }

    public MenuBuilder createItem(Integer slot, ItemStack itemBuilder, Integer amount){
        itemBuilder.setAmount(amount);
        itemMap.put(slot, itemBuilder);
        return this;
    }

    public MenuBuilder createMenu(String title, Integer size){
        inventory = plugin.getServer().createInventory(this, size, title);
        return this;
    }

    public MenuBuilder createMenu(String title, InventoryType type){
        inventory = plugin.getServer().createInventory(this, type, title);
        return this;
    }

    public void open(Player player){
        if(inventory == null){
            createMenu("Menu", 9);
        }

        for(Map.Entry<Integer, ItemStack> entry : itemMap.entrySet()){
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        player.openInventory(inventory);
    }

    public abstract void onClick(InventoryClickEvent event);

    public abstract void onClose(InventoryCloseEvent event);

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
