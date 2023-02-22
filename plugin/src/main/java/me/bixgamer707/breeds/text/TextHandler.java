package me.bixgamer707.breeds.text;

import me.bixgamer707.breeds.file.YamlFile;
import org.bukkit.entity.Player;

import java.util.List;

public interface TextHandler {

    String colorize(String text);

    String colorize(String text, Player player);

    String colorizeMessages(String path, YamlFile file, Player player);

    String colorizeMessages(String path, YamlFile file);

    StringBuilder colorizeList(List<String> list);

    StringBuilder colorizeList(List<String> list, Player player);

    List<String> colorize(List<String> list);

    List<String> colorize(List<String> list, Player player);
}
