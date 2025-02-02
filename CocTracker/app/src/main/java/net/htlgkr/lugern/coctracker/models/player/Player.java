package net.htlgkr.lugern.coctracker.models.player;

import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.models.shared.BuilderBaseLeague;
import net.htlgkr.lugern.coctracker.models.shared.Label;

import java.util.List;

public class Player {
    private String tag;
    private String name;
    private int expLevel;
    private int trophies;
    private int bestTrophies;
    private int warStars;
    private int attackWins;
    private int defenseWins;
    private int townHallLevel;
    private int townHallWeaponLevel;
    private int builderHallLevel;
    private int builderBaseTrophies;
    private int bestBuilderBaseTrophies;
    private String role;
    private String warPreference;
    private int donations;
    private int donationsReceived;
    private int clanCapitalContributions;
    private Clan clan;
    private League league;
    private BuilderBaseLeague builderBaseLeague;
    private PlayerHouse playerHouse;
    private LegendStatistics legendStatistics;
    private List<PlayerItemLevel> troops;
    private List<PlayerItemLevel> heroes;
    private List<PlayerItemLevel> heroEquipments;
    private List<PlayerItemLevel> spells;
    private List<PlayerAchievmentProgress> achievements;
    private List<Label> labels;

    public List<PlayerAchievmentProgress> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<PlayerAchievmentProgress> achievements) {
        this.achievements = achievements;
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

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public int getBestTrophies() {
        return bestTrophies;
    }

    public void setBestTrophies(int bestTrophies) {
        this.bestTrophies = bestTrophies;
    }

    public int getWarStars() {
        return warStars;
    }

    public void setWarStars(int warStars) {
        this.warStars = warStars;
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

    public int getTownHallLevel() {
        return townHallLevel;
    }

    public void setTownHallLevel(int townHallLevel) {
        this.townHallLevel = townHallLevel;
    }

    public int getTownHallWeaponLevel() {
        return townHallWeaponLevel;
    }

    public void setTownHallWeaponLevel(int townHallWeaponLevel) {
        this.townHallWeaponLevel = townHallWeaponLevel;
    }

    public int getBuilderHallLevel() {
        return builderHallLevel;
    }

    public void setBuilderHallLevel(int builderHallLevel) {
        this.builderHallLevel = builderHallLevel;
    }

    public int getBuilderBaseTrophies() {
        return builderBaseTrophies;
    }

    public void setBuilderBaseTrophies(int builderBaseTrophies) {
        this.builderBaseTrophies = builderBaseTrophies;
    }

    public int getBestBuilderBaseTrophies() {
        return bestBuilderBaseTrophies;
    }

    public void setBestBuilderBaseTrophies(int bestBuilderBaseTrophies) {
        this.bestBuilderBaseTrophies = bestBuilderBaseTrophies;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getWarPreference() {
        return warPreference;
    }

    public void setWarPreference(String warPreference) {
        this.warPreference = warPreference;
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

    public int getClanCapitalContributions() {
        return clanCapitalContributions;
    }

    public void setClanCapitalContributions(int clanCapitalContributions) {
        this.clanCapitalContributions = clanCapitalContributions;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

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

    public PlayerHouse getPlayerHouse() {
        return playerHouse;
    }

    public void setPlayerHouse(PlayerHouse playerHouse) {
        this.playerHouse = playerHouse;
    }

    public LegendStatistics getLegendStatistics() {
        return legendStatistics;
    }

    public void setLegendStatistics(LegendStatistics legendStatistics) {
        this.legendStatistics = legendStatistics;
    }

    public List<PlayerItemLevel> getTroops() {
        return troops;
    }

    public void setTroops(List<PlayerItemLevel> troops) {
        this.troops = troops;
    }

    public List<PlayerItemLevel> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<PlayerItemLevel> heroes) {
        this.heroes = heroes;
    }

    public List<PlayerItemLevel> getHeroEquipments() {
        return heroEquipments;
    }

    public void setHeroEquipments(List<PlayerItemLevel> heroEquipments) {
        this.heroEquipments = heroEquipments;
    }

    public List<PlayerItemLevel> getSpells() {
        return spells;
    }

    public void setSpells(List<PlayerItemLevel> spells) {
        this.spells = spells;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}
