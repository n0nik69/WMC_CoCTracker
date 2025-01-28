package net.htlgkr.lugern.coctracker.models.player;

public class Spell {
    private String name;
    private int level;
    private int maxLevel;
    private String village;

    public Spell(String name, int level, int maxLevel, String village) {
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.village = village;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
