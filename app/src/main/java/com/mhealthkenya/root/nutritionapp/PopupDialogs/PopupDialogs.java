package com.mhealthkenya.root.nutritionapp.PopupDialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mhealthkenya.root.nutritionapp.DateTimePicker.DateTimePicker;
import com.mhealthkenya.root.nutritionapp.R;
import com.mhealthkenya.root.nutritionapp.Tables.Cliniciandetails;
import com.mhealthkenya.root.nutritionapp.config.Config;
import com.mhealthkenya.root.nutritionapp.progress.Progress;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopupDialogs {

    Context ctx;
    Progress pr;

    public PopupDialogs(Context ctx) {
        this.ctx = ctx;
        this.pr = new Progress(ctx);
    }


    public void addCaregiver(){
        try{

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(ctx);
            View promptsView = li.inflate(R.layout.add_caregiver, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ctx);
            DateTimePicker dtp=new DateTimePicker(ctx);



            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();


            final ArrayAdapter<String> arrayAdapterComm;

            arrayAdapterComm = new ArrayAdapter<String>(ctx,
                    android.R.layout.simple_list_item_checked, Config.commList);



            final EditText idnumT = (EditText) promptsView
                    .findViewById(R.id.idnumber_add);
            final EditText phoneT = (EditText) promptsView
                    .findViewById(R.id.phonenumber_add);


            final Button buttonAdd = (Button) promptsView
                    .findViewById(R.id.caregiverAddSubmit);

            final MaterialBetterSpinner commmode = (MaterialBetterSpinner) promptsView
                    .findViewById(R.id.communication_addselect);

            commmode.setAdapter(arrayAdapterComm);
            final String[] selectedComm = new String[1];
//            dtp.setDatePicker(dateT);




            commmode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedComm[0] =commmode.getText().toString();
//                    Toast.makeText(ctx, ""+selectedComm[0], Toast.LENGTH_SHORT).show();

                }
            });



            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String idnumS=idnumT.getText().toString();
                    String phoneS=phoneT.getText().toString();


//                    Toast.makeText(ctx, ""+idnumS, Toast.LENGTH_SHORT).show();
                    if(idnumS.trim().isEmpty()){

                        Toast.makeText(ctx, "id number is required", Toast.LENGTH_SHORT).show();
                    }
                    else if(phoneS.trim().isEmpty()){
                        Toast.makeText(ctx, "phone number is required", Toast.LENGTH_SHORT).show();
                    }

                    else if(selectedComm[0].trim().isEmpty()){
                        Toast.makeText(ctx, "Communication mode is required", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        String mycommval="";
                        if(selectedComm[0].contentEquals(Config.commList[0])){
                            mycommval="1";
                        }
                        else if(selectedComm[0].contentEquals(Config.commList[1])){
                            mycommval="2";
                        }
                        SubmitAddedCaregiver(idnumS,phoneS,mycommval);
                    }


                }
            });





            alertDialog.show();
        }
        catch(Exception e){


        }
    }




    public void editCaregiver(final String idnum,final String phone,final String date,final String communication,final String clinicianid,final String caregiverid){
        try{

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(ctx);
            View promptsView = li.inflate(R.layout.edit_caregiver, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ctx);



            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            final TextView idnumT = (TextView) promptsView
                    .findViewById(R.id.idnumber_edit);
            final TextView phoneT = (TextView) promptsView
                    .findViewById(R.id.phonenumber_edit);
            final TextView dateT = (TextView) promptsView
                    .findViewById(R.id.recruitdate_edit);
            final TextView commT = (TextView) promptsView
                    .findViewById(R.id.communication_edit);

            if(communication.contentEquals("1")){

                commT.setText(Config.commList[0]);

            }
            else if(communication.contentEquals("2")){

                commT.setText(Config.commList[1]);
            }
            else{

                commT.setText(communication);

            }

            idnumT.setText(idnum);
            phoneT.setText(phone);
            dateT.setText(date);






            alertDialog.show();
        }
        catch(Exception e){


        }
    }

   //add caregiver

    public void SubmitAddedCaregiver(final String idnumber,final String phone,final String communication) {

        pr.showProgress("adding caregiver.....");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ADDCAREGIVER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pr.dissmissProgress();
                        Toast.makeText(ctx, " "+response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dissmissProgress();
                        Toast.makeText(ctx, "error "+error, Toast.LENGTH_SHORT).show();


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                String userId="";

               List<Cliniciandetails> ml=Cliniciandetails.findWithQuery(Cliniciandetails.class,"select * from Cliniciandetails limit 1");
               for(int x=0;x<ml.size();x++){
                   userId=ml.get(x).getClinicianid();

               }
                params.put("id_number", idnumber);
                params.put("phone_number",phone);
                params.put("user_id",userId);
                params.put("message_type",communication);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
        requestQueue.add(stringRequest);



    }

   //add caregiver


}
