package net.htlgkr.lugern.coctracker.models.player;

import java.util.ArrayList;

public class PlayerItemLevel {
    ArrayList<PlayerItemLevel> playerItemLevels = new ArrayList<>();
    private int level;
    private String name;
    private int maxLevel;
    private Village village;
    private boolean superTroopIsActive;
    private int playerItemImage;

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


    public ArrayList<PlayerItemLevel> getPlayerItemLevels() {
        return playerItemLevels;
    }

    public int getPlayerItemImage() {
        return playerItemImage;
    }
}
