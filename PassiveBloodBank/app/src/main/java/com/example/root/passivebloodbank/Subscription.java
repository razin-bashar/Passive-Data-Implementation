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
public class Subscription  extends Activity {
    EditText bd;
    EditText loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription);
        bd= (EditText) findViewById(R.id.sblood);
        loc=(EditText) findViewById(R.id.slocation);
    }
    public  void SUB(View view) throws InterruptedException, JSONException, ExecutionException {
        SharedPreferences storage=getSharedPreferences("loginsession", Context.MODE_PRIVATE);
        String userid=storage.getString("Userid","");
        if(!userid.isEmpty()){
            JSONObject js=new JSONObject();
            js.put("BloodGroup",bd.getText().toString());
            js.put("Id",userid);
            js.put("Location",loc.getText().toString());

            Http_post_call res=new Http_post_call(Config.SUB,js.toString());
            res.execute();
            String result=res.get().toString();
            if(!result.isEmpty()&&result.contains("No error")){

                SharedPreferences storage2=getSharedPreferences("sublist", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=storage2.edit();

                //one time execution statement
                String ss=storage2.getString("counter","");
                if(ss.isEmpty()){
                    editor.putString("counter","1");
                    editor.commit();
                }


                int counter=Integer.parseInt(storage2.getString("counter",""));

                editor.putString("sid"+counter,new JSONObject(result).getString("SID"));
                editor.putString("counter",(++counter)+"");
                editor.commit();


            }
            else Toast.makeText(getApplicationContext(),"subscription exitst",Toast.LENGTH_LONG).show();



        }
        else{
            Toast.makeText(getApplicationContext(),"you did not login.plz login...",Toast.LENGTH_LONG).show();
            Thread.sleep(500);
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
        }
    }
}
