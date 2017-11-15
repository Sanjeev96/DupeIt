package com.dhillon.dupeitv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //declare variables
    private EditText username_et,forename_et, surname_et, email_et,pass_et;
    private Button regbtn;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //get firebase auth instsnce

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        //link variables to front end
        username_et = (EditText) findViewById(R.id.etUsername);
        forename_et = (EditText)findViewById(R.id.etForename);
        surname_et = (EditText)findViewById(R.id.etSurname);
        email_et = (EditText)findViewById(R.id.etEmail);
        pass_et = (EditText)findViewById(R.id.etPass);
        regbtn = (Button)findViewById(R.id.btnReg);

        //method that performs
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = username_et.getText().toString().trim();
                final String forename = forename_et.getText().toString().trim();
                final String surname = surname_et.getText().toString().trim();
                String email = email_et.getText().toString().trim();
                String password = pass_et.getText().toString().trim();

                if(username_et.getText().toString().trim().equals(""))
                {
                    username_et.setError("Enter Username");
                }

                if( forename_et.getText().toString().trim().equals(""))
                {
                    forename_et.setError("Enter Forename");
                    return;
                }
                if( surname_et.getText().toString().trim().equals(""))
                {
                    surname_et.setError("Enter Surname");
                    return;
                }
                if( email_et.getText().toString().trim().equals(""))
                {
                    email_et.setError("Enter Email");
                    return;
                }
                 if( pass_et.getText().toString().trim().equals(""))
                {
                    pass_et.setError("Enter Password");
                    return;
                }



                progressDialog.setMessage("Registering, please wait..");
                progressDialog.show();
                //creates user
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Registration Successful" , Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, MainLoginActivity.class));
                                    finish();


                                    //Saves personal data of registering user
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                                    DatabaseReference currentUserDB = mDatabase.child(auth.getCurrentUser().getUid()); //Access current folder for user data
                                    currentUserDB.child("username").setValue(username);
                                    currentUserDB.child("forename").setValue(forename);
                                    currentUserDB.child("surename").setValue(surname);
                                    currentUserDB.child("profilePic").setValue("Default");


                                } else{
                                    progressDialog.hide();
                                    Toast.makeText(RegisterActivity.this, "Error Registration failed  " , Toast.LENGTH_SHORT).show();

                                }


                            }
                        });


            }
        });


        }


    }

