package net.htlgkr.lugern.coctracker.list.listViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.htlgkr.lugern.coctracker.list.listModel.AchievmentCard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AchievmentViewModel extends ViewModel {
    public MutableLiveData<ArrayList<AchievmentCard>> observableItems;
    private ArrayList<AchievmentCard> achievmentCards;

    public AchievmentViewModel() {
        observableItems = new MutableLiveData<>();
        achievmentCards = new ArrayList<>();
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

            observableItems.postValue(achievmentCards);
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
}
