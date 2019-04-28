/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.drivers;

import prototype.data.models.ReservationModel;
import prototype.data.models.RoomModel;
import java.util.List;
import prototype.data.persistence.EntityDatabase;

/** RoomDriver
 * Refactored from original implementation
 * Modifies room entities for scheduler
 * Pass search params to db class
 * Pass queried entity ref or
 * Pass queried list of entities
 *
 * @author los; 
 
 Note:
 original implementation was not used
 rewritten to use single src EntityDatabase
 methods reduced to rtrn, flag, srch, asign, dsign
 */
public class RoomDriver implements DataDriver {
    
    /**
     * Core RoomDriver Methods
     * 
     * @return 
     */
    
    // return all rooms
    public static List<RoomModel> rtrnAllRooms(){
        return EntityDatabase.RoomTable.rtrvAllRooms();
    }
    // return available rooms
    public static List<RoomModel> rtrnAvailableRooms(){
        return EntityDatabase.RoomTable.rtrvAvailableRooms();
    }
    // return occupied rooms
    public static List<RoomModel> rtrnOccupiedRooms(){
        return EntityDatabase.RoomTable.rtrvOccupiedRooms();
    }
    
    // flag room occupied
    public static void flagRoomAsOccupied(RoomModel rm){
        rm.setIsOccupied(true);
    }
    // flag room available
    public static void flagRoomAsAvailable(RoomModel rm){
        rm.setIsOccupied(false);
    }
    
    // search by rsv
    public static RoomModel srchRoomByRsv(ReservationModel rsv){
        return EntityDatabase.RoomTable.rtrvByReservation(rsv);
    }
    
    // assignRsvToRoom
    public static void asignRsvToRoom(RoomModel rm, ReservationModel rsv){
        rm.setReservation(rsv);
    }
    // deassignRsvFromRoom
    public static void dsignRsvFromRoom(RoomModel rm){
        rm.setReservation(null);
    }
}
