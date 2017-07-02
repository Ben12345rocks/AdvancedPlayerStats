package com.Ben12345rocks.AdvancedPlayerStats.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Ben12345rocks.AdvancedPlayerStats.Main;

public class PlayerListeners implements Listener {

	/** The plugin. */
	private static Main plugin;

	/**
	 * Instantiates a new player join event.
	 *
	 * @param plugin
	 *            the plugin
	 */
	public PlayerListeners(Main plugin) {
		PlayerListeners.plugin = plugin;
	}

	/**
	 * On player login.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

			@Override
			public void run() {
				Player player = event.getPlayer();
				if (player != null) {
					plugin.setUpdate(true);
					// User user =
					// plugin.getUserManager().getAdvancedPlayerStatsUser(player);
					// user.updateLastOnline();
					// user.checkFirstJoin();
				}
			}
		}, 10l);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerLogOff(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (player != null) {
			// String uuid = player.getUniqueId().toString();
			Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {

				@Override
				public void run() {
					plugin.setUpdate(true);
					// User user =
					// plugin.getUserManager().getAdvancedPlayerStatsUser(new
					// UUID(uuid));
					// user.updateLastOnline();
				}
			}, 10l);
		}
	}

}
