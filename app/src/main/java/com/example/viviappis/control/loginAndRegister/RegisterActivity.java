package com.example.viviappis.control.loginAndRegister;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Utente;
import com.example.viviappis.data.model.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Questa classe serve a gestire activity di register
 * @author Jacopo Cini
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity
{
    private EditText inpUser, inpPsw, inpEmail, inpDate, inpName, inpSurn;
    private Button bReg;
    private Switch changePage;

    private FirebaseAuth au;
    private FirebaseFirestore db;
    private CheckBox privacyCheck;


    //eliminabile
    private TextView result;

    /**
     * Questa funzione permette di creare activity per il register
     * @param savedInstanceState Rappresenta lo stato che ce alla creazione dell'attività
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpUIViews();
        addActionListener();
        super.setTitle("Register");

        au = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Questa funzione serve a inizzializzare le variabili dell'activity utili per recuperare i valori di input e mostrare i valori di output
     */
    private void setUpUIViews()
    {
        inpUser  = (EditText) findViewById(R.id.registerName);
        inpEmail = (EditText) findViewById(R.id.registerEmail);
        inpPsw   = (EditText) findViewById(R.id.registerPassword);
        inpDate  = (EditText) findViewById(R.id.registerDataNasc);
        inpName  = (EditText) findViewById(R.id.registerName);
        inpSurn  = (EditText) findViewById(R.id.registerSurname);
        privacyCheck= (CheckBox) findViewById(R.id.checkPrivacy);


        bReg = (Button) findViewById(R.id.register);

        changePage = (Switch) findViewById(R.id.page_status_switch2);
        ((TextView)findViewById(R.id.textView3)).setMovementMethod(new ScrollingMovementMethod());

        result = (TextView) findViewById(R.id.registerResult);
    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        changePage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { //Uno switch che se premuto sposta da accedi a registrati
                if (isChecked) {
                    // The switch is turned on
                    Log.d("Switch", "Turned on");

                } else {
                    // The switch is turned off
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });

        bReg.setOnClickListener((v) ->
        {
            Utente u = validate();

            if (u != null)
            {
                au.createUserWithEmailAndPassword(u.getEmail(), u.getPassword()).addOnCompleteListener((task) ->
                {
                    if (task.isSuccessful())
                    {
                        Utilities.dbGetCollUsers(db,getResources()).
                                document(u.getEmail()).set(u.toMap()).addOnCompleteListener((t) ->
                                {
                                    if (t.isSuccessful())
                                    {
                                        result.setText(R.string.reg_succ);
                                        result.setOnClickListener((r) ->
                                        {
                                            startActivity(new Intent(this, LoginActivity.class));
                                        });
                                        startActivity(new Intent(this, LoginActivity.class));
                                    } else result.setText(R.string.reg_rej);
                                });
                    }
                    else
                    {
                        try {throw task.getException();}
                        catch (FirebaseAuthInvalidCredentialsException e)
                        {
                            if (e instanceof FirebaseAuthWeakPasswordException)
                                result.setText(R.string.reg_err_psw);
                            else result.setText(R.string.reg_err_email);
                        }
                        catch (FirebaseAuthUserCollisionException e) {result.setText(R.string.reg_err_exist);}
                        catch (Exception e) {result.setText(R.string.err_gen);}
                    }
                });
            } else result.setText(R.string.err_no_all_data);
        });

        inpDate.setOnClickListener(Utilities.createDataInp(inpDate,this, result,-1));
        inpDate.setFocusable(false);

        inpPsw.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (charSequence.length() < 6) inpPsw.setTextColor(Color.RED);
                else if (charSequence.length() >= 6 && charSequence.length() < 9)
                    inpPsw.setTextColor(Color.rgb(255, 165, 0));
                else inpPsw.setTextColor(Color.GREEN);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        inpEmail.setOnFocusChangeListener((view, b) ->
        {
            if(!b)
            {
                String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
                //Compile regular expression to get the pattern
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(inpEmail.getText());

                if(matcher.matches()) inpEmail.setTextColor(Color.GREEN);
                else                  inpEmail.setTextColor(Color.RED);
            }
            else inpEmail.setTextColor(Color.BLACK);
        });
    }


    /**
     * Controlla se l'utente inserito nella pagina di register e un utente valido(ha tutti i campi non nulli)
     * @return Ritorna l'utente con email e password, o null in caso in cui uno dei due campi siano vuoti
     * */
    private Utente validate()
    {
        String u = inpUser.getText().toString();
        String n = inpName.getText().toString();
        String s = inpSurn.getText().toString();
        String p = inpPsw.getText().toString();
        String e = inpEmail.getText().toString();
        String d = inpDate.getText().toString();
        boolean pc=privacyCheck.isChecked();

        return !u.isEmpty() && !p.isEmpty() && !e.isEmpty() && !d.isEmpty()  && !d.equals("") && pc? new Utente(n, s, u, d, e,p) : null;
    }
}