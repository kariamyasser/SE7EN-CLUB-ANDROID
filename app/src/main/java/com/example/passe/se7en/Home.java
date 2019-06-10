package com.example.passe.se7en;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth =FirebaseAuth.getInstance();



    }

    public void signoutcustomer(View view) {

        mAuth.signOut();//go to login activity


        Intent i = new Intent(Home.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    public void GoToMenu(View view) {// go to food menu

        Intent i=new Intent(Home.this,OrderFood.class);
        startActivity(i);
    }


    public void GoToNewGames(View view) {
        //go to new games activity
        Intent i=new Intent(Home.this,ViewNewGames.class);
        startActivity(i);
    }
    public void GoToNewOffers(View view) {

        //go to new offers activity

        Intent i=new Intent(Home.this,ViewOffers.class);
        startActivity(i);
    }

    public void goToContact(View view) {

        //go to new offers activity

        Intent i=new Intent(Home.this,ConnectUs.class);
        startActivity(i);
    }



    public void goTONewReservation(View view) {

        //go to new offers activity

        Intent i=new Intent(Home.this,AddNewReservation.class);
        startActivity(i);
    }
    public void goToReview(View view) {

        //go to new offers activity

        Intent i=new Intent(Home.this,AddReview.class);
        startActivity(i);
    }

    public void goToResponse(View view) {

        //go to new offers activity

        Intent i=new Intent(Home.this,ViewResponse.class);
        startActivity(i);
    }
    public void goToAVRoom(View view) {

        //go to new offers activity

        Intent i=new Intent(Home.this,ViewRooms.class);
        startActivity(i);
    }



    public void  goToStop(View view) {

        //go to stop activity

        Intent i=new Intent(Home.this,StopReservation.class);
        startActivity(i);
    }

}