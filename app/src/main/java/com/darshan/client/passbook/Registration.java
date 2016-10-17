package com.darshan.client.passbook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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



    public void register(View v) {
        AsyncRegister asyncRegister = new AsyncRegister();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String cpassword = etCpassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (checkValidations(username, password, cpassword, email)) {
            if (isOnline()) {
                asyncRegister.execute(username, password, email, "http://dhoondlee.com/darshanjain/register.php");
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
        } else {
            return true;
        }
    }



    public class AsyncRegister extends AsyncTask<String, Void, String> {

        String err = "";
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(Registration.this);
            pd.setMessage("Loading...");
            pd.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            String result = "null", line;
            try {
                String username = strings[0];
                String password = strings[1];
                String email = strings[2];
                URL url = new URL(strings[3]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
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
            return "something went wrong" + err;
        }
        @Override
        protected void onPostExecute(String aVoid) {
            pd.dismiss();

                Toast.makeText(getApplicationContext(), aVoid, Toast.LENGTH_LONG).show();
                etUsername.setText("");
                etEmail.setText("");
                etPassword.setText("");
                etCpassword.setText("");

            }
    }
    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


}
