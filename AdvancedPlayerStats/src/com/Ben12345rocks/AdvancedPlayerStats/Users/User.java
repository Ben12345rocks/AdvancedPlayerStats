package com.Ben12345rocks.AdvancedPlayerStats.Users;

import org.bukkit.entity.Player;

import com.Ben12345rocks.AdvancedCore.Objects.UUID;
import com.Ben12345rocks.AdvancedPlayerStats.Main;

public class User extends com.Ben12345rocks.AdvancedCore.Objects.User {

	/** The plugin. */
	static Main plugin = Main.plugin;

	/**
	 * Instantiates a new user.
	 *
	 * @param player
	 *            the player
	 */
	@Deprecated
	public User(Player player) {
		super(Main.plugin, player);
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param playerName
	 *            the player name
	 */
	@Deprecated
	public User(String playerName) {
		super(Main.plugin, playerName);

	}

	/**
	 * Instantiates a new user.
	 *
	 * @param uuid
	 *            the uuid
	 */
	@Deprecated
	public User(UUID uuid) {
		super(Main.plugin, uuid);

	}

	/**
	 * Instantiates a new user.
	 *
	 * @param uuid
	 *            the uuid
	 * @param loadName
	 *            the load name
	 */
	@Deprecated
	public User(UUID uuid, boolean loadName) {
		super(Main.plugin, uuid, loadName);
	}

	public long getLastOnline() {
		return Long.valueOf(getData().getString("LastOnline"));
	}

	public void setLastOnline(long login) {
		getData().setString("LastOnline", "" + login);
	}

	public void updateLastOnline() {
		setLastOnline(System.currentTimeMillis());
	}
}
