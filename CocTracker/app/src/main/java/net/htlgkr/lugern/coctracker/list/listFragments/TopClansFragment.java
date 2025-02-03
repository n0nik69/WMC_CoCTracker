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
import net.htlgkr.lugern.coctracker.list.adapter.MyTopClansRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.list.listViewModels.TopClansViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class TopClansFragment extends Fragment {

    MainViewModel mainViewModel;
    private int columnCount = 1;

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
        View listView = view.findViewById(R.id.list);
        TopClansViewModel topClansViewModel = new ViewModelProvider(requireActivity()).get(TopClansViewModel.class);
        // Set the adapter
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }

            topClansViewModel.observableItems.observe(getViewLifecycleOwner(), items -> {
//                MyTopClansRecyclerViewAdapter adapter = new MyTopClansRecyclerViewAdapter(topClansViewModel.observableItems.getValue());
                MyTopClansRecyclerViewAdapter adapter = new MyTopClansRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(position -> Log.i("LIST FRAGMENT", "clicked " + position));
            });
        }
        return view;
    }
}