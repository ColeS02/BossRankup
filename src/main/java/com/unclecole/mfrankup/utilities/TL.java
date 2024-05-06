package com.unclecole.mfrankup.utilities;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;

public enum TL {
	NO_PERMISSION("messages.no-permission", "&cYou don't have the permission to do that."),
	SUCCESSFULLY_RANKED_UP("messages.successfully-ranked-up", "&a&lSUCCESS! &fYou ranked up to %rank%!"),
	NOT_MAX_LEVEL("messages.not-max-level", "&4&lERROR! &fMust be max level too prestige!"),
	SUCCESSFULLY_PRESTIGED("messages.successfully-ranked-up", "&a&lSUCCESS! &fYou prestige %prestige%!"),
	INSUFFICENT_FUNDS("messages.insufficient-funds", "&4&lERROR! &fNot enough money! (%BossRankup_next_rank_cost%)"),
	MAX_RANK("messages.max-rank", "&4&lERROR! &fMax Rank!"),
	INVALID_USER("messages.invalid-user", "&4&lERROR! &fInvalid User!"),
	INVALID_RANK("messages.invalid-rank", "&4&lERROR! &fInvalid Rank!"),
	SUCCESSFULLY_SET_USER_RANK("messages.successfully-set-user-rank", "&a&lSUCCESS! &fSet %user% to rank %rank%!"),
	RANK_SET("messages.rank-set", "&fYour rank has been set too %rank%!"),
	AUTO_RANKUP_ENABLED("messages.auto-rankup-enabled", "&fAuto rankup &a&lENABLED!"),
	AUTO_RANKUP_DISABLED("messages.auto-rankup-disabled", "&fAuto rankup &c&lDISABLED!"),
	RANK("messages.rank","&c&lRANK: &fCurrent rank is %rank%");


	private final String path;

	private String def;
	private static ConfigFile config;

	TL(String path, String start) {
		this.path = path;
		this.def = start;
	}

	public String getDefault() {
		return this.def;
	}

	public String getPath() {
		return this.path;
	}

	public void setDefault(String message) {
		this.def = message;
	}

	public void send(CommandSender sender) {
		if (sender instanceof org.bukkit.entity.Player) {
			Player player = (Player) sender;
			sender.sendMessage(PlaceholderAPI.setPlaceholders(player, C.color(getDefault())));
		} else {
			sender.sendMessage(C.strip(getDefault()));
		}
	}

	public static void loadMessages(ConfigFile configFile) {
		config = configFile;
		FileConfiguration data = configFile.getConfig();
		for (TL message : values()) {
			if (!data.contains(message.getPath())) {
				data.set(message.getPath(), message.getDefault());
			}
		}
		configFile.save();
	}


	public void send(CommandSender sender, PlaceHolder... placeHolders) {
		if (sender instanceof org.bukkit.entity.Player) {
			Player player = (Player) sender;
			sender.sendMessage(PlaceholderAPI.setPlaceholders(player, C.color(getDefault(), placeHolders)));
		} else {
			sender.sendMessage(C.strip(getDefault(), placeHolders));
		}
	}

	public static void message(CommandSender sender, String message) {
		sender.sendMessage(C.color(message));
	}

	public static void message(CommandSender sender, String message, PlaceHolder... placeHolders) {
		sender.sendMessage(C.color(message, placeHolders));
	}

	public static void message(CommandSender sender, List<String> message) {
		message.forEach(m -> sender.sendMessage(C.color(m)));
	}

	public static void message(CommandSender sender, List<String> message, PlaceHolder... placeHolders) {
		message.forEach(m -> sender.sendMessage(C.color(m, placeHolders)));
	}

	public static void log(Level lvl, String message) {
		Bukkit.getLogger().log(lvl, message);
	}
}
