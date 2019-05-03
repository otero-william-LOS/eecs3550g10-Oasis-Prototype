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
import java.util.logging.Level;
import java.util.logging.Logger;
import prototype.data.drivers.BillChargeDriver;
import prototype.data.models.BillChargeModel;
import prototype.data.models.ReservationType;

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

    public static void makeMyFolder(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public static void writeOccupancyReport() {
        makeMyFolder("Reports");
        File fout = new File("Reports\\OccupancyReport.txt");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            LocalDate currentDay = LocalDate.now();

            // title line
            String lineContents = "Occupancy Report";
            bw.write(lineContents);
            bw.newLine();

            List<ReservationModel> reservations = new ArrayList<ReservationModel>();
            int total = 0;
            // get all the reservations for the nxt 30 days and write a line to the occupancy report
            // for each day containing all the specified information
            for (int i = 1; i < 31; i++) {
                int prepaid = 0, sixtyday = 0, conventional = 0, incentive = 0, rooms = 0;
                reservations = ReservationDriver.searchByDate(currentDay.plusDays(i));

                for (ReservationModel reservation : reservations) {
                    if (reservation.getReservationType().equals(ReservationType.PREPAID)) {
                        prepaid++;
                    } else if (reservation.getReservationType().equals(ReservationType.SIXTYADV)) {
                        sixtyday++;
                    } else if (reservation.getReservationType().equals(ReservationType.CONVENTIONAL)) {
                        conventional++;
                    } else if (reservation.getReservationType().equals(ReservationType.INCENTIVE)) {
                        incentive++;
                    }

                    rooms++;
                }

                // create the string that will be written to the report file
                lineContents = "Date: " + currentDay.plusDays(i).toString() + " || Prepaid reservations: " + prepaid
                        + " || Sixty-day reservations: " + sixtyday
                        + " || Conventional reservations: " + conventional
                        + " || Incentive reservations: " + incentive
                        + " || Rooms reserved: " + rooms;

                bw.write(lineContents);
                bw.newLine();

                // add to total and clear rooms and lineContents for next iteration of for loop
                total += rooms;
                rooms = 0;

            }

            // get the average occupancy for the next 30 days and write it to file then save file
            total = total / 30;
            lineContents = "Average occupancy for the next 30 days: " + total;
            bw.write(lineContents);
            bw.newLine();
            bw.close();

        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    public static void writeIncomeReport() {
        makeMyFolder("Reports");
        File fout = new File("Reports\\IncomeReport.txt");

        try {
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            LocalDate currentDay = LocalDate.now();
            int length = 0;
            double income = 0, incomeTotalDaily = 0, incomeTotal = 0;

            // title line
            String lineContents = "Expected Room Income Report";
            bw.write(lineContents);
            bw.newLine();
            List<ReservationModel> reservations = new ArrayList<ReservationModel>();

            // get all the reservations for the nxt 30 days and write a line to the occupancy report
            // for each day containing all the specified information
            for (int i = 1; i < 31; i++) {
                reservations = ReservationDriver.searchByDate(currentDay.plusDays(i));
                lineContents = "Date: " + currentDay.plusDays(i) + " || ";

                for (ReservationModel reservation : reservations) {
                    income++;
                }

                income = income * RateDriver.searchByDate(currentDay).getBaseRate();
                incomeTotal = incomeTotal + income;

                // create the string that will be written to the report file
                lineContents += "Income for the day: " + income;

                bw.write(lineContents);
                bw.newLine();

                income = 0;

            }

            // line 31 
            lineContents = "Total income for the next 30 days: " + incomeTotal;
            bw.write(lineContents);
            bw.newLine();

            // line 32
            incomeTotal = incomeTotal / 30;
            lineContents = "Average income for the next 30 days: " + (incomeTotal / 30);
            bw.write(lineContents);
            bw.newLine();
            bw.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    //TODO: the report needs to be sorted by guest name still
    public static void writeDailyArrivalsReport() {
        makeMyFolder("Reports");
        File fout = new File("Reports\\DailyArrivalsReport.txt");

        try {
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            LocalDate currentDay = LocalDate.now();

            // title line
            String lineContents = "Daily Arrivals Report";
            bw.write(lineContents);
            bw.newLine();

            List<ReservationModel> reservations = new ArrayList<ReservationModel>();

            reservations = ReservationDriver.searchByDateArrive(currentDay);

            reservations.sort((x, y) -> x.getGuest().getName().compareTo(y.getGuest().getName()));
            //Collections.sort(reservations, Collections.reverseOrder());
            for (ReservationModel reservation : reservations) {
                lineContents = "Guest Name: " + reservation.getGuest().getName()
                        + " || Reservation Type: " + reservation.getReservationType().toString()
                        + " || Room ID: " + reservation.getRoom().getRoomID()
                        + " || Departure Date: " + reservation.getDateDepart().toString();
                bw.write(lineContents);
                bw.newLine();
            }

            bw.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    //TODO: the report needs to be sorted by room number and maybe remove no shows?
    public static void writeDailyOccupancyReport() {
        makeMyFolder("Reports");
        File fout = new File("Reports\\DailyOccupancyReport.txt");

        try {
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            LocalDate currentDay = LocalDate.now();

            // title line
            String lineContents = "Daily Occupancy Report";
            bw.write(lineContents);
            bw.newLine();

            List<ReservationModel> reservations = new ArrayList<ReservationModel>();
            for (ReservationModel rsv : ReservationDriver.returnAllReservations()) {
                if (rsv.getDateArrive().minusDays(1).isBefore(currentDay)
                        && rsv.getDateDepart().plusDays(1).isAfter(currentDay)) {
                    reservations.add(rsv);
                }
            }
            // reservations = ReservationDriver.searchByDate(currentDay);
            reservations.sort((x, y) -> Short.toString(x.getRoom().getRoomID()).compareTo(Short.toString(y.getRoom().getRoomID())));
            for (ReservationModel reservation : reservations) {
                lineContents = "Room ID: " + reservation.getRoom().getRoomID();
                if (reservation.getDateDepart() != currentDay) {
                    lineContents += " || Guest Name: " + reservation.getGuest().getName()
                            + " || Departure Date: " + reservation.getDateDepart().toString();
                } else {
                    lineContents += " || Guest Name: *" + reservation.getGuest().getName();
                }
                bw.write(lineContents);
                bw.newLine();
            }

            bw.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    public static void writeIncentiveReport() {
        double dailyLoss = 0, totalLoss = 0;
        LocalDate currentDay = LocalDate.now();
        String lineContents = "";

        makeMyFolder("Reports");
        File fout = new File("Reports\\IncentiveReport.txt");

        try {
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            // title line
            lineContents = "Incentive Report";
            bw.write(lineContents);
            bw.newLine();

            for (int i = 0; i < 30; i++) {
                List<ReservationModel> reservations = ReservationDriver.searchByDate(currentDay.plusDays(i));
                RateModel rate = RateDriver.searchByDate(currentDay.plusDays(i));

                dailyLoss = ((45 - reservations.size()) * rate.getBaseRate());
                totalLoss = totalLoss + dailyLoss;
                lineContents = "Date: " + currentDay.plusDays(i).toString() + " || Total Discount: " + dailyLoss;
                bw.write(lineContents);
                bw.newLine();
            }

            lineContents = "Total discount for the next 30 days: " + totalLoss;
            bw.write(lineContents);
            bw.newLine();

            lineContents = "Average discount for the next 30 days: " + (totalLoss / 30);
            bw.write(lineContents);
            bw.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }

    }

}
