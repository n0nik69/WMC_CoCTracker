package net.htlgkr.lugern.coctracker.models.player;

public class PlayerAchievmentProgress {
    private int stars;
    private int value;
    private String name;
    private int target;
    private String completionInfo;
    private Village village;

    public int getStars() {
        return stars;
    }


    public int getValue() {
        return value;
    }


    public String getName() {
        return name;
    }


    public int getTarget() {
        return target;
    }


    public String getCompletionInfo() {
        return completionInfo;
    }


    public Village getVillage() {
        return village;
    }

}
