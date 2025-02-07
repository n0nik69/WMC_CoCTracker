package net.htlgkr.lugern.coctracker.list.listFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.fragments.ClanScreen;
import net.htlgkr.lugern.coctracker.list.adapter.MyTopClansRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.clan.ClanRanking;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

public class TopClansFragment extends Fragment {

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
        LogicViewModel logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        final MyTopClansRecyclerViewAdapter[] adapter = new MyTopClansRecyclerViewAdapter[1];

        View listView = view.findViewById(R.id.list);
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            logicViewModel.observableItemsClanRanking.observe(getViewLifecycleOwner(), items -> {
                adapter[0] = new MyTopClansRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter[0]);

                adapter[0].setOnItemClickListener(position -> {
                    ClanRanking clickedClan = items.get(position);
                    if (clickedClan != null) {
                        String clanTag = clickedClan.getTag();
                        Log.i("LIST FRAGMENT", "Clicked on position: " + position + ", ClanTag: " + clanTag);

                        ClanScreen clanScreen = new ClanScreen();
                        Bundle bundle = new Bundle();
                        bundle.putString("CLAN_TAG", clanTag);
                        clanScreen.setArguments(bundle);

                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.mainFragment, clanScreen, "CLANSCREEN")
                                .commit();
                    }
                });


            });
        }
        return view;
    }
}