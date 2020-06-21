package com.klcovid19project.Login;

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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.klcovid19project.GPSTracker;
import com.klcovid19project.MainActivity;
import com.klcovid19project.Models.Users;
import com.klcovid19project.R;
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

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;
    private String Gender="Male";
    private DatabaseReference mUsersDatabase;

    private Button Edit_Profile_Get_Location;
    private RadioButton Edit_Profile_Male, Edit_Profile_Female, Edit_Profile_Others;
    private FloatingActionButton Edit_Profile_Save;
    private TextView Edit_Profile_Phone_Number;
    private String Name;
    private EditText Edit_Profile_Name, Edit_Profile_Age, Edit_Profile_Email_Id, Edit_Profile_Address,Edit_Profile_State,Edit_Profile_Country,
            Edit_Profile_City;

    private static final int REQUEST_LOCATION = 1;
    private GPSTracker gpsTracker;

    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        Name = intent.getStringExtra("access");

        Back = findViewById(R.id.toolbar_icon);
        Edit_Profile_Name = findViewById(R.id.edit_profile_name);
        Edit_Profile_Phone_Number = findViewById(R.id.edit_profile_phone_number);
        Edit_Profile_Address = findViewById(R.id.edit_profile_address);
        Edit_Profile_City = findViewById(R.id.edit_profile_city);
        Edit_Profile_State = findViewById(R.id.edit_profile_state);
        Edit_Profile_Country = findViewById(R.id.edit_profile_country);
        Edit_Profile_Email_Id = findViewById(R.id.edit_profile_email_address);
        Edit_Profile_Male = findViewById(R.id.edit_profile_male);
        Edit_Profile_Age = findViewById(R.id.edit_profile_age);
        Edit_Profile_Female = findViewById(R.id.edit_profile_female);
        Edit_Profile_Others = findViewById(R.id.edit_profile_others);
        Edit_Profile_Save = findViewById(R.id.edit_profile_save);
        Edit_Profile_Get_Location = findViewById(R.id.edit_profile_get_location);

        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        gpsTracker = new GPSTracker(getApplicationContext());
        Edit_Profile_Get_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mUsersDatabase = FirebaseDatabase.getInstance().getReference("Users").child(mCurrentUserId);
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                Edit_Profile_Name.setText(user.getName());
                Edit_Profile_Address.setText(user.getAddress());
                Edit_Profile_City.setText(user.getCity());
                Edit_Profile_Age.setText(user.getAge());
                Edit_Profile_State.setText(user.getState());
                Edit_Profile_Country.setText(user.getCountry());
                Gender = user.getGender();
                if (!Gender.isEmpty()){
                    if(Gender.equals("Male"))
                        Edit_Profile_Male.setChecked(true);
                    else if(Gender.equals("Female"))
                        Edit_Profile_Female.setChecked(true);
                    else
                        Edit_Profile_Others.setChecked(true);
                }

                Edit_Profile_Phone_Number.setText(user.getPhone_number());
                Edit_Profile_Email_Id.setText(user.getEmail_id());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Edit_Profile_Male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Gender="Male";
                }
            }
        });
        Edit_Profile_Female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Gender="Female";
                }
            }
        });
        Edit_Profile_Others.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Gender="Others";
                }
            }
        });

        Edit_Profile_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEmpty(Edit_Profile_Name.getText().toString(),Edit_Profile_Address.getText().toString(),
                        Edit_Profile_City.getText().toString(),
                        Edit_Profile_State.getText().toString(),
                        Edit_Profile_Country.getText().toString(),
                        Gender, Edit_Profile_Age.getText().toString(),Edit_Profile_Email_Id.getText().toString())) {

                    UpdateProfile(Edit_Profile_Name.getText().toString(),
                            Edit_Profile_Address.getText().toString(),
                            Edit_Profile_City.getText().toString(),
                            Edit_Profile_State.getText().toString(),
                            Edit_Profile_Country.getText().toString(),
                            Gender, Edit_Profile_Age.getText().toString(),Edit_Profile_Email_Id.getText().toString());
                }else{
                    Toast.makeText(ProfileActivity.this, "Complete all details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void UpdateProfile(String name, String address,String city, String state, String country, String gender, String age, String email) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("address", address);
        map.put("city", city);
        map.put("country", country);
        map.put("state", state);
        map.put("verified", "true");
        map.put("age", age);
        map.put("email_id", email);
        map.put("gender", gender);
        map.put("user_id", mCurrentUserId);

        mUsersDatabase.updateChildren(map);

        Toast.makeText(ProfileActivity.this, "Successfully Updated!", Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(String name, String s, String toString, String string, String s1, String gender, String toString1, String string1) {
        if (name.isEmpty() || s.isEmpty() || toString.isEmpty() || string.isEmpty() || s1.isEmpty() || gender.isEmpty() || toString1.isEmpty() || string1.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Complete All Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                ProfileActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                ProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Geocoder geocoder = new Geocoder(ProfileActivity.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(gpsTracker.getLocation().getLatitude(), gpsTracker.getLocation().getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            final String address = addresses.get(0).getAddressLine(0);
            final String city = addresses.get(0).getLocality();
            final String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            Edit_Profile_Address.setText(address);
            Edit_Profile_City.setText(city);
            Edit_Profile_State.setText(state);
            Edit_Profile_Country.setText(country);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Name.equals("false")) {

            FirebaseAuth auth = FirebaseAuth.getInstance();

            if (auth.getCurrentUser() != null) {
                mUsersDatabase = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid().toString());
                mUsersDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Users user = dataSnapshot.getValue(Users.class);
                        if (user.getVerified().equals("true")) {
                            Intent setupIntent = new Intent(ProfileActivity.this, MainActivity.class);
                            setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(setupIntent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        }


    }
}
