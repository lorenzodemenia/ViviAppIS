package com.example.viviappis.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.R;
import com.example.viviappis.control.loginAndRegister.LoginActivity;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.ScorrimentoDashboard;
import com.example.viviappis.databinding.FragmentEventPageBinding;
import com.example.viviappis.databinding.FragmentHomeBinding;
import com.example.viviappis.ui.event.EventPageFragment;
import com.example.viviappis.ui.event.NewEventFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HomeFragment extends Fragment
{

    private FragmentHomeBinding  binding;
    private Button bHomeNewEvent;
    private ProgressBar asp;
    private RecyclerView iscr, prop;

    private FirebaseFirestore db;
    private FirebaseAuth au;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        au = FirebaseAuth.getInstance();

        setUpUIViews();
        addActionListener();

        db.collection(getResources().getString(R.string.db_rac_events)).
                whereArrayContains("partecipants",au.getCurrentUser().getEmail()).get().addOnCompleteListener((task)->
        {
            createDash(task.getResult().getDocuments(), iscr);
        });

        db.collection(getResources().getString(R.string.db_rac_events)).
                whereEqualTo("creator",au.getCurrentUser().getEmail()).get().addOnCompleteListener((task)->
                {
                    createDash(task.getResult().getDocuments(), prop);
                });

        return root;
    }

    public void createDash(List<DocumentSnapshot> l, RecyclerView r)
    {
        ScorrimentoDashboard adapter = new ScorrimentoDashboard(this.getContext(), this, this::hide,false);

        asp.setVisibility(View.VISIBLE);

        System.out.println(l);

        for (DocumentSnapshot i : l)//creare i vari contenitori per gli eventi ==> aspetto frontend
        {
            adapter.addEvent(new Evento(i.getData()),i.getId());
            //aggiungere ascoltatore on clik per mandare nella pagina dell'evento
        }
        asp.setVisibility(View.INVISIBLE);

        r.setAdapter(adapter);
        r.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }



    /**
     * Funzione usata per riportare in vita il fragment
     */
    @Override
    public void onResume()
    {
        super.onResume();
        Fragment a = getChildFragmentManager().findFragmentById(R.id.homeFragment);
        if (a != null) {

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.remove(a);
            transaction.commit();
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
        asp = binding.homeLoad;
        iscr = binding.homeViewEventsIscr;
        prop = binding.homeViewEventsProp;
    }

    /**
     * nasconde tutti i componenti appartenenti alla pagina home
     */
    public void hide(Boolean a)
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
            NewEventFragment a = new NewEventFragment();

            transaction.replace(R.id.homeFragment, a, null);
            transaction.setMaxLifecycle(a , Lifecycle.State.STARTED );

            transaction.commit();
            hide(true);
        });
    }
}