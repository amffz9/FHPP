package com.example.adam.fhpp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hose_array, android.R.layout.simple_spinner_dropdown_item);// an adapter for the spinner
        Spinner spinner = (Spinner)findViewById(R.id.spinner);//get the spinner itself
        spinner.setAdapter(adapter);//tell the spinner which adapter to use
        AdapterView.OnItemSelectedListener itemselected = new AdapterView.OnItemSelectedListener() {//what to do with spinner
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("item selected",Integer.toString(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemselected);
    }
    
}
