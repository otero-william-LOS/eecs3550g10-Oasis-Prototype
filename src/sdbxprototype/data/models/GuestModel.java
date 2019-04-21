package sdbxprototype.data.models;

import java.util.List;

/**
 *
 * @author los
 */
public class GuestModel extends DataModel {
    
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
    
    //  ListRsv Attribute
    /*
        Entity Relation; volatile
        DataModel
        one-to-(1+) rsv relation
    */
    private List<RsvModel> _ListRsv;
    public List<RsvModel> getListRsv() {return _ListRsv;}
    public void setListRsv(List<RsvModel> list) {this._ListRsv = list;}
    
    
    //  Model Constructor
    public GuestModel(int guestID, String name, String ccInfo, String email, List<RsvModel> listRsv) {
        this._GuestID = guestID;
        this._Name = name;
        this._CCInfo = ccInfo;
        this._Email = email;
        this._ListRsv = listRsv;
    }
    
    public GuestModel(int guestID, String name, String ccInfo, String email) {
        this(guestID, name, ccInfo, email, null);
    }
    
    public GuestModel(int guestID, String name, String email) {
        this(guestID, name, null, email, null);
    }
    
    public GuestModel() {
        this(0, null, null, null, null);
    }
    
    // Overrides
    @Override
    public int hashCode() {
        // TODO: Warning 
        // - this method can create orphans when using a HashSet, or similar
        // - highly recommend to use some immutable Attribute (strings)
        int hash = 1;
        hash += hash * 23 + _GuestID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GuestModel)) {
            return false;
        }        
        GuestModel other = (GuestModel) obj;        
        return (this._GuestID != other._GuestID);
    }
    
    @Override
    public String toString() {
        String info = "Guest [ guestID=" + Integer.toString(_GuestID);
        info += " | name=" + _Name;
        info += " | email=" + _Email;
        info += " | ccInfo=" + _CCInfo;
        if (_ListRsv != null){
            info += " | ListRsv.size=" + Integer.toString(_ListRsv.size());
        }
        info += " ]";
        return  info;
    }
}
