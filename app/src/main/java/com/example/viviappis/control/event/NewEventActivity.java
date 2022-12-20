package com.example.viviappis.control.event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewEventActivity extends AppCompatActivity
{
    private EditText inpName, inpDesc, inpDate, inpPsw;
    private TextView result;
    private Spinner inpType;
    private Switch inpPublic;
    private Button bCont;

    private FirebaseAuth au;
    private FirebaseFirestore db;

    private Evento e;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        super.setTitle("Nuovo evento");

        setUpUIViews();
        addActionListener();

        au = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Questa funzione serve a inizzializzare le variabili dell'activity utili per recuperare i valori di input e mostrare i valori di output
     */
    private void setUpUIViews()
    {
        inpName = (EditText) findViewById(R.id.newEvName1);
        inpDesc = (EditText) findViewById(R.id.newEvDesc1);
        inpDate = (EditText) findViewById(R.id.newEvDate1);
        inpPsw = (EditText) findViewById(R.id.newEvPsw);
        inpType = (Spinner) findViewById(R.id.newEvType1);
        inpPublic = (Switch) findViewById(R.id.newEvPublic1);

        bCont   = (Button) findViewById(R.id.newEvCont1);
        result  = (TextView) findViewById(R.id.newEvResult);

        String[] type={"test", "re", "fefe"};

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        inpType.setAdapter(aa);
    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        inpDate.setOnClickListener(Utilities.createDataInp(inpDate, this, result, 1));
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
            System.out.println(e);
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

        return !n.isEmpty() && !d.equals("")? new Evento(n,ds,au.getCurrentUser().getEmail(),d,p,i) : null;
    }
}