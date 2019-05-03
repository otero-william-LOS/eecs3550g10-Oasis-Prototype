/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.drivers;

import prototype.data.persistence.EntityDatabase;
import prototype.data.models.GuestModel;
import prototype.data.models.ReservationModel;
import java.util.List;

/**
 *
 * @author wotero
 */
public class GuestDriver implements DataDriver {

    // create guest
    public static void createGuest(String name, String email, String ccInfo) {
        EntityDatabase.GuestTable.addGuest(name, email, ccInfo);
    }
    public static void createGuest(String name, String email) {
        EntityDatabase.GuestTable.addGuest(name, email);
    }
    public static int createGuestReturnID(String name, String email) {
        return EntityDatabase.GuestTable.addGuestReturnID(name, email);
    }
    public static int createGuestReturnID(String name, String email, String ccInfo) {
        return EntityDatabase.GuestTable.addGuestReturnID(name, email, ccInfo);
    }
    
    // search attr
    public static GuestModel searchByID(int guestID) {
        //return matchGuest_ID;
        return EntityDatabase.GuestTable.retrieveByID(guestID);
    }
    public static GuestModel searchByName(String name) {
        //return matchName;
        return EntityDatabase.GuestTable.retrieveByName(name);
    }
    public static GuestModel searchByEmail(String email) {
        return EntityDatabase.GuestTable.retrieveByEmail(email);
    }
    
    public static List<GuestModel> returnAllGuests(){
        return EntityDatabase.GuestTable.retrieveAllGuests();
    }
    
    // search by rsv ref/id
    public static GuestModel searchByReservation(int rsvID) {
        return EntityDatabase.GuestTable.retrieveByReservation(rsvID);
    }
    public static GuestModel searchByReservation(ReservationModel rsv) {
        int rsvID = rsv.getReservationID();
        return EntityDatabase.GuestTable.retrieveByReservation(rsvID);
    }
    
    // modify ccInfo
    public static void modifyGuestCreditCardInfo(GuestModel guest, String ccInfo) {
        guest.setCCInfo(ccInfo);
    }
    public static void modifyGuestCreditCardInfo(int guestID, String ccInfo) {
        GuestModel guest = EntityDatabase.GuestTable.retrieveByID(guestID);
        modifyGuestCreditCardInfo(guest, ccInfo);
    }
    
    // add Rsv to Guest's listRsv
    public static void attachReservation(GuestModel guest, ReservationModel rsv){
        List<ReservationModel> reservations = guest.getListRsv();
        reservations.add(rsv);
        guest.setListRsv(reservations);
    }
    public static void attachReservation(GuestModel guest, int rsvID){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        guest.getListRsv().add(rsv);
    }
    public static void attachReservation(int guestID, int rsvID){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        GuestModel guest = EntityDatabase.GuestTable.retrieveByID(guestID);
        guest.getListRsv().add(rsv);
    }
}
