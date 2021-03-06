package prototype.data.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.sql.Date;
import java.util.ArrayList;

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
    private int m_RsvID = 0;
    public int getReservationID() {return m_RsvID;}
    public void setReservationID(int rsvID) {this.m_RsvID = rsvID;}
    
    //  DateArrive Attribute
    /**
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date m_DateArrive = new Date(Long.MIN_VALUE);
    // hidden; future dev
    protected Date getSqlDateArrive() {return m_DateArrive;}
    protected void setSqlDateArrive(Date dateArrive) {this.m_DateArrive = dateArrive;}
    //  Additional DateArrive Get/Set Using LocalDate Conversion
    public LocalDate getDateArrive() {
        return (m_DateArrive != null && !m_DateArrive.equals(new Date(Long.MIN_VALUE))) ? m_DateArrive.toLocalDate() : LocalDate.MIN;
    }
    public void setDateArrive(LocalDate dateArrive) {
        this.m_DateArrive = (dateArrive != null && !dateArrive.equals(LocalDate.MIN)) ? Date.valueOf(dateArrive) : new Date(Long.MIN_VALUE);
    }
    
    //  DateDepart Attribute
    /**
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date m_DateDepart = new Date(Long.MIN_VALUE);
    //  hidden; future dev
    protected Date getSqlDateDepart() {return m_DateDepart;}
    protected void setSqlDateDepart(Date dateDepart) {this.m_DateDepart = dateDepart;}
    //  Additional DateDepart Get/Set Using LocalDate Conversion
    public LocalDate getDateDepart() {
        return (m_DateDepart != null && !m_DateDepart.equals(new Date(Long.MIN_VALUE))) ? m_DateDepart.toLocalDate() : LocalDate.MIN;
    }
    public void setDateDepart(LocalDate dateDepart) {
        this.m_DateDepart = (dateDepart != null && !dateDepart.equals(LocalDate.MIN)) ? Date.valueOf(dateDepart) : new Date(Long.MIN_VALUE);
    }
    
    //  DatePaid Attribute
    /**
        CSV Column; non-volatile
        Date
        Records Date that specific Rsv was fully (pre-)payed
    */
    private Date m_DatePaid= new Date(Long.MIN_VALUE);
    //hidden; future dev
    protected Date getSqlDatePaid() {return m_DatePaid;}
    protected void setSqlDatePaid(Date datePaid) {this.m_DatePaid = datePaid;}
    //  Additional DatePaid Get/Set Using LocalDate Conversion
    public LocalDate getDatePaid() {
        return (m_DatePaid != null && !m_DatePaid.equals(Long.MIN_VALUE)) ? m_DatePaid.toLocalDate() : LocalDate.MIN;
    }
    public void setDatePaid(LocalDate datePaid) {
        this.m_DatePaid = (datePaid != null && !datePaid.equals(LocalDate.MIN)) ? Date.valueOf(datePaid) : new Date(Long.MIN_VALUE);
    }
    
    //  ReservationType Attribute
    /**
        CSV Column; non-volatile
        Enum
        Assigns type of specific Rsv, and will determine rate charged
    */
    private ReservationType m_RsvType = ReservationType.CONVENTIONAL;
    public ReservationType getReservationType() {return m_RsvType;}
    public void setReservationType(ReservationType rsvType) {this.m_RsvType = rsvType;}
    
    //  Room Attribute
    /**
        CSV Column; non-volatile
        RoomModel; (RoomID String in CSV)
        Specific Room assigned to this rsv on morning of arrival
        (0 or 1)-to-one
    */
    private RoomModel m_Room = RoomModel.EMPTY_ENTITY;
    public RoomModel getRoom() {return m_Room;}
    public void setRoom(RoomModel room) {this.m_Room = room;}
    
    //  Guest Attribute
    /**
        CSV Column; non-volatile
        GuestModel; (GuestID String in CSV)
        Unique guest who made this rsv
        (1+)-to-one
    */
    private GuestModel m_Guest = GuestModel.EMPTY_ENTITY;
    public GuestModel getGuest() {return m_Guest;}
    public void setGuest(GuestModel guest) {this.m_Guest = guest;}
    
    //  ListBllchrg Attribute
    /**
        Entity Relation; volatile
        Collection<Bllchrg>
        one-to-(0+) list of bill charges
    */
    private List<BillChargeModel> m_ListBllchrg = new ArrayList<>();
    public List<BillChargeModel> getListBillCharges() {return m_ListBllchrg;}
    public void setListBillCharges(List<BillChargeModel> list) {this.m_ListBllchrg = list;}
    
    //  IsNoShow Attribute
    /**
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean m_IsNoShow = false;
    public boolean isNoShow() {return m_IsNoShow;}
    public void setIsNoShow(boolean isNoShow) {this.m_IsNoShow = isNoShow;}
    
    //  IsPaid Attribute
    /**
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean m_IsPaid = false;
    public boolean isPaid() {return m_IsPaid;}
    public void setIsPaid(boolean isPaid) {this.m_IsPaid = isPaid;}
    
    //  IsConcluded Attribute
    /**
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean m_IsConcluded = false;
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
        this(rsvID, (Date)null, null, null, rsvType, room, guest, listBllchrg, isNoShow, isPaid, isConcluded);
        this.m_DateArrive = (dArrv != null) ? Date.valueOf(dArrv) : new Date(Long.MIN_VALUE);
        this.m_DateDepart = (dDprt != null) ? Date.valueOf(dDprt) : new Date(Long.MIN_VALUE);
        this.m_DatePaid = (dPaid != null) ? Date.valueOf(dPaid) : new Date(Long.MIN_VALUE);
    }

    public ReservationModel(int rsvID, LocalDate dArrv, LocalDate dDprt, LocalDate dPaid, 
            ReservationType rsvType, boolean isNoShow, boolean isPaid, boolean isConcluded) {
        this(rsvID, dArrv, dDprt, dPaid, rsvType, RoomModel.EMPTY_ENTITY, GuestModel.EMPTY_ENTITY, 
                new ArrayList<>(), isNoShow, isPaid, isConcluded);
    }

    public ReservationModel(int rsvID, LocalDate dArrv, LocalDate dDprt, ReservationType rsvType) {
        this(rsvID, dArrv, dDprt, LocalDate.MIN, rsvType, RoomModel.EMPTY_ENTITY, GuestModel.EMPTY_ENTITY, 
                new ArrayList<>(), false, false, false);
    }

    public ReservationModel(int rsvID, LocalDate dArrv, LocalDate dDprt) {
        this(rsvID, dArrv, dDprt, LocalDate.MIN, ReservationType.CONVENTIONAL, RoomModel.EMPTY_ENTITY, 
                GuestModel.EMPTY_ENTITY, new ArrayList<>(), false, false, false);
    }

    public ReservationModel() {
        this(0, new Date(Long.MIN_VALUE), new Date(Long.MIN_VALUE), new Date(Long.MIN_VALUE), 
                ReservationType.CONVENTIONAL, RoomModel.EMPTY_ENTITY, GuestModel.EMPTY_ENTITY, new ArrayList<>(), 
                false, false, false);
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
        return (this.m_RsvID == other.m_RsvID);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat fm = new SimpleDateFormat("MM-dd-yyyy");
    
        String info = "Rsv [ rsvID=" + Integer.toString(m_RsvID);
        
        if (m_DateArrive != null && !m_DateArrive.equals(new Date(Long.MIN_VALUE)))
            info += " | dateArrive=" + fm.format(m_DateArrive);
        if (m_DateDepart != null && !m_DateDepart.equals(new Date(Long.MIN_VALUE)))
            info += " | dateDepart=" + fm.format(m_DateDepart);
        if (m_DatePaid != null && !m_DatePaid.equals(new Date(Long.MIN_VALUE)))
            info += " | datePaid=" + fm.format(m_DatePaid);
        
        if (m_RsvType != null)
            info += " | rsvType=" + m_RsvType.name();
        if (m_Room != null && !m_Room.equals(RoomModel.EMPTY_ENTITY))
            info += " | Room [ roomID=" + Integer.toString(m_Room.getRoomID()) + " ]";
        if (m_Guest != null && !m_Guest.equals(GuestModel.EMPTY_ENTITY))
            info += " | Guest [ guestID=" + Integer.toString(m_Guest.getGuestID()) + " ]";
        if (m_ListBllchrg != null){
            info += " | ListBllchrg.size=" + Integer.toString(m_ListBllchrg.size());
        }
    
        info += " | isNoShow=" + Boolean.toString(m_IsNoShow);
        info += " | isPaid=" + Boolean.toString(m_IsPaid);
        info += " | isConcluded=" + Boolean.toString(m_IsConcluded);
        info += " ]";
        return  info;
    }
    
    // public empty entity
    public static final ReservationModel EMPTY_ENTITY = new ReservationModel();
}
