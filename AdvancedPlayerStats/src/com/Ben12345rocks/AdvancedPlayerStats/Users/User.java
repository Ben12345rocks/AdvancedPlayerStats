package com.Ben12345rocks.AdvancedPlayerStats.Users;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
		return getOfflinePlayer().getLastPlayed();
	}

	public long getFirstJoin() {
		return getOfflinePlayer().getFirstPlayed();
	}

	public boolean wasOnlineToday() {
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(getLastOnline()), ZoneId.systemDefault());
		LocalDateTime now = LocalDateTime.now();
		if (date.getDayOfMonth() == now.getDayOfMonth()) {
			return true;
		}
		return false;
	}

	public void setIPAddress(String address) {
		getData().setString("Address", address);
	}

	public String getIPAddress() {
		return getData().getString("Address");
	}
}
