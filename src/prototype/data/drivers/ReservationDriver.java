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
import java.time.temporal.ChronoUnit;
import static java.lang.Math.toIntExact;
/**
 *
 * @author los
 */
public class ReservationDriver implements DataDriver {
    
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
  
    public static void modifyDateArrive(ReservationModel rsv, LocalDate arrv){
        rsv.setDateArrive(arrv);
    }
    public static void modifyDateArrive(int rsvID, LocalDate arrv){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        modifyDateArrive(rsv, arrv);
    }
    public static void modifyDateDepart(ReservationModel rsv, LocalDate dprt) {
        rsv.setDateDepart(dprt);
    }
    public static void modifyDateDepart(int rsvID, LocalDate dprt){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        modifyDateDepart(rsv, dprt);
    }
    public static void modifyDatePaid(ReservationModel rsv, LocalDate paid) {
        rsv.setDatePaid(paid);
    }  
    public static void modifyDatePaid(int rsvID, LocalDate paid) {
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        modifyDatePaid(rsv, paid);
    }
    public static void modifyReservationType(ReservationModel rsv, ReservationType type) {
        rsv.setReservationType(type);
    }
    public static void modifyReservationType(int rsvID, ReservationType type){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        modifyReservationType(rsv, type);
    }
    
    // returns all reservations based on date attr
    public static List<ReservationModel> srchByDateArrv(LocalDate arrv){
        return EntityDatabase.ReservationTable.retrieveByDateArrive(arrv);
    }
    public static List<ReservationModel> srchByDateDprt(LocalDate dprt){
        return EntityDatabase.ReservationTable.retrieveByDateDepart(dprt);
    }
    public static List<ReservationModel> srchByDateInMddl(LocalDate inclsv){
        return EntityDatabase.ReservationTable.retrieveByDateInMiddle(inclsv);
    }
    public static List<ReservationModel> srchByDate(LocalDate date){
        return EntityDatabase.ReservationTable.retrieveByDate(date);
    }
    public static List<ReservationModel> searchByGuest(int guestID){
        return EntityDatabase.ReservationTable.retrieveByGuest(guestID);
    }
    public static List<ReservationModel> searchByGuest(GuestModel guest){
        return searchByGuest(guest.getGuestID());
    }
    public static ReservationModel searchByRoom(int RoomID) {
        return EntityDatabase.ReservationTable.retrieveByRoom(RoomID);
    }
    public static ReservationModel searchByRoom(RoomModel room){
        return searchByRoom(room.getRoomID());
    }
    public static List<ReservationModel> searchByType(ReservationType rsvType){
        return EntityDatabase.ReservationTable.retrieveByType(rsvType);
    }
 
    // returns reservations
    public static List<ReservationModel> returnAllReservations(){
        return EntityDatabase.ReservationTable.retrieveAllReservations();
    }
    public static List<ReservationModel> returnAllNoShow(){
        return EntityDatabase.ReservationTable.retrieveAllNoShow();
    }
    public static List<ReservationModel> returnAllPaid(){
        return EntityDatabase.ReservationTable.retrieveAllPaid();
    }
    public static List<ReservationModel> returnAllConcluded(){
        return EntityDatabase.ReservationTable.retrieveAllConcluded();
    }
    
    public static ReservationModel getReservationByID(int RsvID){
        return EntityDatabase.ReservationTable.retrieveByID(RsvID);
    }
    
    // returns length of stay
    public static int getLengthOfStay(int rsvID){
        ReservationModel reservation = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        LocalDate start = reservation.getDateArrive();
        LocalDate end = reservation.getDateDepart();      
        return toIntExact(ChronoUnit.DAYS.between(start, end));
    }

    // (de-)attach Entities
    public static void attachRoom(ReservationModel rsv, RoomModel room){
        rsv.setRoom(room);
    }
    public static void attachRoom(int rsvID, RoomModel room){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        attachRoom(rsv, room);
    }
    public static void deattachRoom(ReservationModel rsv){
        rsv.setRoom(null);
    }
    public static void deattachRoom(int rsvID){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        deattachRoom(rsv);
    }
    public static void attachGuest(ReservationModel rsv, GuestModel guest){
        rsv.setGuest(guest);
    }
    public static void attachGuest(int rsvID, GuestModel guest){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        attachGuest(rsv, guest);
    }
    public static void attachBillCharge(ReservationModel rsv, BillChargeModel bllchrg){
        rsv.getListBillCharges().add(bllchrg);
    }
    public static void attachBillCharge(int rsvID, BillChargeModel bllchrg){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        rsv.getListBillCharges().add(bllchrg);
    }

    // flags
    public static void flagAsNoShow(ReservationModel rsv) {
        rsv.setIsNoShow(true);
    }
    public static void flagAsNoShow(int rsvID) {
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        flagAsNoShow(rsv);
    }
    public static void flagAsPaid(ReservationModel rsv) {
        rsv.setIsPaid(true);
    }
    public static void flagAsPaid(int rsvID){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        flagAsPaid(rsv);
    }
    public static void flagAsConcluded(ReservationModel rsv) {
        rsv.setIsConcluded(true);
    }
    public static void flagAsConcluded(int rsvID){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        flagAsConcluded(rsv);
    }
}
