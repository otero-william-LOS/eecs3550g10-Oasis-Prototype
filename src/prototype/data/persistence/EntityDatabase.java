/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.persistence;

import prototype.data.models.ReservationType;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import prototype.data.models.ReservationModel;
import prototype.data.models.RoomModel;
import prototype.data.models.RateModel;
import prototype.data.models.GuestModel;
import prototype.data.models.BillChargeModel;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author wotero
 */
public class EntityDatabase {
    
    private static final List<ReservationModel> TBL_RSV_ENTITY = new ArrayList<>();
    private static final List<RoomModel> TBL_ROOM_ENTITY = new ArrayList<>();
    private static final List<RateModel> TBL_RATE_ENTITY = new ArrayList<>();
    private static final List<BillChargeModel> TBL_BLLCHRG_ENTITY = new ArrayList<>();
    private static final List<GuestModel> TBL_GUEST_ENTITY = new ArrayList<>();
    
    
    /**
     *  EntityDatabase Create & Retrieval
     */
    
    //<editor-fold defaultstate="collapsed" desc="Rsv Entity Create & Retrieval Methods">
    
    // SDBX: Retrieve Simple ReservationModel Table DEV Method
    public static List<ReservationModel> retrieveAllRsvs(){
        List<ReservationModel> rsvs = new ArrayList<>(TBL_RSV_ENTITY);
        return rsvs;
    }
    
    // stuff from Austin =======================================================
    //dummy database table for reservation
    private static ArrayList<ReservationModel> reservationTable = new ArrayList();

    // creates a new reservation for the first ReservationModel constructor
    public static void addReservation(LocalDate dateArrive, LocalDate dateDepart, LocalDate datePaid, ReservationType rsvType,
            RoomModel room, GuestModel guest, ArrayList<BillChargeModel> listBllchrg,
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        ReservationModel reservation = new ReservationModel(reservationTable.size() + 1, dateArrive, dateDepart, datePaid, rsvType, room, guest, listBllchrg,
                isNoShow, isPaid, isConcluded);
        reservationTable.add(reservation);
    }

    // creates a new reservation for the second ReservationModel constructor
    public static void addReservation(LocalDate dateArrive, LocalDate dateDepart, LocalDate datePaid, ReservationType rsvType,
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        ReservationModel reservation = new ReservationModel(reservationTable.size() + 1, dateArrive, dateDepart, datePaid, rsvType,
                isNoShow, isPaid, isConcluded);
        reservationTable.add(reservation);
    }

    // creates a new reservation for the third ReservationModel constructor
    public static void addReservation(LocalDate dateArrive, LocalDate dateDepart, ReservationType rsvType) {
        ReservationModel reservation = new ReservationModel(reservationTable.size() + 1, dateArrive, dateDepart, rsvType);
        reservationTable.add(reservation);
    }

    // creates a new reservation for the fourth ReservationModel constructor
    public static void addReservation(LocalDate dateArrive, LocalDate dateDepart) {
        ReservationModel reservation = new ReservationModel(reservationTable.size() + 1, dateArrive, dateDepart);
        reservationTable.add(reservation);
    }
    
    // creates a new reservation for the fourth ReservationModel constructor for use 
    // in intitial creation in the scheduler
    public static int addReservationSched(LocalDate dateArrive, LocalDate dateDepart) {
        int id = reservationTable.size() + 1;
        ReservationModel reservation = new ReservationModel(id, dateArrive, dateDepart);
        reservationTable.add(reservation);
        return id;
    }

    // changes the DateArrive property of the Reservation
    public static void changeRsvArrival(int primaryKey, Date arrival) {
        ReservationModel reservation = reservationTable.get(primaryKey);
//        reservation.setDateArrive(arrival);
        reservationTable.set(primaryKey, reservation);
    }

    // changes the DateDepart property of the Reservation
    public static void changeRsvDeparture(int primaryKey, Date departure) {
        ReservationModel reservation = reservationTable.get(primaryKey);
//        reservation.setDateDepart(departure);
        reservationTable.set(primaryKey, reservation);
    }

    // changes the ReservationType property of the Reservation
    public static void changeRsvType(int primaryKey, ReservationType rsvType) {
        ReservationModel reservation = reservationTable.get(primaryKey);
        reservation.setReservationType(rsvType);
        reservationTable.set(primaryKey, reservation);
    }

    public static ArrayList<ReservationModel> searchRsvByGuest(GuestModel guest) {
        ArrayList<ReservationModel> matchingRsvs = new ArrayList<>();
        for (ReservationModel rsv : reservationTable) {
            if (rsv.getGuest().getGuestID() == guest.getGuestID()) {
                matchingRsvs.add(rsv);
            }
        }      
        return matchingRsvs;
    }
    
