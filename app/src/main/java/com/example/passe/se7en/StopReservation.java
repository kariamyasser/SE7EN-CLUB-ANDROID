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
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import static com.example.passe.se7en.R.drawable.stop;

public class StopReservation extends AppCompatActivity {


    private RecyclerView mGameList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//Initializations

        mGameList = (RecyclerView) findViewById(R.id.stopRecyclerview);
        mGameList.setHasFixedSize(true);
        mGameList.setLayoutManager(new LinearLayoutManager(this));
//Database references
             mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(StopReservation.this, Login.class);

                    startActivity(i);
                    finish();
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("inprogress").child(mAuth.getCurrentUser().getUid().toString());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StopReservation.this, Home.class);
                startActivity(i);
                finish();
            }
        });
    }




   protected void onStart() {
        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);
        try {
            FirebaseRecyclerAdapter<InProgress,StopReservation.GamexViewHolder> FBRA = new FirebaseRecyclerAdapter<InProgress,StopReservation.GamexViewHolder>(
                    InProgress.class,
                    R.layout.gameitem,
                    StopReservation.GamexViewHolder.class,
                    mDatabase)

            {
                //adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
                @Override
                protected void populateViewHolder(final StopReservation.GamexViewHolder viewHolder, InProgress model, int position) {

                    viewHolder.setType(model.getType());
                    viewHolder.setMillis(model.getMillis());
                    viewHolder.setStartTime(model.getStarttime());

                    final String requestkey =getRef(position).getKey().toString();

                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            AlertDialog.Builder builder = new AlertDialog.Builder(StopReservation.this);

                            final String millis=   viewHolder.getMillis().toString();

                            Calendar c = Calendar.getInstance();



                           // long time = c.getTimeInMillis();
                            long time = System.currentTimeMillis();

                            long time2= Long.parseLong(millis);
                            double p=((time-time2)*30)/3600000;
                            final String price = String.valueOf(p);
                            builder.setTitle("Stop Reservation?");
                            builder.setIcon(R.drawable.infinite);





                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Accept the request


                                    dialog.dismiss();
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(StopReservation.this);
                                    builder2.setTitle("Your Total is "+price +" LE");
                                    builder2.setIcon(R.drawable.infinite);

                                    builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog2, int which) {
                                            // Accept the request



                                            mDatabase.child(requestkey).removeValue();

                                            dialog2.dismiss();
                                        }
                                    });

                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();

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
            mGameList.setAdapter(FBRA);













        }
        catch (Exception e)
        {}
    }


    public static class GamexViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public GamexViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }



        public void setType(String name) {
            TextView gamename = (TextView) mView.findViewById(R.id.itemgamename);
            gamename.setText(" "+name);
        }

        public void setStartTime(String price) {
            TextView desc = (TextView) mView.findViewById(R.id.timezone);
            desc.setText("Start Time : "+price);
        }

        public void setMillis(String price) {
            TextView desc = (TextView) mView.findViewById(R.id.timezone2);
            desc.setText(price);
        }

        public String getMillis() {
            TextView username = (TextView) mView.findViewById(R.id.timezone2);
            return username.getText().toString();
        }
    }


    public void HideKeyboard(View view) {

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




