package com.unclecole.mfrankup.objects;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Ranks {

    private String name;
    private long cost;
    private List<String> commands;
    private List<String> messages;

    public Ranks(String name, long cost, List<String> commands, List<String> messages) {
        this.name = name;
        this.cost = cost;
        this.commands = commands;
        this.messages = messages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public long getCost() {
        return cost;
    }

    public List<String> getCommands() {
        return commands;
    }

    public List<String> getMessages() { return messages; }
}
