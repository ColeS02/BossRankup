package com.unclecole.mfrankup.database.serializer;

import com.unclecole.mfrankup.MFRankup;

public class Serializer {


    /**
     * Saves your class to a .json file.
     */
    public void save(Object instance) {
        MFRankup.getPersist().save(instance);
    }

    /**
     * Loads your class from a json file
     *
   */
    public <T> T load(T def, Class<T> clazz, String name) {
        return MFRankup.getPersist().loadOrSaveDefault(def, clazz, name);
    }



}
