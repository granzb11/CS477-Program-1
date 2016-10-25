package com.example.gustavoranz.cs477_program_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private Button go_button;
    private TextView score_TextView;
    private Bundle newBundle = new Bundle();
    int total_questions=0, answered_correct=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score_TextView = (TextView) findViewById(R.id.score_TextView);
        score_TextView.setText("Score: " + answered_correct + "/" + total_questions);

        //creating and linking spinners
        final Spinner question_spinner = (Spinner) findViewById(R.id.question_spinner);
        final Spinner bits_spinner = (Spinner) findViewById(R.id.bits_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> question_type_adapter = ArrayAdapter.createFromResource(this,
                R.array.question_type_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> bit_size_adapter = ArrayAdapter.createFromResource(this,
                R.array.bit_size_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        question_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bit_size_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        question_spinner.setAdapter(question_type_adapter);
        bits_spinner.setAdapter(bit_size_adapter);

        //Linking buttons from layout to button objects created in this file
        go_button = (Button) findViewById(R.id.go_button);

        //Event Listener for the withResult button
        go_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //if they pick the hex to decimal question

                //getting the strings picked from the spinners
                final String question_spinner_text = question_spinner.getSelectedItem().toString();
                final String bit_spinner_text = bits_spinner.getSelectedItem().toString();

                System.out.println(question_spinner_text);

                if (question_spinner_text.equalsIgnoreCase("Hex To Decimal"))
                {
                    Intent hexTodecimal = new Intent(MainActivity.this, Hex_to_Dec.class);
                    Bundle extras = new Bundle();
                    extras.putString("bits", bit_spinner_text);//attaching the bit string to the bundle
                    hexTodecimal.putExtras(extras);//attaching the bundle to the intent
                    startActivityForResult(hexTodecimal, 1);//starting the new activity with result
                }else
                if (question_spinner_text.equalsIgnoreCase("Decimal to Unsigned Hex"))
                {
                    Intent decimalToUnsigned = new Intent(MainActivity.this, Dec_to_unsigned.class);
                    Bundle extras = new Bundle();
                    extras.putString("bits", bit_spinner_text);//attaching the bit string to the bundle
                    decimalToUnsigned.putExtras(extras);//attaching the bundle to the intent
                    startActivityForResult(decimalToUnsigned, 2);//starting the new activity with result
                }else
                if (question_spinner_text.equalsIgnoreCase("Decimal to Signed Hex"))
                {
                    Intent decimalToUnsigned = new Intent(MainActivity.this, Dec_to_signed.class);
                    Bundle extras = new Bundle();
                    extras.putString("bits", bit_spinner_text);//attaching the bit string to the bundle
                    decimalToUnsigned.putExtras(extras);//attaching the bundle to the intent
                    startActivityForResult(decimalToUnsigned, 3);//starting the new activity with result
                }

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                if(data.getStringExtra("signed_answer").equalsIgnoreCase("true"))
                    answered_correct++;
                if(data.getStringExtra("unsigned_answer").equalsIgnoreCase("true"))
                    answered_correct++;
                total_questions += 2;
            }
        }
        if(requestCode == 2){
            if(resultCode == RESULT_OK)
                if (data.getStringExtra("unsigned_answer").equalsIgnoreCase("true"))
                    answered_correct++;
                total_questions++;
            }

        if(requestCode == 3){
            if(resultCode == RESULT_OK)
                if (data.getStringExtra("signed_answer").equalsIgnoreCase("true"))
                    answered_correct++;
            total_questions++;
        }
        score_TextView.setText("Score: " + answered_correct + "/" + total_questions);
    }

}
