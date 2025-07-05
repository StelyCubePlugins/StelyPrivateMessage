package fr.stelycube.stelyprivatemessage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

public class CMDStaff extends Command {
    public CMDStaff(String name) {
        super(name);
    }

    @SuppressWarnings("deprecation")
    public void execute(CommandSender sender, String[] args) {

        String prefixstaff = ChatColor.translateAlternateColorCodes('&',
                App.getinstance().messages.getString("Staff.Prefix.Plugin"));
        String permmsgbstaff = ChatColor.translateAlternateColorCodes('&',
                App.getinstance().messages.getString("Staff.Permissions.Perm.msgbstaff"));
        String msgbnopermstaff = ChatColor.translateAlternateColorCodes('&',
                App.getinstance().messages.getString("Staff.Permissions.NoPerm.msgbstaff")
                        .replace("%permmsgbstaff%", permmsgbstaff).replace("%prefixstaff%", prefixstaff));
        // String usagemsgbstaff = ChatColor.translateAlternateColorCodes('&',
        // App.getinstance().messages.getString("Staff.Usages.Commandes.msgbstaff").replace("%prefixstaff%",
        // prefixstaff));
        String msgreload = ChatColor.translateAlternateColorCodes('&', App.getinstance().messages
                .getString("Staff.Message.Commandes.Reload").replace("%prefixstaff%", prefixstaff));

        ConfigPV config = new ConfigPV();
        Configuration configa = config.getConfig("PlayerDesactive");

        if (args.length == 0) {

            /*
             * sender.sendMessage(
             * "§6------------------------§cI§6-§cN§6-§cF§6-§cO§6----------------------");
             * sender.sendMessage( ChatColor.RED + "Développeur ➜" + " " + ChatColor.GREEN +
             * " " + App.getinstance().getDescription().getAuthor());
             * sender.sendMessage("");
             * sender.sendMessage( ChatColor.RED + "Version ➜" + ChatColor.GREEN + " " +
             * App.getinstance().getDescription().getVersion());
             * sender.sendMessage("");
             * sender.sendMessage( ChatColor.RED + "Description ➜" + " " + ChatColor.GREEN +
             * "Plugin de message privé du BungeeCord");
             * sender.sendMessage("");
             * sender.sendMessage(new TextComponent(usagemsgbstaff));
             * sender.sendMessage("");
             * sender.
             * sendMessage("§aCrée par les fondateurs du serveur §e§lStelyCube§a : §bplay.stelycube.fr"
             * );
             * sender.sendMessage("§6-----------------------------------------------------")
             * ;
             */
            return;
        }
        if (sender.hasPermission(permmsgbstaff)) {

            if (args[0].equalsIgnoreCase("reload".trim())) {

                ProxyServer.getInstance().getPluginManager().getPlugin("StelyMsgPvB").onDisable();
                ProxyServer.getInstance().getPluginManager().getPlugin("StelyMsgPvB").onLoad();
                ProxyServer.getInstance().getPluginManager().getPlugin("StelyMsgPvB").onEnable();
                sender.sendMessage(msgreload);
            }

            if (args[0].equalsIgnoreCase("true".trim())) {

                if (configa.get(sender.getName()) == null || configa.getBoolean(sender.getName()) == false) {
                    configa.set(sender.getName(), true);
                    config.saveConfig(configa, "PlayerDesactive");
                }
                sender.sendMessage("§7[§5StelyMsgPv§7] §aLes messages privés sont activé !");
            }

            if (args[0].equalsIgnoreCase("false".trim())) {

                if (configa.get(sender.getName()) == null || configa.getBoolean(sender.getName()) == true) {
                    configa.set(sender.getName(), false);
                    config.saveConfig(configa, "PlayerDesactive");
                }
                sender.sendMessage("§7[§5StelyMsgPv§7] §aLes messages privés sont désactivé !");
            }
        } else {

            sender.sendMessage(msgbnopermstaff);
        }

    }
}
