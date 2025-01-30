package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

// Laden in ImageView


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentClanScreenBinding;
import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.viewmodels.RequestViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ClanScreen extends Fragment {
    FragmentClanScreenBinding binding;

    public ClanScreen() {
        // Required empty public constructor
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
        ImageView imageView = binding.ivClanBadge;
        RequestViewModel requestViewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);
        requestViewModel.init(requireContext());
        CircularProgressIndicator progressIndicator = binding.cp;
        progressIndicator.show();
        binding.tvClans.setOnClickListener(view -> {
            // Menü-Ressourcen-ID übergeben
            showMenu(view, R.menu.popup_menu);
        });
        binding.cp.setVisibility(GONE);
        binding.btnSearchClan.setOnClickListener(view -> {
            binding.ivClanBadge.setImageDrawable(null);

            String clanTagOrName = String.valueOf(binding.tiClan.getText()).trim().toUpperCase();
            if (clanTagOrName.contains("#")) {
                clanTagOrName = clanTagOrName.replace("#", "%23");
            }

            binding.cp.setVisibility(VISIBLE);

            String url = "https://api.clashofclans.com/v1/clans/" + clanTagOrName;
            requestViewModel.setApiUrl(url);
            requestViewModel.requestData(new HTTPListener<>() {
                @Override
                public void onSuccess(String a) {

                    requestViewModel.loadClanInfo(a);
                    System.out.println();
                    Clan clan = requestViewModel.getClan();
                    binding.tvClanName.setText(clan.getName());
                    binding.tvClanDescription.setText(clan.getDescription());
                    binding.tvClanDescription.setVisibility(VISIBLE);
                    binding.tvClanName.setVisibility(VISIBLE);
                    binding.cp.setVisibility(GONE);
                    Picasso.get()
                            .load(clan.getBadgeUrls().getLarge())
                            .into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    binding.ivClanBadge.setVisibility(VISIBLE);
                                    progressIndicator.setVisibility(GONE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    progressIndicator.setVisibility(GONE);
                                }
                            });
                }

                @Override
                public void onError(String error) {
                    System.out.println(error);
                }
            });

            binding.tiClan.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    binding.btnSearchClan.setVisibility(VISIBLE);
                    binding.ivClanBadge.setVisibility(GONE);
                    binding.tvClanDescription.setVisibility(GONE);
                    binding.tvClanName.setVisibility(GONE);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            binding.btnSearchClan.setVisibility(GONE);
            binding.ivClanBadge.setVisibility(VISIBLE);

        });


        // Lade das Bild mit Picasso

        return binding.getRoot();
    }

    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.clanInfoItem) {
                return true;
            } else if (menuItem.getItemId() == R.id.clanWarLogItem) {
                return true;
            } else return menuItem.getItemId() == R.id.currentLeagueItem;
        });

        popup.show();
    }


}