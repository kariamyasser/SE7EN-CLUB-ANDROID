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

public class ViewRequest extends AppCompatActivity {



    private RecyclerView mRequestList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private DatabaseReference firebase;
    private DatabaseReference mDatabase2;
    private  static String EM="";
    private DatabaseReference responseRef;
    private DatabaseReference reservationRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//initializations
        mRequestList = (RecyclerView) findViewById(R.id.requestsRecyclerview);
        mRequestList.setHasFixedSize(true);
        mRequestList.setLayoutManager(new LinearLayoutManager(this));
//database references
        mDatabase = FirebaseDatabase.getInstance().getReference().child("requests");
        firebase = FirebaseDatabase.getInstance().getReference();
      responseRef = FirebaseDatabase.getInstance().getReference().child("response");
        reservationRef = FirebaseDatabase.getInstance().getReference().child("reservation");




        mAuth =FirebaseAuth.getInstance();

        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null)//check if no signed in user
                {
                    Intent i=new Intent(ViewRequest.this,Login.class);
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

                Intent i = new Intent(ViewRequest.this, HomeAdmin.class);
                startActivity(i);
                            finish();
            }
        });


    }






    @Override
    protected void onStart() {
        super.onStart();
EM="";
        //adapting the recyclerview together with the cardview and the coordinator layout to view items  using  holders
        mAuth.addAuthStateListener(mAuthListener);
        try {

            FirebaseRecyclerAdapter<Request,ViewRequest.RequestViewHolder> FBRA = new FirebaseRecyclerAdapter<Request,ViewRequest.RequestViewHolder>(
                    Request.class,
                    R.layout.singlerequest,
                    ViewRequest.RequestViewHolder.class,
                    mDatabase)

            {
                //filling data
                @Override
                protected void populateViewHolder(final ViewRequest.RequestViewHolder viewHolder, Request model, int position) {
//upload to database
                    viewHolder.setRUserName(model.getUsername());
                    viewHolder.setType(model.getType());
                    viewHolder.setdate(model.getDate());
                    viewHolder.settime(model.getTime());
                    viewHolder.setRUserPhone(model.getUserphone());
                    viewHolder.setEmail(model.getUseremail());
                    viewHolder.setNumber(model.getNumber());
                    viewHolder.setRoom(model.getRoom());
                    viewHolder.setGame(model.getGame());
                    final String requestkey =getRef(position).getKey().toString();


                    viewHolder.V.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            AlertDialog.Builder builder = new AlertDialog.Builder(ViewRequest.this);

                       final String email=   viewHolder.getEmail().toString();
                            final String UN = viewHolder.getRUserName().toString();
                            final String UP =viewHolder.getUserPhone().toString();
                            final String date=viewHolder.getdate().toString();
                            final String time= viewHolder.gettime().toString();
                            final String type=viewHolder.getType().toString();
                            final String number=viewHolder.getNumber().toString();
                            final String room=viewHolder.getRoom().toString();
                            final String game=viewHolder.getGame().toString();
                            EM=email;

                            builder.setTitle("What do you want to do?");
                            builder.setIcon(R.drawable.infinite);

                            final EditText input = new EditText(ViewRequest.this);
                            input.setHint(" Enter Note ");
                            builder.setView(input);




                            builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    // Accept the request

                                    String n =input.getText().toString().trim();
                                    if (TextUtils.isEmpty(n) ) {
                                       n= " no notes";
                                    }
                                    final String Note= n;
                                    final DatabaseReference newOrder  =responseRef.child(EM).push();
                                    newOrder.child("respond").setValue("Accepted");
                                    newOrder.child("time").setValue(time);
                                    newOrder.child("date").setValue(date);
                                    newOrder.child("type").setValue(type);
                                    newOrder.child("note").setValue("Note : "+Note);
                                    newOrder.child("room").setValue(room);
                                    newOrder.child("number").setValue(number);
                                    newOrder.child("game").setValue(game);
                                    final DatabaseReference newOrder2 = reservationRef.push();
                                    newOrder2.child("userphone").setValue(UP);
                                    newOrder2.child("id").setValue(EM);
                                    newOrder2.child("time").setValue(time);
                                    newOrder2.child("date").setValue(date);
                                    newOrder2.child("type").setValue(type);
                                    newOrder2.child("username").setValue(UN);
                                    newOrder2.child("note").setValue("Note : "+Note);
                                    newOrder2.child("room").setValue(room);
                                    newOrder2.child("number").setValue(number);
                                    newOrder2.child("game").setValue(game);

                                    mDatabase.child(requestkey).removeValue();

                                    dialog.dismiss();
                                }
                            });

                            builder.setNegativeButton("Reject", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String n =input.getText().toString().trim();
                                    if (TextUtils.isEmpty(n) ) {
                                        n= " no notes";
                                    }
                                    final String Note= n;
//reject the request and add a response
                                    final DatabaseReference newOrder  =responseRef.child(EM).push();
                                    newOrder.child("respond").setValue("Rejected");
                                    newOrder.child("time").setValue(time);
                                    newOrder.child("date").setValue(date);
                                    newOrder.child("type").setValue(type);
                                    newOrder.child("note").setValue("Note : "+Note);
                                    newOrder.child("room").setValue(room);
                                    newOrder.child("number").setValue(number);
                                    newOrder.child("game").setValue(game);
                                    mDatabase.child(requestkey).removeValue();//remove request
                                    dialog.dismiss();
                                }
                            });

                            builder.setNeutralButton("Cancel",new DialogInterface.OnClickListener()
                            {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // Do nothing
                                    dialog.dismiss();
                                }
                            });


                            AlertDialog alert = builder.create();
                            alert.show();


                        }
                    });

                }

            };
            mRequestList.setAdapter(FBRA);

        }
        catch (Exception e)
        {}
    }
