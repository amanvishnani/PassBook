package com.darshan.client.passbook;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

/**
 * Created by DARSHAN on 10-09-2016.
 */
public class HomePage extends Activity {
    EditText etService,etEmail,etPassword;
   ArrayList <User> arrayList;
    ListView lv;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        etService= (EditText) findViewById(R.id.etService);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);

    }


    public void sendData(View view)
    {
        String service=etService.getText().toString();
        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();
        String username=Username.USERNAME;
        AsyncSend asyncSend=new AsyncSend();
        asyncSend.execute("entry",service,email,password,username,"http://dhoondlee.com/darshanjain/dataEnter.php");
    }

    public void showList(View v)
    {
        lv= (ListView) findViewById(R.id.lvData);
        arrayList=new ArrayList<User>();
        AsyncSend asyncSend1=new AsyncSend();
        asyncSend1.execute("list");

    }


    private class AsyncSend extends AsyncTask<String,Void,String>{
        String err;
        String type;
        @Override
        protected String doInBackground(String... strings) {

            String result="",line;
            try {
                type=strings[0];

                if(type.equals("entry")) {
                    String service = strings[1];
                    String email = strings[2];
                    String password=strings[3];
                    String username=strings[4];

                    URL url = new URL(strings[5]);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                    String data = URLEncoder.encode("service", "UTF-8") + "=" + URLEncoder.encode(service, "UTF-8") + "&" +
                            URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
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
                }


                if(type.equals("list"))
                {
                    URL url = new URL("http://dhoondlee.com/darshanjain/JsonList.php");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
                    String data =URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(Username.USERNAME, "UTF-8");
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



                }










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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(type.equals("list"))
            {

                insertUserDetailInArrayList(s);
                UserAdapter userAdapter=new UserAdapter(getApplicationContext(),arrayList);
                lv.setAdapter(userAdapter);
            }

        }
        private void insertUserDetailInArrayList(String JsonString) {

            try {
                JSONObject jsonObject=new JSONObject(JsonString);
                JSONArray jsonArray=jsonObject.getJSONArray("login");
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                    String serviceprovider=jsonObject1.getString("serviceProvider");
                    String email=jsonObject1.getString("email");
                    String password=jsonObject1.getString("password");


                    user=new User();
                    user.setServiceProvider(serviceprovider);
                    user.setEmail(email);
                    user.setPassword(password);
                    arrayList.add(user);


                }


            } catch (JSONException e) {
                e.printStackTrace();
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
