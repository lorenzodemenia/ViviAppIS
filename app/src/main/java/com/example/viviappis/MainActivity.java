package com.example.viviappis;

import static com.example.viviappis.data.model.Evento.eventMap;
import static com.example.viviappis.data.model.Utente.userMap;
import static com.example.viviappis.data.model.Utilities.generateRandomId;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.viviappis.data.model.Evento;
import com.example.viviappis.data.model.Utente;
import com.example.viviappis.ui.loginAndRegister.LoginActivity;
import com.example.viviappis.ui.loginAndRegister.RegisterActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth au;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        au = FirebaseAuth.getInstance();

        if(au.getCurrentUser()==null || au.getCurrentUser().isAnonymous()) startActivity(new Intent(this, LoginActivity.class));
        else
        {
            /*eliminabile --------------------------------*/
            au.signOut();
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