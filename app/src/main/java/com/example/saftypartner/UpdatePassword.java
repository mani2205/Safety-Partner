package com.example.saftypartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

    private EditText password,confirmPassword;
    Button updatePassword;
    String passwordS,confirmPasswordS;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        password=findViewById(R.id.newPassword);
        confirmPassword=findViewById(R.id.confirmNewPassword);
        updatePassword=findViewById(R.id.updatePassword);

        firebaseUser = firebaseAuth.getInstance().getCurrentUser();

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordS=password.getText().toString().trim();
                confirmPasswordS=confirmPassword.getText().toString().trim();

                if(passwordS.isEmpty())
                {
                    password.setError("Password is empty !");
                    password.requestFocus();
                    return;
                }
                if(confirmPasswordS.isEmpty())
                {
                    confirmPassword.setError("Confirm password is empty !");
                    confirmPassword.requestFocus();
                    return;
                }
                if(!passwordS.equals(confirmPasswordS))
                {
                    confirmPassword.setError("Password and confirm password must be same !");
                    confirmPassword.requestFocus();
                    return;
                }


                firebaseUser.updatePassword(passwordS).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdatePassword.this,"Password update sucessfull !",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UpdatePassword.this,MainActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(UpdatePassword.this,"Password update Failed !",Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });

    }
}
