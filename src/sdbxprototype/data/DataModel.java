package sdbxprototype.data;

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
}
