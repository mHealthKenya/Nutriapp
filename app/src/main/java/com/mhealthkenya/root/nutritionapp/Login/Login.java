package com.mhealthkenya.root.nutritionapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mhealthkenya.root.nutritionapp.Caregivers.Caregivers;
import com.mhealthkenya.root.nutritionapp.R;
import com.mhealthkenya.root.nutritionapp.Requestperms.RequestPerms;
import com.mhealthkenya.root.nutritionapp.Tables.Cliniciandetails;
import com.mhealthkenya.root.nutritionapp.config.Config;
import com.mhealthkenya.root.nutritionapp.dialogs.Dialogs;
import com.mhealthkenya.root.nutritionapp.progress.Progress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText uname,password;
    Progress pr;
    Dialogs mydialog;
    private JSONArray id_result;
    RequestPerms rp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initialise();
        rp.requestPerms();
    }

    private void initialise(){
        rp=new RequestPerms(Login.this,this);
        mydialog=new Dialogs(Login.this);
        pr=new Progress(Login.this);
        uname=(EditText) findViewById(R.id.login_email);
        password=(EditText) findViewById(R.id.login_password);
    }

    private void validate(){

        try{

            String unameS=uname.getText().toString();
            String passwords=password.getText().toString();

            if(unameS.trim().isEmpty()){
                Toast.makeText(this, "username is required", Toast.LENGTH_SHORT).show();
            }
            else if(passwords.trim().isEmpty()){

                Toast.makeText(this, "password is required", Toast.LENGTH_SHORT).show();
            }
            else{


                SignInUser(unameS,passwords);
            }
        }
        catch(Exception e){


        }
    }

    public void Login(View v){

        try{

            validate();
        }
        catch(Exception e){


        }
    }





    public void SignInUser(final String em,final String pass) {

        pr.showProgress("Logging in.....");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contentEquals("Invalid login credentials")) {
                            pr.dissmissProgress();
                            mydialog.showErrorDialog(response,"Login Error");
                        }
                        else  if (response.contentEquals("Email does not exist in the system")) {
                            pr.dissmissProgress();
                            mydialog.showErrorDialog(response,"Login Error");
                        }

                        else {

                            pr.dissmissProgress();

                            //code to get all user info

                            JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                System.out.println("**********jsonresults*****");
                                System.out.println(id_result);

                                getMyUserDetails(id_result);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Login.this, "error getting all user details "+e, Toast.LENGTH_SHORT).show();

                            }

                            //code to get all user info

//
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();

                        mydialog.showErrorDialog("Check internet connection and try again","Login Error");

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("email", em);
                params.put("password", pass);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }


    private void getMyUserDetails(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String userid=json.getString("user_id");
                String fname=json.getString("first_name");
                String lname=json.getString("last_name");
                String phone=json.getString("phone_number");
                String facilityid=json.getString("facility_id");
                String email=json.getString("email");
                String idumber=json.getString("id_number");

                Cliniciandetails.deleteAll(Cliniciandetails.class);

                Cliniciandetails cd=new Cliniciandetails();
                cd.setClinicianid(userid);
                cd.setEmail(email);
                cd.setFname(fname);
                cd.setFacilityid(facilityid);
                cd.setPhonenumber(phone);
                cd.setLname(lname);
                cd.setIdnumber(idumber);
                cd.setPassword(password.getText().toString());
                cd.save();

                Intent myint=new Intent(getApplicationContext(), Caregivers.class);
                startActivity(myint);





            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Login.this, "an error occured userdetails "+ e, Toast.LENGTH_SHORT).show();
            }
        }

    }


}
