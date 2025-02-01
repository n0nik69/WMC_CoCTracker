package net.htlgkr.lugern.coctracker.einModel.einClan;

import net.htlgkr.lugern.coctracker.models.clan.Location;
import net.htlgkr.lugern.coctracker.models.other.BadgeUrls;
import net.htlgkr.lugern.coctracker.models.player.Player;

import java.util.List;

public class MainClan {
    private String tag;
    private String name;
    private String type;
    private String description;
    private boolean isFamilyFriendly;
    private int clanLevel;
    private int clanPoints;
    private int clanBuilderBasePoints;
    private int clanCapitalPoints;
    private int requiredTrophies;
    private int requiredBuilderBaseTrophies;
    private int requiredTownhallLevel;
    private String warFrequency;
    private int warWinStreak;
    private int warWins;
    private int warTies;
    private int warLosses;
    private boolean isWarLogPublic;
    private int members;
    private BadgeUrls badgeUrls;
    private List<Player> memberList;
    private Location location;
    private CapitalLeague capitalLeague;
    private WarLeague warLeague;
    private List<Label> labels;
    private Language chatLanguage;
    private ClanCapital clanCapital;
}


