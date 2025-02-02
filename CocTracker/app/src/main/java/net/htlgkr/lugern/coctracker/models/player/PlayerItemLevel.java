package net.htlgkr.lugern.coctracker.models.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerItemLevel {
    List<PlayerItemLevel> playerItemLevels = new ArrayList<>();
    private int level;
    private String name;
    private int maxLevel;
    private Village village;
    private boolean superTroopIsActive;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public boolean isSuperTroopIsActive() {
        return superTroopIsActive;
    }

    public void setSuperTroopIsActive(boolean superTroopIsActive) {
        this.superTroopIsActive = superTroopIsActive;
    }

    public List<PlayerItemLevel> getPlayerItemLevels() {
        return playerItemLevels;
    }

    public void setPlayerItemLevels(List<PlayerItemLevel> playerItemLevels) {
        this.playerItemLevels = playerItemLevels;
    }
}
