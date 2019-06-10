package com.example.passe.se7en;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Addfood extends AppCompatActivity {

    private EditText fname;
    private EditText fprice;
   private ImageButton imageButton;
    private static final int Gallery=1;
    private FloatingActionButton f;
    private Uri uri=null;
    private StorageReference storageReference=null;
    private DatabaseReference mRef;
    private FirebaseDatabase firebaseDatabase;
    private FloatingActionButton DB,FB,SB ;
    private TextView DT,FT,ST;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//Initializations
        fname =(EditText) findViewById(R.id.foodname);
        fprice =(EditText) findViewById(R.id.foodprice);
        imageButton=(ImageButton) findViewById(R.id.imageButton);
        f=(FloatingActionButton) findViewById(R.id.floatingActionButton);


        DB=(FloatingActionButton) findViewById(R.id.DrinksB);
        FB=(FloatingActionButton) findViewById(R.id.FoodB);
        SB=(FloatingActionButton) findViewById(R.id.SheeshaB);
        DT=(TextView) findViewById(R.id.Drinks);
        FT=(TextView) findViewById(R.id.Food);
        ST=(TextView) findViewById(R.id.Sheesha);
//References to Firebase
        storageReference= FirebaseStorage.getInstance().getReference();
       // mRef=FirebaseDatabase.getInstance().getReference("FoodItem");
        mRef=FirebaseDatabase.getInstance().getReference("FoodItem");
        f.setEnabled(false);

        DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef=FirebaseDatabase.getInstance().getReference("Drinks");

                DB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                FB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                SB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));

                f.setEnabled(true);
            }
        });

        DT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef=FirebaseDatabase.getInstance().getReference("Drinks");

                DB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                FB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                SB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));

                f.setEnabled(true);
            }
        });




        FB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef=FirebaseDatabase.getInstance().getReference("FoodItem");

                FB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                DB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                SB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));

                f.setEnabled(true);
            }
        });

        FT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef=FirebaseDatabase.getInstance().getReference("FoodItem");

                FB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                DB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                SB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));

                f.setEnabled(true);
            }
        });


        SB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef=FirebaseDatabase.getInstance().getReference("Sheesha");

                FB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                DB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                SB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

                f.setEnabled(true);
            }
        });

        ST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef=FirebaseDatabase.getInstance().getReference("Sheesha");

                FB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                DB.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));
                SB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

                f.setEnabled(true);
            }
        });




        f.setOnClickListener(new View.OnClickListener() {  //when  Add button clicked this inner function is called to add the new food
                                                            //item and check for credentials
            @Override
            public void onClick(View view) {

                f.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Please Wait !!! \n the item is being added", Toast.LENGTH_SHORT).show();
             final String n=fname.getText().toString().trim();
                final String p=fprice.getText().toString().trim();
                if(TextUtils.isEmpty(n)||TextUtils.isEmpty(p)) //check no empty fields
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Name and Price " , Toast.LENGTH_SHORT).show();

                }
                else
                {
                    try {
                            //get reference to Database and start adding the food item attributes to the firebase

                        StorageReference file = storageReference.child(uri.getLastPathSegment()); //add image to firebase online storage and return link
                        file.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                @SuppressWarnings("VisibleForTests") final Uri downloadurl = taskSnapshot.getDownloadUrl();
                                final DatabaseReference newPost = mRef.push();
                                newPost.child("name").setValue(n);
                                newPost.child("price").setValue(p);
                                newPost.child("image").setValue(downloadurl.toString());
                                Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), " Failed! \n Please Check Your Connection", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    catch(Exception e){
                        //No  Picture added
                    Toast.makeText(getApplicationContext(), "Please Add a Picture", Toast.LENGTH_SHORT).show();

                }

                }

                f.setEnabled(true);

            }

        });




        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.deleteFoodItem);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//return to main home of admin
                Intent i=new Intent(Addfood.this,DeleteFood.class);
                startActivity(i);




            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//return to main home of admin
                    Intent intent=new Intent(Addfood.this,HomeAdmin.class);
                  startActivity(intent);
                finish();
            }
        });
    }


    public void setImageButton(View view) {
        //access phone gallery to get image with a new activity
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Gallery);

//Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
 //       galleryIntent.setType("Image/*");
  //      startActivityForResult(galleryIntent,Gallery);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//get the image from phone
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery && resultCode==RESULT_OK)
        {
            uri =data.getData();
            imageButton.setImageURI(uri);
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

    @Override
    public void onPause() {

        super.onPause();

        if(FirebaseDatabase.getInstance()!=null)
        {
            FirebaseDatabase.getInstance().goOffline();
        }
    }
}
