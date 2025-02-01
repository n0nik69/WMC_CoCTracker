package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.callback.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentCardBinding;
import net.htlgkr.lugern.coctracker.einModel.einPlayer.Player;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyClanRecyclerViewAdapter extends RecyclerView.Adapter<MyClanRecyclerViewAdapter.ViewHolder> {

    private final List<Player> values;
    private MyOnCardClickListener onCardClickListener;

    public MyClanRecyclerViewAdapter(List<Player> items) {
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
        Player clan = values.get(position);

        holder.binding.tvCardTownhallLvl.setText(clan.getTownHallLevel());
//        holder.binding.ivCardTownhallLvl.setImageResource(clan.getIvTownhallLvl());

        holder.binding.tvPlayerNameInClan.setText(clan.getName());
//        holder.binding.tvClanRole.setText(clan.getTvRole());

        holder.binding.tvCardExpLvl.setText(clan.getExpLevel());
//        holder.binding.ivCardExpLvl.setImageResource(clan.getIvExpLvl());

        holder.binding.tvCardTrophies.setText(clan.getTrophies());
//        holder.binding.ivCardTrophies.setImageResource(clan.getIvTrophies());

        holder.binding.tvBuildbaseTrophies.setText(clan.getBuilderBaseTrophies());
//        holder.binding.ivCardBuildbaseTrophies.setImageResource(clan.getIvBuildTrophies());

        holder.binding.tvCardDonations.setText(clan.getDonations());
//        holder.binding.tvCardDonationsReceived.setText(clan.getTvDonationsReceived());

        holder.binding.tvCardLeague.setText(clan.getLeague().toString());

//        if (card.getIvLeague() != null) {
//            Glide.with(holder.binding.ivLeague.getContext())
//                    .load(card.getIvLeagueUrl())
//                    .into(holder.binding.ivLeague);
//        } else {
//            holder.binding.ivLeague.setImageResource(card.getIvLeague()); // Standard-Icon
//        }

        holder.clan = clan;
        holder.itemView.setOnClickListener(v -> {
            if (onCardClickListener != null) {
                onCardClickListener.onItemClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Player clan;
        public FragmentCardBinding binding;

        public ViewHolder(FragmentCardBinding binding) {
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