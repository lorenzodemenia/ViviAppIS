package com.example.viviappis.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.ScorrimentoDashboard;
import com.example.viviappis.databinding.FragmentDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment
{
    private FragmentDashboardBinding binding;
    private FirebaseAuth au;
    private FirebaseFirestore db;

    private SearchView srcDash;
    private RecyclerView recyclerView;
    private ProgressBar  asp;
    private Switch swich;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        au = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setUpUIViews();
        addActionListener();

        db.collection(getResources().getString(R.string.db_rac_events)).get().addOnCompleteListener((t) ->
        {
           createDash(t.getResult().getDocuments());
        });

        return root;
    }

    /**
     * crea la dash bord del fragmant per la visualizzazione degli eventi
     * @param l lista dei documenti presi dal server che rappresentano gli eventi
     */
    public void createDash(List<DocumentSnapshot> l)
    {
        ScorrimentoDashboard adapter = new ScorrimentoDashboard(this.getContext(), this, this::hide,true);

        asp.setVisibility(View.VISIBLE);

        for (DocumentSnapshot i : l)//creare i vari contenitori per gli eventi ==> aspetto frontend
        {
            adapter.addEvent(new Evento(i.getData()),   i.getId());
            //aggiungere ascoltatore on clik per mandare nella pagina dell'evento
        }
        asp.setVisibility(View.INVISIBLE);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Fragment a = getChildFragmentManager().findFragmentById(R.id.dasboardFragment);
        if (a != null)
        {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.remove(a);
            transaction.commit();
        }
    }

    public void hide(Boolean a)
    {
        if(a)binding.dasboard.setVisibility(View.INVISIBLE);
        else binding.dasboard.setVisibility(View.VISIBLE);
    }


    public List<DocumentSnapshot> filterListByLuogo(List<DocumentSnapshot> l, String luogo)
    {
        int i=0;

        while (i<l.size())
        {
            Map<String, Object> m = l.get(i).getData();
            String cnt = m.get("luogo")!=null ? (String) m.get("luogo") : "";

            if(!cnt.contains(luogo)) l.remove(i);
            else                      i++;
        }

        return l;
    }
    public List<DocumentSnapshot> filterListByName(List<DocumentSnapshot> l, String name)
    {
        int i=0;

        while (i<l.size())
        {
            Map<String, Object> m = l.get(i).getData();
            String cnt = (String) m.get("name");

            if(!cnt.startsWith(name)) l.remove(i);
            else                      i++;
        }

        return l;
    }

    /**
     * Questa funzione serve a inizzializzare le variabili del fragment utili per recuperare i valori di input e mostrare i valori di output
     */
    private void setUpUIViews()
    {
        srcDash = binding.dashSrcEvent;
        recyclerView = binding.dashViewEvents;
        asp = binding.dashProgressBar;
        swich = binding.typeSrcEvent;
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
                if(swich.isChecked())
                {
                    db.collection(getResources().getString(R.string.db_rac_events)).get().addOnCompleteListener((t) ->
                    {
                        createDash(filterListByLuogo(t.getResult().getDocuments(),s));
                    });
                }
                else
                {
                    db.collection(getResources().getString(R.string.db_rac_events)).get().addOnCompleteListener((t) ->
                    {
                        createDash(filterListByName(t.getResult().getDocuments(),s));
                    });
                }

                return true;
            }
        });

        swich.setOnCheckedChangeListener((com, bool) ->
        {
            if (bool)
            {
                swich.setText(getResources().getText(R.string.page_dash_lg));
                srcDash.setQueryHint(getResources().getText(R.string.page_dash_src_lg));
            }
            else
            {
                swich.setText(getResources().getText(R.string.page_dash_nm));
                srcDash.setQueryHint(getResources().getText(R.string.page_dash_src_nm));
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