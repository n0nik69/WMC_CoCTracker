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
import net.htlgkr.lugern.coctracker.list.adapter.MyTopClansRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.clan.ClanRanking;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class TopClansFragment extends Fragment {

    MainViewModel mainViewModel;
    private int columnCount = 1;
    private LogicViewModel logicViewModel;

    public TopClansFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_clans_list, container, false);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        final MyTopClansRecyclerViewAdapter[] adapter = new MyTopClansRecyclerViewAdapter[1];

        View listView = view.findViewById(R.id.list);
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }

            logicViewModel.observableItemsClanRanking.observe(getViewLifecycleOwner(), items -> {
                adapter[0] = new MyTopClansRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter[0]);
                
                adapter[0].setOnItemClickListener(position -> {
                    ClanRanking clickedClan = items.get(position);
                    if (clickedClan != null) {
                        String clanTag = clickedClan.getTag();
                        Log.i("LIST FRAGMENT", "Clicked on position: " + position + ", ClanTag: " + clanTag);

                        ClanScreen clanScreen = (ClanScreen) getParentFragmentManager().findFragmentByTag("CLANSCREEN");

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