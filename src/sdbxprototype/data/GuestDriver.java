/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdbxprototype.data;
import java.util.*;
import java.util.ArrayList;

/**
 *
 * @author wotero
 */
public class GuestDriver implements DataDriver {

 public String genGuest_ID;
 public String createGuest;
 public String modifyGuest;
 public String retrieveGuest;
 public String returnGuest;
 public String flagGuest;
 public String searchGuest;
 public ArrayList<String> listReservations = new ArrayList<String>();

 /*
 Generation of Guest_ID, CreateGuest, ModifyGuest, RetrieveGuest,
 ReturnGuest, FlagGuest, SearchGuest methods.
 */

 public void genGuest_ID(String Name, String Email, String Guest_ID, String CC_info, boolean isConcluded) {
 DevDatabase.genGuest_ID(Name, Email, Guest_ID, CC_info, isConcluded);
 }
    
 public void createGuest(String Name, String Email, String Guest_ID, String CC_info, boolean isConcluded) {
 DevDatabase.createGuest(Name, Email, Guest_ID, CC_info, isConcluded);
 }
    
 public void modifyGuest(String Name, String Email, boolean isConcluded) {
 DevDatabase.modifyGuestInfo(Name, Email, isConcluded);    
 }
    
 //Methods of retrieving Guest data
    
 //boolean retrieveAllGuest = genRetrieveGuest.isEmpty();
 //boolean retrieveGuestByID = genRetrieveGuest.isEmpty();
 //boolean retrieveGuestByRsv = genRetrieveGuest.isEmpty();
 //boolean retrieveCC_InfoIsNull = genRetrieveGuest.isEmpty();
    
    
public void retrieveGuest(String Name, String Email, String Guest_ID, boolean isConcluded) {
 DevDatabase.retrieveGuestInfo(Name, Email, Guest_ID, isConcluded);
}    

public static ArrayList<listReservations> retrieveByName (GuestModel Name) {
     return DevDatabase.retrieveByName(Name);
}    
 
public static ArrayList<listReservations> retrieveByEmail (GuestModel Email) {
     return DevDatabase.retrieveByEmail(Email);
}   
 
public static ArrayList<ListReservations> retrieveByGuest_ID (GuestModel Guest_ID) {
    return DevDatabase.retrieveByGuest_ID(Guest_ID);
}

public void returnGuest(String Name, String Email, String Guest_ID, boolean isconcluded) {
 DevDatabase.returnGuestInfo(Name, Email, Guest_ID, isConcluded);
}    
    
public static ArrayList<ListReservations> returnByName (GuestModel Name) {
    return DevDatabase.returnByName(Name);
}
 
public static ArrayList<ListReservations> returnByEmail (GuestModel Email) {
    return DevDatabase.returnByEmail(Email);
}

public static ArrayList<ListReservations> returnByGuest_ID (GuestModel Guest_ID) {
    return DevDatabase.returnByGuest_ID(Guest_ID);
}
    
public static void flagIsNoShow (int primaryKey) {
  DevDatabase.flagGuestNoShow(primaryKey);
}    

public static void flagIsPaid (int primaryKey) {
  DevDatabase.flagGuestPaid(primaryKey);
}   
    
public static void flagIsConcluded (int primaryKey) {
  DevDatabase.flagGuestConcluded(primaryKey);
}  

public void searchGuest(String Name, String Email, String Guest_ID, boolean isCondluded) {
  DevDatabase.searchGuestInfo(Name, Email, Guest_ID, isConcluded);  
}
    
public static ArrayList<ListReservations> searchByName (GuestModel Name) {
    return DevDatabase.searchByName(Name);
}
 
public static ArrayList<ListReservations> searchByEmail (GuestModel Email) {
    return DevDatabase.searchByEmail(Email);
}

public static ArrayList<ListReservations> searchByGuest_ID (GuestModel Guest_ID) {
    return DevDatabase.searchByGuest_ID(Guest_ID);
}
}


 /* public void genReturnGuest(String Name, String Email, String CC_info,
    String Guest_ID) {
 this.genReturnGuest = genReturnGuest;
 }
 public void genFlagGuest(String Name, String Email, String CC_info, String
Guest_ID) {
 this.genFlagGuest = genReturnGuest;
 boolean retrieveCC_InfoIsNull = genRetrieveGuest.isEmpty();
 }
 public void genSearchGuest(String Name, String Email, String CC_info,
String Guest_ID) {
 this.genSearchGuest = genSearchGuest;
 } 
} */
