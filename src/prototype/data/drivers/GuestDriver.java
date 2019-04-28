/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.drivers;

import prototype.data.persistence.EntityDatabase;
import prototype.data.models.GuestModel;
import prototype.data.models.ReservationModel;

/**
 *
 * @author wotero
 */
public class GuestDriver implements DataDriver {

    // create guest
    public void createGuest(String name, String email, String guestID, String ccInfo) {
        EntityDatabase.GuestTable.addGuest(name, email, ccInfo);
    }
    public void createGuest(String name, String email, String guestID) {
        EntityDatabase.GuestTable.addGuest(name, email);
    }
    public int createGuestReturnID(String name, String email) {
        return EntityDatabase.GuestTable.addGuestReturnID(name, email);
    }
    
    // search attr
    public static GuestModel searchByID(int guestID) {
        //return matchGuest_ID;
        return EntityDatabase.GuestTable.retrieveByID(guestID);
    }
    public static GuestModel searchByName(String name) {
        //return matchName;
        return EntityDatabase.GuestTable.retrieveByEmail(name);
    }
    public static GuestModel searchByEmail(String email) {
        return EntityDatabase.GuestTable.retrieveByEmail(email);
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
        guest.getListRsv().add(rsv);
    }
    public static void attachReservation(GuestModel guest, int rsvID){
        ReservationModel rsv = EntityDatabase.ReservationTable.retrieveByID(rsvID);
        guest.getListRsv().add(rsv);
    }
}
