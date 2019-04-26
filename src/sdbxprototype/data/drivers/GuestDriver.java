/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdbxprototype.data.drivers;

import java.util.ArrayList;
import sdbxprototype.data.DevDatabase;
import sdbxprototype.data.models.GuestModel;

/**
 *
 * @author wotero
 */
public class GuestDriver implements DataDriver {

    public static ArrayList<GuestModel> searchByGuest_ID(GuestModel Guest_ID) {
        return DevDatabase.searchByGuest_ID(Guest_ID);
    }

    public static ArrayList<GuestModel> returnByGuest_ID(GuestModel Guest_ID) {
        return DevDatabase.searchByGuest_ID(Guest_ID);
    }

    public static ArrayList<GuestModel> returnByEmail(String Email) {
        return DevDatabase.searchByName(Email);
    }

    public static ArrayList<GuestModel> retrieveByGuest_ID(GuestModel Guest_ID) {
        return DevDatabase.searchByGuest_ID(Guest_ID);
    }

    public static ArrayList<GuestModel> searchByEmail(String Email) {
        return DevDatabase.searchByName(Email);
    }

    public static ArrayList<GuestModel> searchByName(String Name) {
        return DevDatabase.searchByName(Name);
    }

    public static ArrayList<GuestModel> returnByName(GuestModel Name) {
        return DevDatabase.searchByGuest_ID(Name);
    }

    public static ArrayList<GuestModel> retrieveByName(GuestModel Name) {
        return DevDatabase.searchByGuest_ID(Name);
    }

    public static ArrayList<GuestModel> retrieveByEmail(GuestModel Email) {
        return DevDatabase.searchByGuest_ID(Email);
    }

    public void searchGuest(String Name, String Email, String Guest_ID) {
//        DevDatabase.searchGuestInfo(Name, Email, Guest_ID);
    }

    public void genGuest_ID(String Name, String Email, String Guest_ID, String CC_info, boolean isConcluded) {
//        DevDatabase.genGuest_ID(Name, Email, Guest_ID, CC_info, isConcluded);
    }

    public void modifyGuest(String Name, String Email, boolean isConcluded) {
//        DevDatabase.modifyGuestInfo(Name, Email, isConcluded);
    }

    public void createGuest(String Name, String Email, String Guest_ID, String CC_info, boolean isConcluded) {
//        DevDatabase.createGuest(Name, Email, Guest_ID, CC_info, isConcluded);
    }

    public void retrieveGuest(String Name, String Email, String Guest_ID, boolean isConcluded) {
//        DevDatabase.retrieveGuestInfo(Name, Email, Guest_ID, isConcluded);
    }

    public void returnGuest(String Name, String Email, String Guest_ID, boolean isconcluded) {
//        DevDatabase.returnGuestInfo(Name, Email, Guest_ID, isConcluded);
    }
    
}
