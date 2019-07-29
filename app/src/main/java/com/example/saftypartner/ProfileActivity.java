package com.example.saftypartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity
{
    private TextView name,phone,email,contact1,contact2,contact3,editProfile,back;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    UserProfile obj;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.NameTV);
        phone=findViewById(R.id.PhoneTv);
        email=findViewById(R.id.EmailTv);
        contact1=findViewById(R.id.contact1Tv);
        contact2=findViewById(R.id.contact2TV);
        contact3=findViewById(R.id.contact3Tv);
        editProfile=findViewById(R.id.EditProfile);
        back=findViewById(R.id.Back);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
       String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference reff=firebaseDatabase.getReference().child("Users").child(user_id);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                obj=dataSnapshot.getValue(UserProfile.class);
                name.setText("Name : "+obj.getName_db());
                phone.setText("Phone No : "+obj.getPhone_db());
                email.setText("Email : "+obj.getEmail_db());
                contact1.setText("Contact 1 : "+obj.getContact1_db());
                contact2.setText("Contact 2 : "+obj.getContact2_db());
                contact3.setText("Contact 3 : "+obj.getContact3_db());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,UpdateProfile.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,LocationShare.class));
            }
        });
    }
}
