package com.example.passe.se7en;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Login extends AppCompatActivity {


    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;

   private TextView reset ;
    private EditText emailET;
    private EditText passwordET;
    private FloatingActionButton f;


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

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//Initializations
       reset =(TextView) findViewById(R.id.reset);
        emailET =(EditText) findViewById(R.id.email);
        passwordET =(EditText) findViewById(R.id.password);
        f = (FloatingActionButton) findViewById(R.id.login);


        //int badgeCount=1;
        //ShortcutBadger.applyCount(Login.this,1);
        final ProgressDialog dialog=new ProgressDialog(Login.this);
        dialog.setMessage("Signing In . . .");

        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        if(!isNetworkAvailable())
        {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Please Check internet connection", Toast.LENGTH_SHORT).show();

        }


//get firebase instance
        auth = FirebaseAuth.getInstance();


//check if there is an already signed in user
        if (auth.getCurrentUser() != null) {

            DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("type");


            mDatabase1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String type = dataSnapshot.getValue().toString();


                    if (type.equals("Admin"))
                    {
                        if(auth.getCurrentUser().isEmailVerified())
                        {

                            Intent i = new Intent(Login.this, HomeAdmin.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();

                        }
                        else
                        {

                            dialog.hide();
                        }


                    }
                    else {
                        if(auth.getCurrentUser().isEmailVerified())
                        {

                            Intent i = new Intent(Login.this, Home.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);

                            finish();
                        }
                        else
                        {

                            dialog.hide();
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }

else{
            dialog.hide();
        }







/*


        ProgressBar spinner = new android.widget.ProgressBar(
                Login.this,
                null,
                android.R.attr.progressBarStyle);

        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
*/


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to reset password activity
                startActivity(new Intent(Login.this, ResetPassword.class));
            }
        });

        //login

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                login();

                          }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//go to signup activity

                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);


            }
        });
    }

    private boolean isNetworkAvailable() {
       try {
           ConnectivityManager connectivityManager
                   = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
           NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
           return activeNetworkInfo != null && activeNetworkInfo.isAvailable() &&
                   activeNetworkInfo.isConnectedOrConnecting();
       }
       catch (Exception e)
       {
           return  false;
       }
    }//check if there is a network connection available

    private void sendEmailVerification() {

        final FirebaseUser user = auth.getCurrentUser();
        Toast.makeText(getApplicationContext(), "please confirm the following email " + user.getEmail(), Toast.LENGTH_SHORT).show();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Re-enable Verify Email button


                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getApplicationContext(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }// send a confirmation email to verify user account


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }



    public void login()//login
    {


        final FloatingActionButton f =(FloatingActionButton)  findViewById(R.id.login);
        f.setEnabled(false);

        if(!isNetworkAvailable())//check connection
        {
            Toast.makeText(getApplicationContext(), "Please check your connection", Toast.LENGTH_SHORT).show();
            f.setEnabled(true);
            return;
        }
//get inputs
        final String email = emailET.getText().toString();
        final String password = passwordET.getText().toString();
//validate inputs
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            f.setEnabled(true);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            f.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isEmailValid(email)) {
            Toast.makeText(getApplicationContext(), "Please Enter correct Email Address", Toast.LENGTH_SHORT).show();
            f.setEnabled(true);
            return;
        }
//signin

        Toast.makeText(getApplicationContext(), "Signing In, Please wait ...", Toast.LENGTH_SHORT).show();
        final ProgressDialog dialog2;
       dialog2=new ProgressDialog(Login.this);
        dialog2.setMessage("Signing in . . .");

        dialog2.setCancelable(false);
        dialog2.setInverseBackgroundForced(false);
        dialog2.show();

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            // there was an error
                            dialog2.dismiss();
                            Toast.makeText(Login.this, "Please enter correct email and password", Toast.LENGTH_LONG).show();
                        }
                        else {


                           DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid()).child("type");


                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String type = dataSnapshot.getValue().toString();


                                    if (type.equals("Admin"))
                                    {
                                        if(auth.getCurrentUser().isEmailVerified())
                                        {

                                            Intent i = new Intent(Login.this, HomeAdmin.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i);
                                            finish();
                                        }
                                        else
                                        {
                                            sendEmailVerification();
                                        }


                                    }
                                    else {
                                        if(auth.getCurrentUser().isEmailVerified())
                                        {

                                            Intent i = new Intent(Login.this, Home.class);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i);

                                            finish();
                                        }
                                        else
                                        {
                                            sendEmailVerification();
                                        }

                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                       }
                            });



                        }
                    }
                });

        dialog2.dismiss();

       f.setEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
public void HideKeyboard(View view)//hide keyboard on touch layout
{

    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
if(mgr.isAcceptingText()) {
    mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
}
}

    @Override
    protected void onStart() {
        super.onStart();





    }
}


