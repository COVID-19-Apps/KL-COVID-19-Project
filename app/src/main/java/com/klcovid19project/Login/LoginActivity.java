package com.klcovid19project.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.klcovid19project.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity{

    private TextInputEditText Login_Phone_Number;
    private Button Login_Verify;
    private String Code="+91";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_Phone_Number = findViewById(R.id.login_phone_number);
        Login_Verify= findViewById(R.id.login_verify);

        Login_Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String number = Login_Phone_Number.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    Login_Phone_Number.setError("Valid number is required");
                    Login_Phone_Number.requestFocus();
                    return;
                }
                Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                intent.putExtra("phonenumber", Code+number);
                startActivity(intent);

            }
        });
    }

}

