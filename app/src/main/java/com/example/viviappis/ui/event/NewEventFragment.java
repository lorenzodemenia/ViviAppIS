package com.example.viviappis.ui.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class NewEventFragment extends Fragment implements OnMapReadyCallback
{
    private EditText inpName, inpDesc, inpDate, inpPsw;
    private TextView result;
    private Spinner inpType;
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
        }
        catch (Exception e){}

        System.out.println("dfdfsasdaf");


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

        bCont   = binding.newEvCont;
        result  = binding.newEvResult;


        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,getActivity().getResources().getTextArray(R.array.new_ev_spinner_item));
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inpType.setAdapter(aa);
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


        bCont.setOnClickListener((v)->
        {
            e = validate();
            if(e==null)result.setText(R.string.err_no_all_data);
            else
            {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
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
        boolean i = inpPublic.isChecked();

        if(!i && p.equals("")) p = Utilities.getNewPassword(10);
        else if(i)             p="";

        return !n.isEmpty() && !d.equals("") ? new Evento(n,ds,au.getCurrentUser().getEmail(),d,p,i, 0, 0) : null;
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