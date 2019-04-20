/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdbxprototype;

import java.util.List;
import java.util.Random;
import sdbxprototype.data.DevDatabase;
import sdbxprototype.data.RoomDriver;
import sdbxprototype.data.RoomModel;
import sdbxprototype.data.RsvModel;

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

    }
    
}
