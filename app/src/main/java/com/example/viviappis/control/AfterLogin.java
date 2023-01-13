package com.example.viviappis.control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.Button;

import com.example.viviappis.R;
import com.example.viviappis.databinding.ActivityMainBinding;
import com.example.viviappis.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Questa classe serve a gestire activity che gestisce le azioni dopo il login
 * @author Jacopo Cini
 * @version 1.0
 */
public class AfterLogin extends AppCompatActivity
{
    private ActivityMainBinding binding;
    private BottomNavigationView navView;

    /**
     * Questa funzione permette di creare activity che gestisce le azioni dopo il login
     * @param savedInstanceState Rappresenta lo stato che ce alla creazione dell'attività
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    /**
     * serve ad evitare che utente passa tornare indientro a un fragment inesistente
     */
    @Override
    public void onBackPressed() {}
}