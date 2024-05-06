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

public class RankupCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] args) {

        if (!(s instanceof Player)) return false;

        Player player = (Player) s;

        int currRank = RankData.rankData.getOrDefault(player.getUniqueId().toString(), 0);

        double eco = MFRankup.getInstance().getEcon().getBalance(player);
        MFRankup bossRankup = MFRankup.getInstance();
        if (bossRankup.getRankData().size() - 1 < currRank + 1) {
            TL.MAX_RANK.send(player);
            return false;
        }
        Ranks nextRank = bossRankup.getRankData().get(currRank + 1);

        if (eco >= nextRank.getCost()) {
            MFRankup.getInstance().getEcon().withdrawPlayer(player, nextRank.getCost());
            RankData.rankData.put(player.getUniqueId().toString(), ++currRank);
            TL.SUCCESSFULLY_RANKED_UP.send(player, new PlaceHolder("%rank%", bossRankup.getRankData().get(RankData.rankData.get(player.getUniqueId().toString())).getName()));

            Ranks rank = bossRankup.getRankData().get(RankData.rankData.get(player.getUniqueId().toString()));

            for (int i = 0; i < rank.getCommands().size(); i++) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rank.getCommands().get(i).replace("[console] ", "").replace("%amount%", "1").replace("%player%", player.getName()));
            }
            for (int i = 0; i < rank.getMessages().size(); i++) {
                player.sendMessage(C.color(rank.getMessages().get(i).replaceAll("%player%", player.getName())));
            }
        } else {
            TL.INSUFFICENT_FUNDS.send(player);
        }

        return false;
    }
}
