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
    public static void generateAccmBill(ReservationModel reservation) {
        LocalDate processDate = reservation.getDateArrive();
        LocalDate departDate = reservation.getDateDepart();
        LocalDate currentToday = LocalDate.now();
        double totalCharge = 0;
        final String descLine = "Date Charged: ";

        for (int i = 0; processDate.plusDays(i).isBefore(departDate.plusDays(1)); i++) {
            RateModel currentRate = RateDriver.searchByDate(reservation.getDateArrive());
            double dayCharge = RateDriver.returnReservationTypeRate(currentRate, reservation.getReservationType());
            totalCharge += dayCharge;

        }

        int currentBillID = BillChargeDriver.createBillChargeReturnID(currentToday, totalCharge,
                descLine + LocalDate.now().toString());

        BillChargeDriver.attachReservation(currentBillID, reservation.getReservationID());
        ReservationDriver.attachBillCharge(reservation.getReservationID(), currentBillID);

    }
      public static double processAccmBill(ReservationModel reservation) {
        
//        Return ccInfo, and accom.bill
//*	User enters payment accepted
//*	Enter date paid and flag is paid, bllchrgs and rsv
        generateAccmBill(reservation);
        String currentCC = reservation.getGuest().getCCInfo();
        LocalDate currentDay = LocalDate.now();
        double totalCharge = 0;
        boolean paymentAccepted = false;
        System.out.println("The CC info entered: " +currentCC);
       
        if (!paymentAccepted) {
            System.out.println("Process payment successful, payment accepted on day"
            +currentDay);
           for (int i = 1; i < reservation.getListBillCharges().size(); i++) {
            totalCharge += reservation.getListBillCharges().get(i).getAmount();
            }
           reservation.setDatePaid(currentDay);
           reservation.setIsPaid(true);
        }
        else {
            System.out.println ("there was an error processing the CC entered");
            reservation.setIsPaid(false);
        }
        return totalCharge;
        
    }
    

    public static double printAccmBill(ReservationModel reservation) throws IOException {
       
        File accmbill = new File("AccomondationBill.txt");
        FileOutputStream accmbillreport = new FileOutputStream(accmbill);
        BufferedWriter accmbuf = new BufferedWriter(new OutputStreamWriter(accmbillreport));
        LocalDate currentDay = LocalDate.now();
        List<ReservationModel> reservations = new ArrayList<ReservationModel>();
        String accmBillInfo = "";
        double totalCharge=0;
        
        
        for (ReservationModel accmBillReservation : reservations) {
            for (int i = 1; i < reservation.getListBillCharges().size(); i++) {
            totalCharge += reservation.getListBillCharges().get(i).getAmount();}
           
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
                    + totalCharge 
                    + "Printed on: " + currentDay;
            accmbuf.write(accmBillInfo);
            accmbuf.newLine();
          }
        accmbuf.close();
        return totalCharge;
    }

    
    
   public static void applyCancelationCharge(ReservationModel reservation){
        final String penaltyApplied = "Cancelation penalty applied on: ";
        RateModel currentRate = RateDriver.searchByDate(reservation.getDateArrive());
        double dayCharge = 0;
        String tempString = penaltyApplied + LocalDate.now().toString();
        int currentBillID = 0;
        
        
         dayCharge = RateDriver.returnNoShowRate(currentRate);
            currentBillID = BillChargeDriver.createBillChargeReturnID(LocalDate.now(), dayCharge, tempString);
            //BillChargeModel currentBillCharge =BillChargeDriver.searchByID(currentBillID);
            BillChargeDriver.attachReservation(currentBillID, reservation.getReservationID());
            ReservationDriver.attachBillCharge(reservation.getReservationID(), currentBillID);
           
            reservation.setIsConcluded(true);
            
            //*****Call Payment Processing****
            reservation.setIsPaid(true);
            reservation.setRoom(RoomModel.EMPTY_ENTITY);
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
        double totalCharge = 0;
        String tempString = penaltyApplied + LocalDate.now().toString();
        String tempString2 = modApplied + LocalDate.now().toString();
        int currentBillID = 0;

        if (reservation.isNoShow()) {

            for (BillChargeModel bill : reservation.getListBillCharges()) {
                totalCharge += bill.getAmount();
            }
            currentBillID = BillChargeDriver.createBillChargeReturnID(LocalDate.now(), (-1 *totalCharge), tempString);
             BillChargeDriver.attachReservation(currentBillID, reservation.getReservationID());
            ReservationDriver.attachBillCharge(reservation.getReservationID(), currentBillID);
            
            dayCharge = RateDriver.returnNoShowRate(currentRate);
            currentBillID = BillChargeDriver.createBillChargeReturnID(LocalDate.now(), dayCharge, tempString);
            //BillChargeModel currentBillCharge =BillChargeDriver.searchByID(currentBillID);
            BillChargeDriver.attachReservation(currentBillID, reservation.getReservationID());
            ReservationDriver.attachBillCharge(reservation.getReservationID(), currentBillID);
           
            reservation.setIsConcluded(true);
            
            //*****Call Payment Processing****
            reservation.setIsPaid(true);
            reservation.setRoom(RoomModel.EMPTY_ENTITY);
        } //else if modified
        else {

            for (int i = 0; processDate.plusDays(i).isBefore(reservation.getDateDepart().plusDays(1)); i++) {
                totalCharge += RateDriver.returnReservationModifyRate(currentRate);
            }

            double sum = 0;

            for (BillChargeModel bill : reservation.getListBillCharges()) {
                sum += bill.getAmount();
            }

            // wont be daycharge 
            totalCharge = (sum < totalCharge) ? (totalCharge - sum)
                    : 0;

            currentBillID = BillChargeDriver.createBillChargeReturnID(LocalDate.now(), totalCharge, tempString2);
            BillChargeDriver.attachReservation(currentBillID, reservation.getReservationID());
            ReservationDriver.attachBillCharge(reservation.getReservationID(), currentBillID);

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

    //public static void 
}
