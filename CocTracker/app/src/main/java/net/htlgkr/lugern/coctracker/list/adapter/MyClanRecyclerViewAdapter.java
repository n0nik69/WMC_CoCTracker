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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyClanRecyclerViewAdapter extends RecyclerView.Adapter<MyClanRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<ClanMember> values;
    private MyOnCardClickListener onCardClickListener;

    public MyClanRecyclerViewAdapter(ArrayList<ClanMember> items) {
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

        ImageView ivTownhallLvl = holder.binding.ivPlayerTownhallLevel;
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
        ImageView ivBuilderBaseLeague = holder.binding.ivPlayerBuildbaseLeague;
        if (clanMember.getBuilderBaseLeague() != null) {
            String builderBaseLeagueName = clanMember.getBuilderBaseLeague().getName();

            Map<String, String> leagueMapping = new HashMap<>();
            leagueMapping.put("Wood League V", "wood5");
            leagueMapping.put("Wood League IV", "wood4");
            leagueMapping.put("Wood League III", "wood3");
            leagueMapping.put("Wood League II", "wood2");
            leagueMapping.put("Wood League I", "wood1");

            leagueMapping.put("Clay League V", "clay5");
            leagueMapping.put("Clay League IV", "clay4");
            leagueMapping.put("Clay League III", "clay3");
            leagueMapping.put("Clay League II", "clay2");
            leagueMapping.put("Clay League I", "clay1");

            leagueMapping.put("Stone League V", "stone5");
            leagueMapping.put("Stone League IV", "stone4");
            leagueMapping.put("Stone League III", "stone3");
            leagueMapping.put("Stone League II", "stone2");
            leagueMapping.put("Stone League I", "stone1");

            leagueMapping.put("Copper League V", "copper5");
            leagueMapping.put("Copper League IV", "copper4");
            leagueMapping.put("Copper League III", "copper3");
            leagueMapping.put("Copper League II", "copper2");
            leagueMapping.put("Copper League I", "copper1");

            leagueMapping.put("Brass League III", "brass3");
            leagueMapping.put("Brass League II", "brass2");
            leagueMapping.put("Brass League I", "brass1");

            leagueMapping.put("Iron League III", "iron3");
            leagueMapping.put("Iron League II", "iron2");
            leagueMapping.put("Iron League I", "iron1");

            leagueMapping.put("Steel League III", "steel3");
            leagueMapping.put("Steel League II", "steel2");
            leagueMapping.put("Steel League I", "steel1");

            leagueMapping.put("Titanium League III", "titanium3");
            leagueMapping.put("Titanium League II", "titanium2");
            leagueMapping.put("Titanium League I", "titanium1");

            leagueMapping.put("Platinum League III", "platinum3");
            leagueMapping.put("Platinum League II", "platinum2");
            leagueMapping.put("Platinum League I", "platinum1");

            leagueMapping.put("Emerald League III", "emerald3");
            leagueMapping.put("Emerald League II", "emerald2");
            leagueMapping.put("Emerald League I", "emerald1");

            leagueMapping.put("Ruby League III", "ruby3");
            leagueMapping.put("Ruby League II", "ruby2");
            leagueMapping.put("Ruby League I", "ruby1");

            leagueMapping.put("Diamond League", "diamond");
            String mappedLeague = leagueMapping.getOrDefault(builderBaseLeagueName, "wood5");

            int resourceId = holder.itemView.getContext().getResources()
                    .getIdentifier(mappedLeague, "drawable", holder.itemView.getContext().getPackageName());

            ivBuilderBaseLeague.setImageResource(resourceId);
        }


        holder.binding.tvPlayerTownhallLevel.setText("RH-Lvl: " + clanMember.getTownHallLevel());
        holder.binding.tvPlayerNameInClan.setText(clanMember.getName());
        holder.binding.tvCardXPLevel.setText("XP-Lvl: " + clanMember.getExpLevel());
        holder.binding.tvPlayerTrophies.setText(String.valueOf(clanMember.getTrophies()));
        holder.binding.tvPlayerBuildbaseTrophies.setText(String.valueOf(clanMember.getBuilderBaseTrophies()));
        holder.binding.tvClanRole.setText(clanMember.getRole().name());
        holder.binding.tvCardDonations.setText(String.valueOf(clanMember.getDonations()));
        holder.binding.tvPlayerBuildbaseLeague.setText(clanMember.getBuilderBaseLeague().getName());
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