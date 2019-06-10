package com.example.passe.se7en;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewReviews extends AppCompatActivity {

    private RecyclerView mReviewList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializations
        mReviewList = (RecyclerView) findViewById(R.id.reviewsRecyclerview);
        mReviewList.setHasFixedSize(true);
        mReviewList.setLayoutManager(new LinearLayoutManager(this));
//Database References
        mDatabase = FirebaseDatabase.getInstance().getReference().child("reviews");
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//checking for signed in user
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(ViewReviews.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }
        };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//go back to home
                Intent i = new Intent(ViewReviews.this, HomeAdmin.class);
                startActivity(i);
                finish();

            }
        });

    }



    protected void onStart() {
        super.onStart();

        //adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders

        mAuth.addAuthStateListener(mAuthListener);
        try {
            FirebaseRecyclerAdapter<Review,ViewReviews.ReviewViewHolder> FBRA = new FirebaseRecyclerAdapter<Review,ViewReviews.ReviewViewHolder>(
                    Review.class,
                    R.layout.singlereview,
                    ViewReviews.ReviewViewHolder.class,
                    mDatabase)

            {
                @Override
                protected void populateViewHolder(ViewReviews.ReviewViewHolder viewHolder, Review model, int position) {
//filling data


                    viewHolder.setrating( Float.parseFloat(model.getRating()));
                    viewHolder.setusername(model.getUsername());
                    viewHolder.setuserphone(model.getUserphone());
                    viewHolder.setcomment(model.getComment());

                }

            };
            mReviewList.setAdapter(FBRA);

        }
        catch (Exception e)
        {}
    }

    //offer holder class adaptation
    public static class ReviewViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setcomment(String d) {
            TextView comment= (TextView) mView.findViewById(R.id.reviewComment);
            comment.setText(d+"\n");
        }

        public void setrating(float r) {
            RatingBar rating = (RatingBar) mView.findViewById(R.id.reviewStars);
            rating.setRating(r);
        }
        public void setusername(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.reviewName);
            desc.setText(un);
        }

        public void setuserphone(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.reviewPhone);
            desc.setText(un);
        }
    }

    public void HideKeyboard(View view) {// hide keyboard on pressing on layout

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
