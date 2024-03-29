package com.example.viviappis.control.loginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viviappis.R;
import com.example.viviappis.control.AfterLogin;
import com.example.viviappis.data.model.Utente;
import com.google.firebase.auth.FirebaseAuth;

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
    private Switch pageStatuSwitch;

    private FirebaseAuth      au;

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
        super.setTitle("Login");

        au = FirebaseAuth.getInstance();
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
        pageStatuSwitch =(Switch) findViewById(R.id.page_status_switch);

    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        linkRegister.setOnClickListener((v) -> {startActivity(new Intent(LoginActivity.this, RegisterActivity.class));});

        pageStatuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { //Uno switch che se premuto sposta da accedi a registrati
                if (isChecked) {
                    // The switch is turned on
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            } else {
                    // The switch is turned off
                    Log.d("Switch", "Turned off");
                }
            }
        });

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

        Utente r = !p.isEmpty() && !e.isEmpty() ? new Utente("","", "","",e,p) : null;

        return r;
    }
}