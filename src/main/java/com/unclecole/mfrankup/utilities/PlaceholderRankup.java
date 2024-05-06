package com.unclecole.mfrankup.utilities;

import com.unclecole.mfrankup.MFRankup;
import com.unclecole.mfrankup.database.RankData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.UUID;

public class PlaceholderRankup extends PlaceholderExpansion {

    private final MFRankup plugin;
    private final NumberFormat numberFormat;

    public PlaceholderRankup(MFRankup plugin) {
        this.plugin = plugin;
        numberFormat = NumberFormat.getInstance();
    }

    @Override
    public String getAuthor() {
        return "Cole";
    }

    @Override
    public String getIdentifier() {
        return "BossRankup";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        Player onlinePlayer = (Player) player;
        if(params.equalsIgnoreCase("rank")) {
            return RankData.rankData.get(onlinePlayer.getUniqueId().toString()).toString();
        }
        if(params.equalsIgnoreCase("rank_name")){
            return plugin.getRankData().get(RankData.rankData.get(onlinePlayer.getUniqueId().toString())).getName();
        }
        if(params.equalsIgnoreCase("prestigerank")){
            return plugin.getRankData().get(RankData.prestigeData.getOrDefault(onlinePlayer.getUniqueId().toString(), 0)).getName();
        }
        if(params.equalsIgnoreCase("next_rank_name")) {
            if(RankData.rankData.get(onlinePlayer.getUniqueId().toString())+1 >= plugin.getRankData().size()) return "MAX RANK";
            return plugin.getRankData().get(RankData.rankData.get(onlinePlayer.getUniqueId().toString())+1).getName();
        }
        if(params.equalsIgnoreCase("rank_progress")) {
            //return C.getProgressBar(BossRankup.getInstance().getApi().getTokenAPI().getTokenBalance(onlinePlayer),
              //      plugin.getRankData().get(RankData.rankData.get(onlinePlayer.getUniqueId())).getCost(), 10, "|");
        }
        if(params.equalsIgnoreCase("next_rank_cost_total")) {
            if(RankData.rankData.get(onlinePlayer.getUniqueId().toString())+1 >= plugin.getRankData().size()) return "MAX RANK";
            return numberFormat.format(plugin.getRankData().get(RankData.rankData.get(onlinePlayer.getUniqueId().toString())+1).getCost());
            //return plugin.getNumberFormat().format(plugin.getRankData().get(RankData.rankData.get(onlinePlayer.getUniqueId())+1).getCost());
        }
        if(params.equalsIgnoreCase("next_rank_cost")) {
            return NumberFormat.getIntegerInstance().format(plugin.getRankData().get(RankData.rankData.get(onlinePlayer.getUniqueId().toString()) + 1).getCost() - MFRankup.getInstance().getEcon().getBalance(onlinePlayer));
        }
        if(params.equalsIgnoreCase("top1")) {
            if(MFRankup.getInstance().getSorting().getFirstRank() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getFirstRank()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }
        if(params.equalsIgnoreCase("top2")) {
            if(MFRankup.getInstance().getSorting().getSecondRank() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getSecondRank()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }
        if(params.equalsIgnoreCase("top3")) {
            if(MFRankup.getInstance().getSorting().getThirdRank() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getThirdRank()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }
        if(params.equalsIgnoreCase("top4")) {
            if(MFRankup.getInstance().getSorting().getFourthRank() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getFourthRank()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }
        if(params.equalsIgnoreCase("top5")) {
            if(MFRankup.getInstance().getSorting().getFifthRank() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getFifthRank()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }
        //
        if(params.equalsIgnoreCase("prestigetop1")) {
            if(MFRankup.getInstance().getSorting().getFirstPrestige() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getFirstPrestige()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }
        if(params.equalsIgnoreCase("prestigetop2")) {
            if(MFRankup.getInstance().getSorting().getSecondPrestige() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getSecondPrestige()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }
        if(params.equalsIgnoreCase("prestigetop3")) {
            if(MFRankup.getInstance().getSorting().getThirdPrestige() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getThirdPrestige()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }
        if(params.equalsIgnoreCase("prestigetop4")) {
            if(MFRankup.getInstance().getSorting().getFourthPrestige() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getFourthPrestige()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }
        if(params.equalsIgnoreCase("prestigetop5")) {
            if(MFRankup.getInstance().getSorting().getFifthPrestige() == null) return "No One";
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(MFRankup.getInstance().getSorting().getFifthPrestige()));
                if(offlinePlayer.getName() == null) return "No One";
                return offlinePlayer.getName();
            }
        }



        return null; // Placeholder is unknown by the Expansion
    }
}
