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
import net.htlgkr.lugern.coctracker.fragments.PlayerScreen;
import net.htlgkr.lugern.coctracker.list.adapter.MyClanRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.clan.ClanMember;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

public class ClanFragment extends Fragment {

    LogicViewModel logicViewModel;

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
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            logicViewModel.observableItemsClanMember.observe(getViewLifecycleOwner(), items -> {
                adapter[0] = new MyClanRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter[0]);

                adapter[0].setOnItemClickListener(position -> {
                    ClanMember clanMember = items.get(position);
                    if (clanMember != null) {
                        String playerTag = clanMember.getTag();
                        Log.i("LIST FRAGMENT", "Clicked on position: " + position + ", Player: " + playerTag);

                        PlayerScreen playerScreen = new PlayerScreen();
                        Bundle bundle = new Bundle();
                        bundle.putString("PLAYER_TAG", playerTag);
                        playerScreen.setArguments(bundle);

                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.mainFragment, playerScreen, "PLAYERSCREEN")
                                .commit();
                    }
                });
            });
        }
        return view;
    }
}