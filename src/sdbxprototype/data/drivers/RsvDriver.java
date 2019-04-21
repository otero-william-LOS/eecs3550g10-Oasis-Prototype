/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdbxprototype.data.drivers;
import sdbxprototype.data.models.RsvModel;
import sdbxprototype.data.models.RoomModel;
import sdbxprototype.data.models.GuestModel;
import sdbxprototype.data.models.BllchrgModel;
import java.util.ArrayList;
import java.util.Date;
import sdbxprototype.data.DevDatabase;
import sdbxprototype.data.RsvType;
/**
 *
 * @author los
 */
public class RsvDriver implements DataDriver {
    
    // creates a new reservation for the first RsvModel constructor
    public static void createRSVC(Date dateArrive, Date dateDepart, Date datePaid, RsvType rsvType,
            RoomModel room, GuestModel guest, ArrayList<BllchrgModel> listBllchrg,
            boolean isNoShow, boolean isPaid, boolean isConcluded){
        DevDatabase.addReservation(dateArrive, dateDepart, datePaid, rsvType, room, guest, listBllchrg,
                isNoShow, isPaid, isConcluded);
    }
    
    // creates a new reservation for the second RsvModel constructor
    public static void createRSVC(Date dateArrive, Date dateDepart, Date datePaid, RsvType rsvType,
            boolean isNoShow, boolean isPaid, boolean isConcluded){
        DevDatabase.addReservation(dateArrive, dateDepart, datePaid, rsvType,
                isNoShow, isPaid, isConcluded);
    }
    
    // creates a new reservation for the third RsvModel constructor
    public static void createRSVC(Date dateArrive, Date dateDepart, RsvType rsvType){
        DevDatabase.addReservation(dateArrive, dateDepart, rsvType);
    }
    
    // creates a new reservation for the fourth RsvModel constructor
    public static void createRSVC(Date dateArrive, Date dateDepart){
        DevDatabase.addReservation(dateArrive, dateDepart);
    }
    
    // creates a new reservation for the fourth RsvModel constructor
    public static int createRSVCSched(Date dateArrive, Date dateDepart){
        int rsvID = DevDatabase.addReservationSched(dateArrive, dateDepart);
        return rsvID;
    }
    
    public static void changeDateArrive(int primaryKey, Date arrival){
        DevDatabase.changeRsvArrival(primaryKey, arrival);
    }
    
    public static void changeDateDepart(int primaryKey, Date departure){
        DevDatabase.changeRsvDeparture(primaryKey, departure);
    }
    
    public static void changeReservationType(int primaryKey, RsvType rsvType){
        DevDatabase.changeRsvType(primaryKey, rsvType);
    }
    
    public static ArrayList<RsvModel> searchByGuest(GuestModel guest){
        //ArrayList<RsvModel> matchingRsvs = new  ArrayList<RsvModel>();
        return DevDatabase.searchRsvByGuest(guest);      
        //return matchingRsvs;
    }
    
    public static ArrayList<RsvModel> searchByRoom(RoomModel room){
        return DevDatabase.searchRsvByRoom(room);
    }
    
    // returns all reservations that are arriving on given day
    public static ArrayList<RsvModel> searchByDateArrive(Date dateArrive){
        return DevDatabase.searchRsvByDtArrive(dateArrive);
    }
    
    // returns all reservations that are departing on given date
    public static ArrayList<RsvModel> searchByDateDepart(Date dateDepart){
        return DevDatabase.searchRsvByDtArrive(dateDepart);
    }
    
    // returns all reservations that are arriving, departing or in middle of stay on given date
    public static ArrayList<RsvModel> searchByDateArriveBtwn(Date dateArrive){
        return DevDatabase.searchRsvByDtArriveBtwn(dateArrive);
    }
    
    //returns all reservations of a given type
    public static ArrayList<RsvModel> searchByType(RsvType rsvType){
        return DevDatabase.searchRsvByType(rsvType);
    }
    
    // returns all paid reservations
    public static ArrayList<RsvModel> getAllPaid(){
        return DevDatabase.getAllPaid();
    }
    
    // returns all conclded reservations
    public static ArrayList<RsvModel> getAllConcluded(){
        return DevDatabase.getAllConcluded();
    }
    
    // returns all noo show reservations
    public static ArrayList<RsvModel> getAllnoShow(){
        return DevDatabase.getAllnoShow();
    }
    
    public static void assignRoom(int primaryKey, RoomModel room){
        DevDatabase.assignRsvRoom(primaryKey, room);
    }
    
    public static void deassignRoom(int primaryKey){
        DevDatabase.deassignRsvRoom(primaryKey);
    }
    
    public static void flagIsNoShow(int primaryKey){
        DevDatabase.flagRsvNoShow(primaryKey);
    }
    
    public static void flagIsPaid(int primaryKey){
        DevDatabase.flagRsvIsPaid(primaryKey);
    }
    
    public static void flagIsConcluded(int primaryKey){
        DevDatabase.flagRsvIsConcluded(primaryKey);
    }
}