//class holder adaptation

    public static class RequestViewHolder extends RecyclerView.ViewHolder{

        View V;


        public RequestViewHolder(View itemView) {
            super(itemView);
            V=itemView;
        }

        public void setEmail(String email) {
            TextView username = (TextView) V.findViewById(R.id.requestemail);
            username.setText(email);
        }

        public String getEmail() {
            TextView username = (TextView) V.findViewById(R.id.requestemail);
            return username.getText().toString();
        }



        public void setGame(String email) {
            TextView username = (TextView) V.findViewById(R.id.requestgame);
            username.setText("Game :"+email);
        }

        public String getGame() {
            TextView username = (TextView) V.findViewById(R.id.requestgame);
            return username.getText().toString();
        }

        public void setRUserName(String name) {
            TextView username = (TextView) V.findViewById(R.id.requestCustomerName);
            username.setText(name);
        }
        public String getRUserName() {
            TextView username = (TextView) V.findViewById(R.id.requestCustomerName);
            return username.getText().toString();
        }


        public void settime(String price) {
            TextView t = (TextView) V.findViewById(R.id.reqquesttime);
            t.setText("Time : "+price);

        }

        public void setNumber(String price) {
            TextView t = (TextView) V.findViewById(R.id.requestNumber);
            t.setText("Number of Persons : "+price);


        }
        public void setRoom(String price) {
            TextView t = (TextView) V.findViewById(R.id.requestRoom);
            t.setText(price);


        }

        public String gettime() {
            TextView username = (TextView) V.findViewById(R.id.reqquesttime);
            return username.getText().toString();
        }

        public void setType(String itemName) {
            TextView t = (TextView) V.findViewById(R.id.requestType);
            t.setText("Type : "+itemName);
        }

        public String getType() {
            TextView username = (TextView) V.findViewById(R.id.requestType);
            return username.getText().toString();
        }

        public void setRUserPhone(String userPhone) {
            TextView phone = (TextView) V.findViewById(R.id.requestCustomerNumber);
            phone.setText(userPhone);
        }
        public String getUserPhone() {
            TextView username = (TextView) V.findViewById(R.id.requestCustomerNumber);
            return username.getText().toString();
        }



        public void setdate(String s) {

            TextView U = (TextView) V.findViewById(R.id.requestdate);
            U.setText("Date : "+s);

        }


        public String getdate() {
            TextView username = (TextView) V.findViewById(R.id.requestdate);
            return username.getText().toString();
        }

        public String getRoom() {
            TextView username = (TextView) V.findViewById(R.id.requestRoom);
            return username.getText().toString();
        }
        public String getNumber() {
            TextView username = (TextView) V.findViewById(R.id.requestNumber);
            return username.getText().toString();
        }
    }


    public void HideKeyboard(View view) {//hide keyboard

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mgr.isAcceptingText()) {
            mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
