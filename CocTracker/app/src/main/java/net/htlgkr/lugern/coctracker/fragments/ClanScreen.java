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
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentClanScreenBinding;
import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.viewmodels.CardViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.RequestViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ClanScreen extends Fragment {
    FragmentClanScreenBinding binding;
    RequestViewModel requestViewModel;

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

        requestViewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);
        requestViewModel.init(requireContext());
        CircularProgressIndicator progressIndicator = binding.cp;
        progressIndicator.show();
        CardViewModel cardViewModel = new ViewModelProvider(requireActivity()).get(CardViewModel.class);
        binding.listLayout.setVisibility(INVISIBLE);
        binding.tvClans.setOnClickListener(view -> {
            // Menü-Ressourcen-ID übergeben
            showMenu(view, R.menu.popup_menu);
        });
        binding.cp.setVisibility(INVISIBLE);
        binding.btnSearchClan.setOnClickListener(view -> {
            binding.ivClanBadge.setImageDrawable(null);

            String clanTagOrName = String.valueOf(binding.tiClan.getText()).trim().toUpperCase();
            String url;
            if (requestViewModel.isSearchPerTag()) {
                if (!clanTagOrName.startsWith("#")) {
                    clanTagOrName = "%23" + clanTagOrName;
                } else {
                    clanTagOrName = clanTagOrName.replace("#", "%23");
                }
                url = "https://api.clashofclans.com/v1/clans/" + clanTagOrName;
            } else {
                // Ersetze Leerzeichen durch %20
                String encodedName = clanTagOrName.replace(" ", "%20");
                url = "https://api.clashofclans.com/v1/clans?name=" + encodedName + "&limit=5";
            }
            

            binding.cp.setVisibility(VISIBLE);

            requestViewModel.setApiUrl(url);
            requestViewModel.requestData(new HTTPListener<>() {
                @Override
                public void onSuccess(String a) {
                    System.out.println();
                    if (requestViewModel.isSearchPerTag()) {
                        requestViewModel.loadClanInfo(a);
                        Clan clan = requestViewModel.getClan();
                        binding.tvClanName.setText(clan.getName());
                        binding.tvClanDescription.setText(clan.getDescription());
                        binding.tvClanDescription.setVisibility(VISIBLE);
                        binding.tvClanName.setVisibility(VISIBLE);
                        binding.cp.setVisibility(INVISIBLE);
                        cardViewModel.loadDataFromJson(a);
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
                }

                @Override
                public void onError(String error) {
                    System.out.println(error);
                    binding.tvClanName.setVisibility(VISIBLE);
                    binding.cp.setVisibility(INVISIBLE);
                    binding.tvClanName.setText("Kein Clan gefunden");
                }
            });

            binding.tiClan.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    binding.btnSearchClan.setVisibility(VISIBLE);
                    binding.ivClanBadge.setVisibility(INVISIBLE);
                    binding.tvClanDescription.setVisibility(INVISIBLE);
                    binding.tvClanName.setVisibility(INVISIBLE);
                    binding.listLayout.setVisibility(INVISIBLE);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            binding.btnSearchClan.setVisibility(INVISIBLE);
            binding.ivClanBadge.setVisibility(VISIBLE);

        });


        // Lade das Bild mit Picasso

        return binding.getRoot();
    }

    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.searchClanPerName) {
                TextInputEditText textInputLayout = binding.tiClan;
                textInputLayout.setHint("Clan per Name suchen");
//                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
//                ft.detach(this).attach(this).commit();
                requestViewModel.setSearchPerTag(false);
                return true;
            } else if (menuItem.getItemId() == R.id.searchClanPerTag) {
                TextInputEditText textInputLayout = binding.tiClan;
                textInputLayout.setHint("Clan per Tag suchen");
                requestViewModel.setSearchPerTag(true);
                return true;
            }
            return false;
        });

        popup.show();
    }


}