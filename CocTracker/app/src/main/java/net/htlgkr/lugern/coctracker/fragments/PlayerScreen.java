package net.htlgkr.lugern.coctracker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.api.HTTPListener;
import net.htlgkr.lugern.coctracker.databinding.FragmentPlayerScreenBinding;
import net.htlgkr.lugern.coctracker.models.player.Player;
import net.htlgkr.lugern.coctracker.viewmodels.RequestViewModel;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PlayerScreen extends Fragment {
    FragmentPlayerScreenBinding binding;

    public PlayerScreen() {
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
        binding = FragmentPlayerScreenBinding.inflate(inflater, container, false);
//        setImageOnImageView(binding.ivTownhall, 9);

        RequestViewModel requestViewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);
        requestViewModel.init(requireContext());
//        requestViewModel.requestStatsAndCards(new HTTPListener<>() {
//            @Override
//            public void onSuccess(String a) {
//                requestViewModel.loadPlayerInfo(a);
//                Player player = requestViewModel.getPlayer();
//                binding.tiPlayerTag.setText(player.getTag());
//                binding.tvPlayerClan.setText(player.getClan().getName());
//                binding.tvPlayerTrophies.setText(String.valueOf(player.getTrophies()));
//                binding.tvPlayerName.setText(player.getName());
//                setImageOnImageView(binding.ivTownhall, 17);
//            }
//
//            @Override
//            public void onError(String error) {
//                System.out.println(error);
//            }
//        });

        return binding.getRoot();
    }

    public void setImageOnImageView(ImageView imageView, int number) {
        if (number < 1 || number > 17) {
            throw new IllegalArgumentException("Parameter 'number' muss zwischen 1 und 17 liegen.");
        }

        int[] images = {
                R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5,
                R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10,
                R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15,
                R.drawable.a16, R.drawable.a17
        };

        imageView.setImageResource(images[number - 1]);
    }

}