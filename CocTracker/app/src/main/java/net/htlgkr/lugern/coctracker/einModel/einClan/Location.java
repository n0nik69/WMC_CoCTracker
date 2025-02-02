package net.htlgkr.lugern.coctracker.einModel.einClan;

public class Location {
    private int id;
    private String name;
    private String countryCode;
    private String localizedName;
    private boolean isCountry;

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public boolean isCountry() {
        return isCountry;
    }

    public void setCountry(boolean country) {
        isCountry = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
