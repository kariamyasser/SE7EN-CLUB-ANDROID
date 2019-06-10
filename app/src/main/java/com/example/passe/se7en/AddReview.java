package com.example.passe.se7en;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.passe.se7en.OrderFood.orderedItems;

public class AddReview extends AppCompatActivity {



    private EditText comment;
    private RatingBar ratingBar;

    private FloatingActionButton f;


    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseAuth mauth;
    private FirebaseDatabase database;
    private DatabaseReference  userData;
    private FirebaseUser current_user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Initializations
        ratingBar = (RatingBar) findViewById(R.id.rateStars);
        comment = (EditText) findViewById(R.id.addedcomment);
        f = (FloatingActionButton) findViewById(R.id.addreview);


      /*  LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);*/

//Database instances and references
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("reviews");
        mauth = FirebaseAuth.getInstance();
        current_user = mauth.getCurrentUser();
        userData = database.getReference().child("Users").child(current_user.getUid());



        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//check if the user is signedout and go to login

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(AddReview.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }
        };






        f.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                     f.setEnabled(false);
//getting inputs
                                     final String com = comment.getText().toString().trim();
                                     final String rating = String.valueOf(ratingBar.getRating()).trim();


//validating the inputs
                                     if (TextUtils.isEmpty(com) || TextUtils.isEmpty(rating) || rating.equals("0.0")) {
                                         Toast.makeText(getApplicationContext(), "Please Enter Rating and Comments ", Toast.LENGTH_SHORT).show();
                                         f.setEnabled(true);
                                         return;
                                     } else {


//adding the offer to the db
                                         Toast.makeText(getApplication(), "Please Wait Your Review is Being Added ...", Toast.LENGTH_SHORT).show();
                                         final DatabaseReference newOrder = mDatabase.push();
                                         userData.addValueEventListener(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(DataSnapshot dataSnapshot) {// insert order to db
                                                 newOrder.child("comment").setValue(com);
                                                 newOrder.child("rating").setValue(rating);
                                                 newOrder.child("userphone").setValue(dataSnapshot.child("PhoneNumber").getValue());
                                                 newOrder.child("useremail").setValue(dataSnapshot.child("Email").getValue());
                                                 newOrder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<Void> task) {


                                                         Toast.makeText(getApplication(), "Your Review Was Added Successfully", Toast.LENGTH_SHORT).show();
                                                     }
                                                 });


                                                 f.setEnabled(true);
                                                 Intent i = new Intent(AddReview.this, Home.class);// go back to home activity
                                                 startActivity(i);
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
                Intent i = new Intent(AddReview.this, Home.class);
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
