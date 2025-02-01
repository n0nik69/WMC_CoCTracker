package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.htlgkr.lugern.coctracker.callback.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentFoundclanBinding;
import net.htlgkr.lugern.coctracker.list.listModel.FoundClanCard;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFoundClanRecyclerViewAdapter extends RecyclerView.Adapter<MyFoundClanRecyclerViewAdapter.ViewHolder> {

    private final List<FoundClanCard> values;
    private MyOnCardClickListener onFoundClanClickListener;

    public MyFoundClanRecyclerViewAdapter(List<FoundClanCard> items) {
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
        FoundClanCard foundClanCard = values.get(position);
        String nameAndTag = foundClanCard.getName() + " - " + foundClanCard.getTag();
        holder.binding.tvFoundClanNameAndTag.setText(nameAndTag);
        holder.binding.tvFoundClanIsFamilyFriendly.setText(
                foundClanCard.isFamilyFriendly() ? "Familiengeeignet" : "Familienungeeignet"
        );

        String warFrequencyText;

        switch (foundClanCard.getWarFrequency()) {
            case "oncePerWeek":
                warFrequencyText = "1x/W";
                break;
            case "Always":
                warFrequencyText = "Immer";
                break;
            case "moreThanOncePerWeek":
                warFrequencyText = ">1x/W";
                break;
            case "lessThanOncePerWeek":
                warFrequencyText = "<1x/W";
                break;
            default:
                warFrequencyText = "N/A";
                break;
        }

        holder.binding.tvFoundClanWarFrequency.setText(warFrequencyText);

        String typeText;

        switch (foundClanCard.getType()) {
            case "open":
                typeText = "Offen";
                break;
            case "inviteOnly":
                typeText = "Einladung";
                break;
            case "closed":
                typeText = "Geschlossen";
                break;
            default:
                typeText = "N/A";
                break;
        }

        holder.binding.tvFoundClanType.setText(typeText);

        holder.binding.tvFoundClanMembers.setText("Mitglieder: " + foundClanCard.getMembers()); //passt
        holder.binding.tvFoundClanLvl.setText("Clan Lvl:" + foundClanCard.getClanLevel());
        holder.binding.tvFoundClanMinTHLvl.setText("Min TH Lvl: " + foundClanCard.getRequiredTownhallLevel());
        holder.binding.tvFoundClanMinTrophies.setText("Min Trophäen: " + foundClanCard.getRequiredTrophies());
        holder.binding.tvFoundClanCountryCode.setText(foundClanCard.getLocation().getCountryCode());
        String imageUrl = foundClanCard.getBadgeUrls().getMedium();

        Picasso.get()
                .load(imageUrl)
                .into(holder.binding.ivFoundClanBadge);

        holder.foundClanCard = foundClanCard;
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public FoundClanCard foundClanCard;
        public FragmentFoundclanBinding binding;

        public ViewHolder(FragmentFoundclanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public String toString() {
            return super.toString() + " '";
        }

        @Override
        public void onClick(View v) {
            onFoundClanClickListener.onItemClick(getLayoutPosition());
        }
    }
}