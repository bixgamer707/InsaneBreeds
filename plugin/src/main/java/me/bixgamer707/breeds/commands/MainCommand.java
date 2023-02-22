package me.bixgamer707.breeds.commands;

import me.bixgamer707.breeds.InsaneBreeds;
import me.bixgamer707.breeds.text.TextHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainCommand implements CommandExecutor, TabCompleter {

    private final InsaneBreeds plugin;
    private final TextHandler textHandler;

    public MainCommand(InsaneBreeds plugin){
        this.plugin = plugin;
        this.textHandler = plugin.getTextHandler();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player player)){
            commandSender.sendMessage("Only players can execute this command");
            return true;
        }

        if(!(args.length > 0)){
            player.sendMessage(textHandler.colorizeMessages("Messages.help", plugin.getMessages().getLang(), player));
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)){
            case "reload":
                plugin.reload();
                player.sendMessage(textHandler.colorizeMessages("Messages.reload", plugin.getMessages().getLang(), player));
                break;
            default:
                player.sendMessage(textHandler.colorizeMessages("Messages.help", plugin.getMessages().getLang(), player));
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();
        if (args.length == 1) {
            commands.add("reload");
            commands.add("help");
            StringUtil.copyPartialMatches(args[0], commands, completions);
        }

        Collections.sort(completions);
        return completions;
    }

}
