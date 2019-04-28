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
import java.time.LocalDate;
import java.util.List;
import prototype.data.persistence.EntityDatabase;
import prototype.data.models.ReservationType;
/**
 *
 * @author los
 */
public class ReservationDriver implements DataDriver {
    
    // creates a new reservation for the first ReservationModel constructor
    public static void createReservation(LocalDate dateArrive, LocalDate dateDepart, LocalDate datePaid, ReservationType rsvType,
            RoomModel room, GuestModel guest, List<BillChargeModel> listBllchrg,
            boolean isNoShow, boolean isPaid, boolean isConcluded){
        EntityDatabase.ReservationTable.addReservation(dateArrive, dateDepart, datePaid, rsvType, room, guest, listBllchrg,
                isNoShow, isPaid, isConcluded);
    }
    // creates a new reservation for the second ReservationModel constructor
    public static void createReservation(LocalDate dateArrive, LocalDate dateDepart, LocalDate datePaid, ReservationType rsvType,
            boolean isNoShow, boolean isPaid, boolean isConcluded){
        EntityDatabase.ReservationTable.addReservation(dateArrive, dateDepart, datePaid, rsvType,
                isNoShow, isPaid, isConcluded);
    }
    // creates a new reservation for the third ReservationModel constructor
    public static void createReservation(LocalDate dateArrive, LocalDate dateDepart, ReservationType rsvType){
        EntityDatabase.ReservationTable.addReservation(dateArrive, dateDepart, rsvType);
    }
    // creates a new reservation for the fourth ReservationModel constructor
    public static void createReservation(LocalDate dateArrive, LocalDate dateDepart){
        EntityDatabase.ReservationTable.addReservation(dateArrive, dateDepart);
    }
    // creates a new reservation for the fourth ReservationModel constructor
    public static int createReservationReturnID(LocalDate dateArrive, LocalDate dateDepart){
        return EntityDatabase.ReservationTable.addReservationReturnID(dateArrive, dateDepart);
    }
//    
    public static void modifyDateArrive(ReservationModel rsv, LocalDate arrv){
        rsv.setDateArrive(arrv);
    }
    public static void modifyDateArrive(int rsvID, LocalDate arrv){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveReservation(rsvID);
        modifyDateArrive(rsv, arrv);
    }
//  
    public static void modifyDateDepart(ReservationModel rsv, LocalDate dprt) {
        rsv.setDateDepart(dprt);
    }
    public static void modifyDateDepart(int rsvID, LocalDate dprt){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveReservation(rsvID);
        modifyDateDepart(rsv, dprt);
    }
//    
    public static void modifyDatePaid(ReservationModel rsv, LocalDate paid) {
        rsv.setDatePaid(paid);
    }  
    public static void modifyDatePaid(int rsvID, LocalDate paid) {
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveReservation(rsvID);
        modifyDatePaid(rsv, paid);
    }
//  
    
    public static void modifyReservationType(ReservationModel rsv, ReservationType type) {
        rsv.setReservationType(type);
    }
    public static void modifyReservationType(int rsvID, ReservationType type){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveReservation(rsvID);
        modifyReservationType(rsv, type);
    }
    
    // returns all reservations based on date attr
    public static List<ReservationModel> srchByDateArrv(LocalDate arrv){
        return EntityDatabase.ReservationTable.retrieveReservationByDateArrive(arrv);
    }
    public static List<ReservationModel> srchByDateDprt(LocalDate dprt){
        return EntityDatabase.ReservationTable.retrieveReservationByDateDepart(dprt);
    }
    public static List<ReservationModel> srchByDateInMddl(LocalDate inclsv){
        return EntityDatabase.ReservationTable.retrieveReservationByDateInMiddle(inclsv);
    }
    public static List<ReservationModel> srchByDate(LocalDate date){
        return EntityDatabase.ReservationTable.retrieveReservationByDate(date);
    }
    
    public static List<ReservationModel> searchByGuest(int guestID){
        return EntityDatabase.ReservationTable.searchRsvByGuest(guestID);
    }
    public static List<ReservationModel> searchByGuest(GuestModel guest){
        return searchByGuest(guest.getGuestID());
    }
    
    public static ReservationModel searchByRoom(int RoomID) {
        return EntityDatabase.ReservationTable.searchRsvByRoom(RoomID);
    }
    public static ReservationModel searchByRoom(RoomModel room){
        return searchByRoom(room.getRoomID());
    }
    
//  TODO START HERE  
    //returns all reservations of a given type
    public static List<ReservationModel> searchByType(ReservationType rsvType){
        return EntityDatabase.ReservationTable.searchReservationByType(rsvType);
    }
//    
//    // returns all paid reservations
//    public static ArrayList<ReservationModel> getAllPaid(){
//        return EntityDatabase.getAllPaid();
//    }
//    
//    // returns all conclded reservations
//    public static ArrayList<ReservationModel> getAllConcluded(){
//        return EntityDatabase.getAllConcluded();
//    }
//    
//    // returns all noo show reservations
//    public static ArrayList<ReservationModel> getAllnoShow(){
//        return EntityDatabase.getAllnoShow();
//    }
//    
//    public static void assignRoom(int primaryKey, RoomModel room){
//        EntityDatabase.assignRsvRoom(primaryKey, room);
//    }
//    
//    public static void deassignRoom(int primaryKey){
//        EntityDatabase.deassignRsvRoom(primaryKey);
//    }
//    
//    public static void flagIsNoShow(int primaryKey){
//        EntityDatabase.flagRsvNoShow(primaryKey);
//    }
//    
//    public static void flagIsPaid(int primaryKey){
//        EntityDatabase.flagRsvIsPaid(primaryKey);
//    }
//    
//    public static void flagIsConcluded(int primaryKey){
//        EntityDatabase.flagRsvIsConcluded(primaryKey);
//    }
//
//    public static void assignRsvRoom(int primaryKey, RoomModel room) {
//        ReservationModel reservation = TBL_RSV_ENTITY.get(primaryKey);
//        reservation.setRoom(room);
//        TBL_RSV_ENTITY.set(primaryKey, reservation);
//    }
//
//    public static void deassignRsvRoom(int primaryKey) {
//        ReservationModel reservation = TBL_RSV_ENTITY.get(primaryKey);
//        RoomModel room = new RoomModel();
//        reservation.setRoom(room);
//        TBL_RSV_ENTITY.set(primaryKey, reservation);
//    }
//
//    public static void flagRsvNoShow(int primaryKey) {
//        ReservationModel reservation = TBL_RSV_ENTITY.get(primaryKey);
//        reservation.setIsNoShow(true);
//        TBL_RSV_ENTITY.set(primaryKey, reservation);
//    }
//
//    public static void flagRsvIsPaid(int primaryKey) {
//        ReservationModel reservation = TBL_RSV_ENTITY.get(primaryKey);
//        reservation.setIsPaid(true);
//        TBL_RSV_ENTITY.set(primaryKey, reservation);
//    }
//
//    public static void flagRsvIsConcluded(int primaryKey) {
//        ReservationModel reservation = TBL_RSV_ENTITY.get(primaryKey);
//        reservation.setIsConcluded(true);
//        TBL_RSV_ENTITY.set(primaryKey, reservation);
//    }
}
