package me.bixgamer707.breeds.util;

import dev.lone.itemsadder.api.CustomStack;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import me.bixgamer707.breeds.file.YamlFile;
import me.bixgamer707.breeds.text.TextHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtil {

    public static ItemStack getItemCustom(YamlFile yamlFile, String path, TextHandler textHandler, Player player){
        if(!yamlFile.get().contains(path) || yamlFile.get().getString(path) == null) {
            return getItem(yamlFile, path, textHandler);
        }

        String[] split = yamlFile.get().getString(path).split(":");

        if(!(split.length > 0)){
            return getItem(yamlFile, path, textHandler);
        }

        if(split[0].equalsIgnoreCase("ia") && itemsAdderEnabled()){
            CustomStack item = CustomStack.getInstance(split[1]);

            if(item == null) return getItem(yamlFile, path,textHandler);

            ItemStack itemStack = item.getItemStack();

            ItemMeta meta = itemStack.getItemMeta();

            if(meta.getLore() != null || !meta.getLore().isEmpty()){
                meta.setLore(textHandler.colorize(meta.getLore(), player));
            }

            return itemStack;
        }
        if(split[0].equalsIgnoreCase("or") && oraxenEnabled()){
            ItemBuilder item = OraxenItems.getItemById(split[1]);

            if(item == null) return getItem(yamlFile, path,textHandler);

            ItemStack itemStack = item.build();

            ItemMeta meta = itemStack.getItemMeta();

            if(meta.getLore() != null || !meta.getLore().isEmpty()){
                meta.setLore(textHandler.colorize(meta.getLore(), player));
            }

            return itemStack;
        }

        return getItem(yamlFile, path,textHandler);
    }
    public static ItemStack getItemCustom(YamlFile yamlFile, String path, TextHandler textHandler){
        return getItemCustom(yamlFile, path, textHandler, null);
    }

    public static ItemStack getItem(YamlFile yamlFile, String path, TextHandler textHandler){
        ItemStack item = new ItemStack(Material.BARRIER);

        if(yamlFile.get().contains(path + ".material")){
            item = new ItemStack(Material.valueOf(yamlFile.get().getString(path + ".material")));
        }

        ItemMeta meta = item.getItemMeta();

        if(yamlFile.get().contains(path + ".name")){
            meta.setDisplayName(textHandler.colorize(yamlFile.get().getString(path + ".name")));
        }else{
            meta.setDisplayName(textHandler.colorize("&cItem not found, contact to admin."));
        }

        if(yamlFile.get().contains(path + ".lore")){
            meta.setLore(textHandler.colorize(yamlFile.get().getStringList(path + ".lore")));
        }

        if(yamlFile.get().contains(path + ".amount")){
            item.setAmount(yamlFile.get().getInt(path + ".amount"));
        }

        if(yamlFile.get().contains(path + ".enchantments")){
            List<String> enchantments = yamlFile.get().getStringList(path + ".enchantments");

            for(String enchantment : enchantments){
                String[] split = enchantment.split(":");
                meta.addEnchant(Enchantment.getByName(split[0]), Integer.parseInt(split[1]), true);
            }
        }

        if(yamlFile.get().contains(path + ".flags")){
            List<String> flags = yamlFile.get().getStringList(path + ".flags");

            for(String flag : flags){
                meta.addItemFlags(ItemFlag.valueOf(flag));
            }
        }

        if(yamlFile.get().contains(path + ".custom")){
            meta.setCustomModelData(yamlFile.get().getInt(path + ".custom"));
        }

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isCustomItem(YamlFile yamlFile, String path, ItemStack item, TextHandler textHandler){
        return item.isSimilar(getItemCustom(yamlFile, path, textHandler));
    }
    
    public static boolean itemsAdderEnabled(){
        return Bukkit.getPluginManager().getPlugin("ItemsAdder") != null;
    }

    public static boolean oraxenEnabled(){
        return Bukkit.getPluginManager().getPlugin("Oraxen") != null;
    }
}
