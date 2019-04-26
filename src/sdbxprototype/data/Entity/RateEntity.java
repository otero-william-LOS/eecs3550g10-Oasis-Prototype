/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import Database.LazyRateModel;
import Models.*;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author cmason12
 */
public class RateEntity extends  Database.Database {

    protected static ArrayList<LazyRateModel> rateToLazyList(ArrayList<RateModel> rateList){
        ArrayList<LazyRateModel> list = new ArrayList<>();
        
        for (int i = 0; i < rateList.size(); i++){
            LazyRateModel lzyRate = new LazyRateModel();
            lzyRate.setBaseRate(rateList.get(i).getBaseRate());
            lzyRate.setRateDate(rateList.get(i).getRateDate());
          list.add(lzyRate);
        }
        return list;
    }
    //----------------------Request All Data----------------------------//
        protected static ArrayList<LazyRateModel> requestAll_LazyRateData() {
        return  Database.Database.getRATE_Data();
    }
        
        
    public static ArrayList<RateModel> requestAll_RATEDATA() {
        ArrayList<RateModel> rate = new ArrayList<>();
        ArrayList<LazyRateModel> lazyRateList
                = Database.Database.getRATE_Data();

        RateModel newRate;
        for (int i = 0; i < lazyRateList.size(); i++) {
            newRate = new RateModel();
            newRate.setRateDate(lazyRateList.get(i).getRateDate());
            newRate.setBaseRate(lazyRateList.get(i).getBaseRate());
            rate.add(newRate);
        }

        return rate;
    }

    //----------------------<END>Request All Data-------------------------// 
    //---------------------Request List ---------------------------------//
    public static ArrayList<RateModel> requestRateList(Date startDate,
            Date endDate) {
        ArrayList<RateModel> reqList = new ArrayList<>();

        ArrayList<RateModel> rateList = requestAll_RATEDATA();

        for (int i = 0; i < rateList.size(); i++) {
            if (EntityDatabase.dateIsBetween(startDate, endDate,
                    rateList.get(i).getRateDate())) {

                reqList.add(rateList.get(i));
            }
        }
        return reqList;
    }
    //---------------------<END> Request List -----------------------------//

    //---------------------Request Single Model --------------------------//
    public static RateModel requestRate(Date date) {
        RateModel rate = new RateModel();

        ArrayList<RateModel> rateList = requestAll_RATEDATA();

        for (int i = 0; i < rateList.size(); i++) {
            if (rateList.get(i).getRateDate().equals(date)) {
                rate = rateList.get(i);
                i = rateList.size() + 1;
            }
        }
        return rate;
    }

    //-------------------<END> Request Single Model --------------------//
}
