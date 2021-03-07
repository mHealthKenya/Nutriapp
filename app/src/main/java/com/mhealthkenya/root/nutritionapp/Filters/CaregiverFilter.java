package com.mhealthkenya.root.nutritionapp.Filters;

//public class CaregiverFilter {
//}


import android.widget.Filter;

import com.mhealthkenya.root.nutritionapp.Models.CaregiverModel;
import com.mhealthkenya.root.nutritionapp.adapter.CaregiverAdapter;

import java.util.ArrayList;

public class CaregiverFilter extends Filter {

    CaregiverAdapter adapter;
    ArrayList<CaregiverModel> filterList;




    public CaregiverFilter(ArrayList<CaregiverModel> filterList,CaregiverAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;



    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<CaregiverModel> filteredCaregivers=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {



                //CHECK
                if((filterList.get(i).getCommunicationMode().toUpperCase().contains(constraint)||filterList.get(i).getIdnumber().toUpperCase().contains(constraint)||filterList.get(i).getRecruitmentDate().toUpperCase().contains(constraint)||filterList.get(i).getPhonenumber().toUpperCase().contains(constraint)))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredCaregivers.add(filterList.get(i));
                }

            }

            results.count=filteredCaregivers.size();
            results.values=filteredCaregivers;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
//
//        adapter.mylist= (ArrayList<BorrowingsModel>) results.values;
        adapter.mylist=(ArrayList<CaregiverModel>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}

