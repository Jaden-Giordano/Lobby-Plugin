package me.greymouth.Lobby;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCommandExecutor implements CommandExecutor {
	
	Lobby plugin;

	public GameCommandExecutor(Lobby plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("game")) {
			if (sender.hasPermission("game.*")) {
				
			}
		}
		
		return false;
	}
	
	

}
