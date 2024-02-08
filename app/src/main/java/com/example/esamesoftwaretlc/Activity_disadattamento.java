package com.example.esamesoftwaretlc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Locale;

public class Activity_disadattamento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disadattamento);

        Spinner sp_Zc = findViewById(R.id.spinner_Zc);
        Spinner sp_ZL = findViewById(R.id.spinner_ZL);
        sp_Zc.setSelection(3);
        sp_ZL.setSelection(3);
    }

    public void calc(View view){
        EditText et_Zc_Re = findViewById(R.id.Zc_Re);
        EditText et_Zc_Im = findViewById(R.id.Zc_Im);
        EditText et_ZL_Re = findViewById(R.id.ZL_Re);
        EditText et_ZL_Im = findViewById(R.id.ZL_Im);

        TextView tv_rho_L = findViewById(R.id.textview_rho_L);
        TextView tv_ROS = findViewById(R.id.textview_ROS);
        TextView tv_RL = findViewById(R.id.textview_RL);

        Spinner sp_Zc = findViewById(R.id.spinner_Zc);
        Spinner sp_ZL = findViewById(R.id.spinner_ZL);

        Complex rho_L;
        double ROS, RL;
        Complex Zc = new Complex(getFromEditText(et_Zc_Re, sp_Zc), getFromEditText(et_Zc_Im, sp_Zc));
        Complex ZL = new Complex(getFromEditText(et_ZL_Re, sp_ZL), getFromEditText(et_ZL_Im, sp_ZL));

        rho_L = ZL.subtract(Zc);
        rho_L = rho_L.divide(Zc.add(ZL));

        ROS = (1 + rho_L.abs())/(1 - rho_L.abs());
        RL = -20 * Math.log10(rho_L.abs());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int n_cifr = Integer.parseInt(preferences.getString("n_dec", "4"));

        tv_rho_L.setText(String.format(Locale.getDefault(), String.format(Locale.getDefault(),"%%.%dg ∠ %%.%dg", n_cifr, n_cifr), rho_L.abs(), rho_L.arg()));
        tv_ROS.setText(String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg", n_cifr), ROS));
        tv_RL.setText(String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg", n_cifr), RL));
    }

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