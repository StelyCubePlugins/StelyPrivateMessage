package fr.stelycube.stelyprivatemessage;

import java.util.HashSet;
import java.util.Set;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.config.Configuration;

public class CommandCMD extends Command implements TabExecutor {
    public CommandCMD(String name) {
        super(name);
    }

    @SuppressWarnings({ "deprecation" })
    public void execute(CommandSender sender, String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&',
                App.getinstance().messages.getString("Prefix.msgb"));
        String permmsgb = ChatColor.translateAlternateColorCodes('&',
                App.getinstance().messages.getString("Permissions.Perm.msgb"));
        String msgbnoperm = ChatColor.translateAlternateColorCodes('&', App.getinstance().messages
                .getString("Permissions.NoPerm.msgb").replace("%permmsgb%", permmsgb).replace("%prefix%", prefix));
        // String usagemsgb = ChatColor.translateAlternateColorCodes('&',
        // App.getinstance().messages.getString("Usages.Commandes.msgb").replace("%prefix%",
        // prefix));
        String permmsgbOP = "stelymsgb.op";
        // String permmsgbstaff = ChatColor.translateAlternateColorCodes('&',
        // App.getinstance().messages.getString("Staff.Permissions.Perm.msgbstaff"));

        ConfigPV config = new ConfigPV();
        Configuration configa = config.getConfig("PlayerDesactive");

        String s = ((ProxiedPlayer) sender).getServer().getInfo().getName();
        String smotd = ((ProxiedPlayer) sender).getServer().getInfo().getMotd();

        if (args.length < 2) {

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
             * sender.sendMessage(new TextComponent(usagemsgb));
             * sender.sendMessage("");
             * sender.
             * sendMessage("§aCrée par les fondateurs du serveur §e§lStelyCube§a : §bplay.stelycube.fr"
             * );
             * sender.sendMessage("§6-----------------------------------------------------")
             * ;
             */
            return;
        }

        if (sender.hasPermission(permmsgb)) {
            ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
            if (p == null) {
                String Nojoueur = ChatColor.translateAlternateColorCodes('&',
                        App.getinstance().messages.getString("Messages.NoJoueurTrouver.msgb")
                                .replace("%joueur%", args[0]).replace("%prefix%", prefix));

                sender.sendMessage(new TextComponent(Nojoueur));
                return;
            }
            if (App.server.contains(s) && App.server.contains(p.getServer().getInfo().getName())) {

                StringBuilder msgBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    msgBuilder.append(args[i]).append(" ");
                }
                String msg = msgBuilder.toString().trim();

                String msgbenvoyer = ChatColor.translateAlternateColorCodes('&',
                        App.getinstance().messages.getString("Messages.Formats.msgb.Envoyer").replace("%server%", smotd)
                                .replace("%player%", p.getName()).replace("%msg%", msg)
                                .replace("%sender%", sender.getName()).replace("%prefix%", prefix));
                String msgbrecu = ChatColor.translateAlternateColorCodes('&',
                        App.getinstance().messages.getString("Messages.Formats.msgb.Recu").replace("%server%", smotd)
                                .replace("%sender%", sender.getName()).replace("%msg%", msg)
                                .replace("%player%", p.getName()).replace("%prefix%", prefix));

                if (!sender.getName().equals(p.getName())) {
                    p.sendMessage(new TextComponent(msgbrecu));
                    for (ProxiedPlayer pls : ProxyServer.getInstance().getPlayers()) {
                        if (configa.get(pls.getName()) != null && configa.getBoolean(pls.getName()) == true) {
                            if (!sender.getName().equals(pls.getName()) && !pls.getName().equals(p.getName())) {
                                if (!sender.hasPermission(permmsgbOP)
                                        || !p.hasPermission(permmsgbOP) && pls.hasPermission(permmsgbOP)) {
                                    pls.sendMessage("§7[spy] " + sender.getName() + " => " + p.getName() + " : " + msg);
                                }
                            }
                        }
                    }
                }
                sender.sendMessage(new TextComponent(msgbenvoyer));
                System.out.println(prefix + "§e" + sender.getName() + " §5✸✸" + " §c[§a" + smotd + "§c] §c[§e"
                        + p.getName() + "§c]" + " ➢§f§o " + msg);
                if (App.lastMessage.containsKey(sender.getName())) {
                    App.lastMessage.remove(sender.getName());
                }
                App.lastMessage.put(sender.getName(), p.getName());
                if (App.lastMessage.containsKey(p.getName())) {
                    App.lastMessage.remove(p.getName());
                }
                App.lastMessage.put(p.getName(), sender.getName());
            } else {
                if (!App.server.contains(p.getServer().getInfo().getName())) {
                    sender.sendMessage(
                            prefix + p.getName() + " n'est pas connecté sur un survie pour recevoir le message !");
                }
            }
        } else {
            sender.sendMessage(msgbnoperm);
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Set<String> match = new HashSet<String>();
        if (args.length == 1) {
            String search = args[0].toLowerCase();
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (App.server.contains(player.getServer().getInfo().getName())) {
                    if (player.getName().toLowerCase().startsWith(search)) {
                        match.add(player.getName());
                    }
                }
            }
        }
        return match;
    }

}
