package com.mhealthkenya.root.nutritionapp.Signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mhealthkenya.root.nutritionapp.R;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

//        setToolBar();
    }

    public void signup(View v){

        try{

            Toast.makeText(this, "signing up", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(this, "error signing up", Toast.LENGTH_SHORT).show();
        }
    }


//    public void setToolBar(){
//
//        try{
//
//            Toolbar toolbar = (Toolbar) findViewById(R.id.regtoolbar);
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setTitle("USER REGISTRATION");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
//        catch(Exception e){
//
//
//        }
//    }
}
