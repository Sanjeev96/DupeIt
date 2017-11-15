package com.dhillon.dupeitv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainLoginActivity extends AppCompatActivity {

    private EditText email_et, pass_et;
    private  TextView tvRegLink,tvPassForgot;
    private Button login_btn;
    public ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
       // getSupportActionBar().hide()

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

          email_et = (EditText) findViewById(R.id.etEmail);
         pass_et = (EditText) findViewById(R.id.etPass);
        //progressBar = (ProgressBar) findViewById(R.id.progbar);
          tvRegLink = (TextView) findViewById(R.id.tvRegLink);
        tvPassForgot = (TextView) findViewById(R.id.tvPassForgot);
        login_btn = (Button) findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();

        tvRegLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegIntent = new Intent(MainLoginActivity.this, RegisterActivity.class);
                MainLoginActivity.this.startActivity(RegIntent);
            }
        });

        tvPassForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SetIntent = new Intent(MainLoginActivity.this, ResetPass_Activity.class);
                MainLoginActivity.this.startActivity(SetIntent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_et.getText().toString();
               final String password = pass_et.getText().toString();

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
                progressDialog.setMessage("Logging in, please wait..");
                progressDialog.show();

                /*authListener = new FirebaseAuth.AuthStateListener(){
                    @Override
                    public void onAuthStatChange(@NonNull FirebaseAuth firebaseAuth){
                        if(firebaseAuth.getCurrentUser()!=null){
                        Toast.makeText(MainLoginActivity.this, "You are logged in" + firebaseAuth.getCurrentUser().get)
                        }
                    }
                };*/



                // progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()) {
                               progressDialog.dismiss();
                               Intent intent = new Intent(MainLoginActivity.this, UserAreaActivity.class);
                               startActivity(intent);
                               finish();
                           }
                           else{
                               progressDialog.dismiss();
                               Toast.makeText(MainLoginActivity.this, "Error Email or password is incorrect.",Toast.LENGTH_LONG).show();
                           }
                            }
                        });

            }


        });
    }
}
