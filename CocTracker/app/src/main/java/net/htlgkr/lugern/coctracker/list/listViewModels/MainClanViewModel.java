package net.htlgkr.lugern.coctracker.list.listViewModels;

import androidx.lifecycle.ViewModel;

import net.htlgkr.lugern.coctracker.einModel.einClan.Clan;
import net.htlgkr.lugern.coctracker.einModel.einClan.Location;
import net.htlgkr.lugern.coctracker.models.other.BadgeUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainClanViewModel extends ViewModel {


    public void loadDataFromJson(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray items = jsonResponse.getJSONArray("items");

//            clanCards.clear();

            for (int i = 0; i < items.length(); i++) {
                JSONObject member = items.getJSONObject(i);
                Clan foundClanCard = new Clan();
                foundClanCard.setName(member.getString("name"));
                foundClanCard.setTag(member.getString("tag"));
                foundClanCard.setType(member.getString("type"));
                foundClanCard.setClanLevel(member.getInt("clanLevel"));
                foundClanCard.setClanPoints(member.getInt("clanPoints"));
                foundClanCard.setClanCapitalPoints(member.getInt("clanCapitalPoints"));
                foundClanCard.setClanBuilderBasePoints(member.getInt("clanBuilderBasePoints"));
                foundClanCard.setRequiredTrophies(member.getInt("requiredTrophies"));
                foundClanCard.setWarFrequency(member.getString("warFrequency"));
                foundClanCard.setWarWinStreak(member.getInt("warWinStreak"));
                foundClanCard.setWarWins(member.getInt("warWins"));
                foundClanCard.setWarLogPublic(member.getBoolean("isWarLogPublic"));
                foundClanCard.setMembers(member.getInt("members"));
                foundClanCard.setRequiredBuilderBaseTrophies(member.getInt("requiredBuilderBaseTrophies"));
                foundClanCard.setRequiredTownhallLevel(member.getInt("requiredTownhallLevel"));


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


                    foundClanCard.setLocation(location);
                }

                if (member.has("isFamilyFriendly")) {
                    foundClanCard.setFamilyFriendly(member.getBoolean("isFamilyFriendly"));
                }

                if (member.has("badgeUrls")) {
                    JSONObject badgeJson = member.getJSONObject("badgeUrls");
                    BadgeUrls badgeUrls = new BadgeUrls();
                    badgeUrls.setSmall(badgeJson.getString("small"));
                    badgeUrls.setMedium(badgeJson.getString("medium"));
                    badgeUrls.setLarge(badgeJson.getString("large"));
                    foundClanCard.setBadgeUrls(badgeUrls);
                }

//                clanCards.add(foundClanCard);
            }

//            observableItems.postValue(clanCards);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
