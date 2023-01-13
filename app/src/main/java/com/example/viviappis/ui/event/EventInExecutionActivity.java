package com.example.viviappis.ui.event;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.Pair;
import com.example.viviappis.R;
import com.example.viviappis.control.AfterLogin;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Utente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EventInExecutionActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth au = FirebaseAuth.getInstance();
    private Evento e = null;
    private String id;
    private String eventType;
    private String eventTypeString;

    private int nRoundTeam = 1;
    Button endGameTeam = (Button) findViewById(R.id.endGameTeam);
    Button replayTeam = (Button) findViewById(R.id.replayTeam);
    Button team1Winner = (Button) findViewById(R.id.team1Winner);
    Button team2Winner = (Button) findViewById(R.id.team2Winner);
    TextView titleTeam = (TextView) findViewById(R.id.titleTeam);
    TextView team1players = (TextView) findViewById(R.id.team1players);
    TextView team2players = (TextView) findViewById(R.id.team2players);
    TextView roundTeam = (TextView) findViewById(R.id.roundTeam);
    Spinner spinTeam1 = (Spinner) findViewById(R.id.spinTeam1);
    Spinner spinTeam2 = (Spinner) findViewById(R.id.spinTeam2);
    TextView team1txt = (TextView) findViewById(R.id.team1);
    TextView team2txt = (TextView) findViewById(R.id.team2);
    Map<String, Integer> tempPointsTeam = new HashMap<>();
    boolean t1Wins = false;
    boolean t2Wins = false;
    List<String> team1 = new ArrayList<>();
    List<String> team2 = new ArrayList<>();
    String[] part;

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
        if(eventTypeString.equals("team")){
            titleTeam.setText(eventType);
            roundTeam.setText("Round " + Integer.toString(nRoundTeam));
            team1txt.setText("Squadra 1");
            team2txt.setText("Squadra 2");
            endGameTeam.setText("Termina Evento");
            replayTeam.setText("Rigioca");
            team1Winner.setText("Vince Squadra 1");
            team2Winner.setText("Vince Squadra 2");
            addPartecipantsToMap();
            createPartString();
            setSpinner(spinTeam1, team1, team1players);
            setSpinner(spinTeam2, team2, team2players);
            addActionListenerTeam();
        }else if(eventTypeString.equals("single")){

        }
    }

    private void createPartString(){
        List<String> tmp = new ArrayList<>(Arrays.asList(part));
        tmp.addAll(e.getPartecipants());
        part = tmp.toArray(part);
    }

    private void printListPlayer(TextView tv, List<String> team){
        String players = team.get(0);
        String tmp = players;
        team.remove(players);
        for(String s: team){
            players += "\n" + s + " " + tempPointsTeam.get(s).toString();
        }
        team.add(tmp);
        tv.setText(players);
    }

    private void addActionListenerTeam(){
        endGameTeam.setOnClickListener((v) -> {
            endGameTeam();
        });

        replayTeam.setOnClickListener((v) -> {
            setReplayTeam();
        });

        team1Winner.setOnClickListener((v) -> {
            t1Wins = true;
            team1Winner.setBackgroundColor(getResources().getColor(R.color.azzurro));
        });
        team2Winner.setOnClickListener((v) -> {
            t2Wins = true;
            team2Winner.setBackgroundColor(getResources().getColor(R.color.azzurro));
        });
    }

    private void setSpinner(Spinner spinner, List<String> team, TextView tv){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EventInExecutionActivity.this, android.R.layout.simple_spinner_item, part);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                team.add(item);
                List<String> l = new ArrayList<>(Arrays.asList(part));
                l.remove(item);
                part = l.toArray(part);
                printListPlayer(tv, team);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void endGameTeam(){
        for(String s: e.getPartecipants()){
            DocumentReference docRef = db.collection("users").document(s);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        Utente u = new Utente(Objects.requireNonNull(doc.getData()));
                        docRef.update("score", u.getScore() + tempPointsTeam.get(s));
                    }
                }
            });
        }
        startActivity(new Intent(EventInExecutionActivity.this, AfterLogin.class));
    }

    private void setReplayTeam(){
        if(t1Wins && !t2Wins){
            for(String s: team1){
                tempPointsTeam.computeIfPresent(s, (k,v) -> v+20);
            }
            t1Wins = false;
        } else if(t2Wins && !t1Wins){
            for(String s: team2){
                tempPointsTeam.computeIfPresent(s, (k,v) -> v+20);
            }
            t2Wins = false;
        }
        nRoundTeam++;
        roundTeam.setText("Round " + Integer.toString(nRoundTeam));

    }

    private void addPartecipantsToMap(){
        for(String s: e.getPartecipants()){
            tempPointsTeam.put(s, 0);
        }
    }

    //passare almeno id all pagina
    private void switchLayout()
    {
        String[] s = getResources().getStringArray(R.array.new_ev_spinner_item);

        if(eventType.equals(s[0]) || eventType.equals(s[1]) || eventType.equals(s[2])){
            eventTypeString = "team";
            setContentView(R.layout.activity_event_in_execution_team_game);
        }else{
            eventTypeString = "single";
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
