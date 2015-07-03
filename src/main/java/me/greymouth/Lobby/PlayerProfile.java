package me.greymouth.Lobby;

import org.bukkit.entity.Player;

public class PlayerProfile {
	
	public Player player;
	public Profile profile;

	public PlayerProfile(Player player, Profile profile) {
		this.player = player;
		this.profile = profile;
	}

}
