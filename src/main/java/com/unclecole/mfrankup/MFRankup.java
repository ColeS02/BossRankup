package com.unclecole.mfrankup;

import com.unclecole.mfrankup.cmds.*;
import com.unclecole.mfrankup.database.RankData;
import com.unclecole.mfrankup.database.serializer.Persist;
import com.unclecole.mfrankup.listeners.PlayerJoinListener;
import com.unclecole.mfrankup.objects.Ranks;
import com.unclecole.mfrankup.tasks.AutoRankupTask;
import com.unclecole.mfrankup.utilities.*;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class MFRankup extends JavaPlugin {

    @Getter private static MFRankup instance;
    @Getter private static Persist persist;
    @Getter private ArrayList<Ranks> rankData;
    @Getter private ArrayList<Ranks> prestigeData;
    @Getter private HashMap<UUID, Long> autoRankup;

    @Getter private Economy econ = null;

    //@Setter @Getter private BossEnchants api;
    @Getter private NumberFormat numberFormat;
    @Getter private Sorting sorting;

    @Override
    public void onEnable() {

        instance = this;
        //api = BossEnchants.getInstance();
        persist = new Persist();
        rankData = new ArrayList<>();
        prestigeData = new ArrayList<>();
        autoRankup = new HashMap<>();
        numberFormat = new NumberFormat(this);
        saveDefaultConfig();
        TL.loadMessages(new ConfigFile("messages.yml", this));
        loadRanks();
        RankData.load();
        sorting = new Sorting();
        sorting.sortRanks();
        sorting.sortPrestiges();

        autoSaveTask();
        autoRankTop();

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            /*
             * We register the EventListener here, when PlaceholderAPI is installed.
             * Since all events are in the main class (this class), we simply use "this"
             */
            new PlaceholderRankup(this).register();
            getLogger().info("loaded PlaceholderAPI");
        } else {
            /*
             * We inform about the fact that PlaceholderAPI isn't installed and then
             * disable this plugin to prevent issues.
             */
            getLogger().info("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("rankup").setExecutor(new RankupCmd());
        getCommand("setrank").setExecutor(new SetRankCmd());
        getCommand("maxrankup").setExecutor(new MaxRankupCmd());
        getCommand("autorankup").setExecutor(new AutoRankupCmd());
        getCommand("rank").setExecutor(new RankCmd());
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new AutoRankupTask(), 20, 20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        RankData.save();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public void loadRanks() {
        for (String key : getConfig().getConfigurationSection("Ranks.default").getKeys(false)) {
            try {
                rankData.add(new Ranks(
                        getConfig().getString( "Ranks.default." + key + ".display"),
                        BigInteger.valueOf(getConfig().getLong( "Ranks.default." + key + ".cost")),
                        getConfig().getStringList( "Ranks.default." + key + ".executecmds"),
                        getConfig().getStringList("Ranks.default." + key + ".msg")));
            } catch (NullPointerException e) {
                Bukkit.getLogger().info( "ERROR! Ranks failed to load");
            }
        }
        for (String key : getConfig().getConfigurationSection("Prestiges").getKeys(false)) {
            try {
                prestigeData.add(new Ranks(
                        getConfig().getString( "Prestiges." + key + ".display"),
                        BigInteger.valueOf(getConfig().getLong( "Prestiges." + key + ".cost")),
                        getConfig().getStringList( "Prestiges." + key + ".executecmds"),
                        getConfig().getStringList("Prestiges." + key + ".msg")));
            } catch (NullPointerException e) {
                Bukkit.getLogger().info( "ERROR! Ranks failed to load");
            }
        }
        Bukkit.getLogger().info( "Success! Ranks have been loaded");
    }

    private void autoSaveTask() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                long timeStart = System.currentTimeMillis();
                RankData.save();
                Bukkit.getLogger().info("[BossRankup] Saving all plugin data.");
                long timeToInit = System.currentTimeMillis();
                Bukkit.getLogger().info("[BossRankup] Saving >> " + (timeToInit - timeStart) + " ms.");
            }
        }, 6000L, 6000L);
    }
    private void autoRankTop() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                sorting.sortPrestiges();
                sorting.sortRanks();
            }
        }, 20L, 20L);
    }
}
