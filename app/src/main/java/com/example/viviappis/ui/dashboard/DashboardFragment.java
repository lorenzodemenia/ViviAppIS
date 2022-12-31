package com.example.viviappis.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.databinding.FragmentDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment
{
    private FragmentDashboardBinding binding;
    private FirebaseAuth au;
    private FirebaseFirestore db;

    private SearchView srcDash;

    /**
     * @author Agostino
     *     Array con la lista degli eventi della dashboard (popolare con DocumentSnapshot) così non tocco cose del DB
     */
    ArrayList<Evento> eventi = new ArrayList<>();
    int frecciaEvento = R.drawable.ic_baseline_arrow_back_ios_24;

    /**
     * @author Agostino
     *  funzione per settare la cardview con gli eventi (uso il costruttore con dei parametri inventati per farlo funzionare intanto)
     */
    private void setUpEventi(){
        String[] titleEventi= getResources().getStringArray(R.array.cardEventsTitle);
        String[] dataEventi = getResources().getStringArray(R.array.cardEventsDate);
        String[] locationEventi = getResources().getStringArray(R.array.cardEventsLocation);

        for(int i=0; i< titleEventi.length;i++){
            eventi.add(new Evento(titleEventi[i],"noDescription","noCreator",dataEventi[i],"noPassword",true,1,10));
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        au = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        db.collection(getResources().getString(R.string.db_rac_events)).get().addOnCompleteListener((t) ->
        {
           createDash(t.getResult().getDocuments());
        });
        setUpEventi();
        setUpUIViews();
        addActionListener();


        return root;
    }

    /**
     * crea la dash bord del fragmant per la visualizzazione degli eventi
     * @param l lista dei documenti presi dal server che rappresentano gli eventi
     */
    public void createDash(List<DocumentSnapshot> l)
    {
        int r;
        for (DocumentSnapshot i : l)//creare i vari contenitori per gli eventi ==> aspetto frontend
        {
            createContenitor(i.getData());
            //aggiungere ascoltatore on clik per mandare nella pagina dell'evento

        }
        //return r;
    }

    public void createContenitor(Map<String, Object> i)
    {
        System.out.println(i);
    }

    public List<DocumentSnapshot> filterListByName(List<DocumentSnapshot> l, String name)
    {
        int i=0;

        while (i<l.size())
        {
            Map<String, Object> m = l.get(i).getData();
            String cnt = (String) m.get("name");

            if(!cnt.startsWith(name)) System.out.println(l.remove(i));
            else i++;
        }

        return l;
    }

    /**
     * Questa funzione serve a inizzializzare le variabili del fragment utili per recuperare i valori di input e mostrare i valori di output
     */
    private void setUpUIViews()
    {
        srcDash = (SearchView) binding.getRoot().getViewById(R.id.dashSrcEvent);
    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        srcDash.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {return false;}

            @Override
            public boolean onQueryTextChange(String s)
            {
                /*db.collection(getResources().getString(R.string.db_rac_events)).add(new Evento(s, "fddsf",au.getCurrentUser().getEmail(), "11/11/2200", "", true)).addOnCompleteListener((t) ->
                {
                    System.out.println("ciao");
                });*/

                db.collection(getResources().getString(R.string.db_rac_events)).get().addOnCompleteListener((t) ->
                {
                    createDash(filterListByName(t.getResult().getDocuments(),s));
                });

                return true;
            }
        });
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }
}