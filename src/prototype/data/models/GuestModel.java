package prototype.data.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author los
 */
public class GuestModel extends DataModel {
    
    //  Attributes
    
    //  GuestID Attribute
    /**
        CSV Column; non-volatile
        int; (String in CSV)
        Provides unique id for guest infro
    */
    private int m_GuestID = 0;
    public int getGuestID() {return m_GuestID;}
    public void setGuestID(int guestID) {this.m_GuestID = guestID;}
    
    //  Name Attribute
    /**
        CSV Column; non-volatile
        String
        Guest's Last Name
    */
    private String m_Name = "";
    public String getName() {return m_Name;}
    public void setName(String name) {this.m_Name = name;}
    
    //  CCInfo Attribute
    /**
        CSV Column; non-volatile
        String
        Provided credit card info for payment processing
    */
    private String m_CCInfo = "";
    public String getCCInfo() {return m_CCInfo;}
    public void setCCInfo(String ccInfo) {this.m_CCInfo = ccInfo;}
    
    //  Email Attribute
    /**
        CSV Column; non-volatile
        String
        Guest's email for communication
    */
    private String m_Email = "";
    public String getEmail() {return m_Email;}
    public void setEmail(String email) {this.m_Email = email;}
    
    //  ListRsv Attribute
    /**
        Entity Relation; volatile
        DataModel
        one-to-(1+) rsv relation
    */
    private List<ReservationModel> m_ListRsv = new ArrayList<>();
    public List<ReservationModel> getListRsv() {return m_ListRsv;}
    public void setListRsv(List<ReservationModel> list) {this.m_ListRsv = list;}
    
    
    //  Model Constructor
    public GuestModel(int guestID, String name, String ccInfo, String email, List<ReservationModel> listRsv) {
        this.m_GuestID = guestID;
        this.m_Name = name;
        this.m_CCInfo = ccInfo;
        this.m_Email = email;
        this.m_ListRsv = listRsv;
    }
    
    public GuestModel(int guestID, String name, String ccInfo, String email) {
        this(guestID, name, ccInfo, email, new ArrayList<>());
    }
    
    public GuestModel(int guestID, String name, String email) {
        this(guestID, name, "", email, new ArrayList<>());
    }
    
    public GuestModel() {
        this(0, "", "", "", new ArrayList<>());
    }
    
    // Overrides
    @Override
    public int hashCode() {
        // TODO: Warning 
        // - this method can create orphans when using a HashSet, or similar
        // - highly recommend to use some immutable Attribute (strings)
        int hash = 1;
        hash += hash * 23 + m_GuestID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GuestModel)) {
            return false;
        }        
        GuestModel other = (GuestModel) obj;        
        return (this.m_GuestID == other.m_GuestID);
    }
    
    @Override
    public String toString() {
        String info = "Guest [ guestID=" + Integer.toString(m_GuestID);
        if (m_Name != null)
            info += " | name=" + m_Name;
        if (m_Email != null)
            info += " | email=" + m_Email;
        if (m_CCInfo != null)
            info += " | ccInfo=" + m_CCInfo;
        if (m_ListRsv != null){
            info += " | ListRsv.size=" + Integer.toString(m_ListRsv.size());
        }
        info += " ]";
        return  info;
    }
    
    public static final GuestModel EMPTY_ENTITY = new GuestModel();
}
