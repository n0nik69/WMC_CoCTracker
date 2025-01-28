package net.htlgkr.lugern.coctracker.models.player;

public class PlayerClan {
    private String tag;
    private String name;
    private int clanLevel;

    public PlayerClan(String tag, String name, int clanLevel) {
        this.tag = tag;
        this.name = name;
        this.clanLevel = clanLevel;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClanLevel() {
        return clanLevel;
    }

    public void setClanLevel(int clanLevel) {
        this.clanLevel = clanLevel;
    }
}
