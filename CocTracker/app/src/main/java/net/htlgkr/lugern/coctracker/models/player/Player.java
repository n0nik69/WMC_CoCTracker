package net.htlgkr.lugern.coctracker.models.player;

import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.models.shared.BuilderBaseLeague;
import net.htlgkr.lugern.coctracker.models.shared.Label;

import java.util.ArrayList;
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
    private ArrayList<PlayerItemLevel> troops;
    private ArrayList<PlayerItemLevel> heroes;
    private ArrayList<PlayerItemLevel> heroEquipments;
    private ArrayList<PlayerItemLevel> spells;
    private ArrayList<PlayerAchievmentProgress> achievements;
    private ArrayList<Label> labels;

    public ArrayList<PlayerAchievmentProgress> getAchievements() {
        return achievements;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
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

    public int getTownHallLevel() {
        return townHallLevel;
    }

    public int getTownHallWeaponLevel() {
        return townHallWeaponLevel;
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

    public void setWarPreference(String warPreference) {
        this.warPreference = warPreference;
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

    public Clan getClan() {
        return clan;
    }

    public League getLeague() {
        return league;
    }

    public BuilderBaseLeague getBuilderBaseLeague() {
        return builderBaseLeague;
    }

    public PlayerHouse getPlayerHouse() {
        return playerHouse;
    }

    public LegendStatistics getLegendStatistics() {
        return legendStatistics;
    }

    public ArrayList<PlayerItemLevel> getTroops() {
        return troops;
    }

    public List<PlayerItemLevel> getHeroes() {
        return heroes;
    }

    public List<PlayerItemLevel> getHeroEquipments() {
        return heroEquipments;
    }

    public List<PlayerItemLevel> getSpells() {
        return spells;
    }

    public List<Label> getLabels() {
        return labels;
    }
}
