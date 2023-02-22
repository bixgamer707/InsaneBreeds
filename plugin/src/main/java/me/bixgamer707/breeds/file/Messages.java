package me.bixgamer707.breeds.file;

import me.bixgamer707.breeds.InsaneBreeds;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Messages {

    private final Set<YamlFile> langs;
    private final InsaneBreeds plugin;
    private File folder;

    public Messages(InsaneBreeds plugin) {
        langs = new HashSet<>();
        this.plugin = plugin;
        folder = new File(plugin.getDataFolder().getAbsolutePath() + "/langs");
    }

    public void start() {
        if(folder == null) {
            folder = new File(plugin.getDataFolder().getAbsolutePath() + "/langs");
        }

        langs.add(new YamlFile(folder, "messages_en.yml"));
        langs.add(new YamlFile(folder, "messages_es.yml"));

        File[] files = folder.listFiles();

        if(files == null){
            return;
        }

        for (File file : files) {
            if (file.getName().endsWith(".yml")) {
                langs.add(new YamlFile(folder, file.getName()));
            }
        }
    }

    public void stop(){
        langs.forEach(YamlFile::save);
        langs.clear();
    }

    public void reload(){
        langs.clear();
        start();
    }

    public YamlFile getLang(){
        YamlFile config = plugin.getConfigFile();

        String lang = config.get().contains("Settings.messages") ? config.get().getString("Settings.messages") : "messages_en.yml";

        for(YamlFile yamlFile : langs){
            if(yamlFile.getId().equalsIgnoreCase(lang)){
                return yamlFile;
            }
        }
        return null;
    }

}
