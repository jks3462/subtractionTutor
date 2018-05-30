package com.example.kirandeep.subtractiontutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirandeep.subtractiontutor.Popups.CountExit;

import java.util.ArrayList;
import java.util.Random;


public class Counting extends AppCompatActivity implements CountExit.MyInterface {
    public static final String PREFS_NAME = "MathsTutorSettings";
    private ImageView textView;
    private ImageView quiz;
    private TextView scoreView;
    private TableLayout visualGrid;
    private ImageButton helpButton;
    private EditText answer;
    private Button submit;
    private SharedPreferences sharedPreferences;
    private Options options;
    private ProblemComposer problemComposer;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private View.OnClickListener answerButtonListener;
    private LinearLayout answerButtonsRow;
    private LinearLayout linear1;
    private Button[] answerButtons;
    private TextView problemView;
    private int points;
    private int answerAttempts;
private int answerCount=0;

    @Override
    public void onChoose() { finish(); }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        //  this code is kept here so the score is not reset every time we change the settings

        scoreView = (TextView)findViewById(R.id.scoreView);

        scoreView.setText(String.format("%s %s",getString(R.string.score), getString(R.string.initialScore)));
        scoreView.setTextColor(Color.BLACK);



       textView = (ImageView)findViewById(R.id.textView);
        quiz = (ImageView)findViewById(R.id.quiz);

    }



    public void onBackPressed()
    {
        // code here to show dialog
        // super.onBackPressed();  // optional depending on your needs
        DialogFragment newFragment = new CountExit("Do you want to exit Quiz?");
        newFragment.show(getSupportFragmentManager(), "exitquiz");
    }

    public void quizStart(View v)
    {
       /* textView.setVisibility(View.GONE);
        quiz.setVisibility(View.GONE);*/
        initialize();
   //     presentProblem();
    }
    private void getSavedSettings()
    {
        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        options.ShowPossibleAnswers = sharedPreferences.getBoolean("showPossibleAnswers", true);
        options.Operator = sharedPreferences.getString("operator", "-");
        options.Operand1 =  new OperandRange(sharedPreferences.getInt("operand1min", 0), sharedPreferences.getInt("operand1max", 10));
        options.Operand2 =  new OperandRange(sharedPreferences.getInt("operand2min", 0), sharedPreferences.getInt("operand2max", 10));
    }


    protected int generateDummyAnswerValue(ArrayList<Integer> disallowedDummyValues){
        int dummyAnswerValue;
        do{
            dummyAnswerValue =  RandomHelper.GetRandomNumberInRange(new OperandRange(answerCount - 5, answerCount + 5)); // Put some spread on the possible answers
        } while (disallowedDummyValues.contains(dummyAnswerValue));

        return dummyAnswerValue;
    }

    public void GeneratePossibilities(Button[] answerButtons)
    {
        ArrayList<Integer> alreadyGeneratedAnswers = new ArrayList<Integer>();
        alreadyGeneratedAnswers.add(answerCount);

        Random correctAnswerButtonIndexRandomizer = new Random();
        int correctAnswerButtonIndex = correctAnswerButtonIndexRandomizer.nextInt(answerButtons.length);

        for (int i=0; i<answerButtons.length; i++)
        {
            if (correctAnswerButtonIndex == i) {
                answerButtons[i].setText(String.valueOf(answerCount));
                answerButtons[i].setTag(answerCount);
            }
            else {
                int dummyAnswerValue = generateDummyAnswerValue(alreadyGeneratedAnswers);
                alreadyGeneratedAnswers.add(dummyAnswerValue);
                answerButtons[i].setText(String.valueOf(dummyAnswerValue));
                answerButtons[i].setTag(dummyAnswerValue);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        initialize();

    }
    private void initialize()
    {
        // Initialize from saved settings (set in options screen)
        options = new Options();
        getSavedSettings();

        textView.setVisibility(View.GONE);
        quiz.setVisibility(View.GONE);

          LinearLayout l = (LinearLayout)findViewById(R.id.row2);
        l.setVisibility(View.VISIBLE);
        scoreView.setVisibility(View.VISIBLE);
    //    problemComposer = ProblemComposerFactory.Create(options);

        //problemView = (TextView)findViewById(R.id.problemView);
        answerButtonsRow = (LinearLayout)findViewById(R.id.row2);
        //answerTextRow = (LinearLayout)findViewById(R.id.row3);
      /*  textAnswer = (TextView)findViewById(R.id.textAnswer);
        textAnswerButton = (Button)findViewById(R.id.textAnswerButton);*/
        answer1Button = (Button)findViewById(R.id.answer1);
        answer2Button = (Button)findViewById(R.id.answer2);
        answer3Button = (Button)findViewById(R.id.answer3);
        //visualGrid = (TableLayout)findViewById(R.id.myTableLayout);




        //this.configureUiSetup();

     /*   textAnswerButtonListener = new View.OnClickListener() {
            public void onClick(View v) {
                v.setTag(textAnswer.getText());
                verifyUserAnswer(v);
            }
        };*/
//        textAnswerButton.setOnClickListener(textAnswerButtonListener);


        // AnswerButtonListener


        generateProblem();





    }






    private void generateProblem()
    {   /*
        problemComposer.GenerateProblem();
        problemView.setText(problemComposer.GetProblemText(false));
      {
            problemComposer.GenerateAnswerPossibilities(answerButtons);
            visualGrid.setVisibility(View.VISIBLE);
            //helpButton.setVisibility(View.VISIBLE);
        //    answerButtonsRow.setVisibility(View.VISIBLE);

        }
        problemComposer.GenerateProblem();
        problemView.setText(problemComposer.GetProblemText(false));
        if (this.options.ShowPossibleAnswers) {
            problemComposer.GenerateAnswerPossibilities(answerButtons);
            visualGrid.setVisibility(View.GONE);
            helpButton.setVisibility(View.VISIBLE);
            answerButtonsRow.setVisibility(View.VISIBLE);
            answerTextRow.setVisibility(View.GONE);
        }
        else {
            textAnswer.setText("");
            helpButton.setVisibility(View.GONE);
            answerButtonsRow.setVisibility(View.GONE);
            answerTextRow.setVisibility(View.VISIBLE);
        }

        if (this.options.ShowPossibleAnswers){
            linear1.setVisibility(View.GONE);
        }
*/
            linear1 = (LinearLayout)findViewById(R.id.linear1);

        ImageView image;
        Random rand = new Random();
        int count = rand.nextInt(8) + 1;
        answerCount=count;
        linear1.removeAllViews();
        for(int i=0;i<count;i++){
            image = new ImageView(getApplicationContext());
            image.setImageResource(R.drawable.apple);
            linear1.addView(image);
        }

        answerButtons = new Button[] {answer1Button, answer2Button, answer3Button};
        GeneratePossibilities(answerButtons);


        answerButtonListener = new View.OnClickListener() {
            public void onClick(View v) {
                verifyUserAnswer(v);
            }
        };
        answer1Button.setOnClickListener(answerButtonListener);
        answer2Button.setOnClickListener(answerButtonListener);
        answer3Button.setOnClickListener(answerButtonListener);




    }

    public void enableViews(View[] views, Boolean enable){
        for (View view : views) {
            view.setClickable(enable);
        }
    }

    private void verifyUserAnswer(final View view) {
        answerAttempts++;

        enableViews(answerButtons,false);
        int result = Integer.parseInt(view.getTag().toString());

        if (answerCount == result) {
            Toast.makeText(this, "CORRECT ANSWER! :)",Toast.LENGTH_SHORT).show();

            points++;


            new Handler().postDelayed(new Runnable() {
                public void run() {
                 //   problemView.setTextColor(Color.WHITE);
                    generateProblem();    // Generate a new problem and update UI
                  enableViews(answerButtons, true);
                }
            }, 1000);
        } else {
           // problemView.setTextColor(Color.RED);
          Toast.makeText(this, "WRONG ANSWER :(",Toast.LENGTH_SHORT).show();
           enableViews(answerButtons, true);
        }
        scoreView.setText(String.format("%s %d/%d", getString(R.string.score), points, answerAttempts));

    }

}
