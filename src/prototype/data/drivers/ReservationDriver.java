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
    /* 
    LOS: Removed create method
            Rsv's 3rd and 4th cnstr are meant for the db csv component;
            plus seemed silly to create a brand new rsv, and need isNoShow isPaid isConcluded
     */
    
    // creates a new reservation 
    public static void createReservation(LocalDate dArrive, LocalDate dDepart, ReservationType rsvType){
        EntityDatabase.ReservationTable.addReservation(dArrive, dDepart, rsvType);
    }
    public static void createReservation(LocalDate dateArrive, LocalDate dateDepart){
        EntityDatabase.ReservationTable.addReservation(dateArrive, dateDepart);
    }
    public static int createReservationReturnID(LocalDate dateArrive, LocalDate dateDepart){
        return EntityDatabase.ReservationTable.addReservationReturnID(dateArrive, dateDepart);
    }
    public static int createReservationReturnID(LocalDate dArrive, LocalDate dDepart, ReservationType rsvType){
        return EntityDatabase.ReservationTable.addReservationReturnID(dArrive, dDepart, rsvType);
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
    
    // returns all reservations based on date, attr, entity
    public static ReservationModel searchByID(int RsvID){
        return EntityDatabase.ReservationTable.retrieveByID(RsvID);
    }
    
    public static List<ReservationModel> searchByDateArrive(LocalDate arrv){
        return EntityDatabase.ReservationTable.retrieveByDateArrive(arrv);
    }
    public static List<ReservationModel> searchByDateDepart(LocalDate dprt){
        return EntityDatabase.ReservationTable.retrieveByDateDepart(dprt);
    }
    public static List<ReservationModel> searchByDateInMiddle(LocalDate inclsv){
        return EntityDatabase.ReservationTable.retrieveByDateInMiddle(inclsv);
    }
    public static List<ReservationModel> searchByDate(LocalDate date){
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
    
    // returns length of stay
    public static int getDayLength(ReservationModel rsv){
        LocalDate start = rsv.getDateArrive();
        LocalDate end = rsv.getDateDepart();      
        return toIntExact(ChronoUnit.DAYS.between(start, end));
    }
    public static int getDayLength(int rsvID){
        ReservationModel reservation = EntityDatabase.ReservationTable.retrieveByID(rsvID);   
        return getDayLength(reservation);
    }

    // (de-)attach Entities
    public static void attachRoom(ReservationModel rsv, RoomModel room){
        rsv.setRoom(room);
    }
    public static void attachRoom(int rsvID, RoomModel room){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        attachRoom(rsv, room);
    }
    public static void attachRoom(int rsvID, int roomID){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        RoomModel room = EntityDatabase.RoomTable.retrieveByID(roomID);
        attachRoom(rsv, room);
    }
    public static void deattachRoom(ReservationModel rsv){
        rsv.setRoom(RoomModel.EMPTY_ENTITY);
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
    public static void attachGuest(int rsvID, int guestID){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        GuestModel guest = EntityDatabase.GuestTable.retrieveByID(guestID);
        attachGuest(rsv, guest);
    }
    public static void attachBillCharge(ReservationModel rsv, BillChargeModel bllchrg){
        rsv.getListBillCharges().add(bllchrg);
    }
    public static void attachBillCharge(int rsvID, BillChargeModel bllchrg){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        attachBillCharge(rsv, bllchrg);
    }
    public static void attachBillCharge(int rsvID, int bllchrgID){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        BillChargeModel bllchrg = EntityDatabase.BillChargeTable.retrieveByID(bllchrgID);
        attachBillCharge(rsv, bllchrg);
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
