package net.htlgkr.lugern.coctracker.models.player;

public class PlayerRanking {
    League league;
    PlayerRankingClan playerRankingClan;
    private int attackWins;
    private int defenseWins;
    private String tag;
    private String name;
    private int expLevel;
    private int rank;
    private int previousRank;
    private int trophies;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public PlayerRankingClan getPlayerRankingClan() {
        return playerRankingClan;
    }

    public void setPlayerRankingClan(PlayerRankingClan playerRankingClan) {
        this.playerRankingClan = playerRankingClan;
    }

    public int getAttackWins() {
        return attackWins;
    }

    public void setAttackWins(int attackWins) {
        this.attackWins = attackWins;
    }

    public int getDefenseWins() {
        return defenseWins;
    }

    public void setDefenseWins(int defenseWins) {
        this.defenseWins = defenseWins;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPreviousRank() {
        return previousRank;
    }

    public void setPreviousRank(int previousRank) {
        this.previousRank = previousRank;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }
}
