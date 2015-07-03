package me.greymouth.Lobby;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QueueCommandExecutor implements CommandExecutor {

	@SuppressWarnings("unused")
	private final Lobby plugin;
	private GameQueue queue;

	public QueueCommandExecutor(Lobby plugin) {
		this.plugin = plugin;
		this.queue = new GameQueue(plugin, 3, "Test", new Runnable() {
			public void run() {
				for (PlayerProfile i : queue.getPlayers()) {
					i.player.teleport(queue.getPlayers().get(0).player);
				}
			}
		});
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("queue")) {
			if (sender.hasPermission("queue.*")) {
				if (args.length > 2) {
					sender.sendMessage("Too many Arguments!");
					return false;
				}
				else if (args.length < 1) {
					sender.sendMessage("Too few Arguments!");
					return false;
				}

				if (args[0].equalsIgnoreCase("join")) {
					return join(sender);
				}
				else if (args[0].equalsIgnoreCase("leave")) {
					return leave(sender);
				}
				else if (args[0].equalsIgnoreCase("setsize")) {
					if (sender.hasPermission("queue.setsize"))
						return setSize(sender, args);
					else
						sender.sendMessage("You don't have permission to do this.");
				}
				return true;
			}
			else {
				sender.sendMessage("You don't have permission to do this.");
			}
		}
		return false;
	}

	public boolean join(CommandSender sender) {
		if (sender instanceof Player) {
			if (!(queue.joinQueue((Player) sender)))
				sender.sendMessage("Queue is full.");
		}
		else {
			sender.sendMessage("Must be a player!");
		}
		return true;
	}

	public boolean leave(CommandSender sender) {
		if (sender instanceof Player) {
			queue.leaveQueue((Player) sender);
		}
		else {
			sender.sendMessage("Must be a player!");
		}
		return true;
	}

	public boolean setSize(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("Must specify size.");
			return false;
		} else {
			this.queue.setSize(Integer.parseInt(args[1]));
		}
		return true;
	}

}
