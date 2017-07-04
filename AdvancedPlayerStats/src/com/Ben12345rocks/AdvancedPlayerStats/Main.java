package com.Ben12345rocks.AdvancedPlayerStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Ben12345rocks.AdvancedCore.AdvancedCoreHook;
import com.Ben12345rocks.AdvancedCore.Objects.CommandHandler;
import com.Ben12345rocks.AdvancedCore.Objects.UUID;
import com.Ben12345rocks.AdvancedCore.Objects.UserStorage;
import com.Ben12345rocks.AdvancedCore.Thread.Thread;
import com.Ben12345rocks.AdvancedCore.Util.Metrics.BStatsMetrics;
import com.Ben12345rocks.AdvancedCore.Util.Metrics.MCStatsMetrics;
import com.Ben12345rocks.AdvancedCore.Util.Misc.StringUtils;
import com.Ben12345rocks.AdvancedCore.mysql.MySQL;
import com.Ben12345rocks.AdvancedPlayerStats.Commands.CommandLoader;
import com.Ben12345rocks.AdvancedPlayerStats.Commands.Executor.CommandAdvancedPlayerStats;
import com.Ben12345rocks.AdvancedPlayerStats.Commands.TabComplete.AdvancedPlayerStatsTabCompleter;
import com.Ben12345rocks.AdvancedPlayerStats.Configs.Config;
import com.Ben12345rocks.AdvancedPlayerStats.Listeners.PlayerListeners;
import com.Ben12345rocks.AdvancedPlayerStats.Listeners.PlayerOntimeListener;
import com.Ben12345rocks.AdvancedPlayerStats.Rewards.OntimeAchivement;
import com.Ben12345rocks.AdvancedPlayerStats.Rewards.OntimeReward;
import com.Ben12345rocks.AdvancedPlayerStats.Users.User;
import com.Ben12345rocks.AdvancedPlayerStats.Users.UserManager;

/**
 * The Class Main.
 */
public class Main extends JavaPlugin {

	public static Main plugin;
	public ArrayList<CommandHandler> commands;
	private HashMap<User, Long> onlineToday;
	private boolean update = false;
	private Config pluginConfig;
	private ArrayList<OntimeReward> ontimeRewards;
	private ArrayList<OntimeReward> ontimeRewardsEach;
	private HashMap<User, Long> ontimeTop;

	/**
	 * @return the ontimeRewards
	 */
	public ArrayList<OntimeReward> getOntimeRewards() {
		return ontimeRewards;
	}

	/**
	 * Debug.
	 *
	 * @param message
	 *            the message
	 */
	public void debug(String message) {
		AdvancedCoreHook.getInstance().debug(plugin, message);
	}

