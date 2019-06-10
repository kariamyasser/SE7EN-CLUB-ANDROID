package com.example.passe.se7en;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.R.attr.format;

public class AddOffer extends AppCompatActivity {


    private TextView ED,SD;
    private EditText OfferDesc;
    private String EndDate="";
    private String StartDate="";
    private Date startD,endD;
   FloatingActionButton f;


    private StorageReference storageReference=null;
    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//Initializations
        ED =(TextView) findViewById(R.id.offerEndDate);
        SD=(TextView) findViewById(R.id.offerStartDate);


        OfferDesc =(EditText) findViewById(R.id.OfferAddedDescription);





        ED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(1);
            }
        });


        SD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
            }
        });


        f=(FloatingActionButton) findViewById(R.id.floatingActionButton2);
//Database References
        storageReference= FirebaseStorage.getInstance().getReference();
        mRef=FirebaseDatabase.getInstance().getReference("Offers");



        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f.setEnabled(false);
//getting inputs

                final String Desc=OfferDesc.getText().toString().trim();


//validating the inputs
                if(TextUtils.isEmpty(StartDate)||TextUtils.isEmpty(EndDate))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Start and End Dates" , Toast.LENGTH_SHORT).show();
                    f.setEnabled(true);
                    return;
                }
                if(TextUtils.isEmpty(Desc))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Description" , Toast.LENGTH_SHORT).show();
                    f.setEnabled(true);
                    return;
                }


                if(startD.after(endD))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter correct date" , Toast.LENGTH_SHORT).show();
                    f.setEnabled(true);
                    return;
                }




                else
                {


//adding the offer to the db
                            final DatabaseReference newPost = mRef.push();
                            newPost.child("startdate").setValue(StartDate);
                            newPost.child("enddate").setValue(EndDate);
                            newPost.child("description").setValue(Desc);
                            Toast.makeText(getApplicationContext(), "Offer Added Successfully", Toast.LENGTH_SHORT).show();

                    f.setEnabled(true);
                }


            }
        });

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.gotodeleteOffer);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(AddOffer.this,DeleteOffer.class);// go to home activity
                startActivity(i);


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(AddOffer.this,HomeAdmin.class);// go to home activity
                startActivity(i);
                finish();


            }
        });

    }

    public void HideKeyboard(View view) {// hide keyboard on touching layout

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mgr.isAcceptingText()) {
            mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        String  x="";
        if(month==0)x="January";
        if(month==1)x="February";
        if(month==2)x="March";
        if(month==3)x="April";
        if(month==4)x="May";
        if(month==5)x="June";
        if(month==6)x="July";
        if(month==7)x="August";
        if(month==8)x="September";
        if(month==9)x="October";
        if(month==10)x="November";
        if(month==11)x="December";


        ED.setText( String.valueOf(day+1) + ", " + x+ " " + String.valueOf(year));
        SD.setText( String.valueOf(day) + ", " + x+ " " + String.valueOf(year));

    }


    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);




        switch (id) {
            case 0:

                // Open the datepicker dialog
                DatePickerDialog dialog = new DatePickerDialog(AddOffer.this, date_listener, year, month, day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return dialog;
            // return new DatePickerDialog(Test.this, date_listener, year,
            //       month, day);
            case 1:

                // Open the timepicker dialog
                DatePickerDialog dialog2= new DatePickerDialog(AddOffer.this, enddate_listener, year, month, day);
                dialog2.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return dialog2;

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String  x="";
            if(month==0)x="January";
            if(month==1)x="February";
            if(month==2)x="March";
            if(month==3)x="April";
            if(month==4)x="May";
            if(month==5)x="June";
            if(month==6)x="July";
            if(month==7)x="August";
            if(month==8)x="September";
            if(month==9)x="October";
            if(month==10)x="November";
            if(month==11)x="December";

            Date d=new Date(day,month-1,year);
            startD=d;

            StartDate = String.valueOf(day) + ", " + x
                    + " " + String.valueOf(year);

            SD.setText(StartDate);


        }
    };
    DatePickerDialog.OnDateSetListener enddate_listener = new DatePickerDialog.OnDateSetListener()  {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text


            String  x="";
            if(month==0)x="January";
            if(month==1)x="February";
            if(month==2)x="March";
            if(month==3)x="April";
            if(month==4)x="May";
            if(month==5)x="June";
            if(month==6)x="July";
            if(month==7)x="August";
            if(month==8)x="September";
            if(month==9)x="October";
            if(month==10)x="November";
            if(month==11)x="December";
            Date d=new Date(day,month-1,year);
            endD=d;
            EndDate = String.valueOf(day) + ", " + x
                    + " " + String.valueOf(year);

            ED.setText(EndDate);


        }
    };

    @Override
    protected void onResume()
    {
        super.onResume();

        if (FirebaseDatabase.getInstance() != null)
        {
            FirebaseDatabase.getInstance().goOnline();
        }
    }

    @Override
    public void onPause() {

        super.onPause();

        if(FirebaseDatabase.getInstance()!=null)
        {
            FirebaseDatabase.getInstance().goOffline();
        }
    }

}
