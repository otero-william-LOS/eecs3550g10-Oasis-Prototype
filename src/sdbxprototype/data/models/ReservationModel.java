package sdbxprototype.data.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author los
 */
public class ReservationModel extends DataModel {
    
    //  Attributes
    
    //  RsvID Attribute
    /**
        CSV Column; non-volatile
        int; (String in CSV)
        Unique primary key for specific rsv
    */
    private int m_RsvID;
    public int getReservationID() {return m_RsvID;}
    public void setReservationID(int rsvID) {this.m_RsvID = rsvID;}
    
    //  DateArrive Attribute
    /**
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date m_DateArrive;
    // hidden; future dev
    protected Date getSqlDateArrive() {return m_DateArrive;}
    protected void setSqlDateArrive(Date dateArrive) {this.m_DateArrive = dateArrive;}
    //  Additional DateArrive Get/Set Using LocalDate Conversion
    public LocalDate getDateArrive() {return m_DateArrive.toLocalDate();}
    public void setDateArrive(LocalDate dateArrive) {this.m_DateArrive = Date.valueOf(dateArrive);}
    
    //  DateDepart Attribute
    /**
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date m_DateDepart;
    //  hidden; future dev
    protected Date getSqlDateDepart() {return m_DateDepart;}
    protected void setSqlDateDepart(Date dateDepart) {this.m_DateDepart = dateDepart;}
    //  Additional DateDepart Get/Set Using LocalDate Conversion
    public LocalDate getDateDepart() {return m_DateDepart.toLocalDate();}
    public void setDateDepart(LocalDate dateDepart) {this.m_DateDepart = Date.valueOf(dateDepart);}
    
    //  DatePaid Attribute
    /**
        CSV Column; non-volatile
        Date
        Records Date that specific Rsv was fully (pre-)payed
    */
    private Date m_DatePaid;
    //hidden; future dev
    protected Date getSqlDatePaid() {return m_DatePaid;}
    protected void setSqlDatePaid(Date datePaid) {this.m_DatePaid = datePaid;}
    //  Additional DatePaid Get/Set Using LocalDate Conversion
    public LocalDate getDatePaid() {return m_DatePaid.toLocalDate();}
    public void setDatePaid(LocalDate datePaid) {this.m_DatePaid = Date.valueOf(datePaid);}
    
    //  ReservationType Attribute
    /**
        CSV Column; non-volatile
        Enum
        Assigns type of specific Rsv, and will determine rate charged
    */
    private ReservationType m_RsvType;
    public ReservationType getReservationType() {return m_RsvType;}
    public void setReservationType(ReservationType rsvType) {this.m_RsvType = rsvType;}
    
    //  Room Attribute
    /**
        CSV Column; non-volatile
        RoomModel; (RoomID String in CSV)
        Specific Room assigned to this rsv on morning of arrival
        (0 or 1)-to-one
    */
    private RoomModel m_Room;
    public RoomModel getRoom() {return m_Room;}
    public void setRoom(RoomModel room) {this.m_Room = room;}
    
    //  Guest Attribute
    /**
        CSV Column; non-volatile
        GuestModel; (GuestID String in CSV)
        Unique guest who made this rsv
        (1+)-to-one
    */
    private GuestModel m_Guest;
    public GuestModel getGuest() {return m_Guest;}
    public void setGuest(GuestModel guest) {this.m_Guest = guest;}
    
    //  ListBllchrg Attribute
    /**
        Entity Relation; volatile
        Collection<Bllchrg>
        one-to-(0+) list of bill charges
    */
    private List<BillChargeModel> m_ListBllchrg;
    public List<BillChargeModel> getListBillCharges() {return m_ListBllchrg;}
    public void setListBillCharges(List<BillChargeModel> list) {this.m_ListBllchrg = list;}
    
