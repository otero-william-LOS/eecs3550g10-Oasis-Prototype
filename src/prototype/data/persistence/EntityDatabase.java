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
    
    private static final List<ReservationModel> rsvTable = new ArrayList<>();
    private static final List<RoomModel> roomTable = new ArrayList<>();
    private static final List<RateModel> rateTable = new ArrayList<>();
    private static final List<BillChargeModel> bllchrgTable = new ArrayList<>();
    private static final List<GuestModel> guestTable = new ArrayList<>();
    
    /**
     *  EntityDatabase Gen Methods for UnitTesting
     */
    
    //<editor-fold defaultstate="collapsed" desc="Rsv Dummy DevGeneration Methods">
    
    // SDBX: Gen Simple ReservationModel with RsvID only
    public static void genRsvTblBase(int numRsv){
        rsvTable.clear();
        for(int i = 0; i < numRsv; i++) 
            rsvTable.add(new ReservationModel(i + 1, LocalDate.now(), LocalDate.now()));
        
        System.out.println("\tNumber of rsv generated: " + Integer.toString(numRsv));
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Room Dummy DevGeneration Methods">
    
    // SDBX: Private base gen method for rooms
    private static void genRoomTableBase(boolean allOccupied, boolean partialOccupied, int numOccupied){
        roomTable.clear();
        
        for(short i = 0; i < 45; i++) {
            short x = (short)(i + 1);
            roomTable.add(new RoomModel(x, false));
        } //all rooms are available
        
        //debug info
        System.out.println("\tAll rooms are generated and set as available.");
        if (allOccupied) 
            System.out.println("\tAll rooms are set as occupied");
        else if (partialOccupied)
            System.out.println("\tPartial amount occupied: " + Integer.toString(numOccupied));
        
        if (allOccupied) 
            roomTable.forEach((room)->room.setIsOccupied(true));
        else if (partialOccupied){
            Random rndm = new Random();
            int cap = Math.min(numOccupied, 45);
            for(int i = 0; i < cap; i++) {
                int x = rndm.nextInt(45);
                if (!roomTable.get(x).isOccupied())
                    roomTable.get(x).setIsOccupied(true);
                else
                    i--; // try again
                //debug info
                System.out.println("\tPartial Set Attempt Index: " + Integer.toString(x));
            }
        }
    }
    // SDBX: Accesible room generation methods
    public static void genRoomTblAllAvailable(){genRoomTableBase(false, false, 0);}
    public static void genRoomTblAllOccupied(){genRoomTableBase(true, false, 0);}
    public static void genRoomTblPartialOccupied(int numOccupied){
        genRoomTableBase(false, true, numOccupied);
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Rate Dummy DevGeneration Methods">
    
    //  SDBX: Dev special comparator, maybe
    
    //  SDBX: Dev dummy rate generation, creates rates for current year
    public static void genRateTableForYear(){
        rateTable.clear();
        
        LocalDate today = LocalDate.now();
        LocalDate startYear = today.with(TemporalAdjusters.firstDayOfYear());
        
        Random rndm = new Random();
        double[] variance = {-10.0, -8.0, -7.5, -6.0, -5.0, -4.0, -2.5, -2.0, -1.0, 0.0, 1.0, 2.0, 2.5, 4.0, 5.0};
        int vSize = variance.length;
        
        System.out.println("jan to april");
        
        //  from jan to april, tourism is low, so rate should be low
        double rate = 200.0;    // arbitrary testing
        long numDays = ChronoUnit.DAYS.between(startYear, startYear.withMonth(5));
        for(long i = 0; i < numDays; i++){
            int rndmIndex = rndm.nextInt(vSize);
            double vRate = rate + variance[rndmIndex];
            LocalDate dt = startYear.plusDays(i);
            addRate(dt, vRate);
        }
        
        System.out.println("may to august");
        
        //  from may to august, tourism is high, so rate should be high
        rate = 375.0;    // arbitrary testing
        numDays = ChronoUnit.DAYS.between(startYear.withMonth(5), startYear.withMonth(9));
        for(long i = 0; i < numDays; i++){
            int rndmIndex = rndm.nextInt(vSize);
            double vRate = rate + variance[rndmIndex];
            LocalDate dt = startYear.withMonth(5).plusDays(i);
            addRate(dt, vRate);
        }
        
        System.out.println("sept to oct");
        
        //  from sept to oct, tourism is low, so rate should be low
        rate = 175.0;    // arbitrary testing
        numDays = ChronoUnit.DAYS.between(startYear.withMonth(9), startYear.withMonth(11));
        for(long i = 0; i < numDays; i++){
            int rndmIndex = rndm.nextInt(vSize);
            double vRate = rate + variance[rndmIndex];
            LocalDate dt = startYear.withMonth(9).plusDays(i);
            addRate(dt, vRate);
        }
        
        System.out.println("nov to dec");
        
        //  from nov to dec, tourism is high, so rate should be high
        rate = 425.0;    // arbitrary testing
        numDays = ChronoUnit.DAYS.between(startYear.withMonth(11), startYear.plusYears(1));
        for(long i = 0; i < numDays; i++){
            int rndmIndex = rndm.nextInt(vSize);
            double vRate = rate + variance[rndmIndex];
            LocalDate dt = startYear.withMonth(11).plusDays(i);
            addRate(dt, vRate);
        }
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Bllchrg Dummy DevGeneration Methods">
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Guest Dummy DevGeneration Methods">
    
//</editor-fold>
    
    /**
     *  EntityDatabase Create & Retrieval
     */
    
    //<editor-fold defaultstate="collapsed" desc="Rsv Entity Create & Retrieval Methods">
    
    // SDBX: Retrieve Simple ReservationModel Table DEV Method
    public static List<ReservationModel> retrieveAllRsvs(){
        List<ReservationModel> rsvs = new ArrayList<>(rsvTable);
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
    
    //<editor-fold defaultstate="collapsed" desc="Room Entity Create & Retrieval Methods">
    
    // Database RoomTbl Retrieval Methods
    public static List<RoomModel> rtrvAllRooms(){
        List<RoomModel> rooms = new ArrayList<>(roomTable);
        return rooms;
    }
    public static List<RoomModel> rtrvAvailableRooms(){
        List<RoomModel> rooms = new ArrayList<>(roomTable);
        rooms.removeIf(room -> room.isOccupied());
        return rooms;
    }
    public static List<RoomModel> rtrvOccupiedRooms(){
        List<RoomModel> rooms = new ArrayList<>(roomTable);
        rooms.removeIf(room -> !room.isOccupied());
        return rooms;
    }
    public static RoomModel rtrvByRsv(ReservationModel queryRsv){
        // TODO: Warning 
        // - this method won't work if the unique Rsv and Room aren't linked
        RoomModel room = 
                rtrvOccupiedRooms().stream().map(RoomModel::getReservation
                ).peek( // DEV Debug Method
                        rsv -> System.out.println("\tChecking: " + rsv + "\tAgainst: " + queryRsv)
                ).filter(
                        rsv -> rsv.equals(queryRsv)
                ).map(ReservationModel::getRoom
                ).findFirst().orElse(null);
        return room;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Rate Entity Create & Retrieval Methods">
    
    // add single rate w/ base rate
    public static void addRate(LocalDate rateDate, double baseRate){
        rateTable.add(new RateModel(rateDate, baseRate));
    }
    // add continuous range of rates w/ base rate
    public static void addRateRange(LocalDate startInclusive, LocalDate endExclusive, double baseRate){
//        Calendar c = Calendar.getInstance();
//        c.setTime(startInclusive);
        LocalDate dt = startInclusive.plusDays(0);
        while (dt.isBefore(endExclusive)){
            rateTable.add(new RateModel(dt, baseRate));
            dt = dt.plusDays(1);
        }
    }
    
    // retrieve rate by date
    public static RateModel rtrvByDate(Date date){
        RateModel rate = 
                rateTable.stream().filter(
                        rt -> rt.getRateSqlDate().equals(date)
                ).findFirst().orElse(null);
        return rate;
    }
    
    // retrieve listRates by dateRange
    public static List<RateModel> rtrvByDateRange(Date startInclusive, Date endInclusive){
        List<RateModel> rates = new ArrayList<>(rateTable);
        rates.removeIf(rt -> rt.getRateSqlDate().before(startInclusive));
        rates.removeIf(rt -> rt.getRateSqlDate().after(endInclusive));
        return rates;
    }
    
    // retrieve all rates
    public static List<RateModel> rtrvAllRates(){
        List<RateModel> rates = new ArrayList<>(rateTable);
        return rates;
    }
    
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Bllchrg Entity Create & Retrieval Methods">

    public static void addBllCharge(LocalDate DateCharged, double amount, String lineDesc) {
        BillChargeModel billing = new BillChargeModel(bllchrgTable.size() + 1, lineDesc, amount, DateCharged);
        bllchrgTable.add(billing);
    }

    public static void addBllCharge(LocalDate DateCharged, double amount) {
        BillChargeModel billing = new BillChargeModel(bllchrgTable.size() + 1, "", amount, DateCharged);
        bllchrgTable.add(billing);
    }

    public static void addBllCharge(LocalDate DateCharged) {
        BillChargeModel billing = new BillChargeModel(bllchrgTable.size() + 1, "", 0, DateCharged);
    }

    public static ArrayList<BillChargeModel> searchByReservation(ReservationModel rsv) {
        ArrayList<BillChargeModel> matchingRsv = new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing: bllchrgTable) {
            if (billing.getReservation().getReservationID() == rsv.getReservationID()) {
                matchingRsv.add(billing);
            }
        }
        return matchingRsv;
    }

    public static ArrayList<BillChargeModel> searchByBllChrgID(BillChargeModel bllchrgID) {
        ArrayList<BillChargeModel> matchingBllChrgID = new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing : bllchrgTable) {
            if (billing.getBillChargeID() == billing.getBillChargeID()) {
                matchingBllChrgID.add(billing);
            }
        }
        return matchingBllChrgID;
    }

    public static ArrayList<BillChargeModel>  searchByDateCharged(BillChargeModel DateCharged) {
        ArrayList<BillChargeModel> matchingDateCharged= new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing:bllchrgTable) {
            if (billing.getDateCharged().equals(billing.getDateCharged())) {
                matchingDateCharged.add(billing);
            }
        }
        return matchingDateCharged;
    }
    public static ArrayList<BillChargeModel>  searchByDatePaid(BillChargeModel DatePaid) {
        ArrayList<BillChargeModel> matchingDatePaid= new  ArrayList<BillChargeModel>();
        for (BillChargeModel billing:bllchrgTable) {
            if (billing.getDatePaid().equals(billing.getDatePaid())) {
                matchingDatePaid.add(billing);
            }
        }
        return matchingDatePaid;
    }

    public static void flagBillIsPaid(int primaryKey) {
        BillChargeModel billingPaid = bllchrgTable.get(primaryKey);
        billingPaid.setIsPaid(true);
        bllchrgTable.set(primaryKey, billingPaid);
        System.out.println("Bill is Paid.");
    }

    public static List<BillChargeModel> retrieveAllBllchrgs() {
         List<BillChargeModel> bllchrgID = new ArrayList<>(bllchrgTable);
         return bllchrgID;
    }
     //Ask For help got stuck
    public static List<BillChargeModel> retrieveAllPaidchrgs() {
     List<BillChargeModel> isPaidCharge = new ArrayList<>(bllchrgTable);
       isPaidCharge.removeIf(isPaidC-> isPaidC.isPaid());
       return isPaidCharge; 
    } 
    public static List<BillChargeModel> retrieveAllUnpaidchrgs() {
       List<BillChargeModel> isPaidCharge = new ArrayList<>(bllchrgTable);
       isPaidCharge.removeIf(isPaidC-> !isPaidC.isPaid());
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
    
}
