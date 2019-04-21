/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdbxprototype;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import sdbxprototype.data.DevDatabase;
import sdbxprototype.data.drivers.RoomDriver;
import sdbxprototype.data.models.RoomModel;
import sdbxprototype.data.drivers.RsvDriver;
import sdbxprototype.data.models.RsvModel;

/**
 *
 * @author wotero
 */
public class SdbxGitRepo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //<editor-fold defaultstate="collapsed" desc="Room Driver Test Scripts">
//        System.out.println("Testing Gen All Empty");
//        DevDatabase.genRoomTblAllAvailable();
//        System.out.println("\tRequesting All Rooms");
//        List<RoomModel> rmList = RoomDriver.rtrnAllRooms();
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        
//        rmList.clear();
//        System.out.println();
//        
//        System.out.println("Testing Gen All Occupied");
//        DevDatabase.genRoomTblAllOccupied();
//        System.out.println("\tRequesting All Rooms");
//        rmList = RoomDriver.rtrnAllRooms();
//        rmList.forEach(rm -> System.out.println("\t\t" + rm));
//        
//        rmList.clear();
//        System.out.println();
//        
//        System.out.println("Testing Gen Partial Occupied");
//        DevDatabase.genRoomTblPartialOccupied(5);
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
//        DevDatabase.genRsvTblBase(10);
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
//          RsvModel reservation = new RsvModel();
//          reservationTable.add(reservation);
//         }
//         
//         // create two day stays
//         for (int i = 0; i < 20; i++){
//            RsvModel reservation = reservationTable.get(i);
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
//            RsvModel reservation = reservationTable.get(i);
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
//         for (RsvModel rsv : reservationTable) {
//            RsvDriver.createRSVC(rsv.getDateArrive(), rsv.getDateDepart());
//        }
//         
//         Date lookFor = new Date();
//         Calendar c = Calendar.getInstance(); 
//         c.setTime(lookFor); 
//         c.add(Calendar.DATE, 3);
//         lookFor = c.getTime();
//            
//         ArrayList<RsvModel> matchingRsvs = RsvDriver.searchByDateArriveBtwn(lookFor);
//         
//         c.setTime(lookFor); 
//         c.add(Calendar.DATE, 10);
//         lookFor = c.getTime();
//         matchingRsvs.addAll(RsvDriver.searchByDateArriveBtwn(lookFor));
//         
//         c.setTime(lookFor); 
//         c.add(Calendar.DATE, 20);
//         lookFor = c.getTime();
//         matchingRsvs.addAll(RsvDriver.searchByDateArriveBtwn(lookFor));
//         
//         c.setTime(lookFor); 
//         c.add(Calendar.DATE, 30);
//         lookFor = c.getTime();
//         matchingRsvs.addAll(RsvDriver.searchByDateArriveBtwn(lookFor));
//         
//         c.setTime(lookFor); 
//         c.add(Calendar.DATE, 25);
//         lookFor = c.getTime();
//         matchingRsvs.addAll(RsvDriver.searchByDateArriveBtwn(lookFor));
//</editor-fold>

        //new rate driver test scripts
    }
    
}
