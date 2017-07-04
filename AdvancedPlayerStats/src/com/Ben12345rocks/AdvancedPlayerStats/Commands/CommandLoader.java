package com.Ben12345rocks.AdvancedPlayerStats.Commands;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.Ben12345rocks.AdvancedCore.Objects.CommandHandler;
import com.Ben12345rocks.AdvancedCore.Util.Misc.StringUtils;
import com.Ben12345rocks.AdvancedPlayerStats.Main;
import com.Ben12345rocks.AdvancedPlayerStats.Users.User;
import com.Ben12345rocks.AdvancedPlayerStats.Users.UserManager;

import me.edge209.OnTime.OnTimeAPI;
import net.md_5.bungee.api.chat.TextComponent;

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
		plugin.commands
				.add(new CommandHandler(new String[] { "Reload" }, "AdvancedPlayerStats.Reload", "Reload plugin") {

					@Override
					public void execute(CommandSender sender, String[] args) {
						plugin.reload();

						sendMessage(sender, "&cAdvancedPlayerStats reloaded");

					}
				});
		plugin.commands.add(new CommandHandler(new String[] { "Today" }, "AdvancedPlayerStats.Today",
				"See whose been online today") {

			@Override
			public void execute(CommandSender sender, String[] args) {
				// sender.sendMessage(StringUtils.getInstance().colorize("&cGetting
				// today's online players"));
				ArrayList<String> msg = new ArrayList<String>();
				msg.add("&cName : Last Seen (" + plugin.getOnlineToday().size() + " Today)");
				for (Entry<User, Long> entry : plugin.getOnlineToday().entrySet()) {
					LocalDateTime lastOnline = LocalDateTime.ofInstant(Instant.ofEpochMilli(entry.getValue()),
							ZoneId.systemDefault());
					Duration dur = Duration.between(lastOnline, LocalDateTime.now());
					long hours = dur.toHours();
					long mins = dur.toMinutes() - (hours * 60);
					msg.add("&f" + entry.getKey().getPlayerName() + " : " + hours + " hours and " + mins
							+ " minutes ago");
				}

				sendMessage(sender, msg);

			}
		});

		plugin.commands.add(new CommandHandler(new String[] { "Yesterday" }, "AdvancedPlayerStats.Yesterday",
				"See whose been online yesterday") {

			@Override
			public void execute(CommandSender sender, String[] args) {
				// sender.sendMessage(StringUtils.getInstance().colorize("&cGetting
				// today's online players"));
				ArrayList<String> msg = new ArrayList<String>();
				msg.add("&cName : Last Seen (" + plugin.getOnlineToday().size() + " Yesterday)");
				for (Entry<User, Long> entry : plugin.getOnlineYesterday().entrySet()) {
					LocalDateTime lastOnline = LocalDateTime.ofInstant(Instant.ofEpochMilli(entry.getValue()),
							ZoneId.systemDefault());
					Duration dur = Duration.between(lastOnline, LocalDateTime.now());
					long hours = dur.toHours();
					long mins = dur.toMinutes() - (hours * 60);
					msg.add("&f" + entry.getKey().getPlayerName() + " : " + hours + " hours and " + mins
							+ " minutes ago");
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

		plugin.commands.add(
				new CommandHandler(new String[] { "Ontime" }, "AdvancedPlayerStats.Onime", "Check your ontime", false) {

					@Override
					public void execute(CommandSender sender, String[] args) {
						Player player = (Player) sender;
						User user = plugin.getUserManager().getAdvancedPlayerStatsUser(player);

						ArrayList<String> msg = new ArrayList<String>();
						msg.add("&cYour Ontime");
						msg.add(user.getOntimeString());
						sendMessage(sender, msg);

					}
				});

		plugin.commands.add(
				new CommandHandler(new String[] { "OntimeTop" }, "AdvancedPlayerStats.OnimeTop", "See Top Ontimes") {

					@Override
					public void execute(CommandSender sender, String[] args) {
						ArrayList<String> msg = new ArrayList<String>();
						msg.add("&cTop Ontime:");
						int i = 1;
						for (Entry<User, Long> entry : plugin.getOntimeTop().entrySet()) {
							if (i <= 10) {
								msg.add("&c" + i + ": " + entry.getKey().getOntimeString() + " &c&l"
										+ entry.getKey().getPlayerName());
								i++;
							}
						}
						sendMessage(sender, msg);
					}
				});

		plugin.commands.add(new CommandHandler(new String[] { "OntimeTop", "(Number)" }, "AdvancedPlayerStats.OnimeTop",
				"See Top Ontimes") {

			@Override
			public void execute(CommandSender sender, String[] args) {
				ArrayList<String> msg = new ArrayList<String>();
				int page = Integer.parseInt(args[1]);
				msg.add("&cTop Ontime (Page " + page + "):");
				int i = 1;
				for (Entry<User, Long> entry : plugin.getOntimeTop().entrySet()) {
					if (i <= page * 10 && i > (page - 1) * 10) {
						msg.add("&c" + i + ": " + entry.getKey().getOntimeString() + " &c&l"
								+ entry.getKey().getPlayerName());

					}
					i++;
				}
				sendMessage(sender, msg);
			}
		});

		plugin.commands.add(new CommandHandler(new String[] { "ConvertFrom", "Ontime" }, "AdvancedPlayerStats.Convert",
				"Convert from Ontime") {

			@Override
			public void execute(CommandSender sender, String[] args) {
				if (Bukkit.getPluginManager().getPlugin("OnTime") != null) {
					for (OfflinePlayer offPlayer : Bukkit.getOfflinePlayers()) {
						if (OnTimeAPI.playerHasOnTimeRecord(offPlayer.getName())) {
							User user = UserManager.getInstance().getAdvancedPlayerStatsUser(offPlayer.getName());
							user.setOntime(OnTimeAPI.getPlayerTimeData(offPlayer.getName(), OnTimeAPI.data.TOTALPLAY));
						}
					}
					sendMessage(sender, "&cFinished converting");
					plugin.setUpdate(true);
				} else {
					sendMessage(sender, "&cOnTime needs to be enabled");
				}
			}
		});

		plugin.commands.add(new CommandHandler(new String[] { "Ontime", "(player)" }, "AdvancedPlayerStats.Onime.Other",
				"Check other player ontime") {

			@Override
			public void execute(CommandSender sender, String[] args) {
				User user = plugin.getUserManager().getAdvancedPlayerStatsUser(args[1]);
				Duration dur = Duration.of(user.getOntime(), ChronoUnit.MILLIS);
				ArrayList<String> msg = new ArrayList<String>();
				msg.add("&c" + args[1] + " Ontime");
				msg.add("&c" + dur.toDays() + " Days " + (dur.toHours() - (dur.toDays() * 24)) + " Hours "
						+ (dur.toMinutes() - (dur.toHours() * 60)) + " Minutes");
				sendMessage(sender, msg);

			}
		});

		plugin.commands
				.add(new CommandHandler(new String[] { "Perms" }, "AdvancedPlayerStats.Perms", "See all permissions") {

					@Override
					public void execute(CommandSender sender, String[] args) {
						ArrayList<String> msg = new ArrayList<String>();

						for (CommandHandler handle : plugin.commands) {
							msg.add(handle.getHelpLineCommand("/aps") + " : " + handle.getPerm());
						}

						for (Permission perm : plugin.getDescription().getPermissions()) {
							msg.add(perm.getName());
						}

						Collections.sort(msg, String.CASE_INSENSITIVE_ORDER);

						sendMessage(sender, msg);
					}

				});

		plugin.commands
				.add(new CommandHandler(new String[] { "Help" }, "AdvancedPlayerStats.Help", "See all permissions") {

					@Override
					public void execute(CommandSender sender, String[] args) {
						ArrayList<TextComponent> texts = new ArrayList<TextComponent>();
						HashMap<String, TextComponent> unsorted = new HashMap<String, TextComponent>();
						texts.add(StringUtils.getInstance().stringToComp("&bAdvancedPlayerStats Help"));

						boolean requirePerms = true;

						for (CommandHandler cmdHandle : plugin.commands) {
							if (sender.hasPermission(cmdHandle.getPerm()) && requirePerms) {
								unsorted.put(cmdHandle.getHelpLineCommand("/aps"), cmdHandle.getHelpLine("/aps"));
							} else if (!requirePerms) {
								unsorted.put(cmdHandle.getHelpLineCommand("/aps"), cmdHandle.getHelpLine("/aps"));
							}
						}

						ArrayList<String> unsortedList = new ArrayList<String>();
						unsortedList.addAll(unsorted.keySet());
						Collections.sort(unsortedList, String.CASE_INSENSITIVE_ORDER);
						for (String cmd : unsortedList) {
							texts.add(unsorted.get(cmd));
						}
						sendMessageJson(sender, texts);
					}

				});

		plugin.commands.add(new CommandHandler(new String[] { "AddOntime", "(Player)", "(Number)" },
				"AdvancedPlayerStats.AddOntime", "Add to players ontime") {

			@Override
			public void execute(CommandSender sender, String[] args) {
				User user = UserManager.getInstance().getAdvancedPlayerStatsUser(args[1]);
				user.updateOntime();
				user.setLoginTime(System.currentTimeMillis() - Integer.parseInt(args[2]) * 1000 * 60);
				user.updateOntime();
				sendMessage(sender, "Set ontime to " + user.getOntime() / 1000 / 60 + " for " + args[1]);
			}
		});
		plugin.commands.add(new CommandHandler(new String[] { "SetOntime", "(Player)", "(Number)" },
				"AdvancedPlayerStats.SetOntime", "Set players ontime") {

			@Override
			public void execute(CommandSender sender, String[] args) {
				User user = UserManager.getInstance().getAdvancedPlayerStatsUser(args[1]);
				user.setOntime(Integer.parseInt(args[2]) * 1000 * 60);
				sendMessage(sender, "Set ontime to " + user.getOntime() / 1000 / 60 + " for " + args[1]);
			}
		});

		plugin.commands.addAll(com.Ben12345rocks.AdvancedCore.Commands.CommandLoader.getInstance()
				.getBasicAdminCommands("AdvancedPlayerStats"));
		plugin.commands.addAll(com.Ben12345rocks.AdvancedCore.Commands.CommandLoader.getInstance()
				.getBasicCommands("AdvancedPlayerStats"));

		loadTabComplete();

	}

	public void loadTabComplete() {
		// for (CommandHandler cmd : plugin.commands) {
		// cmd.reloadTabComplete();
		// }
	}
}
