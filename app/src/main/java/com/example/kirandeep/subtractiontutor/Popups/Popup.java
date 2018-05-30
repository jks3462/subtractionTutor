package com.example.kirandeep.subtractiontutor.Popups;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.kirandeep.subtractiontutor.Counting;
import com.example.kirandeep.subtractiontutor.MainActivity;
import com.example.kirandeep.subtractiontutor.R;

/**
 * Created by jaskirat singh on 31-10-2016.
 */


public class Popup extends DialogFragment {


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
    public Popup(String s1){
        s=s1;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage( s)
                .setPositiveButton(R.string.replay, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Intent intent = new Intent(getActivity(),Counting.class);
                        startActivity(intent);
                        mListener.onChoose();
                    }
                })
                .setNegativeButton(R.string.mainmenu, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        mListener.onChoose();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


}