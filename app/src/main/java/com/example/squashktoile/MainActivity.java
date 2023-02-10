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
                float msRecovery;
                float howManyMS;
                float howManyPeople;

                boolean validation = true;

                courtPrize =giveMeFloat(String.valueOf(editTextNumberCourtPrize.getText()));
                msRecovery =giveMeFloat(String.valueOf(editTextNumberMSrecovery.getText()));
                howManyMS = giveMeFloat(String.valueOf(editTextNumberHowManyMS.getText()));
                howManyPeople = giveMeFloat(String.valueOf(editTextNumberHowManyPeople.getText()));
                int howManyCourts = (int) Math.floor(howManyPeople/2);
                System.out.println("howManyCourts " + howManyCourts);



                float totalPrize = courtPrize * howManyCourts;
                float excessPrize = totalPrize - msRecovery * howManyMS;
                float howManyPeopleWithoutMS = Math.max(0, howManyPeople - howManyMS);
                if (howManyPeopleWithoutMS < 0) {
                    System.out.println("Mniej osób niż osób :D");
                    validation = false;

                }

                float excessPrizeForEveryone = (excessPrize - (howManyPeopleWithoutMS * msRecovery)) / howManyPeople;
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                String ForPeopleWithMS = df.format(excessPrizeForEveryone);
                float excessPrizeForPeopleWoMS = excessPrizeForEveryone + msRecovery;
                String ForPeopleWoMS = df.format(excessPrizeForPeopleWoMS);



                if (courtPrize <= 0) {
                    System.out.println("za mało za kort");
                    validation = false;
                }
                if (msRecovery <= 0) {
                    System.out.println("za mało dopłaty z MultiSporta");
                    validation = false;
                }
                if (howManyPeople <= 1) {
                    System.out.println("za mało osób");
                    validation = false;
                }
                if (howManyPeople - howManyMS < 0) {
                    System.out.println("Więcej kart MS niż osób");
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

            float giveMeFloat(String strNumber) {
                if (strNumber != null && strNumber.length() > 0) {
                    try {
                        return Float.parseFloat(strNumber);
                    } catch(Exception e) {
                        System.out.println("Ooooo nieeee" + e);
                        return 0;
                    }
                }
                else {
                    return 0;}
            }


        });

    }
}