    public static ArrayList<ReservationModel> searchRsvByRoom(RoomModel room) {
        ArrayList<ReservationModel> matchingRsvs = new ArrayList<>();
        for (ReservationModel rsv : reservationTable) {
            if (rsv.getRoom().getRoomID() == room.getRoomID()) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    public static ArrayList<ReservationModel> searchRsvByDtArrive(Date dateArrive) {
        ArrayList<ReservationModel> matchingRsvs = new ArrayList<>();
        for (ReservationModel rsv : reservationTable) {
//            if (rsv.getSqlDateArrive() == dateArrive) {
//                matchingRsvs.add(rsv);
//            }
        }
        return matchingRsvs;
    }
    
    public static ArrayList<ReservationModel> searchRsvByDtDepart(Date dateDepart) {
        ArrayList<ReservationModel> matchingRsvs = new ArrayList<>();
        for (ReservationModel rsv : reservationTable) {
//            if (rsv.getSqlDateArrive() == dateDepart) {
//                matchingRsvs.add(rsv);
//            }
        }
        return matchingRsvs;
    }
    
    // returns all reservations that are arriving, departing or in middle of stay on given date
    public static ArrayList<ReservationModel> searchRsvByDtArriveBtwn(Date date) {
        ArrayList<ReservationModel> matchingRsvs = new ArrayList<>();
        for (ReservationModel rsv : reservationTable) {
//            if((date.before(rsv.getSqlDateDepart()) & date.after(rsv.getSqlDateArrive()) || date.equals(rsv.getSqlDateArrive()) || date.equals(rsv.getSqlDateDepart())) == true){
//            //if (rsv.getSqlDateArrive() == dateArrive) {
//                matchingRsvs.add(rsv);
//            }
        }
        return matchingRsvs;
    }
    
    //returns all reservations of a given type
    public static ArrayList<ReservationModel> searchRsvByType(ReservationType rsvType) {
        ArrayList<ReservationModel> matchingRsvs = new ArrayList<>();
        for (ReservationModel rsv : reservationTable) {
            if (rsv.getReservationType() == rsvType) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    // returns all paid reservations
    public static ArrayList<ReservationModel> getAllPaid() {
        ArrayList<ReservationModel> matchingRsvs = new ArrayList<>();
        for (ReservationModel rsv : reservationTable) {
            if (rsv.isPaid() == true) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    // returns all conclded reservations
    public static ArrayList<ReservationModel> getAllConcluded() {
        ArrayList<ReservationModel> matchingRsvs = new ArrayList<>();
        for (ReservationModel rsv : reservationTable) {
            if (rsv.isConcluded() == true) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    // returns all no show reservations
    public static ArrayList<ReservationModel> getAllnoShow() {
        ArrayList<ReservationModel> matchingRsvs = new ArrayList<>();
        for (ReservationModel rsv : reservationTable) {
            if (rsv.isNoShow()== true) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }

    public static void assignRsvRoom(int primaryKey, RoomModel room) {
        ReservationModel reservation = reservationTable.get(primaryKey);
        reservation.setRoom(room);
        reservationTable.set(primaryKey, reservation);
    }

    public static void deassignRsvRoom(int primaryKey) {
        ReservationModel reservation = reservationTable.get(primaryKey);
        RoomModel room = new RoomModel();
        reservation.setRoom(room);
        reservationTable.set(primaryKey, reservation);
    }

    public static void flagRsvNoShow(int primaryKey) {
        ReservationModel reservation = reservationTable.get(primaryKey);
        reservation.setIsNoShow(true);
        reservationTable.set(primaryKey, reservation);
    }

    public static void flagRsvIsPaid(int primaryKey) {
        ReservationModel reservation = reservationTable.get(primaryKey);
        reservation.setIsPaid(true);
        reservationTable.set(primaryKey, reservation);
    }

    public static void flagRsvIsConcluded(int primaryKey) {
        ReservationModel reservation = reservationTable.get(primaryKey);
        reservation.setIsConcluded(true);
        reservationTable.set(primaryKey, reservation);
    }
    // end of stuff from austin ================================================
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Bllchrg Entity Create & Retrieval Methods">

    public static void addBllCharge(LocalDate DateCharged, double amount, String lineDesc) {
        BillChargeModel billing = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, lineDesc, amount, DateCharged);
        TBL_BLLCHRG_ENTITY.add(billing);
    }

    public static void addBllCharge(LocalDate DateCharged, double amount) {
        BillChargeModel billing = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, "", amount, DateCharged);
        TBL_BLLCHRG_ENTITY.add(billing);
    }

    public static void addBllCharge(LocalDate DateCharged) {
        BillChargeModel billing = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, "", 0, DateCharged);
    }

    public static ArrayList<BillChargeModel> searchByReservation(ReservationModel rsv) {
        ArrayList<BillChargeModel> matchingRsv = new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing: TBL_BLLCHRG_ENTITY) {
            if (billing.getReservation().getReservationID() == rsv.getReservationID()) {
                matchingRsv.add(billing);
            }
        }
        return matchingRsv;
    }
    
    public static ArrayList<BillChargeModel> searchByReservation(int rsvID) {
        ArrayList<BillChargeModel> matchingRsv = new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing: TBL_BLLCHRG_ENTITY) {
            if (billing.getReservation().getReservationID() == rsvID) {
                matchingRsv.add(billing);
            }
        }
        return matchingRsv;
    }
    
        public static ArrayList<BillChargeModel> searchByBllChrgID(int bllchrgID) {
        ArrayList<BillChargeModel> matchingBllChrgID = new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing : TBL_BLLCHRG_ENTITY) {
            if (billing.getBillChargeID() == bllchrgID) {
                matchingBllChrgID.add(billing);
            }
        }
        return matchingBllChrgID;
    }
    
    public static ArrayList<BillChargeModel>  searchByDateCharged(LocalDate dateChrged ) {
        ArrayList<BillChargeModel> matchingDateCharged= new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing:TBL_BLLCHRG_ENTITY) {
            if (billing.getDateCharged().equals(dateChrged)) {
                matchingDateCharged.add(billing);
            }
        }
        return matchingDateCharged;
    }
    
        public static ArrayList<BillChargeModel>  searchByDateRangeCharged(LocalDate dateChrgedSart, LocalDate dateChrgedend ) {
        ArrayList<BillChargeModel> matchingDateCharged= new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing:TBL_BLLCHRG_ENTITY) {
            if ((billing.getDateCharged().equals(dateChrgedSart) || billing.getDateCharged().isAfter(dateChrgedSart)) && 
                    ((billing.getDateCharged().equals(dateChrgedend) || billing.getDateCharged().isBefore(dateChrgedend)))) {
                matchingDateCharged.add(billing);
            }
        }
        return matchingDateCharged;
    }
    
    public static ArrayList<BillChargeModel>  searchByDatePaid(LocalDate DatePaid) {
        ArrayList<BillChargeModel> matchingDatePaid= new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing:TBL_BLLCHRG_ENTITY) {
            if (billing.getDatePaid().equals(DatePaid)) {
                matchingDatePaid.add(billing);
            }
        }
        return matchingDatePaid;
    }
    
