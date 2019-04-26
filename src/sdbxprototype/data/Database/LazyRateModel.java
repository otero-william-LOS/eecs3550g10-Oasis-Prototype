package Database;

import java.sql.Date;



/**
 *
 * @author los
 */
public class LazyRateModel {
    
    //  Attributes
    
    //  RateDate Attribute
    /*
        CSV Column; non-volatile
        Date
        Acts as primary key for this specific base rate
    */
    private Date _RateDate;
    public Date getRateDate() {return _RateDate;}
    public void setRateDate(Date rateDate) {this._RateDate = rateDate;}
    
    //  BaseRate Attribute
    /*
        CSV Column; non-volatile
        double
        Base charge for any room on a specific day
    */
    private double _BaseRate;
    public double getBaseRate() {return _BaseRate;}
    public void setBaseRate(double baseRate) {this._BaseRate = baseRate;}
    
    
    //  Model Constructors
    public LazyRateModel(Date rateDate, double baseRate) {
        this._RateDate = rateDate;
        this._BaseRate = baseRate;
    }
    
    public LazyRateModel(Date rateDate) {
        this._RateDate = rateDate;
        this._BaseRate = 0;
    }

    public LazyRateModel() {
        this._RateDate = null;
        this._BaseRate = 0;
    }    
}
