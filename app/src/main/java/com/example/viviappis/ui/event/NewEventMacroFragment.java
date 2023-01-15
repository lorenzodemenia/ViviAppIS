package com.example.viviappis.ui.event;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.viviappis.Pair;
import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Utilities;
import com.example.viviappis.databinding.FragmentNewEventMacroBinding;
import com.example.viviappis.ui.home.HomeFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe serve a gestire la pagina di conferma per la creazione di un nuovo evento
 * @author jacopo
 * @version 1.0
 */
public class NewEventMacroFragment extends Fragment
{
    private Evento e;
    private String t;
    private TextView nm, ds, data, min, max,type, pub, psw, ora, luogo;
    private Button conf, mod;

    private Map<String, Pair<Integer,Integer>> minMax = new HashMap<>();
    private FirebaseFirestore db;

    private FragmentNewEventMacroBinding binding;

    /**
     * crea il fragment
     * @param savedInstanceState istanze precedenti
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        e = new Evento(getArguments().getString(getResources().getString(R.string.event_send_ev)));
        t = getArguments().getString(getResources().getString(R.string.event_macro_send_type));
        db = FirebaseFirestore.getInstance();

        createminMax();
    }

    /**
     * carica dal file string le strighe relative al massimo e minimo di utenti per gioco
     */
    private void createminMax()
    {
        String a[] = getResources().getStringArray(R.array.new_ev_spinner_item);
        int m[] = getResources().getIntArray(R.array.min_for_event);
        int M[] = getResources().getIntArray(R.array.max_for_event);

        for (int i =0;i<a.length;i++)minMax.put(a[i],new Pair<>(m[i],M[i]));
    }


    /**
     * mostra evento nei campi di output
     */
    private void showEvent()
    {
        nm.setText(e.getName());
        ds.setText(e.getDescription());
        ds.setMovementMethod(new ScrollingMovementMethod());
        data.setText(e.getDate());
        type.setText(t);
        pub.setText(e.isPublic() ? "Pubblico" : "Privato");
        type.setText(t);
        e.setMinPart(minMax.get(t).getX());
        e.setMaxPart(minMax.get(t).getY());
        max.setText(e.getMaxPart()+"");
        min.setText(e.getMinPart()+"");
        psw.setText(e.getPassword());
        ora.setText(e.getOra());
        luogo.setText(e.getLuogo());
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
        binding = FragmentNewEventMacroBinding.inflate(getLayoutInflater());

        setUpUIViews();
        addActionListener();
        showEvent();


        return binding.getRoot();
    }

    /**
     * Questa funzione serve a inizzializzare le variabili dell'activity utili per recuperare i valori di input e mostrare i valori di output
     */
    private void setUpUIViews() {
        nm = binding.newEventMacroNm;
        data = binding.newEventMacroDate;
        ds = binding.newEventMacroDesc;
        min = binding.newEventMacroMin;
        max = binding.newEventMacroMax;
        pub = binding.newEventMacroPub;
        type = binding.newEventMacroType;
        psw = binding.newEventMacroPsw;
        ora = binding.newEventMacroHour;
        luogo = binding.newEventMacroLuogo;

        conf = binding.newEventMacroConf;
        mod = binding.newEventMacroMod;
    }


    /**
     * serve a nascondere e a rendere visibile la pagina
     * @param a true pagina visibile, false pagina invisibile
     */
    private void hide(Boolean a)
    {
        if(a)binding.newEventMacro.setVisibility(View.INVISIBLE);
        else binding.newEventMacro.setVisibility(View.VISIBLE);
    }

    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        conf.setOnClickListener((v)->
        {
            Map<String, Object> a = e.toMap();
            a.put("class", t);

            Utilities.dbGetCollEvents(db,getResources()).add(a).addOnCompleteListener((task)->
            {
                if(task.isSuccessful())
                {
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.allert_title_new_event))
                            .setMessage(getResources().getString(R.string.allert_text_new_event)).show();

                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.setReorderingAllowed(true);

                    transaction.replace(R.id.newEventMacroFragment, HomeFragment.class, null);

                    transaction.commit();
                    hide(true);
                }

            });
        });

        mod.setOnClickListener((v)->
        {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);

            Bundle mex = new Bundle();
            mex.putString(getResources().getString(R.string.event_send_ev),e.toStringData());
            mex.putString(getResources().getString(R.string.event_macro_send_type),t);

            transaction.replace(R.id.newEventMacroFragment, NewEventFragment.class, mex);

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