package net.htlgkr.lugern.coctracker;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.RequestViewModel;

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

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_1) {
                mainViewModel.showScreen(MainViewModel.playerScreen);
            } else if (item.getItemId() == R.id.item_2) {
                mainViewModel.showScreen(MainViewModel.clanScreen);
            } else if (item.getItemId() == R.id.item_3) {
                mainViewModel.showScreen(MainViewModel.leagueScreen);
            } else if (item.getItemId() == R.id.item_4) {
                mainViewModel.showScreen(MainViewModel.rankingScreen);
            } else if (item.getItemId() == R.id.item_5) {
                mainViewModel.showScreen(MainViewModel.goldpassScreen);
            }
            return true;
        });


        RequestViewModel requestViewModel = new ViewModelProvider(this).get(RequestViewModel.class);
        requestViewModel.init(getApplicationContext());
        requestViewModel.requestStatsAndCards(new HTTPListener<>() {
            @Override
            public void onSuccess(String a) {
//                Log.i("ad", a);
                requestViewModel.loadPlayerInfo(a);
            }

            @Override
            public void onError(String error) {
                Log.i("Error", error);
            }
        });

    }


}