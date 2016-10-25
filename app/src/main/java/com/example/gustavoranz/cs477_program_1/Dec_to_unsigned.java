package com.example.gustavoranz.cs477_program_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class Dec_to_unsigned extends AppCompatActivity {

    Spinner left_bits, middle_bits, right_bits;
    TextView question_TextView, results;
    EditText unsigned_answer;
    String num_bits, hex_bit1 ="0", hex_bit2="0";
    int num_of_clicks, quiz_num;
    boolean unsigned = false, too_small = false, too_big = false, hex_answer = false, quiz_num_too_small = false,
            quiz_num_too_big = false, quiz_num_hex = false;
    RadioGroup rg;
    Button check_answers_button, calculator_button;
    GridLayout myLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dec_to_unsigned);

        middle_bits = (Spinner) findViewById(R.id.middle_bits_spinner);
        right_bits = (Spinner) findViewById(R.id.right_bits_spinner);
        question_TextView = (TextView) findViewById(R.id.question_textView);
        results = (TextView) findViewById(R.id.results_textView);
        myLayout = (GridLayout) findViewById(R.id.grid_layout_unsigned);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> hex_value_adapter = ArrayAdapter.createFromResource(this,
                R.array.hex_values_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        hex_value_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        middle_bits.setAdapter(hex_value_adapter);
        right_bits.setAdapter(hex_value_adapter);

        Random rn = new Random();
        int quiz_num = rn.nextInt((255-9)+1)-9;


        if(quiz_num < 0)
            quiz_num_too_small = true;
        else if(quiz_num > 255)
            quiz_num_too_big = true;
        else
            quiz_num_hex = true;

        question_TextView.setText("In unsigned, what is the 8-bit hex value for " + quiz_num + "?");

        //creating the RadioGroup object so that I can then created a listener for the specific radioButton group
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        //RadioGroup event listener to see which of the radio buttons is checked
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //Actual function which checks which radioButton is checked
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                System.out.println("Nothing much man");
                switch (checkedId) {
                    case R.id.too_small_radioButton:
                        too_small = true;
                        break;

                    case R.id.too_large_radioButton:
                        too_big = true;
                        break;

                    case R.id.answer_radioButton:
                        hex_answer = true;
                        break;
                }
            }
        });

        if(quiz_num > 0 & quiz_num < 255) {
            //getting binary representation of random number into a string variable
            String binary = Integer.toBinaryString(quiz_num);

            //getting the bits from the string
            String binary_bit1 = binary.substring(0, (binary.length() - 4));
            String binary_bit2 = binary.substring(binary.length() - 4, binary.length());

            hex_bit1 = Integer.toHexString(Integer.parseInt(binary_bit1, 2));
            hex_bit2 = Integer.toHexString(Integer.parseInt(binary_bit2, 2));

        }

        check_answers_button = (Button) findViewById(R.id.check_answer_button);
        calculator_button = (Button) findViewById(R.id.calculator_button);

        //Event Listener for the withResult button
        check_answers_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                num_of_clicks++;//use to launch different intent on second click

                if(too_small & quiz_num_too_small)
                    unsigned = true;
                else if(too_big & quiz_num_too_big)
                    unsigned = true;
                else if((middle_bits.getSelectedItem().toString().equalsIgnoreCase(hex_bit1)) & (right_bits.getSelectedItem().toString().equalsIgnoreCase(hex_bit2)))
                    unsigned = true;

                if(num_of_clicks == 2){
                    Intent back_to_main = new Intent();//(Hex_to_Dec.this, MainActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("unsigned_answer", String.valueOf(unsigned));//attaching the boolean value of unsigned
                    back_to_main.putExtras(extras);//attaching the bundle to the intent
                    setResult(Activity.RESULT_OK,back_to_main);
                    finish();
                }else {
                    if (unsigned) {
                        myLayout.setBackgroundColor(Color.GREEN);
                        check_answers_button.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                myLayout.setBackgroundColor(Color.WHITE);
                            }
                        }, 2000);
                        Toast.makeText(Dec_to_unsigned.this, "Good job, you answered correctly!", Toast.LENGTH_SHORT).show();
                    } else {
                        myLayout.setBackgroundColor(Color.RED);
                        check_answers_button.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                myLayout.setBackgroundColor(Color.WHITE);
                            }
                        }, 2000);
                        if(quiz_num_too_small)
                            results.setText("Looks like you made a mistake. The number was too small to be represented.");
                        else if(quiz_num_too_big)
                            results.setText("Looks like you made a mistake. The number was too large to be represented.");
                        else
                            results.setText("Looks like you made some mistakes. The answer is: 0x" + hex_bit1 + hex_bit2);
                    }
                    //changing text on button
                    check_answers_button.setText("Another Question?");
                }
            }
        });

        //Event Listener for the Convert Implicitly
        calculator_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //This is used to start lab1 by clicking this button
                //I added an intent filter to the AndroidManifest of that application
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setType("text/plain");
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);

            }

        });
    }


}
