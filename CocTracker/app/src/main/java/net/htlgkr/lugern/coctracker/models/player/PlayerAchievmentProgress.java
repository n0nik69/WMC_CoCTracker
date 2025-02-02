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

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getCompletionInfo() {
        return completionInfo;
    }

    public void setCompletionInfo(String completionInfo) {
        this.completionInfo = completionInfo;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }
}
