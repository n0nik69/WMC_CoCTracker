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
import net.htlgkr.lugern.coctracker.list.adapter.MyAchievmentRecyclerViewAdapter;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

public class AchievmentFragment extends Fragment {

    public AchievmentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievment_list, container, false);
        View listView = view.findViewById(R.id.list);
        LogicViewModel logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            logicViewModel.observableItemsPlayerAchievments.observe(getViewLifecycleOwner(), items -> {
                Log.d("AchievmentFragment", "Items erhalten: " + (items != null ? items.size() : "null"));

                if (items == null || items.isEmpty()) {
                    Log.e("AchievmentFragment", "Die Liste ist leer oder null!");
                } else {
                    MyAchievmentRecyclerViewAdapter adapter = new MyAchievmentRecyclerViewAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
            });

        }
        return view;
    }
}