package net.htlgkr.lugern.coctracker.list.listFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.viewmodels.LogicViewModel;

public class AchievmentFragment extends Fragment {

    private int columnCount = 1;
    private LogicViewModel logicViewModel;

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
        logicViewModel = new ViewModelProvider(requireActivity()).get(LogicViewModel.class);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }

            logicViewModel.observableItemsPlayerAchievments.observe(getViewLifecycleOwner(), items -> {
//                if (items.isEmpty()) {
//                    Log.e("ClanFragment", "Die Liste ist leer!");
//                }
//                Log.d("ClanFragment", "Items erhalten: " + items.size());


//                MyAchievmentRecyclerViewAdapter adapter = new MyAchievmentRecyclerViewAdapter(items);
//                recyclerView.setAdapter(adapter);

//                adapter.setOnItemClickListener(position -> Log.i("LIST FRAGMENT", "clicked an achievment " + position));
            });
        }
        return view;
    }
}