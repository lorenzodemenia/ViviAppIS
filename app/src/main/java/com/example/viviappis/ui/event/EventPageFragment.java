package com.example.viviappis.ui.event;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.viviappis.R;
import com.example.viviappis.control.loginAndRegister.LoginActivity;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.ScorrimentoPartecipant;
import com.example.viviappis.databinding.FragmentEventPageBinding;
import com.example.viviappis.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class EventPageFragment extends Fragment
{
    private FragmentEventPageBinding binding;

    private TextView inpNm, inpDs, inpDate, inpProp;
    private RecyclerView inpPart;
    private Button bIscr, bCanc, bStart;

    private String id;
    private Evento e;

    private FirebaseAuth au;
    private FirebaseFirestore db;
    private ConstraintLayout pAdmin, pUsers;
    private static AlertDialog b = null;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentEventPageBinding.inflate(inflater, container, false);

        id = getArguments().getString(getResources().getString(R.string.event_send_ev));

        db = FirebaseFirestore.getInstance();
        au = FirebaseAuth.getInstance();

        setUpUIViews();

        if(e==null)
        {
            dbGetCollEvents().document(id).get().addOnCompleteListener((task ->
            {
                if(task.getResult().getData()!=null)
                {
                    e = new Evento(task.getResult().getData());
                    setViewEvent();
                    addActionListener();
                }
                else
                {
                    if(b==null)
                    {
                        b = new AlertDialog.Builder(getContext())
                                .setTitle(getResources().getString(R.string.allert_title_show_event_err))
                                .setMessage(getResources().getString(R.string.allert_text_show_event_err)).show();
                    }
                    else b = null;
                }

            }));
        }

        return binding.getRoot();
    }

    public void hide(Boolean a)
    {
        if(a)binding.pageEvent.setVisibility(View.INVISIBLE);
        else binding.pageEvent.setVisibility(View.VISIBLE);
    }

    private void setUpUIViews()
    {
        inpNm = binding.pageEventNm;
        inpDs = binding.pageEventDesc;
        inpDate = binding.pageEventData;
        inpProp = binding.pageEventProp;
        inpPart = binding.pageEventPart;
        bIscr = binding.pageEventIscr;
        bCanc = binding.pageEventCanc;
        bStart = binding.pageEventStart;
        pAdmin = binding.pageEventAdmin;
        pUsers = binding.pageEventUser;
    }

    private com.google.firebase.firestore.CollectionReference dbGetCollEvents() {return db.collection(getResources().getString(R.string.db_rac_events));}
    private com.google.firebase.firestore.CollectionReference dbGetCollUsers() {return db.collection(getResources().getString(R.string.db_rac_users));}

    private void setViewEvent()
    {
        inpNm.setText(e.getName());
        inpDs.setText(e.getDescription());
        inpDate.setText(e.getDate());
        dbGetCollUsers().document(e.getCreator()).get().addOnCompleteListener((task)->
        {
          inpProp.setText(task.getResult().get("username").toString());
        });
        createDash(e.getPartecipants());
        switcPanelProp(e.getCreator().equals(au.getCurrentUser().getEmail()));

    }

    private void switcPanelProp(boolean b)
    {
        pAdmin.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
        pUsers.setVisibility(!b ? View.VISIBLE : View.INVISIBLE);
    }

    private View.OnClickListener getOnClickListenerIscr(Boolean b)
    {
        View.OnClickListener r;

        if(!b)
        {
            //disiscrivi
            bIscr.setText(getResources().getString(R.string.page_event_dis));
            r = (v)->
            {
                e.removePartecipant(au.getCurrentUser().getEmail());
                dbGetCollEvents().document(id).update("partecipants", e.getPartecipants());
                bIscr.setOnClickListener(getOnClickListenerIscr(true));
                createDash(e.getPartecipants());
            };
        }
        else
        {
            //iscrivi
            bIscr.setText(getResources().getString(R.string.page_event_isc));
            r = (v)->
            {
                e.addPartecipants(au.getCurrentUser().getEmail());
                dbGetCollEvents().document(id).update("partecipants", e.getPartecipants());
                bIscr.setOnClickListener(getOnClickListenerIscr(false));
                createDash(e.getPartecipants());
            };
        }


        return r;
    }

    /**
     * crea la dash bord del fragmant per la visualizzazione degli eventi
     * @param l lista dei documenti presi dal server che rappresentano gli eventi
     */
    public void createDash(List<String> l)
    {
        ScorrimentoPartecipant adapter = new ScorrimentoPartecipant(getContext());
        Integer app = 1;

        for (String i : l)//creare i vari contenitori per gli eventi ==> aspetto frontend
        {
           dbGetCollUsers().document(i).get().addOnCompleteListener((task)->
            {
                adapter.addPart(task.getResult().get("username").toString());
                if(i.equals(l.get(l.size()-1)))
                {
                    inpPart.setAdapter(adapter);
                    inpPart.setLayoutManager(new LinearLayoutManager(this.getContext()));
                }
            });
        }

        if(l.size()==0)
        {
            inpPart.setAdapter(adapter);
            inpPart.setLayoutManager(new LinearLayoutManager(this.getContext()));
        }
    }



    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        bIscr.setOnClickListener(getOnClickListenerIscr(!e.getPartecipants().contains(au.getCurrentUser().getEmail())));
        if(e.getCreator().equals(au.getCurrentUser().getEmail()))
        {
            bCanc.setOnClickListener((v)->
            {
                dbGetCollEvents().document(id).delete().addOnCompleteListener((task ->
                {
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.allert_title_del_event))
                            .setMessage(getResources().getString(R.string.allert_text_del_event)).show();

                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setReorderingAllowed(true);

                    transaction.replace(R.id.pageEventFragment, HomeFragment.class, null);

                    transaction.commit();
                    hide(true);
                }));
            });
            bStart.setOnClickListener((v)->
            {
                startActivity(new Intent(getActivity(), EventInExecutionActivity.class));
            });
        }
    }
}