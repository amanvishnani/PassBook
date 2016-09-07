package com.darshan.client.passbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by DARSHAN on 30-08-2016.
 */
public class Login extends Activity {
    EditText etUsername, etPassword;
    TextView tvRegistration;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViewComponents();
    }

    private void initializeViewComponents() {

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegistration = (TextView) findViewById(R.id.tvRegistration);
        btnLogin = (Button) findViewById(R.id.btnLogin);

    }
    public void login(View v)
    {
        String username=etUsername.getText().toString();
        String password=etPassword.getText().toString();
        if(username.equals("admin") && password.equals("admin"))
        {
            Intent i=new Intent(Login.this,MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    public void registration(View v)
    {
        Intent i=new Intent(Login.this,Registration.class);
        startActivity(i);
        finish();
    }


}
