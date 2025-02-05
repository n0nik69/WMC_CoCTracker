package net.htlgkr.lugern.coctracker.models.shared;

public class Country {
    int id;
    String name;
    boolean isCountry;
    String countryCode;

    public Country(int id, String name, boolean isCountry, String countryCode) {
        this.id = id;
        this.name = name;
        this.isCountry = isCountry;
        this.countryCode = countryCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCountry() {
        return isCountry;
    }

    public String getCountryCode() {
        return countryCode;
    }
}