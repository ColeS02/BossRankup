package com.unclecole.mfrankup.cmds;

import com.unclecole.mfrankup.MFRankup;
import com.unclecole.mfrankup.database.RankData;
import com.unclecole.mfrankup.objects.Ranks;
import com.unclecole.mfrankup.utilities.C;
import com.unclecole.mfrankup.utilities.PlaceHolder;
import com.unclecole.mfrankup.utilities.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class MaxRankupCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] args) {

        if (!(s instanceof Player)) return false;

        Player player = (Player) s;

        int currRank = RankData.rankData.getOrDefault(player.getUniqueId().toString(), 0);


        double bal = MFRankup.getInstance().getEcon().getBalance(player);
        MFRankup mfrankup = MFRankup.getInstance();

        if (mfrankup.getRankData().size() - 1 < currRank + 1) {
            TL.MAX_RANK.send(player);
            return false;
        }

        Ranks nextRank = mfrankup.getRankData().get(currRank + 1);

        if (bal <= nextRank.getCost()) {
            TL.INSUFFICENT_FUNDS.send(player);
            return false;
        }
        maxRank(currRank, player, (long) bal);
        return false;
    }

    public void maxRank(int currRank, Player player, long bal) {
        Bukkit.getScheduler().runTaskAsynchronously(MFRankup.getInstance(), new Runnable() {
            @Override
            public void run() {

                Ranks nextRank = MFRankup.getInstance().getRankData().get(currRank + 1);
                long amt = nextRank.getCost();

                for(int i = currRank; i < MFRankup.getInstance().getRankData().size(); i++) {
                    if (bal >= amt || MFRankup.getInstance().getRankData().size()-1 < i + 1) {
                        amt -= nextRank.getCost();
                        nextRank = MFRankup.getInstance().getRankData().get(i);
                        TL.SUCCESSFULLY_RANKED_UP.send(player, new PlaceHolder("%rank%", nextRank.getName()));
                        RankData.rankData.put(player.getUniqueId().toString(),i);
                        for(int y = currRank; y<=i; y++) {
                            Ranks rank = MFRankup.getInstance().getRankData().get(y);
                                for (int x = 0; x < rank.getCommands().size(); x++) {
                                    int finalX = x;
                                    Bukkit.getScheduler().runTask(MFRankup.getInstance(), new Runnable() {
                                        @Override
                                        public void run() {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rank.getCommands().get(finalX).replace("[console] ", "").replace("%amount%", "1").replace("%player%", player.getName()));
                                        }
                                    });
                                }

                            for(int x = 0; x < rank.getMessages().size(); x++) {
                                int finalX = x;
                                Bukkit.getScheduler().runTask(MFRankup.getInstance(), new Runnable() {
                                    @Override
                                    public void run() {
                                        player.sendMessage(C.color(rank.getMessages().get(finalX), new PlaceHolder("%player%", player.getName())));
                                    }
                                });
                            }
                        }
                        MFRankup.getInstance().getEcon().withdrawPlayer(player, amt);
                        return;
                    } else {
                        nextRank = MFRankup.getInstance().getRankData().get(i+1);
                        amt += nextRank.getCost();
                    }

                }
            }
        });
    }
}
