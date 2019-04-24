package sdbxprototype.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author los
 */
public class BllchrgModel {
    
    //  Attributes
    
    //  BllchrgID Attribute
    /*
        CSV Column; non-volatile
        int; (String in CSV)
        Unique id for this specific bill charge line item
    */
    private int _BllchrgID;

    BllchrgModel(int i, Date DateCharged, double amount, String lineDesc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    BllchrgModel(int i, Date DateCharged, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    BllchrgModel(int i, Date DateCharged) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public int getBllchrgID() {return _BllchrgID;}
    public void setBllchrgID(int bllchrgID) {this._BllchrgID = bllchrgID;}    
    
    //  Reservation Attribute
    /*
        CSV Column; non-volatile
        RsvModel; (RsvID String in CSV)
        (0+)-to-one rsv relation
    */
    private RsvModel _Rsv;
    public RsvModel getReservation() {return _Rsv;}
    public void setReservation(RsvModel rsv) {this._Rsv = rsv;}
    
    //  LineDescription Attribute
    /*
        CSV Column; non-volatile
        String
        Desc
    */
    private String _LineDescription;
    public String getLineDescription() {return _LineDescription;}
    public void setLineDescription(String lineDesc) {this._LineDescription = lineDesc;}
    
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
    public void setDateCharged(Date dateCharged) {this._DateCharged = dateCharged;}
    
    //  DatePaid Attribute
    /*
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date _DatePaid;
    public Date getDatePaid() {return _DatePaid;}
    public void setDatePaid(Date datePaid) {this._DatePaid = datePaid;}
    
    //  IsPaid Attribute
    /*
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean _IsPaid;
    public boolean isIsPaid() {return _IsPaid;}
    public void setIsPaid(boolean isPaid) {this._IsPaid = isPaid;}
    
    
    //  Model Constructor
    public BllchrgModel(int bllchrgID, RsvModel rsv, String lineDesc, double amount, Date dateCharged, Date datePaid, boolean isPaid) {
        this._BllchrgID = bllchrgID;
        this._Rsv = rsv;
        this._LineDescription = lineDesc;
        this._Amount = amount;
        this._DateCharged = dateCharged;
        this._DatePaid = datePaid;
        this._IsPaid = isPaid;
    }
    
    public BllchrgModel(int bllchrgID, RsvModel rsv, String lineDesc, double amount, Date dateCharged) {
        this(bllchrgID, rsv, lineDesc, amount, dateCharged, null, false);
    }
    
    public BllchrgModel(int bllchrgID, String lineDesc, double amount, Date dateCharged) {
        this(bllchrgID, null, lineDesc, amount, dateCharged, null, false);
    }
    
    public BllchrgModel() {
        this(0, null, null, 0, null, null, false);
    }
    
    // Overrides
    @Override
    public int hashCode() {
        // TODO: Warning 
        // - this method can create orphans when using a HashSet, or similar
        // - highly recommend to use some immutable Attribute (strings)
        int hash = 2;
        hash += hash * 13 + _BllchrgID;
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BllchrgModel)) {
            return false;
        }        
        BllchrgModel other = (BllchrgModel) obj;        
        return (this._BllchrgID != other._BllchrgID);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat fm = new SimpleDateFormat("MM-dd-yyyy");
        
        String info = "Bllchrg [ bllchrgID=" + _BllchrgID;
        if (_Rsv != null)
            info += " | Rsv [ rsvID=" + Integer.toString(_Rsv.getRsvID()) + " ]";
        info += " | lineDesc=" + _LineDescription;
        info += " | amount=" + Double.toString(_Amount);
        if (_DateCharged != null)
            info += " | dateCharged=" + fm.format(_DateCharged);
        if (_DatePaid != null)
            info += " | datePaid=" + fm.format(_DatePaid);
        info += " | isPaid=" + Boolean.toString(_IsPaid);
        info += " ]";
        return  info;
    }

    void setRsv(RsvModel rsv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
