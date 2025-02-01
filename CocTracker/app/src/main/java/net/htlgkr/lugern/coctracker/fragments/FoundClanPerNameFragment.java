package net.htlgkr.lugern.coctracker.fragments;

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
import net.htlgkr.lugern.coctracker.viewmodels.FoundClanViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class FoundClanPerNameFragment extends Fragment {

    MainViewModel mainViewModel;
    private int columnCount = 1;

    public FoundClanPerNameFragment() {
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
        FoundClanViewModel foundClanViewModel = new ViewModelProvider(requireActivity()).get(FoundClanViewModel.class);

        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }

            foundClanViewModel.observableItems.observe(getViewLifecycleOwner(), items -> {
                MyFoundClanPerNameRecyclerViewAdapter adapter = new MyFoundClanPerNameRecyclerViewAdapter(foundClanViewModel.observableItems.getValue());
                recyclerView.setAdapter(adapter);
                adapter.setOnFoundClanClickListener(position -> Log.i("LIST FRAGMENT", "clicked " + position));
            });

        }
        return view;
    }
}