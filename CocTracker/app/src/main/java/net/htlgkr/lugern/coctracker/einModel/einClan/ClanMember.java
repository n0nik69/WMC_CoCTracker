package net.htlgkr.lugern.coctracker.einModel.einClan;

import net.htlgkr.lugern.coctracker.einModel.einPlayer.League;
import net.htlgkr.lugern.coctracker.einModel.playerundclan.BuilderBaseLeague;

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
    private int builderBasTrophies;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public BuilderBaseLeague getBuilderBaseLeague() {
        return builderBaseLeague;
    }

    public void setBuilderBaseLeague(BuilderBaseLeague builderBaseLeague) {
        this.builderBaseLeague = builderBaseLeague;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getTownHallLevel() {
        return townHallLevel;
    }

    public void setTownHallLevel(int townHallLevel) {
        this.townHallLevel = townHallLevel;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }

    public int getClanRank() {
        return clanRank;
    }

    public void setClanRank(int clanRank) {
        this.clanRank = clanRank;
    }

    public int getPreviousClanRank() {
        return previousClanRank;
    }

    public void setPreviousClanRank(int previousClanRank) {
        this.previousClanRank = previousClanRank;
    }

    public int getDonations() {
        return donations;
    }

    public void setDonations(int donations) {
        this.donations = donations;
    }

    public int getDonationsReceived() {
        return donationsReceived;
    }

    public void setDonationsReceived(int donationsReceived) {
        this.donationsReceived = donationsReceived;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public int getBuilderBasTrophies() {
        return builderBasTrophies;
    }

    public void setBuilderBasTrophies(int builderBasTrophies) {
        this.builderBasTrophies = builderBasTrophies;
    }
}
