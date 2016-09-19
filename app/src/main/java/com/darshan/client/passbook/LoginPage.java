package com.darshan.client.passbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by DARSHAN on 10-09-2016.
 */
public class LoginPage extends Activity{
    EditText etUsername, etPassword;
    TextView tvRegistration;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
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
        AsyncLogin asyncLogin=new AsyncLogin();
        asyncLogin.execute(username,password,"http://dhoondlee.com/darshanjain/login.php");

    }

    public void registration(View v)
    {
        Intent i=new Intent(LoginPage.this,Registration.class);
        startActivity(i);
        finish();
    }


    public class AsyncLogin extends AsyncTask<String, Void, String> {

        String err="";

        String username;
        String password;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        @Override
        protected String doInBackground(String... strings) {

            String result="",line;
            try {
                 username= strings[0];
                password = strings[1];
                URL url = new URL(strings[2]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.flush();
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    result = result + line;

                }
                inputStream.close();


                return result;

            } catch (UnsupportedEncodingException e) {
                err = err + e;
                e.printStackTrace();
            } catch (ProtocolException e) {
                err = err + e;
                e.printStackTrace();
            } catch (MalformedURLException e) {
                err = err + e;
                e.printStackTrace();
            } catch (IOException e) {
                err = err + e;
                e.printStackTrace();
            }

            return "something went wrong"+err;
        }

        @Override
        protected void onPostExecute(String aVoid) {

            super.onPostExecute(aVoid);

             if(aVoid.equals("done"))
            {
                Username.USERNAME=username;
                Intent i=new Intent(LoginPage.this,HomePage.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),"WELCOME!!!",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"INVALID USERNAME OR PASSWORD... RETRY!!!",Toast.LENGTH_LONG).show();
            }



        }


    }














}
