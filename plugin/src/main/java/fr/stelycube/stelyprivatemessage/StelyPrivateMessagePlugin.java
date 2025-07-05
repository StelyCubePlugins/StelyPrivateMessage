package fr.stelycube.stelyprivatemessage;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.stelycube.stelyprivatemessage.config.ConfigLoader;
import net.md_5.bungee.api.plugin.Plugin;

public class StelyPrivateMessagePlugin extends Plugin {

    @Override
    public void onEnable() {
        final Logger logger = getLogger();
        final File dataFolder = getDataFolder();
        final ConfigLoader configLoader = new ConfigLoader();

        if (!configLoader.checkDataFolder(dataFolder)) {
            logger.log(Level.WARNING, "Can not write in the directory : " + dataFolder.getAbsolutePath());
            logger.log(Level.WARNING, "Disable the plugin");
            return;
        }

        
    }

}
