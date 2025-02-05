package net.htlgkr.lugern.coctracker.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.databinding.FragmentHeroBinding;
import net.htlgkr.lugern.coctracker.models.player.PlayerItemLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHeroRecyclerViewAdapter extends RecyclerView.Adapter<MyHeroRecyclerViewAdapter.ViewHolder> {

    private final List<PlayerItemLevel> values;
    private final Map<String, Integer> heroImageMap;
    private final Map<String, Integer> equipmentImageMap;

    public MyHeroRecyclerViewAdapter(List<PlayerItemLevel> items) {
        values = items;

        // Map für Helden-Bilder
        heroImageMap = new HashMap<>();
        heroImageMap.put("Barbarian King", R.drawable.barbarianking);
        heroImageMap.put("Archer Queen", R.drawable.archerqueen);
        heroImageMap.put("Grand Warden", R.drawable.grandwarden);
        heroImageMap.put("Battle Machine", R.drawable.battlemachine);
        heroImageMap.put("Royal Champion", R.drawable.royalchampion);
        heroImageMap.put("Battle Copter", R.drawable.battlecopter);
        heroImageMap.put("Minion Prince", R.drawable.minionprince);

        // Map für Heldenausrüstungen
        equipmentImageMap = new HashMap<>();
        equipmentImageMap.put("Spiky Ball", R.drawable.spikyball);
        equipmentImageMap.put("Earthquake Boots", R.drawable.earthquakeboots);
        equipmentImageMap.put("Healer Puppet", R.drawable.healerpuppet);
        equipmentImageMap.put("Giant Arrow", R.drawable.giantarrow);
        equipmentImageMap.put("Eternal Tome", R.drawable.eternaltome);
        equipmentImageMap.put("Healing Tome", R.drawable.healingtome);
        equipmentImageMap.put("Hog Rider Puppet", R.drawable.hogriderpuppet);
        equipmentImageMap.put("Electro Boots", R.drawable.electroboots);
        equipmentImageMap.put("Dark Orb", R.drawable.darkorb);
        equipmentImageMap.put("Henchmen Puppet", R.drawable.henchmenpuppet);
        equipmentImageMap.put("Giant Gauntlet", R.drawable.giantgauntlet);
        equipmentImageMap.put("Rocket Spear", R.drawable.rocketspear);
        equipmentImageMap.put("Frozen Arrow", R.drawable.frozenarrow);
        equipmentImageMap.put("Fireball", R.drawable.fireball);
        equipmentImageMap.put("Magic Mirror", R.drawable.magicmirror);
        equipmentImageMap.put("Lavaloon Puppet", R.drawable.lavaloonpuppet);
        equipmentImageMap.put("Barbarian Puppet", R.drawable.barbarianpuppet);
        equipmentImageMap.put("Rage Vial", R.drawable.ragevial);
        equipmentImageMap.put("Archer Puppet", R.drawable.archerpuppet);
        equipmentImageMap.put("Invisibility Vial", R.drawable.invisibilityvial);
        equipmentImageMap.put("Life Gem", R.drawable.lifegem);
        equipmentImageMap.put("Seeking Shield", R.drawable.seekingshield);
        equipmentImageMap.put("Royal Gem", R.drawable.royalgem);
        equipmentImageMap.put("Vampstache", R.drawable.vampstache);
        equipmentImageMap.put("Haste Vial", R.drawable.hastevial);
        equipmentImageMap.put("Rage Gem", R.drawable.ragegem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentHeroBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        PlayerItemLevel hero = values.get(position);

        // Hero-Image setzen
        Integer heroImageRes = heroImageMap.get(hero.getName());
        if (heroImageRes != null) {
            holder.binding.ivHero.setImageResource(heroImageRes);
        } else {
            holder.binding.ivHero.setImageResource(R.drawable.resource_default);
        }

        holder.binding.ivHeroName.setText(hero.getName());
        holder.binding.ivHeroLevel.setText("Level " + hero.getLevel() + " / " + hero.getMaxLevel());

        // Equipment setzen
        List<PlayerItemLevel> equipment = hero.getEquipment();
        if (equipment != null && !equipment.isEmpty()) {
            // Erstes Equipment
            PlayerItemLevel equip1 = equipment.get(0);
            Integer equip1ImageRes = equipmentImageMap.get(equip1.getName());
            if (equip1ImageRes != null) {
                holder.binding.ivHeroEquipment1.setImageResource(equip1ImageRes);
            } else {
                holder.binding.ivHeroEquipment1.setImageResource(R.drawable.resource_default);
            }
            holder.binding.tvHeroEquipment1.setText(equip1.getName());

            // Zweites Equipment (falls vorhanden)
            if (equipment.size() > 1) {
                PlayerItemLevel equip2 = equipment.get(1);
                Integer equip2ImageRes = equipmentImageMap.get(equip2.getName());
                if (equip2ImageRes != null) {
                    holder.binding.ivHeroEquipment2.setImageResource(equip2ImageRes);
                } else {
                    holder.binding.ivHeroEquipment2.setImageResource(R.drawable.resource_default);
                }
                holder.binding.tvHeroEquipment2.setText(equip2.getName());
                holder.binding.ivHeroEquipment2.setVisibility(View.VISIBLE);
                holder.binding.tvHeroEquipment2.setVisibility(View.VISIBLE);
            } else {
                holder.binding.ivHeroEquipment2.setVisibility(View.GONE);
                holder.binding.tvHeroEquipment2.setVisibility(View.GONE);
            }
        } else {
            // Falls kein Equipment vorhanden ist, ausblenden
            holder.binding.ivHeroEquipment1.setVisibility(View.GONE);
            holder.binding.tvHeroEquipment1.setVisibility(View.GONE);
            holder.binding.ivHeroEquipment2.setVisibility(View.GONE);
            holder.binding.tvHeroEquipment2.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FragmentHeroBinding binding;

        public ViewHolder(FragmentHeroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
