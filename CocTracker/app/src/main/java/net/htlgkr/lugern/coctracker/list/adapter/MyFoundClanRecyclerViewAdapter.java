package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.htlgkr.lugern.coctracker.callbacks.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentFoundclanBinding;
import net.htlgkr.lugern.coctracker.models.clan.Clan;

import java.util.List;

public class MyFoundClanRecyclerViewAdapter extends RecyclerView.Adapter<MyFoundClanRecyclerViewAdapter.ViewHolder> {

    private final List<Clan> values;
    private MyOnCardClickListener onFoundClanClickListener;

    public MyFoundClanRecyclerViewAdapter(List<Clan> items) {
        values = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentFoundclanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public void setOnFoundClanClickListener(MyOnCardClickListener onFoundClanClickListener) {
        this.onFoundClanClickListener = onFoundClanClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Clan clan = values.get(position);
        String nameAndTag = clan.getName() + " - " + clan.getTag();
        holder.binding.tvFoundClanNameAndTag.setText(nameAndTag);
        holder.binding.tvFoundClanIsFamilyFriendly.setText(
                clan.isFamilyFriendly() ? "Familiengeeignet" : "Familienungeeignet"
        );

        String warFrequencyText;

        switch (clan.getWarFrequency()) {
            case oncePerWeek:
                warFrequencyText = "1x/W";
                break;
            case always:
                warFrequencyText = "Immer";
                break;
            case moreThanOncePerWeek:
                warFrequencyText = ">1x/W";
                break;
            case lessThanOncePerWeek:
                warFrequencyText = "<1x/W";
                break;
            case never:
                warFrequencyText = "Nie";
                break;
            case any:
                warFrequencyText = "Egal";
                break;
            default:
                warFrequencyText = "N/A";
                break;
        }

        holder.binding.tvFoundClanWarFrequency.setText(warFrequencyText);

        String typeText;

        switch (clan.getType()) {
            case open:
                typeText = "Offen";
                break;
            case inviteOnly:
                typeText = "Einladung";
                break;
            case closed:
                typeText = "Geschlossen";
                break;
            default:
                typeText = "N/A";
                break;
        }

        holder.binding.tvFoundClanType.setText(typeText);

        holder.binding.tvFoundClanMembers.setText("Mitglieder: " +
                (clan.getMembers() != 0 ? clan.getMembers() : "N/A"));

        holder.binding.tvFoundClanLvl.setText("Clan Lvl: " +
                (clan.getClanLevel() != 0 ? clan.getClanLevel() : "N/A"));

        holder.binding.tvFoundClanMinTHLvl.setText("Min TH Lvl: " +
                (clan.getRequiredTownhallLevel() != 0 ? clan.getRequiredTownhallLevel() : "N/A"));

        holder.binding.tvFoundClanMinTrophies.setText("Min Trophäen: " +
                (clan.getRequiredTrophies() != 0 ? clan.getRequiredTrophies() : "N/A"));

        holder.binding.tvFoundClanCountryCode.setText(
                (clan.getLocation() != null && clan.getLocation().getCountryCode() != null)
                        ? clan.getLocation().getCountryCode() : "N/A");

        String imageUrl = clan.getBadgeUrls().getMedium();

        Picasso.get()
                .load(imageUrl)
                .into(holder.binding.ivFoundClanBadge);

        holder.itemView.setOnClickListener(v -> {
            if (onFoundClanClickListener != null) {
                onFoundClanClickListener.onItemClick(holder.getLayoutPosition());
            }
        });

        holder.clan = clan;
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Clan clan;
        public FragmentFoundclanBinding binding;

        public ViewHolder(FragmentFoundclanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public String toString() {
            return super.toString() + " ";
        }

        @Override
        public void onClick(View v) {
            onFoundClanClickListener.onItemClick(getLayoutPosition());
        }
    }
}