package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.callbacks.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentTopPlayersBinding;
import net.htlgkr.lugern.coctracker.models.player.PlayerRanking;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTopPlayersRecyclerViewAdapter extends RecyclerView.Adapter<MyTopPlayersRecyclerViewAdapter.ViewHolder> {

    private final List<PlayerRanking> values;
    private MyOnCardClickListener myOnCardClickListener;

    public MyTopPlayersRecyclerViewAdapter(List<PlayerRanking> items) {
        values = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentTopPlayersBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//
        PlayerRanking playerRanking = values.get(position);

        holder.binding.tvTopPlayerLevel.setText(String.valueOf(playerRanking.getExpLevel()));
        holder.binding.tvTopPlayerAttacksWon.setText("Attack Wins: " + playerRanking.getAttackWins());
        holder.binding.tvTopPlayerDefensesWon.setText("Defenses Won: " + playerRanking.getDefenseWins());
        holder.binding.tvTopPlayerName.setText(playerRanking.getName());
        holder.binding.tvTopPlayerRank.setText(String.valueOf(playerRanking.getRank()));
        holder.binding.tvTopPlayerTrophies.setText(String.valueOf(playerRanking.getTrophies()));

        holder.playerRanking = playerRanking;
        holder.itemView.setOnClickListener(v -> {
            if (myOnCardClickListener != null) {
                myOnCardClickListener.onItemClick(holder.getLayoutPosition());
            }
        });
    }

    public void setOnItemClickListener(MyOnCardClickListener myOnCardClickListener) {
        this.myOnCardClickListener = myOnCardClickListener;
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public FragmentTopPlayersBinding binding;
        public PlayerRanking playerRanking;


        public ViewHolder(FragmentTopPlayersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
//            binding.tv
        }

        @Override
        public String toString() {
            return super.toString() + " ";
        }

        @Override
        public void onClick(View v) {
            myOnCardClickListener.onItemClick(getLayoutPosition());
        }
    }
}