	/**
	 * Metrics.
	 */
	@SuppressWarnings("unused")
	private void metrics() {
		try {
			MCStatsMetrics metrics = new MCStatsMetrics(this);
			metrics.start();
			plugin.debug("Loaded Metrics");
		} catch (IOException e) {
			plugin.getLogger().info("Can't submit metrics stats");
		}

		BStatsMetrics metrics = new BStatsMetrics(this);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		for (String uuid : UserManager.getInstance().getAllUUIDs()) {
			User user = UserManager.getInstance().getAdvancedPlayerStatsUser(new UUID(uuid));
			user.updateOntime();
		}
		HandlerList.unregisterAll(plugin);
		plugin = null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		plugin = this;

		onlineToday = new LinkedHashMap<User, Long>();
		ontimeTop = new LinkedHashMap<User, Long>();

		loadFiles();
		updateAdvancedCoreHook();
		AdvancedCoreHook.getInstance().loadHook(plugin);
		registerEvents();
		registerCommands();
		metrics();

		loadOntimeRewards();

		update = true;
		update();
		AdvancedCoreHook.getInstance().getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				if (update) {
					update();
					update = false;
				}
			}
		}, 1000 * 60 * 3, 1000 * 60 * 3);

		AdvancedCoreHook.getInstance().getTimer().schedule(new TimerTask() {

			@Override
			public void run() {
				for (String uuid : getUserManager().getAllUUIDs()) {
					User user = getUserManager().getAdvancedPlayerStatsUser(new UUID(uuid));
					user.updateOntime();
				}
				plugin.debug("Ontime updated");
			}
		}, 1000 * 60 * 15, 1000 * 60 * 15);

		plugin.getLogger().info("Enabled AdvancedPlayerStats " + plugin.getDescription().getVersion());
	}

	private void loadOntimeRewards() {
		try {
			ontimeRewards = new ArrayList<OntimeReward>();
			for (String str : Config.getInstance().getOntimeRewards()) {
				if (StringUtils.getInstance().isInt(str)) {
					int reward = Integer.parseInt(str);
					if (Config.getInstance().getOntimeRewardEnabled(reward)) {
						ontimeRewards.add(new OntimeReward(reward,
								OntimeAchivement.getTimeType(Config.getInstance().getOntimeRewardTimeType(reward)),
								Config.getInstance().getOntimeRewardPath(reward)));
					}
				}
			}

			ontimeRewardsEach = new ArrayList<OntimeReward>();
			for (String str : Config.getInstance().getOntimeRewardsEach()) {
				if (StringUtils.getInstance().isInt(str)) {
					int reward = Integer.parseInt(str);
					if (Config.getInstance().getOntimeRewardsEachEnabled(reward)) {
						ontimeRewardsEach.add(new OntimeReward(reward,
								OntimeAchivement.getTimeType(Config.getInstance().getOntimeRewardsEachTimeType(reward)),
								Config.getInstance().getOntimeRewardsEachPath(reward)));
					}
				}
			}
		} catch (Exception e) {
			plugin.getLogger().warning("Failed to load ontime rewards: " + e.getMessage());
			AdvancedCoreHook.getInstance().debug(e);
		}
	}

	/**
	 * @return the ontimeRewardsEach
	 */
	public ArrayList<OntimeReward> getOntimeRewardsEach() {
		return ontimeRewardsEach;
	}

	private void loadFiles() {
		pluginConfig = Config.getInstance();
		pluginConfig.setup();
	}

	/**
	 * @return the pluginConfig
	 */
	public Config getPluginConfig() {
		return pluginConfig;
	}

	public void reload() {
		Config.getInstance().reloadData();
		loadOntimeRewards();
		updateAdvancedCoreHook();
		update();
	}

	/**
	 * @return the onlineToday
	 */
	public HashMap<User, Long> getOnlineToday() {
		return onlineToday;
	}

	/**
	 * @return the update
	 */
	public boolean isUpdate() {
		return update;
	}

	/**
	 * @param update
	 *            the update to set
	 */
	public void setUpdate(boolean update) {
		this.update = update;
	}

	public UserManager getUserManager() {
		return UserManager.getInstance();
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerListeners(this), plugin);
		pm.registerEvents(new PlayerOntimeListener(this), plugin);

		debug("Loaded Events");
	}

	private void registerCommands() {
		CommandLoader.getInstance().loadCommands();
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("advancedplayerstats");
		cmds.add("aps");
		cmds.add("advancedps");
		cmds.add("playerstats");
		cmds.add("ps");
		for (String cmd : cmds) {
			getCommand(cmd).setExecutor(new CommandAdvancedPlayerStats());
			getCommand(cmd).setTabCompleter(new AdvancedPlayerStatsTabCompleter());
		}

		plugin.debug("Loaded Commands");
	}

	public synchronized void update() {
		onlineToday = new LinkedHashMap<User, Long>();
		ontimeTop = new LinkedHashMap<User, Long>();
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			User user = plugin.getUserManager().getAdvancedPlayerStatsUser(player);
			if (user.wasOnlineToday()) {
				onlineToday.put(user, user.getLastOnline());
			}
			if (user.getOntime() > 0) {
				ontimeTop.put(user, user.getOntime());
			}
		}
		onlineToday = sortByValuesLong(onlineToday, false);
		ontimeTop = sortByValuesLong(ontimeTop, true);

		debug("Background task ran");
	}

	/**
	 * @return the ontimeTop
	 */
	public HashMap<User, Long> getOntimeTop() {
		return ontimeTop;
	}

	private HashMap<User, Long> sortByValuesLong(HashMap<User, Long> unsortMap, final boolean order) {

		List<Entry<User, Long>> list = new LinkedList<Entry<User, Long>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<User, Long>>() {
			@Override
			public int compare(Entry<User, Long> o1, Entry<User, Long> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		HashMap<User, Long> sortedMap = new LinkedHashMap<User, Long>();
		for (Entry<User, Long> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public void updateAdvancedCoreHook() {
		AdvancedCoreHook.getInstance().getJavascriptEngine().put("AdvancedPlayerStats", this);
		AdvancedCoreHook.getInstance().setExtraDebug(Config.getInstance().getExtraDebug());
		AdvancedCoreHook.getInstance().setStorageType(UserStorage.valueOf(Config.getInstance().getDataStorage()));
		loadMySQL();

		AdvancedCoreHook.getInstance().setDebug(Config.getInstance().getDebugEnabled());
		AdvancedCoreHook.getInstance().setDebugIngame(Config.getInstance().getDebugInfoIngame());
		AdvancedCoreHook.getInstance().setFormatNoPerms(Config.getInstance().getFormatNoPerms());
		AdvancedCoreHook.getInstance().setFormatNotNumber(Config.getInstance().getFormatNotNumber());
		AdvancedCoreHook.getInstance().setHelpLine(Config.getInstance().getFormatHelpLine());
		AdvancedCoreHook.getInstance().setLogDebugToFile(Config.getInstance().getLogDebugToFile());
	}

	private void loadMySQL() {
		if (AdvancedCoreHook.getInstance().getStorageType().equals(UserStorage.MYSQL)) {
			Thread.getInstance().run(new Runnable() {

				@Override
				public void run() {
					AdvancedCoreHook.getInstance()
							.setMysql(new MySQL("AdvancedPlayerStats_Users", Config.getInstance().getMySqlHost(),
									Config.getInstance().getMySqlPort(), Config.getInstance().getMySqlDatabase(),
									Config.getInstance().getMySqlUsername(), Config.getInstance().getMySqlPassword(),
									Config.getInstance().getMySqlMaxConnections()));
				}
			});

		}
	}

}
