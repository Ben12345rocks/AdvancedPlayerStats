package com.Ben12345rocks.AdvancedPlayerStats;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Ben12345rocks.AdvancedCore.AdvancedCoreHook;
import com.Ben12345rocks.AdvancedCore.Objects.CommandHandler;
import com.Ben12345rocks.AdvancedCore.Util.Metrics.BStatsMetrics;
import com.Ben12345rocks.AdvancedCore.Util.Metrics.MCStatsMetrics;
import com.Ben12345rocks.AdvancedPlayerStats.Commands.CommandLoader;
import com.Ben12345rocks.AdvancedPlayerStats.Commands.Executor.CommandAdvancedPlayerStats;
import com.Ben12345rocks.AdvancedPlayerStats.Commands.TabComplete.AdvancedPlayerStatsTabCompleter;
import com.Ben12345rocks.AdvancedPlayerStats.Listeners.PlayerListeners;
import com.Ben12345rocks.AdvancedPlayerStats.Users.UserManager;

/**
 * The Class Main.
 */
public class Main extends JavaPlugin {

	public static Main plugin;
	public ArrayList<CommandHandler> commands;

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

		AdvancedCoreHook.getInstance().loadHook(plugin);
		registerEvents();
		registerCommands();
		metrics();

		plugin.getLogger().info("Enabled AdvancedPlayerStats " + plugin.getDescription().getVersion());
	}

	public UserManager getUserManager() {
		return UserManager.getInstance();
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerListeners(this), plugin);

		debug("Loaded Events");
	}

	private void registerCommands() {
		CommandLoader.getInstance().loadCommands();

		getCommand("advancedplayerstats").setExecutor(new CommandAdvancedPlayerStats());
		getCommand("advancedplayerstats").setTabCompleter(new AdvancedPlayerStatsTabCompleter());

		plugin.debug("Loaded Commands");

	}

}