    public static BillChargeModel getSingleCharge(int billChargeID){
        BillChargeModel returnbill = new BillChargeModel();
        boolean wasFound = false;
        for (BillChargeModel billing:TBL_BLLCHRG_ENTITY) {
            if (billing.getBillChargeID() == (billChargeID)) {
                returnbill = billing;
                wasFound = true;
            }
        }
        
        if (wasFound == true)
        return returnbill;
        else
        return null;
    }

    public static void flagBillIsPaid(int primaryKey) {
        BillChargeModel billingPaid = TBL_BLLCHRG_ENTITY.get(primaryKey);
        billingPaid.setIsPaid(true);
        TBL_BLLCHRG_ENTITY.set(primaryKey, billingPaid);
        System.out.println("Bill is Paid.");
    }

    public static List<BillChargeModel> retrieveAllBllchrgs() {
         List<BillChargeModel> bllchrgID = new ArrayList<>(TBL_BLLCHRG_ENTITY);
         return bllchrgID;
    }
    
     //Ask For help got stuck
    public static List<BillChargeModel> retrieveAllPaidchrgs() {
     List<BillChargeModel> isPaidCharge = new ArrayList<>(TBL_BLLCHRG_ENTITY);
       isPaidCharge.removeIf(isPaidC-> isPaidC.isPaid());
       return isPaidCharge; 
    } 
    
