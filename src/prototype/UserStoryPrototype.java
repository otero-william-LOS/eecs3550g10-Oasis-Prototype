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
import prototype.data.models.RoomModel;
import prototype.data.drivers.ReservationDriver;
import prototype.data.models.RateModel;
import prototype.data.models.ReservationModel;

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
//        System.out.println("Testing Gen All Empty");
//        EntityDatabase.genRoomTblAllAvailable();
//        System.out.println("\tRequesting All Rooms");
//        List<RoomModel> rmList = RoomDriver.rtrnAllRooms();
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
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
        //       Random rand = new Random();
        //        
        //         ArrayList<RsvModel> reservationTable = new ArrayList();
        //         for(int i = 0; i < 40; i++){
        //          ReservationModel reservation = new ReservationModel();
        //          reservationTable.add(reservation);
        //         }
        //         
        //         // create two day stays
        //         for (int i = 0; i < 20; i++){
        //            ReservationModel reservation = reservationTable.get(i);
        //            int start = rand.nextInt(50) + 1;
        //            Date startDate = new Date();
        //            Date endDate = new Date();
        //            Calendar c = Calendar.getInstance(); 
        //            c.setTime(startDate); 
        //            c.add(Calendar.DATE, start);
        //            startDate = c.getTime();
        //            
        //            c.setTime(endDate); 
        //            c.add(Calendar.DATE, start + 2);
        //            endDate = c.getTime();
        //            
        //            reservation.setDateArrive(startDate);
        //            reservation.setDateDepart(endDate);
        //            
        //            reservationTable.set(i, reservation);
        //         }
        //         
        //         // create 5 day stays
        //         for (int i = 20; i < 40; i++){
        //            ReservationModel reservation = reservationTable.get(i);
        //            int start = rand.nextInt(50) + 1;
        //            Date startDate = new Date();
        //            Date endDate = new Date();
        //            Calendar c = Calendar.getInstance(); 
        //            c.setTime(startDate); 
        //            c.add(Calendar.DATE, start);
        //            startDate = c.getTime();
        //            
        //            c.setTime(endDate); 
        //            c.add(Calendar.DATE, start + 5);
        //            endDate = c.getTime();
        //            
        //            reservation.setDateArrive(startDate);
        //            reservation.setDateDepart(endDate);
        //            
        //            reservationTable.set(i, reservation);
        //         }
        //         
        //         for (ReservationModel rsv : reservationTable) {
        //            ReservationDriver.createRSVC(rsv.getDateArrive(), rsv.getDateDepart());
        //        }
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

        //new rate driver test scripts
        System.out.println("Testing Gen Rates for Year Method");
        EntityDatabase.DevUtilities.genRateTableForYear();
        System.out.println("\tRequesting All Rates");
        List<RateModel> rtList = EntityDatabase.RateTable.retriveAllRates();
        rtList.forEach(rt -> System.out.println("\t\t" + rt));
        System.out.println("\tNumber of Rates:" + Integer.toString(rtList.size()));
    }
    
}
