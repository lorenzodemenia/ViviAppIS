package com.example.viviappis;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viviappis.control.AfterLogin;
import com.example.viviappis.control.loginAndRegister.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Questa classe serve a gestire activity principale
 * @author Jacopo Cini
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity
{
    protected FirebaseAuth au;

    /**
     * Questa funzione permette di creare actactivity principale
     * @param savedInstanceState Rappresenta lo stato che ce alla creazione dell'attività
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        au = FirebaseAuth.getInstance();

        if(au.getCurrentUser()==null || au.getCurrentUser().isAnonymous()) startActivity(new Intent(this, LoginActivity.class));
        else                                                               startActivity(new Intent(this, AfterLogin.class));
    }
}