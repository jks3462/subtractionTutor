package com.example.kirandeep.subtractiontutor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirandeep.subtractiontutor.Popups.TutorialExit;

import java.util.Locale;

public class TutorialsHome extends AppCompatActivity implements TutorialExit.MyInterface{

    TextToSpeech t1;
    ImageView b1;
    ImageView lm;
    TextToSpeech t2;
    ImageView b2;

    public static final String PREFS_NAME = "MathsTutorSettings";
    private final int OPTIONS_ACTIVITY_REQUEST_CODE=0;

    private ProblemComposer problemComposer;
    private int points;
    private int answerAttempts;

    private TextView scoreView;
    private TextView problemView;
    private TableLayout visualGrid;
    private ImageView helpButton;
    private LinearLayout answerButtonsRow;
    private LinearLayout answerTextRow;
    private Button textAnswerButton;
    private TextView textAnswer;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private LinearLayout keyRow1;
    private LinearLayout keyRow2;
    private LinearLayout keyRow3;
    private LinearLayout keyRow4;
    private Button key1Button;
    private Button key2Button;
    private Button key3Button;
    private Button key4Button;
    private Button key5Button;
    private Button key6Button;
    private Button key7Button;
    private Button key8Button;
    private Button key9Button;
    private Button key0Button;
    private View.OnClickListener helpButtonListener;
    private View.OnClickListener textAnswerButtonListener;
    private View.OnClickListener answerButtonListener;
    private View.OnClickListener keyButtonListener;
    private Button[] answerButtons;

    private SharedPreferences sharedPreferences;
    private Options options;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  this code is kept here so the score is not reset every time we change the settings
        setContentView(R.layout.activity_tutorials_home);
      /*  scoreView = (TextView)findViewById(R.id.scoreView);
        scoreView.setText(String.format("%s %s",getString(R.string.score), getString(R.string.initialScore)));
        scoreView.setTextColor(Color.GREEN);*/
lm=(ImageView) findViewById(R.id.learnmore);
        lm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://youtu.be/oAOpN9_0Nfo"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        //this is the code for text to speech
        b2=(ImageView)findViewById(R.id.button1);

        t2=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t2.setLanguage(Locale.UK);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Subtraction is taking away one number from another        . There are some apples on the screen and if some are taken away      count the remaining to get the answer.";


                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t2.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        initialize();
        presentProblem();
    }
