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
import net.htlgkr.lugern.coctracker.list.adapter.MyTopPlayersRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.models.player.PlayerRanking;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

public class TopPlayersFragment extends Fragment {

    private int columnCount = 1;
    private LogicViewModel logicViewModel;

    public TopPlayersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_players_list, container, false);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        final MyTopPlayersRecyclerViewAdapter[] adapter = new MyTopPlayersRecyclerViewAdapter[1];
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }
            logicViewModel.observableItemsPlayerRanking.observe(getViewLifecycleOwner(), items -> {
                adapter[0] = new MyTopPlayersRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter[0]);

                adapter[0].setOnItemClickListener(position -> {
                    PlayerRanking clickedPlayer = items.get(position);
                    if (clickedPlayer != null) {
                        String playerTag = clickedPlayer.getTag();
                        Log.i("LIST FRAGMENT", "Clicked on position: " + position + ", Player: " + playerTag);
                        PlayerScreen playerScreen = (PlayerScreen) getParentFragmentManager().findFragmentByTag("PLAYERSCREEN");
                        if (playerScreen != null) {
                            playerScreen.searchPlayerPerTag(playerTag);
                        } else {
                            Log.e("TopPlayerFragment", "PlayerScreen Fragment not found");
                        }
                    }
                });

            });
        }
        return view;
    }
}