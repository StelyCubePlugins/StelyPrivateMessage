package fr.stelycube.stelyprivatemessage.message;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SpyManager {

    private final String spyPermission;
    private final Set<UUID> disabledPlayers;
    private final Map<MessagePath, List<String>> spyServers;

    public SpyManager(@NotNull String spyPermission, @NotNull Map<MessagePath, List<String>> spyServers) {
        this.spyPermission = spyPermission;
        disabledPlayers = new HashSet<>();
        this.spyServers = spyServers;
    }

    public List<ProxiedPlayer> collectSpyPlayers(@NotNull MessagePath messagePath) {
        final var spyingServers = spyServers.get(messagePath);
        if (spyServers == null) {
            return Collections.emptyList();
        }
        final var spyPlayers = new LinkedList<ProxiedPlayer>();
        final var proxy = ProxyServer.getInstance();
        for (var serverName : spyingServers) {
            final var serverInfo = proxy.getServerInfo(serverName);
            if (serverInfo == null) {
                continue;
            }
            for (final var player : serverInfo.getPlayers()) {
                if (disabledPlayers.contains(player.getUniqueId())) {
                    continue;
                }
                if (!player.hasPermission(spyPermission)) {
                    continue;
                }
                spyPlayers.add(player);
            }
        }
        return spyPlayers;
    }

}
