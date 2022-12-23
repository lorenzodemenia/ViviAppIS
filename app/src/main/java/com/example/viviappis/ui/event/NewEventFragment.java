package com.example.viviappis.ui.event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
        inpMap = binding.newEventMap;

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
            else {
                System.out.println(e);
            }
        });
    }

    private Evento validate()
    {
        String n = inpName.getText().toString();
        String ds = inpDesc.getText().toString();
        String t = inpType.getSelectedItem().toString();
        String d = inpDate.getText().toString();
        String p = inpPsw.getText().toString();
        boolean i = inpPublic.isChecked();

        if(!i && p.equals("")) p = Utilities.getNewPassword(10);
        else if(i)             p="";

        return !n.isEmpty() && !d.equals("")? new Evento(n,ds,au.getCurrentUser().getEmail(),d,p,i, 0, 0) : null;
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