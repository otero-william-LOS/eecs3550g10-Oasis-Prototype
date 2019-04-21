package Database;

import java.util.ArrayList;
import Models.*;

/**
 *
 * @author los
 */
public class LazyGuestModel {
    
    //  Attributes
    
    //  GuestID Attribute
    /*
        CSV Column; non-volatile
        int; (String in CSV)
        Provides unique id for guest infro
    */
    private int _GuestID;
    public int getGuestID() {return _GuestID;}
    public void setGuestID(int guestID) {this._GuestID = guestID;}
    
    //  Name Attribute
    /*
        CSV Column; non-volatile
        String
        Guest's Last Name
    */
    private String _Name;
    public String getName() {return _Name;}
    public void setName(String name) {this._Name = name;}
    
    //  CCInfo Attribute
    /*
        CSV Column; non-volatile
        String
        Provided credit card info for payment processing
    */
    private String _CCInfo;
    public String getCCInfo() {return _CCInfo;}
    public void setCCInfo(String ccInfo) {this._CCInfo = ccInfo;}
    
    //  Email Attribute
    /*
        CSV Column; non-volatile
        String
        Guest's email for communication
    */
    
    private String _Email;
    public String getEmail() {return _Email;}
    public void setEmail(String email) {this._Email = email;}

    
    //  Model Constructor
    public LazyGuestModel(int guestID, String name, String ccInfo,
            String email, ArrayList<LazyRsvModel> listRsv) {
        this._GuestID = guestID;
        this._Name = name;
        this._CCInfo = ccInfo;
        this._Email = email;

    }
    
    public LazyGuestModel(int guestID, String name, String ccInfo,
            String email) {
        this._GuestID = guestID;
        this._Name = name;
        this._CCInfo = ccInfo;
        this._Email = email;

    }
    
    public LazyGuestModel(int guestID, String name, String email) {
        this._GuestID = guestID;
        this._Name = name;
        this._CCInfo = null;
        this._Email = email;
    }
    
    public LazyGuestModel() {
        this._GuestID = 0;
        this._Name = null;
        this._CCInfo = null;
        this._Email = null;

    }
}
