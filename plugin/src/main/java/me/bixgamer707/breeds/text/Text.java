package me.bixgamer707.breeds.text;

import me.bixgamer707.breeds.file.YamlFile;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text implements TextHandler {

    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");
    private final String prefix;

    public Text(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String colorize(String text) {
        if (Bukkit.getVersion().contains("1.16")) {
            Matcher match = HEX_PATTERN.matcher(text);
            while (match.find()) {
                String color = text.substring(match.start(), match.end());
                text = text.replace(color, ChatColor.of(color) + "");
                match = HEX_PATTERN.matcher(text);
            }
        }
        return ChatColor.translateAlternateColorCodes(
                '&',
                text.replaceAll("%prefix%", prefix)
        );
    }


    @Override
    public String colorize(String text, Player player) {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && player != null){
            text = PlaceholderAPI.setPlaceholders(player, text);
        }
        return colorize(text);
    }

    @Override
    public String colorizeMessages(String path, YamlFile file, Player player) {
        return colorize(
                file.get().contains(path) ? file.get().getString(path) : ""
                , player
        );
    }

    @Override
    public String colorizeMessages(String path, YamlFile file) {
        return colorize(
                file.get().contains(path) ? file.get().getString(path) : ""
        );
    }

    @Override
    public StringBuilder colorizeList(List<String> list) {
        return colorizeList(list, null);
    }

    @Override
    public StringBuilder colorizeList(List<String> list, Player player) {
        StringBuilder builder = new StringBuilder();

        list.forEach(line -> builder.append(colorize(line, player)).append("\n"));

        return builder;
    }

    @Override
    public List<String> colorize(List<String> list) {
        return colorize(list, null);
    }

    @Override
    public List<String> colorize(List<String> list, Player player) {
        list.replaceAll(line -> colorize(line, player));
        return list;
    }
}
