/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import prototype.data.persistence.EntityDatabase;
import prototype.data.drivers.RateDriver;
import prototype.data.drivers.RoomDriver;
import prototype.data.drivers.GuestDriver;
import prototype.data.models.GuestModel;
import prototype.data.models.RoomModel;
import prototype.data.models.ReservationModel;
import prototype.data.drivers.ReservationDriver;
import prototype.data.models.RateModel;
import prototype.data.models.ReservationModel;
import prototype.logic.schedulers.ReportGeneration;
import java.io.IOException;
import java.time.LocalDate;
import prototype.data.models.ReservationType;
import prototype.logic.schedulers.RateScheduling;


/**
 *
 * @author wotero
 */
public class UserStoryPrototype {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //<editor-fold defaultstate="collapsed" desc="Room Driver Test Scripts">
        System.out.println("Testing Gen All Empty");
        EntityDatabase.DevUtilities.generateRoomTableAllAvailable();
        System.out.println("\tRequesting All Rooms");
        List<RoomModel> rmList = RoomDriver.returnAllRooms();
        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        
//        rmList.clear();
//        System.out.println();
//        
//        System.out.println("Testing Gen All Occupied");
//        EntityDatabase.genRoomTblAllOccupied();
//        System.out.println("\tRequesting All Rooms");
//        rmList = RoomDriver.rtrnAllRooms();
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        
//        rmList.clear();
//        System.out.println();
//        
//        System.out.println("Testing Gen Partial Occupied");
//        EntityDatabase.genRoomTblPartialOccupied(5);
//        System.out.println("\tRequesting All Rooms");
//        rmList = RoomDriver.rtrnAllRooms();
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        
//        rmList.clear();
//        System.out.println();
//        
//        System.out.println("Next Using Partial Scenario");
//        System.out.println("\tPrint Available Rooms:");
//        rmList = RoomDriver.rtrnAvailableRooms();
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        System.out.println("\tPrint Occupied Rooms (no Rsv yet):");
//        rmList = RoomDriver.rtrnOccupiedRooms();
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        
//        System.out.println();
//        
//        System.out.println("Testing DEV-only gen Rsvs");
//        EntityDatabase.genRsvTblBase(10);
//        System.out.println("\tRequesting All Rsvs");
//        List<RsvModel> rsvList = RoomDriver.devRtrnAllRsvs();
//        rsvList.forEach(rsv -> System.out.println("\t\t" + rsv));
//        
//        System.out.println();
//        
//        System.out.println("Linking the initial occupied rooms to a rsv sequentially");
//        for (int i = 0; i < rmList.size(); i++)
//            RoomDriver.devLinkRsvAndRoom(rsvList.get(i), rmList.get(i));
//        System.out.println("\tPrint Occupied Rooms:");
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        System.out.println("\tPrint All Rsvs:");
//        rsvList.forEach(rsv -> System.out.println("\t\t" + rsv));
//        
//        rmList.clear();
//        System.out.println();
//        
//        System.out.println("Assign remaining Rsv to a random available room");
//        Random rndm = new Random();
//        rmList = RoomDriver.rtrnAvailableRooms();
//        for(int i = 5; i < rsvList.size(); i++) {
//            int x = rndm.nextInt(rmList.size());
//            System.out.println("\tLink and Remove Random Available Room @Index: " + Integer.toString(x));
//            RoomDriver.devLinkRsvAndRoom(rsvList.get(i), rmList.get(x));
//            RoomDriver.flagRoomAsOccupied(rmList.get(x));
//            rmList.remove(x);
//        }
//        System.out.println("\tPrint Remaining Available Rooms:");
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        rmList.clear();
//        System.out.println("\tPrint Occupied Rooms:");
//        rmList = RoomDriver.rtrnOccupiedRooms();
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        System.out.println("\tPrint All Rsvs:");
//        rsvList.forEach(rsv -> System.out.println("\t\t" + rsv));
//        rmList.clear();
//        System.out.println("\tPrint All Rooms:");
//        rmList = RoomDriver.rtrnAllRooms();
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        
//        System.out.println();
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Reservation Driver Test Stuff">
               Random rand = new Random();
                
                 ArrayList<ReservationModel> reservationTable = new ArrayList();
                 for(int i = 0; i < 40; i++){
                  ReservationModel reservation = new ReservationModel();
                  reservationTable.add(reservation);
                 }
                
