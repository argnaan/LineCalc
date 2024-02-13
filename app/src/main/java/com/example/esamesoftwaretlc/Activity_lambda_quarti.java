package com.example.esamesoftwaretlc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Locale;

public class Activity_lambda_quarti extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lambda_quarti);

        Spinner sp_Rc = findViewById(R.id.spinner_Rc);
        Spinner sp_Zl = findViewById(R.id.spinner_ZL);
        Spinner sp_lambda = findViewById(R.id.spinner_lambda);
        sp_Rc.setSelection(3);
        sp_Zl.setSelection(3);
        sp_lambda.setSelection(3);
    }

    public void calcola(View view){
        //Lettura dei valori di Rc, Zl e Lambda
        EditText et_Rc = findViewById(R.id.Rc);
        EditText et_Zl_Re = findViewById(R.id.ZL_Re);
        EditText et_Zl_Im = findViewById(R.id.ZL_Im);
        EditText et_lambda = findViewById(R.id.lambda);

        //Creazione dei complessi Zc e Zl, utili per calcoli futuri
        TextView tv_Rca = findViewById(R.id.textview_Rca);
        TextView tv_L = findViewById(R.id.textview_L);

        //Ottenimento del valore degli spinner
        Spinner sp_Rc = findViewById(R.id.spinner_Rc);
        Spinner sp_Zl = findViewById(R.id.spinner_ZL);
        Spinner sp_lambda = findViewById(R.id.spinner_lambda);

        //Creazione delle variabili utili per i calcoli
        double ROS, Rc, Ra, L, lambda;
        Complex rho_L;
        Rc = getFromEditText(et_Rc, sp_Rc);
        lambda = getFromEditText(et_lambda, sp_lambda);
        Complex Zc = new Complex(Rc, 0);
        Complex ZL = new Complex(getFromEditText(et_Zl_Re, sp_Zl), getFromEditText(et_Zl_Im, sp_Zl));

        //Calcoli
        rho_L = ZL.subtract(Zc);
        rho_L = rho_L.divide(Zc.add(ZL));
        ROS = (1 + rho_L.abs())/(1 - rho_L.abs());
        RadioButton r_min = findViewById(R.id.radioMin);
        if(r_min.isChecked()) {
            Ra = Rc / Math.sqrt(ROS);
            L = (lambda/4)*(1 - rho_L.arg()/Math.PI);
        }else{
            Ra = Rc * Math.sqrt(ROS);
            L = (-rho_L.arg()/(4*Math.PI))*lambda;
        }

        //Mostra i risultati a schermo
        SharedPreferences preferences = getSharedPreferences("com.example.esamesoftwaretlc_preferences", MODE_PRIVATE);
        int n_cifre = Integer.parseInt(preferences.getString("n_dec", "4"));
        tv_Rca.setText(String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg", n_cifre), Ra));
        tv_L.setText((String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg", n_cifre), L)));
    }

    //Metodo per ottenere il valore da un EditText e dallo Spinner
    private double getFromEditText(EditText et, Spinner sp){
        if(et.getText().toString().equals("")) {
            et.setText("0");
            return 0;
        } else{
            double val = Float.parseFloat(et.getText().toString());
            val *= Math.pow(10, (sp.getSelectedItemPosition()-3)*3);
            return val;
        }
    }
}