package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.databinding.FragmentTopClansBinding;

public class MyTopClansRecyclerViewAdapter extends RecyclerView.Adapter<MyTopClansRecyclerViewAdapter.ViewHolder> {

//    private final List<PlaceholderItem> mValues;

//    public MyTopClansRecyclerViewAdapter(List<PlaceholderItem> items) {
//        mValues = items;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentTopClansBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
//        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
//        public PlaceholderItem mItem;

        public ViewHolder(FragmentTopClansBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}