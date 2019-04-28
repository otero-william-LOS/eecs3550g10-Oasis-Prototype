/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.drivers;

import java.util.ArrayList;
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

    public static ArrayList<BillChargeModel> searchByDatePaid(LocalDate DatePaid) {
        ArrayList<BillChargeModel> matchingDatePaid = new ArrayList<BillChargeModel>();
        EntityDatabase.BillChargeTable.searchByDatePaid(DatePaid);
        return matchingDatePaid;
    }

    //Create BllCharge for 3 different constructors
    public static void createBllChrg(LocalDate DateCharged, double amount, String lineDesc) {
        EntityDatabase.BillChargeTable.addBllCharge(DateCharged, amount, lineDesc);
    }

    public static void createBllChrg(LocalDate DateCharged, double amount) {
        EntityDatabase.BillChargeTable.addBllCharge(DateCharged, amount);
    }

    public static void createBllChrg(LocalDate DateCharged) {
        EntityDatabase.BillChargeTable.addBllCharge(DateCharged);
    }

    //search by reservation, or bill charge ID or the date charged or paid
    public static List<BillChargeModel> searchByReservation(ReservationModel rsv) {
        List<BillChargeModel> matchingRsv = new ArrayList<>();
        matchingRsv = EntityDatabase.BillChargeTable.searchByReservation(rsv.getReservationID());
        return matchingRsv;
    }

    public static List<BillChargeModel> searchByBllChrgID(int bllchrgID) {
        List<BillChargeModel> matchingBllChrgID = new ArrayList<>();
        matchingBllChrgID = EntityDatabase.BillChargeTable.searchByBllChrgID(bllchrgID);
        return matchingBllChrgID;
    }

    public static List<BillChargeModel> searchByDateCharged(LocalDate DateCharged) {
        List<BillChargeModel> matchingDateCharged = new ArrayList<>();
        matchingDateCharged =EntityDatabase.BillChargeTable.searchByDateCharged(DateCharged);
        return matchingDateCharged;
    }

    public static void flagIsPaid(BillChargeModel bill) {
        bill.setIsPaid(true);
    }
    
    public static void flagIsPaid(int billID) {
        BillChargeModel bill = EntityDatabase.BillChargeTable.getBillCharge(billID);    
        bill.setIsPaid(true);
    }

    // asignRsvToBllchrg
    public static void asignRsvToBllchrg(BillChargeModel bllchrgID, ReservationModel rsv) {
        bllchrgID.setReservation(rsv);
    }

    public static List<BillChargeModel> rtrnAllPaidChrgs() {
        return EntityDatabase.BillChargeTable.retrieveAllPaidchrgs();
    }

    //Retrieve billing data
    public static List<BillChargeModel> rtrnAllBllChrgs() {
        return EntityDatabase.BillChargeTable.retrieveAllBllchrgs();
    }

    public static List<BillChargeModel> rtrnAllUnpaidChrgs() {
        return EntityDatabase.BillChargeTable.retrieveAllUnpaidchrgs();
    }
    
    public static void modifyBillChargeLineDesc(int billID){
        BillChargeModel bill = EntityDatabase.BillChargeTable.getBillCharge(billID);
        String lineDesc = bill.getLineDescription();
        lineDesc += " REMOVED";
        bill.setLineDescription(lineDesc);            
    }
}
