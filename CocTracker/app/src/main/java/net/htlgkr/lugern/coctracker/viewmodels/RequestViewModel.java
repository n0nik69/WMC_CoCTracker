package net.htlgkr.lugern.coctracker.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.models.player.Player;

import java.util.HashMap;
import java.util.Map;

public class RequestViewModel extends ViewModel {

    private static final String API_URL = "https://api.clashofclans.com/v1/players/%2322RVG90R2"; // Spieler-URL
    private static final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImQzZjU2YWU3LWFkZjItNDM3NC1iNmFlLWJkNjRmNWRmOTAzNiIsImlhdCI6MTczODA5MjUyNCwic3ViIjoiZGV2ZWxvcGVyLzgzNjM3MjQ5LTdmZjEtZWRhNC03NWIwLTYzZDE5ZTkxNWM4YSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE0NS40MC40OS44MiJdLCJ0eXBlIjoiY2xpZW50In1dfQ.3aep5cEIFjVz_QSCy75sjly0N-x1HYc8xR2MYJEw7hdZTcBAGDFFJ5h7EUZ0wsRcUZ4491ZGj0XZHdTdeQunVA"; // Setze deinen API-Schlüssel hier ein
    private RequestQueue queue;
    private Gson gson = new Gson();
    private Player player;

    public void init(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void requestStatsAndCards(HTTPListener<String> listener) {

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
        System.out.println("Daten geladen..");
        Log.i("Daten", "Daten geladen.. mit Log");
    }

    public Player getPlayer() {
        return player;
    }
}
