package com.example.esamesoftwaretlc;

import static java.lang.Math.abs;
import static java.lang.Math.atan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class Activity_stub extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //crea l'attivity e mostra la mostra a schermo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stub);

        //creazione degli spinner
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

        TextView tv_l = findViewById(R.id.textview_l);
        TextView tv_lstub = findViewById(R.id.textview_lstub);
        Spinner sp_Rc = findViewById(R.id.spinner_Rc);
        Spinner sp_Zl = findViewById(R.id.spinner_ZL);
        Spinner sp_lambda = findViewById(R.id.spinner_lambda);

        double lambda, beta, theta1, theta2, l1, l2, lStub1, lStub2, b, rho_L, u, v;

        //Creazione dei complessi Zc e Zl, utili per calcoli futuri
        Complex Zc = new Complex(getFromEditText(et_Rc, sp_Rc), 0);
        Complex ZL = new Complex(getFromEditText(et_Zl_Re, sp_Zl), getFromEditText(et_Zl_Im, sp_Zl));
        Complex rho;
        //Ottenimento del valore di lambda
        lambda = getFromEditText(et_lambda, sp_lambda);
        //Ottenimento della valore di selezione CC o CA
        RadioButton r_corto = findViewById(R.id.corto);

        // Calcoli:
        beta = 2*Math.PI/lambda;
        rho = ZL.subtract(Zc);
        rho = rho.divide(Zc.add(ZL));
        rho_L = rho.abs();
        u = -Math.pow(rho_L, 2);
        v = -rho_L * Math.sqrt(1 - Math.pow(rho_L, 2));
        theta1 = rho.arg() - Math.atan(v/u) + Math.PI;
        theta2 = rho.arg() - Math.atan(-v/u) + Math.PI;
        b = (2*v)/(Math.pow(u+1, 2) + Math.pow(v, 2));
        //Calcolo di D e L (distanza dal carico e lunghezza dello Stub)
        l1 = (lambda * abs(theta1))/(4 * Math.PI);
        l2 = (lambda * abs(theta2))/(4 * Math.PI);
        if(r_corto.isChecked()) {
            lStub1 = -(1/beta) * atan(1/b);
            lStub2 = -(1/beta) * atan(-1/b);
        }else{
            lStub1 = (1/beta) * atan(b);
            lStub2 = (1/beta) * atan(-b);
        }

        // Questi valori devono essere positivi (le impedenze hanno periodicità spaziale di lambda)
        while (l1 < 0) l1 = l1 + lambda/2;
        while (l2 < 0) l2 = l2 + lambda/2;
        while (lStub1 < 0) lStub1 = lStub1 + lambda/2;
        while (lStub2 < 0) lStub2 = lStub2 + lambda/2;

        //Mostrare a schermo i risultati
        SharedPreferences preferences = getSharedPreferences("com.example.esamesoftwaretlc_preferences", MODE_PRIVATE);
        int n_cifr = Integer.parseInt(preferences.getString("n_dec", "4"));

        tv_l.setText(String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg ; %%.%dg", n_cifr, n_cifr), l1, l2));
        tv_lstub.setText((String.format(Locale.getDefault(), String.format(Locale.getDefault(), "%%.%dg ; %%.%dg", n_cifr, n_cifr), lStub1, lStub2)));
    }

    //Lettura del valore degli spinner relativi
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
