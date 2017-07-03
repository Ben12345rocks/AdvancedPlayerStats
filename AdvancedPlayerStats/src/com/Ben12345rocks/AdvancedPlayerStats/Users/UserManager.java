package com.Ben12345rocks.AdvancedPlayerStats.Users;

import java.util.ArrayList;

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

	public ArrayList<String> getAllUUIDs() {
		return com.Ben12345rocks.AdvancedCore.UserManager.UserManager.getInstance().getAllUUIDs();
	}

	public ArrayList<User> getMatchedIps(User user) {
		ArrayList<User> matched = new ArrayList<User>();
		for (String uuid : UserManager.getInstance().getAllUUIDs()) {
			User user1 = UserManager.getInstance().getAdvancedPlayerStatsUser(new UUID(uuid));
			if (user1.getIPAddress().equals(user.getIPAddress())
					&& !user1.getPlayerName().equals(user.getPlayerName())) {
				matched.add(user1);
			}
		}
		return matched;
	}
}
