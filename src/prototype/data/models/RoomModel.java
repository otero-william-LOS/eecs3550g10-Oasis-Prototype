package prototype.data.models;

/**
 *
 * @author los
 */
public class RoomModel extends DataModel {
    
    // RoomID attribute
    /**
        CSV Column; non-volatile
        Short (exactly 45 rooms); (String in CSV)
        Identifies room w/ unique id
    */
    private short m_RoomID = 0;
    public short getRoomID() {return m_RoomID;}
    public void setRoomID(short roomID) {this.m_RoomID = roomID;}
    
    
    // IsOccupied attribute
    /**
        CSV Column; non-volatile
        bool
        Flag room as Vacant or Occupied
        Provides unique room's last known status
    */
    private boolean m_IsOccupied = false;
    public boolean isOccupied() {return m_IsOccupied;}
    public void setIsOccupied(boolean isOccupied) {this.m_IsOccupied = isOccupied;}
    
    
    // ReservationModel attribute
    /**
        Entity Relation; volatile
        ReservationModel
        one-to-(0 or 1)
    */
    private ReservationModel m_Rsv = ReservationModel.EMPTY_ENTITY;
    public ReservationModel getReservation() {return m_Rsv;}
    public void setReservation(ReservationModel rsv) {m_Rsv = rsv;}
    

    // RoomModel Constructors
    public RoomModel(short roomID, boolean isOcuppied, ReservationModel rsvID) {
        this.m_RoomID = roomID;
        this.m_IsOccupied = isOcuppied;
        this.m_Rsv = rsvID;
    }
    public RoomModel(short roomID, boolean isOcuppied) {
        this(roomID, isOcuppied, ReservationModel.EMPTY_ENTITY);
    }
    public RoomModel() {
        this((short)0, false, ReservationModel.EMPTY_ENTITY);
    }
    
    // Overrides
    @Override
    public int hashCode() {
        // TODO: Warning 
        // - this method can create orphans when using a HashSet, or similar
        // - highly recommend to use some immutable Attribute (strings)
        int hash = 7;
        hash += hash * 17 + m_RoomID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RoomModel)) {
            return false;
        }        
        RoomModel other = (RoomModel) obj;        
        return (this.m_RoomID == other.m_RoomID);
    }

    @Override
    public String toString() {
        String info = "Room [ roomID=" + Short.toString(m_RoomID);
        info += " | isOccupied=" + Boolean.toString(m_IsOccupied);
        if (m_Rsv != null && !m_Rsv.equals(ReservationModel.EMPTY_ENTITY))
            info += " | Rsv [ rsvID=" + Integer.toString(m_Rsv.getReservationID()) + " ]";
        info += " ]";
        return  info;
    }
    
    public static final RoomModel EMPTY_ENTITY = new RoomModel();
}
