/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.logic.schedulers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import prototype.data.persistence.EntityDatabase;
import prototype.data.drivers.RateDriver;
import prototype.data.drivers.RoomDriver;
import prototype.data.models.RoomModel;
import prototype.data.drivers.ReservationDriver;
import prototype.data.models.RateModel;
import prototype.data.models.ReservationModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import prototype.data.drivers.BillChargeDriver;
import prototype.data.models.BillChargeModel;

/**
 *
 * @author jon
 */
public class ReportGeneration implements Scheduler {

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
    
    public static void writeOccupancyReport() throws IOException {
    File fout = new File("OccupancyReport.txt");
    FileOutputStream fos = new FileOutputStream(fout);

    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    LocalDate currentDay = LocalDate.now();
    int prepaid =0, sixtyday =0, conventional =0, incentive =0, rooms = 0, total =0;
    String lineContents = "";
    List<ReservationModel> reservations = new ArrayList<ReservationModel>();

    // get all the reservations for the nxt 30 days and write a line to the occupancy report
    // for each day containing all the specified information
    for(int i = 1; i < 31; i++){
        reservations = ReservationDriver.searchByDate(currentDay.plusDays(i));
        lineContents += currentDay.plusDays(i) + " || ";

        for (ReservationModel reservation: reservations){
            if(reservation.getReservationType().toString() == "prepaid")
                prepaid++;
            else if(reservation.getReservationType().toString() == "sixtyDay")
                sixtyday++;
            else if(reservation.getReservationType().toString() == "conventional")
                conventional++;               
            else if(reservation.getReservationType().toString() == "incentive")
                incentive++;

            rooms++;
        }

        // create the string that will be written to the report file
        lineContents += "Number of prepaid reservations: " + prepaid + 
                " || Number of sixty-day reservations: " + sixtyday +
                " || Number of conventional reservations: " + conventional +
                " || Number of incentive reservations: " + incentive + 
                " || Number of rooms reserved: " + rooms;

        bw.write(lineContents);
        bw.newLine();

        // add to total and clear rooms and lineContents for next iteration of for loop
        total += rooms;
        rooms = 0;
        lineContents = "";
    }
    
    // get the average occupancy for the next 30 days and write it to file then save file
    total = total / 30;
    lineContents = "Average occupancy for the next 30 days: " + total;
    bw.write(lineContents);
    bw.newLine();
    bw.close();
    }
    
    public static void writeIncomeReport() throws IOException {
        File fout = new File("IncomeReport.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        LocalDate currentDay = LocalDate.now();
        int length = 0;
        double income =0, incomeTotalDaily = 0, incomeTotal = 0;
        String lineContents = "";
        List<ReservationModel> reservations = new ArrayList<ReservationModel>();
      
        // get all the reservations for the nxt 30 days and write a line to the occupancy report
        // for each day containing all the specified information
        for(int i = 1; i < 31; i++){
            reservations = ReservationDriver.searchByDate(currentDay.plusDays(i));
            lineContents += currentDay.plusDays(i) + " || ";

            for (ReservationModel reservation: reservations){
                
                length = ReservationDriver.getDayLength(reservation.getReservationID());
                List<BillChargeModel> bills = new ArrayList<BillChargeModel>();
                BillChargeModel bill = bills.get(0);

                income = (bill.getAmount()) / length; // get the amount charged for the reservation and divide it by number of 
                incomeTotalDaily += income;               
            }

            // create the string that will be written to the report file
            lineContents += "Income for the day: " + incomeTotalDaily;

            bw.write(lineContents);
            bw.newLine();

            // add to total and clear rooms and lineContents for next iteration of for loop
            incomeTotal += incomeTotalDaily;
            lineContents = "";
        }

        // line 31 
        lineContents = "Total income for the next 30 days: " + incomeTotal;
        bw.write(lineContents);
        bw.newLine();
        
        // line 32
        incomeTotal = incomeTotal / 30;
        lineContents = "Average income for the next 30 days: " + incomeTotal;
        bw.write(lineContents);
        bw.newLine();
        bw.close();
    }
    
    //TODO: the report needs to be sorted by guest name still
    public static void writeDailyArrivalsReport() throws IOException {
        File fout = new File("DailyArrivalsReport.txt");
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        LocalDate currentDay = LocalDate.now();
        String lineContents = "";
        List<ReservationModel> reservations = new ArrayList<ReservationModel>();

        reservations = ReservationDriver.searchByDateArrive(currentDay);

        reservations.sort((x, y) -> x.getGuest().getName().compareTo(y.getGuest().getName()));
        //Collections.sort(reservations, Collections.reverseOrder());
        for (ReservationModel reservation: reservations){
            lineContents = "Guest Name: " + reservation.getGuest().getName() +
                    " || Reservation Type: " + reservation.getReservationType().toString() +
                    " || Room ID: " + reservation.getRoom().getRoomID() + 
                    " || Departure Date: " + reservation.getDateDepart().toString();
            bw.write(lineContents);
            bw.newLine();
        }

        bw.close();
    }
    
    //TODO: the report needs to be sorted by room number and maybe remove no shows?
    public static void writeDailyOccupancyReport() throws IOException {
        File fout = new File("DailyOccupancyReport.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        LocalDate currentDay = LocalDate.now();
        String lineContents = "";
        List<ReservationModel> reservations = new ArrayList<ReservationModel>();
        reservations = ReservationDriver.searchByDate(currentDay);
        reservations.sort((x, y) -> Short.toString(x.getRoom().getRoomID()).compareTo(Short.toString(y.getRoom().getRoomID())));
        for (ReservationModel reservation: reservations){
            lineContents = "Room ID: " + reservation.getRoom().getRoomID();
            if(reservation.getDateDepart() != currentDay){
                lineContents += " || Guest Name: " + reservation.getGuest().getName() +
                        " || Departure Date: " + reservation.getDateDepart().toString();
            }
            else{
                lineContents +=" || Guest Name: *" + reservation.getGuest().getName();
            }                                       
            bw.write(lineContents);
            bw.newLine();
        }

        bw.close();
    }
    
}