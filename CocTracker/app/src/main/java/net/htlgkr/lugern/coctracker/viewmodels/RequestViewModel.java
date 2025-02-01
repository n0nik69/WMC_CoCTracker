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
import com.google.gson.reflect.TypeToken;

import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.einModel.einClan.Clan;
import net.htlgkr.lugern.coctracker.einModel.einPlayer.Player;
import net.htlgkr.lugern.coctracker.models.clan.FoundClanResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestViewModel extends ViewModel {

    private static final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImMxZTY3Nzg2LTkyNGYtNDY4Mi1hYTViLTlhNmI0NGVjYzEzYSIsImlhdCI6MTczODQwNjgxOCwic3ViIjoiZGV2ZWxvcGVyLzgzNjM3MjQ5LTdmZjEtZWRhNC03NWIwLTYzZDE5ZTkxNWM4YSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE0NS40MC40OS44MiJdLCJ0eXBlIjoiY2xpZW50In1dfQ.VolJrLgvOZcd4kpRbQyz_774_PCFod2wHbFEDrYx8USjYun5KpbjRJHBhtB6k6Qzjwb4yUbmG9FsvNkWFDraNw"; // Setze deinen API-Schlüssel hier ein
    private static String API_URL = "https://api.clashofclans.com/v1"; // Spieler-URL
    private final MutableLiveData<Boolean> searchPerTag = new MutableLiveData<>();
    private RequestQueue queue;
    private Gson gson = new Gson();
    private Player player;
    private Clan clan;
    private List<Clan> clans;

    public static String getApiUrl() {
        return API_URL;
    }

    public static void setApiUrl(String apiUrl) {
        API_URL = apiUrl;
    }

    public LiveData<Boolean> isSearchPerTag() {
        return searchPerTag;
    }

    public void setSearchPerTag(boolean value) {
        searchPerTag.setValue(value);
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

    public void loadClanInfo(String json) {
        TypeToken<Clan> typeToken = new TypeToken<>() {
        };
        clan = gson.fromJson(json, typeToken);
        System.out.println();
    }

    public void loadFoundClanInfo(String json) {
        FoundClanResponse response = gson.fromJson(json, FoundClanResponse.class);
        clans = response.getItems();
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