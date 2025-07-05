package fr.stelycube.stelyprivatemessage;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Leave implements Listener
{
	@EventHandler
	public void onQuit(PlayerDisconnectEvent e)
	{
		String name = e.getPlayer().getName();
		if (App.lastMessage.containsKey(name)) {
			App.lastMessage.remove(name);
		}
	}
}
