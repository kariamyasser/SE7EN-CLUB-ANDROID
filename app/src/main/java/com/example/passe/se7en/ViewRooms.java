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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewRooms extends AppCompatActivity {


    private RecyclerView mReviewList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rooms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializations
        mReviewList = (RecyclerView) findViewById(R.id.roomsRecyclerview);
        mReviewList.setHasFixedSize(true);
        mReviewList.setLayoutManager(new LinearLayoutManager(this));
//Database References
        mDatabase = FirebaseDatabase.getInstance().getReference().child("rooms");
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//checking for signed in user
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(ViewRooms.this, Login.class);
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
                Intent i = new Intent(ViewRooms.this, Home.class);
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
            FirebaseRecyclerAdapter<Room,ViewRooms.RoomViewHolder> FBRA = new FirebaseRecyclerAdapter<Room,ViewRooms.RoomViewHolder>(
                    Room.class,
                    R.layout.gameitem,
                    ViewRooms.RoomViewHolder.class,
                    mDatabase)

            {
                @Override
                protected void populateViewHolder(ViewRooms.RoomViewHolder viewHolder,Room model, int position) {
//filling data


                    viewHolder.setImage(getApplicationContext(),model.getImage());
                    viewHolder.setName(model.getName());
                    viewHolder.setDesc(model.getState());

                }

            };
            mReviewList.setAdapter(FBRA);

        }
        catch (Exception e)
        {}
    }

    //offer holder class adaptation
    public static class RoomViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public RoomViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }


        public void setImage(Context ctx, String image) {
            ImageView imageView=(ImageView) mView.findViewById(R.id.itemgameimage);
            Picasso.with(ctx).load(image).into(imageView);

        }

        public void setName(String name) {
            TextView gamename = (TextView) mView.findViewById(R.id.itemgamename);
            gamename.setText(name);
        }

        public void setDesc(String price) {
            TextView desc = (TextView) mView.findViewById(R.id.itemgamedesc);
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
