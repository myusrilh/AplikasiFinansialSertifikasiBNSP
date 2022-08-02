package com.example.aplikasifinansialsertifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aplikasifinansialsertifikasi.helpers.DatabaseHelper;
import com.example.aplikasifinansialsertifikasi.helpers.User;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    // Declare EditText
    EditText usernameEdt;
    EditText passwordEdt;

    // Declare Button
    Button loginBtn;

    TextView directToRegister;

    DatabaseHelper databaseHelper;
    User user = null;

    ConstraintLayout constraintLayoutLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        initViews();

        loginBtn.setOnClickListener(v-> {

            if (usernameEdt.getText() != null && passwordEdt.getText() != null) {

                String userName = usernameEdt.getText().toString();
                String passWord = passwordEdt.getText().toString();

                if (validate(userName, passWord)) {

                    user = new User();

                    //Authenticate user
                    user.setUsername(userName);
                    user.setPassword(passWord);
                    User currentUser = databaseHelper.checkUser(user);

                    if (currentUser != null) {
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        i.putExtra("User", currentUser);
                        startActivity(i);
                    } else {
                        Snackbar.make(constraintLayoutLogin, "User tidak ada, tolong registrasi terlebih dahulu!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(constraintLayoutLogin, "Input dengan benar!", Snackbar.LENGTH_LONG).show();
                }
            }else{
                Snackbar.make(constraintLayoutLogin, "Input dengan benar!", Snackbar.LENGTH_LONG).show();
            }
        });

        directToRegister.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        });
    }

    private boolean validate(String username, String password) {

        boolean valid;

        //Handling validation for Email field
        if (username.isEmpty()) {
            valid = false;
            Snackbar.make(constraintLayoutLogin, "Username tidak boleh kosong!", Snackbar.LENGTH_LONG).show();
        } else {
            if(username.contains(" ")){
                valid = false;
                Snackbar.make(constraintLayoutLogin, "Masukkan tidak boleh memiliki spasi!", Snackbar.LENGTH_LONG).show();
            }else{
                valid = true;
            }
        }

        //Handling validation for Password field
        if (password.isEmpty()) {
            valid = false;
            Snackbar.make(constraintLayoutLogin, "Password tidak boleh kosong!", Snackbar.LENGTH_LONG).show();
        } else {
            valid = true;
        }

        return valid;

    }

    private void initViews(){
        usernameEdt = findViewById(R.id.username_login_edt);
        passwordEdt = findViewById(R.id.password_login_edt);
        directToRegister = findViewById(R.id.direct_to_register_tv);

        loginBtn = findViewById(R.id.login_btn);
        constraintLayoutLogin= findViewById(R.id.login_constraint);
    }
}