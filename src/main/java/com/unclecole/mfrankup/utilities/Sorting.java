package com.unclecole.mfrankup.utilities;

import com.unclecole.mfrankup.database.RankData;
import lombok.Getter;

import java.util.*;

public class Sorting {

    @Getter private String firstRank;
    @Getter private String secondRank;
    @Getter private String thirdRank;
    @Getter private String fourthRank;
    @Getter private String fifthRank;

    @Getter private String firstPrestige;
    @Getter private String secondPrestige;
    @Getter private String thirdPrestige;
    @Getter private String fourthPrestige;
    @Getter private String fifthPrestige;

    public void sortRanks()
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<>(RankData.rankData.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        Collections.reverse(list);

        // put data from sorted list to hashmap
        if(list.size() >= 1) firstRank = list.get(0).getKey();
        if(list.size() >= 2) secondRank = list.get(1).getKey();
        if(list.size() >= 3) thirdRank = list.get(2).getKey();
        if(list.size() >= 4) fourthRank = list.get(3).getKey();
        if(list.size() >= 5) fifthRank = list.get(4).getKey();
        /*for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        return temp;*/
    }

    public void sortPrestiges()
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<>(RankData.prestigeData.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        Collections.reverse(list);

        // put data from sorted list to hashmap
        if(list.size() >= 1) firstPrestige = list.get(0).getKey();
        if(list.size() >= 2) secondPrestige = list.get(1).getKey();
        if(list.size() >= 3) thirdPrestige = list.get(2).getKey();
        if(list.size() >= 4) fourthPrestige = list.get(3).getKey();
        if(list.size() >= 5) fifthPrestige = list.get(4).getKey();
        /*for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }

        return temp;*/
    }
}
