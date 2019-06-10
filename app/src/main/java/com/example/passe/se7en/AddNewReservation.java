package com.example.passe.se7en;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import java.util.Calendar;
import java.util.Date;

import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewReservation extends AppCompatActivity {


    private FloatingActionButton fBilliards;

    private FloatingActionButton fPing;
    private String type;
    private FloatingActionButton fPS ;
    private FloatingActionButton fPS3 ;
    private FloatingActionButton f;
    private TextView DR,TR;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseAuth mauth;
    private FirebaseDatabase database;
    private DatabaseReference  userData;
    private FirebaseUser current_user;
    private String Date="";
    private String Time="";
    private Calendar cal;
    private EditText editText,editText2,editText3;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_reservation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//Initializations
        type=" ";
        fBilliards = (FloatingActionButton) findViewById(R.id.FB);
        fPing = (FloatingActionButton) findViewById(R.id.FP);
        fPS = (FloatingActionButton) findViewById(R.id.FPS);
        fPS3 = (FloatingActionButton) findViewById(R.id.FPS3);
        f = (FloatingActionButton) findViewById(R.id.addNewReservation);
        DR =(TextView)  findViewById(R.id.xDateReservation);
        TR =(TextView)  findViewById(R.id.xTimeReservation);
        editText =(EditText)  findViewById(R.id.editText);
        editText2 =(EditText)  findViewById(R.id.editText2);
        editText3 =(EditText)  findViewById(R.id.editText3);
//get date and time
        cal=Calendar.getInstance();
//References to Firebase
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("requests");
        mauth = FirebaseAuth.getInstance();
        current_user = mauth.getCurrentUser();
        userData = database.getReference().child("Users").child(current_user.getUid());
        editText2.setEnabled(false);



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {//check if the user is signedout and go to login

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(AddNewReservation.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }
        };


     /*   final Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(AddNewReservation.this,
                R.array.rooms, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
*/

//choose type of registration
        fPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type ="playstation 4";

                fPS.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                fBilliards.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                fPing.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                fPS3.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                editText2.setEnabled(true);
            }
        });
        fPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="ping pong";
                fPS.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                fBilliards.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                fPing.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                fPS3.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                editText2.setEnabled(false);
            }
        });
        fBilliards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type ="billiards";
                fPS.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                fBilliards.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                fPing.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                fPS3.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                editText2.setEnabled(false);
            }
        });
        fPS3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type ="playstation 3";
                fPS.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                fBilliards.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                fPing.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                fPS3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                editText2.setEnabled(true);
            }
        });


        DR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
            }
        });


        TR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(1);
            }
        });






        f.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                f.setEnabled(false);
//getting inputs

                final String Number= editText.getText().toString().trim();
             //   final String Room= spinner.getSelectedItem().toString().trim();

                final String Roomx= editText3.getText().toString().trim();

                String Gamex= editText2.getText().toString().trim();

                if (type.equals(" ")) {
                    Toast.makeText(getApplicationContext(), "Please choose reservation type", Toast.LENGTH_SHORT).show();
                    f.setEnabled(true);
                    return;
                }
                //validating the inputs
                if (TextUtils.isEmpty(Date) || TextUtils.isEmpty(Time) ) {
                    Toast.makeText(getApplicationContext(), "Please Enter Date & Time", Toast.LENGTH_SHORT).show();
                    f.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(Number) ) {
                    Toast.makeText(getApplicationContext(), "Please Enter Number of Persons", Toast.LENGTH_SHORT).show();
                    f.setEnabled(true);
                    return;
                }


                if (Integer.parseInt(Number) > 9 || Integer.parseInt(Number) < 1 ) {
                    Toast.makeText(getApplicationContext(), "Please enter a Number Between 1 & 8", Toast.LENGTH_SHORT).show();
                    f.setEnabled(true);
                    return;
                }

                Calendar c = Calendar.getInstance();


                if(c.after(cal))
                {


                    Toast.makeText(getApplicationContext(), "Please Enter correct Time", Toast.LENGTH_SHORT).show();
                    f.setEnabled(true);
                    return;


                }


                else {

                    if (TextUtils.isEmpty(Gamex) ) {
                        Gamex=" No Game Entered";
                    }

                    if (TextUtils.isEmpty(Roomx) ) {
                        Gamex=" No Room Entered";
                    }

                    // Date picker dialog
                final String Game =Gamex;
                    final String Room =Roomx;

//adding the offer to the db
                    Toast.makeText(getApplication(), "Please Wait Your Request is Being Added ...", Toast.LENGTH_SHORT).show();
                    final DatabaseReference newOrder = mDatabase.push();
                    userData.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {// insert order to db
                         //   newOrder.child("time").setValue(timeH+ " : " +timeM);
                          //  newOrder.child("date").setValue(dayD+ " / " +dayM);

                            newOrder.child("time").setValue(Time);
                            newOrder.child("date").setValue(Date);
                            newOrder.child("number").setValue(Number);
                            newOrder.child("room").setValue(Room);
                            newOrder.child("type").setValue(type);
                            newOrder.child("userphone").setValue(dataSnapshot.child("PhoneNumber").getValue());
                            newOrder.child("useremail").setValue(current_user.getUid());
                            newOrder.child("game").setValue(Game);
                            newOrder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {


                                    Toast.makeText(getApplication(), "Your Request has been submitted successfully", Toast.LENGTH_SHORT).show();
                                }
                            });


                            f.setEnabled(true);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                 }


                    });
                }


            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddNewReservation.this, Home.class);// go back to home activity
                startActivity(i);
                finish();

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
    type=" ";
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
//set current time and date

        TR.setText(String.valueOf(hour) + " : " + String.valueOf(minute));
        DR.setText( String.valueOf(day) + ", " + x+ " " + String.valueOf(year));

    }

    public void HideKeyboard(View view) {// hide keyboard

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mgr.isAcceptingText()) {
            mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        switch (id) {
            case 0:

                // Open the datepicker dialog
                DatePickerDialog dialog = new DatePickerDialog(AddNewReservation.this, date_listener, year, month, day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return dialog;
            // return new DatePickerDialog(Test.this, date_listener, year,
            //       month, day);
            case 1:

                // Open the timepicker dialog
                TimePickerDialog dialog2= new TimePickerDialog(AddNewReservation.this, time_listener, hour,
                        minute, false);
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



           Date = String.valueOf(day) + ", " + x
                    + " " + String.valueOf(year);


// Set time of calendar chosen

            cal.set(Calendar.DAY_OF_MONTH,day);
            cal.set(Calendar.MONTH,month);
            cal.set(Calendar.YEAR,year);
            DR.setText(Date);


        }
    };
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            Time= String.valueOf(hour) + " : " + String.valueOf(minute);
            TR.setText(Time);

            //set time as chosen
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

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

