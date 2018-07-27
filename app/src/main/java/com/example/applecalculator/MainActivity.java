package com.example.applecalculator;

import android.support.v7.app.AppCompatActivity;

import com.example.applecalculator.objcts.addition;
import com.example.applecalculator.objcts.division;
import com.example.applecalculator.objcts.multiplication;
import com.example.applecalculator.objcts.numbers;
import com.example.applecalculator.objcts.operations;
import com.example.applecalculator.objcts.subtraction;
import com.example.applecalculator.objcts.token;

import android.util.Log;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Views
    private ImageButton zero_button, one_button, two_button, three_button, four_button,
            five_button, six_button, seven_button, eight_button, nine_button;
    private ImageButton percent_button, period_button, posneg_button, process_button, clear_button,
            multi_button, division_button, add_button, subtract_button;
    TextView mathView;
    //Data Structures
    Stack<token> operations_stack;
    Queue<token> multi_usage_queue;

    public static String math_view = "0";

    operations new_opt = null;
    //Flags
    Boolean reset_flag = true;
    Boolean ac_flag = false;
    Boolean period_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup_views();
        setup_listeners();

        operations_stack = new Stack<>();
        multi_usage_queue = new LinkedList<>();

    }

    @Override
    public void onClick(View view) {

        double translation = Double.parseDouble(math_view);
        Boolean found;
        found = number_clicked(view);

        if (!found) {
            found = operation_clicked(view, translation);
        }

        if (!found) {
            if (view.getId() == R.id.percentbutton) {
                operations peek_opt = (operations) operations_stack.peek();

                if (translation == 0) {
                    math_view = String.valueOf(0);
                } else if (peek_opt.get_priority() == 2) {      //Multiplication and Division
                    translation = translation * 0.01;
                    math_view = String.valueOf(translation);
                } else if (peek_opt.get_priority() == 1) {      //Addition and Subtraction
                    translation = translation * 0.01
                            * calculate_queue(multi_usage_queue, operations_stack);
                    math_view = String.valueOf(translation);
                }
                mathView.setText(easy_read(math_view,false));
            } else if (view.getId() == R.id.processbutton) {


                number_handler(translation);    //for the number on the user view
                Double result_so_far =
                        calculate_queue(multi_usage_queue, operations_stack);

                while (!multi_usage_queue.isEmpty()) {
                    multi_usage_queue.remove();
                }

                math_view = String.valueOf(result_so_far);
                mathView.setText(easy_read(math_view,false));
            } else if (view.getId() == R.id.acbutton) {

                if (!reset_flag && !ac_flag) {

                    ac_flag = true;
                } else if (ac_flag) {
                    while (!multi_usage_queue.isEmpty()) {
                        multi_usage_queue.remove();
                    }
                    while (!operations_stack.isEmpty()) {
                        operations_stack.pop();
                    }
                }

                reset_flag = true;
                period_flag = false;
                math_view = "0";
                mathView.setText(easy_read(math_view,false));

            }
        }
    }

    /**
     * Setting up the views in one convenient function. Mostly because it's more organised like
     * this.
     */
    private void setup_views() {

        //interface
        mathView = findViewById(R.id.mathview);
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

    /**
     * Setting up the listeners for the views in one convenient function. Mostly because it's more
     * organized like this.
     */
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

    /**
     * Makes the number object, adds it into the multi usage queue, and then makes sure the app
     * knows to reset user view flag, and the decimal usage flag.
     *
     * @param num: the translation of the math_view.
     */
    private void number_handler(double num) {
        numbers current_number = new numbers(num);
        multi_usage_queue.add(current_number);
        reset_flag = true;
        period_flag = false;
    }

    /**
     * Handles numbered buttons' clicks and how numbers are put together behind the scenes.
     *
     * @param view: the view that was clicked
     * @return Boolean representing whether if the clicked view was any of the numbered buttons.
     */
    private Boolean number_clicked(View view) {

        if (view.getId() == R.id.zerobutton) {
            if (reset_flag) {
                reset_flag = false;
                ac_flag = false;
                math_view = "0";
            } else if (!math_view.equals("0")) {
                math_view = math_view + "0";
            }
            mathView.setText(easy_read(math_view,true));
            return true;
        }
        else if (view.getId() == R.id.onebutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                math_view = "1";
            } else {
                math_view = math_view + "1";
            }
            mathView.setText(easy_read(math_view,true));
            return true;
        }
        else if (view.getId() == R.id.twobutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                math_view = "2";
            } else {
                math_view = math_view + "2";
            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }
        else if (view.getId() == R.id.threebutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                math_view = "3";
            } else {
                math_view = math_view + "3";
            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }
        else if (view.getId() == R.id.fourbutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                math_view = "4";
            } else {
                math_view = math_view + "4";
            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }
        else if (view.getId() == R.id.fivebutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                math_view = "5";
            } else {
                math_view = math_view + "5";
            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }
        else if (view.getId() == R.id.sixbutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                math_view = "6";
            } else {
                math_view = math_view + "6";
            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }
        else if (view.getId() == R.id.sevenbutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                math_view = "7";
            } else {
                math_view = math_view + "7";
            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }
        else if (view.getId() == R.id.eightbutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                math_view = "8";
            } else {
                math_view = math_view + "8";
            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }
        else if (view.getId() == R.id.ninebutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                math_view = "9";
            } else {
                math_view = math_view + "9";
            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }
        else if (view.getId() == R.id.periodbutton) {
            if ((reset_flag) || (Double.parseDouble(math_view) == 0)) {
                reset_flag = false;
                ac_flag = false;
                period_flag = true;
                math_view = "0.";
            } else if (!period_flag) {
                period_flag = true;
                math_view = math_view + ".";
            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }
        else if (view.getId() == R.id.pnbutton) {
            if (math_view.charAt(0) == '-') {
                math_view = math_view.substring(1);
            } else {
                math_view = '-' + math_view;

            }
            mathView.setText(easy_read(math_view, true));
            return true;
        }

        return false;
    }

    /**
     * If the button clicked is an operation, it will identify it, add it to the operation
     * stack and then return true to let the app know it was a operation click.
     *
     * @param view: the button that was clicked.
     * @return Boolean: whether if the view clicked, was an operation button.
     */
    private Boolean operation_clicked(View view, Double num) {
        int isDone = 0;

        //operations check.
        if (view.getId() == R.id.multibutton) {
            new_opt = new multiplication();
            isDone = 1;
        } else if (view.getId() == R.id.dvsnbutton) {
            new_opt = new division();
            isDone = 1;
        } else if (view.getId() == R.id.addbutton) {
            new_opt = new addition();
            isDone = 1;
        } else if (view.getId() == R.id.subtractbutton) {
            new_opt = new subtraction();
            isDone = 1;
        }

        //stack handle after identification.
        if (isDone == 1) {
            number_handler(num);
            operations_handler(new_opt);

//            Queue <token> queue_clone = new LinkedList<>(multi_usage_queue);
//            Stack <token> stack_clone = (Stack<token>)operations_stack.clone();
//            math_view = String.valueOf(calculate_queue(queue_clone,stack_clone));
//            mathView.setText(easy_read(math_view,false));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Basically adding the received operation and then properly adding it to the stack, while
     * making sure the stack and queue are in correct standing (the Shunting-yard algorithm).
     * <p>
     * If the new operation has a higher/equal priority then the peak operation, the peak operation
     * needs to go to the Queue, so that it is not dealt with anymore. If the stack is empty, then
     * nothing needs to be done.
     *
     * @param new_opt: the new operation that needs to enter the stack
     */
    private void operations_handler(operations new_opt) {

        while (!operations_stack.isEmpty()) {
            operations peek_opt = (operations) operations_stack.peek();

            if (peek_opt.get_priority() >= new_opt.get_priority()) {
                token switch_token = operations_stack.pop();
                multi_usage_queue.add(switch_token);
            } else {
                break;
            }
        }

        operations_stack.push(new_opt);
    }

    /**
     * This function is designed to calculate the outcome of a given queue.
     * Implements the Postfix Calculator Algorithm.
     *
     * @param some_queue: the multi_usage_queue in this scenario.
     * @return outcome: the out come of the given queue.
     */
    private double calculate_queue(Queue<token> some_queue, Stack<token> op_stack) {

        //Some of Shunting-Yard Algorithm
        /* Moving the remaining operations from the operations stack. */
        while (!op_stack.isEmpty()) {
            some_queue.add(op_stack.pop());
        }

        //Postfix Calculator Algorithm
        Stack<numbers> number_stack = new Stack<>();
        while (!some_queue.isEmpty()) {

            token first_token = some_queue.remove();

            if (first_token.get_type().equals("number")) {
                number_stack.push((numbers) first_token);
            } else if (first_token.get_type().equals("operation")) {
                operations current_operation = (operations) first_token;
                double right = number_stack.pop().get_value();
                double left = number_stack.pop().get_value();

                double result = current_operation.operate(left, right);
                numbers new_num = new numbers(result);
                number_stack.push(new_num);
            }
        }

        return number_stack.pop().get_value();
    }

    /**
     * This function checks whether if the given string is nine digits or less.
     *
     * @param s: the math_view
     * @return Boolean
     */
    private String check_nine(String s) {
        int count = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0, len = s.length(); i < len; i++) {
            if (count == 9) {
                break;
            } else if (Character.isDigit(s.charAt(i))) {
                count++;
            }
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    /**
     * This function grabs results, puts commas throughout the results, accounts for the negative
     * sign, makes sure the font size gets smaller as there are more numbers presented in the
     * math_view, and the values would never get more then 9 digits.
     *
     * @param s: String representation of the number that needs to become easier to read.
     * @return result: brand new string that is easily read.
     */
    private String easy_read(String s,Boolean user_typed) {
        int num_digits = s.length();
        int pos_dot = s.indexOf(".");
        int pos_neg = s.indexOf("-");

        //no unnecessary decimal sign should ever be returned.
        if ((pos_dot != -1) && (!user_typed) && (Double.parseDouble(s) % 1 == 0)) {
            s = String.valueOf(Math.round(Double.parseDouble(s)));
        }


        //dot and neg sign handles - comma placement.
        if (pos_neg == -1) {
            pos_neg = 0;
        } else {
            pos_neg = 1;
        }
        if (pos_dot == -1) {
            pos_dot = num_digits;
        }

        //comma placement.
        for (int i = pos_dot - 3; i > pos_neg; i = i - 3) {
            s = s.substring(0, i) + "," + s.substring(i, s.length());
        }

        //make sure there is the 9 number limit.
        s = check_nine(s);

        //font size.
        if (num_digits <= 7) {
            mathView.setTextSize(80);
        } else if (num_digits == 8) {
            mathView.setTextSize(70);
        } else if (num_digits == 9) {
            mathView.setTextSize(60);
        }




        return s;
    }
}