    public static List<BillChargeModel> retrieveAllUnpaidchrgs() {
       List<BillChargeModel> isPaidCharge = new ArrayList<>(TBL_BLLCHRG_ENTITY);
       isPaidCharge.removeIf(bill-> !bill.isPaid());
       return isPaidCharge; 
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Guest Entity Create & Retrieval Methods">
    
    public static ArrayList<GuestModel> guestTableData = new ArrayList();
    
    public static void createGuest(String Name, String Email, String CC_info) {
        GuestModel createGuest = new GuestModel(guestTableData.size() + 1, Name, Email, CC_info);
        guestTableData.add(createGuest);
    }
        
    public static void modifyGuest(String Name, String Email, String ccInfo) {
        GuestModel modifyGuest = new GuestModel(guestTableData.size(), Name, Email, ccInfo);
        guestTableData.add(modifyGuest);
        guestTableData.clear();
    }
    
    public static void modifyGuest(String Name, String Email) {
        GuestModel modifyGuest = new GuestModel(guestTableData.size(), Name, Email);
        guestTableData.add(modifyGuest);
        guestTableData.clear();
    }
    
    public static void retrieveGuest(String Name, String Email, String CC_info) {
        GuestModel retrieveGuest = new GuestModel(guestTableData.size(), Name, Email, CC_info);
        guestTableData.add(retrieveGuest);
    }
    
    public static ArrayList<GuestModel> searchByName(String guestName) {
        ArrayList<GuestModel> matchName = new  ArrayList<GuestModel>();
        
        for (GuestModel Name: guestTableData) {
            if (Name.getName().equals(guestName)) {
                matchName.add(Name);
            }
        }
        return matchName;
    }
    
    public static ArrayList<GuestModel> searchByGuest_ID(GuestModel Guest_ID) {
        ArrayList<GuestModel> matchGuest_ID = new  ArrayList<GuestModel>();
        
        for (GuestModel GuestID: guestTableData) {
            if (GuestID.getGuestID() != GuestID.getGuestID()) {
                matchGuest_ID.add(GuestID);
            }
        }
        return matchGuest_ID;
    }
//</editor-fold>
    
    //  nested class for rsv entity table
    public final static class ReservationTable {

//    protected static ArrayList<LazyRsvModel> rsvToLazyList(ArrayList<RsvModel> tempList) {
//        ArrayList<LazyRsvModel> list = new ArrayList<>();
//
//        for (int i = 0; i < tempList.size(); i++) {
//            LazyRsvModel newLzy = new LazyRsvModel();
//            newLzy.setRsvID(tempList.get(i).getRsvID());
//            newLzy.setDateArrive(tempList.get(i).getDateArrive());
//            newLzy.setDateDepart(tempList.get(i).getDateDepart());
//            newLzy.setDatePaid(tempList.get(i).getDatePaid());
//            newLzy.setRsvType(tempList.get(i).getRsvType());
//            if(tempList.get(i).getRoom() != null)
//            newLzy.setRoom(tempList.get(i).getRoom().getRoomID());
//            newLzy.setGuest(tempList.get(i).getGuest().getGuestID());
//            newLzy.setIsNoShow(tempList.get(i).isIsNoShow());
//            newLzy.setIsPaid(tempList.get(i).isIsPaid());
//            newLzy.setIsConcluded(tempList.get(i).isIsConcluded());
//            list.add(newLzy);
//        }
//        return list;
//    }
//
//    //-----------------------Request All data--------------------------//
//    public static ArrayList<LazyRsvModel> requestAll_LazyRSVData() {
//
//        return Database.Database.getRSV_Data();
//    }
//
//    public static ArrayList<RsvModel> requestAll_RSVData() {
//        ArrayList<RsvModel> rsvList = new ArrayList<>();
//        ArrayList<LazyRsvModel> lazyRsvList
//                = Database.Database.getRSV_Data();
//        ArrayList<LazyBllchrgModel> billList
//                = Database.Database.getBllchrg_Data();
//
//        RsvModel newRsv = new RsvModel();
//        for (int i = 0; i < lazyRsvList.size(); i++) {
//            newRsv = new RsvModel();
//            newRsv.setRsvID(lazyRsvList.get(i).getRsvID());
//            newRsv.setDateArrive(lazyRsvList.get(i).getDateArrive());
//            newRsv.setDateDepart(lazyRsvList.get(i).getDateDepart());
//            newRsv.setDatePaid(lazyRsvList.get(i).getDatePaid());
//            newRsv.setRsvType(lazyRsvList.get(i).getRsvType());
//            //createRoomModel(RsvID);
//            RoomModel room = creatRoomModel(lazyRsvList.get(i), newRsv);
//            room.setRsv(newRsv);
//            newRsv.setRoom(room);
//            //getGuestModel
//            newRsv.setGuest(creatGuestModel(lazyRsvList.get(i), newRsv));
//            newRsv.setIsNoShow(lazyRsvList.get(i).isIsNoShow());
//            newRsv.setIsPaid(lazyRsvList.get(i).isIsPaid());
//            newRsv.setIsConcluded(lazyRsvList.get(i).isIsConcluded());
//            rsvList.add(newRsv);
//        }
//
//        return rsvList;
//    }
//    //-----------------------<End> Request All data-----------------------//
//
//    //-------------------------Request Lists of Data --------------------//
//    public static ArrayList<RsvModel> requestRsvList(Date date) {
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (EntityDatabase.dateIsBetween(rsvList.get(i).getDateArrive(),
//                    rsvList.get(i).getDateDepart(), date)) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//
//        return filteredList;
//    }
//
//    public static ArrayList<RsvModel> requestRsvList(Date dateStart,
//            Date dateEnd) {
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
//                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//
//        return filteredList;
//    }
//
//    public static ArrayList<RsvModel> requestRsvIsPaidList() {
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (rsvList.get(i).isIsPaid()) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//        return filteredList;
//    }
//
//    //Probably will need to be re written
//    public static ArrayList<RsvModel> requestRsvIsPaidList(Date dateStart,
//            Date dateEnd) {
//
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
//                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)
//                    && rsvList.get(i).isIsPaid()) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//
//        return filteredList;
//
//    }
//
//    public static ArrayList<RsvModel> requestRsvIsNotPaidList() {
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (!rsvList.get(i).isIsPaid()) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//        return filteredList;
//    }
//
//    //Probably will need to be re written
//    public static ArrayList<RsvModel> requestRsvIsNotPaidList(Date dateStart,
//            Date dateEnd) {
//
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
//                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)
//                    && !rsvList.get(i).isIsPaid()) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//
//        return filteredList;
//
//    }
//
//    public static ArrayList<RsvModel> requestRsvIsNoShowList() {
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (rsvList.get(i).isIsNoShow()) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//        return filteredList;
//    }
//
//    //Probably will need to be re written
//    public static ArrayList<RsvModel> requestRsvIsNoShowList(Date dateStart,
//            Date dateEnd) {
//
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
//                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)
//                    && rsvList.get(i).isIsNoShow()) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//
//        return filteredList;
//
//    }
//
//    public static ArrayList<RsvModel> requestRsvIsConcludedList() {
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (rsvList.get(i).isIsConcluded()) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//        return filteredList;
//    }
//
//    //Probably will need to be re written
//    public static ArrayList<RsvModel> requestRsvIsPaidConcluded(Date dateStart,
//            Date dateEnd) {
//
//        ArrayList<RsvModel> filteredList = new ArrayList<>();
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
//                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)
//                    && rsvList.get(i).isIsPaid()) {
//                filteredList.add(rsvList.get(i));
//            }
//        }
//
//        return filteredList;
//
//    }
//    //-----------------------<END>Request Lists of Data ------------------//
//
//    //-------------------------Single Model Request --------------------//
//    public static RsvModel requestRSV(int rsvID) {
//        RsvModel reqRsv = new RsvModel();
//
//        ArrayList<RsvModel> rsvList = requestAll_RSVData();
//
//        for (int i = 0; i < rsvList.size(); i++) {
//            if (rsvList.get(i).getRsvID() == rsvID) {
//                reqRsv = rsvList.get(i);
//                i = rsvList.size() + 1;
//            }
//        }
//        return reqRsv;
//    }
//
//    //-----------------------<END> Single Model Reques-------------------//
    
    }
    
    //  nested class for room entity table
    public final static class RoomTable {
        // Database RoomTbl Retrieval Methods
        public static List<RoomModel> rtrvAllRooms() {
            List<RoomModel> rooms = new ArrayList<>(EntityDatabase.TBL_ROOM_ENTITY);
            return rooms;
        }

        public static List<RoomModel> rtrvOccupiedRooms() {
            List<RoomModel> rooms = new ArrayList<>(EntityDatabase.TBL_ROOM_ENTITY);
            rooms.removeIf((RoomModel room) -> !room.isOccupied());
            return rooms;
        }

        public static List<RoomModel> rtrvAvailableRooms() {
            List<RoomModel> rooms = new ArrayList<>(EntityDatabase.TBL_ROOM_ENTITY);
            rooms.removeIf(rm -> rm.isOccupied());
            return rooms;
        }

        public static RoomModel rtrvByReservation(ReservationModel queryRsv) {
            // TODO: Warning
            // - this method won't work if the unique Rsv and Room aren't linked
            RoomModel room = 
                    rtrvOccupiedRooms().stream().map(
                        RoomModel::getReservation
                    ).filter(
                        rsv -> rsv.equals(queryRsv)
                    ).map(
                        ReservationModel::getRoom
                    ).findFirst().orElse(null);
            return room;
        }
    }
    
    //  nested class for rate entity table
    public final static class RateTable {

        // add continuous range of rates w/ base rate
        public static void addRateRange(LocalDate startInclusive, LocalDate endExclusive, double baseRate) {
            LocalDate dt = startInclusive.plusDays(0);
            while (dt.isBefore(endExclusive)) {
                EntityDatabase.TBL_RATE_ENTITY.add(new RateModel(dt, baseRate));
                dt = dt.plusDays(1);
            }
        }

        // retrieve listRates by dateRange
        public static List<RateModel> rtrvByDateRange(Date startInclusive, Date endInclusive) {
            List<RateModel> rates = new ArrayList<>(EntityDatabase.TBL_RATE_ENTITY);
            rates.removeIf((RateModel rt) -> rt.getRateSqlDate().before(startInclusive));
            rates.removeIf((RateModel rt) -> rt.getRateSqlDate().after(endInclusive));
            return rates;
        }

        // retrieve all rates
        public static List<RateModel> rtrvAllRates() {
            List<RateModel> rates = new ArrayList<>(EntityDatabase.TBL_RATE_ENTITY);
            return rates;
        }

        // add single rate w/ base rate
        public static void addRate(LocalDate rateDate, double baseRate) {
            EntityDatabase.TBL_RATE_ENTITY.add(new RateModel(rateDate, baseRate));
        }

        // retrieve rate by date
        public static RateModel rtrvByDate(Date date) {
            RateModel rate = EntityDatabase.TBL_RATE_ENTITY.stream().filter((RateModel rt) -> rt.getRateSqlDate().equals(date)).findFirst().orElse(null);
            return rate;
        }
    }
    
    //  nested class for guest entity table
    public final static class GuestTable {

//      protected static ArrayList<LazyGuestModel> guestToLazyList(ArrayList<GuestModel> inList){
//          ArrayList<LazyGuestModel> list = new ArrayList<>();
//          
//          
//          for (int i =0; i < inList.size(); i++){
//              LazyGuestModel temp = new LazyGuestModel();
//              
//              temp.setGuestID(inList.get(i).getGuestID());
//              temp.setName(inList.get(i).getName());
//              temp.setCCInfo(inList.get(i).getCCInfo());
//              temp.setEmail(inList.get(i).getEmail());
//             
//              list.add(temp);
//          }
//          
//          return list;
//      }
//    //------------------Request All ------------------------------//
//     public static ArrayList<LazyGuestModel> requestAll_LazyGuestData() {
//        return Database.Database.getGUEST_Data();
//    }
//    public static ArrayList<GuestModel> requestAll_GuestData() {
//        ArrayList<GuestModel> guestList = new ArrayList<>();
//        ArrayList<LazyGuestModel> lazyGuestList =
//                Database.Database.getGUEST_Data();
//
//        GuestModel newGuest;
//        for (int i = 0; i < lazyGuestList.size(); i++) {
//            newGuest = new GuestModel();
//            newGuest.setGuestID(lazyGuestList.get(i).getGuestID());
//            newGuest.setName(lazyGuestList.get(i).getName());
//            newGuest.setCCInfo(lazyGuestList.get(i).getCCInfo());
//            newGuest.setEmail(lazyGuestList.get(i).getEmail());
//
//            guestList.add(newGuest);
//        }
//
//        return guestList;
//    }
//    //------------------<END> Request All ------------------------------//   
//
//    //----------------------Request Lists---------------------------//
//    public static ArrayList<GuestModel> requestGuestNameList(String name) {
//        ArrayList<GuestModel> reqList = new ArrayList<>();
//
//        ArrayList<GuestModel> guestList = requestAll_GuestData();
//
//        for (int i = 0; i < guestList.size(); i++) {
//            if (guestList.get(i).getName().contains(name)
//                    || guestList.get(i).getName().equals(name)) {
//                reqList.add(guestList.get(i));
//            }
//        }
//        return reqList;
//    }
//
//    public static ArrayList<GuestModel> requestGuestEmailList(String email) {
//        ArrayList<GuestModel> reqList = new ArrayList<>();
//
//        ArrayList<GuestModel> guestList = requestAll_GuestData();
//
//        for (int i = 0; i < guestList.size(); i++) {
//            if (guestList.get(i).getEmail().contains(email)
//                    || guestList.get(i).getEmail().equals(email)) {
//                reqList.add(guestList.get(i));
//            }
//        }
//        return reqList;
//    }
//
//    //----------------------<END> Request Lists---------------------------//
//    //------------------Request Single Model------------------------//
//    public static GuestModel requestGuest(int guestID) {
//        GuestModel guest = new GuestModel();
//
//        ArrayList<GuestModel> guestList = requestAll_GuestData();
//
//        for (int i = 0; i < guestList.size(); i++) {
//            if (guestList.get(i).getGuestID() == guestID) {
//                guest = guestList.get(i);
//            }
//        }
//        return guest;
//    }
//    //------------------<END> Request Single Model------------------------//
    
    }
    
    //  nested class for bllchrg entity table
    public final static class BillChargeTable {
        
    public static void addBllCharge(LocalDate DateCharged, double amount, String lineDesc) {
        BillChargeModel billing = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, lineDesc, amount, DateCharged);
        TBL_BLLCHRG_ENTITY.add(billing);
    }

    public static void addBllCharge(LocalDate DateCharged, double amount) {
        BillChargeModel billing = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, "", amount, DateCharged);
        TBL_BLLCHRG_ENTITY.add(billing);
    }

    public static void addBllCharge(LocalDate DateCharged) {
        BillChargeModel billing = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, "", 0, DateCharged);
    }

    public static List<BillChargeModel> searchByReservation(int rsvID) {
        List<BillChargeModel> matchingRsv = new  ArrayList<>();
        for (BillChargeModel billing: TBL_BLLCHRG_ENTITY) {
            if (billing.getReservation().getReservationID() == rsvID) {
                matchingRsv.add(billing);
            }
        }
        return matchingRsv;
    }
    
    public static List<BillChargeModel> searchByBllChrgID(int bllchrgID) {
        List<BillChargeModel> matchingBllChrgID = new  ArrayList<>();
        for (BillChargeModel billing : TBL_BLLCHRG_ENTITY) {
            if (billing.getBillChargeID() == bllchrgID) {
                matchingBllChrgID.add(billing);
            }
        }
        return matchingBllChrgID;
    }
    
    public static List<BillChargeModel>  searchByDateCharged(LocalDate dateChrged ) {
        List<BillChargeModel> matchingDateCharged= new  ArrayList<>();
        for (BillChargeModel billing:TBL_BLLCHRG_ENTITY) {
            if (billing.getDateCharged().equals(dateChrged)) {
                matchingDateCharged.add(billing);
            }
        }
        return matchingDateCharged;
    }
    
    public static List<BillChargeModel>  searchByDateRangeCharged(LocalDate dateChrgedSart, LocalDate dateChrgedend ) {
        List<BillChargeModel> matchingDateCharged= new  ArrayList<>();
        for (BillChargeModel billing:TBL_BLLCHRG_ENTITY) {
            if ((billing.getDateCharged().equals(dateChrgedSart) || billing.getDateCharged().isAfter(dateChrgedSart)) && 
                    ((billing.getDateCharged().equals(dateChrgedend) || billing.getDateCharged().isBefore(dateChrgedend)))) {
                matchingDateCharged.add(billing);
            }
        }
        return matchingDateCharged;
    }
    
    public static List<BillChargeModel>  searchByDatePaid(LocalDate DatePaid) {
        List<BillChargeModel> matchingDatePaid= new  ArrayList<>();
        for (BillChargeModel billing:TBL_BLLCHRG_ENTITY) {
            if (billing.getDatePaid().equals(DatePaid)) {
                matchingDatePaid.add(billing);
            }
        }
        return matchingDatePaid;
    }
    
    public static BillChargeModel getBillCharge(int billChargeID){
        BillChargeModel returnbill = new BillChargeModel();
        boolean wasFound = false;
        for (BillChargeModel billing:TBL_BLLCHRG_ENTITY) {
            if (billing.getBillChargeID() == (billChargeID)) {
                returnbill = billing;
                wasFound = true;
            }
        }
        
        if (wasFound == true)
        return returnbill;
        else
        return null;
    }

    public static List<BillChargeModel> retrieveAllBllchrgs() {
         List<BillChargeModel> bllchrgID = new ArrayList<>(TBL_BLLCHRG_ENTITY);
         return bllchrgID;
    }
    
     //Ask For help got stuck
    public static List<BillChargeModel> retrieveAllPaidchrgs() {
     List<BillChargeModel> isPaidCharge = new ArrayList<>(TBL_BLLCHRG_ENTITY);
       isPaidCharge.removeIf(isPaidC-> isPaidC.isPaid());
       return isPaidCharge; 
    } 
    
    public static List<BillChargeModel> retrieveAllUnpaidchrgs() {
       List<BillChargeModel> isPaidCharge = new ArrayList<>(TBL_BLLCHRG_ENTITY);
       isPaidCharge.removeIf(bill-> !bill.isPaid());
       return isPaidCharge; 
    }
    
    }
    
