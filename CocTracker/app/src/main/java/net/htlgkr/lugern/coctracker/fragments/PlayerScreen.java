package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

import com.google.android.material.textfield.TextInputLayout;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentPlayerScreenBinding;
import net.htlgkr.lugern.coctracker.list.adapter.MyAchievmentRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.list.adapter.MyHeroRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.list.adapter.MySpellRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.list.adapter.MyTroopsRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.player.Player;
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
    private boolean isMoved = false;

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
            animateViews(binding.textInputLayout2);
            searchPlayerPerTag("");
        });

        binding.tiPlayerTag.setOnClickListener(v -> {
//            binding.tvClanError.setText("");
            reverseAnimation(binding.textInputLayout2);
        });


        logicViewModel.observableItemsPlayerAchievments.observe(getViewLifecycleOwner(), items -> {
            MyAchievmentRecyclerViewAdapter adapter = new MyAchievmentRecyclerViewAdapter(logicViewModel.observableItemsPlayerAchievments.getValue());
        });

        logicViewModel.obserVableItemsPlayerTroops.observe(getViewLifecycleOwner(), items -> {
            MyTroopsRecyclerViewAdapter adapter = new MyTroopsRecyclerViewAdapter(logicViewModel.obserVableItemsPlayerTroops.getValue());
        });

        logicViewModel.observableItemsPlayerSpells.observe(getViewLifecycleOwner(), items -> {
            MySpellRecyclerViewAdapter adapter = new MySpellRecyclerViewAdapter(logicViewModel.observableItemsPlayerSpells.getValue());
        });

        logicViewModel.observableItemsPlayerHeroes.observe(getViewLifecycleOwner(), items -> {
            MyHeroRecyclerViewAdapter adapter = new MyHeroRecyclerViewAdapter(logicViewModel.observableItemsPlayerHeroes.getValue());
        });

        //können wsl auskommentiert werden


        binding.tiPlayerTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateButtonState();
                popup.getMenu().findItem(R.id.playerTroops).setEnabled(false);
                popup.getMenu().findItem(R.id.playerAchievments).setEnabled(false);
                popup.getMenu().findItem(R.id.playerHeroes).setEnabled(false);
                popup.getMenu().findItem(R.id.playerSpells).setEnabled(false);
                binding.listLayoutPlayer.setVisibility(INVISIBLE);
                binding.tvPlayerError.setVisibility(INVISIBLE);
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
        binding.btnSearchPlayer.setEnabled(isTextNotEmpty);
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
            }
//            } else if (menuItem.getItemId() == R.id.searchPlayerPerTag) {
//                binding.tvPlayer.setVisibility(VISIBLE);
//                binding.textInputLayout2.setVisibility(VISIBLE);
//                binding.btnSearchPlayer.setVisibility(VISIBLE);
//                isMenuSelected = true;
//                updateButtonState();
//            }
            return false;
        });
        popup.show();
    }

    private void animateViews(TextInputLayout textInputLayout) {
        if (!isMoved) {
            ObjectAnimator moveX = ObjectAnimator.ofFloat(textInputLayout, "translationX", 190f); // Verschiebt nach rechts
            ObjectAnimator moveY = ObjectAnimator.ofFloat(textInputLayout, "translationY", -180f); // Verschiebt nach oben

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(textInputLayout, "scaleX", 0.6f); // Verkleinert in X-Richtung
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(textInputLayout, "scaleY", 0.6f); // Verkleinert in Y-Richtung
            binding.textInputLayout2.setVisibility(VISIBLE);
            ObjectAnimator fadeInLayout = ObjectAnimator.ofFloat(binding.textInputLayout2, "alpha", 0f, 1f);

            long duration = 1000;
            moveX.setDuration(duration);
            moveY.setDuration(duration);
            scaleX.setDuration(duration);
            scaleY.setDuration(duration);
            fadeInLayout.setDuration(duration);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(moveX, moveY, scaleX, scaleY, fadeInLayout);
            animatorSet.start();

            isMoved = true;
        }
    }

    private void reverseAnimation(TextInputLayout autoCompleteTextView) {
        if (isMoved) {
//            binding.listLayoutFoundClans.setVisibility(INVISIBLE);
//            binding.listLayoutClanMembers.setVisibility(INVISIBLE);
            ObjectAnimator moveX = ObjectAnimator.ofFloat(autoCompleteTextView, "translationX", 0f); // Zurück an die ursprüngliche X-Position
            ObjectAnimator moveY = ObjectAnimator.ofFloat(autoCompleteTextView, "translationY", 0f); // Zurück an die ursprüngliche Y-Position

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleX", 1f); // Zurück auf die ursprüngliche Größe in X-Richtung
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleY", 1f); // Zurück auf die ursprüngliche Größe in Y-Richtung

            ObjectAnimator fadeInTextInputField = ObjectAnimator.ofFloat(binding.textInputLayout2, "alpha", 0f, 1f);

            long duration = 1000;
            fadeInTextInputField.setDuration(duration);
            moveX.setDuration(duration);
            moveY.setDuration(duration);
            scaleX.setDuration(duration);
            scaleY.setDuration(duration);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(moveX, moveY, scaleX, scaleY, fadeInTextInputField);
            animatorSet.start();
            isMoved = false;
        }
    }

    public void searchPlayerPerTag(String clanTag) {
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
                Player player = logicViewModel.getPlayer();
                binding.tvPlayerName.setText(player.getName());
                binding.tvPlayerName.setVisibility(VISIBLE);
                binding.listLayoutPlayer.setVisibility(VISIBLE);
                popup.getMenu().findItem(R.id.playerTroops).setEnabled(true);
                popup.getMenu().findItem(R.id.playerAchievments).setEnabled(true);
                popup.getMenu().findItem(R.id.playerSpells).setEnabled(true);
                popup.getMenu().findItem(R.id.playerHeroes).setEnabled(true);
                binding.playerCP.setVisibility(INVISIBLE);
            }

            @Override
            public void onError(String error) {
                binding.tvPlayerError.setVisibility(VISIBLE);
                binding.playerCP.setVisibility(INVISIBLE);
            }
        });
    }
}