package net.htlgkr.lugern.coctracker.models.player;

import net.htlgkr.lugern.coctracker.models.clan.IconUrls;

public class League {
    private int id;
    private String name;
    private IconUrls iconUrls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
