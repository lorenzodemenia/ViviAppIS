package com.example.viviappis.control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.viviappis.R;
import com.example.viviappis.control.event.NewEventActivity;
import com.example.viviappis.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Questa classe serve a gestire activity che gestisce le azioni dopo il login
 * @author Jacopo Cini
 * @version 1.0
 */
public class AfterLogin extends AppCompatActivity
{
    private ActivityMainBinding binding;
    private Button bHomeNewEvent;
    private SearchView srcDash;

    /**
     * Questa funzione permette di creare activity che gestisce le azioni dopo il login
     * @param savedInstanceState Rappresenta lo stato che ce alla creazione dell'attività
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        setUpUIViews();
        addActionListener();
    }

    /**
     * Questa funzione serve a inizzializzare le variabili dell'activity utili per recuperare i valori di input e mostrare i valori di output
     */
    private void setUpUIViews()
    {
        bHomeNewEvent = (Button) findViewById(R.id.homeNewEvent);
        srcDash = (SearchView) findViewById(R.id.dashSrcEvent);


        System.out.println(srcDash);
    }


    /**
     * Questa funzione va ad inserire i Listener ai vari componenti nella pagina
     */
    private void addActionListener()
    {
        bHomeNewEvent.setOnClickListener((i)->{startActivity(new Intent(this, NewEventActivity.class));});
    }
}