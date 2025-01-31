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
        Card card = values.get(position);

        holder.binding.tvCardTownhallLvl.setText(card.getTvTownhallLvl());
        holder.binding.ivCardTownhallLvl.setImageResource(card.getIvTownhallLvl());

        holder.binding.tvPlayerNameInClan.setText(card.getTvName());
        holder.binding.tvClanRole.setText(card.getTvRole());

        holder.binding.tvCardExpLvl.setText(card.getTvExpLvl());
        holder.binding.ivCardExpLvl.setImageResource(card.getIvExpLvl());

        holder.binding.tvCardTrophies.setText(card.getTvTrophies());
        holder.binding.ivCardTrophies.setImageResource(card.getIvTrophies());

        holder.binding.tvBuildbaseTrophies.setText(card.getTvBuildTrophies());
        holder.binding.ivCardBuildbaseTrophies.setImageResource(card.getIvBuildTrophies());

        holder.binding.tvCardDonations.setText(card.getTvDonations());
        holder.binding.tvCardDonationsReceived.setText(card.getTvDonationsReceived());
//        holder.binding.ivCardDonations.setImageResource(card.getIvDonations());

        holder.binding.tvCardLeague.setText(card.getTvLeague());

//        if (card.getIvLeague() != null) {
//            Glide.with(holder.binding.ivLeague.getContext())
//                    .load(card.getIvLeagueUrl())
//                    .into(holder.binding.ivLeague);
//        } else {
//            holder.binding.ivLeague.setImageResource(card.getIvLeague()); // Standard-Icon
//        }

        holder.card = card;
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
        //        public final TextView tv
        public Card card;
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