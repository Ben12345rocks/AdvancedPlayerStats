package com.Ben12345rocks.AdvancedPlayerStats.Commands.Executor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.Ben12345rocks.AdvancedCore.Objects.CommandHandler;
import com.Ben12345rocks.AdvancedPlayerStats.Main;

public class CommandAdvancedPlayerStats implements CommandExecutor {

	/** The instance. */
	private static CommandAdvancedPlayerStats instance = new CommandAdvancedPlayerStats();

	static Main plugin = Main.plugin;

	public static CommandAdvancedPlayerStats getInstance() {
		return instance;
	}

	public CommandAdvancedPlayerStats() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.
	 * CommandSender , org.bukkit.command.Command, java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		for (CommandHandler commandHandler : plugin.commands) {
			if (commandHandler.runCommand(sender, args)) {
				return true;
			}
		}

		// invalid command
		sender.sendMessage(ChatColor.RED + "No valid arguments, see /advancedplayerstats help!");

		return true;
	}

}
