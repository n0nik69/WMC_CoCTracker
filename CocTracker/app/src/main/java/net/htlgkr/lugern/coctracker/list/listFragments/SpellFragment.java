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
import net.htlgkr.lugern.coctracker.list.adapter.MySpellRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

public class SpellFragment extends Fragment {

    private int columnCount = 2;
    private LogicViewModel logicViewModel;

    public SpellFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spell_list, container, false);
        View listView = view.findViewById(R.id.list);
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);

        // Set the adapter
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }

            logicViewModel.observableItemsPlayerSpells.observe(getViewLifecycleOwner(), items -> {
                Log.d("ClanFragment", "Items erhalten: " + items.size());

                if (items.isEmpty()) {
                    Log.e("ClanFragment", "Die Liste ist leer!");
                }

                MySpellRecyclerViewAdapter adapter = new MySpellRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter);

            });
        }
        return view;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_spell_list, container, false);
//        View listView = view.findViewById(R.id.list);
//        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
//
//        // Set the adapter
//        if (listView instanceof RecyclerView) {
//            Context context = listView.getContext();
//            RecyclerView recyclerView = (RecyclerView) listView;
//
//            // Verwende GridLayoutManager mit 2 Spalten
//            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
//
//            logicViewModel.observableItemsPlayerSpells.observe(getViewLifecycleOwner(), items -> {
//                Log.d("ClanFragment", "Items erhalten: " + items.size());
//
//                if (items.isEmpty()) {
//                    Log.e("ClanFragment", "Die Liste ist leer!");
//                }
//
//                MySpellRecyclerViewAdapter adapter = new MySpellRecyclerViewAdapter(items);
//                recyclerView.setAdapter(adapter);
//            });
//        }
//        return view;
//    }

}