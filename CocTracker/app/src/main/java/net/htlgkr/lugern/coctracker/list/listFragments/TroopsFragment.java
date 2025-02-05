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
import net.htlgkr.lugern.coctracker.list.adapter.MyTroopsRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class TroopsFragment extends Fragment {

    MainViewModel mainViewModel;
    private int columnCount = 3;
    private LogicViewModel logicViewModel;

    public TroopsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_troops_list, container, false);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        View listView = view.findViewById(R.id.list);

        // Set the adapter
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }

            logicViewModel.obserVableItemsPlayerTroops.observe(getViewLifecycleOwner(), items -> {
                Log.d("ClanFragment", "Items erhalten: " + items.size());

                if (items.isEmpty()) {
                    Log.e("ClanFragment", "Die Liste ist leer!");
                }

                MyTroopsRecyclerViewAdapter adapter = new MyTroopsRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter);

            });
        }
        return view;
    }
}