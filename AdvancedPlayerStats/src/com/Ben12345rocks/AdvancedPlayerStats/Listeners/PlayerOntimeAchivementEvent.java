package com.Ben12345rocks.AdvancedPlayerStats.Listeners;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.Ben12345rocks.AdvancedPlayerStats.Rewards.OntimeAchivement;
import com.Ben12345rocks.AdvancedPlayerStats.Users.User;

// TODO: Auto-generated Javadoc
/**
 * The Class DayChangeEvent.
 */
public class PlayerOntimeAchivementEvent extends Event {

	/** The Constant handlers. */
	private static final HandlerList handlers = new HandlerList();

	/**
	 * Gets the handler list.
	 *
	 * @return the handler list
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}

	public PlayerOntimeAchivementEvent(User user, OntimeAchivement achivement, int time) {
		super(true);
		this.user = user;
		this.achivement = achivement;
		this.time = time;
	}

	private User user;
	private OntimeAchivement achivement;

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the achivement
	 */
	public OntimeAchivement getAchivement() {
		return achivement;
	}

	/**
	 * @param achivement
	 *            the achivement to set
	 */
	public void setAchivement(OntimeAchivement achivement) {
		this.achivement = achivement;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	private int time;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.bukkit.event.Event#getHandlers()
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
