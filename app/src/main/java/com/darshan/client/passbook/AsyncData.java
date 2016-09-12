package com.darshan.client.passbook;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
public class AsyncData extends AsyncTask<String, Void, String> {
    Context ctx;
String err="";
    public AsyncData(Context applicationContext) {

        ctx = applicationContext;
    }

    ProgressDialog progressDialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected String doInBackground(String...strings) {

        String result="null",line;

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

        return "something went wrong"+err;
    }

    @Override
    protected void onPostExecute(String aVoid) {

        super.onPostExecute(aVoid);

        Toast.makeText(ctx,aVoid,Toast.LENGTH_LONG).show();

    }


}
