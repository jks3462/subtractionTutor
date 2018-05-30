package com.example.kirandeep.subtractiontutor.Popups;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.kirandeep.subtractiontutor.R;

/**
 * Created by jaskirat singh on 31-10-2016.
 */


public class ExitPopup extends DialogFragment {


    public static interface MyInterface {
        public void onChoose();
    }

    private MyInterface mListener;

    @Override
    public void onAttach(Activity activity) {
        mListener = (MyInterface) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    //we have passed the score in s1 ..it the constructor
String s;
    public ExitPopup(String s1){
        s=s1;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(s);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // FIRE ZE MISSILES!
                mListener.onChoose();
               // System.exit(0);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                // Intent intent = new Intent(getActivity(), HomeActivity.class);
                // startActivity(intent);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}