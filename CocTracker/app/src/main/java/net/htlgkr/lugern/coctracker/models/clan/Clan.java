package net.htlgkr.lugern.coctracker.models.clan;

import net.htlgkr.lugern.coctracker.models.shared.Label;

import java.util.ArrayList;
import java.util.List;

public class Clan {
    private String tag;
    private String name;
    private Type type;
    private String description;
    private boolean isFamilyFriendly;
    private int clanLevel;
    private int clanPoints;
    private int clanBuilderBasePoints;
    private int clanCapitalPoints;
    private int requiredTrophies;
    private int requiredBuilderBaseTrophies;
    private int requiredTownhallLevel;
    private WarFrequency warFrequency;
    private int warWinStreak;
    private int warWins;
    private int warTies;
    private int warLosses;
    private boolean isWarLogPublic;
    private int members;
    private BadgeUrls badgeUrls;
    private ArrayList<ClanMember> memberList;
    private Location location;
    private CapitalLeague capitalLeague;
    private WarLeague warLeague;
    private List<Label> labels;
    private Language chatLanguage;
    private ClanCapital clanCapital;

    public int getClanPoints() {
        return clanPoints;
    }

    public ArrayList<ClanMember> getMemberList() {
        return memberList;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public Type getType() {
        return type;
    }

    public WarFrequency getWarFrequency() {
        return warFrequency;
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

    public String getDescription() {
        return description;
    }

    public boolean isFamilyFriendly() {
        return isFamilyFriendly;
    }

    public int getClanLevel() {
        return clanLevel;
    }

    public int getRequiredTrophies() {
        return requiredTrophies;
    }

    public int getRequiredTownhallLevel() {
        return requiredTownhallLevel;
    }

    public int getMembers() {
        return members;
    }

    public BadgeUrls getBadgeUrls() {
        return badgeUrls;
    }

    public Location getLocation() {
        return location;
    }
}


