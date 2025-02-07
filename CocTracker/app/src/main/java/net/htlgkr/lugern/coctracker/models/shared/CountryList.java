package net.htlgkr.lugern.coctracker.models.shared;

import java.util.ArrayList;
import java.util.List;

public class CountryList {

    public static List<Country> getCountries() {
        List<Country> countryList = new ArrayList<>(); //sind hardcodiert, weil die Liste, welche ich von der API bekomme zu lange ist und ChatGPT diese nicht verarbeiten kann :)LVVRPUJ08
        countryList.add(new Country(32000120, "Italy", true, "IT"));
        countryList.add(new Country(32000022, "Austria", true, "AT"));
        countryList.add(new Country(32000094, "Germany", true, "DE"));
        countryList.add(new Country(32000249, "United States", true, "US"));
        countryList.add(new Country(32000193, "Russia", true, "RU"));
        countryList.add(new Country(32000056, "China", true, "CN"));
        countryList.add(new Country(32000113, "India", true, "IN"));
        countryList.add(new Country(32000114, "Indonesia", true, "ID"));
        countryList.add(new Country(32000178, "Pakistan", true, "PK"));
        countryList.add(new Country(32000038, "Brazil", true, "BR"));
        countryList.add(new Country(32000153, "Mexico", true, "MX"));
        countryList.add(new Country(32000187, "Poland", true, "PL"));
        countryList.add(new Country(32000248, "United Kingdom", true, "GB"));
        countryList.add(new Country(32000218, "Spain", true, "ES"));
        countryList.add(new Country(32000115, "Iran", true, "IR"));
        countryList.add(new Country(32000116, "Iraq", true, "IQ"));
        countryList.add(new Country(32000117, "Ireland", true, "IE"));
        countryList.add(new Country(32000119, "Israel", true, "IL"));
        countryList.add(new Country(32000087, "France", true, "FR"));
        return countryList;
    }
}
