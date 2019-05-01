/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.logic.schedulers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import prototype.data.drivers.RateDriver;
import prototype.data.models.RateModel;
import prototype.data.models.ReservationModel;
import java.time.LocalDate;
import prototype.data.persistence.EntityDatabase;
import prototype.data.drivers.RateDriver;
import prototype.data.drivers.RoomDriver;
import prototype.data.models.RoomModel;
import prototype.data.drivers.ReservationDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import prototype.data.drivers.BillChargeDriver;
import prototype.data.models.BillChargeModel;
import prototype.data.models.ReservationType;

/**
 *
 * @author jon
 */
public class PaymentProcessing implements Scheduler {

    /* The accomodation bill report needs to have the date the bill was printed, 
        guest name, room number, arrival date, departure date, 
        number of nights, total charge. For prepaid and 60-day reservations, 
        the billreflect the date on which it was paid in advance with amount */
    public static double generateAccmBill(ReservationModel reservation) {
        LocalDate processDate = reservation.getDateArrive();
        LocalDate departDate = reservation.getDateDepart();
        LocalDate currentToday = LocalDate.now();
        double totalCharge = 0;
        final String descLine = "Date Charged: ";
        final String penaltyApplied = "No show penalty applied on: ";
        final String modApplied = " rsv modification penalty applied on: ";
        boolean run = false;
        boolean isPopulated = false;
        
        if (!isSizeNull)
        isPopulated = !(descLine + (departDate.toString()))
                .equals(reservation.getListBillCharges()
                        .get(reservation.getListBillCharges().size() - 1)
                        .getLineDescription());
        
        //if statement to handle duplicates
        if (reservation.getListBillCharges().isEmpty()) {
            run = true;
        }
        if (run != true && !isPopulated) {
            run = true;
        }
        //do we need a if statement if Arrival Date got changes as well?
        if (run) {
            for (int i = 0; processDate.plusDays(i).isBefore(departDate.plusDays(1)); i++) {
                RateModel currentRate = RateDriver.searchByDate(reservation.getDateArrive());
                double dayCharge = RateDriver.returnReservationTypeRate(currentRate, reservation.getReservationType());
                totalCharge += dayCharge;
                String tempString = descLine + processDate.toString();
                int currentBillID = BillChargeDriver.createBillChargeReturnID(currentToday, dayCharge, tempString);
                //BillChargeModel currentBillCharge =BillChargeDriver.searchByID(currentBillID);
                BillChargeDriver.attachReservation(currentBillID, reservation.getReservationID());
                ReservationDriver.attachBillCharge(reservation.getReservationID(), currentBillID);
            }

        } else if (isPopulated) {
            int lastOf = 1;
            for (int c = 0; c < reservation.getListBillCharges().size(); c++) {
                if ((descLine + processDate.toString()).equals(reservation.getListBillCharges().get(c).getLineDescription())) {
                lastOf = c;
            } 
                else if ((modApplied + processDate.toString()).equals(reservation.getListBillCharges().get(c).getLineDescription())) {
            
                lastOf = c;
                }
                else if ((penaltyApplied + processDate.toString()).equals(reservation.getListBillCharges().get(c).getLineDescription())) {
                lastOf = c;
                }
            }
            for (int i = lastOf; i < reservation.getListBillCharges().size(); i++) {
                totalCharge += reservation.getListBillCharges().get(i).getAmount();
            }
        }
        return totalCharge;

    }

    public static void processAccmBill(ReservationModel reservation){
        // check if 1st bill day arrived day varies. Then void all the unmatched
        // days until you reach DateArrive that match
       
        
        // Call GenerateAccmBill to apply the total charge

    }

    public static void printAccmBill(ReservationModel reservation) throws IOException {
        /*This is a systmem.out print, wasns't sure how we are suppose to do it
        double charge = 0.0;
        
        for (int i=0; i <reservation.getListBillCharges().size() ; i++) {
         System.out.println("the following are bill charges for the reservation");
         System.out.println(reservation.getListBillCharges().get(i).toString());
         charge += reservation.getListBillCharges().get(i).getAmount();}
            System.out.println("The charge is" +charge);
         */
        File accmbill = new File("AccomondationBill.txt");
        FileOutputStream accmbillreport = new FileOutputStream(accmbill);
        BufferedWriter accmbuf = new BufferedWriter(new OutputStreamWriter(accmbillreport));
        LocalDate currentDay = LocalDate.now();
        List<ReservationModel> reservations = new ArrayList<ReservationModel>();
        String accmBillInfo = "";

        for (ReservationModel accmBillReservation : reservations) {
            accmBillInfo += " Accomndation Bill for Guest: "
                    + reservation.getGuest().getName()
                    + " with Reservation Type: "
                    + reservation.getReservationType().toString()
                    + " reserved room number: "
                    + reservation.getRoom().getRoomID()
                    + " Arrival Date:"
                    + reservation.getDateArrive().toString()
                    + " and  Departure Date: "
                    + reservation.getDateDepart().toString()
                    + " Total Charge is: "
                    + reservation.getListBillCharges()
                    + "Printed on: " + currentDay;
            accmbuf.write(accmBillInfo);
            accmbuf.newLine();
        }
        accmbuf.close();
    }

    public static void applyPenaltyCharge(ReservationModel reservation) {
        /* Charge the no show penalty if customer does not show on the first day
        Each morning the employee must generate penalty charges */
        //pick the first day of the list
        LocalDate processDate = reservation.getDateArrive();
        final String penaltyApplied = "No show penalty applied on: ";
        final String modApplied = " rsv modification penalty applied on: ";
        RateModel currentRate = RateDriver.searchByDate(reservation.getDateArrive());
        double dayCharge = 0;
        String tempString = penaltyApplied + processDate.toString();
        int currentBillID = 0;

        if (reservation.isNoShow()) {

            dayCharge = RateDriver.returnNoShowRate(currentRate);
            currentBillID = BillChargeDriver.createBillChargeReturnID(LocalDate.now(), dayCharge, tempString);
            //BillChargeModel currentBillCharge =BillChargeDriver.searchByID(currentBillID);
            BillChargeDriver.attachReservation(currentBillID, reservation.getReservationID());
            ReservationDriver.attachBillCharge(reservation.getReservationID(), currentBillID);
            /*
        Refund for arrival day +1 to depart day. 
        Send a message to state reinbursement
             */

            reservation.setIsConcluded(true);
            reservation.setIsPaid(true);
            reservation.setRoom(RoomModel.EMPTY_ENTITY);
        } //else if modified
        else {
            for (int i = 0; processDate.plusDays(i).isBefore(reservation.getDateDepart().plusDays(1)); i++) {

                dayCharge = RateDriver.returnReservationModifyRate(currentRate);
                currentBillID = BillChargeDriver.createBillChargeReturnID(LocalDate.now(), dayCharge, tempString);
                BillChargeDriver.attachReservation(currentBillID, reservation.getReservationID());
                ReservationDriver.attachBillCharge(reservation.getReservationID(), currentBillID);

            }
        }
    }

    @Override
    public void openModule() {
        //  TBD; possible future dev
    }

    @Override
    public void closeModule() {
        //  TBD; possible future dev
    }

    @Override
    public void outputModuleMetrics() {
        //  TODO will help with module unit testing
    }

    @Override
    public void runModuleUserStories() {
        //  TODO will be updated as new user stories are created.
    }

}
