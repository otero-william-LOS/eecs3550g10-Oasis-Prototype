/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import Database.LazyBllchrgModel;
import static Entity.EntityDatabase.createReservationModel;
import java.util.ArrayList;
import Models.*;
import java.sql.Date;

/**
 *
 * @author cmason12
 */
public class BllchrgEntity extends  Database.Database {
    
    
    protected static ArrayList<LazyBllchrgModel> requestAll_LazyBllchrgData(ArrayList<BllchrgModel> bllList){
        ArrayList<LazyBllchrgModel> list = new ArrayList<>();
        
        for (int i = 0; i < bllList.size(); i++){
            LazyBllchrgModel lzyBll = new LazyBllchrgModel();
            lzyBll.setBllchrgID(bllList.get(i).getBllchrgID());
            lzyBll.setLineDescription(
                    bllList.get(i).getLineDescription());
            lzyBll.setAmount(bllList.get(i).getAmount());
            lzyBll.setDateCharged(bllList.get(i).getDateCharged());
            lzyBll.setDatePaid(bllList.get(i).getDatePaid());
            lzyBll.setIsPaid(bllList.get(i).isIsPaid());
            lzyBll.setReservation(bllList.get(i).getReservation().getRsvID());
            
            
            list.add(lzyBll);
        }
        
        return list;
    }
//------------------------Request All Data -------------------------------//
        public static ArrayList<LazyBllchrgModel> requestAll_LazyBllchrgData() {
        return Database.Database.getBllchrg_Data();
    }
    public static ArrayList<BllchrgModel> requestAll_BllchrgData() {
        ArrayList<BllchrgModel> billList = new ArrayList<>();
        ArrayList<LazyBllchrgModel> lazyBllchrgList
                = Database.Database.getBllchrg_Data();

        BllchrgModel newBllchrg;
        for (int i = 0; i < lazyBllchrgList.size(); i++) {
            newBllchrg = new BllchrgModel();
            newBllchrg.setBllchrgID(lazyBllchrgList.get(i).getBllchrgID());
            newBllchrg.setReservation(
                    createReservationModel(lazyBllchrgList.get(i)));
            newBllchrg.setLineDescription(
                    lazyBllchrgList.get(i).getLineDescription());
            newBllchrg.setAmount(lazyBllchrgList.get(i).getAmount());
            newBllchrg.setDateCharged(lazyBllchrgList.get(i).getDateCharged());
            newBllchrg.setDatePaid(lazyBllchrgList.get(i).getDatePaid());
            newBllchrg.setIsPaid(lazyBllchrgList.get(i).isIsPaid());
            billList.add(newBllchrg);
        }

        return billList;
    }
//------------------------<END> Request All Data ---------------------------//
    //------------------------Request Lists-------------------------------//
    public static ArrayList<BllchrgModel> requestBillchrgDateChargedList(
            Date startDate, Date endDate) {
        ArrayList<BllchrgModel> filteredList = new ArrayList<>();
        ArrayList<BllchrgModel> billList = requestAll_BllchrgData();

        for (int i = 0; i < billList.size(); i++) {
            if ((billList.get(i).getDateCharged().equals(startDate)
                    || billList.get(i).getDateCharged().after(startDate))
                    && (billList.get(i).getDateCharged().equals(endDate)
                    || billList.get(i).getDateCharged().before(endDate))) {
                filteredList.add(billList.get(i));
            }
        }
        return filteredList;
    }

    public static ArrayList<BllchrgModel> requestBillchrgPaidList(
            Date startDate, Date endDate) {
        ArrayList<BllchrgModel> filteredList = new ArrayList<>();
        ArrayList<BllchrgModel> billList = requestAll_BllchrgData();

        for (int i = 0; i < billList.size(); i++) {
            if ((billList.get(i).getDatePaid().equals(startDate)
                    || billList.get(i).getDatePaid().after(startDate))
                    && (billList.get(i).getDatePaid().equals(endDate)
                    || billList.get(i).getDatePaid().before(endDate))) {
                filteredList.add(billList.get(i));
            }
        }
        return filteredList;
    }

    public static ArrayList<BllchrgModel> requestBillchrgIsPaidList(
            Date startDate, Date endDate) {
        ArrayList<BllchrgModel> filteredList = new ArrayList<>();
        ArrayList<BllchrgModel> billList = requestAll_BllchrgData();

        for (int i = 0; i < billList.size(); i++) {
            if ((billList.get(i).getDateCharged().equals(startDate)
                    || billList.get(i).getDateCharged().after(startDate))
                    && (billList.get(i).getDateCharged().equals(endDate)
                    || billList.get(i).getDateCharged().before(endDate))
                    && billList.get(i).isIsPaid()) {
                filteredList.add(billList.get(i));
            }
        }
        return filteredList;
    }

    public static ArrayList<BllchrgModel> requestBillchrgIsNotPaidList(
            Date startDate, Date endDate) {
        ArrayList<BllchrgModel> filteredList = new ArrayList<>();
        ArrayList<BllchrgModel> billList = requestAll_BllchrgData();

        for (int i = 0; i < billList.size(); i++) {
            if ((billList.get(i).getDateCharged().equals(startDate)
                    || billList.get(i).getDateCharged().after(startDate))
                    && (billList.get(i).getDateCharged().equals(endDate)
                    || billList.get(i).getDateCharged().before(endDate))
                    && !billList.get(i).isIsPaid()) {
                filteredList.add(billList.get(i));
            }
        }
        return filteredList;
    }
    //------------------------Request Lists-------------------------------//
    
    //---------------------Request Single Model---------------------//
    public static ArrayList<BllchrgModel> requestBllchrgList(int rsvID){
         ArrayList<BllchrgModel> reqList = new ArrayList<>();
         
          ArrayList<BllchrgModel> billList = requestAll_BllchrgData();
         
         for(int i = 0; i < billList.size(); i++ ){
             if (billList.get(i).getReservation().getRsvID() == rsvID){
                 reqList.add(billList.get(i));
             }
         }
         return reqList;
     }
    
    
    public static BllchrgModel requestRSV(int billID){
         BllchrgModel reqBill = new BllchrgModel();
         
          ArrayList<BllchrgModel> billList = requestAll_BllchrgData();
         
         for(int i = 0; i < billList.size(); i++ ){
             if (billList.get(i).getBllchrgID() == billID){
                 reqBill = (billList.get(i));
             }
         }
         return reqBill;
     }
     //---------------------<END> Request Single Model---------------------//
}
