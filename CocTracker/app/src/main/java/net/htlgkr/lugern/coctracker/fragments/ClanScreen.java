package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentClanScreenBinding;
import net.htlgkr.lugern.coctracker.list.adapter.MyClanRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.models.shared.Label;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

import java.util.List;

public class ClanScreen extends Fragment {
    FragmentClanScreenBinding binding;
    LogicViewModel logicViewModel;
    String clanTagOrName;
    MainViewModel mainViewModel;
    ImageView imageView;
    String url;
    CircularProgressIndicator progressIndicator;
    private boolean isMoved = false;
    private boolean searchByTag = true;

    public ClanScreen() {
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        String clanTag = getArguments() != null ? getArguments().getString("CLAN_TAG") : null;
        if (clanTag != null) {
//            isMoved = true;
            searchClanPerTag(clanTag);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentClanScreenBinding.inflate(inflater, container, false);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        logicViewModel.init(requireContext());
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        imageView = binding.ivClanBadge;
        progressIndicator = binding.cp;
        binding.btnSearchClan.setEnabled(false);
        binding.textInputLayout.setVisibility(VISIBLE);
        binding.listLayoutFoundClans.setVisibility(INVISIBLE);

        logicViewModel.observableItemsClanMember.observe(getViewLifecycleOwner(), items -> {
            MyClanRecyclerViewAdapter adapter = new MyClanRecyclerViewAdapter(logicViewModel.observableItemsClanMember.getValue());
            adapter.setOnItemClickListener(position -> Log.i("jlkasdf", String.valueOf(position)));
        });
        showElements(false);

        binding.tiClan.setOnClickListener(v -> {
            binding.tvClanError.setVisibility(INVISIBLE);
            showElements(false);
            reverseAnimation(binding.textInputLayout);
        });

        binding.tiClan.setHint("Enter Clantag");

        binding.btnSearchClan.setOnClickListener(view -> {
            animateViews(binding.textInputLayout);

            binding.cp.setVisibility(View.VISIBLE);

            if (searchByTag) {
                binding.listLayoutFoundClans.setVisibility(INVISIBLE);
                binding.listLayoutClanMembers.setVisibility(VISIBLE);
                searchClanPerTag("");

            } else {
                binding.listLayoutClanMembers.setVisibility(INVISIBLE);
                binding.listLayoutFoundClans.setVisibility(VISIBLE);
                searchClanPerName();
            }
        });

        TextInputEditText textInputLayout = binding.tiClan;


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        searchByTag = true;
                        textInputLayout.setHint("Enter Clantag");
                        break;
                    case 1:
                        searchByTag = false;
                        textInputLayout.setHint("Enter Clanname");
                        break;
                }
                binding.tiClan.setText("");
                binding.btnSearchClan.setVisibility(VISIBLE);
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.btnSearchClan.setVisibility(VISIBLE);


        binding.tiClan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateButtonState();
                showElements(false);
                binding.btnSearchClan.setVisibility(View.VISIBLE);
                binding.listLayoutFoundClans.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return binding.getRoot();
    }

    public void showElements(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;

        binding.tvClanName.setVisibility(visibility);
        binding.tvClanDescription.setVisibility(visibility);
        binding.tvClanType.setVisibility(visibility);
        binding.tvClanLocationName.setVisibility(visibility);
        binding.tvClanRequiredTrophies.setVisibility(visibility);
        binding.tvClanLevel.setVisibility(visibility);
        binding.tvClanRequiredTownhall.setVisibility(visibility);
        binding.tvClanPoints.setVisibility(visibility);
        binding.ivClanBadge.setVisibility(visibility);
        binding.clanBadgeOne.setVisibility(visibility);
        binding.clanBadgeTwo.setVisibility(visibility);
        binding.clanBadgeThree.setVisibility(visibility);
    }


    public void searchClanPerTag(String clanTag) {

        animateViews(binding.textInputLayout);
        binding.listLayoutFoundClans.setVisibility(GONE);
        if (clanTag.isEmpty()) {
            clanTagOrName = String.valueOf(binding.tiClan.getText()).trim().toUpperCase();
        } else {
            clanTagOrName = clanTag;
        }
        if (!clanTagOrName.startsWith("#")) {
            clanTagOrName = "%23" + clanTagOrName;
        } else {
            clanTagOrName = clanTagOrName.replace("#", "%23");
        }

        url = "https://api.clashofclans.com/v1/clans/" + clanTagOrName;
        logicViewModel.setApiUrl(url);
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                logicViewModel.loadClanFromJson(json);
                mainViewModel.showScreen(MainViewModel.clanMemberList);
                binding.listLayoutClanMembers.setVisibility(VISIBLE);
                binding.btnSearchClan.setVisibility(VISIBLE);
                binding.cp.setVisibility(INVISIBLE);
                Clan clan = logicViewModel.getClan();

                binding.tvClanName.setText(clan.getName());
                binding.tvClanDescription.setText(clan.getDescription());
                binding.tvClanType.setText(String.valueOf(clan.getType()));
                binding.tvClanLocationName.setText(clan.getLocation().getName());
                binding.tvClanRequiredTrophies.setText("Required Trophies: " + clan.getRequiredTrophies());
                binding.tvClanLevel.setText("Level: " + clan.getClanLevel());
                String imageUrl = clan.getBadgeUrls().getLarge();
                Glide.with(requireContext())
                        .load(imageUrl)
                        .into(binding.ivClanBadge);

                binding.tvClanRequiredTownhall.setText("Min RH: " + clan.getRequiredTownhallLevel());
                binding.tvClanPoints.setText("Points: " + clan.getClanPoints());


                List<Label> labels = clan.getLabels();
                if (labels.size() > 0) {
                    Glide.with(requireContext()).load(labels.get(0).getIconUrls().getMedium()).into(binding.clanBadgeOne);
                }
                if (labels.size() > 1) {
                    Glide.with(requireContext()).load(labels.get(1).getIconUrls().getMedium()).into(binding.clanBadgeTwo);
                }
                if (labels.size() > 2) {
                    Glide.with(requireContext()).load(labels.get(2).getIconUrls().getMedium()).into(binding.clanBadgeThree);
                }

                showElements(true);
            }

