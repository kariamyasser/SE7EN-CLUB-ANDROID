package com.example.passe.se7en;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewFoodOrders extends AppCompatActivity {


    private RecyclerView mOrderList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//initializations
            mOrderList = (RecyclerView) findViewById(R.id.orderRecyclerview);
            mOrderList.setHasFixedSize(true);
            mOrderList.setLayoutManager(new LinearLayoutManager(this));
//database references
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");


        mAuth =FirebaseAuth.getInstance();

        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null)//check if no signed in user
                {
                    Intent i=new Intent(ViewFoodOrders.this,Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                finish();
                }
            }
        };



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);//go backto home
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(ViewFoodOrders.this, HomeAdmin.class);
                    startActivity(i);
                    finish();

                }
            });


    }



    @Override
    protected void onStart() {
        super.onStart();

        //adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        mAuth.addAuthStateListener(mAuthListener);
        try {
            FirebaseRecyclerAdapter<Order,OrderViewHolder> FBRA = new FirebaseRecyclerAdapter<Order,OrderViewHolder>(
                    Order.class,
                    R.layout.orderitem,
                    OrderViewHolder.class,
                    mDatabase)

            {
            //filling data
                @Override
                protected void populateViewHolder(OrderViewHolder viewHolder, Order model, int position) {

                    viewHolder.setOEmail(model.getUseremail());
                    viewHolder.setOUserName(model.getUsername());
                    viewHolder.setOItemName(model.getItemname());
                    viewHolder.setOQuantity(model.getQuantity());
                    viewHolder.setOUserPhone(model.getUserphone());
                    viewHolder.setOroom(model.getRoom());

                    final String requestkey =getRef(position).getKey().toString();








                    viewHolder.V.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {//go to choosing  quantity activity


                            AlertDialog.Builder builder = new AlertDialog.Builder(ViewFoodOrders.this);


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
            mOrderList.setAdapter(FBRA);

        }
catch (Exception e)
{}
    }
//class holder adaptation

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        View V;

        public OrderViewHolder(View itemView) {
            super(itemView);
            V=itemView;
        }


       public void setOUserName(String name) {
            TextView username = (TextView) V.findViewById(R.id.orderCustomerName);
            username.setText(name);
        }

        public void setOQuantity(String price) {
            TextView foodq = (TextView) V.findViewById(R.id.orderItemQuantity);
            foodq.setText(price);

        }

        public void setOItemName(String itemName) {
            TextView foodname = (TextView) V.findViewById(R.id.OrderItemName);
            foodname.setText(itemName);
        }

        public void setOUserPhone(String userPhone) {
            TextView phone = (TextView) V.findViewById(R.id.orderCustomerNumber);
            phone.setText(userPhone);
        }

        public void setOEmail(String userPhone) {
            TextView Username = (TextView) V.findViewById(R.id.orderCustomerName);
            Username.setText(userPhone);
        }

        public void setOroom(String s) {

            TextView U = (TextView) V.findViewById(R.id.orderCustomerRoom);
            U.setText("Registeration ID : "+s);

        }
    }


    public void HideKeyboard(View view) {//hide keyboard

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
