package net.htlgkr.lugern.coctracker.einModel.einPlayer;

public class Achievment {
    private String name;
    private int stars;
    private int value;
    private int target;
    private String info;
    private String completionInfo;
    private String village;

    public Achievment(String name, int stars, int value, int target, String info, String completionInfo, String village) {
        this.name = name;
        this.stars = stars;
        this.value = value;
        this.target = target;
        this.info = info;
        this.completionInfo = completionInfo;
        this.village = village;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
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

    public void setCompletionInfo(String completionInfo) {
        this.completionInfo = completionInfo;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
