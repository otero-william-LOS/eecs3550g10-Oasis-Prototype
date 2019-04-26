package Database;


import Models.*;
import static Models.RsvType.*;
import java.sql.Date;

/**
 *
 * @author los
 */
public class LazyRsvModel {
    
    //  Attributes
    
    //  RsvID Attribute
    /*
        CSV Column; non-volatile
        int; (String in CSV)
        Unique primary key for specific rsv
    */
    private int _RsvID;
    public int getRsvID() {return _RsvID;}
    public void setRsvID(int rsvID) {this._RsvID = rsvID;}
    
    //  DateArrive Attribute
    /*
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date _DateArrive;
    public Date getDateArrive() {return _DateArrive;}
    public void setDateArrive(Date dateArrive) {
        this._DateArrive = dateArrive;
    }
    
    //  DateDepart Attribute
    /*
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date _DateDepart;
    public Date getDateDepart() {return _DateDepart;}
    public void setDateDepart(Date dateDepart) {this._DateDepart = dateDepart;}
    
    //  DatePaid Attribute
    /*
        CSV Column; non-volatile
        Date
        Records Date that specific Rsv was fully (pre-)payed
    */
    private Date _DatePaid;
    public Date getDatePaid() {return _DatePaid;}
    public void setDatePaid(Date datePaid) {this._DatePaid = datePaid;}
    
    //  RsvType Attribute
    /*
        CSV Column; non-volatile
        Enum
        Assigns type of specific Rsv, and will determine rate charged
    */
    private RsvType _RsvType;
    public RsvType getRsvType() {return _RsvType;}
    public void setRsvType(RsvType rsvType) {this._RsvType = rsvType;}
    
    //  Room Attribute
    /*
        CSV Column; non-volatile
        RoomModel; (RoomID String in CSV)
        Specific Room assigned to this rsv on morning of arrival
        (0 or 1)-to-one
    */
    private short  _Room;
    public short getRoom() {return _Room;}
    public void setRoom(short room) {this._Room = room;}
    
    //  Guest Attribute
    /*
        CSV Column; non-volatile
        GuestModel; (GuestID String in CSV)
        Unique guest who made this rsv
        (1+)-to-one
    */
    private int _Guest;
    public int getGuest() {return _Guest;}
    public void setGuest(int guest) {this._Guest = guest;}
    
   
    
    //  IsNoShow Attribute
    /*
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean _IsNoShow;
    public boolean isIsNoShow() {return _IsNoShow;}
    public void setIsNoShow(boolean isNoShow) {this._IsNoShow = isNoShow;}
    
    //  IsPaid Attribute
    /*
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean _IsPaid;
    public boolean isIsPaid() {return _IsPaid;}
    public void setIsPaid(boolean isPaid) {this._IsPaid = isPaid;}
    
    //  IsConcluded Attribute
    /*
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean _IsConcluded;
    public boolean isIsConcluded() {return _IsConcluded;}
    public void setIsConcluded(boolean isConcluded) {
        this._IsConcluded = isConcluded;
    }
    
    
    //  Model Constructor
    public LazyRsvModel(int rsvID, Date dateArrive, Date dateDepart, 
            Date datePaid, RsvType rsvType, 
            short room, int guest, 
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        this._RsvID = rsvID;
        this._DateArrive = dateArrive;
        this._DateDepart = dateDepart;
        this._DatePaid = datePaid;
        this._RsvType = rsvType;
        this._Room = room;
        this._Guest = guest;
        this._IsNoShow = isNoShow;
        this._IsPaid = isPaid;
        this._IsConcluded = isConcluded;
    }

    public LazyRsvModel(int rsvID, Date dateArrive, Date dateDepart, 
            Date datePaid, RsvType rsvType, 
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        this._RsvID = rsvID;
        this._DateArrive = dateArrive;
        this._DateDepart = dateDepart;
        this._DatePaid = datePaid;
        this._RsvType = rsvType;
        this._Room = 0;
        this._Guest = 0;
        this._IsNoShow = isNoShow;
        this._IsPaid = isPaid;
        this._IsConcluded = isConcluded;
    }

    public LazyRsvModel(int rsvID, Date dateArrive,
            Date dateDepart, RsvType rsvType) {
        this._RsvID = rsvID;
        this._DateArrive = dateArrive;
        this._DateDepart = dateDepart;
        this._DatePaid = null;
        this._RsvType = rsvType;
        this._Room = 0;
        this._Guest = 0;
        this._IsNoShow = false;
        this._IsPaid = false;
        this._IsConcluded = false;
    }

    public LazyRsvModel(int rsvID, 
            Date dateArrive, Date dateDepart) {
        this._RsvID = rsvID;
        this._DateArrive = dateArrive;
        this._DateDepart = dateDepart;
        this._DatePaid = null;
        this._RsvType = null;
        this._Room = 0;
        this._Guest = 0;
        this._IsNoShow = false;
        this._IsPaid = false;
        this._IsConcluded = false;
    }

    public LazyRsvModel() {
        this._RsvID = 0;
        this._DateArrive = null;
        this._DateDepart = null;
        this._DatePaid = null;
        this._RsvType = null;
        this._Room = 0;
        this._Guest = 0;
        this._IsNoShow = false;
        this._IsPaid = false;
        this._IsConcluded = false;
    }
}
