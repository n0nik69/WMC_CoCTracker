package net.htlgkr.lugern.coctracker.list.listViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.htlgkr.lugern.coctracker.models.clan.ClanRanking;
import net.htlgkr.lugern.coctracker.viewmodels.RequestViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TopClansViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ClanRanking>> observableItems;
    RequestViewModel requestViewModel;
    private ArrayList<ClanRanking> clanRankings;

    public TopClansViewModel() {
        observableItems = new MutableLiveData<>();
        clanRankings = new ArrayList<>();
//        requestViewModel = new ViewModelProvider(this).get(RequestViewModel.class);
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

            observableItems.postValue(clanRankings);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
