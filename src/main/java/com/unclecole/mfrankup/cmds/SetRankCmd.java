package com.unclecole.mfrankup.cmds;

import com.unclecole.mfrankup.MFRankup;
import com.unclecole.mfrankup.database.RankData;
import com.unclecole.mfrankup.utilities.PlaceHolder;
import com.unclecole.mfrankup.utilities.TL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRankCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] args) {

        if(!(s instanceof Player)) return false;

        Player player = (Player) s;

        if(!player.hasPermission("Bossrankup.Setrank")) {
            TL.NO_PERMISSION.send(player);
            return false;
        }

        if(args.length < 2) {
            return false;
        }

        if(Bukkit.getPlayer(args[0]) == null) {
            TL.INVALID_USER.send(player);
            return false;
        }
        Player playerChange = Bukkit.getPlayer(args[0]);

        int rank = Integer.parseInt(args[1])-1;
        if(rank < 0 || rank > MFRankup.getInstance().getRankData().size()-1) {
            TL.INVALID_RANK.send(player);
            return false;
        }


        RankData.rankData.put(playerChange.getUniqueId().toString(), rank);
        TL.SUCCESSFULLY_SET_USER_RANK.send(player,new PlaceHolder("%user%", playerChange.getName()), new PlaceHolder("%rank%", rank+1));
        if(!player.equals(playerChange)) {
            TL.RANK_SET.send(playerChange, new PlaceHolder("%rank%", rank + 1));
        }
        return false;
    }
}
