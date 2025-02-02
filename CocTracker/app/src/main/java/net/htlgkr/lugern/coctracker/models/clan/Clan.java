package net.htlgkr.lugern.coctracker.models.clan;

import net.htlgkr.lugern.coctracker.models.shared.Label;

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
    private List<ClanMember> memberList;
    private Location location;
    private CapitalLeague capitalLeague;
    private WarLeague warLeague;
    private List<Label> labels;
    private Language chatLanguage;
    private ClanCapital clanCapital;

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public WarFrequency getWarFrequency() {
        return warFrequency;
    }

    public void setWarFrequency(WarFrequency warFrequency) {
        this.warFrequency = warFrequency;
    }


    public List<ClanMember> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<ClanMember> memberList) {
        this.memberList = memberList;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFamilyFriendly() {
        return isFamilyFriendly;
    }

    public void setFamilyFriendly(boolean familyFriendly) {
        isFamilyFriendly = familyFriendly;
    }

    public int getClanLevel() {
        return clanLevel;
    }

    public void setClanLevel(int clanLevel) {
        this.clanLevel = clanLevel;
    }

    public int getClanPoints() {
        return clanPoints;
    }

    public void setClanPoints(int clanPoints) {
        this.clanPoints = clanPoints;
    }

    public int getClanBuilderBasePoints() {
        return clanBuilderBasePoints;
    }

    public void setClanBuilderBasePoints(int clanBuilderBasePoints) {
        this.clanBuilderBasePoints = clanBuilderBasePoints;
    }

    public int getClanCapitalPoints() {
        return clanCapitalPoints;
    }

    public void setClanCapitalPoints(int clanCapitalPoints) {
        this.clanCapitalPoints = clanCapitalPoints;
    }

    public int getRequiredTrophies() {
        return requiredTrophies;
    }

    public void setRequiredTrophies(int requiredTrophies) {
        this.requiredTrophies = requiredTrophies;
    }

    public int getRequiredBuilderBaseTrophies() {
        return requiredBuilderBaseTrophies;
    }

    public void setRequiredBuilderBaseTrophies(int requiredBuilderBaseTrophies) {
        this.requiredBuilderBaseTrophies = requiredBuilderBaseTrophies;
    }

    public int getRequiredTownhallLevel() {
        return requiredTownhallLevel;
    }

    public void setRequiredTownhallLevel(int requiredTownhallLevel) {
        this.requiredTownhallLevel = requiredTownhallLevel;
    }

    public int getWarWinStreak() {
        return warWinStreak;
    }

    public void setWarWinStreak(int warWinStreak) {
        this.warWinStreak = warWinStreak;
    }

    public int getWarWins() {
        return warWins;
    }

    public void setWarWins(int warWins) {
        this.warWins = warWins;
    }

    public int getWarTies() {
        return warTies;
    }

    public void setWarTies(int warTies) {
        this.warTies = warTies;
    }

    public int getWarLosses() {
        return warLosses;
    }

    public void setWarLosses(int warLosses) {
        this.warLosses = warLosses;
    }

    public boolean isWarLogPublic() {
        return isWarLogPublic;
    }

    public void setWarLogPublic(boolean warLogPublic) {
        isWarLogPublic = warLogPublic;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public BadgeUrls getBadgeUrls() {
        return badgeUrls;
    }

    public void setBadgeUrls(BadgeUrls badgeUrls) {
        this.badgeUrls = badgeUrls;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CapitalLeague getCapitalLeague() {
        return capitalLeague;
    }

    public void setCapitalLeague(CapitalLeague capitalLeague) {
        this.capitalLeague = capitalLeague;
    }

    public WarLeague getWarLeague() {
        return warLeague;
    }

    public void setWarLeague(WarLeague warLeague) {
        this.warLeague = warLeague;
    }

    public Language getChatLanguage() {
        return chatLanguage;
    }

    public void setChatLanguage(Language chatLanguage) {
        this.chatLanguage = chatLanguage;
    }

    public ClanCapital getClanCapital() {
        return clanCapital;
    }

    public void setClanCapital(ClanCapital clanCapital) {
        this.clanCapital = clanCapital;
    }
}