    //  IsNoShow Attribute
    /**
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean m_IsNoShow;
    public boolean isNoShow() {return m_IsNoShow;}
    public void setIsNoShow(boolean isNoShow) {this.m_IsNoShow = isNoShow;}
    
    //  IsPaid Attribute
    /**
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean m_IsPaid;
    public boolean isPaid() {return m_IsPaid;}
    public void setIsPaid(boolean isPaid) {this.m_IsPaid = isPaid;}
    
    //  IsConcluded Attribute
    /**
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean m_IsConcluded;
    public boolean isConcluded() {return m_IsConcluded;}
    public void setIsConcluded(boolean isConcluded) {this.m_IsConcluded = isConcluded;}
        
    //  Model Constructor
    private ReservationModel(int rsvID, Date dateArrive, Date dateDepart, Date datePaid, ReservationType rsvType, 
            RoomModel room, GuestModel guest, List<BillChargeModel> listBllchrg, 
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        this.m_RsvID = rsvID;
        this.m_DateArrive = dateArrive;
        this.m_DateDepart = dateDepart;
        this.m_DatePaid = datePaid;
        this.m_RsvType = rsvType;
        this.m_Room = room;
        this.m_Guest = guest;
        this.m_ListBllchrg = listBllchrg;
        this.m_IsNoShow = isNoShow;
        this.m_IsPaid = isPaid;
        this.m_IsConcluded = isConcluded;
    }
    
    public ReservationModel(int rsvID, LocalDate dArrv, LocalDate dDprt, LocalDate dPaid, 
            ReservationType rsvType, RoomModel room, GuestModel guest, List<BillChargeModel> listBllchrg, 
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        this(rsvID, Date.valueOf(dArrv), Date.valueOf(dDprt), Date.valueOf(dPaid), rsvType, room, guest, listBllchrg, isNoShow, isPaid, isConcluded);
    }

    public ReservationModel(int rsvID, LocalDate dArrv, LocalDate dDprt, LocalDate dPaid, 
            ReservationType rsvType, boolean isNoShow, boolean isPaid, boolean isConcluded) {
        this(rsvID, dArrv, dDprt, dPaid, rsvType, null, null, null, isNoShow, isPaid, isConcluded);
    }

    public ReservationModel(int rsvID, LocalDate dArrv, LocalDate dDprt, ReservationType rsvType) {
        this(rsvID, dArrv, dDprt, null, rsvType, null, null, null, false, false, false);
    }

    public ReservationModel(int rsvID, LocalDate dArrv, LocalDate dDprt) {
        this(rsvID, dArrv, dDprt, null, null, null, null, null, false, false, false);
    }

    public ReservationModel() {
        this(0, (Date)null, null, null, null, null, null, null, false, false, false);
    }
    
    @Override
    public int hashCode() {
        // TODO: Warning 
        // - this method will create orphans when using a HashSet, or similar
        // - highly recommend to use some immutable Attribute (strings)
        int hash = 5;
        hash += hash * 19 + this.m_RsvID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ReservationModel)) {
            return false;
        }        
        ReservationModel other = (ReservationModel) obj;        
        return (this.m_RsvID != other.m_RsvID);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat fm = new SimpleDateFormat("MM-dd-yyyy");
    
        String info = "Rsv [ rsvID=" + Integer.toString(m_RsvID);
        
        if (m_DateArrive != null)
            info += " | dateArrive=" + fm.format(m_DateArrive);
        if (m_DateDepart != null)
            info += " | dateDepart=" + fm.format(m_DateDepart);
        if (m_DatePaid != null)
            info += " | datePaid=" + fm.format(m_DatePaid);
        
        if (m_RsvType != null)
            info += " | rsvType=" + m_RsvType.name();
        if (m_Room != null)
            info += " | Room [ roomID=" + Integer.toString(m_Room.getRoomID()) + " ]";
        if (m_Guest != null)
            info += " | Guest [ guestID=" + Integer.toString(m_Guest.getGuestID());
        if (m_ListBllchrg != null){
            info += " | ListBllchrg.size=" + Integer.toString(m_ListBllchrg.size());
        }
    
        info += " | isNoShow=" + Boolean.toString(m_IsNoShow);
        info += " | isPaid=" + Boolean.toString(m_IsPaid);
        info += " | isConcluded=" + Boolean.toString(m_IsConcluded);
        info += " ]";
        return  info;
    }
}
