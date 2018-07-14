package com.example.applecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton zero_button, one_button, two_button, three_button, four_button, five_button,
            six_button, seven_button, eight_button, nine_button;

    private ImageButton percent_button,period_button,posneg_button,process_button,clear_button,
    multi_button,division_button,add_button,subtract_button;

    EditText mathView;

    private static String first_number ;
    private static String second_number;
    public static String math_view = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup_things();
        setup_listeners();

    }


    private void setup_things(){

        //interface
        mathView= findViewById(R.id.mathview);
        mathView.setText(math_view);

        //number buttons
        zero_button = findViewById(R.id.zerobutton);
        one_button = findViewById(R.id.onebutton);
        two_button = findViewById(R.id.twobutton);
        three_button = findViewById(R.id.threebutton);
        four_button = findViewById(R.id.fourbutton);
        five_button = findViewById(R.id.fivebutton);
        six_button = findViewById(R.id.sixbutton);
        seven_button = findViewById(R.id.sevenbutton);
        eight_button = findViewById(R.id.eightbutton);
        nine_button = findViewById(R.id.ninebutton);

        //special buttons
        percent_button = findViewById(R.id.percentbutton);
        period_button = findViewById(R.id.periodbutton);
        posneg_button = findViewById(R.id.pnbutton);
        process_button = findViewById(R.id.processbutton);
        clear_button = findViewById(R.id.acbutton);

        //operation buttons
        multi_button = findViewById(R.id.multibutton);
        division_button = findViewById(R.id.dvsnbutton);
        add_button = findViewById(R.id.addbutton);
        subtract_button = findViewById(R.id.subtractbutton);

    }

    private void setup_listeners() {

        //number button listeners
        zero_button.setOnClickListener(this);
        one_button.setOnClickListener(this);
        two_button.setOnClickListener(this);
        three_button.setOnClickListener(this);
        four_button.setOnClickListener(this);
        five_button.setOnClickListener(this);
        six_button.setOnClickListener(this);
        seven_button.setOnClickListener(this);
        eight_button.setOnClickListener(this);
        nine_button.setOnClickListener(this);

        //special button listeners
        percent_button.setOnClickListener(this);
        period_button.setOnClickListener(this);
        posneg_button.setOnClickListener(this);
        process_button.setOnClickListener(this);
        clear_button.setOnClickListener(this);

        //operation button listeners
        multi_button.setOnClickListener(this);
        division_button.setOnClickListener(this);
        add_button.setOnClickListener(this);
        subtract_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId())
        {
        case R.id.multibutton:
            int outcome=Integer.parseInt(first_number)*Integer.parseInt(second_number);
            mathView.setText(String.valueOf(outcome));
            break;

        case R.id.twobutton:
            int add=Integer.parseInt(first_number)+Integer.parseInt(second_number);
            mathView.setText(String.valueOf(add));
            break;

        case R.id.threebutton:
            int sub=Integer.parseInt(first_number)-Integer.parseInt(second_number);
            mathView.setText(String.valueOf(sub));
            break;

        case R.id.fourbutton:
            try {
                int div = Integer.parseInt(first_number) / Integer.parseInt(second_number);
                mathView.setText(String.valueOf(div));
            }

            catch(Exception e)
            {
                mathView.setText("Div. By 0..!!");
            }
            break;
        }
        //gotta add other functionalities here, but first make sure what you have is working.
    }
}
