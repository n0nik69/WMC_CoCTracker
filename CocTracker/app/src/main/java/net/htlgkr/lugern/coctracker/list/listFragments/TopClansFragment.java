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

    private int columnCount = 1;
    private LogicViewModel logicViewModel;
    private MainViewModel mainViewModel;

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
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
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

                        // Erstelle ein neues ClanScreen und setze das Clan-Tag als Argument
                        ClanScreen clanScreen = new ClanScreen();
                        Bundle bundle = new Bundle();
                        bundle.putString("CLAN_TAG", clanTag);  // Übergebe das Clan-Tag
                        clanScreen.setArguments(bundle);  // Setze die Argumente

                        // Ersetze das Fragment mit dem neuen ClanScreen
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.mainFragment, clanScreen, "CLANSCREEN")
                                .addToBackStack(null)  // Optional: Füge es zum Backstack hinzu, wenn du eine Zurück-Navigation willst
                                .commit();
                    }
                });


            });
        }
        return view;
    }
}