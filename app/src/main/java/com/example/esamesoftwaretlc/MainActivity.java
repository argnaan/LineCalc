package com.example.esamesoftwaretlc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String lingua = Locale.getDefault().getLanguage();

    // Metodo onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setta il tema dell'applicazione
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("theme", "light");
        if (theme.equals("light"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if(theme.equals("dark"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        // Mostra il layout dell'attività
        setContentView(R.layout.activity_main);
    }

    // Metodo per aprire l'attività per il la conversione da forma algebrica a forma polare di un numero complesso
    public void polarToAlgebra(View view){
        Intent intent = new Intent(this, Activity_Linear.class);
        startActivity(intent);
    }

    // Metodo per aprire l'attività per il calcolo dei parametri di trasmissione
    public void calcSecParameter(View view){
        Intent intent = new Intent(this, Activity_Zc.class);
        startActivity(intent);
    }

    // Metodo per aprire l'attività per il calcolo del disadattamento
    public void calcDisad(View view){
        Intent intent = new Intent(this, Activity_disadattamento.class);
        startActivity(intent);
    }

    // Metodo per aprire l'attività per il calcolo del dimensionamento di uno stub
    public void calcStub(View view){
        Intent intent = new Intent(this, Activity_stub.class);
        startActivity(intent);
    }

    // Metodo per aprire l'attività per il calcolo del dimensionamento di un adattaore a lambda quarti
    public void calcLambdaQuarti(View view) {
        Intent intent = new Intent(this, Activity_lambda_quarti.class);
        startActivity(intent);
    }

    // Metodo per aprire l'attività delle impostazioni
    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //Metodo onResume
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String lingua_new = preferences.getString("language", "default");
        if(!lingua_new.equals(lingua)){
            lingua = lingua_new;
            Locale locale = new Locale(lingua);
            Locale.setDefault(locale);
            Resources resources = getResources();
            Configuration config = new Configuration();
            config.setLocale(locale);
            resources.updateConfiguration(config, resources.getDisplayMetrics());
            recreate();
        }

    }
}