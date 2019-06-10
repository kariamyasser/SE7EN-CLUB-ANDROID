package com.example.passe.se7en;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeRoomStatus extends AppCompatActivity {


    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseAuth mauth;
    private FirebaseDatabase database;

    private FirebaseUser current_user;
    private Switch s1,s2,s3,s4,s5,s6,s7;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_room_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//set initializations
        s1=(Switch) findViewById(R.id.switch1);
        s2=(Switch) findViewById(R.id.switch2);
        s3=(Switch) findViewById(R.id.switch3);
        s4=(Switch) findViewById(R.id.switch4);
        s5=(Switch) findViewById(R.id.switch5);
        s6=(Switch) findViewById(R.id.switch6);
//get firebase references
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("rooms");
        mauth = FirebaseAuth.getInstance();
        current_user = mauth.getCurrentUser();




//upload room states changes
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (s1.isChecked())
                {

                    mDatabase.child("room1").child("name").setValue("Room 1");
                    mDatabase.child("room1").child("state").setValue("Available");

                }
                else if (! s1.isChecked())
                {
                    mDatabase.child("room1").child("name").setValue("Room 1");
                    mDatabase.child("room1").child("state").setValue("Busy");
                }

            }
        });


        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (s2.isChecked())
                {

                    mDatabase.child("room2").child("name").setValue("Room 2");
                    mDatabase.child("room2").child("state").setValue("Available");

                }
                else if (! s2.isChecked())
                {
                    mDatabase.child("room2").child("name").setValue("Room 2");
                    mDatabase.child("room2").child("state").setValue("Busy");
                }

            }
        });
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (s3.isChecked())
                {

                    mDatabase.child("room3").child("name").setValue("Room 3");
                    mDatabase.child("room3").child("state").setValue("Available");

                }
                else if (! s3.isChecked())
                {
                    mDatabase.child("room3").child("name").setValue("Room 3");
                    mDatabase.child("room3").child("state").setValue("Busy");
                }

            }
        });
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (s4.isChecked())
                {

                    mDatabase.child("room4").child("name").setValue("Room 4");
                    mDatabase.child("room4").child("state").setValue("Available");

                }
                else if (! s4.isChecked())
                {
                    mDatabase.child("room4").child("name").setValue("Room 4");
                    mDatabase.child("room4").child("state").setValue("Busy");
                }

            }
        });

        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (s5.isChecked())
                {

                    mDatabase.child("room5").child("name").setValue("Room 5");
                    mDatabase.child("room5").child("state").setValue("Available");

                }
                else if (! s5.isChecked())
                {
                    mDatabase.child("room5").child("name").setValue("Room 5");
                    mDatabase.child("room5").child("state").setValue("Busy");
                }

            }
        });
        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (s6.isChecked())
                {

                    mDatabase.child("room6").child("name").setValue("Room 6");
                    mDatabase.child("room6").child("state").setValue("Available");

                }
                else if (! s6.isChecked())
                {
                    mDatabase.child("room6").child("name").setValue("Room 6");
                    mDatabase.child("room6").child("state").setValue("Busy");
                }

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//check if the user is signedout and go to login

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(ChangeRoomStatus.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        };





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ChangeRoomStatus.this,HomeAdmin.class);// go to home activity
                startActivity(i);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("rooms");
        mDatabase1.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String type = dataSnapshot.child("room1").child("state").getValue().toString();
//set current room statuses
                if (type.equals("Available")) {

                    s1.setChecked(true);

                }
                else if (type.equals("Busy")){ s1.setChecked(false);}
                String type2 = dataSnapshot.child("room2").child("state").getValue().toString();

                if (type2.equals("Available")) {
                    s2.setChecked(true);
                } else if (type.equals("Busy")) { s2.setChecked(false);
                }

                String type3 = dataSnapshot.child("room3").child("state").getValue().toString();
                if (type3.equals("Available")) {
                    s3.setChecked(true);
                } else if (type.equals("Busy")){ s3.setChecked(false);
                }

                String type4 = dataSnapshot.child("room4").child("state").getValue().toString();
                if (type4.equals("Available")) {
                    s4.setChecked(true);
                } else if (type.equals("Busy")){ s4.setChecked(false);
                }

                String type5 = dataSnapshot.child("room5").child("state").getValue().toString();
                if (type5.equals("Available")) {
                    s5.setChecked(true);
                } else if (type.equals("Busy")){ s5.setChecked(false);
                }

                String type6 = dataSnapshot.child("room6").child("state").getValue().toString();
                if (type6.equals("Available")) {
                    s6.setChecked(true);
                } else if (type.equals("Busy")){ s6.setChecked(false);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




    }
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
