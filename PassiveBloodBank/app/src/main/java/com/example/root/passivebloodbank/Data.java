package com.example.root.passivebloodbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by root on 1/1/10.
 */
public class Data  extends Activity implements AdapterView.OnItemClickListener{
    ListView lv;
    public static ArrayAdapter<String> adapter;
    public static  ArrayList<String> detail=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);
        lv=(ListView)findViewById(R.id.listView);
        try {
            show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    void show() throws JSONException {
        SharedPreferences DATA=getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = DATA.getAll();
        ArrayList<String> listtag=new ArrayList<>();
        int i=0;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().toString());
            listtag.add(i,new JSONObject(entry.getValue().toString()).getString("Name"));
            detail.add(i,entry.getValue().toString());
        }
        adapter=new ArrayAdapter<String>(this,R.layout.datalistview,listtag);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getApplicationContext(),Detail.class);
        intent.putExtra("dataindex",i+"");
        startActivity(intent);
        // finish();
    }
}
