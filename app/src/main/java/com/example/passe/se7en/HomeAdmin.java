package com.example.passe.se7en;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class HomeAdmin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth =FirebaseAuth.getInstance();

    }





    public void signout(View view) {//signout and go to login activity
        mAuth.signOut();
        Intent i = new Intent(HomeAdmin.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();

    }
    public void GoToAddFood(View view) {//go to edit menu

        Intent i=new Intent(HomeAdmin.this,Addfood.class);
        startActivity(i);
    }
    public void GoToAddGame(View view) {// go to add new game

        Intent i=new Intent(HomeAdmin.this,AddNewGame.class);
        startActivity(i);
    }
    public void GoToOrders(View view) {//go to view orders activity

        Intent i=new Intent(HomeAdmin.this,ViewFoodOrders.class);
        startActivity(i);
    }
    public void GoToAddOffers(View view) {// go to add new offer

        Intent i=new Intent(HomeAdmin.this,AddOffer.class);
        startActivity(i);
    }



    public void goToviewReview(View view) {// go to add new offer

        Intent i=new Intent(HomeAdmin.this,ViewReviews.class);
        startActivity(i);
    }

    public void goToviewRequest(View view) {// go to add new offer

        Intent i=new Intent(HomeAdmin.this,ViewRequest.class);
        startActivity(i);
    }
    public void goToviewReservation(View view) {// go to add new offer

        Intent i=new Intent(HomeAdmin.this,ViewReservations.class);
        startActivity(i);
    }

    public void goToRooms(View view) {// go to add new offer

     //   Intent i=new Intent(HomeAdmin.this,ChangeRoomStatus.class);
      //  startActivity(i);
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
