package com.Ben12345rocks.AdvancedPlayerStats.Listeners;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.Ben12345rocks.AdvancedCore.Data.ServerData;
import com.Ben12345rocks.AdvancedCore.Listeners.DateChangedEvent;
import com.Ben12345rocks.AdvancedCore.Objects.UUID;
import com.Ben12345rocks.AdvancedCore.TimeChecker.TimeType;
import com.Ben12345rocks.AdvancedPlayerStats.Main;
import com.Ben12345rocks.AdvancedPlayerStats.Users.User;
import com.Ben12345rocks.AdvancedPlayerStats.Users.UserManager;

public class DayChangeListener implements Listener {

	/** The plugin. */
	@SuppressWarnings("unused")
	private static Main plugin;

	/**
	 * Instantiates a new player join event.
	 *
	 * @param plugin
	 *            the plugin
	 */
	public DayChangeListener(Main plugin) {
		DayChangeListener.plugin = plugin;
	}

	/**
	 * On player login.
	 *
	 * @param event
	 *            the event
	 */
	@SuppressWarnings("unchecked")
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDayChange(DateChangedEvent event) {
		if (event.getTimeType().equals(TimeType.DAY)) {
			ArrayList<String> onlineTodayUuids = (ArrayList<String>) ServerData.getInstance().getData()
					.getList("OnlineToday", new ArrayList<String>());
			for (String uuid : UserManager.getInstance().getAllUUIDs()) {
				User user = UserManager.getInstance().getAdvancedPlayerStatsUser(new UUID(uuid));
				if (onlineTodayUuids.contains(uuid)) {
					user.setOnlineYesterday(true);
				} else {
					user.setOnlineYesterday(false);
				}
			}
		}
	}

}
