package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.callback.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentAchievmentBinding;
import net.htlgkr.lugern.coctracker.list.listModel.AchievmentCard;

import java.util.List;

public class MyAchievmentRecyclerViewAdapter extends RecyclerView.Adapter<MyAchievmentRecyclerViewAdapter.ViewHolder> {

    private final List<AchievmentCard> values;
    private MyOnCardClickListener onCardClickListener;

    public MyAchievmentRecyclerViewAdapter(List<AchievmentCard> items) {
        values = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentAchievmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //hier daten
        AchievmentCard achievmentCard = values.get(position);

        holder.binding.ivAchievmentStars.setImageResource(achievmentCard.getStars());
        holder.binding.tvAchievmentName.setText(achievmentCard.getName());
        holder.binding.tvAchievmentCompletionInfo.setText(achievmentCard.getCompletionInfo());
        holder.binding.tvAchievmentInfo.setText(achievmentCard.getInfo());
        holder.achievmentCard = achievmentCard;
        holder.itemView.setOnClickListener(v -> {
            if (onCardClickListener != null) {
                onCardClickListener.onItemClick(holder.getLayoutPosition());
            }
        });
    }

    public void setOnItemClickListener(MyOnCardClickListener onItemClickListener) {
        this.onCardClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public FragmentAchievmentBinding binding;
        public AchievmentCard achievmentCard;

        public ViewHolder(FragmentAchievmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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