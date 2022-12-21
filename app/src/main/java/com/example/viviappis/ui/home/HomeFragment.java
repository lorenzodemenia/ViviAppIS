package com.example.viviappis.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.viviappis.R;
import com.example.viviappis.databinding.FragmentHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment
{

    private FragmentHomeBinding  binding;
    public static BottomNavigationView navbar;
    private Button bHomeNewEvent;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setUpUIViews();
        addActionListener();


        return root;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    private void setUpUIViews()
    {
        bHomeNewEvent = binding.homeNewEvent;
    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        bHomeNewEvent.setOnClickListener((i)-> {navbar.setSelectedItemId(R.id.navigation_new_event);});
    }
}