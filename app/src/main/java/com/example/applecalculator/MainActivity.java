package com.example.applecalculator;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    private static int first_number = 0;
    private static int second_number;
    public static String math_view = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button OneButton = findViewById(R.id.onebutton);
        OneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (math_view == "0"){
                    math_view = "1";
                }
                math_view = math_view + "1";
            }
        });

        final Button TwoButton = findViewById(R.id.twobutton);
        TwoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (math_view == "0"){
                    math_view = "2";
                }
                math_view = math_view + "2";
            }
        });

        final Button ThreeButton = findViewById(R.id.threebutton);
        ThreeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (math_view == "0"){
                    math_view = "3";
                }
                math_view = math_view + "3";
            }
        });

        final Button AddButton = findViewById(R.id.addbutton);
        AddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //There is more to this
                first_number = Integer.parseInt(math_view);
                math_view = "0";

            }
        });
    }
}
