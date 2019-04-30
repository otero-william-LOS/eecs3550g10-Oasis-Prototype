/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.drivers;

import java.time.LocalDate;
import java.util.List;
import prototype.data.persistence.EntityDatabase;
import prototype.data.models.BillChargeModel;
import prototype.data.models.ReservationModel;

/**
 *
 * @author wotero
 */
public class BillChargeDriver implements DataDriver {

    //Create BllCharge for 3 different constructors
    public static void createBillCharge(LocalDate dateCharged, double amount, String lineDesc) {
        EntityDatabase.BillChargeTable.addBillCharge(dateCharged, amount, lineDesc);
    }
    public static void createBillCharge(LocalDate dateCharged, double amount) {
        EntityDatabase.BillChargeTable.addBillCharge(dateCharged, amount);
    }
    public static void createBillCharge(LocalDate dateCharged) {
        EntityDatabase.BillChargeTable.addBillCharge(dateCharged);
    }
    public static int createBillChargeReturnID(LocalDate dateCharged) {
        return EntityDatabase.BillChargeTable.addBillChargeReturnID(dateCharged);
    }
    public static int createBillChargeReturnID(LocalDate dateCharged, double amount) {
        return EntityDatabase.BillChargeTable.addBillChargeReturnID(dateCharged, amount);
    }
    public static int createBillChargeReturnID(LocalDate dateCharged, double amount, String lineDesc) {
        return EntityDatabase.BillChargeTable.addBillChargeReturnID(dateCharged, amount, lineDesc);
    }

    //search by reservation, or bill charge ID or the date charged or paid
    public static BillChargeModel searchByID(int bllchrgID) {
        //return matchingBllChrgID;
        return EntityDatabase.BillChargeTable.retrieveByID(bllchrgID);
    }
    public static List<BillChargeModel> searchByReservation(ReservationModel rsv) {
        //return matchingRsv;
        return EntityDatabase.BillChargeTable.retrieveByReservation(rsv.getReservationID());
    }
    public static List<BillChargeModel> searchByDateCharged(LocalDate dateCharged) {
        //return matchingDateCharged;
        return EntityDatabase.BillChargeTable.retrieveByDateCharged(dateCharged);
    }
    public static List<BillChargeModel> searchByDatePaid(LocalDate datePaid) {
        //return matchingDatePaid;
        return EntityDatabase.BillChargeTable.retrieveByDatePaid(datePaid);
    }

    public static void flagAsPaid(BillChargeModel bill) {
        bill.setIsPaid(true);
    }
    public static void flagAsPaid(int billID) {
        BillChargeModel bill = EntityDatabase.BillChargeTable.retrieveByID(billID);    
        flagAsPaid(bill);
    }

    // attach RsvToBllchrg
    public static void attachReservation(BillChargeModel bllchrg, ReservationModel rsv) {
        bllchrg.setReservation(rsv);
    }
    public static void attachReservation(int bllchrgID, ReservationModel rsv) {
        BillChargeModel bllchrg = EntityDatabase.BillChargeTable.retrieveByID(bllchrgID);
        BillChargeDriver.attachReservation(bllchrg, rsv);
    }
    public static void attachReservation(int bllchrgID, int rsvID) {
        BillChargeModel bllchrg = EntityDatabase.BillChargeTable.retrieveByID(bllchrgID);
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        BillChargeDriver.attachReservation(bllchrg, rsv);
    }
    
    //Retrieve billing data
    public static List<BillChargeModel> returnAllBillCharges() {
        return EntityDatabase.BillChargeTable.retrieveAllBillCharges();
    }
    public static List<BillChargeModel> returnAllPaidCharges() {
        return EntityDatabase.BillChargeTable.retrieveAllPaidCharges();
    }
    public static List<BillChargeModel> returnAllUnpaidCharges() {
        return EntityDatabase.BillChargeTable.retrieveAllUnpaidCharges();
    }
    
    public static void modifyBillChargeLineDesc(BillChargeModel bllchrg){
        String lineDesc = bllchrg.getLineDescription();
        lineDesc += " REMOVED";
        bllchrg.setLineDescription(lineDesc);            
    }
    public static void modifyBillChargeLineDesc(int bllchrgID){
        BillChargeModel bllchrg = EntityDatabase.BillChargeTable.retrieveByID(bllchrgID);
        modifyBillChargeLineDesc(bllchrg);
    }
}
