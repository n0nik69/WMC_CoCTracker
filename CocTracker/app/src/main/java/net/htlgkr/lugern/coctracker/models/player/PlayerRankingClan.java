package net.htlgkr.lugern.coctracker.models.player;

import net.htlgkr.lugern.coctracker.models.clan.BadgeUrls;

public class PlayerRankingClan {
    private String name;
    private String tag;
    private BadgeUrls badgeUrls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public BadgeUrls getBadgeUrls() {
        return badgeUrls;
    }

    public void setBadgeUrls(BadgeUrls badgeUrls) {
        this.badgeUrls = badgeUrls;
    }
}
