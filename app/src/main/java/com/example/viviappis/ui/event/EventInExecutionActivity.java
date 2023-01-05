package com.example.viviappis.ui.event;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.MacroAree.AreaA.CorsaConSacchi;
import com.example.viviappis.data.model.MacroAree.AreaA.Flipper;
import com.example.viviappis.data.model.MacroAree.AreaA.MoscaCieca;
import com.example.viviappis.data.model.MacroAree.AreaA.Nascondino;
import com.example.viviappis.data.model.MacroAree.AreaA.Sardine;
import com.example.viviappis.data.model.MacroAree.AreaA.UnoDueTreStella;
import com.example.viviappis.data.model.MacroAree.AreaD.PallaPrigioniera;
import com.example.viviappis.data.model.MacroAree.AreaD.RubaBandiera;
import com.example.viviappis.data.model.MacroAree.AreaD.TiroAllaFune;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventInExecutionActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth au = FirebaseAuth.getInstance();
    private Evento e = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String eventType = savedInstanceState.getString(getResources().getString(R.string.event_macro_send_type));
        e = new Evento(savedInstanceState.getString(getResources().getString(R.string.event_send_ev)));
        String[] s = (String[]) getResources().getTextArray(R.array.new_ev_spinner_item);
        if (eventType.equals(s[0])) {
            e = new PallaPrigioniera(e.getName(), e.getDescription(), e.getCreator(), e.getDate(), e.getPassword(), e.isPublic(), "Squadra 1", "Squadra 2", 10); // points da stabilire
        } else if(eventType.equals(s[1])) {
            e = new RubaBandiera(e.getName(), e.getDescription(), e.getCreator(), e.getDate(), e.getPassword(), e.isPublic(), "Squadra 1", "Squadra 2", 10); // points da stabilire
        } else if(eventType.equals(s[2])) {
            e = new TiroAllaFune(e.getName(), e.getDescription(), e.getCreator(), e.getDate(), e.getPassword(), e.isPublic(), "Squadra 1", "Squadra 2", 10); // points da stabilire
        } else if(eventType.equals(s[3])) {
            e = new CorsaConSacchi(e.getName(), e.getDescription(), e.getCreator(), e.getDate(), e.getPassword(), e.isPublic(), 10); // points da stabilire
        } else if(eventType.equals(s[4])) {
            e = new Flipper(e.getName(), e.getDescription(), e.getCreator(), e.getDate(), e.getPassword(), e.isPublic(), 10); // points da stabilire
        } else if(eventType.equals(s[5])) {
            e = new MoscaCieca(e.getName(), e.getDescription(), e.getCreator(), e.getDate(), e.getPassword(), e.isPublic(), 10); // points da stabilire
        } else if(eventType.equals(s[6])) {
            e = new Nascondino(e.getName(), e.getDescription(), e.getCreator(), e.getDate(), e.getPassword(), e.isPublic(),  10); // points da stabilire
        } else if(eventType.equals(s[7])) {
            e = new Sardine(e.getName(), e.getDescription(), e.getCreator(), e.getDate(), e.getPassword(), e.isPublic(),  10); // points da stabilire
        } else if(eventType.equals(s[8])) {
            e = new UnoDueTreStella(e.getName(), e.getDescription(), e.getCreator(), e.getDate(), e.getPassword(), e.isPublic(),  10); // points da stabilire
        }

    }


}
