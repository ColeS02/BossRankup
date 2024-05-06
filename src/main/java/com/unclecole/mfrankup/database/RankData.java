package com.unclecole.mfrankup.database;

import com.unclecole.mfrankup.database.serializer.Serializer;

import java.util.HashMap;

public class RankData {

    public static transient RankData instance = new RankData();

    public static HashMap<String, Integer> rankData = new HashMap<>();
    public static HashMap<String, Integer> prestigeData = new HashMap<>();

    public static void save() {
        new Serializer().save(instance);
    }

    public static void load() {
        new Serializer().load(instance, RankData.class, "rankdata");
    }

}
