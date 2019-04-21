/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DevDatabase;
import Models.BllchrgModel;
import Models.GuestModel;
import Models.RateModel;
import Models.RoomModel;
import Models.RsvModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author cmason12
 */
public class DevDatabase {
    
    private static final ArrayList<RsvModel> rsvTable = new ArrayList<>();
    private static final  ArrayList<RoomModel> roomTable = new ArrayList<>();
    private static final  ArrayList<RateModel> rateTable = new ArrayList<>();
    private static final  ArrayList<BllchrgModel> bllchrgTable = new ArrayList<>();
    private static final  ArrayList<GuestModel> guestTable = new ArrayList<>();
    
    /**
     *  DevDatabase Gen Methods for UnitTesting
     */
    
    //<editor-fold defaultstate="collapsed" desc="Rsv Dummy DevGeneration Methods">
    
    // SDBX: Gen Simple RsvModel with RsvID only
    public static void genRsvTblBase(int numRsv){
        rsvTable.clear();
        for(int i = 0; i < numRsv; i++) 
            rsvTable.add(new RsvModel(i + 1));
        
        System.out.println("\tNumber of rsv generated: " + Integer.toString(numRsv));
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Room Dummy DevGeneration Methods">
    
    // SDBX: Private base gen method for rooms
    private static void genRoomTableBase(boolean allOccupied, boolean partialOccupied, int numOccupied){
        roomTable.clear();
        
        for(short i = 0; i < 45; i++) {
            short x = (short)(i + 1);
            roomTable.add(new RoomModel(x, false));
        } //all rooms are available
        
        //debug info
        System.out.println("\tAll rooms are generated and set as available.");
        if (allOccupied) 
            System.out.println("\tAll rooms are set as occupied");
        else if (partialOccupied)
            System.out.println("\tPartial amount occupied: " + Integer.toString(numOccupied));
        
        if (allOccupied) 
            roomTable.forEach((room)->room.setIsOccupied(true));
        else if (partialOccupied){
            Random rndm = new Random();
            int cap = Math.min(numOccupied, 45);
            for(int i = 0; i < cap; i++) {
                int x = rndm.nextInt(45);
                if (!roomTable.get(x).getIsOccupied())
                    roomTable.get(x).setIsOccupied(true);
                else
                    i--; // try again
                //debug info
                System.out.println("\tPartial Set Attempt Index: " + Integer.toString(x));
            }
        }
    }
    // SDBX: Accesible room generation methods
    public static void genRoomTblAllAvailable(){genRoomTableBase(false, false, 0);}
    public static void genRoomTblAllOccupied(){genRoomTableBase(true, false, 0);}
    public static void genRoomTblPartialOccupied(int numOccupied){
        genRoomTableBase(false, true, numOccupied);
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Rate Dummy DevGeneration Methods">
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Bllchrg Dummy DevGeneration Methods">
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Guest Dummy DevGeneration Methods">
    
//</editor-fold>
    
    /**
     *  DevDatabase Create & Retrieval
     */
    
    //<editor-fold defaultstate="collapsed" desc="Rsv Entity Create & Retrieval Methods">
    
    // SDBX: Retrieve Simple RsvModel Table DEV Method
    public static List<RsvModel> retrieveAllRsvs(){
        List<RsvModel> rsvs = new ArrayList<>(rsvTable);
        return rsvs;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Room Entity Create & Retrieval Methods">
    
    // Database RoomTbl Retrieval Methods
    public static List<RoomModel> rtrvAllRooms(){
        List<RoomModel> rooms = new ArrayList<>(roomTable);
        return rooms;
    }
    public static List<RoomModel> rtrvAvailableRooms(){
        List<RoomModel> rooms = new ArrayList<>(roomTable);
        rooms.removeIf(room -> room.getIsOccupied());
        return rooms;
    }
    public static List<RoomModel> rtrvOccupiedRooms(){
        List<RoomModel> rooms = new ArrayList<>(roomTable);
        rooms.removeIf(room -> !room.getIsOccupied());
        return rooms;
    }
    public static RoomModel rtrvByRsv(RsvModel queryRsv){
        // TODO: Warning 
        // - this method won't work if the unique Rsv and Room aren't linked
        RoomModel room = 
                rtrvOccupiedRooms().stream().map(
                        RoomModel::getRsv
                ).peek( // DEV Debug Method
                        rsv -> System.out.println("\tChecking: " + rsv + "\tAgainst: " + queryRsv)
                ).filter(
                        rsv -> rsv.equals(queryRsv)
                ).map(
                        RsvModel::getRoom
                ).findFirst().orElse(null);
        return room;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Rate Entity Create & Retrieval Methods">
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Bllchrg Entity Create & Retrieval Methods">
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Guest Entity Create & Retrieval Methods">
    
//</editor-fold>
}