    //  nested class for development
    public final static class DevUtilities {
        
        //Room generation utilities
        private static void genRoomTableBase(boolean allOccupied, boolean partialOccupied, int nOccupied) {
            EntityDatabase.TBL_ROOM_ENTITY.clear();
            for (short i = 0; i < 45; i++) {
                short x = (short) (i + 1);
                EntityDatabase.TBL_ROOM_ENTITY.add(new RoomModel(x, false));
            } //all rooms are available
            //debug info
            System.out.println("\tAll rooms are generated and set as available.");
            if (allOccupied) {
                System.out.println("\tAll rooms are set as occupied");
            } else if (partialOccupied) {
                System.out.println("\tPartial amount occupied: " + Integer.toString(nOccupied));
            }
            if (allOccupied) {
                EntityDatabase.TBL_ROOM_ENTITY.forEach((RoomModel room) -> room.setIsOccupied(true));
            } else if (partialOccupied) {
                Random rndm = new Random();
                int cap = Math.min(nOccupied, 45);
                for (int i = 0; i < cap; i++) {
                    int x = rndm.nextInt(45);
                    if (!EntityDatabase.TBL_ROOM_ENTITY.get(x).isOccupied()) {
                        EntityDatabase.TBL_ROOM_ENTITY.get(x).setIsOccupied(true);
                    } else {
                        i--; // try again
                    }
                    //debug info
                    System.out.println("\tPartial Set Attempt Index: " + Integer.toString(x));
                }
            }
        }
        public static void generateRoomTableAllOccupied() {
            genRoomTableBase(true, false, 0);
        }
        public static void generateRoomTableAllAvailable() {
            genRoomTableBase(false, false, 0);
        }
        public static void generateRoomTablePartialOccupied(int nOccupied) {
            genRoomTableBase(false, true, nOccupied);
        }

