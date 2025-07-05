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

public class RetourCMD extends Command implements TabExecutor {

    public RetourCMD(String name) {
        super(name);
    }

    String prefix = "§c[§5StelyMsgPv§c] ";

    @SuppressWarnings("deprecation")
    public void execute(CommandSender sender, String[] args) {

        String prefixrb = ChatColor.translateAlternateColorCodes('&',
                App.getinstance().messages.getString("Prefix.rb"));
        String permrb = ChatColor.translateAlternateColorCodes('&',
                App.getinstance().messages.getString("Permissions.Perm.rb"));
        String rbnoperm = ChatColor.translateAlternateColorCodes('&', App.getinstance().messages
                .getString("Permissions.NoPerm.rb").replace("%permrb%", permrb).replace("%prefixrb%", prefixrb));
        String Nojoueurrb = ChatColor.translateAlternateColorCodes('&',
                App.getinstance().messages.getString("Messages.NoJoueurTrouver.rb").replace("%prefixrb%", prefixrb));

        final var serverInfo = ((ProxiedPlayer) sender).getServer().getInfo();
        String s = serverInfo.getName();
        String serverName = serverInfo.getMotd();

        if (sender.hasPermission(permrb)) {
            if (App.server.contains(s)) {
                if (!App.lastMessage.containsKey(sender.getName())) {
                    sender.sendMessage(new TextComponent(Nojoueurrb));
                    return;
                }
                ProxiedPlayer target = ProxyServer.getInstance()
                        .getPlayer((String) App.lastMessage.get(sender.getName()));
                if (target == null) {
                    sender.sendMessage(new TextComponent(prefixrb + ChatColor.RED
                            + (String) App.lastMessage.get(sender.getName()) + " est hors ligne !"));
                    return;
                }
                String message = "";
                for (int i = 0; i < args.length; i++) {
                    message = message + args[i] + " ";
                }
                message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message.trim()));

                String rbenvoyer = ChatColor.translateAlternateColorCodes('&',
                        App.getinstance().messages.getString("Messages.Formats.rb.Envoyer").replace("%server%", serverName)
                                .replace("%player%", target.getName()).replace("%msg%", message)
                                .replace("%sender%", sender.getName()).replace("%prefixrb%", prefixrb));
                String rbrecu = ChatColor.translateAlternateColorCodes('&',
                        App.getinstance().messages.getString("Messages.Formats.rb.Recu").replace("%server%", serverName)
                                .replace("%sender%", sender.getName()).replace("%msg%", message)
                                .replace("%player%", target.getName()).replace("%prefixrb%", prefixrb));

                target.sendMessage(new TextComponent(rbrecu));
                sender.sendMessage(new TextComponent(rbenvoyer));
                System.out.println(prefixrb + "§e" + sender.getName() + " §5✸✸" + " §c[§a" + s + "§c] §c[§e"
                        + target.getName() + "§c]" + " ➢§f§o " + message);
                if (App.lastMessage.containsKey(sender.getName())) {
                    App.lastMessage.remove(sender.getName());
                }
                App.lastMessage.put(sender.getName(), target.getName());
                if (App.lastMessage.containsKey(target.getName())) {
                    App.lastMessage.remove(target.getName());
                }
                App.lastMessage.put(target.getName(), sender.getName());
            }
        } else {
            sender.sendMessage(rbnoperm);
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