/*
    public void youtube()
    {
        Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }*/
    @Override
    public void onChoose() { finish(); }


    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }


    public void onBackPressed()
    {
        // code here to show dialog
        // super.onBackPressed();  // optional depending on your needs
        DialogFragment newFragment = new TutorialExit("Do you want to exit tutorials?");
        newFragment.show(getSupportFragmentManager(), "exittut");
    }

    /** Called when clicking the menu button. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title_only, menu);

        return true;
    }

    /** Called when a menu item in the menu is clicked. */
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuabout:
                Toast.makeText(this, "Interactive Subtraction Tutor for kids", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menuoptions:
                Intent intent = new Intent(this, OptionsScreen.class);
                startActivityForResult(intent, OPTIONS_ACTIVITY_REQUEST_CODE);
               // startActivity(intent);
                return true;


            default:
                if (!item.hasSubMenu()) {
                    return true;
                }
                break;
        }

        return false;
    }

    /** Called when a sub/child activity/form/screen is exiting. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);


        initialize();
        presentProblem();
    }
    private void getSavedSettings()
    {
        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        options.ShowPossibleAnswers = sharedPreferences.getBoolean("showPossibleAnswers", true);
        options.Operator = sharedPreferences.getString("operator", "-");
        options.Operand1 =  new OperandRange(sharedPreferences.getInt("operator1min", 0), sharedPreferences.getInt("operator1max", 10));
        options.Operand2 =  new OperandRange(sharedPreferences.getInt("operator2min", 0), sharedPreferences.getInt("operator2max", 10));
    }
    /** UI initialization code */
    private void initialize()
    {
        // Initialize from saved settings (set in options screen)
        options = new Options();
        getSavedSettings();
        problemComposer = ProblemComposerFactory.Create(options);

        problemView = (TextView)findViewById(R.id.problemView);
        answerButtonsRow = (LinearLayout)findViewById(R.id.row2);
        answerTextRow = (LinearLayout)findViewById(R.id.row3);
      /*  textAnswer = (TextView)findViewById(R.id.textAnswer);
        textAnswerButton = (Button)findViewById(R.id.textAnswerButton);*/
        answer1Button = (Button)findViewById(R.id.answer1);
        answer2Button = (Button)findViewById(R.id.answer2);
        answer3Button = (Button)findViewById(R.id.answer3);
        helpButton = (ImageView) findViewById(R.id.helpButton);
        visualGrid = (TableLayout)findViewById(R.id.myTableLayout);



        this.configureUiSetup();

     /*   textAnswerButtonListener = new View.OnClickListener() {
            public void onClick(View v) {
                v.setTag(textAnswer.getText());
                verifyUserAnswer(v);
            }
        };*/
//        textAnswerButton.setOnClickListener(textAnswerButtonListener);


        // AnswerButtonListener
        answerButtonListener = new View.OnClickListener() {
            public void onClick(View v) {

                verifyUserAnswer(v);
            }
        };
        answer1Button.setOnClickListener(answerButtonListener);
        answer2Button.setOnClickListener(answerButtonListener);
        answer3Button.setOnClickListener(answerButtonListener);

        // KeyButtonListener
        keyButtonListener = new View.OnClickListener() {
            public void onClick(View v) {
                Button button = (Button) v;
                textAnswer.setText(textAnswer.getText().toString()+button.getText().toString());
            }
        };

       /* key1Button.setOnClickListener(keyButtonListener);
        key2Button.setOnClickListener(keyButtonListener);
        key3Button.setOnClickListener(keyButtonListener);
        key4Button.setOnClickListener(keyButtonListener);
        key5Button.setOnClickListener(keyButtonListener);
        key6Button.setOnClickListener(keyButtonListener);
        key7Button.setOnClickListener(keyButtonListener);
        key8Button.setOnClickListener(keyButtonListener);
        key9Button.setOnClickListener(keyButtonListener);
        key0Button.setOnClickListener(keyButtonListener);*/

        answerButtons = new Button[] {answer1Button, answer2Button, answer3Button};

        // AnswerButton3Listener
        helpButtonListener = new View.OnClickListener() {
            public void onClick(View v) {
                showProblemHelp();
            }
        };
        helpButton.setOnClickListener(helpButtonListener);
    }

    private void configureUiSetup()
    {
        if (this.options.ShowPossibleAnswers) {
            answerButtonsRow.setVisibility(View.VISIBLE);
           /* answerTextRow.setVisibility(View.GONE);
           keyRow1.setVisibility(View.GONE);
            keyRow2.setVisibility(View.GONE);
            keyRow3.setVisibility(View.GONE);
            keyRow4.setVisibility(View.GONE);*/
        }


    }
    private void presentProblem()
    {
        problemComposer.GenerateProblem();
        problemView.setText(problemComposer.GetProblemText(false));
         {
            problemComposer.GenerateAnswerPossibilities(answerButtons);
            visualGrid.setVisibility(View.GONE);
            helpButton.setVisibility(View.VISIBLE);
            answerButtonsRow.setVisibility(View.VISIBLE);
            answerTextRow.setVisibility(View.GONE);
        }

        //this is the code for text to speech
        b1=(ImageView)findViewById(R.id.button);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = problemComposer.GetProblemTextForAudio(false);


                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        /*else {
            textAnswer.setText("");
            helpButton.setVisibility(View.GONE);
            answerButtonsRow.setVisibility(View.GONE);
            answerTextRow.setVisibility(View.VISIBLE);
        }*/
    }
    private void showProblemHelp()
    {
       {
            helpButton.setVisibility(View.GONE);
            visualGrid.setVisibility(View.VISIBLE);
            problemComposer.showImageHint(visualGrid, answerButtons);
        }
    }
    private void verifyUserAnswer(final View view) {
        answerAttempts++;
        // Disable further user input when the text is green/red otherwise the user can click many times on the buttons

        problemComposer.enableViews(answerButtons, false);

        int result = Integer.parseInt(view.getTag().toString());
        if (problemComposer.getCorrectAnswer() == result) {

            problemView.setTextColor(Color.GREEN);
            problemView.setText(problemComposer.GetProblemText(true));    // Show full problem + correct result
            points++;

            // Wait 1 second before resetting the text color and showing a new problem text
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    problemView.setTextColor(Color.WHITE);
                    presentProblem();    // Generate a new problem and update UI
                    problemComposer.enableViews(answerButtons, true);
                }
            }, 1000);
        } else {
            problemView.setTextColor(Color.RED);
            problemComposer.enableViews(answerButtons, true);
        }

    }
}
