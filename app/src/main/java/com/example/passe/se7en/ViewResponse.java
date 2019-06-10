package com.example.passe.se7en;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewResponse extends AppCompatActivity {


    private RecyclerView mReviewList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_response);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializations
        mReviewList = (RecyclerView) findViewById(R.id.responseRecyclerview);
        mReviewList.setHasFixedSize(true);
        mReviewList.setLayoutManager(new LinearLayoutManager(this));
//Database References
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//checking for signed in user
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(ViewResponse.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("response").child(mAuth.getCurrentUser().getUid().toString());





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//go back to home
                Intent i = new Intent(ViewResponse.this, Home.class);
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
            FirebaseRecyclerAdapter<Response, ViewResponse.ResponseViewHolder> FBRA = new FirebaseRecyclerAdapter<Response, ViewResponse.ResponseViewHolder>(
                    Response.class,
                    R.layout.singleresponse,
                    ViewResponse.ResponseViewHolder.class,
                    mDatabase)

            {
                @Override
                protected void populateViewHolder(ViewResponse.ResponseViewHolder viewHolder, Response model, int position) {
//filling data


                    viewHolder.settime(model.getTime());
                    viewHolder.setDate(model.getDate());
                    viewHolder.setType(model.getType());
                    viewHolder.setGame(model.getGame());
                    viewHolder.setNumber(model.getNumber());
                    viewHolder.setNote(model.getNote());
                    viewHolder.setRoom(model.getRoom());
                    viewHolder.setrespond(model.getRespond());

                    final String requestkey =getRef(position).getKey().toString();








                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {//go to choosing  quantity activity


                            AlertDialog.Builder builder = new AlertDialog.Builder(ViewResponse.this);


                            builder.setTitle(" Delete ?");
                            builder.setIcon(R.drawable.infinite);


                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog


                                    mDatabase.child(requestkey).removeValue();

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



            mReviewList.setAdapter(FBRA);

        }
        catch (Exception e)
        {}
    }

    //offer holder class adaptation
    public static class ResponseViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ResponseViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setDate(String d) {
            TextView comment= (TextView) mView.findViewById(R.id.responsedate);
            comment.setText(d);
        }

        public void setrespond(String r) {
            TextView desc = (TextView) mView.findViewById(R.id.responseState);
            desc.setText(r);

        }
        public void settime(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.responsetime);
            desc.setText(un);
        }

        public void setType(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.responsetype);
            desc.setText(un);

        }

        public void setNumber(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.responsenumber);
            desc.setText(un);

        }
        public void setGame(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.responsegame);
            desc.setText(un);

        }
        public void setRoom(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.responseroom);
            desc.setText(un);

        }
        public void setNote(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.responsenote);
            desc.setText(un);

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
