package com.example.ramy.voicecalculator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{

    private LinearLayout rootLayout;
    private TextView textView;
    private ImageButton btnSpeak;
    private  Button powerButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private MediaPlayer mp;
    private int cursorPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rootLayout = (LinearLayout)findViewById(R.id.rootLayout);
        textView = (TextView) findViewById(R.id.textView);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        powerButton = (Button) findViewById(R.id.power);
        powerButton.setText(Html.fromHtml("X <sup><small> Y </small></sup>"));


        mp = MediaPlayer.create(this, R.raw.click);
        cursorPosition = 0;

        initButtons(rootLayout);



    }



    public void initButtons(LinearLayout layout)
    {
        for (int i = 0; i < layout.getChildCount(); i++)
        {
            View v = layout.getChildAt(i);

            if(v instanceof LinearLayout)
                initButtons((LinearLayout) v);

            if (v instanceof Button)
            {
                v.setSoundEffectsEnabled(false);
            }
        }

    }


    /**
     * Showing google speech input dialog
     * */
    public void speak(View v)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String res = result.get(0);
                    res = changeToOperators(res);
                    textView.append(res);
                }
                break;
            }

        }
    }


    private String changeToOperators(String str)
    {
        str = str.replaceAll("one", "1");
        str = str.replaceAll("two", "2");
        str = str.replaceAll("three", "3");
        str = str.replaceAll("four", "4");
        str = str.replaceAll("five", "5");
        str = str.replaceAll("six", "6");
        str = str.replaceAll("seven", "7");
        str = str.replaceAll("eight", "8");
        str = str.replaceAll("nine", "9");


        str = str.replaceAll("times?", "x");
        str = str.replaceAll("multipl(y|ied)", "x");

        str = str.replaceAll("plus", "+");

        str = str.replaceAll("minus|negative", "-");

        str =  str.replaceAll("divid(ed)?", "/");
        str =  str.replaceAll("over", "/");

        str = str.replaceAll("power", "^");

        str = str.replaceAll("open(ed)? bracket(s)?", "(");
        str = str.replaceAll("close(d)? bracket(s)?", ")");

        str = str.replaceAll("[a-z]", "");


        return str;

    }


    private static String convertPower(String expression, int operator) throws EvaluationException
    {
        String base = "";
        String exponent = "";

        int base_index = operator;
        int exp_index = operator;


        while(base_index > 0 && new String(expression.charAt(--base_index) + "").matches("[0-9]"))
        {
            base = expression.charAt(base_index) + base;

        }


        while(exp_index < expression.length() - 1 && new String(expression.charAt(++exp_index) + "").matches("[0-9]"))
        {
            exponent = exponent + expression.charAt(exp_index);

        }

        return expression.substring(0,(base_index == 0? 0: base_index+1)) +
                new Evaluator().evaluate("pow(" + base + "," + exponent +")") + expression.substring(exp_index+1);

    }

    private static String calculateFactorial(String expression, int factOp)
    {

        try
        {
            String num = "";
            int index = factOp;
            while(index > 0 && (expression.charAt(--index) + "").matches("[0-9]"))
                num += expression.charAt(index);


            int num_i = Integer.parseInt(num);
            int fact = num_i;

            if(num_i == 0)
                fact = 1;

            while(num_i > 1)
                fact = fact * --num_i;

            expression = expression.substring(0, index) + fact + expression.substring(factOp+1);

            return expression;

        }catch(NumberFormatException e)
        {
            return "Invalid";
        }

    }



    public void clickZero(View v)
    {
        mp.start();
        cursorPosition++;

        textView.append("0");


    }

    public void clickOne(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("1");

    }

    public void clickTwo(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("2");

    }

    public void clickThree(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("3");

    }

    public void clickFour(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("4");

    }

    public void clickFive(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("5");

    }

    public void clickSix(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("6");

    }

    public void clickSeven(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("7");

    }

    public void clickEight(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("8");

    }

    public void clickNine(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("9");

    }


    public void clickDot(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append(".");

    }

    public void erase(View v)
    {
        mp.start();
        cursorPosition = 0;
        textView.setText("");

    }

    public void clickDelete(View v)
    {
        mp.start();
        CharSequence text = textView.getText();
        if(text.length() > 0)
        {
            cursorPosition--;
            textView.setText(text.subSequence(0, text.length() - 1));

        }

    }

    public void add(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("+");
    }

    public void subtract(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("-");

    }


    public void multiply(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("x");

    }

    public void divide(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("/");

    }

    public void negative(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("-");
    }

    public void openBracket(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("(");

    }


    public void closedBracket(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append(")");

    }


    public void power(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("^");

    }

    public void clickSqrt(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("√(");

    }

    public void factorial(View v)
    {
        mp.start();
        cursorPosition++;
        textView.append("!");

    }


    public void equal(View v)
    {
        mp.start();
        Evaluator evaluator = new Evaluator();

        String expression = textView.getText().toString();
        expression = expression.replaceAll("x", "*");
        expression = expression.replaceAll("√", "sqrt");



        try
        {
            for (int i = expression.length() - 1; i >= 0; i--)
            {
                if(expression.charAt(i) == '^')
                    expression = convertPower(expression, i);

                else if(expression.charAt(i) == '!')
                    expression = calculateFactorial(expression, i);

            }

            String answer = evaluator.evaluate(expression);
            textView.setText(answer);
            cursorPosition = answer.length();

        } catch (EvaluationException e)
        {
            textView.setText(textView.getText().toString().replaceAll("\\*", "x" ));

            Toast.makeText(this, "Invalid Expression", Toast.LENGTH_LONG).show();
        }

    }


}
