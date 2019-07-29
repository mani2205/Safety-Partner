package com.example.saftypartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity
{
    EditText updateName,updatPhone,updateContact1,updateContact2,updateContact3;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    Button update;
    UserProfile obj;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        updateName=findViewById(R.id.updateName);
        updatPhone=findViewById(R.id.updatePhone);
        updateContact1=findViewById(R.id.updateContact1);
        updateContact2=findViewById(R.id.updateContact2);
        updateContact3=findViewById(R.id.updateContact3);
        update=findViewById(R.id.btnUpdate);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference reff=firebaseDatabase.getReference().child("Users").child(user_id);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                obj=dataSnapshot.getValue(UserProfile.class);
                updateName.setText(obj.getName_db());
                updatPhone.setText(obj.getPhone_db());
                updateContact1.setText(obj.getContact1_db());
                updateContact2.setText(obj.getContact2_db());
                updateContact3.setText(obj.getContact3_db());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName=updateName.getText().toString().trim();
                String newPhone=updatPhone.getText().toString().trim();
                String newContact1=updateContact1.getText().toString().trim();
                String newContact2=updateContact2.getText().toString().trim();
                String newContact3=updateContact3.getText().toString().trim();

                if(newName.isEmpty())
                {
                    updateName.setError("Name is empty");
                    updateName.requestFocus();
                    return;
                }
                if(newPhone.isEmpty())
                {
                    updatPhone.setError("Phone Number is empty");
                    updatPhone.requestFocus();
                    return;
                }
                if(newContact1.isEmpty())
                {
                    updateContact1.setError("Contact 1 is empty");
                    updateContact1.requestFocus();
                    return;
                }
                if(newContact2.isEmpty())
                {
                    updateContact2.setError("Contact 2 is empty");
                    updateContact2.requestFocus();
                    return;
                }
                if(newContact3.isEmpty())
                {
                    updateContact3.setError("Contact 3 is empty");
                    updateContact3.requestFocus();
                    return;
                }

                String user_id = mAuth.getCurrentUser().getUid().toString();
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                obj.setName_db(newName);
                obj.setPhone_db(newPhone);
                obj.setContact1_db(newContact1);
                obj.setContact2_db(newContact2);
                obj.setContact3_db(newContact3);

                reff.setValue(obj);
                Toast.makeText(UpdateProfile.this,"Data Updated successfully.",Toast.LENGTH_LONG).show();
                startActivity(new Intent(UpdateProfile.this,LocationShare.class));
                updateName.setText("");
                updatPhone.setText("");
                updateContact1.setText("");
                updateContact2.setText("");
                updateContact3.setText("");


            }
        });
    }
}
