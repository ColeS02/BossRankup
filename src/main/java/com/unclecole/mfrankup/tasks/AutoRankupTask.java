package com.unclecole.mfrankup.tasks;

import com.unclecole.mfrankup.MFRankup;
import com.unclecole.mfrankup.database.RankData;
import com.unclecole.mfrankup.objects.Ranks;
import com.unclecole.mfrankup.utilities.C;
import com.unclecole.mfrankup.utilities.PlaceHolder;
import com.unclecole.mfrankup.utilities.TL;
import me.taleeko.bossenchants.BossEnchants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigInteger;

public class AutoRankupTask implements Runnable {

    @Override
    public void run() {
        MFRankup.getInstance().getAutoRankup().forEach((uuid, time) -> {
            if(((System.currentTimeMillis() - time) % 6000) >= 5000) {

                if(Bukkit.getPlayer(uuid) == null) return;

                Player player = Bukkit.getPlayer(uuid);

                int currRank = RankData.rankData.getOrDefault(uuid.toString(), 0);

                BigInteger eco = BossEnchants.getInstance().getTokenAPI().getTokenBalance(player);
                MFRankup bossRankup = MFRankup.getInstance();

                if (bossRankup.getRankData().size() - 1 >= currRank + 1) {

                    Ranks nextRank = bossRankup.getRankData().get(currRank + 1);

                    if (eco.compareTo(nextRank.getCost()) >= 1) {
                        maxRank(currRank, player, eco);
                    }
                }
            }
        });
    }

    public void maxRank(int currRank, Player player, BigInteger eco) {
        Bukkit.getScheduler().runTaskAsynchronously(MFRankup.getInstance(), new Runnable() {
            @Override
            public void run() {

                Ranks nextRank = MFRankup.getInstance().getRankData().get(currRank + 1);
                long amt = nextRank.getCost();

                for(int i = currRank; i < MFRankup.getInstance().getRankData().size(); i++) {
                    if ((eco.compareTo(amt) <= -1 || MFRankup.getInstance().getRankData().size()-1 < i + 1) && (i - currRank) > 0) {
                        amt = amt.subtract(nextRank.getCost());
                        nextRank = MFRankup.getInstance().getRankData().get(i);
                        TL.SUCCESSFULLY_RANKED_UP.send(player, new PlaceHolder("%rank%", nextRank.getName()));
                        RankData.rankData.put(player.getUniqueId().toString(),i);
                        for(int y = currRank; y<i; y++) {
                            Ranks rank = MFRankup.getInstance().getRankData().get(y);
                            if(RankData.prestigeData.getOrDefault(player.getUniqueId().toString(), 0) < 1) {
                                for (int x = 0; x < rank.getCommands().size(); x++) {
                                    int finalX = x;
                                    Bukkit.getScheduler().runTask(MFRankup.getInstance(), new Runnable() {
                                        @Override
                                        public void run() {
                                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rank.getCommands().get(finalX).replace("[console] ", "").replace("%amount%", "1").replace("%player%", player.getName()));
                                        }
                                    });
                                }
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
                        BossEnchants.getInstance().getTokenAPI().removeTokens(player, amt);
                        return;
                    } else {
                        nextRank = MFRankup.getInstance().getRankData().get(i+1);
                        amt = amt.add(nextRank.getCost());
                    }

                }
            }
        });
    }
}
