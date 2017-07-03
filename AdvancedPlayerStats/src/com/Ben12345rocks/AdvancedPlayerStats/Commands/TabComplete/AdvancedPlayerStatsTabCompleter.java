package com.Ben12345rocks.AdvancedPlayerStats.Commands.TabComplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.Ben12345rocks.AdvancedCore.Objects.CommandHandler;
import com.Ben12345rocks.AdvancedCore.Util.Misc.StringUtils;
import com.Ben12345rocks.AdvancedPlayerStats.Main;

/**
 * The Class AdminVoteTabCompleter.
 */
public class AdvancedPlayerStatsTabCompleter implements TabCompleter {

	/** The plugin. */
	Main plugin = Main.plugin;
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		ArrayList<String> tab = new ArrayList<String>();

		Set<String> cmds = new HashSet<String>();

		for (CommandHandler cmdHandle : plugin.commands) {
			cmds.addAll(cmdHandle.getTabCompleteOptions(sender, args, args.length - 1));
		}

		for (String str : cmds) {
			if (StringUtils.getInstance().startsWithIgnoreCase(str, args[args.length - 1])) {
				tab.add(str);
			}
		}

		Collections.sort(tab, String.CASE_INSENSITIVE_ORDER);

		return tab;
	}

}
