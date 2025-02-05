package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.databinding.FragmentTroopsBinding;
import net.htlgkr.lugern.coctracker.models.player.PlayerItemLevel;

import java.util.List;

public class MyTroopsRecyclerViewAdapter extends RecyclerView.Adapter<MyTroopsRecyclerViewAdapter.ViewHolder> {

    private final List<PlayerItemLevel> values;

    public MyTroopsRecyclerViewAdapter(List<PlayerItemLevel> items) {
        values = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentTroopsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PlayerItemLevel playerItemLevel = values.get(position);
        holder.binding.tvTroopName.setText(playerItemLevel.getName());
        holder.binding.tvTroopLevel.setText(playerItemLevel.getLevel() + "/" + playerItemLevel.getMaxLevel());
        holder.binding.ivTroop.setImageResource(playerItemLevel.getPlayerItemImage());

        holder.playerItemLevel = playerItemLevel;
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FragmentTroopsBinding binding;
        public PlayerItemLevel playerItemLevel;

        public ViewHolder(FragmentTroopsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public String toString() {
            return super.toString() + " ";
        }
    }
}