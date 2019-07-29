package com.example.saftypartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfo extends AppCompatActivity {

    private EditText name,phone,contact1,contact2,contact3;
    private Button finish;
    private FirebaseAuth mAuth;
    private DatabaseReference reff;
    UserProfile obj;
    Bundle recive;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        recive=this.getIntent().getExtras();

        name=findViewById(R.id.nameSignup);
        phone=findViewById(R.id.phoneSigup);
        contact1=findViewById(R.id.contact1);
        contact2=findViewById(R.id.contact2);
        contact3=findViewById(R.id.contact3);
        obj=new UserProfile();
        mAuth = FirebaseAuth.getInstance();

        finish=findViewById(R.id.btnFinish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String nameS=name.getText().toString().trim();
                 String phoneS=phone.getText().toString().trim();
                 String contact1S=contact1.getText().toString().trim();
                 String contact2S=contact2.getText().toString().trim();
                 String contact3S=contact3.getText().toString().trim();
                 String emailS=recive.getString("email");

                if(nameS.isEmpty())
                 {
                     name.setError("Name is empty !");
                     name.requestFocus();
                     return;
                 }
                if(phoneS.isEmpty())
                {
                    phone.setError("Name is empty !");
                    phone.requestFocus();
                    return;
                }
                if(contact1S.isEmpty())
                {
                    contact1.setError("Name is empty !");
                    contact1.requestFocus();
                    return;
                }
                if(contact2S.isEmpty())
                {
                    contact2.setError("Name is empty !");
                    contact2.requestFocus();
                    return;
                }
                if(contact3S.isEmpty())
                {
                    contact3.setError("Name is empty !");
                    contact3.requestFocus();
                    return;
                }
                String user_id = mAuth.getCurrentUser().getUid().toString();
                reff = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                obj.setName_db(nameS);
                obj.setPhone_db(phoneS);
                obj.setEmail_db(emailS);
                obj.setContact1_db(contact1S);
                obj.setContact2_db(contact2S);
                obj.setContact3_db(contact3S);

                reff.setValue(obj);
                Toast.makeText(UserInfo.this,"Data added successfully.",Toast.LENGTH_LONG).show();
                startActivity(new Intent(UserInfo.this,MainActivity.class));
                name.setText("");
                phone.setText("");
                contact1.setText("");
                contact2.setText("");
                contact3.setText("");


            }
        });

    }
}
