package com.example.esamesoftwaretlc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    String lingua = Locale.getDefault().getLanguage();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("theme", "light");
        if (theme.equals("light"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if(theme.equals("dark"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.link_github);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void calcSecParameter(View view){
        Intent intent = new Intent(this, Activity_Zc.class);
        startActivity(intent);
    }

    public void polarToAlgebra(View view){
        Intent intent = new Intent(this, Activity_Linear.class);
        startActivity(intent);
    }

    public void calcDisad(View view){
        Intent intent = new Intent(this, Activity_disadattamento.class);
        startActivity(intent);
    }

    public void calcStub(View view){
        Intent intent = new Intent(this, Activity_stub.class);
        startActivity(intent);
    }

    public void calcLambdaQuarti(View view) {
        Intent intent = new Intent(this, Activity_lambda_quarti.class);
        startActivity(intent);
    }
    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

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