            @Override
            public void onError(String error) {
                binding.cp.setVisibility(INVISIBLE);
                binding.listLayoutClanMembers.setVisibility(GONE);
                binding.listLayoutFoundClans.setVisibility(GONE);
                binding.tvClanError.setVisibility(VISIBLE);
                binding.tvClanError.setText("Clan not found");
            }
        });
    }

    private void searchClanPerName() {
        clanTagOrName = String.valueOf(binding.tiClan.getText()).trim().toUpperCase();
        String encodedName = clanTagOrName.replace(" ", "%20");
        url = "https://api.clashofclans.com/v1/clans?name=" + encodedName + "&limit=10";
        logicViewModel.setApiUrl(url);
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                if (json.contains("\"items\":[]")) {
                    binding.tvClanError.setText("Clan not found!");
                } else {
                    logicViewModel.loadFoundClansFromJson(json);


                    mainViewModel.showScreen(MainViewModel.foundClansList);
                    binding.cp.setVisibility(INVISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                //there wont be an error for sure (nah realtalk), nein wirklich es kommt immer eine antwort
            }
        });
    }

    private void animateViews(TextInputLayout textInputLayout) {
        if (!isMoved) {

            ObjectAnimator moveX = ObjectAnimator.ofFloat(textInputLayout, "translationX", 200f); // Verschiebt nach rechts
            ObjectAnimator moveY = ObjectAnimator.ofFloat(textInputLayout, "translationY", -200f); // Verschiebt nach oben

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(textInputLayout, "scaleX", 0.6f); // Verkleinert in X-Richtung
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(textInputLayout, "scaleY", 0.6f); // Verkleinert in Y-Richtung
            binding.textInputLayout.setVisibility(VISIBLE);
            ObjectAnimator fadeOutTabs = ObjectAnimator.ofFloat(binding.tabLayout, "alpha", 1f, 0f);
            ObjectAnimator fadeOutButton = ObjectAnimator.ofFloat(binding.btnSearchClan, "alpha", 1f, 0f);

            long duration = 1000;
            fadeOutTabs.setDuration(duration);
            fadeOutButton.setDuration(duration);
            moveX.setDuration(duration);
            moveY.setDuration(duration);
            scaleX.setDuration(duration);
            scaleY.setDuration(duration);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(fadeOutTabs, fadeOutButton, moveX, moveY, scaleX, scaleY);
            animatorSet.start();

            isMoved = true;
        }
    }


    private void reverseAnimation(TextInputLayout autoCompleteTextView) {
        if (isMoved) {
            binding.listLayoutFoundClans.setVisibility(INVISIBLE);
            binding.listLayoutClanMembers.setVisibility(INVISIBLE);

            ObjectAnimator moveX = ObjectAnimator.ofFloat(autoCompleteTextView, "translationX", 0f); // Zurück an die ursprüngliche X-Position
            ObjectAnimator moveY = ObjectAnimator.ofFloat(autoCompleteTextView, "translationY", 0f); // Zurück an die ursprüngliche Y-Position

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleX", 1f); // Zurück auf die ursprüngliche Größe in X-Richtung
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleY", 1f); // Zurück auf die ursprüngliche Größe in Y-Richtung
            binding.tabLayout.setVisibility(VISIBLE);
            ObjectAnimator fadeInTextInputField = ObjectAnimator.ofFloat(binding.textInputLayout, "alpha", 0f, 1f);
            ObjectAnimator fadeInButton = ObjectAnimator.ofFloat(binding.btnSearchClan, "alpha", 0f, 1f);
            ObjectAnimator fadeInTabs = ObjectAnimator.ofFloat(binding.tabLayout, "alpha", 0f, 1f);

            long duration = 1000;
            fadeInTextInputField.setDuration(duration);
            moveX.setDuration(duration);
            moveY.setDuration(duration);
            scaleX.setDuration(duration);
            scaleY.setDuration(duration);
            fadeInTabs.setDuration(duration);
            fadeInButton.setDuration(duration);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(moveX, moveY, scaleX, scaleY, fadeInTextInputField, fadeInTabs, fadeInButton);
            animatorSet.start();

            isMoved = false;
        }
    }

    private void updateButtonState() {
        boolean isTextNotEmpty = !binding.tiClan.getText().toString().trim().isEmpty();
        binding.btnSearchClan.setEnabled(isTextNotEmpty);
    }
}