                 // create two day stays
                 for (int i = 0; i < 20; i++){
                    ReservationModel reservation = reservationTable.get(i);
                    int start = rand.nextInt(50) + 1;
                    LocalDate startDate = LocalDate.now();
                    LocalDate endDate = LocalDate.now().plusDays(2);
                  reservation.setDateArrive(startDate);
                    reservation.setDateDepart(endDate);
                    
                    reservationTable.set(i, reservation);
                 }
              // create 5 day stays
                 for (int i = 20; i < 40; i++){
                    ReservationModel reservation = reservationTable.get(i);
                    int start = rand.nextInt(50) + 1;
                    LocalDate startDate = LocalDate.now();
                    LocalDate endDate = LocalDate.now().plusDays(5);
                   
                    reservation.setDateArrive(startDate);
                   reservation.setDateDepart(endDate);
                    
                    reservationTable.set(i, reservation);
                 }
                 
                 String guestName = "";
                 final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
                 final int N = lexicon.length();


                
                 
                for (ReservationModel rsv : reservationTable) {
                    Random r = new Random();
                    for (int i = 0; i < 10; i++) {
                        guestName += lexicon.charAt(r.nextInt(N));
                    }
                    int id = GuestDriver.createGuestReturnID(guestName, guestName);
                    int rsvId = ReservationDriver.createReservationReturnID(rsv.getDateArrive(), rsv.getDateDepart());
                    ReservationDriver.attachGuest(id, GuestDriver.searchByID(id));
                    ReservationDriver.modifyReservationType(rsvId, ReservationType.CONVENTIONAL);
                    
                    ReservationDriver.attachRoom(rsvId, 1);
                    RoomDriver.attachReservation(1, ReservationDriver.searchByID(rsvId));
                    GuestModel guestperson = GuestDriver.searchByID(id);
                    List<ReservationModel> newRSV = new ArrayList<>();
                    newRSV.add(ReservationDriver.searchByID(rsvId));
                    guestperson.setListRsv(newRSV);
                    guestName = "";

                }
        //         
        //         Date lookFor = new Date();
        //         Calendar c = Calendar.getInstance(); 
        //         c.setTime(lookFor); 
        //         c.add(Calendar.DATE, 3);
        //         lookFor = c.getTime();
        //            
        //         ArrayList<RsvModel> matchingRsvs = ReservationDriver.searchByDateArriveBtwn(lookFor);
        //         
        //         c.setTime(lookFor); 
        //         c.add(Calendar.DATE, 10);
        //         lookFor = c.getTime();
        //         matchingRsvs.addAll(ReservationDriver.searchByDateArriveBtwn(lookFor));
        //         
        //         c.setTime(lookFor); 
        //         c.add(Calendar.DATE, 20);
        //         lookFor = c.getTime();
        //         matchingRsvs.addAll(ReservationDriver.searchByDateArriveBtwn(lookFor));
        //         
        //         c.setTime(lookFor); 
        //         c.add(Calendar.DATE, 30);
        //         lookFor = c.getTime();
        //         matchingRsvs.addAll(ReservationDriver.searchByDateArriveBtwn(lookFor));
        //         
        //         c.setTime(lookFor); 
        //         c.add(Calendar.DATE, 25);
        //         lookFor = c.getTime();
        //         matchingRsvs.addAll(ReservationDriver.searchByDateArriveBtwn(lookFor));
        //</editor-fold>
        
       //<editor-fold defaultstate="collapsed" desc="Guest Shit">
       
       //<editor-fold defaultstate="collapsed" desc="Guest Shit">

        //new rate driver test scripts
        System.out.println("Testing Gen Rates for Year Method");
        EntityDatabase.DevUtilities.genRateTableForYear();
        System.out.println("\tRequesting All Rates");
        List<RateModel> rtList = EntityDatabase.RateTable.retriveAllRates();
        rtList.forEach(rt -> System.out.println("\t\t" + rt));
        System.out.println("\tNumber of Rates:" + Integer.toString(rtList.size()));
        
        try {
            ReportGeneration.writeDailyArrivalsReport();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        try {
            ReportGeneration.writeOccupancyReport();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        try {
            ReportGeneration.writeDailyOccupancyReport();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        try {
            ReportGeneration.writeIncomeReport();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
        try {
            ReportGeneration.writeIncentiveReport();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        
        RateScheduling scheduler = new RateScheduling();
        String returnVal = scheduler.setRate(LocalDate.now(), 6969);
        returnVal = scheduler.setRate(LocalDate.now().minusDays(1), 6969);
        returnVal = scheduler.setRate(LocalDate.now().plusDays(400), 6969);
        returnVal = scheduler.setRateRange(LocalDate.now(), LocalDate.now().plusDays(5), 6969);
        returnVal = scheduler.setRateRange(LocalDate.now().plusDays(400), LocalDate.now().plusDays(405), 6969);
        returnVal = scheduler.setRateRange(LocalDate.now().plusDays(1), LocalDate.now(), 6969);
        int i;
        
        
    }
    
}
