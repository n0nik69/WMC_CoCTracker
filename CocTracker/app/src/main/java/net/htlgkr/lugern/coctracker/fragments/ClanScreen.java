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

        // Lade das Bild mit Picasso
        Picasso.get()
                .load(imageUrl)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Verstecke den Ladeindikator nach erfolgreichem Laden
                        progressIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Verstecke den Ladeindikator auch bei Fehlern
//                        progressIndicator.setVisibility(View.GONE);
                    }
                });
        return binding.getRoot();
    }
}