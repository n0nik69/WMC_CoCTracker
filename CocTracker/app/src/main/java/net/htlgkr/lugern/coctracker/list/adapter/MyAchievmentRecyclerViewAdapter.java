package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.callbacks.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentAchievmentBinding;
import net.htlgkr.lugern.coctracker.models.player.PlayerAchievmentProgress;

import java.util.List;

public class MyAchievmentRecyclerViewAdapter extends RecyclerView.Adapter<MyAchievmentRecyclerViewAdapter.ViewHolder> {

    private final List<PlayerAchievmentProgress> values;
    private MyOnCardClickListener onCardClickListener;

    public MyAchievmentRecyclerViewAdapter(List<PlayerAchievmentProgress> items) {
        values = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentAchievmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PlayerAchievmentProgress achievment = values.get(position);

        // Setze den Namen und die Beschreibungen
        holder.binding.tvAchievmentName.setText(achievment.getName());
        holder.binding.tvAchievmentCompletionInfo.setText(achievment.getCompletionInfo());
        holder.binding.tvAchievmentInfo.setText(achievment.getInfo());

        // Entscheide, welches Bild gesetzt werden soll
        int starImageRes;
        switch (achievment.getStars()) {
            case 1:
                starImageRes = R.drawable.onestars;
                break;
            case 2:
                starImageRes = R.drawable.twostars;
                break;
            case 3:
                starImageRes = R.drawable.threestars;
                break;
            default:
                starImageRes = R.drawable.nostars;
                break;
        }

        holder.binding.ivAchievmentStars.setImageResource(starImageRes);

        holder.achievment = achievment;
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
        public PlayerAchievmentProgress achievment;

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