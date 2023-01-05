package com.example.viviappis.ui.event;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.viviappis.Pair;
import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.databinding.FragmentNewEventBinding;
import com.example.viviappis.databinding.FragmentNewEventMacroBinding;
import com.example.viviappis.ui.home.HomeFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.common.subtyping.qual.Bottom;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewEventMacroFragment extends Fragment
{
    private Evento e;
    private String t;
    private TextView nm, ds, data, min, max,type, pub, psw;
    private Button conf, mod;

    private Map<String, Pair<Integer,Integer>> minMax = new HashMap<>();
    private FirebaseFirestore db;

    private FragmentNewEventMacroBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        e = new Evento(getArguments().getString(getResources().getString(R.string.event_send_ev)));
        t = getArguments().getString(getResources().getString(R.string.event_macro_send_type));
        db = FirebaseFirestore.getInstance();

        createminMax();
    }

    private void createminMax()
    {
        String a[] = getResources().getStringArray(R.array.new_ev_spinner_item);
        int m[] = getResources().getIntArray(R.array.min_for_event);
        int M[] = getResources().getIntArray(R.array.max_for_event);

        for (int i =0;i<a.length;i++)minMax.put(a[i],new Pair<>(m[i],M[i]));
    }


    private void showEvent()
    {
        nm.setText(e.getName());
        ds.setText(e.getDescription());
        data.setText(e.getDate());
        type.setText(t);
        pub.setText(e.isPublic() ? "Publico" : "Privato");
        type.setText(t);
        e.setMinPart(minMax.get(t).getX());
        e.setMaxPart(minMax.get(t).getY());
        max.setText(e.getMaxPart()+"");
        min.setText(e.getMinPart()+"");
        psw.setText(e.getPassword());

        //class


    }

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

        conf = binding.newEventMacroConf;
        mod = binding.newEventMacroMod;
    }


    /**
     * nasconde tutti i componenti appartenenti alla pagina newEvent
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

            db.collection(getResources().getString(R.string.db_rac_events)).add(a).addOnCompleteListener((task)->
            {
                if(task.isSuccessful())
                {
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getString(R.string.allert_title_new_event))
                            .setMessage(getResources().getString(R.string.allert_text_new_event)).show();

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.setReorderingAllowed(true);

                    transaction.replace(R.id.newEventMacroFragment, HomeFragment.class, null);

                    transaction.commit();
                    hide(true);
                }

            });
        });

        mod.setOnClickListener((v)->
        {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.setReorderingAllowed(true);

            Bundle mex = new Bundle();
            mex.putString(getResources().getString(R.string.event_send_ev),e.toStringData());
            mex.putString(getResources().getString(R.string.event_macro_send_type),t);

            transaction.replace(R.id.newEventMacroFragment, NewEventFragment.class, mex);

            transaction.commit();
            hide(true);
        });
    }

}