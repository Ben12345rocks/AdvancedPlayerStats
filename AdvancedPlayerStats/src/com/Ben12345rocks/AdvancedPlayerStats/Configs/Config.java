package com.Ben12345rocks.AdvancedPlayerStats.Configs;

import java.io.File;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

import com.Ben12345rocks.AdvancedCore.YML.YMLFile;
import com.Ben12345rocks.AdvancedPlayerStats.Main;

// TODO: Auto-generated Javadoc
/**
 * The Class Config.
 */
public class Config extends YMLFile {

	/** The instance. */
	static Config instance = new Config();

	/** The plugin. */
	static Main plugin = Main.plugin;

	/**
	 * Gets the single instance of Config.
	 *
	 * @return single instance of Config
	 */
	public static Config getInstance() {
		return instance;
	}

	/**
	 * Instantiates a new config.
	 */
	public Config() {
		super(new File(Main.plugin.getDataFolder(), "Config.yml"));
	}

	public String getDataStorage() {
		return getData().getString("DataStorage", "FLAT");
	}

	/**
	 * Gets the debug enabled.
	 *
	 * @return the debug enabled
	 */
	public boolean getDebugEnabled() {
		return getData().getBoolean("Debug");
	}

	/**
	 * Gets the debug info ingame.
	 *
	 * @return the debug info ingame
	 */
	public boolean getDebugInfoIngame() {
		return getData().getBoolean("DebugInfoIngame");
	}

	public boolean getExtraDebug() {
		return getData().getBoolean("ExtraDebug");
	}

	public boolean getLogDebugToFile() {
		return getData().getBoolean("LogDebugToFile", true);
	}

	public String getFormatHelpLine() {
		return getData().getString("Format.HelpLine", "&3&l%Command% - &3%HelpMessage%");
	}

	public Set<String> getOntimeRewards() {
		return getData().getConfigurationSection("OntimeRewards").getKeys(false);
	}

	private ConfigurationSection getOntimeReward(int reward) {
		return getData().getConfigurationSection("OntimeRewards." + reward);
	}

	public boolean getOntimeRewardEnabled(int reward) {
		return getOntimeReward(reward).getBoolean("Enabled");
	}

	public String getOntimeRewardTimeType(int reward) {
		return getOntimeReward(reward).getString("TimeType", "HOURS");
	}

	public String getOntimeRewardPath(int reward) {
		return getOntimeReward(reward).getConfigurationSection("Rewards").getCurrentPath();
	}

	/**
	 * Gets the format no perms.
	 *
	 * @return the format no perms
	 */
	public String getFormatNoPerms() {
		return getData().getString("Format.NoPerms", "&cYou do not have enough permission!");
	}

	public String getFormatNotNumber() {
		return getData().getString("Format.NotNumber", "&cError on &6%arg%&c, number expected!");
	}

	public String getMySqlDatabase() {
		return getData().getString("MySQL.Database", "");
	}

	public String getMySqlHost() {
		return getData().getString("MySQL.Host", "");
	}

	public int getMySqlMaxConnections() {
		return getData().getInt("MySQL.MaxConnections", 1);
	}

	public String getMySqlPassword() {
		return getData().getString("MySQL.Password", "");
	}

	public int getMySqlPort() {
		return getData().getInt("MySQL.Port");
	}

	public boolean getMySqlPreloadTable() {
		return getData().getBoolean("MySQL.PreLoadTable");
	}

	public String getMySqlUsername() {
		return getData().getString("MySQL.Username", "");
	}

	@Override
	public void onFileCreation() {
		plugin.saveResource("Config.yml", true);
	}
}
