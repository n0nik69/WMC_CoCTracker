package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentClanScreenBinding;
import net.htlgkr.lugern.coctracker.list.adapter.MyFoundClanRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

public class ClanScreen extends Fragment {
    FragmentClanScreenBinding binding;
    LogicViewModel logicViewModel;
    String clanTagOrName;
    ImageView imageView;
    String url;
    CircularProgressIndicator progressIndicator;
    private boolean isMenuSelected = false;

    public ClanScreen() {
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        String clanTag = getArguments() != null ? getArguments().getString("CLAN_TAG") : null;
        if (clanTag != null) {
            loadClanFromName(clanTag);
        }
    }

    public void loadClanFromName(String clanTagOrName) {
        updateButtonState();
        logicViewModel.init(requireContext());
        binding.btnSearchClan.setVisibility(INVISIBLE);
        ConstraintLayout listLayout = binding.listLayout;
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) listLayout.getLayoutParams();
        params.topMargin = 100;

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
                System.out.println();

                logicViewModel.loadClanFromJson(json);
                Clan clan = logicViewModel.getClan();
                binding.tvClanName.setText(clan.getName());
                binding.tvClanDescription.setText(clan.getDescription());
                binding.tvClanDescription.setVisibility(VISIBLE);
                binding.tvClanName.setVisibility(VISIBLE);
                binding.listLayout.setVisibility(VISIBLE);

