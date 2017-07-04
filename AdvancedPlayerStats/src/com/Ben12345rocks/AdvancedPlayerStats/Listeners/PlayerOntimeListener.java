package com.Ben12345rocks.AdvancedPlayerStats.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.Ben12345rocks.AdvancedPlayerStats.Main;
import com.Ben12345rocks.AdvancedPlayerStats.Rewards.OntimeReward;

public class PlayerOntimeListener implements Listener {

	/** The plugin. */
	private static Main plugin;

	/**
	 * Instantiates a new player join event.
	 *
	 * @param plugin
	 *            the plugin
	 */
	public PlayerOntimeListener(Main plugin) {
		PlayerOntimeListener.plugin = plugin;
	}

	/**
	 * On player login.
	 *
	 * @param event
	 *            the event
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerOntime(PlayerOntimeAchivementEvent event) {
		for (OntimeReward reward : plugin.getOntimeRewards()) {
			if (reward.getTimeType().equals(event.getAchivement())) {
				if (reward.getTime() == event.getTime()) {
					reward.giveReward(event.getUser());
				}
			}
		}

		for (OntimeReward reward : plugin.getOntimeRewardsEach()) {
			if (reward.getTimeType().equals(event.getAchivement())) {
				if (event.getTime() % reward.getTime() == 0) {
					reward.giveReward(event.getUser());
				}
			}
		}
	}

}
