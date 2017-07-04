package com.Ben12345rocks.AdvancedPlayerStats.Rewards;

import com.Ben12345rocks.AdvancedCore.Objects.RewardBuilder;
import com.Ben12345rocks.AdvancedPlayerStats.Configs.Config;
import com.Ben12345rocks.AdvancedPlayerStats.Users.User;

public class OntimeReward {
	private int time;
	private OntimeAchivement timeType;
	private String rewardPath;

	public OntimeReward(int time, OntimeAchivement timeType, String rewardPath) {
		this.time = time;
		this.timeType = timeType;
		this.rewardPath = rewardPath;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @return the timeType
	 */
	public OntimeAchivement getTimeType() {
		return timeType;
	}

	/**
	 * @return the reward
	 */
	public String getRewardPath() {
		return rewardPath;
	}

	public void giveReward(User user) {
		new RewardBuilder(Config.getInstance().getData(), rewardPath).withPlaceHolder("time", "" + time)
				.withPlaceHolder("timetype", timeType.getText()).withPlaceHolder("ontime", user.getOntimeString())
				.send(user);
	}

}
