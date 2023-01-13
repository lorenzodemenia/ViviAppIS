package com.example.viviappis.ui.home;

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
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Utilities;
import com.example.viviappis.data.model.recicleView.ScorrimentoDashboard;
import com.example.viviappis.databinding.FragmentHomeBinding;
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


    /**
     * crea il fragment
     * @param savedInstanceState istanze precedenti
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}
    /**
     * serve a creare la view del fragment
     * @param inflater inflanter per creare istanza della pagina xml
     * @param container container del fragment
     * @param savedInstanceState istanze precedenti
     * @return la view della pagina
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        au = FirebaseAuth.getInstance();

        setUpUIViews();
        addActionListener();

        Utilities.dbGetCollEvents(db,getResources()).whereArrayContains("partecipants",au.getCurrentUser().getEmail()).get()
                .addOnCompleteListener((task)-> {createDash(task.getResult().getDocuments(), iscr);});

        Utilities.dbGetCollEvents(db,getResources()).whereEqualTo("creator",au.getCurrentUser().getEmail()).get()
                .addOnCompleteListener((task)-> {createDash(task.getResult().getDocuments(), prop);});

        return root;
    }

    /**
     * crea la dashbord per la visualizzazzione degli eventi di cui utente e iscritto o proprietario
     * @param l lista di elementi da aggiungere nella RecyclerView
     * @param r recyclerView a cui caricare i dati
     */
    public void createDash(List<DocumentSnapshot> l, RecyclerView r)
    {
        ScorrimentoDashboard adapter = new ScorrimentoDashboard(this.getContext(), this, this::hide,false);

        asp.setVisibility(View.VISIBLE);

        for (DocumentSnapshot i : l) adapter.addEvent(new Evento(i.getData()),i.getId());

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
     * serve a nascondere e a rendere visibile la pagina
     * @param a true pagina visibile, false pagina invisibile
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
    /**
     * funzione che distrugge la view della pagina
     */
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}