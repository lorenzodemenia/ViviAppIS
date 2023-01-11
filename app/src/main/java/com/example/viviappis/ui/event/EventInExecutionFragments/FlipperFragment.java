package com.example.viviappis.ui.event.EventInExecutionFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Timer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class FlipperFragment extends Fragment {
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button endGameButton;
    Timer t = new Timer();
    FirebaseAuth au = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email;
    String id;
    Evento e;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_flipper, container, false);
        e = new Evento(savedInstanceState.getString(getResources().getString(R.string.event_send_ev)));
        db.collection(getResources().getString(R.string.db_rac_users)).get().addOnCompleteListener((t) -> {
            email = au.getCurrentUser().getEmail();
        });
        id = getArguments().getString(getResources().getString(R.string.event_send_ev));
        if (email.equals(e.getCreator())) {
            endGameButton = (Button) rootView.findViewById(R.id.end_game_button);
            endGameButton.setText(R.string.event_end);
            mTextViewCountDown = (TextView) rootView.findViewById(R.id.text_view_countdown);
            mButtonStartPause = (Button) rootView.findViewById(R.id.button_start_pause);
            mButtonReset = (Button) rootView.findViewById(R.id.button_reset);
            mButtonStartPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (t.ismTimerRunning()) {
                        t.pauseTimer();
                        mButtonStartPause.setText("Start");
                        mButtonReset.setVisibility(View.VISIBLE);
                    } else {
                        t.startTimer(id);
                        if (!t.ismTimerRunning()) {
                            mButtonStartPause.setText("Start");
                            mButtonStartPause.setVisibility(View.INVISIBLE);
                            mButtonReset.setVisibility(View.VISIBLE);
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Gioco Finito!")
                                    .setMessage("\nSe vuoi giocare ancora tocca lo schermo.\nSe invece avete finito e volete vedere la Ranklist, tocca il bottone \"FINISCI EVENTO\"").show();
                        } else if (t.ismTimerRunning()) {
                            mButtonStartPause.setText("Pause");
                            mButtonReset.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });
            mButtonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t.resetTimer(id);
                    mButtonReset.setVisibility(View.INVISIBLE);
                    mButtonStartPause.setVisibility(View.VISIBLE);
                }
            });
            FirebaseDatabase db = FirebaseDatabase.getInstance("https://timer-c99c2-default-rtdb.europe-west1.firebasedatabase.app");
            DatabaseReference dbRef = db.getReference("timers").child(id);
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mTextViewCountDown.setText((CharSequence) snapshot.getValue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setVisibility(View.INVISIBLE);
            FirebaseDatabase db = FirebaseDatabase.getInstance("https://timer-c99c2-default-rtdb.europe-west1.firebasedatabase.app");
            DatabaseReference dbRef = db.getReference("timers").child(id);
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mTextViewCountDown.setText((CharSequence) snapshot.getValue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        return rootView;
    }

    public void onDestroyView() {
        if (email.equals(e.getCreator())) {
            FirebaseDatabase db = FirebaseDatabase.getInstance("https://timer-c99c2-default-rtdb.europe-west1.firebasedatabase.app");
            DatabaseReference dbRef = db.getReference();
            dbRef.child("timers").child(id).removeValue();
            super.onDestroyView();
        }
    }
}
