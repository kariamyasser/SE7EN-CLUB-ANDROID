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
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OrderFood extends AppCompatActivity {
    private RecyclerView mFoodList;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private FirebaseAuth mauth;
    private FirebaseDatabase database;
    private DatabaseReference  userData, mRef;
    private FirebaseUser current_user;


    public static String orderedItems="";
    public static String orderedItemsQuantity="";
    public static String roomNumber=null;
    public static float total=00;
    public static String Category="FoodItem";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Initializations
        mFoodList = (RecyclerView) findViewById(R.id.foodlist);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager(new LinearLayoutManager(this));
//Database instances and references



            mDatabase = FirebaseDatabase.getInstance().getReference().child("FoodItem");



            mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();

        current_user = mauth.getCurrentUser();

        userData = database.getReference().child("Users").child(current_user.getUid());

        mRef = database.getReference().child("Orders");





        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {//check if the user is signedout and go to login

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent i = new Intent(OrderFood.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        };


    }


   @Override
    protected void onStart() {
        super.onStart();

       TextView totalp=(TextView) findViewById(R.id.totalprice);
               totalp.setText(" Total : " + total +" LE");

        mAuth.addAuthStateListener(mAuthListener);
//adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        FirebaseRecyclerAdapter<Food,FoodViewHolder> FBRA= new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.singleitem,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {//filling the data to holders
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setPrice(model.getPrice());

                final String food_key =getRef(position).getKey().toString();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//go to choosing  quantity activity
                        Intent i=new Intent(OrderFood.this,AddFoodOrder.class);
                        i.putExtra("foodID",food_key);
                        startActivity(i);

                    }
                });
            }
        };
        mFoodList.setAdapter(FBRA);

    }


    public void OD(View view) {

        Category="Drinks";
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Drinks");
        mAuth.addAuthStateListener(mAuthListener);
//adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        FirebaseRecyclerAdapter<Food,FoodViewHolder> FBRA= new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.singleitem,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {//filling the data to holders
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setPrice(model.getPrice());

                final String food_key =getRef(position).getKey().toString();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//go to choosing  quantity activity
                        Intent i=new Intent(OrderFood.this,AddFoodOrder.class);
                        i.putExtra("foodID",food_key);
                        startActivity(i);

                    }
                });
            }
        };
        mFoodList.setAdapter(FBRA);

    }

    public void OF(View view) {

        Category="FoodItem";
        mDatabase = FirebaseDatabase.getInstance().getReference().child("FoodItem");
        mAuth.addAuthStateListener(mAuthListener);
//adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        FirebaseRecyclerAdapter<Food,FoodViewHolder> FBRA= new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.singleitem,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {//filling the data to holders
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setPrice(model.getPrice());

                final String food_key =getRef(position).getKey().toString();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//go to choosing  quantity activity
                        Intent i=new Intent(OrderFood.this,AddFoodOrder.class);
                        i.putExtra("foodID",food_key);
                        startActivity(i);

                    }
                });
            }
        };
        mFoodList.setAdapter(FBRA);

    }
    public void OS(View view) {
        Category="Sheesha";
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Sheesha");
        mAuth.addAuthStateListener(mAuthListener);
//adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        FirebaseRecyclerAdapter<Food,FoodViewHolder> FBRA= new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.singleitem,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {//filling the data to holders
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
                viewHolder.setPrice(model.getPrice());

                final String food_key =getRef(position).getKey().toString();


                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {//go to choosing  quantity activity
                        Intent i=new Intent(OrderFood.this,AddFoodOrder.class);
                        i.putExtra("foodID",food_key);
                        startActivity(i);

                    }
                });
            }
        };
        mFoodList.setAdapter(FBRA);

    }


        public static class FoodViewHolder extends RecyclerView.ViewHolder{// view holder class adaptation

        View mView;

        public FoodViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void setImage(Context ctx,String image) {
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




    public void HideKeyboard(View view) {// hide keyboard

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mgr.isAcceptingText()) {
            mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void finishorderItems(View view)
    {



        if(TextUtils.isEmpty(roomNumber))
        {

//Reset and the room number
            orderedItemsQuantity="";
            orderedItems="";
            roomNumber=null;

            OrderFood.total=00;
            Toast.makeText(getApplication(), "Please select items to add order ...", Toast.LENGTH_SHORT).show();
            return;

        }
        else {
            Toast.makeText(getApplication(), "Please Wait Your Order is Being Added ...", Toast.LENGTH_SHORT).show();
            final DatabaseReference newOrder = mRef.push();
            userData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {// insert order to db
                    newOrder.child("itemname").setValue(orderedItems);
                    newOrder.child("quantity").setValue(orderedItemsQuantity);
                    newOrder.child("room").setValue(roomNumber);

                    newOrder.child("userphone").setValue(dataSnapshot.child("PhoneNumber").getValue());
                    newOrder.child("useremail").setValue(dataSnapshot.child("Email").getValue());
                    newOrder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {


                            orderedItemsQuantity = "";
                            orderedItems = "";

                            OrderFood.total=00;
                            roomNumber = null;
                            Toast.makeText(getApplication(), "Your Order Was Added Successfully", Toast.LENGTH_SHORT).show();
                            TextView totalp=(TextView) findViewById(R.id.totalprice);
                            totalp.setText(" Total : " + total +" LE");

                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }


    }
    public void BackFromOrder(View view) {

        orderedItemsQuantity="";
        orderedItems="";
        roomNumber=null;

        OrderFood.total=00;
        Intent i = new Intent(OrderFood.this, Home.class);
        startActivity(i);
        finish();
    }

}

