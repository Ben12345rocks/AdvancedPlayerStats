package com.Ben12345rocks.AdvancedPlayerStats.Users;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.Ben12345rocks.AdvancedCore.Objects.UUID;
import com.Ben12345rocks.AdvancedCore.Util.Misc.PlayerUtils;

public class UserManager {
	/** The instance. */
	static UserManager instance = new UserManager();

	/**
	 * Gets the single instance of UserManager.
	 *
	 * @return single instance of UserManager
	 */
	public static UserManager getInstance() {
		return instance;
	}

	public UserManager() {
		super();
	}

	public User getAdvancedPlayerStatsUser(OfflinePlayer player) {
		return getAdvancedPlayerStatsUser(player.getName());
	}

	public User getAdvancedPlayerStatsUser(Player player) {
		return getAdvancedPlayerStatsUser(player.getName());
	}

	public User getAdvancedPlayerStatsUser(String playerName) {
		return getAdvancedPlayerStatsUser(new UUID(PlayerUtils.getInstance().getUUID(playerName)));
	}

	@SuppressWarnings("deprecation")
	public User getAdvancedPlayerStatsUser(UUID uuid) {
		return new User(uuid);
	}
}
