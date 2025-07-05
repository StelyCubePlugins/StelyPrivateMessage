package fr.stelycube.stelyprivatemessage;

import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LeaveListener implements Listener {

    private final Map<UUID, UUID> lastReceivers;

    public LeaveListener(Map<UUID, UUID> lastReceivers) {
        this.lastReceivers = lastReceivers;
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        lastReceivers.remove(e.getPlayer().getUniqueId());
    }

}
