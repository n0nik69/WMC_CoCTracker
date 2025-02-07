package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentLeagueScreenBinding;
import net.htlgkr.lugern.coctracker.models.shared.Country;
import net.htlgkr.lugern.coctracker.models.shared.CountryList;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class LeagueScreen extends Fragment {
    FragmentLeagueScreenBinding binding;
    MainViewModel mainViewModel;
    LogicViewModel logicViewModel;

    private boolean isMoved = false;

    public LeagueScreen() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeagueScreenBinding.inflate(inflater, container, false);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        logicViewModel.init(requireContext());
        List<Country> countryList = CountryList.getCountries();
        List<String> countryNames = new ArrayList<>();

        for (Country country : countryList) {
            if (country.isCountry()) {
                countryNames.add(country.getName());
            }
        }

        binding.listLayoutLeague.setVisibility(INVISIBLE);

        AutoCompleteTextView autoCompleteTextView = binding.autoCompleteCountry;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, countryNames);
        autoCompleteTextView.setAdapter(adapter);
        binding.btnSearchLeague.setEnabled(false);
        RadioGroup radioGroup = binding.radioGroup;
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            updateSearchButtonState(autoCompleteTextView, checkedId);
        });

        binding.btnSearchLeague.setOnClickListener(v -> {
            animateViews(autoCompleteTextView);
            String selectedCountry = autoCompleteTextView.getText().toString();
            int countryId = getCountryIdByName(selectedCountry);
            String selectedRadioOption = getSelectedRadioOption(radioGroup);
            binding.cpLeague.setVisibility(VISIBLE);
            loadTopClansOrPlayers(countryId, selectedRadioOption);
        });


        autoCompleteTextView.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                updateSearchButtonState(autoCompleteTextView, radioGroup.getCheckedRadioButtonId());
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {
            }
        });
        autoCompleteTextView.setOnClickListener(v -> {
            reverseAnimation(autoCompleteTextView);
            binding.tvNoCountry.setText("");
        });

        return binding.getRoot();
    }

    private String getSelectedRadioOption(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            View selectedRadioButton = radioGroup.findViewById(selectedId);
            return ((android.widget.RadioButton) selectedRadioButton).getText().toString();
        }
        return "";
    }

    private void updateSearchButtonState(AutoCompleteTextView autoCompleteTextView, int checkedId) {
        boolean isCountrySelected = checkedId != -1;
        boolean isCountryNameValid = !autoCompleteTextView.getText().toString().isEmpty();

        binding.btnSearchLeague.setEnabled(isCountrySelected && isCountryNameValid);

        binding.btnSearchLeague.setEnabled(binding.btnSearchLeague.isEnabled());
    }

    private int getCountryIdByName(String countryName) {
        List<Country> countryList = CountryList.getCountries();
        for (Country country : countryList) {
            if (country.getName().equalsIgnoreCase(countryName)) {
                return country.getId();
            }
        }
        return -1;
    }

    private void loadTopClansOrPlayers(int countryId, String checkedOption) {
        String location = "";
        switch (checkedOption) {
            case "Top Players":
                location = "players";
                break;
            case "Top Clans":
                location = "clans";
                break;
            case "Top Builder Base Players":
                location = "players-builder-base";
                break;
            case "Top Builder Base Clans":
                location = "clans-builder-base";
                break;
        }

        String url = "https://api.clashofclans.com/v1/locations/" + countryId + "/rankings/" + location + "?limit=25";
        logicViewModel.setApiUrl(url);
        String finalLocation = location;
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                binding.cpLeague.setVisibility(INVISIBLE);
                if (finalLocation.equals("clans") || finalLocation.equals("clans-builder-base")) {
                    logicViewModel.loadTopClansFromJson(json);
                    mainViewModel.showScreen(MainViewModel.topClansList);
                } else {
                    logicViewModel.loadTopPlayerFromJson(json);
                    mainViewModel.showScreen(MainViewModel.topPlayersList);
                }
            }

            @Override
            public void onError(String error) {
                binding.tvNoCountry.setText("No Country/Player found");
                binding.cpLeague.setVisibility(INVISIBLE);
            }
        });
    }

    private void animateViews(AutoCompleteTextView autoCompleteTextView) {
        if (!isMoved) {
            ObjectAnimator moveX = ObjectAnimator.ofFloat(autoCompleteTextView, "translationX", 220f);
            ObjectAnimator moveY = ObjectAnimator.ofFloat(autoCompleteTextView, "translationY", -185f);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleX", 0.6f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleY", 0.6f);
            binding.listLayoutLeague.setVisibility(VISIBLE);
            ObjectAnimator fadeInLayout = ObjectAnimator.ofFloat(binding.listLayoutLeague, "alpha", 0f, 1f);
            ObjectAnimator fadeOutButton = ObjectAnimator.ofFloat(binding.btnSearchLeague, "alpha", 1f, 0f);
            ObjectAnimator fadeOutRadioGroup = ObjectAnimator.ofFloat(binding.radioGroup, "alpha", 1f, 0f);

            long duration = 1000;
            moveX.setDuration(duration);
            moveY.setDuration(duration);
            scaleX.setDuration(duration);
            scaleY.setDuration(duration);
            fadeInLayout.setDuration(duration);
            fadeOutButton.setDuration(duration);
            fadeOutRadioGroup.setDuration(duration);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(moveX, moveY, scaleX, scaleY, fadeInLayout, fadeOutButton, fadeOutRadioGroup);
            animatorSet.start();

            isMoved = true;
        }
    }

    private void reverseAnimation(AutoCompleteTextView autoCompleteTextView) {
        if (isMoved) {
            ObjectAnimator moveX = ObjectAnimator.ofFloat(autoCompleteTextView, "translationX", 0f);
            ObjectAnimator moveY = ObjectAnimator.ofFloat(autoCompleteTextView, "translationY", 0f);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleX", 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(autoCompleteTextView, "scaleY", 1f);

            ObjectAnimator fadeOutLayout = ObjectAnimator.ofFloat(binding.listLayoutLeague, "alpha", 1f, 0f);
            ObjectAnimator fadeInButton = ObjectAnimator.ofFloat(binding.btnSearchLeague, "alpha", 0f, 1f);
            ObjectAnimator fadeInRadioGroup = ObjectAnimator.ofFloat(binding.radioGroup, "alpha", 0f, 1f);

            long duration = 1000;
            fadeOutLayout.setDuration(duration);
            fadeInButton.setDuration(duration);
            fadeInRadioGroup.setDuration(duration);
            moveX.setDuration(duration);
            moveY.setDuration(duration);
            scaleX.setDuration(duration);
            scaleY.setDuration(duration);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(moveX, moveY, scaleX, scaleY, fadeInButton, fadeOutLayout, fadeInRadioGroup);
            animatorSet.start();
            binding.listLayoutLeague.setVisibility(GONE);
            isMoved = false;
        }
    }
}
