package sdbxprototype.data.models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * DataModel
 * 
 * establishes entity models as subclass
 * provides two common attributes for persistence
 * also, provides their get/set
 * 
 * NOTE:
 * originally intended to be a interface
 * get/set behaviors constant amongst all subclasses
 * interface would require repeat implementations
 * 
 * @author los
 */

public abstract class DataModel {
    
    // isModified attribute
    private boolean _IsModified;
    public void setIsModified(boolean val){_IsModified = val;}
    public boolean getIsModified(){return _IsModified;}
    
    // lastModified attribute
    private Date _LastModified;
    public Date getLastModified() {return _LastModified;}
    public void setLastModified(Date val) {_LastModified = val;}
    
    // SDBX LOS:
    /**
     * I want to provide additional get/set for all Date attributes,
     * to help the development of the schedulers.
     * 
     * This is additional functionality, and not intended to replace
     * the original UML class
     * 
     * This fix is based off of this stackoverflow answer,
     * https://stackoverflow.com/a/40143687
     *
     * @param date
     * @return localDate
     */
    
    public static LocalDate localDateFromUtilDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = instant.atZone(zid);
        return zdt.toLocalDate();
    }
    
    public static Date utilDateFromLocalDate(LocalDate local){
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = local.atStartOfDay(zid);
        Instant instant = zdt.toInstant();
        return Date.from(instant);
    }
}
