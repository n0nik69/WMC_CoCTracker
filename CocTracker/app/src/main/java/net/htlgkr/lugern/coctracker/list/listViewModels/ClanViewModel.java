package net.htlgkr.lugern.coctracker.list.listViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.htlgkr.lugern.coctracker.models.clan.ClanMember;
import net.htlgkr.lugern.coctracker.models.clan.IconUrls;
import net.htlgkr.lugern.coctracker.models.clan.Role;
import net.htlgkr.lugern.coctracker.models.player.League;
import net.htlgkr.lugern.coctracker.models.player.PlayerAchievmentProgress;
import net.htlgkr.lugern.coctracker.models.player.Village;
import net.htlgkr.lugern.coctracker.models.shared.BuilderBaseLeague;
import net.htlgkr.lugern.coctracker.models.shared.Label;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClanViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ClanMember>> observableItems;
    private ArrayList<ClanMember> clanCards;

    public ClanViewModel() {
        observableItems = new MutableLiveData<>();
        clanCards = new ArrayList<>();
    }

    public void setPlayerPerClan(String json) {
        try {
            JSONObject jsonResponse = new JSONObject(json);
            JSONArray memberList = jsonResponse.getJSONArray("memberList");

            clanCards.clear();
            for (int i = 0; i < memberList.length(); i++) {
                JSONObject member = memberList.getJSONObject(i);
                ClanMember clanMember = new ClanMember();
                setIfExistsInt(member, "expLevel", clanMember::setExpLevel);
                setIfExistsInt(member, "townHallLevel", clanMember::setTownHallLevel);
                setIfExistsInt(member, "clanRank", clanMember::setClanRank);
                setIfExistsInt(member, "previousClanRank", clanMember::setPreviousClanRank);

                setIfExistsInt(member, "trophies", clanMember::setTrophies);
                setIfExistsInt(member, "builderBaseTrophies", clanMember::setBuilderBaseTrophies);
                setIfExistsInt(member, "donations", clanMember::setDonations);
                setIfExistsInt(member, "donationsReceived", clanMember::setDonationsReceived);
                if (member.has("role"))
                    clanMember.setRole(Role.valueOf(member.optString("role", null)));

                setIfExistsLeague(member, clanMember::setLeague);
                setIfExistsBuilderBaseLeague(member, clanMember::setBuilderBaseLeague);

                setIfExistsString(member, "name", clanMember::setName);
                setIfExistsString(member, "tag", clanMember::setTag);
                List<PlayerAchievmentProgress> achievements = new ArrayList<>();
                List<Label> labels = new ArrayList<>();
                setAchievementListIfExists(member, achievements);
                setLabelListIfExists(member, labels);
                clanCards.add(clanMember);
            }

            observableItems.postValue(clanCards);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    //hilfsmethoden shit
    private void setIfExistsInt(JSONObject member, String key, Consumer<Integer> setter) {
        if (member.has(key)) {
            try {
                setter.accept(member.getInt(key));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setIfExistsString(JSONObject member, String key, Consumer<String> setter) {
        if (member.has(key)) {
            try {
                setter.accept(member.getString(key));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setAchievementListIfExists(JSONObject member, List<PlayerAchievmentProgress> achievements) {
        try {
            if (member.has("achievements")) {
                JSONArray array = member.getJSONArray("achievements");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject achievementObject = array.getJSONObject(i);
                    PlayerAchievmentProgress achievementProgress = new PlayerAchievmentProgress();

                    setIfExistsInt(achievementObject, "stars", achievementProgress::setStars);
                    setIfExistsInt(achievementObject, "value", achievementProgress::setValue);
                    setIfExistsString(achievementObject, "name", achievementProgress::setName);
                    setIfExistsInt(achievementObject, "target", achievementProgress::setTarget);
                    setIfExistsString(achievementObject, "completionInfo", achievementProgress::setCompletionInfo);
                    setIfExistsString(achievementObject, "village", value -> achievementProgress.setVillage(Village.valueOf(value)));
                    achievements.add(achievementProgress);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setLabelListIfExists(JSONObject member, List<Label> labels) {
        try {
            if (member.has("labels")) {
                JSONArray array = member.getJSONArray("labels");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject labelObject = array.getJSONObject(i);
                    Label label = new Label();
                    setIfExistsString(labelObject, "name", label::setName);
                    setIfExistsInt(labelObject, "id", label::setId);
                    setIfExistsJSONObject(labelObject, label::setIconUrls);
                    labels.add(label);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setIfExistsJSONObject(JSONObject jsonObject, Consumer<IconUrls> setter) {
        try {
            if (jsonObject.has("iconUrls")) {
                JSONObject iconUrlsObject = jsonObject.getJSONObject("iconUrls");
                IconUrls iconUrls = new IconUrls();
                setIfExistsString(iconUrlsObject, "small", iconUrls::setSmall);
                setIfExistsString(iconUrlsObject, "medium", iconUrls::setMedium);
                setIfExistsString(iconUrlsObject, "large", iconUrls::setLarge);
                setter.accept(iconUrls);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void setIfExistsLeague(JSONObject member, Consumer<League> setter) {
        if (member.has("league")) {
            try {
                JSONObject leagueObject = member.getJSONObject("league");
                League league = new League();
                setIfExistsInt(leagueObject, "id", league::setId);
                setIfExistsString(leagueObject, "name", league::setName);

                if (leagueObject.has("iconUrls")) {
                    JSONObject iconUrlsObject = leagueObject.getJSONObject("iconUrls");
                    IconUrls iconUrls = new IconUrls();
                    setIfExistsString(iconUrlsObject, "small", iconUrls::setSmall);
                    setIfExistsString(iconUrlsObject, "medium", iconUrls::setMedium);
                    setIfExistsString(iconUrlsObject, "large", iconUrls::setLarge);
                    league.setIconUrls(iconUrls);
                }

                setter.accept(league);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setIfExistsBuilderBaseLeague(JSONObject member, Consumer<BuilderBaseLeague> setter) {
        if (member.has("builderBaseLeague")) {
            try {
                JSONObject builderBaseLeagueObject = member.getJSONObject("builderBaseLeague");
                int id = builderBaseLeagueObject.getInt("id");
                String name = builderBaseLeagueObject.getString("name");

                BuilderBaseLeague builderBaseLeague = new BuilderBaseLeague(id, name);
                setter.accept(builderBaseLeague);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}