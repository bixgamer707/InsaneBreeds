package me.bixgamer707.breeds.file;

import me.bixgamer707.breeds.InsaneBreeds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;

public class YamlFile {

    private final File file;
    private FileConfiguration configuration;
    private String id;

    public YamlFile(File folder, String fileName) {
        if (!folder.exists() && !folder.mkdirs()) {
            throw new RuntimeException("Could not create folder: " + folder);
        }
        this.file = new File(folder, fileName);
        this.id = fileName;

        if (!file.exists()) {
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName)) {
                if (stream != null) {
                    Files.copy(stream, file.toPath());
                }
            } catch (IOException e) {
                throw new UncheckedIOException("Could not copy file: " + fileName, e);
            }
        }
        reload();
    }

    public YamlFile(InsaneBreeds plugin, String fileName) {
        this(plugin.getDataFolder(), fileName);
    }

    public void reload() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration get() {
        return configuration;
    }

    public String getId(){
        return id;
    }
}