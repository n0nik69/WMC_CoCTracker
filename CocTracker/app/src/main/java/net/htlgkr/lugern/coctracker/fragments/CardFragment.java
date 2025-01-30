package net.htlgkr.lugern.coctracker.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.viewmodels.CardViewModel;
import net.htlgkr.lugern.coctracker.viewmodels.MainViewModel;

public class CardFragment extends Fragment {

    MainViewModel mainViewModel;
    private int columnCount = 1;

    public CardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);

        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (columnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
//            }
////            recyclerView.setAdapter(new MyCardRecyclerViewAdapter(PlaceholderContent.ITEMS));
//        }

        View listView = view.findViewById(R.id.list);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        CardViewModel cardViewModel = new ViewModelProvider(requireActivity()).get(CardViewModel.class);

        if (listView instanceof RecyclerView) {
            Context context = listView.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (columnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
            }

            cardViewModel.observableItems.observe(getViewLifecycleOwner(), items -> {
                MyCardRecyclerViewAdapter adapter = new MyCardRecyclerViewAdapter(cardViewModel.observableItems.getValue());
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(position -> Log.i("LIST FRAGMENT", "clicked " + position));
            });

        }
        return view;
    }
}