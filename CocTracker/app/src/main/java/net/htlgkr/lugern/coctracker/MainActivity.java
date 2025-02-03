package net.htlgkr.lugern.coctracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.htlgkr.lugern.coctracker.fragments.ClanScreen;
import net.htlgkr.lugern.coctracker.fragments.PlayerScreen;
import net.htlgkr.lugern.coctracker.list.listFragments.ClanFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.FoundClanFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.TopClansFragment;
import net.htlgkr.lugern.coctracker.list.listFragments.TopPlayersFragment;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

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
        LogicViewModel logicViewModel = new ViewModelProvider(this).get(LogicViewModel.class);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_1) {
                mainViewModel.showScreen(MainViewModel.clanScreen);
            } else if (item.getItemId() == R.id.item_2) {
                mainViewModel.showScreen(MainViewModel.playerScreen);
            } else if (item.getItemId() == R.id.item_3) {
                mainViewModel.showScreen(MainViewModel.leagueScreen);
            } else if (item.getItemId() == R.id.item_4) {
                mainViewModel.showScreen(MainViewModel.rankingScreen);
            }
            return true;
        });

        mainViewModel.state.observe(this, state -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (state) {
                case MainViewModel.playerScreen:
                    transaction.replace(R.id.mainFragment, new PlayerScreen(), "PLAYERSCREEN");
                    break;
                case MainViewModel.clanScreen:
                    transaction.replace(R.id.mainFragment, new ClanScreen(), "CLANSCREEN");
                    break;
            }
            transaction.commit();
        });

        logicViewModel.getSearchPerName().observe(this, isSearchPerName -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (isSearchPerName) {
                transaction.replace(R.id.listLayout, new FoundClanFragment());
            }
            transaction.commit();
        });

        logicViewModel.getSearchPerTag().observe(this, isSearchPerTag -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (isSearchPerTag) {
                transaction.replace(R.id.listLayout, new ClanFragment());
            }
            transaction.commit();
        });

        logicViewModel.getShowTopClansList().observe(this, showTopClansList -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (showTopClansList) {
                transaction.replace(R.id.listLayout, new TopClansFragment());
            }
            transaction.commit();
        });

        logicViewModel.getShowTopPlayersList().observe(this, showTopPlayersList -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (showTopPlayersList) {
                transaction.replace(R.id.playerListLayout, new TopPlayersFragment());
            }
            transaction.commit();
        });


    }

}