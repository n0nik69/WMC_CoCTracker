package net.htlgkr.lugern.coctracker.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.list.ClanCard;

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

            clanCards.clear(); // Vorherige Daten löschen

            for (int i = 0; i < memberList.length(); i++) {
                JSONObject member = memberList.getJSONObject(i);

                ClanCard clanCard = new ClanCard();
                int townHallLevel = member.getInt("townHallLevel");

// Setze das Townhall-Level-Label
                clanCard.setTvTownhallLvl("TH " + townHallLevel);

// Setze das entsprechende Townhall-Icon
                int townHallIcon;
                switch (townHallLevel) {
                    case 1:
                        townHallIcon = R.drawable.a1;
                        break;
                    case 2:
                        townHallIcon = R.drawable.a2;
                        break;
                    case 3:
                        townHallIcon = R.drawable.a3;
                        break;
                    case 4:
                        townHallIcon = R.drawable.a4;
                        break;
                    case 5:
                        townHallIcon = R.drawable.a5;
                        break;
                    case 6:
                        townHallIcon = R.drawable.a6;
                        break;
                    case 7:
                        townHallIcon = R.drawable.a7;
                        break;
                    case 8:
                        townHallIcon = R.drawable.a8;
                        break;
                    case 9:
                        townHallIcon = R.drawable.a9;
                        break;
                    case 10:
                        townHallIcon = R.drawable.a10;
                        break;
                    case 11:
                        townHallIcon = R.drawable.a11;
                        break;
                    case 12:
                        townHallIcon = R.drawable.a12;
                        break;
                    case 13:
                        townHallIcon = R.drawable.a13;
                        break;
                    case 14:
                        townHallIcon = R.drawable.a14;
                        break;
                    case 15:
                        townHallIcon = R.drawable.a15;
                        break;
                    case 16:
                        townHallIcon = R.drawable.a16;
                        break;
                    case 17:
                        townHallIcon = R.drawable.a17;
                        break;
                    default:
                        townHallIcon = R.drawable.a1; // Fallback, falls etwas schiefgeht
                        break;
                }

// Setze das Townhall-Icon
                clanCard.setIvTownhallLvl(townHallIcon);


                clanCard.setTvExpLvl("XP: " + member.getInt("expLevel"));
                clanCard.setIvExpLvl(R.drawable.xp_small); // Beispiel-Icon

                clanCard.setTvTrophies(member.getInt("trophies") + " 🏆");
                clanCard.setIvTrophies(R.drawable.a1); // Beispiel-Icon

                clanCard.setTvBuildTrophies(member.getInt("builderBaseTrophies") + " 🏗");
                clanCard.setIvBuildTrophies(R.drawable.a1);

                clanCard.setTvDonations("Gespendet: " + member.getInt("donations") + " / Erhalten: " + member.getInt("donationsReceived"));
                clanCard.setTvDonations(member.getString("donations"));
                clanCard.setTvDonationsReceived(member.getString("donationsReceived"));
//                card.setIvDonations(R.drawable.a1);


                String nameAndTag = member.getString("name") + " - " + member.getString("tag");
//                card.setTvName(member.getString("name") );
                clanCard.setTvName(nameAndTag);
//                card.setTvName(member.getString("name") + " - " + member.getString("tag"));
                clanCard.setTvRole(member.getString("role"));

                // Liga-Icon setzen, falls vorhanden
                if (member.has("league")) {
                    JSONObject league = member.getJSONObject("league");
                    clanCard.setTvLeague(league.getString("name"));
                    clanCard.setIvLeague(R.drawable.a1); // Hier könnte man evtl. das Bild dynamisch laden
                }

                clanCards.add(clanCard);
            }

            observableItems.postValue(clanCards); // LiveData  aktualisieren
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
