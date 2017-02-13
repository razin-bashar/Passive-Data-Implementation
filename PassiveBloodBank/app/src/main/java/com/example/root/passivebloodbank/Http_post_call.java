package com.example.root.passivebloodbank;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by root on 9/5/16.
 */
public class Http_post_call extends AsyncTask<String,Void,String> {
    String url;
    String data;
    public String jsonn = "";
    ProgressDialog pDialog;
    public Http_post_call(String url, String data) {
        this.url = url;
        this.data = data;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            //String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(mail, "UTF-8");
            JSONObject loginfo=new JSONObject(data);
            System.out.println(loginfo.toString());
            URL urll = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection) urll.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setUseCaches(false);
            httpCon.setFixedLengthStreamingMode(loginfo.toString().getBytes().length);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            OutputStream out = httpCon.getOutputStream();
            out.write(loginfo.toString().getBytes());
            out.close();


            InputStream is = httpCon.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            //is.close();
          //  jsonn = sb.toString();
            JSONObject temp=new JSONObject();
            if(sb.length()==5){
                temp.put("status","successful");
                jsonn=temp.toString();
                System.out.println("posthttp"+jsonn);
                return jsonn;
            }
            jsonn = sb.toString();
            System.out.println("posthttp"+jsonn);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  jsonn;
    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println("chosma:"+jsonn);
    }
}
