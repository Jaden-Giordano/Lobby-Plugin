package me.greymouth.Lobby;

import java.util.ArrayList;
import java.util.Collection;

import me.greymouth.Lobby.tools.JSONTools;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lobby extends JavaPlugin {

	public static String pluginFolder;
	public static ArrayList<Profile> profiles;
	
	public ArrayList<Game> games;

	public static String getPluginFolder() {
		return pluginFolder;
	}

	public static ArrayList<Profile> getProfiles() {
		return profiles;
	}

	public static boolean contains(Player p) {
		for (Profile i : profiles) {
			if (i.getID().equals(p.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onEnable() {
		pluginFolder = getDataFolder().getAbsolutePath();

		getServer().getPluginManager().registerEvents(new Events(), this);
		
		games = new ArrayList<Game>();

		this.getCommand("queue").setExecutor(new QueueCommandExecutor(this));
		this.getCommand("profiles").setExecutor(new ProfileCommandExecutor(this));

		profiles = JSONTools.getProfilesFromJSON();
		Collection<? extends Player> p = this.getServer().getOnlinePlayers();

		for (Player i : p) {
			if (!contains(i)) {
				profiles.add(new Profile(i.getUniqueId().toString(), 0));
			}
		}

		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			public void run() {
				World w = getServer().getWorld("Map");
				int index = 0;
				for (Profile i : profiles) {
					for (Player p : getServer().getOnlinePlayers()) {
						if (index == 0)
							w = p.getWorld();
						if (i.getID().equals(p.getUniqueId().toString())) {
							updateOnlinePlayer(i, p);
						}
						index++;
					}
				}
				w.setTime(17000);
			}

		}, 11, 11);

		getLogger().info("Tut Plugin Enabled...");
	}

	public void updateOnlinePlayer(Profile profile, Player player) {
		player.setLevel(profile.getLevel());
	}

	@Override
	public void onDisable() {
		JSONTools.writeProfilesToJSON(profiles);

		getLogger().info("Tut Plugin Disabled...");
	}
	
	public Profile findProfile(Player p) {
		for (Profile i : profiles) {
			if (i.getID().equals(p.getUniqueId().toString())) {
				return i;
			}
		}
		return null;
	}

}
