package net.htlgkr.lugern.coctracker.fragments;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

// Laden in ImageView


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.databinding.FragmentClanScreenBinding;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ClanScreen extends Fragment {
    FragmentClanScreenBinding binding;

    public ClanScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentClanScreenBinding.inflate(inflater, container, false);
        ImageView imageView = binding.imageView3;
        CircularProgressIndicator progressIndicator = binding.cp;
        progressIndicator.show();
        String imageUrl = "https://api-assets.clashofclans.com/badges/512/jcC_061k3vRpc6JsBezoLQNucmBuLOL0uyuCUYvWHPQ.png";
        binding.tvClans.setOnClickListener(view -> {
            // Menü-Ressourcen-ID übergeben
            showMenu(view, R.menu.popup_menu);
        });

        // Lade das Bild mit Picasso
        Picasso.get()
                .load(imageUrl)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressIndicator.setVisibility(View.GONE);
                    }
                });
        return binding.getRoot();
    }

    private void showMenu(View v, int menuRes) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.getMenuInflater().inflate(menuRes, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            // Verwende if-else anstelle von switch
            if (menuItem.getItemId() == R.id.clanInfoItem) {
                // Handle option 1 click
                return true;
            } else // Handle option 3 click
                if (menuItem.getItemId() == R.id.clanWarLogItem) {
                    // Handle option 2 click
                    return true;
                } else return menuItem.getItemId() == R.id.currentLeagueItem;
        });

        popup.show();
    }


}