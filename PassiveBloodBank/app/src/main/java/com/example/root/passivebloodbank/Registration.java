package com.example.root.passivebloodbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by root on 2/13/17.
 */
public class Registration extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        name=(EditText) findViewById(R.id.name);
        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.password);
    }

    public void REG(View view) throws JSONException, ExecutionException, InterruptedException {
        JSONObject js=new JSONObject();
        js.put("Name",name.getText().toString());
        js.put("Email",email.getText().toString());
        js.put("Password",pass.getText().toString());
        System.out.println(js.toString());

        Http_post_call reg=new Http_post_call(Config.REG,js.toString());
        reg.execute();
        String res=reg.get().toString();

        if(res.contains("No error")){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }
}
