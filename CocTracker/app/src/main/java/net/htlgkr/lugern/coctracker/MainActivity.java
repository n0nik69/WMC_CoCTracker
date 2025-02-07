package net.htlgkr.lugern.coctracker;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.fragments.ClanScreen;
import net.htlgkr.lugern.coctracker.fragments.LeagueScreen;
import net.htlgkr.lugern.coctracker.fragments.PlayerScreen;
import net.htlgkr.lugern.coctracker.list.listFragments.AchievmentFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.ClanFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.FoundClanFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.HeroFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.SpellFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.TopClansFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.TopPlayersFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.TroopsFragment;
import net.htlgkr.lugern.coctracker.misc.MusicService;
import net.htlgkr.lugern.coctracker.models.player.Goldpass;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private View popupView;
    private LogicViewModel logicViewModel;
    private TextView goldpassSeason;
    private CircularProgressIndicator circular;


    @Override
    protected void onPause() {
        super.onPause();
        Intent musicServiceIntent = new Intent(this, MusicService.class);
        stopService(musicServiceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent musicServiceIntent = new Intent(this, MusicService.class);
        startService(musicServiceIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ConstraintLayout mainLayout = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (view, insets) -> {
            Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(
                    systemBarsInsets.left,
                    systemBarsInsets.top,
                    systemBarsInsets.right,
                    systemBarsInsets.bottom
            );
            return WindowInsetsCompat.CONSUMED;
        });
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        logicViewModel = new ViewModelProvider(this).get(LogicViewModel.class);
        logicViewModel.init(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_1) {
                mainViewModel.showScreen(MainViewModel.clanScreen);
            } else if (item.getItemId() == R.id.item_2) {
                mainViewModel.showScreen(MainViewModel.playerScreen);
            } else if (item.getItemId() == R.id.item_3) {
                mainViewModel.showScreen(MainViewModel.leagueScreen);
            }
            return true;
        });

        popupView = findViewById(R.id.popupContainer);
        circular = findViewById(R.id.circular);
        goldpassSeason = findViewById(R.id.currentGoldpassSeason);
        Button closeButton = findViewById(R.id.btnClosePopup);
        closeButton.setOnClickListener(v -> hidePopup());


        showPopup();


        Intent musicServiceIntent = new Intent(this, MusicService.class);
        startService(musicServiceIntent);

        mainViewModel.state.observe(this, state -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (state) {
                case MainViewModel.playerScreen:
                    transaction.replace(R.id.mainFragment, new PlayerScreen(), "PLAYERSCREEN");
                    break;
                case MainViewModel.clanScreen:
                    transaction.replace(R.id.mainFragment, new ClanScreen(), "CLANSCREEN");
                    break;
                case MainViewModel.leagueScreen:
                    transaction.replace(R.id.mainFragment, new LeagueScreen(), "LEAGUESCREEN");
                    break;
                case MainViewModel.playerAchievmentList:
                    transaction.replace(R.id.listLayoutPlayer, new AchievmentFragment(), "PLAYERACHIEVMENTS");
                    break;
                case MainViewModel.topClansList:
                    transaction.replace(R.id.listLayoutLeague, new TopClansFragment(), "TOPSCLANS");
                    break;
                case MainViewModel.foundClansList:
                    transaction.replace(R.id.listLayoutFoundClans, new FoundClanFragment(), "FOUNDCLANS");
                    break;
                case MainViewModel.clanMemberList:
                    transaction.replace(R.id.listLayoutClanMembers, new ClanFragment(), "CLANMEMBER");
                    break;
                case MainViewModel.topPlayersList:
                    transaction.replace(R.id.listLayoutLeague, new TopPlayersFragment(), "TOPPLAYERS");
                    break;
                case MainViewModel.playerTroops:
                    transaction.replace(R.id.listLayoutPlayer, new TroopsFragment(), "PLAYERTROOPS");
                    break;
                case MainViewModel.playerSpells:
                    transaction.replace(R.id.listLayoutPlayer, new SpellFragment(), "PLAYERSPELLS");
                    break;
                case MainViewModel.playerHeroes:
                    transaction.replace(R.id.listLayoutPlayer, new HeroFragment(), "PLAYERHEROES");
                    break;
            }
            transaction.commit();
        });
    }

    private void showPopup() {
        String url = "https://api.clashofclans.com/v1/goldpass/seasons/current";
        logicViewModel.setApiUrl(url);
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                logicViewModel.loadGoldpass(json);
                circular.setVisibility(GONE);
                Goldpass goldpass = logicViewModel.getGoldpass();
                goldpassSeason.setText("The current Goldpass season runs from " + goldpass.getStartTime() + " until " + goldpass.getEndTime());

            }

            @Override
            public void onError(String error) {
            }
        });

        if (popupView != null && popupView.getVisibility() != View.VISIBLE) {
            popupView.setAlpha(0f);
            popupView.setVisibility(View.VISIBLE);
            popupView.animate().alpha(1f).setDuration(500).start();
        }
    }


    private void hidePopup() {
        ConstraintLayout popupPassCL = findViewById(R.id.popupPassCL);

        if (popupView.getVisibility() == View.VISIBLE) {
            popupView.animate()
                    .alpha(0f)
                    .setDuration(500)
                    .withEndAction(() -> {
                        popupView.setVisibility(GONE);
                        popupPassCL.setVisibility(GONE);
                    })
                    .start();
        }
    }
}