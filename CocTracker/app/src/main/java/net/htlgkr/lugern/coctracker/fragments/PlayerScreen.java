package net.htlgkr.lugern.coctracker.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.htlgkr.lugern.coctracker.R;
import net.htlgkr.lugern.coctracker.databinding.FragmentPlayerScreenBinding;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayerScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}