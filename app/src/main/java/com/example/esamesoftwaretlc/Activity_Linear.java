package com.example.esamesoftwaretlc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Locale;


public class Activity_Linear extends AppCompatActivity {

    @Override
    //Crea la activity e la mostra a schermo
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);
    }

    /*
    Calcola il numero complesso da polare a lineare
    Prende i valori di ampiezza e fase e li trasforma in reali e immaginari
    Mostra i risultati a schermo
    Si sarebbe potuto fare usando la classe Complex, ma ho preferito fare i calcoli a mano
    */
    public void calcPolToLin(View view) {
        //Lettura dei valori di ampiezza e fase
        EditText etAmplitude = (EditText) findViewById(R.id.amplitude);
        EditText etPhase = (EditText) findViewById(R.id.phase);
        if(etAmplitude.getText().toString().isEmpty()){
            etAmplitude.setText("0");
        }
        if(etPhase.getText().toString().isEmpty()){
            etPhase.setText("0");
        }

        //Calcolo dei valori reali e immaginari
        double fAmplitude = Float.parseFloat(etAmplitude.getText().toString());
        double fPhase = Float.parseFloat(etPhase.getText().toString());
        double fRealValue = fAmplitude * Math.cos(fPhase);
        double fImaginalValue = fAmplitude * Math.sin(fPhase);

        //Mostra i risultati a schermo
        TextView twRealValue = (TextView) findViewById(R.id.realValue);
        TextView twImaginalValue = (TextView) findViewById(R.id.imaginaryValue);
        SharedPreferences preferences = getSharedPreferences("com.example.esamesoftwaretlc_preferences", MODE_PRIVATE);
        int n_cifr = Integer.parseInt(preferences.getString("n_dec", "4"));
        // forza ad usare il formato decimale con il punto al posto della virgola, alrimenti non funziona
        twRealValue.setText(String.format(Locale.US, String.format(Locale.getDefault(), "%%.%dg", n_cifr), fRealValue));
        twImaginalValue.setText(String.format(Locale.US,String.format(Locale.getDefault(), "%%.%dg", n_cifr), fImaginalValue));
    }

    /*
    Calcola il numero complesso da lineare a polare
    Prende i valori di reali e immaginari e li trasforma in ampiezza e fase
    Mostra i risultati a schermo
    Si sarebbe potuto fare usando la classe Complex, ma ho preferito fare i calcoli a mano
    */
    public void calcLinToPol(View view){
        //Lettura dei valori di reali e immaginari
        EditText etRealValue = (EditText) findViewById(R.id.realValue);
        EditText etImaginalValue = (EditText) findViewById(R.id.imaginaryValue);
        if(etRealValue.getText().toString().isEmpty()){
            etRealValue.setText("0");
        }
        if(etImaginalValue.getText().toString().isEmpty()){
            etImaginalValue.setText("0");
        }

        //Calcolo dei valori di ampiezza e fase
        double fRealValue = Float.parseFloat(etRealValue.getText().toString());
        double fImaginalValue = Float.parseFloat(etImaginalValue.getText().toString());
        Complex c = new Complex(fRealValue, fImaginalValue);
        double fAmplitude = c.abs();
        double fPhase = c.arg();

        //Mostra i risultati a schermo
        TextView twAmplitude = (TextView) findViewById(R.id.amplitude);
        TextView twPhase = (TextView) findViewById(R.id.phase);
        SharedPreferences preferences = getSharedPreferences("com.example.esamesoftwaretlc_preferences", MODE_PRIVATE);
        int n_cifr = Integer.parseInt(preferences.getString("n_dec", "4"));

        // forza ad usare il formato decimale con il punto al posto della virgola, alrimenti non funziona
        twAmplitude.setText(String.format(Locale.US, String.format(Locale.getDefault(), "%%.%dg", n_cifr), fAmplitude));
        twPhase.setText(String.format(Locale.US, String.format(Locale.getDefault(), "%%.%dg", n_cifr), fPhase));
    }
}