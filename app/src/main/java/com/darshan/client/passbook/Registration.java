package com.darshan.client.passbook;

import android.app.Activity;
import android.content.Intent;
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


    public void registration(View v) {



        Intent i = new Intent(Registration.this, Login.class);
        startActivity(i);
        finish();
    }

    public void register(View v) {

        BackgroundTask bt=new BackgroundTask(getApplicationContext());
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String cpassword = etCpassword.getText().toString();
        String email = etEmail.getText().toString();
        bt.execute("register",username,password,email);



    }


}
