package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.callbacks.MyOnCardClickListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentTroopBinding;
import net.htlgkr.lugern.coctracker.models.player.PlayerItemLevel;

import java.util.List;

public class MyTroopsRecyclerViewAdapter extends RecyclerView.Adapter<MyTroopsRecyclerViewAdapter.ViewHolder> {

    private final List<PlayerItemLevel> values;
    private MyOnCardClickListener onCardClickListener;

    public MyTroopsRecyclerViewAdapter(List<PlayerItemLevel> items) {
        values = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentTroopBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public void setOnItemClickListener(MyOnCardClickListener onItemClickListener) {
        this.onCardClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PlayerItemLevel playerItemLevel = values.get(position);

        holder.binding.tvTroopName.setText(playerItemLevel.getName());
        holder.binding.tvTroopLevel.setText("Lvl: " + playerItemLevel.getLevel() + "/" + playerItemLevel.getMaxLevel());

        // Bild laden
        int imageResId = getTroopImageResource(holder.itemView, playerItemLevel.getName());
        if (imageResId != 0) {
            holder.binding.ivTroop.setImageResource(imageResId);
        } else {
            holder.binding.ivTroop.setImageResource(R.drawable.resource_default);
        }

        holder.playerItemLevel = playerItemLevel;
    }


    @Override
    public int getItemCount() {
        return values.size();
    }

    private String getImageName(String troopName) {
        // Ausnahmen
        switch (troopName) {
            case "P.E.K.K.A":
                return "pekka";
            case "Power P.E.K.K.A":
                return "powerpekka";
            case "L.A.S.S.I":
                return "lassi";
        }

        // Standard: Leerzeichen entfernen, in Kleinbuchstaben umwandeln
        return troopName.toLowerCase().replace(" ", "");
    }


    private int getTroopImageResource(View view, String troopName) {
        String imageName = getImageName(troopName);
        return view.getContext().getResources().getIdentifier(imageName, "drawable", view.getContext().getPackageName());
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public FragmentTroopBinding binding;
        public PlayerItemLevel playerItemLevel;

        public ViewHolder(FragmentTroopBinding binding) {
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