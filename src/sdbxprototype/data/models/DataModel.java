package sdbxprototype.data.models;

import java.time.LocalDate;
import java.sql.Date;

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
    private boolean m_IsModified;
    public void setIsModified(boolean isModified){m_IsModified = isModified;}
    public boolean isModified(){return m_IsModified;}
    
    // lastModified attribute
    private Date m_LastModified;
    // hidden; future expansion
    protected Date getSqlLastModified() {return m_LastModified;}
    protected void setSqlLastModified(Date date) {m_LastModified = date;}
    // ld public get/set
    public LocalDate getLastModified() {return m_LastModified.toLocalDate();}
    public void setLastModified(LocalDate local) {m_LastModified = Date.valueOf(local);}
}
