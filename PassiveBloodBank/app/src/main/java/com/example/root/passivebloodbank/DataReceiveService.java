package com.example.root.passivebloodbank;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * Created by root on 2/13/17.
 */
public class DataReceiveService extends IntentService {

   public DataReceiveService(){
       super("dataservice");
   }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        SharedPreferences sid = getSharedPreferences("sublist", Context.MODE_PRIVATE);
        SharedPreferences DATA = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=DATA.edit();
        String s = sid.getString("counter", "");

        if (!s.isEmpty()) {
            int counter = Integer.parseInt(s);
            for (int i = 1; i < counter; i++) {
                String jsonn=null;
                String ls = sid.getString("sid" + i, "");
                JSONObject js = new JSONObject();
                try {
                    js.put("SID", ls);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.print(js.toString());

                JSONObject loginfo = null;
                try {
                    loginfo = new JSONObject(js.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //System.out.println(loginfo.toString());
                URL urll = null;
                try {
                    urll = new URL(Config.DAT);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection httpCon = null;
                try {
                    httpCon = (HttpURLConnection) urll.openConnection();

                    httpCon.setDoOutput(true);
                    httpCon.setUseCaches(false);
                    httpCon.setFixedLengthStreamingMode(loginfo.toString().getBytes().length);
                    httpCon.setRequestMethod("POST");
                    httpCon.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded;charset=UTF-8");
                    OutputStream out = httpCon.getOutputStream();
                    out.write(loginfo.toString().getBytes());
                    out.close();

                   // Thread.sleep(50);
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
                    JSONObject temp = new JSONObject();
                    if (sb.length() == 5) {
                        temp.put("status", "successful");
                        jsonn = temp.toString();
                      //  System.out.println("posthttp" + jsonn);
                    }
                    jsonn = sb.toString();
                    //System.out.println("posthttp" + jsonn);

                    JSONArray js1=new JSONArray(jsonn);
                  //  System.out.println(js1.toString());

                    //System.out.println(js1.length());

                    for(int i1=1;i1<js1.length();i1++){
                        JSONObject jso;
                        jso=js1.getJSONObject(i1);
                        System.out.println(jso.toString());
                        if(!DATA.contains("Did"+jso.getString("Did"))){
                            editor.putString("Did"+jso.getString("Did"),jso.toString());
                            editor.commit();
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
