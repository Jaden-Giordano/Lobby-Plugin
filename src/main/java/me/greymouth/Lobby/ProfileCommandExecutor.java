package me.greymouth.Lobby;

import me.greymouth.Lobby.tools.JSONTools;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ProfileCommandExecutor implements CommandExecutor {
	
	@SuppressWarnings("unused")
	private final Lobby plugin;
	
	public ProfileCommandExecutor(Lobby plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("profiles")) {
			if (sender.hasPermission("profiles")) {
				if (args.length < 1) {
					sender.sendMessage("Too few arguements!");
					return false;
				}
				
				if (args[0].equalsIgnoreCase("save")) {
					return save(args);
				}
			}
			else {
				sender.sendMessage("You don't have permission to do this.");
				return true;
			}
		}
		
		return false;
	}
	
	public boolean save(String[] args) {
		JSONTools.writeProfilesToJSON(Lobby.getProfiles());
		return true;
	}

}
