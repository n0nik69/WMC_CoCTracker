package net.htlgkr.lugern.coctracker.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.models.Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Card>> observableItems;
    private ArrayList<Card> cards;

    public CardViewModel() {
        observableItems = new MutableLiveData<>();
        cards = new ArrayList<>();
    }

    public void loadDataFromJson(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray memberList = jsonResponse.getJSONArray("memberList");

            cards.clear(); // Vorherige Daten löschen

            for (int i = 0; i < memberList.length(); i++) {
                JSONObject member = memberList.getJSONObject(i);

                Card card = new Card();
                card.setTvTownhallLvl("TH " + member.getInt("townHallLevel"));
                card.setIvTownhallLvl(R.drawable.a1); // Beispiel-Icon

                card.setTvExpLvl("XP: " + member.getInt("expLevel"));
                card.setIvExpLvl(R.drawable.xp_small); // Beispiel-Icon

                card.setTvTrophies(member.getInt("trophies") + " 🏆");
                card.setIvTrophies(R.drawable.a1); // Beispiel-Icon

                card.setTvBuildTrophies(member.getInt("builderBaseTrophies") + " 🏗");
                card.setIvBuildTrophies(R.drawable.a1);

                card.setTvDonations("Gespendet: " + member.getInt("donations") + " / Erhalten: " + member.getInt("donationsReceived"));
                card.setTvDonations(member.getString("donations"));
                card.setTvDonationsReceived(member.getString("donationsReceived"));
//                card.setIvDonations(R.drawable.a1);

                
                String nameAndTag = member.getString("name") + " - " + member.getString("tag");
//                card.setTvName(member.getString("name") );
                card.setTvName(nameAndTag);
//                card.setTvName(member.getString("name") + " - " + member.getString("tag"));
                card.setTvRole(member.getString("role"));

                // Liga-Icon setzen, falls vorhanden
                if (member.has("league")) {
                    JSONObject league = member.getJSONObject("league");
                    card.setTvLeague(league.getString("name"));
                    card.setIvLeague(R.drawable.a1); // Hier könnte man evtl. das Bild dynamisch laden
                }

                cards.add(card);
            }

            observableItems.postValue(cards); // LiveData  aktualisieren
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
