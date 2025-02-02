package net.htlgkr.lugern.coctracker.einModel.einClan;

import java.util.List;

public class Label {
    private String name;
    private int id;
    private List<String> iconUrls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getIconUrls() {
        return iconUrls;
    }

    public void setIconUrls(List<String> iconUrls) {
        this.iconUrls = iconUrls;
    }
}
