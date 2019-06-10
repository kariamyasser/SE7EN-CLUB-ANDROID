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
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewOffers extends AppCompatActivity {

    private RecyclerView mOfferList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//Initializations
        mOfferList = (RecyclerView) findViewById(R.id.offerviewlist);
        mOfferList.setHasFixedSize(true);
        mOfferList.setLayoutManager(new LinearLayoutManager(this));
//Database References
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Offers");
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//checking for signed in user
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(ViewOffers.this, Login.class);
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
                Intent i = new Intent(ViewOffers.this, Home.class);
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
            FirebaseRecyclerAdapter<Offer,ViewOffers.OfferViewHolder> FBRA = new FirebaseRecyclerAdapter<Offer,ViewOffers.OfferViewHolder>(
                    Offer.class,
                    R.layout.offersingleitem,
                    ViewOffers.OfferViewHolder.class,
                    mDatabase)

            {
                @Override
                protected void populateViewHolder(ViewOffers.OfferViewHolder viewHolder, Offer model, int position) {
//filling data
                    viewHolder.setdesc(model.getDescription());
                    viewHolder.setStartDate(model.getStartdate());
                    viewHolder.setEndDate(model.getEnddate());
                }

            };
            mOfferList.setAdapter(FBRA);

        }
        catch (Exception e)
        {}
    }

//offer holder class adaptation
    public static class OfferViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public OfferViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setdesc(String d) {
            TextView desc = (TextView) mView.findViewById(R.id.OfferDescriptionView);
            desc.setText(d);
        }

        public void setStartDate(String price) {
            TextView desc = (TextView) mView.findViewById(R.id.StartViewDate);
            desc.setText(price);
        }
        public void setEndDate(String price) {
            TextView desc = (TextView) mView.findViewById(R.id.EndViewDate);
            desc.setText(price);
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
