package com.example.root.passivebloodbank;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by root on 2/13/17.
 */
public class Detail extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Bundle x=getIntent().getExtras();
        String i=x.getString("dataindex");
        int index=Integer.parseInt(i);
        String info=Data.detail.get(index);
        JSONObject js=null;
        try {
            js=new JSONObject(info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView a= (TextView) findViewById(R.id.textView);
        TextView b= (TextView) findViewById(R.id.textView2);
        TextView c= (TextView) findViewById(R.id.textView3);
        TextView d= (TextView) findViewById(R.id.textView4);
        try {
            a.setText(js.getString("Name"));
            b.setText(js.getString("Location"));
            c.setText(js.getString("BloodGroup"));
            d.setText(js.getString("Phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
