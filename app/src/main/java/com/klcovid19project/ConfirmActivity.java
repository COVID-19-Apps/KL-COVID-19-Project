package com.klcovid19project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.klcovid19project.GPSTracker;
import com.klcovid19project.Models.Users;
import com.klcovid19project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ConfirmActivity extends AppCompatActivity {

    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;
    private DatabaseReference mUsersDatabase, mPersonsDatabase;

    private Button Confirm_Get_Location;
    private FloatingActionButton Confirm_Done;
    private TextView Confirm_Phone_Number;
    private String category_name;
    private EditText Confirm_Name, Confirm_Email_Id, Confirm_State, Confirm_Address,
            Confirm_City, Description;
    private Spinner category;

    private static final int REQUEST_LOCATION = 1;
    private GPSTracker gpsTracker;
    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Back = findViewById(R.id.toolbar_icon);
        Confirm_Name = findViewById(R.id.confirm_name);
        Confirm_Phone_Number = findViewById(R.id.confirm_phone_number);
        Confirm_Address = findViewById(R.id.confirm_address);
        Confirm_City = findViewById(R.id.confirm_city);
        Confirm_State = findViewById(R.id.confirm_state);
        Description = findViewById(R.id.confirm_desc);
        Confirm_Email_Id = findViewById(R.id.confirm_email_address);
        Confirm_Done = findViewById(R.id.confirm_done);
        Confirm_Get_Location = findViewById(R.id.confirm_get_location);
        category=findViewById(R.id.categorys);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_name=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        mPersonsDatabase = FirebaseDatabase.getInstance().getReference("Persons");
        mPersonsDatabase.keepSynced(true);

        gpsTracker = new GPSTracker(getApplicationContext());
        Confirm_Get_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });


        mUsersDatabase = FirebaseDatabase.getInstance().getReference("Users").child(mCurrentUserId);
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                Confirm_Name.setText(user.getName());
                Confirm_Address.setText(user.getAddress());
                Confirm_City.setText(user.getCity());
                Confirm_State.setText(user.getState());
                Confirm_Phone_Number.setText(user.getPhone_number());
                Confirm_Email_Id.setText(user.getEmail_id());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Confirm_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEmpty(Confirm_Name.getText().toString(), Confirm_Address.getText().toString(),
                        Confirm_City.getText().toString(),
                        Description.getText().toString(),Confirm_Email_Id.getText().toString())) {
                    if (!category_name.equals("Select")) {

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", Confirm_Name.getText().toString());
                        map.put("address", Confirm_Address.getText().toString());
                        map.put("city", Confirm_City.getText().toString());
                        map.put("state", Confirm_State.getText().toString());
                        map.put("phone_number", Confirm_Phone_Number.getText().toString());
                        map.put("type", category_name);
                        map.put("email_id", Confirm_Email_Id.getText().toString());
                        map.put("desc", Description.getText().toString());
                        map.put("user_id", mCurrentUserId);

                        mPersonsDatabase.child(mCurrentUserId+category_name).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()){
                                    Toast.makeText(ConfirmActivity.this, "Thank You", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });


                    }else{
                        Toast.makeText(ConfirmActivity.this, "Please select category", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ConfirmActivity.this, "Complete all details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean isEmpty(String name, String add, String city,  String desc, String email) {
        if (name.isEmpty() || add.isEmpty() || city.isEmpty() || desc.isEmpty() || email.isEmpty()) {
            Toast.makeText(ConfirmActivity.this, "Complete All Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                ConfirmActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                ConfirmActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Geocoder geocoder = new Geocoder(ConfirmActivity.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(gpsTracker.getLocation().getLatitude(), gpsTracker.getLocation().getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            final String address = addresses.get(0).getAddressLine(0);
            final String city = addresses.get(0).getLocality();
            final String state = addresses.get(0).getAdminArea();
            Confirm_Address.setText(address);
            Confirm_City.setText(city);
            Confirm_State.setText(state);
        }
    }
}
