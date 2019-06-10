package com.example.passe.se7en;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteOffer extends AppCompatActivity {


    private RecyclerView mOfferList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_offer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializations
        mOfferList = (RecyclerView) findViewById(R.id.deleteOfferRecyclerview);
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
                    Intent i = new Intent(DeleteOffer.this, Login.class);
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
                Intent i = new Intent(DeleteOffer.this, AddOffer.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
            FirebaseRecyclerAdapter<Offer,DeleteOffer.dOfferViewHolder> FBRA = new FirebaseRecyclerAdapter<Offer,DeleteOffer.dOfferViewHolder>(
                    Offer.class,
                    R.layout.offersingleitem,
                    DeleteOffer.dOfferViewHolder.class,
                    mDatabase)

            {
                @Override
                protected void populateViewHolder(DeleteOffer.dOfferViewHolder viewHolder, Offer model, int position) {
//filling data
                    viewHolder.setdesc(model.getDescription());
                    viewHolder.setStartDate(model.getStartdate());
                    viewHolder.setEndDate(model.getEnddate());
                    final String key =getRef(position).getKey().toString();


                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {//go to choosing  quantity activity


                            AlertDialog.Builder builder = new AlertDialog.Builder(DeleteOffer.this);

//delete offer dialog  box confirmation
                            builder.setTitle(" Delete ?");
                            builder.setIcon(R.drawable.infinite);


                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog


                                    mDatabase.child(key).removeValue();

                                    dialog.dismiss();
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });


                            AlertDialog alert = builder.create();
                            alert.show();



                        }
                    });

                }

            };
            mOfferList.setAdapter(FBRA);

        }
        catch (Exception e)
        {}
    }

    //offer holder class adaptation
    public static class dOfferViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public dOfferViewHolder(View itemView) {
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
