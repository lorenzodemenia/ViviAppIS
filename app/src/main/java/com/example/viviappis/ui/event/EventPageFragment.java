package com.example.viviappis.ui.event;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Utilities;
import com.example.viviappis.data.model.recicleView.ScorrimentoPartecipant;
import com.example.viviappis.databinding.FragmentEventPageBinding;
import com.example.viviappis.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


/**
 * Questa classe serve a gestire la pagina di visualizzazione dell'evento
 * @author jacopo
 * @version 1.0
 */
public class EventPageFragment extends Fragment
{
    private FragmentEventPageBinding binding;

    private TextView outNm, outDs, outDate, outProp, outH, outLuogo;
    private RecyclerView outPart;

    private Button bIscr, bCanc, bStart;

    private String id;

    private Evento e;

    private FirebaseAuth au;
    private FirebaseFirestore db;
    private ConstraintLayout pAdmin, pUsers;
    private static AlertDialog b = null;
    private String psw="";


    /**
     * crea il fragment
     * @param savedInstanceState istanze precedenti
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * serve a creare la view del fragment
     * @param inflater inflanter per creare istanza della pagina xml
     * @param container container del fragment
     * @param savedInstanceState istanze precedenti
     * @return la view della pagina 
     */
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
            Utilities.dbGetCollEvents(db,getResources()).document(id).get().addOnCompleteListener((task ->
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

    /**
     * serve a nascondere e a rendere visibile la pagina
     * @param a true pagina visibile, false pagina invisibile
     */
    public void hide(Boolean a)
    {
        if(a)binding.pageEvent.setVisibility(View.INVISIBLE);
        else binding.pageEvent.setVisibility(View.VISIBLE);
    }

    /**
     * Questa funzione serve a inizzializzare le variabili del fragment eventpage per recuperare i valori di input e mostrare i valori di output
     */
    private void setUpUIViews()
    {
        outNm = binding.pageEventNm;
        outDs = binding.pageEventDesc;
        outDate = binding.pageEventData;
        outProp = binding.pageEventProp;
        outPart = binding.pageEventPart;
        outH = binding.pageEventHour;
        outLuogo = binding.pageEventLuogo;
        bIscr = binding.pageEventIscr;
        bCanc = binding.pageEventCanc;
        bStart = binding.pageEventStart;
        pAdmin = binding.pageEventAdmin;
        pUsers = binding.pageEventUser;
    }


    /**
     * Carica i valori dell'evento nei vari campi di output
     */
    private void setViewEvent()
    {
        outNm.setText(e.getName());
        outDs.setText(e.getDescription());
        outDs.setMovementMethod(new ScrollingMovementMethod());
        outDate.setText(e.getDate());
        outH.setText(e.getOra());
        outLuogo.setText(e.getLuogo());
        Utilities.dbGetCollUsers(db,getResources()).document(e.getCreator()).get().addOnCompleteListener((task)->
        {
          outProp.setText(task.getResult().get("username").toString());
        });
        createDash(e.getPartecipants());
        switcPanelProp(e.getCreator().equals(au.getCurrentUser().getEmail()));

    }

    /**
     * cambia il pannello di controllo dell'evento
     * @param b true pannello admin evento(start e canc evento), false pannello utente(iscriviti, disiscriviti)
     */
    private void switcPanelProp(boolean b)
    {
        pAdmin.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
        pUsers.setVisibility(!b ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * genera il onClicklistener da mettere nel bottone d'iscrizione o disiscrizione
     * @param b true se e da aggungere l'ascoltatore per gestire iscrizione, false se è da gestire ascoltatore per la disiscrizione
     * @return
     */
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
                Utilities.dbGetCollEvents(db,getResources()).document(id).update("partecipants", e.getPartecipants());
                bIscr.setOnClickListener(getOnClickListenerIscr(true));
                createDash(e.getPartecipants());
            };
        }
        else
        {
            //iscriviti
            bIscr.setText(getResources().getString(R.string.page_event_isc));
            r = (v)->
            {
                if(e.isPublic()) iscr();
                else             cntrPsw();
            };
        }


        return r;
    }

    /**
     * funzione per iscrivere utente che preme il tasto di iscriviti
     */
    private void iscr()
    {
        Utilities.dbGetCollEvents(db,getResources()).document(id).get().addOnCompleteListener((task ->
        {
            e = new Evento(task.getResult().getData());
            if (e.getPartecipants().size()<e.getMaxPart())
            {
                e.addPartecipants(au.getCurrentUser().getEmail());
                Utilities.dbGetCollEvents(db, getResources()).document(id).update("partecipants", e.getPartecipants());
                bIscr.setOnClickListener(getOnClickListenerIscr(false));
                createDash(e.getPartecipants());
            }
            else
            {
                new AlertDialog.Builder(getContext())
                        .setTitle("Evento pieno")
                        .setMessage("Evento ha raggiunto il numero massimo di partecipanti").show();
            }
        }));
    }

    /**
     * funzione per andare a controllare se la password inserita nell inp dialog è uguale a quella dell'evento privato a cui ci si vuole iscrivere
     */
    private void cntrPsw()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(getResources().getString(R.string.allert_title_show_inp_psw));

        EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which)->
        {
            psw = input.getText().toString();
            boolean r =  psw.equals(e.getPassword());

            if (!r)
            {
                new AlertDialog.Builder(getContext())
                    .setTitle(getResources().getString(R.string.allert_title_wrong_psw))
                    .setMessage(getResources().getString(R.string.allert_text_wrong_psw)).show();
            }
            else iscr();
        });
        builder.setNegativeButton("Cancel", (dialog, which)-> {dialog.cancel();});

        builder.show();
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
            Utilities.dbGetCollUsers(db,getResources()).document(i).get().addOnCompleteListener((task)->
            {
                adapter.addPart(task.getResult().getString("username"));
                if(l.size()>0 &&i.equals(l.get(l.size()-1)))
                {
                    outPart.setAdapter(adapter);
                    outPart.setLayoutManager(new LinearLayoutManager(this.getContext()));
                }
            });
        }

        if(l.size()==0)
        {
            outPart.setAdapter(adapter);
            outPart.setLayoutManager(new LinearLayoutManager(this.getContext()));
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
                Utilities.dbGetCollEvents(db,getResources()).document(id).delete().addOnCompleteListener((task ->
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
                Utilities.dbGetCollEvents(db,getResources()).document(id).get().addOnCompleteListener((task ->
                {
                    e = new Evento(task.getResult().getData());
                    if(e.canStart() && e.getMinPart()<=e.getPartecipants().size())
                        startActivity(new Intent(getActivity(), EventInExecutionActivity.class).putExtra(getResources().getString(R.string.event_send_ev), id));
                }));
            });
        }
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