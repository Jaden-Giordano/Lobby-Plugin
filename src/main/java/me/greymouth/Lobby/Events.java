package me.greymouth.Lobby;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class Events implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onLogin(PlayerLoginEvent e) {
		if (!Lobby.contains(e.getPlayer())) {
			Lobby.getProfiles().add(new Profile(e.getPlayer().getUniqueId().toString(), 0));
		}
	}
}
