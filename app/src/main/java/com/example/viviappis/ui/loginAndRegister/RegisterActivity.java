package com.example.viviappis.ui.loginAndRegister;

import static com.example.viviappis.data.model.Utilities.generateRandomId;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Utente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class RegisterActivity extends AppCompatActivity
{
    private EditText inpUser, inpPsw, inpEmail, inpDate;
    private Button bReg;

    private FirebaseAuth au;
    private FirebaseFirestore db;

    //eliminabile
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpUIViews();
        addActionListener();

        au = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();}

    private void setUpUIViews()
    {
        inpUser = (EditText) findViewById(R.id.registerUsername);
        inpEmail = (EditText) findViewById(R.id.registerEmail);
        inpPsw = (EditText) findViewById(R.id.registerPassword);
        inpDate = (EditText) findViewById(R.id.registerDataNasc);

        bReg = (Button) findViewById(R.id.register);

        //eliminabile
        result = (TextView) findViewById(R.id.registerResult);
    }

    private void addActionListener()
    {
        bReg.setOnClickListener((v)->
        {
            Utente u = validate();

            if(u!=null)
            {
                au.createUserWithEmailAndPassword(u.getEmail(), u.getPassword()).addOnCompleteListener((task) ->
                {
                    if(task.isSuccessful())
                    {
                        db.collection(getResources().getString(R.string.db_rac_users)).
                                document(u.getEmail()).set(Utente.userMap(u)).addOnCompleteListener((t) ->
                        {
                            if(t.isSuccessful())
                            {
                                result.setText(R.string.reg_succ);
                                result.setOnClickListener((r) -> {startActivity(new Intent(this, LoginActivity.class));});
                            }
                            else result.setText(R.string.reg_rej);
                        });
                    }
                    else
                    {
                        try {throw task.getException();}
                        catch (FirebaseAuthInvalidCredentialsException e)
                        {
                            if(e.getClass().equals(FirebaseAuthWeakPasswordException.class)) result.setText(R.string.reg_err_psw);
                            else                                                             result.setText(R.string.reg_err_email);
                        }
                        catch (FirebaseAuthUserCollisionException e) {result.setText(R.string.reg_err_exist);}
                        catch (Exception e) {result.setText(R.string.reg_err_gen);}
                    }

                });
            }
            else result.setText(R.string.reg_err_no_all_data);
        });
    }

    private Utente validate()
    {
        String u = inpUser.getText().toString();
        String p = inpPsw.getText().toString();
        String e = inpEmail.getText().toString();
        String d = inpDate.getText().toString();

        Utente r= !u.isEmpty() && !p.isEmpty() && !e.isEmpty() && !d.isEmpty() ? new Utente(u,u,d,e,p) : null;

        return r;
     }
}