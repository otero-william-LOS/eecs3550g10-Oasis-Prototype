package prototype.data.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;

/**
 *
 * @author los
 */
public class BillChargeModel extends DataModel {
    
    //  Attributes
    
    //  BllchrgID Attribute
    /**
        CSV Column; non-volatile
        int; (String in CSV)
        Unique id for this specific bill charge line item
    */
    private int m_BllchrgID = 0;
    public int getBillChargeID() {return m_BllchrgID;}
    public void setBillChargeID(int bllchrgID) {this.m_BllchrgID = bllchrgID;}    
    
    //  Reservation Attribute
    /**
        CSV Column; non-volatile
        ReservationModel; (RsvID String in CSV)
        (0+)-to-one rsv relation
    */
    private ReservationModel m_Rsv = ReservationModel.EMPTY_ENTITY;
    public ReservationModel getReservation() {return m_Rsv;}
    public void setReservation(ReservationModel rsv) {this.m_Rsv = rsv;}
    
    //  LineDescription Attribute
    /**
        CSV Column; non-volatile
        String
        Desc
    */
    private String m_LineDesc = "";
    public String getLineDescription() {return m_LineDesc;}
    public void setLineDescription(String lineDesc) {this.m_LineDesc = lineDesc;}
    
    //  Amount Attribute
    /**
        CSV Column; non-volatile
        double; (string in CSV)
        Desc
    */
    private double m_Amount = 0.0;
    public double getAmount() {return m_Amount;}
    public void setAmount(double amount) {this.m_Amount = amount;}
    
    //  DateCharged Attribute
    /**
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date m_DateCharged = new Date(Long.MIN_VALUE);
    //hidden; future dev
    protected Date getSqlDateCharged() {return m_DateCharged;}
    protected void setSqlDateCharged(Date dCharged) {this.m_DateCharged = dCharged;}
    //  Additional DateCharged Get/Set Using LocalDate Conversion
    public LocalDate getDateCharged() {
        return (m_DateCharged != null && !m_DateCharged.equals(new Date(Long.MIN_VALUE))) ?  m_DateCharged.toLocalDate() : LocalDate.MIN;
    }
    public void setDateCharged(LocalDate ldCharged) {
        this.m_DateCharged = (ldCharged != null && !ldCharged.equals(LocalDate.MIN)) ? Date.valueOf(ldCharged) : new Date(Long.MIN_VALUE);
    }
    
    //  DatePaid Attribute
    /**
        CSV Column; non-volatile
        Date
        Desc
    */
    private Date m_DatePaid = new Date(Long.MIN_VALUE);
    //  hidden; future expansion
    protected Date getSqlDatePaid() {return m_DatePaid;}
    protected void setSqlDatePaid(Date dPaid) {this.m_DatePaid = dPaid;}
    //  Additional DatePaid Get/Set Using LocalDate Conversion
    public LocalDate getDatePaid() {
        return (m_DatePaid != null && !m_DatePaid.equals(new Date(Long.MIN_VALUE))) ? m_DatePaid.toLocalDate() : LocalDate.MIN;
    }
    public void setDatePaid(LocalDate ldPaid) {
        this.m_DatePaid = (ldPaid != null && !ldPaid.equals(LocalDate.MIN)) ? Date.valueOf(ldPaid) : new Date(Long.MIN_VALUE);
    }
    
    //  IsPaid Attribute
    /**
        CSV Column; non-volatile
        boolean
        Desc
    */
    private boolean m_IsPaid = false;
    public boolean isPaid() {return m_IsPaid;}
    public void setIsPaid(boolean isPaid) {this.m_IsPaid = isPaid;}
        
    //  Model Constructor
    private BillChargeModel(int bllchrgID, ReservationModel rsv, String lineDesc, double amount, Date dateCharged, Date datePaid, boolean isPaid) {
        this.m_BllchrgID = bllchrgID;
        this.m_Rsv = rsv;
        this.m_LineDesc = lineDesc;
        this.m_Amount = amount;
        this.m_DateCharged = dateCharged;
        this.m_DatePaid = datePaid;
        this.m_IsPaid = isPaid;
    }
    
    public BillChargeModel(int bllchrgID, ReservationModel rsv, String lineDesc, double amount, LocalDate dateCharged, LocalDate datePaid, boolean isPaid) {
        this(bllchrgID, rsv, lineDesc, amount, (Date)null, null, isPaid);
        this.m_DateCharged = (dateCharged != null) ? Date.valueOf(dateCharged) : new Date(Long.MIN_VALUE);
        this.m_DatePaid = (datePaid != null) ? Date.valueOf(datePaid) : new Date(Long.MIN_VALUE);
    }
    public BillChargeModel(int bllchrgID, ReservationModel rsv, String lineDesc, double amount, LocalDate dateCharged) {
        this(bllchrgID, rsv, lineDesc, amount, dateCharged, LocalDate.MIN, false);
    }
    public BillChargeModel(int bllchrgID, String lineDesc, double amount, LocalDate dateCharged) {
        this(bllchrgID, ReservationModel.EMPTY_ENTITY, lineDesc, amount, dateCharged, LocalDate.MIN, false);
    }
    public BillChargeModel() {
        this(0, ReservationModel.EMPTY_ENTITY, "", 0, new Date(Long.MIN_VALUE), new Date(Long.MIN_VALUE), false);
    }
    
    // Overrides
    @Override
    public int hashCode() {
        // TODO: Warning 
        // - this method can create orphans when using a HashSet, or similar
        // - highly recommend to use some immutable Attribute (strings)
        int hash = 2;
        hash += hash * 13 + m_BllchrgID;
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BillChargeModel)) {
            return false;
        }        
        BillChargeModel other = (BillChargeModel) obj;        
        return (this.m_BllchrgID == other.m_BllchrgID);
    }
    
    @Override
    public String toString() {
        SimpleDateFormat fm = new SimpleDateFormat("MM-dd-yyyy");
        
        String info = "Bllchrg [ bllchrgID=" + m_BllchrgID;
        if (m_Rsv != null && !m_Rsv.equals(ReservationModel.EMPTY_ENTITY))
        {info += " | Rsv [ rsvID=" + Integer.toString(m_Rsv.getReservationID()) + " ]";}
        if (m_LineDesc != null)
        {info += " | lineDesc=" + m_LineDesc;}
        info += " | amount=" + Double.toString(m_Amount);
        if (m_DateCharged != null && !m_DateCharged.equals(new Date(Long.MIN_VALUE)))
        {info += " | dateCharged=" + fm.format(m_DateCharged);}
        if (m_DatePaid != null && !m_DatePaid.equals(new Date(Long.MIN_VALUE)))
        {info += " | datePaid=" + fm.format(m_DatePaid);}
        info += " | isPaid=" + Boolean.toString(m_IsPaid);
        info += " ]";
        return  info;
    }
    
    public static final BillChargeModel EMPTY_ENTITY = new BillChargeModel();
}
