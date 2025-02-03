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
import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.models.clan.ClanMember;
import net.htlgkr.lugern.coctracker.models.clan.ClanRanking;
import net.htlgkr.lugern.coctracker.models.player.AchievmentCard;
import net.htlgkr.lugern.coctracker.models.player.Player;
import net.htlgkr.lugern.coctracker.models.player.PlayerRanking;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicViewModel extends ViewModel {
    private static final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImMxZTY3Nzg2LTkyNGYtNDY4Mi1hYTViLTlhNmI0NGVjYzEzYSIsImlhdCI6MTczODQwNjgxOCwic3ViIjoiZGV2ZWxvcGVyLzgzNjM3MjQ5LTdmZjEtZWRhNC03NWIwLTYzZDE5ZTkxNWM4YSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE0NS40MC40OS44MiJdLCJ0eXBlIjoiY2xpZW50In1dfQ.VolJrLgvOZcd4kpRbQyz_774_PCFod2wHbFEDrYx8USjYun5KpbjRJHBhtB6k6Qzjwb4yUbmG9FsvNkWFDraNw"; // Setze deinen API-Schlüssel hier ein
    private static String API_URL = "https://api.clashofclans.com/v1";
    public MutableLiveData<ArrayList<AchievmentCard>> observableItemsAchievmentCard;
    public MutableLiveData<ArrayList<ClanMember>> observableItemsClanMember;
    public MutableLiveData<ArrayList<Clan>> observableItemsClan;
    public MutableLiveData<ArrayList<ClanRanking>> observableItemsClanRanking;
    public MutableLiveData<ArrayList<Clan>> observableItemsFoundClans;
    public MutableLiveData<ArrayList<PlayerRanking>> observableItemsPlayerRanking;
    private MutableLiveData<Boolean> searchPerTag = new MutableLiveData<>();
    private MutableLiveData<Boolean> showTopPlayersList = new MutableLiveData<>();
    private MutableLiveData<Boolean> showTopClansList = new MutableLiveData<>();
    private MutableLiveData<Boolean> searchPerName = new MutableLiveData<>();
    private RequestQueue queue;
    private Gson gson;
    private Player player;
    private Clan clan;
    private ArrayList<ClanRanking> clanRankings;
    private ArrayList<PlayerRanking> playerRankings;
    private ArrayList<Clan> foundClans;

    public LogicViewModel() {
        observableItemsAchievmentCard = new MutableLiveData<>();
        observableItemsFoundClans = new MutableLiveData<>();
        playerRankings = new ArrayList<>();
        observableItemsPlayerRanking = new MutableLiveData<>();
        observableItemsClanMember = new MutableLiveData<>();
        gson = new Gson();
        observableItemsClan = new MutableLiveData<>();
        foundClans = new ArrayList<>();
        observableItemsClanRanking = new MutableLiveData<>();
        clanRankings = new ArrayList<>();
    }


    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    public MutableLiveData<Boolean> getSearchPerTag() {
        return searchPerTag;
    }

    public void setSearchPerTag(boolean searchPerTag) {
        this.searchPerTag.setValue(searchPerTag);
    }

    public MutableLiveData<Boolean> getSearchPerName() {
        return searchPerName;
    }

    public void setSearchPerName(boolean searchPerName) {
        this.searchPerName.setValue(searchPerName);
    }

    public LiveData<Boolean> getShowTopClansList() {
        return showTopClansList;
    }

    public void setShowTopClansList(boolean showTopClansList) {
        this.showTopClansList.setValue(showTopClansList);
    }

    public void setPlayerClicked() {
        showTopPlayersList.setValue(true);
    }

    public LiveData<Boolean> getShowTopPlayersList() {
        return showTopPlayersList;
    }

    public void setShowTopPlayersList(boolean showTopPlayersList) {
        this.showTopPlayersList.setValue(showTopPlayersList);
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

    public void loadPlayerFromJson(String json) {
        TypeToken<Player> typeToken = new TypeToken<>() {
        };
        player = gson.fromJson(json, typeToken);
    }

    public void loadTopPlayerFromJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray itemsArray = jsonObject.getAsJsonArray("items");

        Type listType = new TypeToken<List<PlayerRanking>>() {
        }.getType();

        playerRankings = gson.fromJson(itemsArray, listType);
        observableItemsPlayerRanking.postValue(playerRankings);
    }

    public List<PlayerRanking> getPlayerRankings() {
        return playerRankings;
    }

    public void loadClanFromJson(String json) {
        TypeToken<Clan> typeToken = new TypeToken<>() {
        };
        clan = gson.fromJson(json, typeToken);
        observableItemsClanMember.postValue(clan.getMemberList());
        System.out.println();
    }

    public void loadTopClansFromJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray itemsArray = jsonObject.getAsJsonArray("items");
        Type listType = new TypeToken<List<ClanRanking>>() {
        }.getType();
        clanRankings = gson.fromJson(itemsArray, listType);
        observableItemsClanRanking.postValue(clanRankings);
    }

    public void loadFoundClansFromJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray itemsArray = jsonObject.getAsJsonArray("items");
        Type listType = new TypeToken<List<Clan>>() {
        }.getType();

        List<Clan> newFoundClans = gson.fromJson(itemsArray, listType);
        foundClans.clear();
        foundClans.addAll(newFoundClans);

        observableItemsFoundClans.postValue(foundClans);
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
}
