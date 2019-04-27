/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.drivers;

import java.util.List;
import java.util.ArrayList;
import prototype.data.persistence.EntityDatabase;
import prototype.data.models.GuestModel;

/**
 *
 * @author wotero
 */
public class GuestDriver implements DataDriver {

    public static List<GuestModel> searchByGuest_ID(GuestModel Guest_ID) {
        List<GuestModel> matchGuest_ID = new ArrayList<>();
        EntityDatabase.searchByGuest_ID(Guest_ID);
        return matchGuest_ID;
    }

    public static List<GuestModel> returnByGuest_ID(GuestModel Guest_ID) {
        return EntityDatabase.searchByGuest_ID(Guest_ID);
    }

    public static List<GuestModel> returnByEmail(String Email) {
        return EntityDatabase.searchByName(Email);
    }

    public static List<GuestModel> retrieveByGuest_ID(GuestModel Guest_ID) {
        return EntityDatabase.searchByGuest_ID(Guest_ID);
    }

    public static List<GuestModel> searchByName(String Name) {
        List<GuestModel> matchName = new ArrayList<>();
        EntityDatabase.searchByName(Name);
        return matchName;
    }

    public static List<GuestModel> returnByName(GuestModel Name) {
        return EntityDatabase.searchByGuest_ID(Name);
    }

    public static List<GuestModel> retrieveByName(GuestModel Name) {
        return EntityDatabase.searchByGuest_ID(Name);
    }

    public static List<GuestModel> retrieveByEmail(GuestModel Email) {
        return EntityDatabase.searchByGuest_ID(Email);
    }

    public void requestAllGuest(String Name, String Email, String Guest_ID) {
//      return EntityDatabase.guestList(Name, Email, Guest_ID);
    }

    public void genGuest_ID(String Name, String Email, String Guest_ID, String CC_info, boolean isConcluded) {
//        EntityDatabase.genGuest_ID(Name, Email, Guest_ID, CC_info, isConcluded);
    }

    public void modifyGuest(String Name, String Email, boolean isConcluded) {
        EntityDatabase.GuestTable.modifyGuest(Name, Email);
    }

    public void createGuest(String Name, String Email, String Guest_ID, String CC_info, boolean isConcluded) {
//        EntityDatabase.createGuest(Name, Email, Guest_ID, CC_info, isConcluded);
    }

    public void retrieveGuest(String Name, String Email, String Guest_ID, boolean isConcluded) {
//        EntityDatabase.retrieveGuestInfo(Name, Email, Guest_ID, isConcluded);
    }

    public void returnGuest(String Name, String Email, String Guest_ID, boolean isconcluded) {
//        EntityDatabase.returnGuestInfo(Name, Email, Guest_ID, isConcluded);
    }
    public static void flagIsConcluded(int primaryKey)  {
        EntityDatabase.flagRsvIsConcluded(primaryKey);
    }
    
}
