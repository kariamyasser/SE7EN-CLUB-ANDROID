package com.example.passe.se7en;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class ConnectUs extends AppCompatActivity {


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
        setContentView(R.layout.activity_connect_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConnectUs.this, Home.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void goTOFB(View view) {

        Intent i = getOpenFacebookIntent(ConnectUs.this);
        startActivity(i);
    }

    public void goTOphone(View view) {


        Intent callIntent = new Intent(Intent.ACTION_DIAL);
         callIntent.setData(Uri.parse("tel:" + "01289778216"));
        startActivity(callIntent);
    }




    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/385589724927967"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Seven-club-playstation-billiards-pingpong-385589724927967/"));
        }
    }

    public void goToContact2(View view) {

        Intent i = new Intent(ConnectUs.this, MapsActivity.class);
        startActivity(i);
    }



}

