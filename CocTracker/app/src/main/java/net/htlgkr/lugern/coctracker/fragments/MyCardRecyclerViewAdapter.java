package net.htlgkr.lugern.coctracker.fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.htlgkr.lugern.coctracker.callback.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentCardBinding;
import net.htlgkr.lugern.coctracker.models.Card;
import net.htlgkr.lugern.coctracker.models.clan.Clan;

import org.w3c.dom.Text;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCardRecyclerViewAdapter extends RecyclerView.Adapter<MyCardRecyclerViewAdapter.ViewHolder> {

    private final List<Card> values;
    private MyOnCardClickListener onCardClickListener;

    public MyCardRecyclerViewAdapter(List<Card> items) {
        values = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    public void setOnItemClickListener(MyOnCardClickListener onItemClickListener) {
        this.onCardClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = values.get(position);
//        holder.mIdView.setText(values.get(position).id);
//        holder.mContentView.setText(values.get(position).content);
        holder.card = values.get(position);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        public final TextView tv
        public Card card;
        public Clan clan;

        public ViewHolder(FragmentCardBinding binding) {
            super(binding.getRoot());

        }

        @Override
        public String toString() {
            return super.toString() + " ";
        }


        @Override
        public void onClick(View v) {
            onCardClickListener.onItemClick(getLayoutPosition());
        }
    }
}