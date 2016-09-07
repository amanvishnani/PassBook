package com.darshan.client.passbook;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
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
import java.net.URL;
import java.net.URLEncoder;
import java.nio.Buffer;

/**
 * Created by DARSHAN on 04-09-2016.
 */
class BackgroundTask extends  {

    Context context;
    private Dialog loadingDialog;

    BackgroundTask(Context ctx) {
        context = ctx;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Please wait", "Loading...");
    }

    @Override
    protected String doInBackground(String... params) {
        String uname = params[0];
        String pass = params[1];

        InputStream is = null;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", uname));
        nameValuePairs.add(new BasicNameValuePair("password", pass));
        String result = null;

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(
                    "http://simplifiedcoding.16mb.com/login.php");
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            is = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        String s = result.trim();
        loadingDialog.dismiss();
        if (s.equalsIgnoreCase("success")) {

        } else {
        }
    }
}
