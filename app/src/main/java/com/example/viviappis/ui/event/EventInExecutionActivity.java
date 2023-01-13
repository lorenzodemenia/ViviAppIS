package com.example.viviappis.ui.event;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class EventInExecutionActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth au = FirebaseAuth.getInstance();
    private Evento e = null;

    private String d;
    private String id;
    private String eventType;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra(getResources().getString(R.string.event_send_ev));

        db.collection(getResources().getString(R.string.db_rac_events)).document(id).get().addOnCompleteListener((t)->
        {
            if (t.isSuccessful())
            {
                e = new Evento(t.getResult().getData());
                eventType = t.getResult().getString("class");
                switchLayout();
            }
        });


    }


    //passare almeno id all pagina
    private void switchLayout()
    {
        String[] s = getResources().getStringArray(R.array.new_ev_spinner_item);

        if(eventType.equals(s[0]) || eventType.equals(s[1]) || eventType.equals(s[2])){
            setContentView(R.layout.activity_event_in_execution_team_game);
        }else{
            setContentView(R.layout.activity_event_in_execution_single_game);
        }
    }

    public Map<String, Object> filterListByID(List<DocumentSnapshot> l, String id)
    {
        int i=0;

        while (i<l.size())
        {
            Map<String, Object> m = l.get(i).getData();
            String cnt = (String) m.get("class");

            if(!cnt.startsWith(id)) l.remove(i);
            else                      i++;
        }
        Map<String, Object> m = l.get(0).getData();
        return m;
    }

}
