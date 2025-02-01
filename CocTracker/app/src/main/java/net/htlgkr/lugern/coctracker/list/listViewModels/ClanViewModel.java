package net.htlgkr.lugern.coctracker.list.listViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.einModel.einPlayer.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClanViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Player>> observableItems;
    private ArrayList<Player> clanCards;

    public ClanViewModel() {
        observableItems = new MutableLiveData<>();
        clanCards = new ArrayList<>();
    }

    public void loadDataFromJson(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray memberList = jsonResponse.getJSONArray("memberList");

            clanCards.clear();

            for (int i = 0; i < memberList.length(); i++) {
                JSONObject member = memberList.getJSONObject(i);

                Player player = new Player();
                int townHallLevel = member.getInt("townHallLevel");

                player.setTownHallLevel(townHallLevel);

                int[] townHallIcons = {
                        R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5,
                        R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10,
                        R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15,
                        R.drawable.a16, R.drawable.a17
                };

                int townHallIcon = (townHallLevel >= 1 && townHallLevel <= 17) ? townHallIcons[townHallLevel - 1] : R.drawable.a1;

//                player.setIvTownhallLvl(townHallIcon);


                player.setExpLevel(member.getInt("expLevel"));
//                player.setIvExpLvl(R.drawable.xp_small);

                player.setTrophies(member.getInt("trophies"));
//                player.setIvTrophies(R.drawable.a1);

                player.setBuilderBaseTrophies(member.getInt("builderBaseTrophies"));
//                player.setIvBuildTrophies(R.drawable.a1);

                player.setDonations(member.getInt("donations"));
                player.setDonationsReceived(member.getInt("donationsReceived"));


                player.setName(member.getString("name"));
                player.setTag(member.getString("tag"));
//                player.set(member.getString("role"));

                if (member.has("league")) {
                    JSONObject league = member.getJSONObject("league");
//                    player.setLeague(league.getString("name"));
//                    player.setIvLeague(R.drawable.a1);
                }

                clanCards.add(player);
            }

            observableItems.postValue(clanCards);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}