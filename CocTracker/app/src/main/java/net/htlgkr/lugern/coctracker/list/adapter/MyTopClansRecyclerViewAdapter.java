package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.callbacks.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentTopClansBinding;
import net.htlgkr.lugern.coctracker.models.clan.ClanRanking;

import java.util.List;

public class MyTopClansRecyclerViewAdapter extends RecyclerView.Adapter<MyTopClansRecyclerViewAdapter.ViewHolder> {


    private final List<ClanRanking> values;
    //    private MyTopClansRecyclerViewAdapter myTopClansRecyclerViewAdapter;
    private MyOnCardClickListener myOnCardClickListener;

    public MyTopClansRecyclerViewAdapter(List<ClanRanking> items) {
        values = items;
    }

    public void setOnItemClickListener(MyOnCardClickListener myOnCardClickListener) {
        this.myOnCardClickListener = myOnCardClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentTopClansBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ClanRanking clanRanking = values.get(position);
//        Picasso.get()
//                .load(clanRanking.getBadgeUrls().getLarge())
//                .into(holder.binding.ivClanRankBadge);

        holder.binding.tvClanRank.setText(String.valueOf(clanRanking.getRank()));
        holder.binding.tvClanRankMembers.setText(clanRanking.getMembers() + "/50");
        holder.binding.tvClanRankName.setText(clanRanking.getName());
//        holder.binding.tvClanRankTag.setText(clanRanking.getTag());
        holder.binding.tvClanRankTrophies.setText(String.valueOf(clanRanking.getClanPoints()));

        holder.clanRanking = clanRanking;
        holder.itemView.setOnClickListener(v -> {
            if (myOnCardClickListener != null) {
                myOnCardClickListener.onItemClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ClanRanking clanRanking;
        public FragmentTopClansBinding binding;

        public ViewHolder(FragmentTopClansBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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