package com.darshan.client.passbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DARSHAN on 30-08-2016.
 */
public class Registration extends Activity {
    EditText etUsername, etPassword, etEmail, etCpassword;
    TextView tvLogin;
    Button btnLogin;
    Boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initializeViewComponents();
    }


    private void initializeViewComponents() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etCpassword = (EditText) findViewById(R.id.etCPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }


    public void loginPage(View v) {


        Intent i = new Intent(Registration.this, LoginPage.class);
        startActivity(i);
        finish();
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public void register(View v) {

        AsyncData asyncData = new AsyncData(getApplicationContext());
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String cpassword = etCpassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();


        if (checkValidations(username, password, cpassword, email)) {
            if (isOnline()) {
                asyncData.execute(username, password, email, "http://dhoondlee.com/darshanjain/register.php");
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection!!!", Toast.LENGTH_SHORT).show();

            }
        }
    }


    private Boolean checkValidations(String username, String password, String cpassword, String email) {

        if (username.equals("") || password.equals("") || email.equals("") || cpassword.equals("")) {
            Toast.makeText(getApplicationContext(), "All Field Are Compulsory...", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(cpassword)) {
            Toast.makeText(getApplicationContext(), "Password is Not Matching...", Toast.LENGTH_SHORT).show();
            return false;

        } else if (!isValidEmailAddress(email)) {
            Toast.makeText(getApplicationContext(), "Invalid Email-ID..", Toast.LENGTH_SHORT).show();
            return false;

        } else if (username.length() <= 6 || username.length() >= 12) {
            Toast.makeText(getApplicationContext(), "Username length must be between 6 to 12 character!!!", Toast.LENGTH_SHORT).show();
            return false;

        } else if (password.length() <= 6 || password.length() >= 12) {
            Toast.makeText(getApplicationContext(), "password length must be between 6 to 12 character!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
