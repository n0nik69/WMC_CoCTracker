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
import net.htlgkr.lugern.coctracker.list.adapter.MyHeroRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

public class HeroFragment extends Fragment {

    public HeroFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hero_list, container, false);
        View listView = view.findViewById(R.id.list);
        LogicViewModel logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);

        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            logicViewModel.observableItemsPlayerHeroes.observe(getViewLifecycleOwner(), items -> {
                Log.d("ClanFragment", "Items erhalten: " + items.size());

                if (items.isEmpty()) {
                    Log.e("ClanFragment", "Die Liste ist leer!");
                }

                MyHeroRecyclerViewAdapter adapter = new MyHeroRecyclerViewAdapter(items);
                recyclerView.setAdapter(adapter);

            });
        }
        return view;
    }
}