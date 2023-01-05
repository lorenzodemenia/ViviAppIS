package com.example.viviappis.ui.event;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.databinding.FragmentNewEventBinding;
import com.example.viviappis.databinding.FragmentNewEventMacroBinding;

import org.checkerframework.common.subtyping.qual.Bottom;

import java.util.List;


public class NewEventMacroFragment extends Fragment
{
    private Evento e;
    private String t;
    private TextView nm, ds, data, min, max,type, pub;
    private Button conf, mod;

    private FragmentNewEventMacroBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        e = new Evento(getArguments().getString(getResources().getString(R.string.event_send_ev)));
        t = getArguments().getString(getResources().getString(R.string.event_macro_send_type));
        System.out.println(e);


    }

    private void showEvent()
    {
        nm.setText(e.getName());
        ds.setText(e.getDescription());
        data.setText(e.getDate());
        type.setText(t);
        pub.setText(e.isPublic() ? "Publico" : "Privato");
        type.setText(t);

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
    private void setUpUIViews()
    {
        nm = binding.newEventMacroNm;
        data = binding.newEventMacroDate;
        ds = binding.newEventMacroDesc;
        min = binding.newEventMacroMin;
        max = binding.newEventMacroMax;
        pub = binding.newEventMacroPub;
        type = binding.newEventMacroType;

        conf = binding.newEventMacroConf;
        mod = binding.newEventMacroMod;
    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        conf.setOnClickListener((v)->
        {

        });

        mod.setOnClickListener((v)->
        {

        });
    }

}