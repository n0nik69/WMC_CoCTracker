package net.htlgkr.lugern.coctracker.models.player;

import java.util.ArrayList;
import java.util.List;

public class Player {

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
    private Clan clan;
    private League league;
    private BuilderBaseLeague builderBaseLeague;
    List<Achievment> achievements = new ArrayList<>();
    List<Troop> troops = new ArrayList<>();
    List<Hero> heroes = new ArrayList<>();
    List<HeroEquipment> heroEquipments = new ArrayList<>();
    List<Spell> spells = new ArrayList<>();

}
