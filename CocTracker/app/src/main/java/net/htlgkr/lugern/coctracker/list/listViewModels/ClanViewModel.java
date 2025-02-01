package net.htlgkr.lugern.coctracker.list.listViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.list.listModel.ClanCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClanViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ClanCard>> observableItems;
    private ArrayList<ClanCard> clanCards;

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

                ClanCard clanCard = new ClanCard();
                int townHallLevel = member.getInt("townHallLevel");

                clanCard.setTvTownhallLvl("TH " + townHallLevel);

                int[] townHallIcons = {
                        R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5,
                        R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10,
                        R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15,
                        R.drawable.a16, R.drawable.a17
                };

                int townHallIcon = (townHallLevel >= 1 && townHallLevel <= 17) ? townHallIcons[townHallLevel - 1] : R.drawable.a1;

                clanCard.setIvTownhallLvl(townHallIcon);


                clanCard.setTvExpLvl("XP: " + member.getInt("expLevel"));
                clanCard.setIvExpLvl(R.drawable.xp_small);

                clanCard.setTvTrophies(member.getInt("trophies") + " 🏆");
                clanCard.setIvTrophies(R.drawable.a1);

                clanCard.setTvBuildTrophies(member.getInt("builderBaseTrophies") + " 🏗");
                clanCard.setIvBuildTrophies(R.drawable.a1);

                clanCard.setTvDonations("Gespendet: " + member.getInt("donations") + " / Erhalten: " + member.getInt("donationsReceived"));
                clanCard.setTvDonations(member.getString("donations"));
                clanCard.setTvDonationsReceived(member.getString("donationsReceived"));


                String nameAndTag = member.getString("name") + " - " + member.getString("tag");
                clanCard.setTvName(nameAndTag);
                clanCard.setTvRole(member.getString("role"));

                if (member.has("league")) {
                    JSONObject league = member.getJSONObject("league");
                    clanCard.setTvLeague(league.getString("name"));
                    clanCard.setIvLeague(R.drawable.a1);
                }

                clanCards.add(clanCard);
            }

            observableItems.postValue(clanCards);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}