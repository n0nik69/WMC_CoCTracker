package net.htlgkr.lugern.coctracker.models.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerItemLevel {
    private int level;
    private String name;
    private int maxLevel;
    private Village village;
    private boolean superTroopIsActive;
    private int playerItemImage;
    private ArrayList<PlayerItemLevel> equipment = new ArrayList<>();

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public List<PlayerItemLevel> getEquipment() {
        return equipment;
    }

}
