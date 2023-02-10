package com.example.squashktoile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextNumberCourtPrize = findViewById(R.id.editTextNumberCourtPrize);
        EditText editTextNumberMSrecovery = findViewById(R.id.editTextNumberMSrecovery);
        EditText editTextNumberHowManyMS = findViewById(R.id.editTextNumberHowManyMS);
        EditText editTextNumberHowManyPeople = findViewById(R.id.editTextNumberHowManyPeople);

        Button button = findViewById(R.id.button);

        TextView textViewWithMS = findViewById(R.id.textViewWithMS);
        TextView textViewWithoutMS = findViewById(R.id.textViewWithoutMS);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                float courtPrize;
                float msRecover;
                float howManyMS;
                float howManyPeople;

                boolean validation = true;

                courtPrize = giveMeInteger(String.valueOf(editTextNumberCourtPrize.getText()));
                msRecover = giveMeInteger(String.valueOf(editTextNumberMSrecovery.getText()));
                howManyMS = giveMeInteger(String.valueOf(editTextNumberHowManyMS.getText()));
                howManyPeople = giveMeInteger(String.valueOf(editTextNumberHowManyPeople.getText()));


                int howManyCourts = (int) Math.floor(howManyPeople/2);


                float totalPrize = courtPrize * howManyCourts;
                float excessPrize = totalPrize - msRecover * howManyMS;
                float howManyPeopleWithoutMS = (float) Math.max(0, howManyPeople - howManyMS);
                System.out.println("koko howManyPeopleWithoutMS: " + howManyPeopleWithoutMS);
                if (howManyPeopleWithoutMS < 0) {
                    System.out.println("People without MS less than 0");
                    validation = false;
                }


                float excessPrizeForPeopleWoMS;
                float excessPrizeForEveryone = (excessPrize - (howManyPeopleWithoutMS * msRecover)) / howManyPeople;
                if (excessPrizeForEveryone > 0 ) {

                    excessPrizeForPeopleWoMS = excessPrizeForEveryone + msRecover;
                } else {
                    excessPrizeForEveryone = 0;
                    excessPrizeForPeopleWoMS = excessPrize / howManyPeopleWithoutMS;
                }

                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                String ForPeopleWithMS = df.format(excessPrizeForEveryone);
                String ForPeopleWoMS = df.format(excessPrizeForPeopleWoMS);

                if (courtPrize <= 0) {
                    System.out.println("Court prize - too low");
                    validation = false;
                }
                if (msRecover <= 0) {
                    System.out.println("MultiSport recover - too low");
                    validation = false;
                }
                if (howManyPeople <= 1.0) {
                    System.out.println("Amount of people - too low");
                    validation = false;
                }
                if (howManyPeople - howManyMS < 0) {
                    System.out.println("More MS cards than people");
                    validation = false;
                }


                if (validation && howManyMS > 0) {
                    textViewWithMS.setText("Dopłata dla osób z MS: " + ForPeopleWithMS);
                } else {
                    textViewWithMS.setText("");
                }
                if (validation && howManyPeopleWithoutMS > 0) {
                    textViewWithoutMS.setText("Dopłata dla osób bez MS: " + ForPeopleWoMS);
                } else {
                    textViewWithoutMS.setText("");
                }
            }

            float giveMeInteger(String strNumber) {
                if (strNumber != null && strNumber.length() > 0) {
                    try {
                        return Integer.parseInt(strNumber);
                    } catch(Exception e) {
                        System.out.println("Oh no! Exception! " + e);
                        return 0;
                    }
                }
                else {
                    return 0;}
            }

        });

    }
}