package com.example.viviappis.ui.event;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Utilities;
import com.example.viviappis.databinding.FragmentNewEventBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


/**
 * Questa classe serve a gestire la pagina di creazione dell'evento
 * @author jacopo
 * @version 1.0
 */
public class NewEventFragment extends Fragment
{
    private EditText inpName, inpDesc, inpDate, inpPsw, inpLuogo;
    private TextView result;
    private Spinner inpType, inpH, inpM;
    private Switch inpPublic;
    private Button bCont;


    private FirebaseAuth au;

    private FragmentNewEventBinding binding;

    private Evento e;
    private String t;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = FragmentNewEventBinding.inflate(getLayoutInflater());

        setUpUIViews();
        addActionListener();

        au = FirebaseAuth.getInstance();

        try
        {
            e = new Evento(getArguments().getString(getResources().getString(R.string.event_send_ev)));
            t = getArguments().getString(getResources().getString(R.string.event_macro_send_type));
            inpName.setText(e.getName());
            inpDesc.setText(e.getDescription());
            inpDate.setText(e.getDate());
            inpName.setText(e.getName());
            inpPublic.setChecked(e.isPublic());
            inpType.setSelection(getPosition(t));
            inpPsw.setText(e.getPassword());
            inpH.setSelection(Integer.valueOf(e.getOra().split(":")[0]));
            inpM.setSelection(Integer.valueOf(e.getOra().split(":")[1])==0 ? 0 : 1);
            inpLuogo.setText(e.getLuogo());
        }
        catch (Exception e){}

        return  binding.getRoot();
    }

    /**
     * Questa funzione serve a inizzializzare le variabili dell'activity utili per recuperare i valori di input e mostrare i valori di output
     */
    private void setUpUIViews()
    {
        inpName = binding.newEvName;
        inpDesc = binding.newEvDesc;
        inpDate = binding.newEvDate;
        inpPsw = binding.newEvPsw;
        inpType = binding.newEvType;
        inpPublic = binding.newEvPublic;
        inpH = binding.newEvOra;
        inpM = binding.newEvMin;
        inpLuogo = binding.newEvLuogo;

        bCont   = binding.newEvCont;
        result  = binding.newEvResult;

        inpDesc.setText(getResources().getStringArray(R.array.description_event)[0]);
        inpDesc.setMovementMethod(new ScrollingMovementMethod());

        setValOra(inpH,23);

        List<String> bb = new ArrayList<>();
        bb.add("00");
        bb.add("30");
        ArrayAdapter cc = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,bb);
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inpM.setAdapter(cc);

        inpPsw.setVisibility(View.INVISIBLE);
        cc = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.new_ev_spinner_item));
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inpType.setAdapter(cc);
    }

    /**
     * setta il valore dell'input che rappresenta ora
     * @param inp spinner che rappresenta ora
     * @param m valore massimo a cui arrivare (compreso)
     */
    private void setValOra(Spinner inp, int m)
    {
        List<String> aa = new ArrayList<>();

        for (int i=0;i<=m; i++) aa.add((i<10 ? "0" : "")+i);

        ArrayAdapter bb = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,aa);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inp.setAdapter(bb);
    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        inpDate.setOnClickListener(Utilities.createDataInp(inpDate, getActivity(), result, 1));
        inpDate.setFocusable(false);

        inpPublic.setOnCheckedChangeListener((com, bool) ->
        {
            if (bool)
            {
                inpPublic.setText(getResources().getText(R.string.new_ev_pub));
                inpPsw.setVisibility(View.INVISIBLE);
            }
            else
            {
                inpPublic.setText(getResources().getText(R.string.new_ev_priv));
                inpPsw.setVisibility(View.VISIBLE);
            }
        });

        inpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String[] a = getResources().getStringArray(R.array.description_event);
                inpDesc.setText(a[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        bCont.setOnClickListener((v)->
        {
            e = validate();
            if(e==null)result.setText(R.string.err_no_all_data);
            else
            {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.setReorderingAllowed(true);

                Bundle mex = new Bundle();
                mex.putString(getResources().getString(R.string.event_send_ev),e.toStringData());
                mex.putString(getResources().getString(R.string.event_macro_send_type),inpType.getSelectedItem().toString());

                transaction.replace(R.id.newEventFragmant, NewEventMacroFragment.class, mex);

                transaction.commit();
                hide(true);
            }
        });
    }

    /**
     * funzione che trova la posizione, nell'array che rappresenta i giochi, della stringa passata
     * @param s stringa da cercare nell'array
     * @return posizione della stringa nell'array
     */
    private int getPosition(String s)
    {
        String a[] = getResources().getStringArray(R.array.new_ev_spinner_item);
        int i =0;

        while (i<a.length)
        {
            if(s.equals(a[i]))return i;
            i++;
        }

        return -1;
    }

    /**
     * serve a nascondere e a rendere visibile la pagina
     * @param a true pagina visibile, false pagina invisibile
     */
    private void hide(Boolean a)
    {
        if(a)binding.newEvent.setVisibility(View.INVISIBLE);
        else binding.newEvent.setVisibility(View.VISIBLE);
    }


    /**
     * serve a controllare se i campi di input per evento sono stati caricati correnttamente dall'utente
     * @return un oggetto di tipo evento se sono stati caricati in modo adeguato i campi di input, null altrimenti
     */
    private Evento validate()
    {
        String n = inpName.getText().toString();
        String ds = inpDesc.getText().toString();
        String d = inpDate.getText().toString();
        String p = inpPsw.getText().toString();
        String l = inpLuogo.getText().toString();
        boolean i = inpPublic.isChecked();
        String h = (String) inpH.getSelectedItem();
        String m = (String) inpM.getSelectedItem();

        if(!i && p.equals("")) p = Utilities.getNewPassword(10);
        else if(i)             p="";

        return  !ds.equals("") && !l.equals("") && !n.equals("") && !d.equals("") ?
                new Evento(n,ds,au.getCurrentUser().getEmail(),d.concat(","+h+":"+m),p,i, 0, 0, l) : null;
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