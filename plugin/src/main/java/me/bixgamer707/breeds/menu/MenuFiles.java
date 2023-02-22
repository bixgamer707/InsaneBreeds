package me.bixgamer707.breeds.menu;

import me.bixgamer707.breeds.InsaneBreeds;
import me.bixgamer707.breeds.file.YamlFile;
import me.bixgamer707.breeds.text.TextHandler;
import me.bixgamer707.breeds.util.ActionUtil;
import me.bixgamer707.breeds.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuFiles extends MenuBuilder {

    private final String id;
    private final TextHandler textHandler;
    public MenuFiles(InsaneBreeds plugin, String id) {
        super(plugin);
        this.id = id;
        this.textHandler = plugin.getTextHandler();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(event.getCurrentItem() == null){
            return;
        }

        YamlFile guis = plugin.getConfigFile();

        String title = guis.get().contains("Guis." + id + ".title") ? guis.get().getString("Guis." + id + ".title") : "&fMenu: &b" + id;

        if(!event.getView().getTitle().equalsIgnoreCase(textHandler.colorize(title, player))){
            return;
        }

        event.setCancelled(true);

        for (Integer slot : itemMap.keySet()){
            if(slot != event.getSlot()) continue;

            List<String> actions =
                    guis.get().contains("Guis." + id + ".items." + slot + ".actions-click") ?
                            guis.get().getStringList("Guis." + id + ".items." + slot + ".actions") : new ArrayList<>(1);

            actions.forEach(action -> ActionUtil.execute(action, player));
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        YamlFile guis = plugin.getConfigFile();

        String title = guis.get().contains("Guis." + id + ".title") ? guis.get().getString("Guis." + id + ".title") : "&fMenu: &b" + id;

        if(!event.getView().getTitle().equalsIgnoreCase(textHandler.colorize(title, player))){
            return;
        }

        List<String> actions =
                guis.get().contains("Guis." + id + ".actions-close") ?
                        guis.get().getStringList("Guis." + id + ".actions-close") : new ArrayList<>(1);

        actions.forEach(action -> ActionUtil.execute(action, player));
    }

    @Override
    public void open(Player player){
        if(inventory == null){
            createMenu(textHandler.colorize("&fMenu: &b"+id, player), 9);
        }

        YamlFile guis = plugin.getConfigFile();

        if(!guis.get().contains("Guis."+id)){
            plugin.getLogger().warning("The GUI "+id+" does not exist!");
            return;
        }

        for(String slot : guis.get().getConfigurationSection("Guis." + id + ".items").getKeys(false)){
            ItemStack item = ItemUtil.getItemCustom(guis, "Guis." + id + ".items." + slot, textHandler, player);

            createItem(Integer.parseInt(slot), item);
        }

        InventoryType type = guis.get().contains("Guis."+id+".type") ? InventoryType.valueOf(guis.get().getString("Guis."+id+".type")) : null;

        if(type != null) {
            createMenu(
                    textHandler.colorize(guis.get().getString("Guis." + id + ".title"), player),
                    type
            );
        }else{
            createMenu(
                    textHandler.colorize(guis.get().getString("Guis." + id + ".title"), player),
                    guis.get().getInt("Guis." + id + ".size")
            );
        }

        for(Map.Entry<Integer, ItemStack> entry : itemMap.entrySet()){
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        player.openInventory(inventory);
    }

    public String getId() {
        return id;
    }
}
