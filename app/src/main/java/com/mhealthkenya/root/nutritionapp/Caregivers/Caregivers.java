package com.mhealthkenya.root.nutritionapp.Caregivers;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mhealthkenya.root.nutritionapp.Login.Login;
import com.mhealthkenya.root.nutritionapp.Models.CaregiverModel;
import com.mhealthkenya.root.nutritionapp.PopupDialogs.PopupDialogs;
import com.mhealthkenya.root.nutritionapp.R;
import com.mhealthkenya.root.nutritionapp.RecyclerListener.RecyclerTouchListener;
import com.mhealthkenya.root.nutritionapp.Tables.Cliniciandetails;
import com.mhealthkenya.root.nutritionapp.adapter.CaregiverAdapter;
import com.mhealthkenya.root.nutritionapp.config.Config;
import com.mhealthkenya.root.nutritionapp.progress.Progress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class Caregivers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CaregiverAdapter adapter;
    private ArrayList<CaregiverModel> itemsList;
    private static final int PERMS_REQUEST_CODE=12345;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    Progress pr;
    private JSONArray id_result;
    FloatingActionButton fabadding;
    PopupDialogs pdialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caregivers);
        initialise();
        requestPerms();
//        setToolBar();
        addCareGiver();

        setMyAdapter();
        setMyRecyclerView();
        setRecyclerClickListener();
        populateCaregivers();
    }


    private void addCareGiver() {

        fabadding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            pdialog.addCaregiver();


//                Toast.makeText(getApplicationContext(), "adding", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateCaregivers(){

        try{

            List<Cliniciandetails> myl=Cliniciandetails.findWithQuery(Cliniciandetails.class,"select * from cliniciandetails limit 1");
            for(int x=0;x<myl.size();x++){

                String clinicianId=myl.get(x).getClinicianid();

                getCaregivers(clinicianId);

            }



        }
        catch(Exception e){


        }
    }

//    public void setToolBar(){
//
//        try{
//
//            Toolbar toolbar = (Toolbar) findViewById(R.id.caregiverstoolbar);
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setTitle("Caregivers");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
//        catch(Exception e){
//
//
//        }
//    }

    private void requestPerms(){


        String[] permissions=new String[]{
                android.Manifest.permission.INTERNET
        };

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getApplicationContext());
            builder.setAutoCancel(true);

        }


    }

    private void initialise(){

        try{
            pdialog=new PopupDialogs(Caregivers.this);
            pr=new Progress(Caregivers.this);
            recyclerView = (RecyclerView) findViewById(R.id.caregivers_recycler_view);
            fabadding = (FloatingActionButton) findViewById(R.id.fabadd);
            itemsList = new ArrayList<>();

//            pr=new Progress(Borrowings.this);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(Caregivers.this, 1);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        catch(Exception e){


        }
    }


    public void setMyAdapter(){

        try{

            adapter = new CaregiverAdapter(Caregivers.this, itemsList);
        }
        catch(Exception e){

            Toast.makeText(this, "error setting adapter", Toast.LENGTH_SHORT).show();
        }
    }

    public void setMyRecyclerView(){

        try{


            recyclerView.setAdapter(adapter);
        }
        catch(Exception e){

            Toast.makeText(this, "error setting recyclerview", Toast.LENGTH_SHORT).show();
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




    public void setRecyclerClickListener(){

        try{

            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(Caregivers.this, recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    CaregiverModel mitem=itemsList.get(position);
                    String idnum=mitem.getIdnumber();
                    String phne=mitem.getPhonenumber();
                    String date=mitem.getRecruitmentDate();
                    String comm=mitem.getCommunicationMode();
                    String clinicianid=mitem.getClinicianid();
                    String caregiver=mitem.getCaregiverid();

                    pdialog.editCaregiver(idnum,phne,date,comm,clinicianid,caregiver);
//                    Toast.makeText(Caregivers.this, "clicked "+sts, Toast.LENGTH_SHORT).show();





                }

                @Override
                public void onLongClick(View view, int position) {


                    Toast.makeText(Caregivers.this, "long click", Toast.LENGTH_SHORT).show();

                }
            }));
        }
        catch(Exception e){


        }
    }







    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {

            case R.id.action_search:
                handleMenuSearch();
                return true;

            case R.id.logout:

                Intent i = new Intent(getApplicationContext(), Login.class);
                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(i);
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.newsearch));

            isSearchOpened = false;

            setMyAdapter();
            setMyRecyclerView();

        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor


            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    try{

                        adapter.getFilter().filter(s.toString());
                    }
                    catch(Exception e){

                        Toast.makeText(Caregivers.this, "error while searching", Toast.LENGTH_SHORT).show();


                    }


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close));


            isSearchOpened = true;
        }
    }



    //********************get data from remote db

    public void getCaregivers(final String clinicianid){


        try{

            pr.showProgress("getting caregivers....");

            StringRequest stringRequest = new StringRequest(POST, Config.GETCAREGIVERS_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            pd.dismissDialog();

                            pr.dissmissProgress();

                            JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                getMyCaregivers(id_result);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "error getting results "+e, Toast.LENGTH_SHORT).show();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                            pd.dismissDialog();
                            pr.dissmissProgress();

                            Toast.makeText(getApplicationContext(), "error occured "+error, Toast.LENGTH_SHORT).show();

                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put(Config.KEY_CLINICIANPOSTID, clinicianid);

                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(Caregivers.this);
            requestQueue.add(stringRequest);


        }
        catch(Exception e){

            Toast.makeText(Caregivers.this, "error getting Loan Requests "+e, Toast.LENGTH_SHORT).show();
        }
    }




    private void getMyCaregivers(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String id=json.getString(Config.KEY_CAREGIVERID);
                String idnumber=json.getString(Config.KEY_CAREGIVERIDNUMBER);
                String phonenumber=json.getString(Config.KEY_CAREGIVERPHONENUMBER);
                String recruitmentdate=json.getString(Config.KEY_CAREGIVERRECRUITMENTDATE);
                String communicationmode=json.getString(Config.KEY_CAREGIVERCOMMUNICATIONMODE);
                String clinicianid=json.getString(Config.KEY_CAREGIVERCLINICIANID);


                CaregiverModel cm=new CaregiverModel(id,clinicianid, idnumber, phonenumber, recruitmentdate, communicationmode);

                itemsList.add(cm);


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(Caregivers.this, "an error getting loan requests "+ e, Toast.LENGTH_SHORT).show();
            }
        }
        adapter.notifyDataSetChanged();

    }

    //*********************get data from remote db

}
