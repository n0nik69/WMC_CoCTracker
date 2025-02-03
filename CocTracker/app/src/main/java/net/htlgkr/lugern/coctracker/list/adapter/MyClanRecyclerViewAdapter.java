package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.callbacks.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentCardBinding;
import net.htlgkr.lugern.coctracker.models.clan.ClanMember;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyClanRecyclerViewAdapter extends RecyclerView.Adapter<MyClanRecyclerViewAdapter.ViewHolder> {

    private final List<ClanMember> values;
    private MyOnCardClickListener onCardClickListener;

    public MyClanRecyclerViewAdapter(List<ClanMember> items) {
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
        ClanMember clanMember = values.get(position);

        ImageView ivTownhallLvl = holder.binding.ivCardTownhallLvl;
        int[] images = {
                R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5,
                R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10,
                R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15,
                R.drawable.a16, R.drawable.a17
        };
        ivTownhallLvl.setImageResource(images[clanMember.getTownHallLevel() - 1]);

        ImageView ivLeague = holder.binding.ivPlayerLeague;
        String leagueName = clanMember.getLeague().getName();

        Map<String, Integer> leagueImages = new HashMap<>();
        leagueImages.put("Unranked", R.drawable.l1);
        leagueImages.put("Bronze", R.drawable.l2);
        leagueImages.put("Silver", R.drawable.l3);
        leagueImages.put("Gold", R.drawable.l4);
        leagueImages.put("Crystal", R.drawable.l5);
        leagueImages.put("Master", R.drawable.l6);
        leagueImages.put("Champion", R.drawable.l7);
        leagueImages.put("Titan", R.drawable.l8);
        leagueImages.put("Legend", R.drawable.l9);

        String leagueType = leagueName.split(" ")[0];

        Integer imageRes = leagueImages.get(leagueType);
        ivLeague.setImageResource(Objects.requireNonNullElseGet(imageRes, () -> R.drawable.l1));


        holder.binding.tvPlayerTownhallLevel.setText("RH-Lvl: " + clanMember.getTownHallLevel());
        holder.binding.tvPlayerNameInClan.setText(clanMember.getName());
        holder.binding.tvCardXPLevel.setText("XP-Lvl: " + clanMember.getExpLevel());
        holder.binding.tvPlayerTrophies.setText(String.valueOf(clanMember.getTrophies()));
        holder.binding.tvPlayerBuildbaseTrophies.setText(String.valueOf(clanMember.getBuilderBaseTrophies()));
        holder.binding.tvClanRole.setText(clanMember.getRole().name());
        holder.binding.tvCardDonations.setText(String.valueOf(clanMember.getDonations()));
        if (clanMember.getLeague() == null) {
            holder.binding.tvPlayerLeague.setText("N/A");
        } else {
            holder.binding.tvPlayerLeague.setText(clanMember.getLeague().getName());
        }

        holder.clan = clanMember;
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
        public ClanMember clan;
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