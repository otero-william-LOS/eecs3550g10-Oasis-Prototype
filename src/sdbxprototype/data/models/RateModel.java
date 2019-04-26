package sdbxprototype.data.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;

/**
 *
 * @author los
 */
public class RateModel extends DataModel {
    
    //  Attributes
    
    //  RateDate Attribute
    /**
        CSV Column; non-volatile
        Date
        Acts as primary key for this specific base rate
    */
    private Date m_RateDate;
    //hidden; for now
    public Date getRateSqlDate() {return m_RateDate;}
    public void setRateSqlDate(Date rateDate) {this.m_RateDate = rateDate;}
    //  Additional RateDate Get/Set Using LocalDate Conversion
    public LocalDate getRateDate() {return m_RateDate.toLocalDate();}
    public void setRateDate(LocalDate rateDate) {this.m_RateDate = Date.valueOf(rateDate);}
    
    //  BaseRate Attribute
    /**
        CSV Column; non-volatile
        double
        Base charge for any room on a specific day
    */
    private double m_BaseRate;
    public double getBaseRate() {return m_BaseRate;}
    public void setBaseRate(double baseRate) {this.m_BaseRate = baseRate;}
    
    
    //  Model Constructors
    private RateModel(Date rateDate, double baseRate) {
        this.m_RateDate = rateDate;
        this.m_BaseRate = baseRate;
    }
    
    public RateModel(LocalDate rateDate, double baseRate) {
        this(Date.valueOf(rateDate), baseRate);
    }
    
    public RateModel(LocalDate rateDate) {
        this(rateDate, 0);
    }

    public RateModel() {
        this((Date)null, 0);
    }    
    
    // Overrides
    @Override
    public int hashCode() {
        // TODO: Warning 
        // - this method can create orphans when using a HashSet, or similar
        // - highly recommend to use some immutable Attribute (strings)
        int hash = 3;
        hash += hash * 11 + m_RateDate.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RateModel)) {
            return false;
        }
        RateModel other = (RateModel) obj;
        return (this.m_RateDate != other.m_RateDate);
    }

    @Override
    public String toString() {
        SimpleDateFormat fm = new SimpleDateFormat("MM-dd-yyyy");
        String info = "Rate [";
        if(m_RateDate != null)
            info += " rateDate=" + fm.format(m_RateDate);
        info += " | baseRate=" + Double.toString(m_BaseRate);
        info += " ]";
        return  info;
    }
}
