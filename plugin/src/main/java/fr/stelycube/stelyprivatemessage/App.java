package fr.stelycube.stelyprivatemessage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class App extends Plugin {

    public static App instance;

    public static App getinstance() {
        return instance;
    }

    public static ArrayList<String> server = new ArrayList<String>();
    public static HashMap<String, String> lastMessage = new HashMap<String, String>();
    net.md_5.bungee.config.Configuration messages;

    public void onEnable() {
        //
        server.add("Survie16");
        server.add("Survie16B");
        server.add("Survie16C");
        server.add("SurvieUsine");

        instance = this;
        System.out.println("§2[StelyMsgPv] est activé !");
        registerCommands();
        ProxyServer.getInstance().getPluginManager().registerListener(this, new Leave());

        ConfigPV config = new ConfigPV();
        config.createFile("PlayerDesactive");
        Configuration configa = config.getConfig("PlayerDesactive");

        configa.set("Pseudo", false);
        config.saveConfig(configa, "PlayerDesactive");

        try {
            if (!this.getDataFolder().exists()) {

                this.getDataFolder().mkdir();

            }

            File file = new File(this.getDataFolder().getPath(), "Config.yml");

            if (!file.exists()) {

                file.createNewFile();
                messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

                // la classe de la commande /msgb
                messages.set("Prefix.msgb", "&c[&5StelyMsgPv&c]");
                messages.set("Messages.Formats.msgb.Envoyer", "&5✸✸ &c[&a%server%&c] &c[&e%player%&c] ➢&f&o %msg%");
                messages.set("Messages.Formats.msgb.Recu", "&5✸✸ &c[&a%server%&c] &c[&e%sender%&c]&a ➢&f&o %msg%");
                messages.set("Permissions.Perm.msgb", "StelyMsgPv.message");
                messages.set("Permissions.NoPerm.msgb", "%prefix% &cVous n'avez pas la permission : &e%permmsgb%");
                messages.set("Usages.Commandes.msgb", "&d/msgb <player> <message>");
                messages.set("Messages.NoJoueurTrouver.msgb", "%prefix% &cImpossible de trouver: &e%joueur% &c!");

                // staff
                messages.set("Staff.Prefix.Plugin", "&c[&5StelyMsgPv&4Staff&c]");
                messages.set("Staff.Permissions.Perm.msgbstaff", "StelyMsgPv.staff");
                messages.set("Staff.Permissions.NoPerm.msgbstaff",
                        "%prefixstaff% &cVous n'avez pas la permission : &e%permmsgbstaff%");
                messages.set("Staff.Usages.Commandes.msgbstaff", "&d/msgbstaff reload");
                messages.set("Staff.Message.Commandes.Reload", "%prefixstaff% &aLe plugin est bien reload !");

                // la classe dela commande /rb
                messages.set("Prefix.rb", "&c[&5StelyMsgPv&c]");
                messages.set("Messages.Formats.rb.Envoyer", "&5✸✸ &c[&a%server%&c] &c[&e%player%&c] ➢&f&o %msg%");
                messages.set("Messages.Formats.rb.Recu", "&5✸✸ &c[&a%server%&c] &c[&e%sender%&c]&a ➢&f&o %msg%");
                messages.set("Permissions.Perm.rb", "StelyMsgPv.message");
                messages.set("Permissions.NoPerm.rb", "%prefixrb% &cVous n'avez pas la permission : &e%permrb%");
                messages.set("Messages.NoJoueurTrouver.rb", "%prefixrb% &cTu n'a personne pour répondre !");

                ConfigurationProvider.getProvider(YamlConfiguration.class).save(messages, file);
            } else {
                messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            }

        } catch (Exception e) {

            ProxyServer.getInstance().getConsole().sendMessage(
                    new TextComponent(ChatColor.RED + "[StelyMsgPv] Impossible de crée le fichier Config.yml"));

        }
    }

    public void onDisable() {
        System.out.println("§c[StelyMsgPv] est désactivé !");

    }

    private void registerCommands() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandCMD("smsg"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CommandCMD("sm"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CMDStaff("msgbstaff"));
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new RetourCMD("sr"));
    }
}
