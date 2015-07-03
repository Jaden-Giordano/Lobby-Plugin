package me.greymouth.Lobby;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameQueue {

	private ArrayList<PlayerProfile> players;
	private int max_players;
	private String name;

	private Runnable func;

	private final Lobby plugin;
	
	private int time_till_start = 4;
	private int countdown_id;

	public GameQueue(Lobby plugin, int max, String name, Runnable func) {
		this.players = new ArrayList<PlayerProfile>();
		this.max_players = max;
		this.name = name;

		this.plugin = plugin;

		this.func = func;
	}

	public boolean joinQueue(Player p) {
		if (players.size() == this.max_players) {
			p.sendMessage(this.name+" is full");
			return false;
		}
		else {
			players.add(new PlayerProfile(p, plugin.findProfile(p)));
			p.sendMessage("You joined " + this.name);

			if (players.size() == max_players-2) {
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this.func, 100);
				countdown_id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					public void run() {
						for (PlayerProfile i : players) {
							i.player.sendMessage(ChatColor.GREEN+"["+name+"] "+ChatColor.WHITE+"Game Starting in "+time_till_start+"...");
							time_till_start--;
							if (time_till_start == 0) {
								plugin.getServer().getScheduler().cancelTask(countdown_id);
							}
						}
					}
				}, 20, 20);
				
				for (PlayerProfile i : players) {
					i.player.sendMessage(ChatColor.GREEN+"["+this.name+"] "+ChatColor.WHITE+"Game Starting in 5 seconds...");
				}
			}

			return true;
		}
	}

	public boolean leaveQueue(Player p) {
		if (players.remove(findPlayerProfile(p))) {
			p.sendMessage("You left " + this.name);
			return true;
		}
		p.sendMessage("You are not currently in a Queue");
		return false;
	}

	public void setSize(int size) {
		this.max_players = size;
	}

	public ArrayList<PlayerProfile> getPlayers() {
		return this.players;
	}
	
	public PlayerProfile findPlayerProfile(Player p) {
		for (PlayerProfile i : players) {
			if (p == i.player) {
				return i;
			}
		}
		return null; // Not found
	}

}
