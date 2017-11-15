package com.dhillon.dupeitv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPass_Activity extends AppCompatActivity {

    private EditText email_et;
    private Button btnSend,btnBack;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_);

        email_et = (EditText) findViewById(R.id.etEmail2);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnBack = (Button) findViewById(R.id.btnBack);

        auth = FirebaseAuth.getInstance();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intentback = new Intent(ResetPass_Activity.this, MainLoginActivity.class);
                ResetPass_Activity.this.startActivity(Intentback);
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString().trim();

                 if( email_et.getText().toString().trim().equals(""))
                {
                    email_et.setError("Enter Email for password reset request");
                    return;
                }

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPass_Activity.this, "We have sent and " +
                                            "email for you to reset your password", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(ResetPass_Activity.this, "ERROR, please enter" +
                                            " a valid email address", Toast.LENGTH_LONG).show();
                                }
                            }

                        });

            }
        });


    }
}
