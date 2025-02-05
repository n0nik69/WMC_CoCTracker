package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentPlayerScreenBinding;
import net.htlgkr.lugern.coctracker.list.adapter.MyAchievmentRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.list.adapter.MyHeroRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.list.adapter.MySpellRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.list.adapter.MyTroopsRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class PlayerScreen extends Fragment {
    FragmentPlayerScreenBinding binding;
    LogicViewModel logicViewModel;
    MainViewModel mainViewModel;
    String playerTag;
    String url;
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
        binding.tvPlayer.setOnClickListener(view -> showMenu(view, R.menu.popup_menu_player));

        binding.btnSearchPlayer.setEnabled(false);
        binding.btnSearchPlayer.setOnClickListener(view -> {
            binding.playerCP.setVisibility(VISIBLE);
            searchPlayerPerTag("");
        });


        logicViewModel.observableItemsPlayerAchievments.observe(getViewLifecycleOwner(), items -> {
            MyAchievmentRecyclerViewAdapter adapter = new MyAchievmentRecyclerViewAdapter(logicViewModel.observableItemsPlayerAchievments.getValue());
//            mainViewModel.showScreen(MainViewModel.playerAchievmentList);
        });

        logicViewModel.obserVableItemsPlayerTroops.observe(getViewLifecycleOwner(), items -> {
            MyTroopsRecyclerViewAdapter adapter = new MyTroopsRecyclerViewAdapter(logicViewModel.obserVableItemsPlayerTroops.getValue());
//            mainViewModel.showScreen(MainViewModel.playerTroops);
        });

        logicViewModel.observableItemsPlayerSpells.observe(getViewLifecycleOwner(), items -> {
            MySpellRecyclerViewAdapter adapter = new MySpellRecyclerViewAdapter(logicViewModel.observableItemsPlayerSpells.getValue());
//            mainViewModel.showScreen(MainViewModel.playerSpells);
        });

        logicViewModel.observableItemsPlayerHeroes.observe(getViewLifecycleOwner(), items -> {
            MyHeroRecyclerViewAdapter adapter = new MyHeroRecyclerViewAdapter(logicViewModel.observableItemsPlayerHeroes.getValue());
//            mainViewModel.showScreen(MainViewModel.playerSpells);
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

    private void showMenu(View v, int menuRes) {
        popup.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.playerAchievments) {
                mainViewModel.showScreen(MainViewModel.playerAchievmentList);
                return true;
            } else if (menuItem.getItemId() == R.id.playerHeroes) {
                mainViewModel.showScreen(MainViewModel.playerHeroes);
                return true;
            } else if (menuItem.getItemId() == R.id.playerTroops) {
                mainViewModel.showScreen(MainViewModel.playerTroops);
                return true;
            } else if (menuItem.getItemId() == R.id.playerSpells) {
                mainViewModel.showScreen(MainViewModel.playerSpells);
                return true;
            
//            else if (menuItem.getItemId() == R.id.topPlayers) {
//                loadTopPlayers();
            } else if (menuItem.getItemId() == R.id.searchPlayerPerTag) {
                binding.tvPlayer.setVisibility(VISIBLE);
                binding.textInputLayout2.setVisibility(VISIBLE);
                binding.btnSearchPlayer.setVisibility(VISIBLE);
                isMenuSelected = true;
                updateButtonState();
                binding.listLayoutTopPlayers.setVisibility(INVISIBLE);
            }
            return false;
        });
        popup.show();
    }

    public void searchPlayerPerTag(String clanTag) {
        binding.listLayoutTopPlayers.setVisibility(GONE);
        if (clanTag.isEmpty()) {
            playerTag = String.valueOf(binding.tiPlayerTag.getText()).trim().toUpperCase();
        } else {
            playerTag = clanTag;
        }
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
                logicViewModel.loadPlayerFromJson(json);
                popup.getMenu().findItem(R.id.playerTroops).setEnabled(true);
                popup.getMenu().findItem(R.id.playerAchievments).setEnabled(true);
                popup.getMenu().findItem(R.id.playerSpells).setEnabled(true);
                popup.getMenu().findItem(R.id.playerHeroes).setEnabled(true);
                binding.playerCP.setVisibility(INVISIBLE);
                binding.listLayoutTopPlayers.setVisibility(VISIBLE);
//                mainViewModel.showScreen(MainViewModel.playerTroops);
            }

            @Override
            public void onError(String error) {
                binding.playerCP.setVisibility(INVISIBLE);
            }
        });
    }

    public void loadTopPlayers() {
        String url = "https://api.clashofclans.com/v1/locations/32000022/rankings/players?limit=25";

        logicViewModel.setApiUrl(url);
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                logicViewModel.loadTopPlayerFromJson(json);
                binding.btnSearchPlayer.setVisibility(INVISIBLE);
                binding.textInputLayout2.setVisibility(INVISIBLE);
                binding.playerCP.setVisibility(INVISIBLE);
                mainViewModel.showScreen(MainViewModel.topPlayersList);
            }

            @Override
            public void onError(String error) {
                binding.playerCP.setVisibility(INVISIBLE);
            }
        });
    }
}