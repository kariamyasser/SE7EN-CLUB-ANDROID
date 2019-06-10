package com.example.passe.se7en;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNewGame extends AppCompatActivity {

    private EditText gname;
    private EditText gdesc;
    private ImageButton gimageButton;
    private static final int Gallery=1;
    private FloatingActionButton f;
    private Uri uri=null;
    private StorageReference storageReference=null;
    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//Initializations
        gname =(EditText) findViewById(R.id.gamename);
        gdesc =(EditText) findViewById(R.id.gamedescription);
        gimageButton=(ImageButton) findViewById(R.id.gameimageButton);
        f=(FloatingActionButton) findViewById(R.id.gfloatingActionButton);
//Database References
        storageReference= FirebaseStorage.getInstance().getReference();
        mRef=FirebaseDatabase.getInstance().getReference("Games");



        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f.setEnabled(false);

                final String n=gname.getText().toString().trim();
                 String p=gdesc.getText().toString().trim();

                if(TextUtils.isEmpty(p))
                {
                   p=" ";

                }
                final String d=p;

                if(TextUtils.isEmpty(n))
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Game Name" , Toast.LENGTH_SHORT).show();

                }
                else
                {
                    try {

//add to Firebase
                        StorageReference file = storageReference.child(uri.getLastPathSegment());
                        file.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                @SuppressWarnings("VisibleForTests") final Uri downloadurl = taskSnapshot.getDownloadUrl();
                                //upload picture
                                Toast.makeText(getApplicationContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                final DatabaseReference newPost = mRef.push();
                                newPost.child("name").setValue(n);
                                newPost.child("description").setValue(d);
                                newPost.child("image").setValue(downloadurl.toString());
                                Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Uploading Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Please Add a Picture", Toast.LENGTH_SHORT).show();

                    }
                    f.setEnabled(true);
                }


            }
        });

        //go to delete game activity
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.gotoDeleteGame);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(AddNewGame.this,DeleteGame.class);
                startActivity(i);


            }
        });
//return to home
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(AddNewGame.this,HomeAdmin.class);
                   startActivity(intent);
                finish();
            }
        });

            }


    public void setGImageButton(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Gallery);

        //Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        //       galleryIntent.setType("Image/*");
        //      startActivityForResult(galleryIntent,Gallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery && resultCode==RESULT_OK)
        {
            uri =data.getData();
            gimageButton.setImageURI(uri);
        }
    }



    public void HideKeyboard(View view) {

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
