package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentPlayerScreenBinding;
import net.htlgkr.lugern.coctracker.list.adapter.MyAchievmentRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.player.Player;
import net.htlgkr.lugern.coctracker.models.player.PlayerAchievmentProgress;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class PlayerScreen extends Fragment {
    FragmentPlayerScreenBinding binding;
    LogicViewModel logicViewModel;
    MainViewModel mainViewModel;
    private boolean isMenuSelected = false;
    private PopupMenu popup;

    public PlayerScreen() {
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popup = new PopupMenu(requireContext(), binding.tvPlayer);
        popup.getMenuInflater().inflate(R.menu.popup_menu_player, popup.getMenu());
        popup.getMenu().findItem(R.id.playerTroops).setEnabled(false);
        popup.getMenu().findItem(R.id.playerAchievments).setEnabled(false);
        popup.getMenu().findItem(R.id.playerHeroes).setEnabled(false);
        popup.getMenu().findItem(R.id.playerSpells).setEnabled(false);
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
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        logicViewModel.init(requireContext());
        binding.tvPlayer.setOnClickListener(view -> {
            logicViewModel.setShowTopPlayersList(false);
            showMenu(view, R.menu.popup_menu_player);

        });

        binding.btnSearchPlayer.setEnabled(false);
        ImageView th = null;
        CircularProgressIndicator cp = null;
        binding.btnSearchPlayer.setOnClickListener(view -> {
            binding.playerCP.setVisibility(VISIBLE);
            showPlayer(th, cp, logicViewModel);
        });

//
        logicViewModel.observableItemsPlayerAchievments.observe(getViewLifecycleOwner(), items -> {
            MyAchievmentRecyclerViewAdapter adapter = new MyAchievmentRecyclerViewAdapter(logicViewModel.observableItemsPlayerAchievments.getValue());
//            logicViewModel.setShowPlayerAchievment(true);
            mainViewModel.showScreen(MainViewModel.playerAchievmentList);
            adapter.setOnItemClickListener(position -> {
//                ArrayList<PlayerAchievmentProgress> playerAchievmentProgresses = logicViewModel.observableItemsPlayerAchievments.getValue();
                PlayerAchievmentProgress playerAchievmentProgress = logicViewModel.observableItemsPlayerAchievments.getValue().get(position);

            });
        });


        binding.tiPlayerTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateButtonState();

                binding.btnSearchPlayer.setVisibility(View.VISIBLE);
                binding.tvPlayerName.setVisibility(View.INVISIBLE);
                binding.tvPlayerTrophies.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        return binding.getRoot();
    }

    private void updateButtonState() {
        boolean isTextNotEmpty = !binding.tiPlayerTag.getText().toString().trim().isEmpty();
        binding.btnSearchPlayer.setEnabled(isTextNotEmpty && isMenuSelected);
    }

    private void showPlayer(ImageView imageView, CircularProgressIndicator progressIndicator, LogicViewModel logicViewModel) {
//        progressIndicator.show();
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
                System.out.println();

                logicViewModel.loadPlayerFromJson(json);
                Player player = logicViewModel.getPlayer();
                binding.playerListLayout.setVisibility(VISIBLE);
                binding.playerCP.setVisibility(INVISIBLE);
                popup.getMenu().findItem(R.id.playerSpells).setEnabled(true);
                popup.getMenu().findItem(R.id.playerAchievments).setEnabled(true);
                popup.getMenu().findItem(R.id.playerHeroes).setEnabled(true);
                popup.getMenu().findItem(R.id.playerTroops).setEnabled(true);

            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });
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

                loadTopPlayers();
            } else if (menuItem.getItemId() == R.id.searchPlayerPerTag) {
                isMenuSelected = true;
                updateButtonState();
                logicViewModel.setPlayerClicked();
                binding.playerListLayout.setVisibility(INVISIBLE);
//                binding.playerCP.setVisibility(VISIBLE);
            }
            return false;
        });

        popup.show();
    }

    public void disableMenuOptions(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        View playerAchievmentsItem = v.findViewById(R.id.playerAchievments);
        playerAchievmentsItem.setEnabled(false);

    }

    private void loadTopPlayers() {
        logicViewModel.setShowTopPlayersList(true);
        String url = "https://api.clashofclans.com/v1/locations/32000022/rankings/players?limit=25";

        logicViewModel.setApiUrl(url);
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                logicViewModel.loadTopPlayerFromJson(json);
                binding.btnSearchPlayer.setVisibility(INVISIBLE);
                binding.textInputLayout2.setVisibility(INVISIBLE);
                binding.playerCP.setVisibility(INVISIBLE);
                System.out.println();
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });
    }
}