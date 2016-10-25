package com.example.gustavoranz.cs477_program_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Hex_to_Dec extends AppCompatActivity {

    String decimal_value;
    String hex_bit1, hex_bit2;
    boolean signed = false, unsigned = false;
    private  Button check_answers_button, calculator_button;
    EditText signed_answer, unsigned_answer;
    TextView results;
    int quiz_num, num_of_clicks=0;
    RelativeLayout myLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hex_to_dec);

        //creating and linking objects
        TextView question_TextView = (TextView) findViewById(R.id.question_textView);
        signed_answer = (EditText) findViewById(R.id.signed_editText);
        unsigned_answer = (EditText) findViewById(R.id.unsigned_editText);
        results = (TextView) findViewById(R.id.results_textView);
        calculator_button = (Button) findViewById(R.id.calculator_button);
        myLayout = (RelativeLayout) findViewById(R.id.relative_layout);

        Random rn = new Random();
        quiz_num = rn.nextInt((255-9)+1)+ 9;

        //getting binary representation of random number into a string variable
        String binary = Integer.toBinaryString(quiz_num);

        //getting the bits from the string
        String binary_bit1 = binary.substring(0,(binary.length()-4));
        String binary_bit2 = binary.substring(binary.length()-4,binary.length());

        //checking if we have a negative number, and if we do, doing the conversion
        if(binary_bit1.startsWith("1")){
            //Call our invert digits method
            String invertedInt = invertDigits(binary);
            //Change this to decimal format.
            int decimalValue = Integer.parseInt(invertedInt, 2);
            //Add 1 to the current decimal and multiply it by -1
            //because we know it's a negative number
            decimalValue = (decimalValue + 1) * -1;

            decimal_value = Integer.toString(decimalValue);
        }

        hex_bit1 = Integer.toHexString(Integer.parseInt(binary_bit1,2));
        hex_bit2 = Integer.toHexString(Integer.parseInt(binary_bit2,2));

        check_answers_button = (Button) findViewById(R.id.check_answers_button);

        question_TextView.setText("What are the signed and unsigned values for the 8-bit hex value 0x"
                + hex_bit1 + hex_bit2);

        //Event Listener for the withResult button
        check_answers_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                num_of_clicks++;//use to launch different intent on second click

                if(num_of_clicks == 2){
                    Intent back_to_main = new Intent();//(Hex_to_Dec.this, MainActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("signed_answer", String.valueOf(signed));//attaching the boolean value of signed
                    extras.putString("unsigned_answer", String.valueOf(unsigned));//attaching the boolean value of unsigned
                    back_to_main.putExtras(extras);//attaching the bundle to the intent
                    setResult(Activity.RESULT_OK,back_to_main);
                    finish();
                }else {
                    if (signed_answer.getText().toString().trim().equals(decimal_value))
                        signed = true;

                    if (unsigned_answer.getText().toString().trim().equals(Integer.toString(quiz_num)))
                        unsigned = true;

                    if (signed & unsigned) {
                        myLayout.setBackgroundColor(Color.GREEN);
                        check_answers_button.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                myLayout.setBackgroundColor(Color.WHITE);
                            }
                        }, 2000);
                        Toast.makeText(Hex_to_Dec.this, "Good job, you got both answers correct!", Toast.LENGTH_SHORT).show();
                    } else {
                        myLayout.setBackgroundColor(Color.RED);
                        check_answers_button.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                myLayout.setBackgroundColor(Color.WHITE);
                            }
                        }, 2000);
                        results.setText("Looks like you made some mistakes. The answer for signed is: "
                                + decimal_value + ". The answer for unsigned is: " + quiz_num + ".");
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

    public static String invertDigits(String binaryInt) {
        String result = binaryInt;
        result = result.replace("0", " "); //temp replace 0s
        result = result.replace("1", "0"); //replace 1s with 0s
        result = result.replace(" ", "1"); //put the 1s back in
        return result;
    }
}
