package com.example.esamesoftwaretlc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class Activity_Zc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zc);
        Spinner sp_R = findViewById(R.id.spinner_resistance);
        Spinner sp_G = findViewById(R.id.spinner_conductance);
        Spinner sp_C = findViewById(R.id.spinner_capacity);
        Spinner sp_L = findViewById(R.id.spinner_inductance);
        Spinner sp_omega = findViewById(R.id.spinner_omega);

        sp_R.setSelection(3);
        sp_G.setSelection(3);
        sp_C.setSelection(3);
        sp_L.setSelection(3);
        sp_omega.setSelection(3);
        
    }

    public void calc(View view) {
        EditText et_R = findViewById(R.id.resistance);
        EditText et_G = findViewById(R.id.conductance);
        EditText et_C = findViewById(R.id.capacity);
        EditText et_L = findViewById(R.id.inductance);
        EditText et_omega = findViewById(R.id.omega);

        Spinner sp_R = findViewById(R.id.spinner_resistance);
        Spinner sp_G = findViewById(R.id.spinner_conductance);
        Spinner sp_C = findViewById(R.id.spinner_capacity);
        Spinner sp_L = findViewById(R.id.spinner_inductance);
        Spinner sp_omega = findViewById(R.id.spinner_omega);

        TextView tv_Zc = findViewById(R.id.textview_Zc);
        TextView tv_gamma = findViewById(R.id.textview_Gamma);
        TextView tv_alfa = findViewById(R.id.textview_alfa);
        TextView tv_beta = findViewById(R.id.textview_beta);


        double R = getFromEditText(et_R, sp_R);
        double G = getFromEditText(et_G, sp_G);
        double C = getFromEditText(et_C, sp_C);
        double L = getFromEditText(et_L, sp_L);
        double omega = getFromEditText(et_omega, sp_omega);

        Complex Zc, gamma;
        Complex Z = new Complex(R, omega * L);
        Complex Y = new Complex(G, omega * C);
        Zc = Z.divide(Y);
        Zc = Zc.sqrt();
        gamma = Z.multiply(Y);
        gamma = gamma.sqrt();

        double alfa = (R * R + omega * omega * L * L) * (G * G + omega * omega * C * C);
        alfa = Math.sqrt(alfa);
        double beta = alfa;
        double ab = omega * omega * L * C - R * G;
        alfa = alfa - ab;
        beta = beta + ab;
        alfa = Math.sqrt(alfa / 2);
        beta = Math.sqrt(beta / 2);

        SharedPreferences preferences = getSharedPreferences("com.example.esamesoftwaretlc_preferences", MODE_PRIVATE);
        int n_cifr = Integer.parseInt(preferences.getString("n_dec", "4"));

        tv_Zc.setText(String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg  %%+.%dg j \t[Ohm]", n_cifr, n_cifr), Zc.real(), Zc.imag()));
        tv_gamma.setText(String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg  %%+.%dg j", n_cifr, n_cifr), gamma.real(), gamma.imag()));
        tv_alfa.setText(String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg \t[Neper/m]", n_cifr), alfa));
        tv_beta.setText(String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg \t[rad/m]", n_cifr), beta));
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