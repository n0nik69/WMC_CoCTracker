package net.htlgkr.lugern.coctracker.list.listFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.fragments.ClanScreen;
import net.htlgkr.lugern.coctracker.list.adapter.MyFoundClanRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.clan.Clan;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class FoundClanFragment extends Fragment {

    MainViewModel mainViewModel;
    private LogicViewModel logicViewModel;
    private int columnCount = 1;

    public FoundClanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foundclan_list, container, false);
        View listView = view.findViewById(R.id.list);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        final MyFoundClanRecyclerViewAdapter[] adapter = new MyFoundClanRecyclerViewAdapter[1];
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }

            logicViewModel.observableItemsFoundClans.observe(getViewLifecycleOwner(), items -> {
                adapter[0] = new MyFoundClanRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter[0]);


                adapter[0].setOnFoundClanClickListener(position -> {
                    Clan clickedClan = items.get(position);
                    if (clickedClan != null) {
                        String clanTag = clickedClan.getTag();
                        Log.i("LIST FRAGMENT", "Clicked on position: " + position + ", ClanTag: " + clanTag);

                        // Hole das bestehende ClanScreen-Fragment
                        ClanScreen clanScreen = (ClanScreen) getParentFragmentManager().findFragmentByTag("CLANSCREEN");

                        // Stelle sicher, dass das Fragment gefunden wurde und rufe die Methode auf
                        if (clanScreen != null) {
                            clanScreen.searchClanPerTag(clanTag);
                        } else {
                            Log.e("FoundClanFragment", "ClanScreen Fragment not found");
                        }
                    }
                });

            });
        }
        return view;
    }
}