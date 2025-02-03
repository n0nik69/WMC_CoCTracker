package net.htlgkr.lugern.coctracker.list.listViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.htlgkr.lugern.coctracker.models.clan.BadgeUrls;
import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.models.clan.Location;
import net.htlgkr.lugern.coctracker.models.clan.Type;
import net.htlgkr.lugern.coctracker.models.clan.WarFrequency;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoundClanViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Clan>> observableItems;
    private ArrayList<Clan> clans;

    public FoundClanViewModel() {
        observableItems = new MutableLiveData<>();
        clans = new ArrayList<>();
    }

    public void loadClanDataFromJson(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray items = jsonResponse.getJSONArray("items");

            clans.clear();

            for (int i = 0; i < items.length(); i++) {
                JSONObject member = items.getJSONObject(i);
                Clan clan = new Clan();
                clan.setName(member.getString("name"));
                clan.setTag(member.getString("tag"));
                clan.setType(Type.valueOf(member.getString("type")));
                clan.setClanLevel(member.getInt("clanLevel"));
                clan.setClanPoints(member.getInt("clanPoints"));
                clan.setClanCapitalPoints(member.getInt("clanCapitalPoints"));
                clan.setClanBuilderBasePoints(member.getInt("clanBuilderBasePoints"));
                clan.setRequiredTrophies(member.getInt("requiredTrophies"));
                clan.setWarFrequency(WarFrequency.valueOf(member.getString("warFrequency")));
                clan.setWarWinStreak(member.getInt("warWinStreak"));
                clan.setWarWins(member.getInt("warWins"));
                clan.setWarLogPublic(member.getBoolean("isWarLogPublic"));
                clan.setMembers(member.getInt("members"));
                clan.setRequiredBuilderBaseTrophies(member.getInt("requiredBuilderBaseTrophies"));
                clan.setRequiredTownhallLevel(member.getInt("requiredTownhallLevel"));


                if (member.has("location")) {
                    JSONObject locationJson = member.getJSONObject("location");
                    Location location = new Location();
                    location.setId(locationJson.getInt("id"));
                    location.setName(locationJson.getString("name"));
                    if (locationJson.has("countryCode") && !locationJson.isNull("countryCode")) {
                        location.setCountryCode(locationJson.getString("countryCode"));
                    } else {
                        location.setCountryCode("N/A");
                    }


                    clan.setLocation(location);
                }

                if (member.has("isFamilyFriendly")) {
                    clan.setFamilyFriendly(member.getBoolean("isFamilyFriendly"));
                }

                if (member.has("badgeUrls")) {
                    JSONObject badgeJson = member.getJSONObject("badgeUrls");
                    BadgeUrls badgeUrls = new BadgeUrls();
                    badgeUrls.setSmall(badgeJson.getString("small"));
                    badgeUrls.setMedium(badgeJson.getString("medium"));
                    badgeUrls.setLarge(badgeJson.getString("large"));
                    clan.setBadgeUrls(badgeUrls);
                }

                clans.add(clan);
            }

            observableItems.postValue(clans);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
