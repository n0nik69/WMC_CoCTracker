package net.htlgkr.lugern.coctracker.viewmodels;

import android.content.Context;

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
import net.htlgkr.lugern.coctracker.models.player.Player;
import net.htlgkr.lugern.coctracker.models.player.PlayerAchievmentProgress;
import net.htlgkr.lugern.coctracker.models.player.PlayerItemLevel;
import net.htlgkr.lugern.coctracker.models.player.PlayerRanking;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicViewModel extends ViewModel {
    private static final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6Ijc1YWI0MDc2LTU3YjYtNGUzNy1iODJlLTUwZTEwNDU2NDk3OCIsImlhdCI6MTczODc3MzU5NSwic3ViIjoiZGV2ZWxvcGVyLzgzNjM3MjQ5LTdmZjEtZWRhNC03NWIwLTYzZDE5ZTkxNWM4YSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE0NS40MC40OS44MiJdLCJ0eXBlIjoiY2xpZW50In1dfQ.N6XCW1-TBaAEH49TL9sgg36oHm1lCDzrx2XTU5NdopjlD2U7-aIQ2QoLcK8BwHWfYwUO99vEkF6QGmrD6y74wQ"; // Setze deinen API-Schlüssel hier ein
    private static String API_URL = "https://api.clashofclans.com/v1";
    public MutableLiveData<ArrayList<ClanMember>> observableItemsClanMember;
    public MutableLiveData<ArrayList<Clan>> observableItemsClan;
    public MutableLiveData<ArrayList<ClanRanking>> observableItemsClanRanking;
    public MutableLiveData<ArrayList<Clan>> observableItemsFoundClans;
    public MutableLiveData<ArrayList<PlayerRanking>> observableItemsPlayerRanking;
    public MutableLiveData<ArrayList<PlayerItemLevel>> observableItemsPlayerSpells;
    public MutableLiveData<ArrayList<PlayerItemLevel>> observableItemsPlayerHeroes;
    public MutableLiveData<ArrayList<PlayerAchievmentProgress>> observableItemsPlayerAchievments;
    public MutableLiveData<ArrayList<PlayerItemLevel>> obserVableItemsPlayerTroops;
    //    public MutableLiveData<ArrayList<Troop>>

    private RequestQueue queue;
    private Gson gson;
    private Player player;
    private Clan clan;
    private ArrayList<ClanRanking> clanRankings;
    private ArrayList<PlayerRanking> playerRankings;
    private ArrayList<Clan> foundClans;

    public LogicViewModel() {
        observableItemsPlayerAchievments = new MutableLiveData<>();
        observableItemsPlayerHeroes = new MutableLiveData<>();
        observableItemsPlayerSpells = new MutableLiveData<>();
        obserVableItemsPlayerTroops = new MutableLiveData<>();
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
        observableItemsPlayerAchievments.postValue(player.getAchievements());
        obserVableItemsPlayerTroops.postValue(player.getTroops());
        observableItemsPlayerSpells.postValue(player.getSpells());
        observableItemsPlayerHeroes.postValue(player.getHeroes());
    }

    public void loadTopPlayerFromJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray itemsArray = jsonObject.getAsJsonArray("items");
        Type listType = new TypeToken<List<PlayerRanking>>() {
        }.getType();
        playerRankings = gson.fromJson(itemsArray, listType);
        observableItemsPlayerRanking.postValue(playerRankings);
    }

    public void loadTopClansFromJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray itemsArray = jsonObject.getAsJsonArray("items");
        Type listType = new TypeToken<List<ClanRanking>>() {
        }.getType();
        clanRankings = gson.fromJson(itemsArray, listType);
        observableItemsClanRanking.postValue(clanRankings);
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
