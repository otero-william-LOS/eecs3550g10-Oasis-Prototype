package sdbxprototype.data.models;

/**
 *
 * @author los
 */
public class RoomModel extends DataModel {
    
    // RoomID attribute
    /*
        CSV Column; non-volatile
        Short (exactly 45 rooms); (String in CSV)
        Identifies room w/ unique id
    */
    private short _RoomID;
    public short getRoomID() {return _RoomID;}
    public void setRoomID(short roomID) {this._RoomID = roomID;}
    
    
    // IsOccupied attribute
    /*
        CSV Column; non-volatile
        bool
        Flag room as Vacant or Occupied
        Provides unique room's last known status
    */
    private boolean _IsOccupied;
    public boolean getIsOccupied() {return _IsOccupied;}
    public void setIsOccupied(boolean isOccupied) {this._IsOccupied = isOccupied;}
    
    
    // RsvModel attribute
    /*
        Entity Relation; volatile
        RsvModel
        one-to-(0 or 1)
    */
    private RsvModel _Rsv;
    public RsvModel getRsv() {return _Rsv;}
    public void setRsv(RsvModel rsv) {_Rsv = rsv;}
    

    // RoomModel Constructors
    public RoomModel(short roomID, boolean isOcuppied, RsvModel rsvID) {
        this._RoomID = roomID;
        this._IsOccupied = isOcuppied;
        this._Rsv = rsvID;
    }
    public RoomModel(short roomID, boolean isOcuppied) {
        this(roomID, isOcuppied, null);
    }
    public RoomModel() {
        this((short)0, false, null);
    }
    
    // Overrides
    @Override
    public int hashCode() {
        // TODO: Warning 
        // - this method can create orphans when using a HashSet, or similar
        // - highly recommend to use some immutable Attribute (strings)
        int hash = 7;
        hash += hash * 17 + _RoomID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RoomModel)) {
            return false;
        }        
        RoomModel other = (RoomModel) obj;        
        return (this._RoomID != other._RoomID);
    }

    @Override
    public String toString() {
        String info = "Room [ roomID=" + Short.toString(_RoomID);
        info += " | isOccupied=" + Boolean.toString(_IsOccupied);
        if (_Rsv != null)
            info += " | Rsv [ rsvID=" + Integer.toString(_Rsv.getRsvID()) + " ]";
        info += " ]";
        return  info;
    }
}
