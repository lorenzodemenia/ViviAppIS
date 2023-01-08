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
import com.example.viviappis.data.model.Timer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TiroAllaFuneFragment extends Fragment {
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    Timer t = new Timer();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_corsa_con_sacchi, container, false);
        mTextViewCountDown = (TextView) rootView.findViewById(R.id.text_view_countdown);
        mButtonStartPause = (Button) rootView.findViewById(R.id.button_start_pause);
        mButtonReset = (Button) rootView.findViewById(R.id.button_reset);
        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t.ismTimerRunning()) {
                    t.pauseTimer();
                    mButtonStartPause.setText("Start");
                    mButtonReset.setVisibility(View.VISIBLE);
                } else {
                    t.startTimer("idEvento");
                    if(!t.ismTimerRunning()){
                        mButtonStartPause.setText("Start");
                        mButtonStartPause.setVisibility(View.INVISIBLE);
                        mButtonReset.setVisibility(View.VISIBLE);
                        new AlertDialog.Builder(getContext())
                                .setTitle("Gioco Finito!")
                                .setMessage("\nSe vuoi giocare ancora tocca lo schermo.\nSe invece avete finito e volete vedere la Ranklist, tocca il bottone \"FINISCI EVENTO\"").show();
                    }else if(t.ismTimerRunning()){
                        mButtonStartPause.setText("pause");
                        mButtonReset.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t.resetTimer("idEvento");
                mButtonReset.setVisibility(View.INVISIBLE);
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
        });
        String timeLeftFormatted = t.updateCountDownText("idEvento");
        mTextViewCountDown.setText(timeLeftFormatted);

        return rootView;
    }



    public void onDestroyView(){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://timer-c99c2-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference dbRef = db.getReference();
        dbRef.child("timers").child("idEvento").removeValue(); //TODO: anche qui da modificare l' idEvento con l' id
        super.onDestroyView();
    }
}
