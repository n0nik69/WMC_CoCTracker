package net.htlgkr.lugern.coctracker.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.list.listModel.AchievmentCard;
import net.htlgkr.lugern.coctracker.models.clan.BadgeUrls;
import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.models.clan.ClanMember;
import net.htlgkr.lugern.coctracker.models.clan.ClanRanking;
import net.htlgkr.lugern.coctracker.models.clan.IconUrls;
import net.htlgkr.lugern.coctracker.models.clan.Location;
import net.htlgkr.lugern.coctracker.models.clan.Role;
import net.htlgkr.lugern.coctracker.models.clan.WarFrequency;
import net.htlgkr.lugern.coctracker.models.player.League;
import net.htlgkr.lugern.coctracker.models.player.Player;
import net.htlgkr.lugern.coctracker.models.player.PlayerAchievmentProgress;
import net.htlgkr.lugern.coctracker.models.player.PlayerRanking;
import net.htlgkr.lugern.coctracker.models.player.Village;
import net.htlgkr.lugern.coctracker.models.shared.BuilderBaseLeague;
import net.htlgkr.lugern.coctracker.models.shared.Label;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MainMainViewModel extends ViewModel {
    private static final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImMxZTY3Nzg2LTkyNGYtNDY4Mi1hYTViLTlhNmI0NGVjYzEzYSIsImlhdCI6MTczODQwNjgxOCwic3ViIjoiZGV2ZWxvcGVyLzgzNjM3MjQ5LTdmZjEtZWRhNC03NWIwLTYzZDE5ZTkxNWM4YSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE0NS40MC40OS44MiJdLCJ0eXBlIjoiY2xpZW50In1dfQ.VolJrLgvOZcd4kpRbQyz_774_PCFod2wHbFEDrYx8USjYun5KpbjRJHBhtB6k6Qzjwb4yUbmG9FsvNkWFDraNw"; // Setze deinen API-Schlüssel hier ein
    private static String API_URL = "https://api.clashofclans.com/v1"; // Spieler-URL
    private final MutableLiveData<Boolean> searchPerTag = new MutableLiveData<>();
    private final MutableLiveData<Boolean> searchPerName = new MutableLiveData<>();
    private final MutableLiveData<Boolean> playerClicked = new MutableLiveData<>();
    public MutableLiveData<ArrayList<AchievmentCard>> observableItemsAchievmentCard;
    public MutableLiveData<ArrayList<ClanMember>> observableItemsClanMember;
    public MutableLiveData<ArrayList<Clan>> observableItemsClan;
    public MutableLiveData<ArrayList<ClanRanking>> observableItemsClanRanking;
    List<PlayerRanking> playerRankings;
    private RequestQueue queue;
    private Gson gson = new Gson();
    private Player player;
    private Clan clan;
    private List<ClanRanking> clanRanking;
    private List<ClanMember> clanMembers;
    private ArrayList<AchievmentCard> achievmentCards;
    private ArrayList<ClanMember> clanCards;
    private ArrayList<Clan> clans;
    private ArrayList<ClanRanking> clanRankings;

    public MainMainViewModel() {
        observableItemsAchievmentCard = new MutableLiveData<>();
        achievmentCards = new ArrayList<>();
        observableItemsClanMember = new MutableLiveData<>();
        clanCards = new ArrayList<>();

        observableItemsClan = new MutableLiveData<>();
        clans = new ArrayList<>();
        observableItemsClanRanking = new MutableLiveData<>();
        clanRankings = new ArrayList<>();

    }

    public static String getApiUrl() {
        return API_URL;
    }

    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    public void setPlayerClicked() {
        playerClicked.setValue(true);
    }

    public LiveData<Boolean> getPlayerClicked() {
        return playerClicked;
    }

    public LiveData<Boolean> isSearchPerTag() {
        return searchPerTag;
    }

    public LiveData<Boolean> isSearchPerName() {
        return searchPerName;
    }

    public void init(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void requestData(HTTPListener<String> listener) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                response -> listener.onSuccess(response.toString()),
                error -> listener.onError(error.getMessage())
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + API_TOKEN);
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public void loadPlayerInfo(String json) {
        TypeToken<Player> typeToken = new TypeToken<>() {
        };
        player = gson.fromJson(json, typeToken);
    }

    public void loadTopPlayers(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray itemsArray = jsonObject.getAsJsonArray("items");

        Type listType = new TypeToken<List<PlayerRanking>>() {
        }.getType();
        playerRankings = gson.fromJson(itemsArray, listType);
    }

    public MutableLiveData<Boolean> getSearchPerTag() {
        return searchPerTag;
    }

    public void setSearchPerTag(boolean value) {
        searchPerTag.setValue(value);
    }

    public MutableLiveData<Boolean> getSearchPerName() {
        return searchPerName;
    }

    public List<PlayerRanking> getPlayerRankings() {
        return playerRankings;
    }

    public void setPlayerRankings(List<PlayerRanking> playerRankings) {
        this.playerRankings = playerRankings;
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public List<ClanRanking> getClanRanking() {
        return clanRanking;
    }

    public void setClanRanking(List<ClanRanking> clanRanking) {
        this.clanRanking = clanRanking;
    }

    public List<ClanMember> getClanMembers() {
        return clanMembers;
    }

    public void setClanMembers(List<ClanMember> clanMembers) {
        this.clanMembers = clanMembers;
    }

    public void loadClanInfo(String json) {
        TypeToken<Clan> typeToken = new TypeToken<>() {
        };
        clan = gson.fromJson(json, typeToken);
        System.out.println();
    }

    public void loadTopClans(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray itemsArray = jsonObject.getAsJsonArray("items");

        Type listType = new TypeToken<List<ClanRanking>>() {
        }.getType();
        List<ClanRanking> clanRankings = gson.fromJson(itemsArray, listType);

    }

    public void loadFoundClanInfo(String json) {
//        ClanMemberList response = gson.fromJson(json, ClanMemberList.class);
//        clanMembers = response.getClanMemberList();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public void loadDataFromJson(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray achievementList = jsonResponse.getJSONArray("achievements");

            ArrayList<AchievmentCard> achievmentCards = new ArrayList<>();

            for (int i = 0; i < achievementList.length(); i++) {
                JSONObject achievement = achievementList.getJSONObject(i);
                AchievmentCard achievmentCard = new AchievmentCard();

                achievmentCard.setName(achievement.getString("name"));
                achievmentCard.setStars(achievement.getInt("stars"));
                achievmentCard.setValue(achievement.getInt("value"));
                achievmentCard.setTarget(achievement.getInt("target"));
                achievmentCard.setInfo(achievement.getString("info"));
                achievmentCard.setCompletionInfo(achievement.optString("completionInfo", ""));
                achievmentCard.setVillage(achievement.getString("village"));

                achievmentCards.add(achievmentCard);
            }

            observableItemsAchievmentCard.postValue(achievmentCards);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<AchievmentCard> getAchievmentCards() {
        return achievmentCards;
    }

    public void setAchievmentCards(ArrayList<AchievmentCard> achievmentCards) {
        this.achievmentCards = achievmentCards;
    }

    public void setPlayerPerClan(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray memberList = jsonResponse.getJSONArray("memberList");

            clanCards.clear();
            for (int i = 0; i < memberList.length(); i++) {
                JSONObject member = memberList.getJSONObject(i);
                ClanMember clanMember = new ClanMember();
                setIfExistsInt(member, "expLevel", clanMember::setExpLevel);
                setIfExistsInt(member, "townHallLevel", clanMember::setTownHallLevel);
                setIfExistsInt(member, "clanRank", clanMember::setClanRank);
                setIfExistsInt(member, "previousClanRank", clanMember::setPreviousClanRank);

                setIfExistsInt(member, "trophies", clanMember::setTrophies);
                setIfExistsInt(member, "builderBaseTrophies", clanMember::setBuilderBaseTrophies);
                setIfExistsInt(member, "donations", clanMember::setDonations);
                setIfExistsInt(member, "donationsReceived", clanMember::setDonationsReceived);
                if (member.has("role"))
                    clanMember.setRole(Role.valueOf(member.optString("role", null)));

                setIfExistsLeague(member, clanMember::setLeague);
                setIfExistsBuilderBaseLeague(member, clanMember::setBuilderBaseLeague);

                setIfExistsString(member, "name", clanMember::setName);
                setIfExistsString(member, "tag", clanMember::setTag);
                List<PlayerAchievmentProgress> achievements = new ArrayList<>();
                List<Label> labels = new ArrayList<>();
                setAchievementListIfExists(member, achievements);
                setLabelListIfExists(member, labels);
                clanCards.add(clanMember);
            }

            observableItemsClanMember.postValue(clanCards);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    //hilfsmethoden shit
    private void setIfExistsInt(JSONObject member, String key, Consumer<Integer> setter) {
        if (member.has(key)) {
            try {
                setter.accept(member.getInt(key));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setIfExistsString(JSONObject member, String key, Consumer<String> setter) {
        if (member.has(key)) {
            try {
                setter.accept(member.getString(key));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setAchievementListIfExists(JSONObject member, List<PlayerAchievmentProgress> achievements) {
        try {
            if (member.has("achievements")) {
                JSONArray array = member.getJSONArray("achievements");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject achievementObject = array.getJSONObject(i);
                    PlayerAchievmentProgress achievementProgress = new PlayerAchievmentProgress();

                    setIfExistsInt(achievementObject, "stars", achievementProgress::setStars);
                    setIfExistsInt(achievementObject, "value", achievementProgress::setValue);
                    setIfExistsString(achievementObject, "name", achievementProgress::setName);
                    setIfExistsInt(achievementObject, "target", achievementProgress::setTarget);
                    setIfExistsString(achievementObject, "completionInfo", achievementProgress::setCompletionInfo);
                    setIfExistsString(achievementObject, "village", value -> achievementProgress.setVillage(Village.valueOf(value)));
                    achievements.add(achievementProgress);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLabelListIfExists(JSONObject member, List<Label> labels) {
        try {
            if (member.has("labels")) {
                JSONArray array = member.getJSONArray("labels");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject labelObject = array.getJSONObject(i);
                    Label label = new Label();
                    setIfExistsString(labelObject, "name", label::setName);
                    setIfExistsInt(labelObject, "id", label::setId);
                    setIfExistsJSONObject(labelObject, label::setIconUrls);
                    labels.add(label);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setIfExistsJSONObject(JSONObject jsonObject, Consumer<IconUrls> setter) {
        try {
            if (jsonObject.has("iconUrls")) {
                JSONObject iconUrlsObject = jsonObject.getJSONObject("iconUrls");
                IconUrls iconUrls = new IconUrls();
                setIfExistsString(iconUrlsObject, "small", iconUrls::setSmall);
                setIfExistsString(iconUrlsObject, "medium", iconUrls::setMedium);
                setIfExistsString(iconUrlsObject, "large", iconUrls::setLarge);
                setter.accept(iconUrls);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setIfExistsLeague(JSONObject member, Consumer<League> setter) {
        if (member.has("league")) {
            try {
                JSONObject leagueObject = member.getJSONObject("league");
                League league = new League();
                setIfExistsInt(leagueObject, "id", league::setId);
                setIfExistsString(leagueObject, "name", league::setName);

                if (leagueObject.has("iconUrls")) {
                    JSONObject iconUrlsObject = leagueObject.getJSONObject("iconUrls");
                    IconUrls iconUrls = new IconUrls();
                    setIfExistsString(iconUrlsObject, "small", iconUrls::setSmall);
                    setIfExistsString(iconUrlsObject, "medium", iconUrls::setMedium);
                    setIfExistsString(iconUrlsObject, "large", iconUrls::setLarge);
                    league.setIconUrls(iconUrls);
                }

                setter.accept(league);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setIfExistsBuilderBaseLeague(JSONObject member, Consumer<BuilderBaseLeague> setter) {
        if (member.has("builderBaseLeague")) {
            try {
                JSONObject builderBaseLeagueObject = member.getJSONObject("builderBaseLeague");
                int id = builderBaseLeagueObject.getInt("id");
                String name = builderBaseLeagueObject.getString("name");

                BuilderBaseLeague builderBaseLeague = new BuilderBaseLeague(id, name);
                setter.accept(builderBaseLeague);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
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
                clan.setType(net.htlgkr.lugern.coctracker.models.clan.Type.valueOf(member.getString("type")));
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

            observableItemsClan.postValue(clans);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadClanDataFromJsonAlt(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray items = jsonResponse.getJSONArray("items");

//            clanCards.clear();

            for (int i = 0; i < items.length(); i++) {
                JSONObject member = items.getJSONObject(i);
                Clan foundClanCard = new Clan();
                foundClanCard.setName(member.getString("name"));
                foundClanCard.setTag(member.getString("tag"));
                foundClanCard.setType(net.htlgkr.lugern.coctracker.models.clan.Type.valueOf(member.getString("type")));
                foundClanCard.setClanLevel(member.getInt("clanLevel"));
                foundClanCard.setClanPoints(member.getInt("clanPoints"));
                foundClanCard.setClanCapitalPoints(member.getInt("clanCapitalPoints"));
                foundClanCard.setClanBuilderBasePoints(member.getInt("clanBuilderBasePoints"));
                foundClanCard.setRequiredTrophies(member.getInt("requiredTrophies"));
                foundClanCard.setWarFrequency(WarFrequency.valueOf(member.getString("warFrequency")));
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

    public void setTopClan(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray items = jsonResponse.getJSONArray("items");

            clanRankings.clear();
            for (int i = 0; i < items.length(); i++) {
                JSONObject member = items.getJSONObject(i);
                ClanRanking clanRanking = new ClanRanking();


//                requestViewModel.getPlayerClicked().observe(this, clicked -> {
//                    if (Boolean.TRUE.equals(clicked)) {
//
//                    }
//                });
                clanRanking.setRank(member.getInt("rank"));
//                clanRanking.setMembers(member.getInt("members"));
                clanRanking.setName(member.getString("name"));
                clanRanking.setTag(member.getString("tag"));
                clanRanking.setClanLevel(member.getInt("clanLevel"));
                clanRanking.setClanPoints(member.getInt("clanPoints"));
                clanRanking.setPreviousRank(member.getInt("previousRank"));
                clanRankings.add(clanRanking);
            }

            observableItemsClanRanking.postValue(clanRankings);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
