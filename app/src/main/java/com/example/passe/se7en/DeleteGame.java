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
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DeleteGame extends AppCompatActivity {

    private RecyclerView mGameList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Initializations

        mGameList = (RecyclerView) findViewById(R.id.deleteGameRecyclerview);
        mGameList.setHasFixedSize(true);
        mGameList.setLayoutManager(new LinearLayoutManager(this));
//Database references
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Games");
        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(DeleteGame.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }
        };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DeleteGame.this, AddNewGame.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        });
    }



    protected void onStart() {
        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);
        try {
            FirebaseRecyclerAdapter<Game,DeleteGame.dGameViewHolder> FBRA = new FirebaseRecyclerAdapter<Game,DeleteGame.dGameViewHolder>(
                    Game.class,
                    R.layout.gameitem,
                    DeleteGame.dGameViewHolder.class,
                    mDatabase)

            {
                //adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
                @Override
                protected void populateViewHolder(DeleteGame.dGameViewHolder viewHolder, Game model, int position) {

                    viewHolder.setName(model.getName());
                    viewHolder.setImage(getApplicationContext(),model.getImage());
                    viewHolder.setPrice(model.getDescription());

                    final String key =getRef(position).getKey().toString();


                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {//go to choosing  quantity activity


                            AlertDialog.Builder builder = new AlertDialog.Builder(DeleteGame.this);

//delete game dialog  box confirmation
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
            mGameList.setAdapter(FBRA);

        }
        catch (Exception e)
        {}
    }


    public static class dGameViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public dGameViewHolder(View itemView) {
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

        public void setPrice(String price) {
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
