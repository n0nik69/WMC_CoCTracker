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
import net.htlgkr.lugern.coctracker.einModel.einClan.Clan;
import net.htlgkr.lugern.coctracker.list.adapter.MyFoundClanRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.list.listViewModels.ClanViewModel;
import net.htlgkr.lugern.coctracker.list.listViewModels.FoundClanViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.RequestViewModel;

public class ClanScreen extends Fragment {
    FragmentClanScreenBinding binding;
    RequestViewModel requestViewModel;
    ClanViewModel clanViewModel;
    FoundClanViewModel foundClanViewModel;
    String clanTagOrName;
    ImageView imageView;
    String url;
    CircularProgressIndicator progressIndicator;
    private boolean isMenuSelected = false;

    public ClanScreen() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String clanTag = getArguments() != null ? getArguments().getString("CLAN_TAG") : null;
        if (clanTag != null) {
            loadClanFromName(clanTag);
        }
    }


    public void loadClanFromName(String clanTagOrName) {
        updateButtonState();

        clanViewModel = new ViewModelProvider(requireActivity()).get(ClanViewModel.class);
        foundClanViewModel = new ViewModelProvider(requireActivity()).get(FoundClanViewModel.class);
        requestViewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);
        requestViewModel.init(requireContext());
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
        requestViewModel.setApiUrl(url);
        requestViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                System.out.println();

                requestViewModel.loadClanInfo(json);
                Clan clan = requestViewModel.getClan();
                binding.tvClanName.setText(clan.getName());
                binding.tvClanDescription.setText(clan.getDescription());
                binding.tvClanDescription.setVisibility(VISIBLE);
                binding.tvClanName.setVisibility(VISIBLE);
                binding.cp.setVisibility(INVISIBLE);
                clanViewModel.loadDataFromJson(json);
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
                        }); ////////////////////////////////von der api die daten , always, never, onceperweek etc
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
        // Inflate the layout for this fragment
        binding = FragmentClanScreenBinding.inflate(inflater, container, false);
        clanViewModel = new ViewModelProvider(requireActivity()).get(ClanViewModel.class);
        foundClanViewModel = new ViewModelProvider(requireActivity()).get(FoundClanViewModel.class);
        requestViewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);
        requestViewModel.init(requireContext());
        imageView = binding.ivClanBadge;
        progressIndicator = binding.cp;
        binding.cp.setVisibility(INVISIBLE);
        binding.listLayout.setVisibility(INVISIBLE);
        binding.tvClans.setOnClickListener(view -> showMenu(view, R.menu.popup_menu_clans));

        foundClanViewModel.observableItems.observe(getViewLifecycleOwner(), items -> {
            MyFoundClanRecyclerViewAdapter adapter = new MyFoundClanRecyclerViewAdapter(foundClanViewModel.observableItems.getValue());
            adapter.setOnFoundClanClickListener(position -> {
                Clan clan = foundClanViewModel.observableItems.getValue().get(position);
                if (clan != null) {
                    String clanTag = clan.getTag();
                    Log.i("LIST FRAGMENT", "Clicked on position: " + position + ", ClanTag: " + clanTag);

                }
            });
        });


        binding.btnSearchClan.setEnabled(false);
        binding.btnSearchClan.setOnClickListener(view -> testMethod(clanViewModel, imageView, progressIndicator, foundClanViewModel));

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

    private void testMethod(ClanViewModel clanViewModel, ImageView imageView, CircularProgressIndicator progressIndicator, FoundClanViewModel foundClanViewModel) {
        binding.ivClanBadge.setImageDrawable(null);
        progressIndicator.show();

        clanTagOrName = String.valueOf(binding.tiClan.getText()).trim().toUpperCase();

        if (Boolean.TRUE.equals(requestViewModel.isSearchPerTag().getValue())) {
            if (!clanTagOrName.startsWith("#")) {
                clanTagOrName = "%23" + clanTagOrName;
            } else {
                clanTagOrName = clanTagOrName.replace("#", "%23");
            }
            url = "https://api.clashofclans.com/v1/clans/" + clanTagOrName;
        } else {
            String encodedName = clanTagOrName.replace(" ", "%20");
            url = "https://api.clashofclans.com/v1/clans?name=" + encodedName + "&limit=10";
        }

        binding.cp.setVisibility(VISIBLE);

        requestViewModel.setApiUrl(url);
        requestViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                System.out.println();
                if (Boolean.TRUE.equals(requestViewModel.isSearchPerTag().getValue())) {
                    requestViewModel.loadClanInfo(json);
                    Clan clan = requestViewModel.getClan();
                    binding.tvClanName.setText(clan.getName());
                    binding.tvClanDescription.setText(clan.getDescription());
                    binding.tvClanDescription.setVisibility(VISIBLE);
                    binding.tvClanName.setVisibility(VISIBLE);
                    binding.cp.setVisibility(INVISIBLE);
                    clanViewModel.loadDataFromJson(json);
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

                } else {
                    requestViewModel.loadFoundClanInfo(json);
                    foundClanViewModel.loadDataFromJson(json);
                    binding.cp.setVisibility(INVISIBLE);
                    binding.listLayout.setVisibility(VISIBLE);
                    ConstraintLayout listLayout = binding.listLayout;
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) listLayout.getLayoutParams();
                    params.topMargin = 8;
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

    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            TextInputEditText textInputLayout = binding.tiClan;

            if (menuItem.getItemId() == R.id.searchClanPerName) {
                binding.btnSearchClan.setVisibility(VISIBLE);
                binding.tvClanName.setVisibility(INVISIBLE);
                binding.ivClanBadge.setVisibility(INVISIBLE);
                binding.tvClanDescription.setVisibility(INVISIBLE);
                binding.listLayout.setVisibility(INVISIBLE);
                textInputLayout.setHint("Clan per Name suchen");
                binding.tiClan.setHint("nigga");
                requestViewModel.setSearchPerTag(false);
            } else if (menuItem.getItemId() == R.id.searchClanPerTag) {
                binding.btnSearchClan.setVisibility(VISIBLE);
                binding.tvClanName.setVisibility(INVISIBLE);
                binding.ivClanBadge.setVisibility(INVISIBLE);
                binding.tvClanDescription.setVisibility(INVISIBLE);
                binding.listLayout.setVisibility(INVISIBLE);
                textInputLayout.setHint("Clan per Tag suchen");
                requestViewModel.setSearchPerTag(true);
            } else {
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