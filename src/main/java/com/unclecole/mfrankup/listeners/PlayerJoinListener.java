package com.unclecole.mfrankup.listeners;

import com.unclecole.mfrankup.database.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if(!RankData.rankData.containsKey(player.getUniqueId().toString())) {
            RankData.rankData.put(player.getUniqueId().toString(),0);
        }
        if(!RankData.prestigeData.containsKey(player.getUniqueId().toString())) {
            RankData.prestigeData.put(player.getUniqueId().toString(),0);
        }
    }
}
