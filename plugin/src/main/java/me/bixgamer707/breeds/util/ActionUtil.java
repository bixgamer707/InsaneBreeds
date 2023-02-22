package me.bixgamer707.breeds.util;

import me.bixgamer707.breeds.InsaneBreeds;
import me.bixgamer707.breeds.file.YamlFile;
import me.bixgamer707.breeds.menu.MenuFiles;
import me.bixgamer707.breeds.text.TextHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Locale;

public class ActionUtil {

    public static void execute(String action, Player player){
        TextHandler textHandler = InsaneBreeds.getInstance().getTextHandler();
        YamlFile messages = InsaneBreeds.getInstance().getMessages().getLang();
        String[] split = action.split(":");

        String key = split[0];

        switch (split[0].toLowerCase(Locale.ROOT)){
            case "close": {
                player.closeInventory();
                player.sendMessage(textHandler.colorizeMessages("close-inventory", messages, player));
                break;
            }
            case "open": {
                if(!(split.length > 1)){
                    InsaneBreeds.getInstance().getLogger().warning("The action "+key+" requires a parameter!");
                    return;
                }

                MenuFiles menuFiles = new MenuFiles(InsaneBreeds.getInstance(), split[1]);

                menuFiles.open(player);
                break;
            }
            case "sound": {
                if(!(split.length > 1)){
                    InsaneBreeds.getInstance().getLogger().warning("The action "+key+" requires a parameter!");
                    return;
                }
                String[] sound = split[1].split(",");

                if(sound.length < 2){
                    InsaneBreeds.getInstance().getLogger().warning("The action "+key+" requires a parameter!");
                    return;
                }

                try{
                    Sound soundBukkit = Sound.valueOf(sound[0].toUpperCase(Locale.ROOT));
                    float volume = Float.parseFloat(sound[1]);
                    float pitch = Float.parseFloat(sound[2]);

                    player.playSound(player.getLocation(), soundBukkit, volume, pitch);
                }catch (IllegalArgumentException e){
                    InsaneBreeds.getInstance().getLogger().warning("The sound "+sound[0]+" does not exist!");
                }
                break;
            }
            case "message_file": {
                if(!(split.length > 1)){
                    InsaneBreeds.getInstance().getLogger().warning("The action "+key+" requires a parameter!");
                    return;
                }
                player.sendMessage(textHandler.colorizeMessages(split[1], messages, player));
                break;
            }
            case "message": {
                if(!(split.length > 1)){
                    InsaneBreeds.getInstance().getLogger().warning("The action "+key+" requires a parameter!");
                    return;
                }
                player.sendMessage(textHandler.colorize(split[1], player));
                break;
            }
            case "command_player": {
                if(!(split.length > 1)){
                    InsaneBreeds.getInstance().getLogger().warning("The action "+key+" requires a parameter!");
                    return;
                }
                player.performCommand(split[1].replaceAll("%player%", player.getName()));
                break;
            }
            case "command_console": {
                if(!(split.length > 1)){
                    InsaneBreeds.getInstance().getLogger().warning("The action "+key+" requires a parameter!");
                    return;
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), split[1].replaceAll("%player%", player.getName()));
                break;
            }
            case "broadcast": {
                if(!(split.length > 1)){
                    InsaneBreeds.getInstance().getLogger().warning("The action "+key+" requires a parameter!");
                    return;
                }

                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(textHandler.colorize(split[1].replaceAll("%player%", player.getName()), onlinePlayer)));
                break;
            }
            default: {
                InsaneBreeds.getInstance().getLogger().warning("The action "+key+" does not exist!");
                break;
            }
        }
    }
}