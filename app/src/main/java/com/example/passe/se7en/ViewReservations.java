package com.example.passe.se7en;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import java.util.Calendar;
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

public class ViewReservations extends AppCompatActivity {


    private RecyclerView mReviewList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializations
        mReviewList = (RecyclerView) findViewById(R.id.reservationRecyclerview);
        mReviewList.setHasFixedSize(true);
        mReviewList.setLayoutManager(new LinearLayoutManager(this));
//Database References
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//checking for signed in user
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(ViewReservations.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("reservation");





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//go back to home
                Intent i = new Intent(ViewReservations.this, HomeAdmin.class);
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
            FirebaseRecyclerAdapter<Reservation,ViewReservations.ReservationViewHolder> FBRA = new FirebaseRecyclerAdapter<Reservation,ViewReservations.ReservationViewHolder>(
                    Reservation.class,
                    R.layout.singlereserve,
                    ViewReservations.ReservationViewHolder.class,
                    mDatabase)

            {
                @Override
                protected void populateViewHolder(final ViewReservations.ReservationViewHolder viewHolder, Reservation model, int position) {
//filling data


                    viewHolder.settime(model.getTime());
                    viewHolder.setDate(model.getDate());
                    viewHolder.setType(model.getType());
                    viewHolder.setusername(model.getUsername());
                    viewHolder.setGame(model.getGame());
                    viewHolder.setNumber(model.getNumber());
                    viewHolder.setNote(model.getNote());
                    viewHolder.setRoom(model.getRoom());
                    viewHolder.setID(model.getId());
                    viewHolder.setuserphone(model.getUserphone());

                    final String requestkey =getRef(position).getKey().toString();


                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {//go to choosing  quantity activity

                            final String IDX=   viewHolder.getID().toString();

                            final String TYPEX=   viewHolder.getType().toString();
                            AlertDialog.Builder builder = new AlertDialog.Builder(ViewReservations.this);


                            builder.setTitle(" Start Reservation ?");
                            builder.setIcon(R.drawable.infinite);


                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Do nothing but close the dialog



                               DatabaseReference  x = FirebaseDatabase.getInstance().getReference().child("inprogress").child(IDX);

                                    Calendar c = Calendar.getInstance();



                                 //   long time = c.getTimeInMillis();
                                    long time = System.currentTimeMillis();

                                    int hour = c.get(Calendar.HOUR_OF_DAY);
                                    int minute = c.get(Calendar.MINUTE);

                                   String StartTime=(String.valueOf(hour) + " : " + String.valueOf(minute));

                                    final DatabaseReference newOrder  =x.push();
                                    newOrder.child("type").setValue(TYPEX);
                                    newOrder.child("starttime").setValue(StartTime);
                                    newOrder.child("millis").setValue(String.valueOf(time));

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
    public static class ReservationViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public String getID() {
            TextView username = (TextView) mView.findViewById(R.id.resss);
            return username.getText().toString();
        }

        public void setDate(String d) {
            TextView comment= (TextView) mView.findViewById(R.id.resdate);
            comment.setText(d);
        }

        public void setNumber(String d) {
            TextView comment= (TextView) mView.findViewById(R.id.resnumber);
            comment.setText(d);
        }
        public void setGame(String d) {
            TextView comment= (TextView) mView.findViewById(R.id.resgame);
            comment.setText(d);
        }
        public void setNote(String d) {
            TextView comment= (TextView) mView.findViewById(R.id.resNote);
            comment.setText(d);
        }
        public void setRoom(String d) {
            TextView comment= (TextView) mView.findViewById(R.id.resroom);
            comment.setText(d);
        }

        public void setusername(String r) {
            TextView desc = (TextView) mView.findViewById(R.id.resCustomerName);
            desc.setText(r);

        }
        public void setuserphone(String r) {
            TextView desc = (TextView) mView.findViewById(R.id.resCustomerNumber);
            desc.setText(r);

        }
        public String getTime() {
            TextView username = (TextView) mView.findViewById(R.id.restime);
            return username.getText().toString();
        }
        public String getType() {
            TextView username = (TextView) mView.findViewById(R.id.resType);
            return username.getText().toString();
        }
        public void settime(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.restime);
            desc.setText(un);
        }


        public void setType(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.resType);
            desc.setText(un);
        }
        public void setID(String un) {
            TextView desc = (TextView) mView.findViewById(R.id.resss);
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
