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
        Encryption ee = new Encryption();

        if (username.equals("") || password.equals("") || email.equals("") || cpassword.equals("")) {
            Toast.makeText(getApplicationContext(), "all field Are compulsory", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(cpassword)) {
            Toast.makeText(getApplicationContext(), "Password is not matching...", Toast.LENGTH_SHORT).show();

        }
        else if (isValidEmailAddress(email)) {
            Toast.makeText(getApplicationContext(), "please add valid email...", Toast.LENGTH_SHORT).show();

        }
        else {

                if(isOnline())
                {
                    asyncData.execute(username, password, email, "http://dhoondlee.com/darshanjain/register.php");
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "No Internet Connection!!!", Toast.LENGTH_SHORT).show();

                }
            }
    }

    private boolean isOnline(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
