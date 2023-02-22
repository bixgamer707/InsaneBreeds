package me.bixgamer707.breeds;

import me.bixgamer707.breeds.breed.manager.BreedManager;
import me.bixgamer707.breeds.breed.user.manager.UserManager;
import me.bixgamer707.breeds.commands.BreedCommand;
import me.bixgamer707.breeds.commands.MainCommand;
import me.bixgamer707.breeds.file.Messages;
import me.bixgamer707.breeds.file.YamlFile;
import me.bixgamer707.breeds.listeners.MenuListener;
import me.bixgamer707.breeds.listeners.PlayerJoinListener;
import me.bixgamer707.breeds.text.Text;
import me.bixgamer707.breeds.text.TextHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class InsaneBreeds extends JavaPlugin {

    private static InsaneBreeds instance;
    private Messages messages;
    private YamlFile configFile,users,breeds;
    private TextHandler textHandler;

    private UserManager userManager;

    private BreedManager breedManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configFile = new YamlFile(this, "config.yml");
        this.users = new YamlFile(this, "users.yml");
        this.breeds = new YamlFile(this, "breeds.yml");
        this.messages = new Messages(this);
        this.textHandler = new Text(configFile.get().getString("Settings.prefix"));
        this.userManager = new UserManager(this);
        this.breedManager = new BreedManager();

        Arrays.asList(
                new MenuListener(),
                new PlayerJoinListener(this)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        getCommand("insanebreeds").setExecutor(new MainCommand(this));
        getCommand("insanebreeds").setTabCompleter(new MainCommand(this));
        getCommand("breeds").setExecutor(new BreedCommand(this));
        getCommand("breeds").setTabCompleter(new BreedCommand(this));

        this.breedManager.loadBreeds(breeds);
        this.userManager.loadUsers(users);

        getLogger().info("InsaneBreeds has been enabled!");
    }

    @Override
    public void onDisable() {
        this.breedManager.saveBreeds(breeds);
        this.userManager.saveUsers(users);

        instance = null;
        getLogger().info("InsaneBreeds has been disabled!");
    }

    public static InsaneBreeds getInstance(){
        return instance;
    }

    public YamlFile getConfigFile(){
        return configFile;
    }

    public Messages getMessages(){
        return messages;
    }

    public TextHandler getTextHandler(){
        return textHandler;
    }

    public UserManager getUserManager(){
        return userManager;
    }

    public BreedManager getBreedManager(){
        return breedManager;
    }

    public YamlFile getUsers(){
        return users;
    }

    public YamlFile getBreeds(){
        return breeds;
    }



    public void reload(){
        this.configFile.reload();
        this.users.reload();
        this.breeds.reload();
        this.messages.reload();
        this.textHandler = new Text(configFile.get().getString("Settings.prefix"));

        this.breedManager.reloadBreeds(breeds);
    }
}
