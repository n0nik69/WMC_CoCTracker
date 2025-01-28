package net.htlgkr.lugern.coctracker.models.player;

import java.util.ArrayList;
import java.util.List;

public class Player {

    List<Achievment> achievements = new ArrayList<>();
    List<Troop> troops = new ArrayList<>();
    List<Hero> heroes = new ArrayList<>();
    List<HeroEquipment> heroEquipments = new ArrayList<>();
    List<Spell> spells = new ArrayList<>();
    private String tag;
    private String name;
    private int townHallLevel;
    private int townHallWeaponLevel;
    private int expLevel;
    private int trophies;
    private int bestTrophies;
    private int warStars;
    private int attackWins;
    private int defenseWins;
    private int builderHallLevel;
    private int builderBaseTrophies;
    private int bestBuilderBaseTrophies;
    private String role;
    private String warPreference;
    private int donations;
    private int donationsReceived;
    private int clanCapitalContributions;
    private PlayerClan clan;
    private League league;
    private BuilderBaseLeague builderBaseLeague;

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public int getTownHallLevel() {
        return townHallLevel;
    }

    public int getTownHallWeaponLevel() {
        return townHallWeaponLevel;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public int getTrophies() {
        return trophies;
    }

    public int getBestTrophies() {
        return bestTrophies;
    }

    public int getWarStars() {
        return warStars;
    }

    public int getAttackWins() {
        return attackWins;
    }

    public int getDefenseWins() {
        return defenseWins;
    }

    public int getBuilderHallLevel() {
        return builderHallLevel;
    }

    public int getBuilderBaseTrophies() {
        return builderBaseTrophies;
    }

    public int getBestBuilderBaseTrophies() {
        return bestBuilderBaseTrophies;
    }

    public String getRole() {
        return role;
    }

    public String getWarPreference() {
        return warPreference;
    }

    public int getDonations() {
        return donations;
    }

    public int getDonationsReceived() {
        return donationsReceived;
    }

    public int getClanCapitalContributions() {
        return clanCapitalContributions;
    }

    public PlayerClan getClan() {
        return clan;
    }

    public League getLeague() {
        return league;
    }

    public BuilderBaseLeague getBuilderBaseLeague() {
        return builderBaseLeague;
    }

    public List<Achievment> getAchievements() {
        return achievements;
    }

    public List<Troop> getTroops() {
        return troops;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public List<HeroEquipment> getHeroEquipments() {
        return heroEquipments;
    }

    public List<Spell> getSpells() {
        return spells;
    }
}
