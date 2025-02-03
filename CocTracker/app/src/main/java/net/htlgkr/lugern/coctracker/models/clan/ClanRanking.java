package net.htlgkr.lugern.coctracker.models.clan;

public class ClanRanking {
    private int clanPoints;
    private int clanLevel;
    private Location location;
    private int members;
    private String tag;
    private String name;
    private int rank;
    private int previousRank;
    private BadgeUrls badgeUrls;

    public int getClanPoints() {
        return clanPoints;
    }


    public int getClanLevel() {
        return clanLevel;
    }

    public Location getLocation() {
        return location;
    }


    public int getMembers() {
        return members;
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

    public int getRank() {
        return rank;
    }


    public int getPreviousRank() {
        return previousRank;
    }


    public BadgeUrls getBadgeUrls() {
        return badgeUrls;
    }

}
