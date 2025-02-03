package net.htlgkr.lugern.coctracker.models.clan;

import net.htlgkr.lugern.coctracker.models.player.League;
import net.htlgkr.lugern.coctracker.models.shared.BuilderBaseLeague;

public class ClanMember {
    League league;
    BuilderBaseLeague builderBaseLeague;
    private String tag;
    private String name;
    private Role role;
    private int townHallLevel;
    private int expLevel;
    private int clanRank;
    private int previousClanRank;
    private int donations;
    private int donationsReceived;
    private int trophies;
    private int builderBaseTrophies;

    public League getLeague() {
        return league;
    }

    public BuilderBaseLeague getBuilderBaseLeague() {
        return builderBaseLeague;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }


    public int getTownHallLevel() {
        return townHallLevel;
    }


    public int getExpLevel() {
        return expLevel;
    }


    public int getClanRank() {
        return clanRank;
    }

    public int getPreviousClanRank() {
        return previousClanRank;
    }


    public int getDonations() {
        return donations;
    }

    public int getDonationsReceived() {
        return donationsReceived;
    }


    public int getTrophies() {
        return trophies;
    }

    public int getBuilderBaseTrophies() {
        return builderBaseTrophies;
    }


}
