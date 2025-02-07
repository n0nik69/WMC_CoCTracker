package net.htlgkr.lugern.coctracker.list.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.databinding.FragmentSpellBinding;
import net.htlgkr.lugern.coctracker.models.player.PlayerItemLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySpellRecyclerViewAdapter extends RecyclerView.Adapter<MySpellRecyclerViewAdapter.ViewHolder> {

    private final List<PlayerItemLevel> values;
    private final Map<String, Integer> spellImageMap;

    public MySpellRecyclerViewAdapter(List<PlayerItemLevel> items) {
        values = items;
        spellImageMap = new HashMap<>();
        spellImageMap.put("Lightning Spell", R.drawable.lightningspell);
        spellImageMap.put("Healing Spell", R.drawable.healingspell);
        spellImageMap.put("Rage Spell", R.drawable.ragespell);
        spellImageMap.put("Jump Spell", R.drawable.jumpspell);
        spellImageMap.put("Freeze Spell", R.drawable.freezespell);
        spellImageMap.put("Clone Spell", R.drawable.clonespell);
        spellImageMap.put("Invisibility Spell", R.drawable.invisibilityspell);
        spellImageMap.put("Poison Spell", R.drawable.poisonspell);
        spellImageMap.put("Earthquake Spell", R.drawable.earthquakespell);
        spellImageMap.put("Haste Spell", R.drawable.hastespell);
        spellImageMap.put("Skeleton Spell", R.drawable.skeletonspell);
        spellImageMap.put("Bat Spell", R.drawable.batspell);
        spellImageMap.put("Recall Spell", R.drawable.recallspell);
        spellImageMap.put("Overgrowth Spell", R.drawable.overgrowthspell);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentSpellBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PlayerItemLevel spell = values.get(position);
        holder.binding.tvSpellName.setText(spell.getName());
        holder.binding.tvSpellLevel.setText("Level " + spell.getLevel() + "/" + spell.getMaxLevel());

        Integer imageRes = spellImageMap.get(spell.getName());
        Log.d("ImageLoad", "Zaubername: " + spell.getName() + ", Bild-Resource: " + imageRes);

        if (imageRes != null) {
            holder.binding.ivSpell.setImageResource(imageRes);
        } else {
            holder.binding.ivSpell.setImageResource(R.drawable.resource_default); // Fallback-Bild
        }
    }


    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FragmentSpellBinding binding;

        public ViewHolder(FragmentSpellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
