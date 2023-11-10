package com.example.calculatorapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView displayNums,historyDisplay;
    Button[] numbuttons = new Button[10];
    Button plusButton, minusButton, divButton, multButton, clButton, equalButton,historyButton;
    CalBrain calBrain = new CalBrain(); // Create an instance of CalBrain
//    String allValues;
    boolean newNumber, histFlag;
    ArrayList<String> historyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayNums = findViewById(R.id.textView);
        historyDisplay=findViewById(R.id.textViewHist);

        // Initialize numbuttons array
        for (int i = 0; i < 10; i++) {
            //create a string to get the resource ID of the button with that name
            int buttonId = getResources().getIdentifier("b" + i, "id", getPackageName());
            // finds the Button view associated with the resource ID and assigns it to the numbuttons array at the index
            numbuttons[i] = findViewById(buttonId);
            numbuttons[i].setOnClickListener(this); // Set the click listener for each button
        }

        divButton = findViewById(R.id.bdivide);
        plusButton = findViewById(R.id.bplus);
        minusButton = findViewById(R.id.bminus);
        multButton = findViewById(R.id.btimes);
        equalButton = findViewById(R.id.bequal);
        clButton = findViewById(R.id.bclear);
        historyButton=findViewById(R.id.histButton);

        divButton.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        multButton.setOnClickListener(this);
        equalButton.setOnClickListener(this);
        clButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);

        historyView = ((MyApp)getApplication()).historyView;

        updateHistoryDisplay();

    }
    @Override
    public void onClick(View v) {

        if (newNumber) {
            newNumber=false;
            displayNums.setText("");
        }
        switch (v.getId()) {
            case R.id.bminus:
                displayNums.append("-");
                break;
            case R.id.bplus:
                displayNums.append("+");
                break;
            case R.id.bdivide:
                displayNums.append("/");
                break;
            case R.id.btimes:
                displayNums.append("*");
                break;
            case R.id.bequal:
                String allValues = displayNums.getText().toString();
                calBrain.push(allValues);

                int result = calBrain.calculate();

                if(calBrain.errorFlag) {
                    Toast.makeText(this, "Incorrect Format", Toast.LENGTH_SHORT).show();
                    displayNums.append(" Wrong Format");
                    calBrain.errorFlag = false;
                    //something went wrong so toast
            }else {
                    displayNums.append("=");
                    displayNums.append(String.valueOf(result));
                    if (histFlag){
                        historyView.add(displayNums.getText().toString());
                        updateHistoryDisplay();
                        // Clear the historyDisplay and append all values from historyView
                    }
                }
                newNumber = true; //new operation starts
                break;

            case R.id.bclear:
                displayNums.setText("");
                break;
            case R.id.histButton:
                if (histFlag) {
                    // If history is currently visible, hide it
                    historyDisplay.setVisibility(View.INVISIBLE);
                    historyButton.setText("ADVANCED - WITH HISTORY");
                    historyButton.setBackgroundColor(ContextCompat.getColor(this,R.color.MediumSlateBlue));
                    histFlag = false;
                } else {
                    // If history is currently hidden, show it
                    historyDisplay.setVisibility(View.VISIBLE);
                    historyButton.setText("STANDARD - NO HISTORY");
                    historyButton.setBackgroundColor(ContextCompat.getColor(this,R.color.Gray));
                    histFlag = true;
                }
                break;
            default:
                for (int i = 0; i < numbuttons.length; i++) {
                        if (v.getId() == numbuttons[i].getId()) {
                            displayNums.append(String.valueOf(i));
                            break; // Exit the loop since we found a match
                    }
                }
        }

    }
    private void updateHistoryDisplay() {
        // Clear the historyDisplay and append all values from historyView
        historyDisplay.setText("");
        for (String historyValue : historyView) {
            historyDisplay.append(historyValue + "\n");
        }
    }
}