                Picasso.get()
                        .load(clan.getBadgeUrls().getLarge())
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                binding.ivClanBadge.setVisibility(VISIBLE);
                                progressIndicator.setVisibility(INVISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {
                                progressIndicator.setVisibility(INVISIBLE);
                            }
                        });
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
                binding.tvClanName.setVisibility(VISIBLE);
                binding.cp.setVisibility(INVISIBLE);
                binding.tvClanName.setText("Kein Clan gefunden");
            }
        });
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
        imageView = binding.ivClanBadge;
        progressIndicator = binding.cp;
        binding.listLayout.setVisibility(INVISIBLE);
        binding.tvClans.setOnClickListener(view -> {
            logicViewModel.setSearchPerName(false);
            logicViewModel.setSearchPerTag(false);
            logicViewModel.setShowTopClansList(false);
            showMenu(view, R.menu.popup_menu_clans);
        });

        logicViewModel.observableItemsClan.observe(getViewLifecycleOwner(), items -> {
            MyFoundClanRecyclerViewAdapter adapter = new MyFoundClanRecyclerViewAdapter(logicViewModel.observableItemsClan.getValue());
            adapter.setOnFoundClanClickListener(position -> {
                Clan clan = logicViewModel.observableItemsClan.getValue().get(position);
                if (clan != null) {
                    String clanTag = clan.getTag();
                    Log.i("LIST FRAGMENT", "Clicked on position: " + position + ", ClanTag: " + clanTag);

                }
            });
        });

        logicViewModel.observableItemsFoundClans.observe(getViewLifecycleOwner(), items -> {
            MyFoundClanRecyclerViewAdapter adapter = new MyFoundClanRecyclerViewAdapter(logicViewModel.observableItemsFoundClans.getValue());
            adapter.setOnFoundClanClickListener(position -> {
                Clan clan = logicViewModel.observableItemsFoundClans.getValue().get(position);
                if (clan != null) {
                    String clanTag = clan.getTag();
                    Log.i("LIST FRAGMENT", "Clicked on position: " + position + ", ClanTag: " + clanTag);
                }
            });
        });


        binding.btnSearchClan.setEnabled(false);
        binding.btnSearchClan.setOnClickListener(view -> {
            testMethod(imageView, progressIndicator, logicViewModel);
        });

        binding.tiClan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateButtonState();

                binding.btnSearchClan.setVisibility(View.VISIBLE);
                binding.ivClanBadge.setVisibility(View.INVISIBLE);
                binding.tvClanDescription.setVisibility(View.INVISIBLE);
                binding.tvClanName.setVisibility(View.INVISIBLE);
                binding.listLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return binding.getRoot();
    }

    private void testMethod(ImageView imageView, CircularProgressIndicator progressIndicator, LogicViewModel logicViewModel) {
        binding.ivClanBadge.setImageDrawable(null);
        progressIndicator.show();

        clanTagOrName = String.valueOf(binding.tiClan.getText()).trim().toUpperCase();

        if (Boolean.TRUE.equals(logicViewModel.getSearchPerTag().getValue())) {
            if (!clanTagOrName.startsWith("#")) {
                clanTagOrName = "%23" + clanTagOrName;
            } else {
                clanTagOrName = clanTagOrName.replace("#", "%23");
            }
            url = "https://api.clashofclans.com/v1/clans/" + clanTagOrName;
        } else if (Boolean.TRUE.equals(logicViewModel.getSearchPerName().getValue())) {
            String encodedName = clanTagOrName.replace(" ", "%20");
            url = "https://api.clashofclans.com/v1/clans?name=" + encodedName + "&limit=10";
        } else {
            url = "https://api.clashofclans.com/v1/locations/32000022/rankings/clans?limit=10";
        }

        binding.cp.setVisibility(VISIBLE);

        logicViewModel.setApiUrl(url);
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                System.out.println();
                if (Boolean.TRUE.equals(logicViewModel.getSearchPerTag().getValue())) {
                    logicViewModel.loadClanFromJson(json);
                    Clan clan = logicViewModel.getClan();
                    binding.tvClanName.setText(clan.getName());
                    binding.tvClanDescription.setText(clan.getDescription());
                    binding.tvClanDescription.setVisibility(VISIBLE);
                    binding.tvClanName.setVisibility(VISIBLE);
                    binding.cp.setVisibility(INVISIBLE);
                    binding.listLayout.setVisibility(VISIBLE);
                    ConstraintLayout listLayout = binding.listLayout;
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) listLayout.getLayoutParams();
                    params.topMargin = 600;

                    Picasso.get()
                            .load(clan.getBadgeUrls().getMedium())
                            .into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    binding.ivClanBadge.setVisibility(VISIBLE);
                                    progressIndicator.setVisibility(INVISIBLE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    progressIndicator.setVisibility(INVISIBLE);
                                }
                            });

                } else if (Boolean.TRUE.equals(logicViewModel.getSearchPerName().getValue())) {
                    logicViewModel.loadFoundClansFromJson(json);

                    binding.cp.setVisibility(INVISIBLE);
                    binding.listLayout.setVisibility(VISIBLE);
                    ConstraintLayout listLayout = binding.listLayout;
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) listLayout.getLayoutParams();
                    params.topMargin = 240;
                } else {
                    displayTopClans();
                }
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
                binding.tvClanName.setVisibility(VISIBLE);
                binding.cp.setVisibility(INVISIBLE);
                binding.tvClanName.setText("Kein Clan gefunden");
            }
        });

        binding.btnSearchClan.setVisibility(INVISIBLE);
        binding.ivClanBadge.setVisibility(VISIBLE);
    }

    private void displayTopClans() {
        Log.i("klasdf", "displayTop clans ageajdfffffffffffffffffff");
        url = "https://api.clashofclans.com/v1/locations/32000022/rankings/clans?limit=25";
        logicViewModel.setApiUrl(url);
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                Log.d("DEBUG", "API-Antwort erhalten: " + json);
                logicViewModel.loadTopClansFromJson(json);
                binding.cp.setVisibility(INVISIBLE);
                binding.listLayout.setVisibility(VISIBLE);
                binding.listLayout.invalidate();

                ConstraintLayout listLayout = binding.listLayout;
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) listLayout.getLayoutParams();
                params.topMargin = 16;
            }

            @Override
            public void onError(String error) {
                System.out.println(error);
            }
        });
    }

    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            TextInputEditText textInputLayout = binding.tiClan;

            if (menuItem.getItemId() == R.id.searchClanPerName) {
                logicViewModel.setSearchPerName(true);
                binding.textInputLayout.setVisibility(VISIBLE);
                binding.btnSearchClan.setVisibility(VISIBLE);
                binding.tvClanName.setVisibility(INVISIBLE);
                binding.ivClanBadge.setVisibility(INVISIBLE);
                binding.tvClanDescription.setVisibility(INVISIBLE);
                binding.listLayout.setVisibility(INVISIBLE);
                textInputLayout.setHint("Clan per Name suchen");

            } else if (menuItem.getItemId() == R.id.searchClanPerTag) {
                logicViewModel.setSearchPerTag(true);
                binding.textInputLayout.setVisibility(VISIBLE);
                binding.btnSearchClan.setVisibility(VISIBLE);
                binding.tvClanName.setVisibility(INVISIBLE);
                binding.ivClanBadge.setVisibility(INVISIBLE);
                binding.tvClanDescription.setVisibility(INVISIBLE);
                textInputLayout.setHint("Clan per Tag suchen");

            } else if (menuItem.getItemId() == R.id.topClans) {
                logicViewModel.setShowTopClansList(true);
                binding.textInputLayout.setVisibility(INVISIBLE);
                binding.btnSearchClan.setVisibility(INVISIBLE);
                binding.tvClanName.setVisibility(INVISIBLE);
                binding.ivClanBadge.setVisibility(INVISIBLE);
                binding.tvClanDescription.setVisibility(INVISIBLE);
                binding.listLayout.setVisibility(VISIBLE);
                displayTopClans();
                return false;
            }

            isMenuSelected = true;
            updateButtonState();
            return true;
        });
        popup.show();
    }

    private void updateButtonState() {
        boolean isTextNotEmpty = !binding.tiClan.getText().toString().trim().isEmpty();
        binding.btnSearchClan.setEnabled(isTextNotEmpty && isMenuSelected);
    }
}