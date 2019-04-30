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
    
    // return rooms
    public static List<RoomModel> returnAllRooms(){
        return EntityDatabase.RoomTable.retrieveAllRooms();
    }
    public static List<RoomModel> returnVacantRooms(){
        return EntityDatabase.RoomTable.retrieveVacantRooms();
    }
    public static List<RoomModel> returnOccupiedRooms(){
        return EntityDatabase.RoomTable.retrieveOccupiedRooms();
    }
    
    // flag room occupied/vacant
    public static void flagRoomAsOccupied(RoomModel room){
        room.setIsOccupied(true);
    }
    public static void flagRoomAsOccupied(int roomID){
        RoomModel rm = EntityDatabase.RoomTable.retrieveByID(roomID);
        flagRoomAsOccupied(rm);
    }
    public static void flagRoomAsVacant(RoomModel room){
        room.setIsOccupied(false);
    }
    public static void flagRoomAsVacant(int roomID){
        RoomModel rm = EntityDatabase.RoomTable.retrieveByID(roomID);
        flagRoomAsVacant(rm);
    }
    
    // search by rsv
    public static RoomModel searchByReservation(int rsvID){
        return EntityDatabase.RoomTable.retrieveByReservation(rsvID);
    }
    public static RoomModel searchByReservation(ReservationModel rsv){
        return searchByReservation(rsv.getReservationID());
    }
    
    // search by id
    public static RoomModel searchByID(int roomID){
        return EntityDatabase.RoomTable.retrieveByID(roomID);
    }
    
    // (de-)attach Rsv attr
    public static void attachReservation(RoomModel room, ReservationModel rsv){
        room.setReservation(rsv);
    }
    public static void attachReservation(int roomID, ReservationModel rsv){
        RoomModel rm = EntityDatabase.RoomTable.retrieveByID(roomID);
        RoomDriver.attachReservation(rm, rsv);
    }
    public static void attachReservation(int roomID, int rsvID){
        RoomModel rm = EntityDatabase.RoomTable.retrieveByID(roomID);
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        RoomDriver.attachReservation(rm, rsv);
    }
    public static void deattachReservation(RoomModel rm){
        rm.setReservation(ReservationModel.EMPTY_ENTITY);
    }
    public static void deattachReservation(int roomID){
        RoomModel room = EntityDatabase.RoomTable.retrieveByID(roomID);
        RoomDriver.deattachReservation(room);
    }
}
