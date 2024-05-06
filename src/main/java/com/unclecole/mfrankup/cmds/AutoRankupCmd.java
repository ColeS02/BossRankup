package com.unclecole.mfrankup.cmds;

import com.unclecole.mfrankup.MFRankup;
import com.unclecole.mfrankup.utilities.TL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoRankupCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] args) {

        if (!(s instanceof Player)) return false;

        Player player = (Player) s;

        if(MFRankup.getInstance().getAutoRankup().containsKey(player.getUniqueId())) {
            MFRankup.getInstance().getAutoRankup().remove(player.getUniqueId());
            TL.AUTO_RANKUP_DISABLED.send(player);
            return false;
        }
        MFRankup.getInstance().getAutoRankup().put(player.getUniqueId(),System.currentTimeMillis());
        TL.AUTO_RANKUP_ENABLED.send(player);
        return false;
    }
}
