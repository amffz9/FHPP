package com.example.adam.fhpp;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
    /*
    FL = Friction Loss
    C = Coefficient of Friction
    Q = Quantity of Water(hundreds of gallons)
    L = Length of Hose hundreds of feet
    FL = C * (Q^2) * L

    PDP = Pump Discharge Pressure
    NP = Nozzle Pressure
    Fog Nozzle - 100psi
    Smoothbore Nozzle - 50 psi

    PDP = FL + NP (+/-)Elevation
    Elevation = .434 * elevation height ft

     */
    private Spinner spinner;
    private EditText gpm;
    private EditText loh;
    private TextView output;
    private double coefficients[] = {1100, 150, 80, 24, 15.5, 8, 2, 0.8, 0.677, 0.34, 0.2, 0.1, 0.08,0.05};
    private double coefficient;
    private double frictionLoss;
    private double pumpDischargePressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner);//get the spinner
        gpm = (EditText)findViewById(R.id.editText);//gallons per minute
        loh = (EditText)findViewById(R.id.editText2);//length of hose
        output = (TextView)findViewById(R.id.textView);//put answer in here
        coefficient = coefficients[0];

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hose_array, android.R.layout.simple_spinner_dropdown_item);// an adapter for the spinner

        spinner.setAdapter(adapter);//tell the spinner which adapter to use
        AdapterView.OnItemSelectedListener itemselected = new AdapterView.OnItemSelectedListener() {//what to do with spinner
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("item selected",Integer.toString(position));
                coefficient = coefficients[position];
                frictionLoss = frictionLoss();
                pumpDischargePressure = pumpDischargePressure(frictionLoss());
                update(frictionLoss,pumpDischargePressure);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        TextWatcher doMath = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                frictionLoss = frictionLoss();
                pumpDischargePressure = pumpDischargePressure(frictionLoss);

                update(frictionLoss,pumpDischargePressure);
            }
        };
        gpm.addTextChangedListener(doMath);
        loh.addTextChangedListener(doMath);
        spinner.setOnItemSelectedListener(itemselected);
    }
    void update(double frictionLoss, double pumpDischargePressure)
    {
        output.setText("Friction Loss: " + String.valueOf(frictionLoss) + "\n"
                + "Pump Discharge Pressure: " + String.valueOf(pumpDischargePressure));
    }
    double frictionLoss()
    {
        double Q;
        double L;
        if (gpm.getText().toString().isEmpty())
        {
            Q = 0;
            Log.e("gpm","empty");
        }
        else
        {
            Q = (Double.parseDouble(gpm.getText().toString())/100);
        }
        if(loh.getText().toString().isEmpty())
        {
            L = 0;
        }
        else
        {
            L = (Double.parseDouble(loh.getText().toString())/100);
            //Log.e("L",Double.toString(L));
        }


        frictionLoss =  coefficient * (Q*Q) * L;

        return frictionLoss;
    }
    double pumpDischargePressure(double frictionLoss)
    {
        /*
        PDP = Pump Discharge Pressure
        NP = Nozzle Pressure
        Fog Nozzle - 100psi
        Smoothbore Nozzle - 50 psi

            PDP = FL + NP (+/-)Elevation
            Elevation = .434 * elevation height ft

        */
        double pressure;
        double nozzlePressure = 100;
        pressure = frictionLoss + nozzlePressure + 0;//assumed 0 elevation for now

        return pressure;
    }

}
