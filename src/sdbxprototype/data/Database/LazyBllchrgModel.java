package Database;

import Models.*;
import java.sql.Date;

/**
 *
 * @author los
 */
public class LazyBllchrgModel {
    
    //  Attributes
    
    //  BllchrgID Attribute
    /*
        CSV Column; non-volatile
        int; (String in CSV)
        Unique id for this specific bill charge line item
    */
    private int _BllchrgID;
    public int getBllchrgID() {return _BllchrgID;}
    public void setBllchrgID(int bllchrgID) {this._BllchrgID = bllchrgID;}    
    
    //  Reservation Attribute
    /*
        CSV Column; non-volatile
        RsvModel; (RsvID String in CSV)
        (0+)-to-one rsv relation
    */
    private int _Reservation;
    public int  getReservation() {return _Reservation;}
    public void setReservation(int rsv) {this._Reservation = rsv;}
    
    //  LineDescription Attribute
    /*
        CSV Column; non-volatile
        String
        Desc
    */
    
    private String _LineDescription;
    public String getLineDescription() {return _LineDescription;}
    public void setLineDescription(String lineDesc) {
        this._LineDescription = lineDesc;
    }
    
    //  Amount Attribute
    /*
        CSV Column; non-volatile
        double; (string in CSV)
        Desc
    */
    private double _Amount;
    public double getAmount() {return _Amount;}
    public void setAmount(double amount) {this._Amount = amount;}
    
    //  DateCharged Attribute
    /*
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date _DateCharged;
    public Date getDateCharged() {return _DateCharged;}
    public void setDateCharged(Date dateCharged) {
        this._DateCharged = dateCharged;
    }
    
    //  DatePaid Attribute
    /*
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date _DatePaid;
    public Date getDatePaid() {return _DatePaid;}
    public void setDatePaid(Date datePaid) {
        this._DatePaid = datePaid;
    }
    
    //  IsPaid Attribute
    /*
        CSV Column; non-volatile
        boolean
        Desc
    */
       
    private boolean _IsPaid;
    public boolean isIsPaid() {return _IsPaid;}
    public void setIsPaid(boolean isPaid) {
        this._IsPaid = isPaid;}
    
    
    
    //  Model Constructor
    public LazyBllchrgModel(int bllchrgID, int rsv, String lineDesc, 
            double amount, Date dateCharged, Date datePaid, boolean isPaid) {
        this._BllchrgID = bllchrgID;
        this._Reservation = rsv;
        this._LineDescription = lineDesc;
        this._Amount = amount;
        this._DateCharged = dateCharged;
        this._DatePaid = datePaid;
        this._IsPaid = isPaid;
    }
    
    public LazyBllchrgModel(int bllchrgID, int rsv, String lineDesc,
            double amount, Date dateCharged) {
        this._BllchrgID = bllchrgID;
        this._Reservation = rsv;
        this._LineDescription = lineDesc;
        this._Amount = amount;
        this._DateCharged = dateCharged;
        this._DatePaid = null;
        this._IsPaid = false;
    }
    
    public LazyBllchrgModel() {
        this._BllchrgID = 0;
        this._Reservation = 0;
        this._LineDescription = null;
        this._Amount = 0;
        this._DateCharged = null;
        this._DatePaid = null;
        this._IsPaid = false;
    }
}
