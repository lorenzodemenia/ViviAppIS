package com.example.viviappis.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.example.viviappis.R;
import com.example.viviappis.databinding.FragmentHomeBinding;
import com.example.viviappis.ui.event.NewEventFragment;
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

    /**
     * Funzione usata per riportare in vita il fragment
     */
    @Override
    public void onResume()
    {
        super.onResume();
        Fragment a = getChildFragmentManager().findFragmentById(R.id.homeFragment);
        if(a!=null)
        {
            hide(true);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.remove(a);
            transaction.commit();
            hide(false);
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Carica i valori utili per accedere all'interfaccia della pagina
     */
    private void setUpUIViews()
    {
        bHomeNewEvent = binding.homeNewEvent;
    }

    /**
     * nasconde tutti i componenti appartenenti alla pagina home
     */
    private void hide(Boolean a)
    {
        if(a)binding.home.setVisibility(View.INVISIBLE);
        else binding.home.setVisibility(View.VISIBLE);
    }

    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        bHomeNewEvent.setOnClickListener((i)->
        {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);

            transaction.replace(R.id.homeFragment, NewEventFragment.class, null);

            transaction.commit();
            hide(true);
        });
    }
}