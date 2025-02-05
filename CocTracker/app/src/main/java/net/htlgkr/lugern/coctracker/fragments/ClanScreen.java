package net.htlgkr.lugern.coctracker.fragments;

import static android.view.View.GONE;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentClanScreenBinding;
import net.htlgkr.lugern.coctracker.list.adapter.MyClanRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class ClanScreen extends Fragment {
    FragmentClanScreenBinding binding;
    LogicViewModel logicViewModel;
    String clanTagOrName;
    MainViewModel mainViewModel;
    ImageView imageView;
    String url;
    CircularProgressIndicator progressIndicator;
    private Runnable selectedAction;
    private boolean isMenuSelected = false;

    public ClanScreen() {
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        String clanTag = getArguments() != null ? getArguments().getString("CLAN_TAG") : null;
        if (clanTag != null) {
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
        binding.listLayoutFoundClans.setVisibility(INVISIBLE);
        binding.tvClans.setOnClickListener(view -> showMenu(view, R.menu.popup_menu_clans));

        logicViewModel.observableItemsClanMember.observe(getViewLifecycleOwner(), items -> {
            MyClanRecyclerViewAdapter adapter = new MyClanRecyclerViewAdapter(logicViewModel.observableItemsClanMember.getValue());
            adapter.setOnItemClickListener(position -> {
                Log.i("jlkasdf", String.valueOf(position));
            });
        });

        binding.btnSearchClan.setOnClickListener(view -> {
            if (selectedAction != null) {
                binding.cp.setVisibility(VISIBLE);
                binding.btnSearchClan.setVisibility(INVISIBLE);
                selectedAction.run();
            }
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
                binding.listLayoutFoundClans.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return binding.getRoot();
    }


    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());
        popup.setOnMenuItemClickListener(menuItem -> {
            TextInputEditText textInputLayout = binding.tiClan;

            if (menuItem.getItemId() == R.id.searchClanPerName) {
                binding.btnSearchClan.setVisibility(VISIBLE);
                binding.textInputLayout.setVisibility(VISIBLE);
                binding.listLayoutFoundClans.setVisibility(INVISIBLE);
                binding.listLayoutClanMembers.setVisibility(INVISIBLE);

                selectedAction = this::searchClanPerName;
                binding.textInputLayout.setVisibility(VISIBLE);
                textInputLayout.setHint("Clan per Name suchen");
                binding.tiClan.setText("");

            } else if (menuItem.getItemId() == R.id.searchClanPerTag) {
                binding.btnSearchClan.setVisibility(VISIBLE);
                binding.textInputLayout.setVisibility(VISIBLE);
                binding.listLayoutClanMembers.setVisibility(INVISIBLE);
                binding.listLayoutFoundClans.setVisibility(INVISIBLE);
                selectedAction = () -> searchClanPerTag("");

                binding.textInputLayout.setVisibility(VISIBLE);
                textInputLayout.setHint("Clan per Tag suchen");
                binding.tiClan.setText("");

            }
//            else if (menuItem.getItemId() == R.id.topClans) {
//                binding.btnSearchClan.setVisibility(INVISIBLE);
//                binding.cp.setVisibility(VISIBLE);
//                binding.textInputLayout.setVisibility(INVISIBLE);
//                binding.listLayoutClanMembers.setVisibility(INVISIBLE);
//                binding.listLayoutTopAndFoundClans.setVisibility(VISIBLE);
//                binding.tiClan.setText("");
//                loadTopClans();
//            }

            binding.btnSearchClan.setVisibility(VISIBLE);
            isMenuSelected = true;
            updateButtonState();
            return true;
        });
        popup.show();
    }


    public void searchClanPerTag(String clanTag) {
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
                binding.textInputLayout.setVisibility(INVISIBLE);
                binding.btnSearchClan.setVisibility(VISIBLE);
                binding.cp.setVisibility(INVISIBLE);
                //nur noch Daten vom Clan anzeigen
            }

            @Override
            public void onError(String error) {
                Log.e("API Error", error);
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
                logicViewModel.loadFoundClansFromJson(json);
                mainViewModel.showScreen(MainViewModel.foundClansList);
                binding.listLayoutFoundClans.setVisibility(VISIBLE);
                binding.textInputLayout.setVisibility(INVISIBLE);
                binding.btnSearchClan.setVisibility(VISIBLE);
                binding.cp.setVisibility(INVISIBLE);
            }

            @Override
            public void onError(String error) {
                Log.e("API Error", error);
            }
        });
    }

    private void loadTopClans() {
        binding.listLayoutClanMembers.setVisibility(GONE);
        url = "https://api.clashofclans.com/v1/locations/32000022/rankings/clans?limit=25";
        logicViewModel.setApiUrl(url);
        logicViewModel.requestData(new HTTPListener<>() {
            @Override
            public void onSuccess(String json) {
                logicViewModel.loadTopClansFromJson(json);
                binding.cp.setVisibility(INVISIBLE);
                binding.listLayoutFoundClans.setVisibility(VISIBLE);
                binding.btnSearchClan.setVisibility(GONE);
                binding.textInputLayout.setVisibility(GONE);
                binding.listLayoutFoundClans.invalidate();
                mainViewModel.showScreen(MainViewModel.topClansList);
            }

            @Override
            public void onError(String error) {
                Log.e("API Error", error);
            }
        });
    }

    private void updateButtonState() {
        boolean isTextNotEmpty = !binding.tiClan.getText().toString().trim().isEmpty();
        binding.btnSearchClan.setEnabled(isTextNotEmpty && isMenuSelected);
    }
}