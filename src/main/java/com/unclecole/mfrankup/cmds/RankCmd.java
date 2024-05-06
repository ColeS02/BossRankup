package com.unclecole.mfrankup.cmds;

import com.unclecole.mfrankup.MFRankup;
import com.unclecole.mfrankup.database.RankData;
import com.unclecole.mfrankup.utilities.PlaceHolder;
import com.unclecole.mfrankup.utilities.TL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] args) {

        if(!(s instanceof Player)) return false;

        Player player = (Player) s;

        String rank = MFRankup.getInstance().getRankData().get(RankData.rankData.get(player.getUniqueId().toString())).getName();
        TL.RANK.send(player, new PlaceHolder("%rank%", rank));
        return false;
    }
}
