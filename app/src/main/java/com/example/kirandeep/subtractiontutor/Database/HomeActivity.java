package com.example.kirandeep.subtractiontutor.Database;


/**
 * Created by jaskirat singh on 31-10-2016.
 */

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kirandeep.subtractiontutor.MainActivity;
import com.example.kirandeep.subtractiontutor.Popups.ExitPopup;
import com.example.kirandeep.subtractiontutor.R;
import com.example.kirandeep.subtractiontutor.Session.AlertDialogManager;
import com.example.kirandeep.subtractiontutor.Session.SessionManager;

public class HomeActivity extends AppCompatActivity implements ExitPopup.MyInterface
{
    public static String Displayname=null;  //to show the name of the person on mainactivity
   // Button btnSignIn,
   ImageView btnSignUp;
    LoginDataBaseAdapter loginDataBaseAdapter;
ImageView btnSignIn;

    @Override
    public void onChoose() { finish(); }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

// create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

// Get The Refference Of Buttons
        btnSignIn=(ImageView) findViewById(R.id.buttonSignIN);
        btnSignUp=(ImageView)findViewById(R.id.buttonSignUP);

// Set OnClick Listener on SignUp button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
// TODO Auto-generated method stub

/// Create Intent for SignUpActivity abd Start The Activity
                Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
                startActivity(intentSignUP);
                finish();
            }
        });
    }

/*
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        // super.onBackPressed();  // optional depending on your needs
        DialogFragment newFragment = new ExitPopup("Do you want to exit SUBTRACTION-TUTOR?");
        newFragment.show(getSupportFragmentManager(), "log");
    }
*/
    // Methos to handleClick Event of Sign In Button
    public void signIn(View V)
    {

        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.activity_login);
        dialog.setTitle("Login");

// get the Refferences of views
        final EditText editTextUserName=(EditText)dialog.findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword=(EditText)dialog.findViewById(R.id.editTextPasswordToLogin);

        ImageView btnSignIn=(ImageView) dialog.findViewById(R.id.buttonSignIn);

// Set On ClickListener
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                // Alert Dialog Manager
                AlertDialogManager alert = new AlertDialogManager();

                // Session Manager Class

                SessionManager session;
// get The User name and Password
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();
                Displayname=userName;
// fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);

                // Session Manager
                session = new SessionManager(getApplicationContext());

// check if the Stored password matches with Password entered by user
                if(password.equals(storedPassword))
                {
                    // Creating user login session
                    // For testing i am stroing name, email as follow
                    // Use user real data
                    session.createLoginSession(userName, password);

                    Toast.makeText(HomeActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
// Close The Database
        loginDataBaseAdapter.close();
    }



}