package com.example.root.passivebloodbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by root on 2/13/17.
 */
public class Insert extends Activity{
    EditText name;
    EditText location;
    EditText bloodgroup;
    EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert);
        name=(EditText) findViewById(R.id.iname);
        location=(EditText) findViewById(R.id.ilocation);
        bloodgroup=(EditText) findViewById(R.id.ibloodgroup);
        phone=(EditText) findViewById(R.id.iphone);

    }
    public void INS(View view) throws JSONException, ExecutionException, InterruptedException {
        JSONObject js=new JSONObject();
        js.put("Name",name.getText().toString());
        js.put("Location",location.getText().toString());
        js.put("BloodGroup",bloodgroup.getText().toString());
        js.put("Phone",phone.getText().toString());
        Http_post_call res=new Http_post_call(Config.INS,js.toString());
        res.execute();
        String result=res.get().toString();
        if(!result.isEmpty()&&result.contains("No error")){
            Toast.makeText(getApplicationContext(),"Insertion Successful",Toast.LENGTH_LONG).show();
            Thread.sleep(500);
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else if(!result.isEmpty())Toast.makeText(getApplicationContext(),"Insertion exist",Toast.LENGTH_LONG).show();
        else Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_LONG).show();
    }
}
