package com.example.root.passivebloodbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    public  static Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=getApplicationContext();
        executePeriodicTask();

    }

    public void executePeriodicTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {

                            //Write your code here , which you want to execute periodically
                            Intent intent=new Intent(getApplicationContext(),DataReceiveService.class);
                            startService(intent);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000);
    }





    public void REG(View view) {
        Intent intent = new Intent(getApplicationContext(), Registration.class);
        startActivity(intent);

    }

    public void LOG(View view) {
        SharedPreferences storage = getSharedPreferences("loginsession", Context.MODE_PRIVATE);
        String userid = storage.getString("Userid", "");
        System.out.println(userid);
        if (userid.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), "You have already loggedin and userid=" + userid, Toast.LENGTH_LONG).show();

    }

    public void SUB(View view) {
        Intent intent = new Intent(getApplicationContext(), Subscription.class);
        startActivity(intent);

    }

    public void DAT(View view) {
        Intent intent = new Intent(getApplicationContext(), Data.class);
        startActivity(intent);

    }

    public void INS(View view) {
        Intent intent = new Intent(getApplicationContext(), Insert.class);
        startActivity(intent);

    }

}
