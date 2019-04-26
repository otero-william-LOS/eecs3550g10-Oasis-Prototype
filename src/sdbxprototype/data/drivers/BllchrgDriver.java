/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdbxprototype.data.drivers;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import sdbxprototype.data.DevDatabase;
import sdbxprototype.data.models.BillChargeModel;
import sdbxprototype.data.models.ReservationModel;

/**
 *
 * @author wotero
 */
public class BllchrgDriver implements DataDriver {

    public static ArrayList<BillChargeModel> searchByDatePaid(BillChargeModel DatePaid) {
        ArrayList<BillChargeModel> matchingDatePaid = new ArrayList<BillChargeModel>();
        DevDatabase.searchByDatePaid(DatePaid);
        return matchingDatePaid;
    }

    //Create BllCharge for 3 different constructors
    public static void createBllChrg(Date DateCharged, double amount, String lineDesc) {
//        DevDatabase.addBllCharge(DateCharged, amount, lineDesc);
    }

    public static void createBllChrg(Date DateCharged, double amount) {
//        DevDatabase.addBllCharge(DateCharged, amount);
    }

    public static void createBllChrg(Date DateCharged) {
//        DevDatabase.addBllCharge(DateCharged);
    }

    //search by reservation, or bill charge ID or the date charged or paid
    public static ArrayList<BillChargeModel> searchByReservation(ReservationModel rsv) {
        ArrayList<BillChargeModel> matchingRsv = new ArrayList<BillChargeModel>();
        DevDatabase.searchByReservation(rsv);
        return matchingRsv;
    }

    public static ArrayList<BillChargeModel> searchByBllChrgID(BillChargeModel bllchrgID) {
        ArrayList<BillChargeModel> matchingBllChrgID = new ArrayList<BillChargeModel>();
        DevDatabase.searchByBllChrgID(bllchrgID);
        return matchingBllChrgID;
    }

    public static ArrayList<BillChargeModel> searchByDateCharged(BillChargeModel DateCharged) {
        ArrayList<BillChargeModel> matchingDateCharged = new ArrayList<BillChargeModel>();
        DevDatabase.searchByDateCharged(DateCharged);
        return matchingDateCharged;
    }

    public static void flagIsPaid(int primaryKey) {
        DevDatabase.flagBillIsPaid(primaryKey);
    }
    public String ModifyBilling;
    private final DevDatabase devDB = new DevDatabase();

    // asignRsvToBllchrg
    public void asignRsvToBllchrg(BillChargeModel bllchrgID, ReservationModel rsv) {
        bllchrgID.setReservation(rsv);
    }

    public List<BillChargeModel> rtrnAllPaidChrgs() {
        return devDB.retrieveAllPaidchrgs();
    }

    //Retrieve billing data
    public List<BillChargeModel> rtrnAllBllChrgs() {
        return devDB.retrieveAllBllchrgs();
    }

    //Modify Bill Charges
    public void ModifyBilling(Date DateCharged, Date DatePaid, double amount, String lineDesc) {
        this.ModifyBilling = ModifyBilling;
    }

    public List<BillChargeModel> rtrnAllUnpaidChrgs() {
        return devDB.retrieveAllUnpaidchrgs();
    }
    
}
