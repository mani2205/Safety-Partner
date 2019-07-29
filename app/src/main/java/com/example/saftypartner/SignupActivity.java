package com.example.saftypartner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText email,password,confirmPassword;
    private Button btnSignup,backtologin;
    private FirebaseAuth mAuth;
    private DatabaseReference reff;
    UserProfile obj1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email=findViewById(R.id.EmailSignup);
        password=findViewById(R.id.PasswordSignup);
        confirmPassword=findViewById(R.id.ConfirmPasswordSignup);
        btnSignup=findViewById(R.id.signUp);
        backtologin=findViewById(R.id.backtoLogin);
        obj1=new UserProfile();
        mAuth = FirebaseAuth.getInstance();


        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignupActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailS=email.getText().toString().trim();
                String passwordS=password.getText().toString().trim();
                String ConfirmPasswordS=confirmPassword.getText().toString();

                if(emailS.isEmpty())
                {
                    email.setError("Enter email address !");
                    email.requestFocus();
                    return;
                }
                if(passwordS.isEmpty())
                {
                    password.setError("Enter password !");
                    password.requestFocus();
                    return;
                }
                if(passwordS.length()<6)
                {
                    password.setError("Password is to short !");
                    password.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailS).matches())
                {
                    email.setError("Enter a valid email address !");
                    email.requestFocus();
                    return;
                }
                if(!ConfirmPasswordS.equals(passwordS))
                {
                    confirmPassword.setError("Password & Confirm password cannot same !");
                    confirmPassword.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailS, passwordS)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Bundle b=new Bundle();
                                    b.putString("email",emailS);
                                    Intent intent=new Intent(SignupActivity.this,UserInfo.class);
                                    intent.putExtras(b);
                                    Toast.makeText(SignupActivity.this,"Registration sucessful",Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                    email.setText("");
                                    password.setText("");
                                    confirmPassword.setText("");

                                } else {
                                    Toast.makeText(SignupActivity.this,"You are alredy registerd, Goto Login",Toast.LENGTH_LONG).show();
                                }

                                // ...
                            }
                        });
            }
        });
    }
}
