package com.example.viviappis.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.viviappis.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /**
         * Commentato da Ago (fa crashare l'app)
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        */

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textProfile;
        /**
         * Commentato da Ago
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        */
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}