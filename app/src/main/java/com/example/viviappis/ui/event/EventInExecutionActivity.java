package com.example.viviappis.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class EventInExecutionActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth au = FirebaseAuth.getInstance();
    private Evento e = null;
    private String id;
    private String eventType;
    private String eventTypeString;

    private int nRoundTeam = 1;
    Button endGameTeam;
    Button replayTeam;
    Button team1Winner;
    Button team2Winner;
    TextView titleTeam;
    TextView team1players;
    TextView team2players;
    TextView roundTeam;
    Spinner spinTeam1;
    Spinner spinTeam2;
    TextView team1txt;
    TextView team2txt;
    Map<String, Integer> tempPointsTeam = new HashMap<>();
    boolean t1Wins = false;
    boolean t2Wins = false;
    List<String> team1 = new ArrayList<>();
    List<String> team2 = new ArrayList<>();
    String[] part;

    private String[] months;
    private Spinner choosePlayer;
    private int nRound = 1;
    TextView round;
    private TextView testoPrimo;
    private TextView testoSecondo;
    private TextView testoTerzo;
    private TextView contienePrimo;
    private TextView contieneSecondo;
    private TextView contieneTerzo;
    TextView staSotto;
    TextView scegli;
    List<String> partecipanti = new ArrayList<>();
    List<String> utentiPrimo = new ArrayList<>();
    List<String> utentiSecondo = new ArrayList<>();
    List<String> utentiTerzo = new ArrayList<>();
    Map<String, Integer> tempPointsPlayer = new HashMap<>();
    private Button resetButton;
    private Button fineEventoButton;
    Spinner primo;
    Spinner secondo;
    Spinner terzo;

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

    private void switchLayout()
    {
        String[] s = getResources().getStringArray(R.array.new_ev_spinner_item);

        if(eventType.equals(s[0]) || eventType.equals(s[1]) || eventType.equals(s[2])){
            setContentView(R.layout.activity_event_in_execution_team_game);
            endGameTeam = (Button) findViewById(R.id.endGameTeam);
            replayTeam = (Button) findViewById(R.id.replayTeam);
            team1Winner = (Button) findViewById(R.id.team1Winner);
            team2Winner = (Button) findViewById(R.id.team2Winner);
            titleTeam = (TextView) findViewById(R.id.titleTeam);
            team1players = (TextView) findViewById(R.id.team1players);
            team2players = (TextView) findViewById(R.id.team2players);
            roundTeam = (TextView) findViewById(R.id.roundTeam);
            spinTeam1 = (Spinner) findViewById(R.id.spinTeam1);
            spinTeam2 = (Spinner) findViewById(R.id.spinTeam2);
            team1txt = (TextView) findViewById(R.id.team1);
            team2txt = (TextView) findViewById(R.id.team2);
            titleTeam.setText(eventType);
            team1players.setMovementMethod(new ScrollingMovementMethod());
            team2players.setMovementMethod(new ScrollingMovementMethod());
            roundTeam.setText(getResources().getString(R.string.ev_in_exec_round) + " " + Integer.toString(nRoundTeam));
            team1txt.setText(getResources().getString(R.string.ev_in_exec_team1));
            team2txt.setText(getResources().getString(R.string.ev_in_exec_team1));
            endGameTeam.setText(getResources().getString(R.string.ev_in_exec_endgame));
            replayTeam.setText(getResources().getString(R.string.ev_in_exec_replay));
            team1Winner.setText(getResources().getString(R.string.ev_in_exec_team1_wins));
            team2Winner.setText(getResources().getString(R.string.ev_in_exec_team2_wins));
            addPartecipantsToMap();
            createPartString();
            setSpinner(spinTeam1, team1, team2, team1players, team2players);
            setSpinner(spinTeam2, team2, team1, team2players, team1players);
            addActionListenerTeam();
        }else{
            eventTypeString = "single";
            setContentView(R.layout.activity_event_in_execution_single_game);
            resetButton = (Button) findViewById(R.id.button_reset);
            fineEventoButton = (Button) findViewById(R.id.button_fine_evento);
            staSotto = (TextView) findViewById(R.id.textViewStaSotto);
            scegli = (TextView) findViewById(R.id.textViewScegli);
            testoPrimo = (TextView) findViewById(R.id.textView1c);
            testoSecondo = (TextView) findViewById(R.id.textView2c);
            testoTerzo = (TextView) findViewById(R.id.textView3c);
            contienePrimo = (TextView) findViewById(R.id.textViewPrimo);
            contieneSecondo = (TextView) findViewById(R.id.textViewSecondo);
            contieneTerzo = (TextView) findViewById(R.id.textViewTerzo);

            round = (TextView) findViewById(R.id.round);
            primo = (Spinner) findViewById(R.id.primoSpinner);
            secondo = (Spinner) findViewById(R.id.secondoSpinner);
            terzo = (Spinner) findViewById(R.id.terzoSpinner);

            round.setText(getResources().getString(R.string.ev_in_exec_round) + " " + Integer.toString(nRound));
            addPartecipantsToMap2();
            createPartString2();
            populateChoosePlayer(primo, utentiPrimo, contienePrimo);
            populateChoosePlayer(secondo, utentiSecondo, contieneSecondo);
            populateChoosePlayer(terzo, utentiTerzo, contieneTerzo);
            partecipanti = e.getPartecipants();
            chooseRandom();
            addActionListenerTeam2();
        }
    }
    private void populateChoosePlayer(Spinner choosePlayer, List<String> utenti, TextView seePlayers) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, part);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choosePlayer.setAdapter(adapter);

        choosePlayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(e.getPartecipants().contains(item)){
                    if(utenti.size()<1) {
                        utenti.add(item);
                        printListPlayer2(seePlayers, utenti);
                    } else {
                        utenti.remove(0);
                        utenti.add(item);
                        printListPlayer2(seePlayers, utenti);
                    }
                }

            }

            private void setText(){

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void chooseRandom() {
        int randomNum = new Random().nextInt(partecipanti.size()-1 + 1);
        printListPlayer2(scegli, partecipanti, randomNum);
    }
    private void addActionListenerTeam2(){
        fineEventoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(String s: e.getPartecipants()){
                    DocumentReference docRef = db.collection("users").document(s);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot doc = task.getResult();
                                Utente u = new Utente(Objects.requireNonNull(doc.getData()));
                                docRef.update("score", u.getScore() + tempPointsPlayer.get(s));
                            }
                        }
                    });
                }
                startActivity(new Intent(EventInExecutionActivity.this, AfterLogin.class));
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseRandom();
                for(String s : partecipanti)
                    tempPointsPlayer.computeIfPresent(s, (k,v) -> v +10);
                tempPointsPlayer.computeIfPresent(utentiPrimo.get(0), (k,v) -> v +10);
                tempPointsPlayer.computeIfPresent(utentiSecondo.get(0), (k,v) -> v +5);
                tempPointsPlayer.computeIfPresent(utentiTerzo.get(0), (k,v) -> v +3);
                nRound++;
                round.setText(getResources().getString(R.string.ev_in_exec_round) + " " + Integer.toString(nRound));
            }
        });
    }
    private void printListPlayer2(TextView seePlayers, List<String> utenti, int n){
        String players;
        players = utenti.get(n);
        seePlayers.setText(players);
    }

    private void createPartString2(){
        List<String> tmp = new ArrayList<>();
        tmp.addAll(e.getPartecipants());
        part = tmp.toArray(new String[0]);
    }
    private void printListPlayer2(TextView seePlayers, List<String> utenti){
        String players = "";
        for(String s: utenti){
            players += "\n" + s + " " + tempPointsPlayer.get(s).toString();
        }
        seePlayers.setText(players);
    }
    private void addPartecipantsToMap2(){
        for(String s: e.getPartecipants()){
            tempPointsPlayer.put(s, 0);
        }
    }
    private void createPartString(){
        List<String> tmp = new ArrayList<>();
        tmp.add("Aggiungi Giocatore");
        tmp.addAll(e.getPartecipants());
        part = tmp.toArray(new String[0]);
    }

    private void printListPlayer(TextView tv, List<String> team){
        String players = "";
        for(String s: team){
            players += "\n" + s + " " + tempPointsTeam.get(s).toString();
        }
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
            t2Wins = false;
            team2Winner.setBackgroundColor(getResources().getColor(R.color.giallocra));
        });
        team2Winner.setOnClickListener((v) -> {
            t2Wins = true;
            team2Winner.setBackgroundColor(getResources().getColor(R.color.azzurro));
            t1Wins = false;
            team1Winner.setBackgroundColor(getResources().getColor(R.color.giallocra));
        });
    }

    private void setSpinner(Spinner spinner, List<String> teamSelected, List<String> otherTeam, TextView tvSelected, TextView otherTV){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EventInExecutionActivity.this, android.R.layout.simple_spinner_item, part);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(e.getPartecipants().contains(item)){
                    if(otherTeam.contains(item)){
                        otherTeam.remove(item);
                    }
                    if(!teamSelected.contains(item))teamSelected.add(item);
                    else                            teamSelected.remove(item);
                    printListPlayer(tvSelected, teamSelected);
                    printListPlayer(otherTV, otherTeam);
                }

            }

            private void setText(){

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
        roundTeam.setText(getResources().getString(R.string.ev_in_exec_round) + " " + Integer.toString(nRoundTeam));
        printListPlayer(team1players, team1);
        printListPlayer(team2players, team2);
        team1Winner.setBackgroundColor(getResources().getColor(R.color.giallocra));
        team2Winner.setBackgroundColor(getResources().getColor(R.color.giallocra));
    }

    private void addPartecipantsToMap(){
        for(String s: e.getPartecipants()){
            tempPointsTeam.put(s, 0);
        }
    }

    //passare almeno id all pagina


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