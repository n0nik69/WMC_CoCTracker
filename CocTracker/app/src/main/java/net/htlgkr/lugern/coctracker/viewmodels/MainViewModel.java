package net.htlgkr.lugern.coctracker.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public static final int playerScreen = 1;
    public static final int clanScreen = 2;
    public static final int leagueScreen = 3;
    public static final int rankingScreen = 4;
    public static final int topClansList = 5;
    public static final int foundClansList = 6;
    public static final int playerAchievmentList = 7;
    public static final int clanMemberList = 8;

    private final MutableLiveData<Integer> _state = new MutableLiveData<>(clanScreen);
    public LiveData<Integer> state = _state;

    public void showScreen(int screen) {
        _state.postValue(screen);
    }

    public LiveData<Integer> getState() {
        return state;
    }
}
