package me.greymouth.Lobby;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;

public class Game {
	
	private int id;
	
	private ArrayList<GamePlayer> players;
	private ArrayList<Location> spawns;
	
	private Location[] bounds;
	
	public static final int PAUSE = 0, RUNNING = 1, COMPLETE = 2, IDLE = 3;
	private int state;
	
	private final Lobby plugin;
	
	private int player_ready_count = 0;

	public Game(Lobby pl, int id) {
		this.id = id;
		
		this.players = new ArrayList<GamePlayer>();
		this.spawns = new ArrayList<Location>();
		
		this.bounds = new Location[2];
		
		this.state = IDLE;
		
		this.plugin = pl;
		
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			public void run() {
				if (player_ready_count == players.size() && state == PAUSE) {
					state = RUNNING;
				}
				
				if (state == RUNNING) {
					//Spawn stuff for 20 secs
					//pause and repeate 2 more times
					//complete game
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

						public void run() {
							for (GamePlayer i : players) {
								i.player.sendMessage("Game"+ChatColor.GREEN+getID()+ChatColor.WHITE+"Complete.");
								endGame();
							}
						}
						
					}, 200);
				}
			}
			
		}, 11, 11);
	}
	
	public void setFirstBounds(Location l) {
		bounds[0] = l;
	}
	
	public void setSecondBounds(Location l) {
		bounds[1] = l;
	}
	
	public void addSpawner(Location l) {
		spawns.add(l);
	}
	
	public void addPlayersFromQueue(ArrayList<PlayerProfile> players) {
		if (state == IDLE) {
			for (PlayerProfile p : players) {
				this.players.add(new GamePlayer(p.player, p.profile));
			}
			this.state = PAUSE;
		}
	}
	
	public void endGame() {
		this.players.clear();
		this.state = COMPLETE;
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

			public void run() {
				// Rebuild level
				id = IDLE;
			}
			
		}, 50);
	}
	
	public void playerReader() {
		
	}
	
	public int getGameState() {
		return this.state;
	}
	
	public int getID() {
		return this.id;
	}

}
