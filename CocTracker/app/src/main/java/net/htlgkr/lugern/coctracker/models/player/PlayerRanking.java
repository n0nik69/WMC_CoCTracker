package net.htlgkr.lugern.coctracker.models.player;

import net.htlgkr.lugern.coctracker.models.shared.BuilderBaseLeague;

public class PlayerRanking {
    League league;
    BuilderBaseLeague builderBaseLeague;
    PlayerRankingClan playerRankingClan;
    private int attackWins;
    private int defenseWins;
    private String tag;
    private String name;
    private int expLevel;
    private int rank;
    private int previousRank;
    private int trophies;
    private int builderBaseTrophies;

    public int getBuilderBaseTrophies() {
        return builderBaseTrophies;
    }


    public int getAttackWins() {
        return attackWins;
    }


    public int getDefenseWins() {
        return defenseWins;
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

    public int getExpLevel() {
        return expLevel;
    }

    public int getRank() {
        return rank;
    }

    public int getTrophies() {
        return trophies;
    }

}
