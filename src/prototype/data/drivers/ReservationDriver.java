/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.drivers;
import prototype.data.models.ReservationModel;
import prototype.data.models.RoomModel;
import prototype.data.models.GuestModel;
import prototype.data.models.BillChargeModel;
import java.util.ArrayList;
import java.sql.Date;
import prototype.data.persistence.EntityDatabase;
import prototype.data.models.ReservationType;
/**
 *
 * @author los
 */
public class ReservationDriver implements DataDriver {
    
    // creates a new reservation for the first ReservationModel constructor
    public static void createRSVC(Date dateArrive, Date dateDepart, Date datePaid, ReservationType rsvType,
            RoomModel room, GuestModel guest, ArrayList<BillChargeModel> listBllchrg,
            boolean isNoShow, boolean isPaid, boolean isConcluded){
//        EntityDatabase.addReservation(dateArrive, dateDepart, datePaid, rsvType, room, guest, listBllchrg,
//                isNoShow, isPaid, isConcluded);
    }
    
    // creates a new reservation for the second ReservationModel constructor
    public static void createRSVC(Date dateArrive, Date dateDepart, Date datePaid, ReservationType rsvType,
            boolean isNoShow, boolean isPaid, boolean isConcluded){
//        EntityDatabase.addReservation(dateArrive, dateDepart, datePaid, rsvType,
//                isNoShow, isPaid, isConcluded);
    }
    
    // creates a new reservation for the third ReservationModel constructor
    public static void createRSVC(Date dateArrive, Date dateDepart, ReservationType rsvType){
//        EntityDatabase.addReservation(dateArrive, dateDepart, rsvType);
    }
    
    // creates a new reservation for the fourth ReservationModel constructor
    public static void createRSVC(Date dateArrive, Date dateDepart){
//        EntityDatabase.addReservation(dateArrive, dateDepart);
    }
    
    // creates a new reservation for the fourth ReservationModel constructor
    public static int createRSVCSched(Date dateArrive, Date dateDepart){
//        int rsvID = EntityDatabase.addReservationSched(dateArrive, dateDepart);
        return 0;// rsvID;
    }
    
    public static void changeDateArrive(int primaryKey, Date arrival){
        EntityDatabase.changeRsvArrival(primaryKey, arrival);
    }
    
    public static void changeDateDepart(int primaryKey, Date departure){
        EntityDatabase.changeRsvDeparture(primaryKey, departure);
    }
    
    public static void changeReservationType(int primaryKey, ReservationType rsvType){
        EntityDatabase.changeRsvType(primaryKey, rsvType);
    }
    
    public static ArrayList<ReservationModel> searchByGuest(GuestModel guest){
        //ArrayList<RsvModel> matchingRsvs = new  ArrayList<RsvModel>();
        return EntityDatabase.searchRsvByGuest(guest);      
        //return matchingRsvs;
    }
    
    public static ArrayList<ReservationModel> searchByRoom(RoomModel room){
        return EntityDatabase.searchRsvByRoom(room);
    }
    
    // returns all reservations that are arriving on given day
    public static ArrayList<ReservationModel> searchByDateArrive(Date dateArrive){
        return EntityDatabase.searchRsvByDtArrive(dateArrive);
    }
    
    // returns all reservations that are departing on given date
    public static ArrayList<ReservationModel> searchByDateDepart(Date dateDepart){
        return EntityDatabase.searchRsvByDtArrive(dateDepart);
    }
    
    // returns all reservations that are arriving, departing or in middle of stay on given date
    public static ArrayList<ReservationModel> searchByDateArriveBtwn(Date dateArrive){
        return EntityDatabase.searchRsvByDtArriveBtwn(dateArrive);
    }
    
    //returns all reservations of a given type
    public static ArrayList<ReservationModel> searchByType(ReservationType rsvType){
        return EntityDatabase.searchRsvByType(rsvType);
    }
    
    // returns all paid reservations
    public static ArrayList<ReservationModel> getAllPaid(){
        return EntityDatabase.getAllPaid();
    }
    
    // returns all conclded reservations
    public static ArrayList<ReservationModel> getAllConcluded(){
        return EntityDatabase.getAllConcluded();
    }
    
    // returns all noo show reservations
    public static ArrayList<ReservationModel> getAllnoShow(){
        return EntityDatabase.getAllnoShow();
    }
    
    public static void assignRoom(int primaryKey, RoomModel room){
        EntityDatabase.assignRsvRoom(primaryKey, room);
    }
    
    public static void deassignRoom(int primaryKey){
        EntityDatabase.deassignRsvRoom(primaryKey);
    }
    
    public static void flagIsNoShow(int primaryKey){
        EntityDatabase.flagRsvNoShow(primaryKey);
    }
    
    public static void flagIsPaid(int primaryKey){
        EntityDatabase.flagRsvIsPaid(primaryKey);
    }
    
    public static void flagIsConcluded(int primaryKey){
        EntityDatabase.flagRsvIsConcluded(primaryKey);
    }
}
