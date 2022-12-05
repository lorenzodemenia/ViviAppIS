package com.example.viviappis.ui.loginAndRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.viviappis.AfterLogin;
import com.example.viviappis.R;
import com.example.viviappis.data.model.Utente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Questa classe serve a gestire activity di login
 * @author Jacopo Cini
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity
{
    private EditText inpPsw, inpEmail;
    private Button   bLog;
    private TextView linkRegister;

    private FirebaseAuth      au;
    private FirebaseFirestore db;

    //eliminabile
    private TextView result;

    /**
     * Questa funzione permette di creare activity per il login
     * @param savedInstanceState Rappresenta lo stato che ce alla creazione dell'attività
     */
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

    /**
     * Questa funzione serve a inizzializzare le variabili dell'activity utili per recuperare i valori di input e mostrare i valori di output
     */
    private void setUpUIViews()
    {
        inpEmail = (EditText) findViewById(R.id.loginEmail);
        inpPsw = (EditText) findViewById(R.id.loginPassword);

        bLog = (Button) findViewById(R.id.login);

        //eliminabile
        result = (TextView) findViewById(R.id.loginResult);
        linkRegister = (TextView) findViewById(R.id.linkRegister);
    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        linkRegister.setOnClickListener((v) -> {startActivity(new Intent(LoginActivity.this, RegisterActivity.class));});

        bLog.setOnClickListener((v)->
        {
            Utente u = validate();

            if(u!=null)
            {
                au.signInWithEmailAndPassword(u.getEmail(), u.getPassword()).addOnCompleteListener((task ->
                {
                    if(task.isSuccessful())
                    {
                        result.setText(R.string.log_succ);
                        startActivity(new Intent(this, AfterLogin.class));
                    }
                    else result.setText(R.string.log_rej);
                }));
                //db.collection("users").document(u.getEmail()).

            }
            else result.setText(R.string.log_err_no_all_data);
        });
    }
    /**
     * Controlla se l'utente inserito nella pagina di login e un utente valido(ha tutti i campi non nulli)
     * @return Ritorna l'utente con email e password, o null in caso in cui uno dei due campi siano vuoti
     * */
    private Utente validate()
    {
        String p = inpPsw.getText().toString();
        String e = inpEmail.getText().toString();

        Utente r = !p.isEmpty() && !e.isEmpty() ? new Utente("","","",e,p) : null;

        return r;
    }
}