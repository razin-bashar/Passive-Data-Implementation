package com.example.root.passivebloodbank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by root on 1/1/10.
 */
public class Login extends Activity {
    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email= (EditText) findViewById(R.id.lemail);
        password= (EditText) findViewById(R.id.lpassword);
    }
    public  void LOG(View view) throws JSONException, ExecutionException, InterruptedException {
        JSONObject js =new JSONObject();
        js.put("Email",email.getText().toString());
        js.put("Password",password.getText().toString());
        Http_post_call log=new Http_post_call(Config.LOG,js.toString());
        log.execute();
        String res=log.get().toString();

        System.out.println("hello"+res);
        JSONObject result=null;
        if(!res.isEmpty())result=new JSONObject(res);

        //session Creating


        if(!res.isEmpty()&& result.getString("error").equalsIgnoreCase("No Error")) {
            SharedPreferences storage=getSharedPreferences("loginsession", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=storage.edit();
            editor.putString("Userid", result.getString("id"));
            editor.commit();
            Toast.makeText(getApplicationContext(), "Login Succesfull", Toast.LENGTH_LONG).show();


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else Toast.makeText(getApplicationContext(), "Login unSuccesfull", Toast.LENGTH_LONG).show();

    }
}
