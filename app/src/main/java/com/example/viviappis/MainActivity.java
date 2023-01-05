package com.example.viviappis;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.viviappis.control.AfterLogin;
import com.example.viviappis.control.loginAndRegister.LoginActivity;
import com.example.viviappis.control.loginAndRegister.RegisterActivity;
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
        else
        {

            /*eliminabile --------------------------------*/
           // au.signOut();
            /*eliminabile --------------------------------*/

            startActivity(new Intent(this, AfterLogin.class));
        }
    }
}



  /*FirebaseFirestore db = FirebaseFirestore.getInstance();

        Utente userObj = new Utente("Pietro", "Smusi", "12/04/1874", "gattinoarruffato@gmil.com", "cacca123");
        Evento eventObj = new Evento("sesso", "gay", userObj, "30/11/2022", "Lorenzocula", false);

        Map<String, Object> event = eventMap(eventObj);
        Map<String, Object> user = userMap(userObj);

        db.collection("users").document(generateRandomId()).set(user);
        db.collection("events").document(generateRandomId()).set(event);*/