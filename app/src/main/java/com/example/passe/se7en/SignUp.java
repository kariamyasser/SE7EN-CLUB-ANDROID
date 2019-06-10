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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {
    private EditText inputEmail, inputPassword,inname,inPhone;

    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        final FloatingActionButton signupBT = (FloatingActionButton) findViewById(R.id.signup);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inname = (EditText) findViewById(R.id.name);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        inPhone = (EditText) findViewById(R.id.Phone);



        signupBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupBT.setEnabled(false);
//get inputs
                final String email = inputEmail.getText().toString().toLowerCase().trim();
                 String password = inputPassword.getText().toString().trim();
                final String name = inname.getText().toString().trim();
                final String phoneNumber = inPhone.getText().toString().trim();
//validations
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please Enter email address!", Toast.LENGTH_SHORT).show();
                    signupBT.setEnabled(true);
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Full Name!", Toast.LENGTH_SHORT).show();
                    signupBT.setEnabled(true);
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Phone Number!", Toast.LENGTH_SHORT).show();
                    signupBT.setEnabled(true);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please Enter password!", Toast.LENGTH_SHORT).show();
                    signupBT.setEnabled(true);
                    return;
                }

                if (!isEmailValid(email)) {
                    Toast.makeText(getApplicationContext(), "Please Enter correct Email Address", Toast.LENGTH_SHORT).show();
                    signupBT.setEnabled(true);
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    signupBT.setEnabled(true);
                    return;
                }

                if (phoneNumber.length() < 11 || phoneNumber.length() > 11) {
                    Toast.makeText(getApplicationContext(), "Please Enter correct phone Number", Toast.LENGTH_SHORT).show();
                    signupBT.setEnabled(true);
                    return;
                }

                if (name.contains("0") ||name.contains("1") ||name.contains("2") ||name.contains("3") ||name.contains("4") ||name.contains("5") ||name.contains("6") ||name.contains("7")
                        ||name.contains("7") ||name.contains("8") ||name.contains("9")) {
                    Toast.makeText(getApplicationContext(), "Please Enter correct name", Toast.LENGTH_SHORT).show();
                    signupBT.setEnabled(true);
                    return;
                }


                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." , Toast.LENGTH_SHORT).show();
                                } else {

                                    String user_id=auth.getCurrentUser().getUid();
                                    DatabaseReference current_user=mDatabase.child(user_id);
                                    current_user.child("Email").setValue(email);
                                    current_user.child("Name").setValue(name);
                                    current_user.child("type").setValue("x");
                                    current_user.child("PhoneNumber").setValue(phoneNumber);
                                    Toast.makeText(SignUp.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                    sendEmailVerification();
                                    startActivity(new Intent(SignUp.this, Login.class));
                                    finish();
                                }
                            }
                        });
                signupBT.setEnabled(true);

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUp.this, Login.class));
                finish();

            }
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void sendEmailVerification() {
        //Verify Email

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
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (FirebaseDatabase.getInstance() != null)
        {
            FirebaseDatabase.getInstance().goOnline();
        }
        progressBar.setVisibility(View.GONE);
    }


    public void HideKeyboard(View view) {//hide keyboard

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mgr.isAcceptingText()) {
            mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
