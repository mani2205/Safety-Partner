package com.example.saftypartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText email,password;
    private Button button_signup,button_login;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    TextView forgotPassword;

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this).setIcon(R.drawable.exit).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_signup=findViewById(R.id.signupBtn);
        button_login=findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.Password);
        forgotPassword=findViewById(R.id.forgotPassword);
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {
            finish();
            startActivity( new Intent(MainActivity.this, LocationShare.class));
            email.setText("");
            password.setText("");
        }
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String emailS=email.getText().toString().trim();
                 String passwordS=password.getText().toString().trim();
                 if(emailS.isEmpty())
                 {
                     email.setError("Email is empty");
                     email.requestFocus();
                     return;
                 }
                 if(passwordS.isEmpty())
                 {
                     password.setError("Password is empty");
                     password.requestFocus();
                     return;
                 }
                mAuth.signInWithEmailAndPassword(emailS, passwordS)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(MainActivity.this,"Welcome.",Toast.LENGTH_LONG).show();
                                    startActivity( new Intent(MainActivity.this, LocationShare.class));
                                    email.setText("");
                                    password.setText("");

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this,"Incorrect user name or password.",Toast.LENGTH_LONG).show();
                                }

                                // ...
                            }
                        });

            }
        });
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            }
        });

    }
}
