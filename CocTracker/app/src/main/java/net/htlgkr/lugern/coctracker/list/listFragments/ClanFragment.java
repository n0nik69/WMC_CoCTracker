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
import net.htlgkr.lugern.coctracker.fragments.PlayerScreen;
import net.htlgkr.lugern.coctracker.list.adapter.MyClanRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.clan.ClanMember;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

public class ClanFragment extends Fragment {

    LogicViewModel logicViewModel;
    private int columnCount = 1;

    public ClanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);
        View listView = view.findViewById(R.id.list);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        final MyClanRecyclerViewAdapter[] adapter = new MyClanRecyclerViewAdapter[1];
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }

//            logicViewModel.observableItemsClanMember.observe(getViewLifecycleOwner(), items -> {
//                Log.d("ClanFragment", "Items erhalten: " + items.size());
//
//                if (items.isEmpty()) {
//                    Log.e("ClanFragment", "Die Liste ist leer!");
//                }
//
//                MyClanRecyclerViewAdapter adapter = new MyClanRecyclerViewAdapter(items);
//                recyclerView.setAdapter(adapter);
//
//                adapter.setOnItemClickListener(position -> Log.i("LIST FRAGMENT", "clicked " + position));
//            });

            logicViewModel.observableItemsClanMember.observe(getViewLifecycleOwner(), items -> {
                adapter[0] = new MyClanRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter[0]);

                adapter[0].setOnItemClickListener(position -> {
                    ClanMember clanMember = items.get(position);
                    if (clanMember != null) {
                        String playerTag = clanMember.getTag();
                        Log.i("LIST FRAGMENT", "Clicked on position: " + position + ", Player: " + playerTag);

                        // Erstelle ein neues PlayerScreen und übergebe das Player-Tag als Argument
                        PlayerScreen playerScreen = new PlayerScreen();
                        Bundle bundle = new Bundle();
                        bundle.putString("PLAYER_TAG", playerTag);  // Übergebe das Player-Tag
                        playerScreen.setArguments(bundle);  // Setze die Argumente

                        // Ersetze das Fragment mit dem neuen PlayerScreen
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.mainFragment, playerScreen, "PLAYERSCREEN")  // Optional: Füge es zum Backstack hinzu
                                .commit();
                    }
                });
            });
        }
        return view;
    }
}