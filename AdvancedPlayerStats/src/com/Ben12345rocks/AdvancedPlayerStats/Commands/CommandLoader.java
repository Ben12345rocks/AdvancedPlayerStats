package com.Ben12345rocks.AdvancedPlayerStats.Commands;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import com.Ben12345rocks.AdvancedCore.Objects.CommandHandler;
import com.Ben12345rocks.AdvancedCore.Objects.UUID;
import com.Ben12345rocks.AdvancedCore.Util.Misc.ArrayUtils;
import com.Ben12345rocks.AdvancedCore.Util.Misc.StringUtils;
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
				sender.sendMessage(StringUtils.getInstance().colorize("&cGetting today's online players"));
				ArrayList<User> onlineToday = new ArrayList<User>();
				for (String uuid : UserManager.getInstance().getAllUUIDs()) {
					User user = plugin.getUserManager().getAdvancedPlayerStatsUser(new UUID(uuid));
					if (user.wasOnlineToday()) {
						onlineToday.add(user);
					}
				}
				ArrayList<String> msg = new ArrayList<String>();
				msg.add("&cName : Last Online");
				for (User user : onlineToday) {
					LocalDateTime lastOnline = LocalDateTime.ofInstant(Instant.ofEpochMilli(user.getLastOnline()),
							ZoneId.systemDefault());
					msg.add("&f" + user.getPlayerName() + " : "
							+ lastOnline.format(new DateTimeFormatterBuilder().appendLiteral("HH:mm").toFormatter()));
				}

				sender.sendMessage(ArrayUtils.getInstance().convert(ArrayUtils.getInstance().colorize(msg)));

			}
		});

		loadTabComplete();

	}

	public void loadTabComplete() {

	}
}
