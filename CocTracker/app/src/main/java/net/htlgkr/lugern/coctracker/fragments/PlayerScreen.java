package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.GONE;
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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentPlayerScreenBinding;
import net.htlgkr.lugern.coctracker.models.player.Player;
import net.htlgkr.lugern.coctracker.models.shared.Label;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlayerScreen extends Fragment {
    FragmentPlayerScreenBinding binding;
    LogicViewModel logicViewModel;
    MainViewModel mainViewModel;
    String playerTag;
    String url;
    private boolean isMoved = false;

    public PlayerScreen() {
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String playerTag = getArguments() != null ? getArguments().getString("PLAYER_TAG") : null;
        if (playerTag != null) {
            searchPlayerPerTag(playerTag);
        }
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

        binding.btnSearchPlayer.setEnabled(false);
        binding.btnSearchPlayer.setOnClickListener(view -> {
            binding.playerCP.setVisibility(VISIBLE);
            animateViews(binding.textInputLayout2);
            searchPlayerPerTag("");
        });

        binding.tiPlayerTag.setHint("Enter Playertag");

        binding.tiPlayerTag.setOnClickListener(v -> {
            binding.listLayoutPlayer.setVisibility(GONE);
            binding.playerCP.setVisibility(INVISIBLE);
            showElements(false);
            reverseAnimation(binding.textInputLayout2);
        });

        showElements(false);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        mainViewModel.showScreen(MainViewModel.playerTroops);
                        break;
                    case 1:
                        mainViewModel.showScreen(MainViewModel.playerSpells);
                        break;
                    case 2:
                        mainViewModel.showScreen(MainViewModel.playerHeroes);
                        break;
                    case 3:
                        mainViewModel.showScreen(MainViewModel.playerAchievmentList);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        binding.tiPlayerTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateButtonState();
                showElements(false);
                binding.listLayoutPlayer.setVisibility(INVISIBLE);
                binding.tvPlayerError.setVisibility(INVISIBLE);
                binding.btnSearchPlayer.setVisibility(View.VISIBLE);
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

    private void animateViews(TextInputLayout textInputLayout) {
        if (!isMoved) {
            ObjectAnimator moveX = ObjectAnimator.ofFloat(textInputLayout, "translationX", 200f); // Verschiebt nach rechts
            ObjectAnimator moveY = ObjectAnimator.ofFloat(textInputLayout, "translationY", -200f); // Verschiebt nach oben

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(textInputLayout, "scaleX", 0.6f); // Verkleinert in X-Richtung
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(textInputLayout, "scaleY", 0.6f); // Verkleinert in Y-Richtung
            binding.textInputLayout2.setVisibility(VISIBLE);
            ObjectAnimator fadeInLayout = ObjectAnimator.ofFloat(binding.textInputLayout2, "alpha", 0f, 1f);
            ObjectAnimator fadeOutButton = ObjectAnimator.ofFloat(binding.btnSearchPlayer, "alpha", 1f, 0f);

            long duration = 1000;
            moveX.setDuration(duration);
            fadeInLayout.setDuration(duration);
            moveY.setDuration(duration);
            scaleX.setDuration(duration);
            scaleY.setDuration(duration);
            fadeInLayout.setDuration(duration);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(moveX, moveY, scaleX, fadeOutButton, scaleY, fadeInLayout);
            animatorSet.start();

            isMoved = true;
        }
    }

    private void reverseAnimation(TextInputLayout autoCompleteTextView) {
        if (isMoved) {
            ObjectAnimator moveX = ObjectAnimator.ofFloat(autoCompleteTextView, "translationX", 0f); // Zurück an die ursprüngliche X-Position
            ObjectAnimator moveY = ObjectAnimator.ofFloat(autoCompleteTextView, "translationY", 0f); // Zurück an die ursprüngliche Y-Position

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleX", 1f); // Zurück auf die ursprüngliche Größe in X-Richtung
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleY", 1f); // Zurück auf die ursprüngliche Größe in Y-Richtung

            ObjectAnimator fadeInTextInputField = ObjectAnimator.ofFloat(binding.textInputLayout2, "alpha", 0f, 1f);
            ObjectAnimator fadeInButton = ObjectAnimator.ofFloat(binding.btnSearchPlayer, "alpha", 0f, 1f);

            long duration = 1000;
            fadeInTextInputField.setDuration(duration);
            fadeInButton.setDuration(duration);
            moveX.setDuration(duration);
            moveY.setDuration(duration);
            scaleX.setDuration(duration);
            scaleY.setDuration(duration);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(moveX, moveY, scaleX, scaleY, fadeInTextInputField, fadeInButton);
            animatorSet.start();
            isMoved = false;
        }
    }

    public void showElements(boolean show) {
        int visibility = show ? View.VISIBLE : GONE;

        binding.tvPlayerDetailBBTrophies.setVisibility(visibility);
        binding.tvPlayerDetailLeague.setVisibility(visibility);
        binding.tvPlayerDetailBBLeague.setVisibility(visibility);
        binding.tvPlayerDetailClanName.setVisibility(visibility);
        binding.tvPlayerDetailClanTag.setVisibility(visibility);
        binding.tvPlayerDetailTrophies.setVisibility(visibility);
        binding.tvPlayerDetailClanTag.setVisibility(visibility);
        binding.ivPlayerBadgeOne.setVisibility(visibility);
        binding.ivPlayerBadgeThree.setVisibility(visibility);
        binding.ivPlayerBadgeTwo.setVisibility(visibility);

        binding.ivPlayerDetailTownhallLevel.setVisibility(visibility);
        binding.ivPlayerDetailBBTownhall.setVisibility(visibility);
        binding.ivPlayerDetailLeague.setVisibility(visibility);
        binding.ivPlayerDetailClan.setVisibility(visibility);
        binding.tvPlayerDetailTownhallLevel.setVisibility(visibility);
        binding.tvPlayerDetailBBTownhallLevel.setVisibility(visibility);
        binding.ivHammer.setVisibility(visibility);
        binding.ivTrophies.setVisibility(visibility);
        binding.tvPlayerDetailName.setVisibility(visibility);
        binding.tvPlayerDetailExpLevel.setVisibility(visibility);
        binding.ivExp.setVisibility(visibility);
        binding.tabLayout.setVisibility(visibility);
    }

    public void searchPlayerPerTag(String clanTag) {
        animateViews(binding.textInputLayout2);
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
                binding.tvPlayerDetailName.setText(player.getName());
                binding.tvPlayerDetailTownhallLevel.setText("TH LVL:" + player.getTownHallLevel());
                binding.tvPlayerDetailExpLevel.setText(String.valueOf(player.getExpLevel()));
                binding.tvPlayerDetailBBLeague.setText(player.getBuilderBaseLeague().getName());
                if (player.getLeague() == null) {
                    binding.tvPlayerDetailLeague.setText("Unranked");
                    binding.ivPlayerDetailLeague.setImageResource(R.drawable.l1);
                } else {
                    binding.tvPlayerDetailLeague.setText(player.getLeague().getName());
                    ImageView ivLeague = binding.ivPlayerDetailLeague;
                    String leagueName = player.getLeague().getName();

                    Map<String, Integer> leagueImages = new HashMap<>();
                    leagueImages.put("Unranked", R.drawable.l1);
                    leagueImages.put("Bronze", R.drawable.l2);
                    leagueImages.put("Silver", R.drawable.l3);
                    leagueImages.put("Gold", R.drawable.l4);
                    leagueImages.put("Crystal", R.drawable.l5);
                    leagueImages.put("Master", R.drawable.l6);
                    leagueImages.put("Champion", R.drawable.l7);
                    leagueImages.put("Titan", R.drawable.l8);
                    leagueImages.put("Legend", R.drawable.l9);

                    String leagueType = leagueName.split(" ")[0];

                    Integer imageRes2 = leagueImages.get(leagueType);
                    ivLeague.setImageResource(Objects.requireNonNullElseGet(imageRes2, () -> R.drawable.l1));

                }
                if (player.getClan() == null) {
                    binding.tvPlayerDetailClanName.setText("Clan: N/A");
                    binding.tvPlayerDetailClanTag.setText("N/A");
                    binding.ivPlayerDetailClan.setImageResource(R.drawable.resource_default);
                } else {
                    binding.tvPlayerDetailClanName.setText("Clan: " + player.getClan().getName());
                    binding.tvPlayerDetailClanTag.setText(player.getClan().getTag());
                    String imageUrl = player.getClan().getBadgeUrls().getLarge();
                    Glide.with(requireContext())
                            .load(imageUrl)
                            .into(binding.ivPlayerDetailClan);
                }

                binding.tvPlayerDetailBBTrophies.setText(String.valueOf(player.getBuilderBaseTrophies()));
                binding.tvPlayerDetailTrophies.setText(String.valueOf(player.getTrophies()));

                binding.tvPlayerDetailBBTownhallLevel.setText("BTH: " + player.getBuilderHallLevel());

                List<Label> labels = player.getLabels();
                int defaultBadge = R.drawable.resource_default;

                if (labels.size() > 0) {
                    Glide.with(requireContext()).load(labels.get(0).getIconUrls().getMedium()).into(binding.ivPlayerBadgeOne);
                } else {
                    Glide.with(requireContext()).load(defaultBadge).into(binding.ivPlayerBadgeOne);
                }

                if (labels.size() > 1) {
                    Glide.with(requireContext()).load(labels.get(1).getIconUrls().getMedium()).into(binding.ivPlayerBadgeTwo);
                } else {
                    Glide.with(requireContext()).load(defaultBadge).into(binding.ivPlayerBadgeTwo);
                }

                if (labels.size() > 2) {
                    Glide.with(requireContext()).load(labels.get(2).getIconUrls().getMedium()).into(binding.ivPlayerBadgeThree);
                } else {
                    Glide.with(requireContext()).load(defaultBadge).into(binding.ivPlayerBadgeThree);
                }


                Map<Integer, Integer> townhallImageMap = new HashMap<>();
                townhallImageMap.put(1, R.drawable.a1);
                townhallImageMap.put(2, R.drawable.a2);
                townhallImageMap.put(3, R.drawable.a3);
                townhallImageMap.put(4, R.drawable.a4);
                townhallImageMap.put(5, R.drawable.a5);
                townhallImageMap.put(6, R.drawable.a6);
                townhallImageMap.put(7, R.drawable.a7);
                townhallImageMap.put(8, R.drawable.a8);
                townhallImageMap.put(9, R.drawable.a9);
                townhallImageMap.put(10, R.drawable.a10);
                townhallImageMap.put(11, R.drawable.a11);
                townhallImageMap.put(12, R.drawable.a12);
                townhallImageMap.put(13, R.drawable.a13);
                townhallImageMap.put(14, R.drawable.a14);
                townhallImageMap.put(15, R.drawable.a15);
                townhallImageMap.put(16, R.drawable.a16);
                townhallImageMap.put(17, R.drawable.a17);

                int townhallLevel = player.getTownHallLevel();
                Integer imageRes = townhallImageMap.get(townhallLevel);
                binding.ivPlayerDetailTownhallLevel.setImageResource(imageRes);

                binding.listLayoutPlayer.setVisibility(VISIBLE);
                binding.playerCP.setVisibility(INVISIBLE);
                showElements(true);
                mainViewModel.showScreen(MainViewModel.playerTroops);
            }

            @Override
            public void onError(String error) {
                binding.tvPlayerError.setVisibility(VISIBLE);
                binding.playerCP.setVisibility(INVISIBLE);
            }
        });
    }
}