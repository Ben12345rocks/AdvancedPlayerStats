package com.Ben12345rocks.AdvancedPlayerStats.Users;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.Ben12345rocks.AdvancedCore.Objects.UUID;
import com.Ben12345rocks.AdvancedPlayerStats.Main;
import com.Ben12345rocks.AdvancedPlayerStats.Listeners.PlayerOntimeAchivementEvent;
import com.Ben12345rocks.AdvancedPlayerStats.Rewards.OntimeAchivement;

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
		if (date.getDayOfMonth() == now.getDayOfMonth() && date.getMonthValue() == now.getMonthValue()
				&& date.getYear() == now.getYear()) {
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

	public long getOntime() {
		return valueOf(getData().getString("Ontime"));
	}

	public void setOntime(long time) {
		getData().setString("Ontime", "" + time);
		plugin.setUpdate(true);
	}

	public void updateLoginTime() {
		setLoginTime(System.currentTimeMillis());
	}

	public void setLoginTime(long time) {
		getData().setString("LoginTime", "" + time);
	}

	public long getLoginTime() {
		return valueOf(getData().getString("LoginTime"));
	}

	private long valueOf(String str) {
		if (str.equals("")) {
			return 0;
		}
		return Long.valueOf(str);
	}

	public void updateOntime() {
		long login = getLoginTime();
		if (login > 0) {
			long cur = System.currentTimeMillis();
			Duration dur = Duration.of(cur - login, ChronoUnit.MILLIS);
			Duration currentOntime = Duration.of(getOntime(), ChronoUnit.MILLIS);
			setOntime(Duration.of(getOntime(), ChronoUnit.MILLIS).plus(dur).toMillis());
			Duration newOntime = Duration.of(getOntime(), ChronoUnit.MILLIS);
			if (currentOntime.toHours() != newOntime.toHours()) {
				// plugin.debug("Hour");
				for (int i = (int) (currentOntime.toHours() + 1); i <= newOntime.toHours(); i++) {
					PlayerOntimeAchivementEvent event = new PlayerOntimeAchivementEvent(this, OntimeAchivement.HOURS,
							i);
					Bukkit.getPluginManager().callEvent(event);
					// plugin.debug("H: " + i);
				}
			}
			if (currentOntime.toDays() != newOntime.toDays()) {
				// plugin.debug("Day");
				for (int i = (int) (currentOntime.toDays() + 1); i <= newOntime.toDays(); i++) {
					PlayerOntimeAchivementEvent event = new PlayerOntimeAchivementEvent(this, OntimeAchivement.DAYS, i);
					Bukkit.getPluginManager().callEvent(event);
					// plugin.debug("Days: " + i);
				}
			}
			if (isOnline()) {
				updateLoginTime();
			} else {
				setLoginTime(0);
			}
		}
	}

	public String getOntimeString() {
		Duration dur = Duration.of(getOntime(), ChronoUnit.MILLIS);
		long days = dur.toDays();
		long hours = (dur.toHours() - (dur.toDays() * 24));
		long minutes = (dur.toMinutes() - (dur.toHours() * 60));
		if (days != 0) {
			return "&c" + days + " Days " + hours + " Hours " + minutes + " Minutes";
		} else if (hours != 0) {
			return "&c" + hours + " Hours " + minutes + " Minutes";
		} else {
			return "&c" + minutes + " Minutes";
		}
	}

	public boolean wasOnlineYesterday() {
		return Boolean.valueOf(getData().getString("OnlineYesterday"));
	}

	public void setOnlineYesterday(boolean value) {
		getData().setString("OnlineYesterday", "" + value);
	}
}
