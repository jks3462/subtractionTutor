package com.example.kirandeep.subtractiontutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kirandeep.subtractiontutor.Database.LoginDataBaseAdapter;
import com.example.kirandeep.subtractiontutor.Popups.LogoutPopup;
import com.example.kirandeep.subtractiontutor.Session.AlertDialogManager;
import com.example.kirandeep.subtractiontutor.Session.SessionManager;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements LogoutPopup.MyInterface{

public static MainActivity Obj;
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;
    // Button Logout
    Button btnLogout;

    LoginDataBaseAdapter loginDataBaseAdapter;
    //TextView profileName;

    @Override
    public void onChoose() { finish(); }

    /** Called when clicking the menu button. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        return true;
    }

    /** Called when a menu item in the menu is clicked. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuabout:
                Toast.makeText(this, "Developer : Kirandeep", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.logout:
                session.logoutUser();
              //  Intent intent= new Intent(this, HomeActivity.class);
              //  startActivity(intent);
                finish();



            default:
                if (!item.hasSubMenu()) {
                    return true;
                }
                break;
        }

        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Obj=this;
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());

        TextView lblName = (TextView) findViewById(R.id.lblName);
      //  TextView lblEmail = (TextView) findViewById(R.id.lblEmail);


        // Button logout
     //   btnLogout = (Button) findViewById(R.id.btnLogout);
        // get user data from session

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);



        // displaying user data
        lblName.setText(Html.fromHtml("<right> WELCOME <b>" + name.toUpperCase() + "</b></right>"));
      //  lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));


     //to display the name of the person logged in

/*
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
                finish();
            }
        });
        */

    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
       // super.onBackPressed();  // optional depending on your needs

      //  DialogFragment newFragment = new LogoutPopup("Do you want to LOGOUT?");
      //  newFragment.show(getSupportFragmentManager(), "logout");
        System.exit(0);
    }
    public void gotoTutorials(View view)
    {
        Intent intent=new Intent(this,TutorialsHome.class);
        startActivity(intent);
finish();

    }
    public void gotoPractice(View view)
    {
        Intent intent=new Intent(this,PracticeHome.class);
        startActivity(intent);
finish();
    }

    public void gotoCounting(View view)
    {
        Intent intent=new Intent(this,Counting.class);
        startActivity(intent);
finish();
    }
}
