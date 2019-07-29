package com.example.saftypartner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationShare extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
        private FirebaseAuth mAuth;
        private TextView name_header,email_header;
        UserProfile obj;
        String number1,number2,number3,message;
        FirebaseDatabase firebaseDatabase;
        String currentLocation,lattitude,longitude;
        private static final int REQUEST_LOCATION = 1;
        private static final int REQUEST_SMS = 2;
         Button helpme,callme,justInform;
        LocationManager locationManager;
        SmsManager sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_share);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        sms=SmsManager.getDefault();

        name_header=headerView.findViewById(R.id.name_header);
        email_header=headerView.findViewById(R.id.email_header);
        helpme=findViewById(R.id.redButton);
        callme=findViewById(R.id.orangeButton);
        justInform=findViewById(R.id.greenButton);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference reff=firebaseDatabase.getReference().child("Users").child(user_id);

        //Set a name and email to name and email header.
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                obj=dataSnapshot.getValue(UserProfile.class);
                name_header.setText(obj.getName_db());
                email_header.setText(obj.getEmail_db());
                number1=obj.getContact1_db();
                number2=obj.getContact2_db();
                number3=obj.getContact3_db();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        helpme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                sms=getSystemService(Context.)
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    NoGpsMessage();

                }
                else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    if (ActivityCompat.checkSelfPermission(LocationShare.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED )
                    {
                        ActivityCompat.requestPermissions(LocationShare.this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS);
                    }
                    else {
                        getLocation();
                    }

                }
            }
        });
        callme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message="I'm in Emergency Please call me or text me ";
                sms.sendTextMessage(number1, null, message, null, null);
                Toast.makeText(LocationShare.this,"SMS send successfully. Don't panic defenetly you got an help",Toast.LENGTH_LONG).show();
                sms.sendTextMessage(number2, null, message, null, null);
                sms.sendTextMessage(number3, null, message, null, null);
            }
        });
        justInform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message="Hey I'm just update my status , If you free now Call me. ! "+currentLocation;
                sms.sendTextMessage(number1, null, message, null, null);
                Toast.makeText(LocationShare.this,"SMS send successfully.",Toast.LENGTH_SHORT).show();
                sms.sendTextMessage(number2, null, message, null, null);
                sms.sendTextMessage(number3, null, message, null, null);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(LocationShare.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationShare.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(LocationShare.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
            {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                currentLocation=lattitude+","+longitude;
                message="I'm in dangerous situation Please track my location and find me, my location is:  "+currentLocation;
                sms.sendTextMessage(number1, null, message, null, null);
                Toast.makeText(LocationShare.this,"SMS send successfully. Don't panic defenetly you got an help",Toast.LENGTH_SHORT).show();
                sms.sendTextMessage(number2, null, message, null, null);
                sms.sendTextMessage(number3, null, message, null, null);

            }
            else  if (location1 != null)
            {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                currentLocation=lattitude+","+longitude;
                message="I'm in dangerous situation Please track my location and find me, my location is:  "+currentLocation;
                sms.sendTextMessage(number1, null, message, null, null);
                Toast.makeText(LocationShare.this,"SMS send successfully. Don't panic defenetly you got an help",Toast.LENGTH_SHORT).show();
                sms.sendTextMessage(number2, null, message, null, null);
                sms.sendTextMessage(number3, null, message, null, null);

            }
            else  if (location2 != null)
            {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                currentLocation=lattitude+","+longitude;
                message="I'm in dangerous situation Please track my location and find me, my location is:  "+currentLocation;
                sms.sendTextMessage(number1, null, message, null, null);
                Toast.makeText(LocationShare.this,"SMS send successfully. Don't panic defenetly you got an help",Toast.LENGTH_SHORT).show();
                sms.sendTextMessage(number2, null, message, null, null);
                sms.sendTextMessage(number3, null, message, null, null);

            }
            else
                {
                Toast.makeText(LocationShare.this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void NoGpsMessage()
    {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            mAuth.signOut();
            finish();
            startActivity(new Intent(LocationShare.this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile)
        {
            startActivity(new Intent(LocationShare.this,ProfileActivity.class));
        }
        else if (id == R.id.nav_setting)
        {
            new AlertDialog.Builder(this).setIcon(R.drawable.settings).setTitle("Settings")
                    .setMessage("You can update your password.")
                    .setPositiveButton("Update Password", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           startActivity(new Intent(LocationShare.this,UpdatePassword.class));
                        }
                    }).show();
        }
        else if (id == R.id.nav_feedback)
        {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
