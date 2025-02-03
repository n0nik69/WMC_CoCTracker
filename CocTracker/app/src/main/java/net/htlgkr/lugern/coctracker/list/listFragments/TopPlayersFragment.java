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
import net.htlgkr.lugern.coctracker.list.adapter.MyTopPlayersRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

/**
 * A fragment representing a list of Items.
 */
public class TopPlayersFragment extends Fragment {

    MainViewModel mainViewModel;
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
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }
            logicViewModel.observableItemsPlayerRanking.observe(getViewLifecycleOwner(), items -> {
                MyTopPlayersRecyclerViewAdapter adapter = new MyTopPlayersRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(position -> Log.i("LIST FRAGMENT", "clicked top players list " + position));
            });
        }
        return view;
    }
}