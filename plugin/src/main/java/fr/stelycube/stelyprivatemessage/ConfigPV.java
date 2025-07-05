package fr.stelycube.stelyprivatemessage;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

public class ConfigPV {

    public void createFile(String fileName) {
        if (!App.instance.getDataFolder().exists()) {
            App.instance.getDataFolder().mkdir();
        }

        File file = new File(App.instance.getDataFolder(), String.valueOf(fileName) + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Configuration getConfig(String fileName) {
        try {
            return ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class)
                    .load(new File(App.instance.getDataFolder(), String.valueOf(fileName) + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public void saveConfig(Configuration config, String fileName) {
        try {
            ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).save(config,
                    new File(App.instance.getDataFolder(), String.valueOf(fileName) + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig(String fileName) {
        try {
            ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class)
                    .load(new File(App.instance.getDataFolder(), String.valueOf(fileName) + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
