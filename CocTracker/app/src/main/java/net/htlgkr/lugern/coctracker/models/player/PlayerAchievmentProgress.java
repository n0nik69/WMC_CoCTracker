package net.htlgkr.lugern.coctracker.models.player;

public class PlayerAchievmentProgress {
    private int stars;
    private int value;
    private String name;
    private int target;
    private String completionInfo;
    private Village village;
    private String info;

    public int getStars() {
        return stars;
    }

    public String getName() {
        return name;
    }

    public String getCompletionInfo() {
        return completionInfo;
    }

    public String getInfo() {
        return info;
    }
}
