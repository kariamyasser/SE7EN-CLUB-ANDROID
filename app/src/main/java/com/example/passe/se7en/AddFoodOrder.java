package com.example.passe.se7en;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class AddFoodOrder extends AppCompatActivity {

    private String food_key = null;

    private TextView foodTitle, foodPrice;
    private EditText quantity;
    private ImageView foodImage;
    private FirebaseAuth mauth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase, userData, mRef;
    private FirebaseUser current_user;
    private String fName;
    private String fprice;
    private String fimage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//checks to remove the reservation id edittext
        TextView e = (TextView) findViewById(R.id.roomNumber);


        if (TextUtils.isEmpty(OrderFood.roomNumber)) {
          e.setVisibility(View.VISIBLE);
        }
        else{
            e.setVisibility(View.INVISIBLE);
        }

//INITIALIZATIONS

        food_key = getIntent().getExtras().getString("foodID");


        foodTitle = (TextView) findViewById(R.id.foodname);
        foodPrice = (TextView) findViewById(R.id.priceO);
        foodImage = (ImageView) findViewById(R.id.foodimageO);
        quantity = (EditText) findViewById(R.id.quantity);

        //Getting References to database
        database = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();

        current_user = mauth.getCurrentUser();


        userData = database.getReference().child("Users").child(current_user.getUid());

        mRef = database.getReference().child("Orders");



        if(OrderFood.Category.equals("Drinks")) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Drinks").child(food_key);

        }
        else if(OrderFood.Category.equals("Sheesha")) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Sheesha").child(food_key);

        }
        else
        {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("FoodItem").child(food_key);

        }

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//getting the food item to be added to order from Database

                    fName = (String) dataSnapshot.child("name").getValue();
                    foodTitle.setText(fName);
                    fprice = (String) dataSnapshot.child("price").getValue();
                    fimage = (String) dataSnapshot.child("image").getValue();
                    foodPrice.setText(fprice + " L.E.");

                    Picasso.with(AddFoodOrder.this).load(fimage).into(foodImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//going to previous activity
                Intent intent = new Intent(AddFoodOrder.this, OrderFood.class);
                  startActivity(intent);
                finish();
            }
        });
    }


    public void orderItem_clicked(View view) {

   // adding the food item to order and checking for inputs

        FloatingActionButton f =(FloatingActionButton) findViewById(R.id.order);

        f.setEnabled(false);
        TextView e = (TextView) findViewById(R.id.roomNumber);

        String room = e.getText().toString();


        if (TextUtils.isEmpty(room)) {

            if (TextUtils.isEmpty(OrderFood.roomNumber)) {
                Toast.makeText(getApplication(), "Please Enter Your Registeration ID", Toast.LENGTH_SHORT).show();
                f.setEnabled(true);
                return;
            }
            room=OrderFood.roomNumber;
        }


        final String Quantity = quantity.getText().toString();


//check for empty fields
        if (TextUtils.isEmpty(Quantity)) {
            Toast.makeText(getApplication(), "Please Enter Quantity", Toast.LENGTH_SHORT).show();
            f.setEnabled(true);
            return;
        }
        else
        {
            if (Integer.parseInt(Quantity) > 20 || Integer.parseInt(Quantity) < 1 ) {
                Toast.makeText(getApplicationContext(), "Please enter correct quantity", Toast.LENGTH_SHORT).show();
                f.setEnabled(true);
                return;
            }
        }
//add to DB
        OrderFood.orderedItemsQuantity = OrderFood.orderedItemsQuantity + Quantity + " Pieces" ;

        fName= wrapString(fName,13);

        OrderFood.orderedItems = OrderFood.orderedItems + fName + "\n";
        OrderFood.orderedItemsQuantity= OrderFood.orderedItemsQuantity +  "\n";
            OrderFood.total =OrderFood.total+(Integer.parseInt(Quantity)* Float.parseFloat(fprice));



        OrderFood.roomNumber = room;

        f.setEnabled(true);

        OrderFood.Category="FoodItem";

        Intent intent = new Intent(AddFoodOrder.this, OrderFood.class);
        startActivity(intent);
        finish();

    }


    @Override
    protected void onStart() {
        super.onStart();

        TextView e = (TextView) findViewById(R.id.roomNumber);

//hiding room number if already entered
        if (TextUtils.isEmpty(OrderFood.roomNumber)) {
            e.setVisibility(View.VISIBLE);
        }
        else{
            e.setVisibility(View.INVISIBLE);
        }


    }


    public void HideKeyboard(View view) {//hide keyboard on touching the layout

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

    public static String wrapString(String string, int charWrap) {
        int lastBreak = 0;
        int nextBreak = charWrap;

        if (string.length() > charWrap) {
            String setString = "";
            do {
                while (string.charAt(nextBreak) != ' ' && nextBreak > lastBreak) {
                    nextBreak--;
                }
                if (nextBreak == lastBreak) {
                    nextBreak = lastBreak + charWrap;
                }

                setString += string.substring(lastBreak, nextBreak).trim() + "\n";
                OrderFood.orderedItemsQuantity= OrderFood.orderedItemsQuantity +  "\n";
                lastBreak = nextBreak;
                nextBreak += charWrap;

            } while (nextBreak < string.length());
            setString += string.substring(lastBreak).trim();
            return setString;
        } else {
            return string;
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









