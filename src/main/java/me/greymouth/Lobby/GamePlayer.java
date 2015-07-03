package me.greymouth.Lobby;

import org.bukkit.entity.Player;

public class GamePlayer {
	
	public Player player;
	public Profile profile;
	public boolean alive;

	public GamePlayer(Player player, Profile profile) {
		this.player = player;
		this.profile = profile;
		this.alive = true;
	}

}
