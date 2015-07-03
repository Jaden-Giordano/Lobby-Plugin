package me.greymouth.Lobby;

public class Profile {

	private String playerid;
	private int level;

	public Profile(String playerid, int level) {
		this.playerid = playerid;
		this.level = level;
	}

	public String getID() {
		return this.playerid;
	}

	public int getLevel() {
		return this.level;
	}

}
