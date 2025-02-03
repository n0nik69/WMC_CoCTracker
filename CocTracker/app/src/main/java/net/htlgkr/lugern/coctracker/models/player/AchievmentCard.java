package net.htlgkr.lugern.coctracker.models.player;

public class AchievmentCard {
    private String name;
    private int stars;
    private int value;
    private int target;
    private String info;
    private String completionInfo;
    private String village;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }


    public int getValue() {
        return value;
    }


    public int getTarget() {
        return target;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCompletionInfo() {
        return completionInfo;
    }


    public String getVillage() {
        return village;
    }

}
