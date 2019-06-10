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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DeleteFood extends AppCompatActivity {


    private RecyclerView mFoodList;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseAuth mauth;
    private FirebaseDatabase database;
    private DatabaseReference  userData;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializations
        mFoodList = (RecyclerView) findViewById(R.id.deleteFoodRecyclerview);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager(new LinearLayoutManager(this));



//Database instances and references
        mDatabase = FirebaseDatabase.getInstance().getReference().child("FoodItem");
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();

        current_user = mauth.getCurrentUser();

        userData = database.getReference().child("Users").child(current_user.getUid());


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//check if the user is signedout and go to login

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(DeleteFood.this, Login.class);
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
                Intent i = new Intent(DeleteFood.this, Addfood.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }


    public void DD(View view) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Drinks");

        mAuth.addAuthStateListener(mAuthListener);
//adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        FirebaseRecyclerAdapter<Food,DeleteFood.deleteFoodViewHolder> FBRA= new FirebaseRecyclerAdapter<Food, DeleteFood.deleteFoodViewHolder>(
                Food.class,
                R.layout.singleitem,
                DeleteFood.deleteFoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(DeleteFood.deleteFoodViewHolder viewHolder, Food model, int position) {//filling the data to holders
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setPrice(model.getPrice());

                final String food_key =getRef(position).getKey().toString();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//go to choosing  quantity activity

//delete food item dialog  box confirmation
                        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteFood.this);


                        builder.setTitle(" Delete ?");
                        builder.setIcon(R.drawable.infinite);


                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog


                                mDatabase.child(food_key).removeValue();

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


        mFoodList.setAdapter(FBRA);

    }
    public void DS(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sheesha");
        mAuth.addAuthStateListener(mAuthListener);
//adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        FirebaseRecyclerAdapter<Food,DeleteFood.deleteFoodViewHolder> FBRA= new FirebaseRecyclerAdapter<Food, DeleteFood.deleteFoodViewHolder>(
                Food.class,
                R.layout.singleitem,
                DeleteFood.deleteFoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(DeleteFood.deleteFoodViewHolder viewHolder, Food model, int position) {//filling the data to holders
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setPrice(model.getPrice());

                final String food_key =getRef(position).getKey().toString();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//go to choosing  quantity activity

//delete food item dialog  box confirmation
                        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteFood.this);


                        builder.setTitle(" Delete ?");
                        builder.setIcon(R.drawable.infinite);


                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog


                                mDatabase.child(food_key).removeValue();

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


        mFoodList.setAdapter(FBRA);


    }
    public void DF(View view) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("FoodItem");
        mAuth.addAuthStateListener(mAuthListener);
//adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        FirebaseRecyclerAdapter<Food,DeleteFood.deleteFoodViewHolder> FBRA= new FirebaseRecyclerAdapter<Food, DeleteFood.deleteFoodViewHolder>(
                Food.class,
                R.layout.singleitem,
                DeleteFood.deleteFoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(DeleteFood.deleteFoodViewHolder viewHolder, Food model, int position) {//filling the data to holders
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setPrice(model.getPrice());

                final String food_key =getRef(position).getKey().toString();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//go to choosing  quantity activity

//delete food item dialog  box confirmation
                        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteFood.this);


                        builder.setTitle(" Delete ?");
                        builder.setIcon(R.drawable.infinite);


                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog


                                mDatabase.child(food_key).removeValue();

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


        mFoodList.setAdapter(FBRA);

    }


    @Override
    protected void onStart() {
        super.onStart();

           mAuth.addAuthStateListener(mAuthListener);
//adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        FirebaseRecyclerAdapter<Food,DeleteFood.deleteFoodViewHolder> FBRA= new FirebaseRecyclerAdapter<Food, DeleteFood.deleteFoodViewHolder>(
                Food.class,
                R.layout.singleitem,
                DeleteFood.deleteFoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(DeleteFood.deleteFoodViewHolder viewHolder, Food model, int position) {//filling the data to holders
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setPrice(model.getPrice());

                final String food_key =getRef(position).getKey().toString();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//go to choosing  quantity activity

//delete food item dialog  box confirmation
                        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteFood.this);


                        builder.setTitle(" Delete ?");
                        builder.setIcon(R.drawable.infinite);


                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog


                                mDatabase.child(food_key).removeValue();

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


        mFoodList.setAdapter(FBRA);

    }



    public static class deleteFoodViewHolder extends RecyclerView.ViewHolder{// view holder class adaptation

        View mView;

        public deleteFoodViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setImage(Context ctx, String image) {
            ImageView imageView=(ImageView) mView.findViewById(R.id.itemgameimage);
            Picasso.with(ctx).load(image).into(imageView);

        }

        public void setName(String name) {
            TextView foodname = (TextView) mView.findViewById(R.id.OfferDescriptionView);
            foodname.setText(name);
        }

        public void setPrice(String price) {
            TextView foodprice = (TextView) mView.findViewById(R.id.itemprice);
            foodprice.setText(price+"  L.E");
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
