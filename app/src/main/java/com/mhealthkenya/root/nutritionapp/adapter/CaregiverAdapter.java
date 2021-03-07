package com.mhealthkenya.root.nutritionapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.mhealthkenya.root.nutritionapp.Filters.CaregiverFilter;
import com.mhealthkenya.root.nutritionapp.Models.CaregiverModel;
import com.mhealthkenya.root.nutritionapp.R;

import java.util.ArrayList;

public class CaregiverAdapter extends RecyclerView.Adapter<CaregiverAdapter.MyviewHolder> implements Filterable {

    public ArrayList<CaregiverModel> mylist, filterList;
    Context ctx;
    CaregiverFilter filter;

    public CaregiverAdapter(Context ctx, ArrayList<CaregiverModel> mylist) {
        this.mylist = mylist;
        this.ctx = ctx;
        this.filterList = mylist;
    }

    @Override
    public CaregiverAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caregivers_row, parent, false);

        return new MyviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        try {

            CaregiverModel itm = mylist.get(position);

            //            holder.id.setText(itm.getId());
            holder.communication.setText(itm.getCommunicationMode());
            holder.recruitDate.setText(itm.getRecruitmentDate());
            holder.phone.setText(itm.getPhonenumber());
            holder.idnumber.setText(itm.getIdnumber());


        } catch (Exception e) {


        }


    }


    @Override
    public int getItemCount() {
        return mylist.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CaregiverFilter(filterList, this);
        }

        return filter;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {


        public TextView idnumber, phone, recruitDate, communication;

        public CardView lcd;


        public MyviewHolder(View itemView) {
            super(itemView);

//            id =(TextView) itemView.findViewById(R.id.borrowings_id);
            idnumber = (TextView) itemView.findViewById(R.id.caregiver_idnumber);
            phone = (TextView) itemView.findViewById(R.id.caregiver_phone);
            recruitDate = (TextView) itemView.findViewById(R.id.caregiver_date);
            communication = (TextView) itemView.findViewById(R.id.caregiver_communication);


//
            lcd = (CardView) itemView.findViewById(R.id.caregivers_card_view);
        }
    }
}

