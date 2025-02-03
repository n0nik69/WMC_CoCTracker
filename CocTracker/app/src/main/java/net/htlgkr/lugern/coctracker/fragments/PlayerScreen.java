package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.INVISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentPlayerScreenBinding;
import net.htlgkr.lugern.coctracker.models.player.Player;
import net.htlgkr.lugern.coctracker.models.player.PlayerRanking;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

import java.util.List;

public class PlayerScreen extends Fragment {
    FragmentPlayerScreenBinding binding;
    //    RequestViewModel requestViewModel;
    LogicViewModel logicViewModel;

    public PlayerScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayerScreenBinding.inflate(inflater, container, false);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        logicViewModel.init(requireContext());
        binding.tvPlayer.setOnClickListener(view -> {
            logicViewModel.setShowTopPlayersList(false);
            showMenu(view, R.menu.popup_menu_player);
        });
        binding.btnSearchPlayer.setOnClickListener(view -> {
            String url;
            String playerTag = String.valueOf(binding.tiPlayerTag.getText()).trim().toUpperCase();

            if (!playerTag.startsWith("#")) {
                playerTag = "%23" + playerTag;
            } else {
                playerTag = playerTag.replace("#", "%23");
            }
            url = "https://api.clashofclans.com/v1/players/" + playerTag;

            logicViewModel.setApiUrl(url);
            logicViewModel.requestData(new HTTPListener<>() {
                @Override
                public void onSuccess(String json) {
                    logicViewModel.loadPlayerInfo(json);
                    Player player = logicViewModel.getPlayer();
                    System.out.println();
                }

                @Override
                public void onError(String error) {
                    System.out.println(error);
                }
            });
        });

        return binding.getRoot();
    }

    public void setImageOnImageView(ImageView imageView, int number) {
        if (number < 1 || number > 17) {
            throw new IllegalArgumentException("Parameter 'number' muss zwischen 1 und 17 liegen.");
        }

        int[] images = {
                R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5,
                R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10,
                R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15,
                R.drawable.a16, R.drawable.a17
        };

        imageView.setImageResource(images[number - 1]);
    }

    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.playerAchievments) {
                return true;
            } else if (menuItem.getItemId() == R.id.playerHeroes) {
                return true;
            } else if (menuItem.getItemId() == R.id.playerTroops) {
                return true;
            } else if (menuItem.getItemId() == R.id.playerSpells) {
                return true;
            } else if (menuItem.getItemId() == R.id.topPlayers) {
                binding.tvPlayer.setOnClickListener(view -> {
                    logicViewModel.setPlayerClicked();
                });
                loadTopPlayers();
            }
            return false;
        });

        popup.show();
    }

    private void loadTopPlayers() {
        logicViewModel.setShowTopPlayersList(true);
        String url = "https://api.clashofclans.com/v1/locations/32000022/rankings/players?limit=10";

        logicViewModel.setApiUrl(url);
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                logicViewModel.loadTopPlayers(json);
                binding.btnSearchPlayer.setVisibility(INVISIBLE);
                binding.textInputLayout2.setVisibility(INVISIBLE);
                logicViewModel.setTopPlayer(json);
                List<PlayerRanking> playerRankingList = logicViewModel.getPlayerRankings();
                System.out.println();
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });
    }
}