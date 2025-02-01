package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.callback.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentCardBinding;
import net.htlgkr.lugern.coctracker.list.listModel.ClanCard;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyClanRecyclerViewAdapter extends RecyclerView.Adapter<MyClanRecyclerViewAdapter.ViewHolder> {

    private final List<ClanCard> values;
    private MyOnCardClickListener onCardClickListener;

    public MyClanRecyclerViewAdapter(List<ClanCard> items) {
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
        ClanCard clanCard = values.get(position);

        holder.binding.tvCardTownhallLvl.setText(clanCard.getTvTownhallLvl());
        holder.binding.ivCardTownhallLvl.setImageResource(clanCard.getIvTownhallLvl());

        holder.binding.tvPlayerNameInClan.setText(clanCard.getTvName());
        holder.binding.tvClanRole.setText(clanCard.getTvRole());

        holder.binding.tvCardExpLvl.setText(clanCard.getTvExpLvl());
        holder.binding.ivCardExpLvl.setImageResource(clanCard.getIvExpLvl());

        holder.binding.tvCardTrophies.setText(clanCard.getTvTrophies());
        holder.binding.ivCardTrophies.setImageResource(clanCard.getIvTrophies());

        holder.binding.tvBuildbaseTrophies.setText(clanCard.getTvBuildTrophies());
        holder.binding.ivCardBuildbaseTrophies.setImageResource(clanCard.getIvBuildTrophies());

        holder.binding.tvCardDonations.setText(clanCard.getTvDonations());
        holder.binding.tvCardDonationsReceived.setText(clanCard.getTvDonationsReceived());

        holder.binding.tvCardLeague.setText(clanCard.getTvLeague());

//        if (card.getIvLeague() != null) {
//            Glide.with(holder.binding.ivLeague.getContext())
//                    .load(card.getIvLeagueUrl())
//                    .into(holder.binding.ivLeague);
//        } else {
//            holder.binding.ivLeague.setImageResource(card.getIvLeague()); // Standard-Icon
//        }

        holder.clanCard = clanCard;
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
        public ClanCard clanCard;
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