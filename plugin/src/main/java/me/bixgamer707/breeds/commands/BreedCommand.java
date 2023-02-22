package me.bixgamer707.breeds.commands;

import me.bixgamer707.breeds.InsaneBreeds;
import me.bixgamer707.breeds.breed.Breed;
import me.bixgamer707.breeds.breed.user.User;
import me.bixgamer707.breeds.file.YamlFile;
import me.bixgamer707.breeds.menu.MenuFiles;
import me.bixgamer707.breeds.text.TextHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BreedCommand implements CommandExecutor, TabCompleter {

    private final InsaneBreeds plugin;
    private final TextHandler textHandler;

    public BreedCommand(InsaneBreeds plugin){
        this.plugin = plugin;
        this.textHandler = plugin.getTextHandler();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage(textHandler.colorizeMessages("Messages.no-console", plugin.getMessages().getLang()));
            return true;
        }

        if(!(args.length > 0)){
            List<String> help = plugin.getMessages().getLang().get().getStringList("Messages.help");

            player.sendMessage(textHandler.colorizeList(help, player).toString());
            return false;
        }

        YamlFile config = plugin.getConfigFile();

        int delay = config.get().contains("Settings.gui.delay") ? config.get().getInt("Settings.gui.delay") : 10;

        String id = config.get().contains("Settings.gui.id") ? config.get().getString("Settings.gui.id") : "select-breed";

        MenuFiles menuFiles = new MenuFiles(plugin, id);

        switch (args[0].toLowerCase(Locale.ROOT)){
            case "add": {
                if (!player.hasPermission("insanebreeds.add")) {
                    player.sendMessage(textHandler.colorizeMessages("Messages.no-permission", plugin.getMessages().getLang(), player));
                    return true;
                }

                if(!(args.length > 2)){
                    List<String> help = plugin.getMessages().getLang().get().getStringList("Messages.help");

                    player.sendMessage(textHandler.colorizeList(help, player).toString());
                    return false;
                }

                Player target = plugin.getServer().getPlayer(args[1]);

                if(target == null){
                    player.sendMessage(textHandler.colorizeMessages("Messages.player-not-found", plugin.getMessages().getLang(), player));
                    return true;
                }

                String breedName = args[2];

                Breed breed = plugin.getBreedManager().getBreed(breedName);

                if(breed == null){
                    player.sendMessage(textHandler.colorizeMessages("Messages.breed-not-found", plugin.getMessages().getLang(), player));
                    return true;
                }

                User user = plugin.getUserManager().getUser(target.getUniqueId());

                if(user.hasBreed(breed.getUuid())){
                    player.sendMessage(textHandler.colorizeMessages("Messages.breed-already-added", plugin.getMessages().getLang(), player));
                    return true;
                }

                user.addBreed(breed);
                break;
            }
            case "remove": {
                if (!player.hasPermission("insanebreeds.remove")) {
                    player.sendMessage(textHandler.colorizeMessages("Messages.no-permission", plugin.getMessages().getLang(), player));
                    return true;
                }

                if(!(args.length > 2)){
                    List<String> help = plugin.getMessages().getLang().get().getStringList("Messages.help");

                    player.sendMessage(textHandler.colorizeList(help, player).toString());
                    return false;
                }

                Player target = plugin.getServer().getPlayer(args[1]);

                if(target == null){
                    player.sendMessage(textHandler.colorizeMessages("Messages.player-not-found", plugin.getMessages().getLang(), player));
                    return true;
                }

                String breedName = args[2];

                Breed breed = plugin.getBreedManager().getBreed(breedName);

                if(breed == null){
                    player.sendMessage(textHandler.colorizeMessages("Messages.breed-not-found", plugin.getMessages().getLang(), player));
                    return true;
                }

                User user = plugin.getUserManager().getUser(target.getUniqueId());

                if(!user.hasBreed(breed.getUuid())){
                    player.sendMessage(textHandler.colorizeMessages("Messages.breed-not-added", plugin.getMessages().getLang(), player));
                    return true;
                }

                user.removeBreed(breed);
                break;
            }
            default:
                if (!player.hasPermission("insanebreeds.gui")){
                    player.sendMessage(textHandler.colorizeMessages("Messages.no-permission", plugin.getMessages().getLang(), player));
                    return true;
                }

                plugin.getServer().getScheduler().runTaskLater(plugin, () -> menuFiles.open(player), delay);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            commands.add("select");
            commands.add("add");
            commands.add("remove");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }else if(args.length == 2) {
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    commands.add(player.getName());
                }
                StringUtil.copyPartialMatches(args[1], commands, completions);
            }
        }else if(args.length == 3){
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                for (Breed breed : plugin.getBreedManager().getBreedMap().values()) {
                    commands.add(breed.getName());
                }
                StringUtil.copyPartialMatches(args[2], commands, completions);
            }
        }

        Collections.sort(completions);
        return completions;
    }

}
