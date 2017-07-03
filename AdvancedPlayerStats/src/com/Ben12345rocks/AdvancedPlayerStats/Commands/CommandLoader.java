package com.Ben12345rocks.AdvancedPlayerStats.Commands;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.command.CommandSender;

import com.Ben12345rocks.AdvancedCore.Objects.CommandHandler;
import com.Ben12345rocks.AdvancedPlayerStats.Main;
import com.Ben12345rocks.AdvancedPlayerStats.Users.User;
import com.Ben12345rocks.AdvancedPlayerStats.Users.UserManager;

// TODO: Auto-generated Javadoc
/**
 * The Class CommandLoader.
 */
public class CommandLoader {

	/** The instance. */
	static CommandLoader instance = new CommandLoader();

	/** The plugin. */
	static Main plugin = Main.plugin;

	/**
	 * Gets the single instance of CommandLoader.
	 *
	 * @return single instance of CommandLoader
	 */
	public static CommandLoader getInstance() {
		return instance;
	}

	/**
	 * Instantiates a new command loader.
	 */
	private CommandLoader() {
	}

	/**
	 * Instantiates a new command loader.
	 *
	 * @param plugin
	 *            the plugin
	 */
	public CommandLoader(Main plugin) {
		CommandLoader.plugin = plugin;
	}

	public void loadCommands() {
		plugin.commands = new ArrayList<CommandHandler>();
		plugin.commands.add(new CommandHandler(new String[] { "Today" }, "AdvancedPlayerStats.Today",
				"See whose been online today") {

			@Override
			public void execute(CommandSender sender, String[] args) {
				// sender.sendMessage(StringUtils.getInstance().colorize("&cGetting
				// today's online players"));
				ArrayList<String> msg = new ArrayList<String>();
				msg.add("&cName : Last Online");
				for (Entry<User, Long> entry : plugin.getOnlineToday().entrySet()) {
					LocalDateTime lastOnline = LocalDateTime.ofInstant(Instant.ofEpochMilli(entry.getValue()),
							ZoneId.systemDefault());
					msg.add("&f" + entry.getKey().getPlayerName() + " : "
							+ lastOnline.format(new DateTimeFormatterBuilder().appendLiteral("HH:mm").toFormatter()));
				}

				sendMessage(sender, msg);

			}
		});

		plugin.commands.add(new CommandHandler(new String[] { "Alts", "(player)" }, "AdvancedPlayerStats.Alts",
				"Check for player alts") {

			@Override
			public void execute(CommandSender sender, String[] args) {
				sendMessage(sender, "&cLooking for possible alts...");
				ArrayList<User> matched = plugin.getUserManager()
						.getMatchedIps(UserManager.getInstance().getAdvancedPlayerStatsUser(args[1]));
				ArrayList<String> msg = new ArrayList<String>();
				if (matched.size() > 0) {
					msg.add("&cPossible alt accounts (According to matching ips):");
					for (User user : matched) {
						msg.add("&c" + user.getPlayerName());
					}
				} else {
					msg.add("&cNo alt accounts found");
				}

				sendMessage(sender, msg);
			}
		});

		loadTabComplete();

	}

	public void loadTabComplete() {

	}
}
