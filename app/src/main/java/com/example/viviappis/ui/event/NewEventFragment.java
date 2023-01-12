package com.example.viviappis.ui.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Utilities;
import com.example.viviappis.databinding.ActivityMainBinding;
import com.example.viviappis.databinding.FragmentNewEventBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class NewEventFragment extends Fragment implements OnMapReadyCallback
{
    private EditText inpName, inpDesc, inpDate, inpPsw, inpLuogo;
    private TextView result;
    private Spinner inpType, inpH, inpM;
    private Switch inpPublic;
    private Button bCont;
    private MapView inpMap;


    private FirebaseAuth au;
    private FirebaseFirestore db;

    private FragmentNewEventBinding binding;

    private Evento e;
    private String t;

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = FragmentNewEventBinding.inflate(getLayoutInflater());

        /*SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mapFragment)
                .commit();

        mapFragment.getMapAsync(this);*/

        /*
        *  <fragment xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:map="http://schemas.android.com/apk/res-auto"
            android:name="com.google.android.gms.maps.SupportMapFragment"
            android:id="@+id/map"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
        * */


        setUpUIViews();
        addActionListener();

        au = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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
       // inpMap = binding.newEventMap;
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
        cc = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,getResources().getTextArray(R.array.new_ev_spinner_item));
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inpType.setAdapter(cc);
    }

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
     * nasconde tutti i componenti appartenenti alla pagina newEvent
     */
    private void hide(Boolean a)
    {
        if(a)binding.newEvent.setVisibility(View.INVISIBLE);
        else binding.newEvent.setVisibility(View.VISIBLE);
    }


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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
    }
}