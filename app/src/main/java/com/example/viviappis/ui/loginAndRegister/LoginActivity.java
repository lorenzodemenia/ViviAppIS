package com.example.viviappis.ui.loginAndRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.viviappis.MainActivity;
import com.example.viviappis.R;
import com.example.viviappis.data.model.Utente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity
{
    private EditText inpPsw, inpEmail;
    private Button bLog;
    private TextView linkRegister;

    private FirebaseAuth au;
    private FirebaseFirestore db;

    //eliminabile
    private TextView result;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpUIViews();
        addActionListener();

        au = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void setUpUIViews()
    {
        inpEmail = (EditText) findViewById(R.id.loginEmail);
        inpPsw = (EditText) findViewById(R.id.loginPassword);

        bLog = (Button) findViewById(R.id.login);

        //eliminabile
        result = (TextView) findViewById(R.id.loginResult);
        linkRegister = (TextView) findViewById(R.id.linkRegister);
    }

    private void addActionListener()
    {
        linkRegister.setOnClickListener((v) -> {startActivity(new Intent(LoginActivity.this, RegisterActivity.class));});

        bLog.setOnClickListener((v)->
        {
            Utente u = validate();

            if(u!=null)
            {
                au.signInWithEmailAndPassword(u.getEmail(), u.getPassword());
                //db.collection("users").document(u.getEmail()).
            }
            else result.setText("Inserisci tutti i dati richiesti");
        });
    }

    private Utente validate()
    {
        String p = inpPsw.getText().toString();
        String e = inpEmail.getText().toString();

        Utente r = !p.isEmpty() && !e.isEmpty() ? new Utente("","","",e,p) : null;

        return r;
    }
}