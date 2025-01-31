package net.htlgkr.lugern.coctracker.models.clan;

import net.htlgkr.lugern.coctracker.models.other.BadgeUrls;
import net.htlgkr.lugern.coctracker.models.player.Player;

import java.util.ArrayList;
import java.util.List;


public class Clan {
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
    private String warFrequency;
    private int warWinStreak;
    private int warWins;
    private boolean isWarLogPublic;
    private int members;
    private int requiredBuilderBaseTrophies;
    private int requiredTownhallLevel;
    private BadgeUrls badgeUrls;
    private List<Player> memberList = new ArrayList<>();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getWarFrequency() {
        return warFrequency;
    }

    public void setWarFrequency(String warFrequency) {
        this.warFrequency = warFrequency;
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

    public BadgeUrls getBadgeUrls() {
        return badgeUrls;
    }

    public void setBadgeUrls(BadgeUrls badgeUrls) {
        this.badgeUrls = badgeUrls;
    }

    public List<Player> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Player> memberList) {
        this.memberList = memberList;
    }
}
