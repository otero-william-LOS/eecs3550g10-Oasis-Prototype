package sdbxprototype.data.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Date;
import sdbxprototype.data.RsvType;
import static sdbxprototype.data.models.DataModel.localDateFromUtilDate;
import static sdbxprototype.data.models.DataModel.utilDateFromLocalDate;

/**
 *
 * @author los
 */
public class RsvModel extends DataModel {
    
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
    public void setDateArrive(Date dateArrive) {this._DateArrive = dateArrive;}
    
    //  Additional DateArrive Get/Set Using LocalDate Conversion
    public LocalDate getDateArriveAsLocalDate() {return localDateFromUtilDate(_DateArrive);}
    public void setDateArriveFromLocalDate(LocalDate dateArrive) {this._DateArrive = utilDateFromLocalDate(dateArrive);}
    
    //  DateDepart Attribute
    /*
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date _DateDepart;
    public Date getDateDepart() {return _DateDepart;}
    public void setDateDepart(Date dateDepart) {this._DateDepart = dateDepart;}
    
    //  Additional DateDepart Get/Set Using LocalDate Conversion
    public LocalDate getDateDepartAsLocalDate() {return localDateFromUtilDate(_DateDepart);}
    public void setDateDepartFromLocalDate(LocalDate dateDepart) {this._DateDepart = utilDateFromLocalDate(dateDepart);}
    
    //  DatePaid Attribute
    /*
        CSV Column; non-volatile
        Date
        Records Date that specific Rsv was fully (pre-)payed
    */
    private Date _DatePaid;
    public Date getDatePaid() {return _DatePaid;}
    public void setDatePaid(Date datePaid) {this._DatePaid = datePaid;}
    
    //  Additional DatePaid Get/Set Using LocalDate Conversion
    public LocalDate getDatePaidAsLocalDate() {return localDateFromUtilDate(_DatePaid);}
    public void setDatePaidFromLocalDate(LocalDate datePaid) {this._DatePaid = utilDateFromLocalDate(datePaid);}
    
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
    private RoomModel _Room;
    public RoomModel getRoom() {return _Room;}
    public void setRoom(RoomModel room) {this._Room = room;}
    
    //  Guest Attribute
    /*
        CSV Column; non-volatile
        GuestModel; (GuestID String in CSV)
        Unique guest who made this rsv
        (1+)-to-one
    */
    private GuestModel _Guest;
    public GuestModel getGuest() {return _Guest;}
    public void setGuest(GuestModel guest) {this._Guest = guest;}
    
    //  ListBllchrg Attribute
    /*
        Entity Relation; volatile
        Collection<Bllchrg>
        one-to-(0+) list of bill charges
    */
    private List<BllchrgModel> _ListBllchrg;
    public List<BllchrgModel> getListBllchrg() {return _ListBllchrg;}
    public void setListBllchrg(List<BllchrgModel> list) {this._ListBllchrg = list;}
    
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
    public void setIsConcluded(boolean isConcluded) {this._IsConcluded = isConcluded;}
    
    
    //  Model Constructor
    public RsvModel(int rsvID, Date dateArrive, Date dateDepart, Date datePaid, RsvType rsvType, 
            RoomModel room, GuestModel guest, List<BllchrgModel> listBllchrg, 
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        this._RsvID = rsvID;
        this._DateArrive = dateArrive;
        this._DateDepart = dateDepart;
        this._DatePaid = datePaid;
        this._RsvType = rsvType;
        this._Room = room;
        this._Guest = guest;
        this._ListBllchrg = listBllchrg;
        this._IsNoShow = isNoShow;
        this._IsPaid = isPaid;
        this._IsConcluded = isConcluded;
    }

    public RsvModel(int rsvID, Date dateArrive, Date dateDepart, Date datePaid, RsvType rsvType, 
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        this(rsvID, dateArrive, dateDepart, datePaid, rsvType, null, null, null, isNoShow, isPaid, isConcluded);
    }

    public RsvModel(int rsvID, Date dateArrive, Date dateDepart, RsvType rsvType) {
        this(rsvID, dateArrive, dateDepart, null, rsvType, null, null, null, false, false, false);
    }

    public RsvModel(int rsvID, Date dateArrive, Date dateDepart) {
        this(rsvID, dateArrive, dateDepart, null, null, null, null, null, false, false, false);
    }

    public RsvModel() {
        this(0, null, null, null, null, null, null, null, false, false, false);
    }
    
    // SDBX: DevConstructor Only
    public RsvModel(int rsvID) {
        this(rsvID, null, null, null, null, null, null, null, false, false, false);
    }    
    
    @Override
    public int hashCode() {
        // TODO: Warning 
        // - this method will create orphans when using a HashSet, or similar
        // - highly recommend to use some immutable Attribute (strings)
        int hash = 5;
        hash += hash * 19 + this._RsvID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RsvModel)) {
            return false;
        }        
        RsvModel other = (RsvModel) obj;        
        return (this._RsvID != other._RsvID);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat fm = new SimpleDateFormat("MM-dd-yyyy");
    
        String info = "Rsv [ rsvID=" + Integer.toString(_RsvID);
        
        if (_DateArrive != null)
            info += " | dateArrive=" + fm.format(_DateArrive);
        if (_DateDepart != null)
            info += " | dateDepart=" + fm.format(_DateDepart);
        if (_DatePaid != null)
            info += " | datePaid=" + fm.format(_DatePaid);
        
        if (_RsvType != null)
            info += " | rsvType=" + _RsvType.name();
        if (_Room != null)
            info += " | Room [ roomID=" + Integer.toString(_Room.getRoomID()) + " ]";
        if (_Guest != null)
            info += " | Guest [ guestID=" + Integer.toString(_Guest.getGuestID());
        if (_ListBllchrg != null){
            info += " | ListBllchrg.size=" + Integer.toString(_ListBllchrg.size());
        }
    
        info += " | isNoShow=" + Boolean.toString(_IsNoShow);
        info += " | isPaid=" + Boolean.toString(_IsPaid);
        info += " | isConcluded=" + Boolean.toString(_IsConcluded);
        info += " ]";
        return  info;
    }
}