        // Reservation Generation
        public static void generateReservationTable(int nRsv) {
            EntityDatabase.TBL_RSV_ENTITY.clear();
            for (int i = 0; i < nRsv; i++) {
                EntityDatabase.TBL_RSV_ENTITY.add(new ReservationModel(i + 1, LocalDate.now(), LocalDate.now()));
            }
            System.out.println("\tNumber of rsv generated: " + Integer.toString(nRsv));
        }

        //  SDBX: Dev dummy rate generation, creates rates for current year
        public static void genRateTableForYear() {
            EntityDatabase.TBL_RATE_ENTITY.clear();
            LocalDate today = LocalDate.now();
            LocalDate startYear = today.with(TemporalAdjusters.firstDayOfYear());
            Random rndm = new Random();
            double[] variance = {-10.0, -8.0, -7.5, -6.0, -5.0, -4.0, -2.5, -2.0, -1.0, 0.0, 1.0, 2.0, 2.5, 4.0, 5.0};
            int vSize = variance.length;
            System.out.println("jan to april");
            //  from jan to april, tourism is low, so rate should be low
            double rate = 200.0;    // arbitrary testing
            long numDays = ChronoUnit.DAYS.between(startYear, startYear.withMonth(5));
            for (long i = 0; i < numDays; i++) {
                int rndmIndex = rndm.nextInt(vSize);
                double vRate = rate + variance[rndmIndex];
                LocalDate dt = startYear.plusDays(i);
                RateTable.addRate(dt, vRate);
            }
            System.out.println("may to august");
            //  from may to august, tourism is high, so rate should be high
            rate = 375.0; // arbitrary testing
            numDays = ChronoUnit.DAYS.between(startYear.withMonth(5), startYear.withMonth(9));
            for (long i = 0; i < numDays; i++) {
                int rndmIndex = rndm.nextInt(vSize);
                double vRate = rate + variance[rndmIndex];
                LocalDate dt = startYear.withMonth(5).plusDays(i);
                RateTable.addRate(dt, vRate);
            }
            System.out.println("sept to oct");
            //  from sept to oct, tourism is low, so rate should be low
            rate = 175.0; // arbitrary testing
            numDays = ChronoUnit.DAYS.between(startYear.withMonth(9), startYear.withMonth(11));
            for (long i = 0; i < numDays; i++) {
                int rndmIndex = rndm.nextInt(vSize);
                double vRate = rate + variance[rndmIndex];
                LocalDate dt = startYear.withMonth(9).plusDays(i);
                RateTable.addRate(dt, vRate);
            }
            System.out.println("nov to dec");
            //  from nov to dec, tourism is high, so rate should be high
            rate = 425.0; // arbitrary testing
            numDays = ChronoUnit.DAYS.between(startYear.withMonth(11), startYear.plusYears(1));
            for (long i = 0; i < numDays; i++) {
                int rndmIndex = rndm.nextInt(vSize);
                double vRate = rate + variance[rndmIndex];
                LocalDate dt = startYear.withMonth(11).plusDays(i);
                RateTable.addRate(dt, vRate);
            }
        }
        
    }
}