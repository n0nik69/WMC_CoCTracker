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

    public Village getVillage() {
        return village;
    }

    public boolean isSuperTroopIsActive() {
        return superTroopIsActive;
    }

    public int getPlayerItemImage() {
        return playerItemImage;
    }

    public List<PlayerItemLevel> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<PlayerItemLevel> equipment) {
        this.equipment = equipment;
    }
}
