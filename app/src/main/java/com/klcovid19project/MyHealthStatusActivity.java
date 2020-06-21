package com.klcovid19project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MyHealthStatusActivity extends AppCompatActivity {

    private RadioGroup radiogrp_caugh, radiogrp_fever, radiogrp_breath, radiogrp_travel, radiogrp_nearaffected;
    private RadioButton rcough,rfever, rbreath, rtravel, rnaf;
    private FloatingActionButton button;

    private DatabaseReference mHealthStatusDatabase;

    private ImageView Back;
    private int factor = 0;
    private boolean serious = false;

    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_health_status);

        radiogrp_caugh=findViewById(R.id.radiogrp_caugh);
        radiogrp_fever=findViewById(R.id.radiogrp_fever);
        radiogrp_breath=findViewById(R.id.radiogrp_breath);
        radiogrp_travel=findViewById(R.id.radiogrp_travel);
        radiogrp_nearaffected=findViewById(R.id.radiogrp_nearaffected);
        button=findViewById(R.id.fab_submit);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();

        mHealthStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Health_Status").child(mCurrentUserId);
        mHealthStatusDatabase.keepSynced(true);

        Back = findViewById(R.id.toolbar_icon);

        final ViewDialog alert = new ViewDialog();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    int caugh = radiogrp_caugh.getCheckedRadioButtonId();
                    rcough = findViewById(caugh);
                    int fever = radiogrp_fever.getCheckedRadioButtonId();
                    rfever = findViewById(fever);
                    int breath = radiogrp_breath.getCheckedRadioButtonId();
                    rbreath = findViewById(breath);
                    int travel = radiogrp_travel.getCheckedRadioButtonId();
                    rtravel = findViewById(travel);
                    int naf = radiogrp_nearaffected.getCheckedRadioButtonId();
                    rnaf = findViewById(naf);

                    String Cough = rcough.getText().toString();
                    String Fever = rfever.getText().toString();
                    String Breath = rbreath.getText().toString();
                    String Travel = rtravel.getText().toString();
                    String NAF = rnaf.getText().toString();


                    if (isEmpty(Cough, Fever, Breath, Travel, NAF)) {

                        if (Cough.equals("Yes")) {
                            factor++;
                        }
                        if (Fever.equals("Yes")) {
                            factor++;
                        }
                        if (Breath.equals("Yes")) {
                            factor++;
                        }
                        if (Travel.equals("Yes")) {
                            factor++;
                        }
                        if (NAF.equals("Yes")) {
                            factor++;
                            serious = true;
                        }

                        final HashMap HealthMap = new HashMap<>();
                        HealthMap.put("cough", Cough);
                        HealthMap.put("fever", Fever);
                        HealthMap.put("breath", Breath);
                        HealthMap.put("Travel", Travel);
                        HealthMap.put("naf", NAF);
                        HealthMap.put("factor", factor);
                        HealthMap.put("serious", serious);

                        mHealthStatusDatabase.setValue(HealthMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()){
                                    if (factor == 0) {
                                        alert.showDialog(MyHealthStatusActivity.this,
                                                "The risk of being affected is less. Stay safe in your home!", R.drawable.healthy);
                                    } else if (serious == true) {
                                        alert.showDialog(MyHealthStatusActivity.this,
                                                "You have a high risk of getting affected. Go to a nearby corona testing center immediately.", R.drawable.danger);
                                    } else {
                                        alert.showDialog(MyHealthStatusActivity.this,
                                                "You have symptoms of Corona virus. Stay quarentined in home and we recommend you to visit a nearby corona testing center.", R.drawable.alert);
                                    }
                                    Toast.makeText(MyHealthStatusActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                    } else {
                        Toast.makeText(MyHealthStatusActivity.this, "Complete all details", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NullPointerException e){
                    Toast.makeText(MyHealthStatusActivity.this, "Please fill in all the details!", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    public class ViewDialog {

        public void showDialog(Activity activity, String msg, int img){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);

            ImageView image = dialog.findViewById(R.id.image);
            image.setImageResource(img);

            TextView text = dialog.findViewById(R.id.message);
            text.setText(msg);

            Button dialogButton = dialog.findViewById(R.id.ok);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

    private boolean isEmpty(String cough, String fever, String breath, String travel, String naf) {
        if (cough.isEmpty() || fever.isEmpty() || breath.isEmpty()
                || travel.isEmpty() || naf.isEmpty()) {
            Toast.makeText(MyHealthStatusActivity.this, "Complete All Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}