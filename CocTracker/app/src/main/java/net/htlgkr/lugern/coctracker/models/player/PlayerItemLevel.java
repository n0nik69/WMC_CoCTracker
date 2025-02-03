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


    public String getName() {
        return name;
    }


    public int getMaxLevel() {
        return maxLevel;
    }


    public Village getVillage() {
        return village;
    }


    public boolean isSuperTroopIsActive() {
        return superTroopIsActive;
    }


    public List<PlayerItemLevel> getPlayerItemLevels() {
        return playerItemLevels;
    }

}
