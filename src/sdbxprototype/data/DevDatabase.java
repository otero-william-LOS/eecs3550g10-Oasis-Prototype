/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdbxprototype.data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import sdbxprototype.data.models.RsvModel;
import sdbxprototype.data.models.RoomModel;
import sdbxprototype.data.models.RateModel;
import sdbxprototype.data.models.GuestModel;
import sdbxprototype.data.models.BllchrgModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import sdbxprototype.data.models.DataModel;

/**
 *
 * @author wotero
 */
public class DevDatabase {
    
    private static final List<RsvModel> rsvTable = new ArrayList<>();
    private static final List<RoomModel> roomTable = new ArrayList<>();
    private static final List<RateModel> rateTable = new ArrayList<>();
    private static final List<BllchrgModel> bllchrgTable = new ArrayList<>();
    private static final List<GuestModel> guestTable = new ArrayList<>();
    
    /**
     *  DevDatabase Gen Methods for UnitTesting
     */
    
    //<editor-fold defaultstate="collapsed" desc="Rsv Dummy DevGeneration Methods">
    
    // SDBX: Gen Simple RsvModel with RsvID only
    public static void genRsvTblBase(int numRsv){
        rsvTable.clear();
        for(int i = 0; i < numRsv; i++) 
            rsvTable.add(new RsvModel(i + 1));
        
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
                if (!roomTable.get(x).getIsOccupied())
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
            Date dt = DataModel.utilDateFromLocalDate(startYear.plusDays(i));
            addRate(dt, vRate);
        }
        
        System.out.println("may to august");
        
        //  from may to august, tourism is high, so rate should be high
        rate = 375.0;    // arbitrary testing
        numDays = ChronoUnit.DAYS.between(startYear.withMonth(5), startYear.withMonth(9));
        for(long i = 0; i < numDays; i++){
            int rndmIndex = rndm.nextInt(vSize);
            double vRate = rate + variance[rndmIndex];
            Date dt = DataModel.utilDateFromLocalDate(startYear.withMonth(5).plusDays(i));
            addRate(dt, vRate);
        }
        
        System.out.println("sept to oct");
        
        //  from sept to oct, tourism is low, so rate should be low
        rate = 175.0;    // arbitrary testing
        numDays = ChronoUnit.DAYS.between(startYear.withMonth(9), startYear.withMonth(11));
        for(long i = 0; i < numDays; i++){
            int rndmIndex = rndm.nextInt(vSize);
            double vRate = rate + variance[rndmIndex];
            Date dt = DataModel.utilDateFromLocalDate(startYear.withMonth(9).plusDays(i));
            addRate(dt, vRate);
        }
        
        System.out.println("nov to dec");
        
        //  from nov to dec, tourism is high, so rate should be high
        rate = 425.0;    // arbitrary testing
        numDays = ChronoUnit.DAYS.between(startYear.withMonth(11), startYear.plusYears(1));
        for(long i = 0; i < numDays; i++){
            int rndmIndex = rndm.nextInt(vSize);
            double vRate = rate + variance[rndmIndex];
            Date dt = DataModel.utilDateFromLocalDate(startYear.withMonth(11).plusDays(i));
            addRate(dt, vRate);
        }
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Bllchrg Dummy DevGeneration Methods">
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Guest Dummy DevGeneration Methods">
    
//</editor-fold>
    
    /**
     *  DevDatabase Create & Retrieval
     */
    
    //<editor-fold defaultstate="collapsed" desc="Rsv Entity Create & Retrieval Methods">
    
    // SDBX: Retrieve Simple RsvModel Table DEV Method
    public static List<RsvModel> retrieveAllRsvs(){
        List<RsvModel> rsvs = new ArrayList<>(rsvTable);
        return rsvs;
    }
    
    // stuff from Austin =======================================================
    //dummy database table for reservation
    private static ArrayList<RsvModel> reservationTable = new ArrayList();

    // creates a new reservation for the first RsvModel constructor
    public static void addReservation(Date dateArrive, Date dateDepart, Date datePaid, RsvType rsvType,
            RoomModel room, GuestModel guest, ArrayList<BllchrgModel> listBllchrg,
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        RsvModel reservation = new RsvModel(reservationTable.size() + 1, dateArrive, dateDepart, datePaid, rsvType, room, guest, listBllchrg,
                isNoShow, isPaid, isConcluded);
        reservationTable.add(reservation);
    }

    // creates a new reservation for the second RsvModel constructor
    public static void addReservation(Date dateArrive, Date dateDepart, Date datePaid, RsvType rsvType,
            boolean isNoShow, boolean isPaid, boolean isConcluded) {
        RsvModel reservation = new RsvModel(reservationTable.size() + 1, dateArrive, dateDepart, datePaid, rsvType,
                isNoShow, isPaid, isConcluded);
        reservationTable.add(reservation);
    }

    // creates a new reservation for the third RsvModel constructor
    public static void addReservation(Date dateArrive, Date dateDepart, RsvType rsvType) {
        RsvModel reservation = new RsvModel(reservationTable.size() + 1, dateArrive, dateDepart, rsvType);
        reservationTable.add(reservation);
    }

    // creates a new reservation for the fourth RsvModel constructor
    public static void addReservation(Date dateArrive, Date dateDepart) {
        RsvModel reservation = new RsvModel(reservationTable.size() + 1, dateArrive, dateDepart);
        reservationTable.add(reservation);
    }
    
    // creates a new reservation for the fourth RsvModel constructor for use 
    // in intitial creation in the scheduler
    public static int addReservationSched(Date dateArrive, Date dateDepart) {
        int id = reservationTable.size() + 1;
        RsvModel reservation = new RsvModel(id, dateArrive, dateDepart);
        reservationTable.add(reservation);
        return id;
    }

    // changes the DateArrive property of the Reservation
    public static void changeRsvArrival(int primaryKey, Date arrival) {
        RsvModel reservation = reservationTable.get(primaryKey);
        reservation.setDateArrive(arrival);
        reservationTable.set(primaryKey, reservation);
    }

    // changes the DateDepart property of the Reservation
    public static void changeRsvDeparture(int primaryKey, Date departure) {
        RsvModel reservation = reservationTable.get(primaryKey);
        reservation.setDateDepart(departure);
        reservationTable.set(primaryKey, reservation);
    }

    // changes the RsvType property of the Reservation
    public static void changeRsvType(int primaryKey, RsvType rsvType) {
        RsvModel reservation = reservationTable.get(primaryKey);
        reservation.setRsvType(rsvType);
        reservationTable.set(primaryKey, reservation);
    }

    public static ArrayList<RsvModel> searchRsvByGuest(GuestModel guest) {
        ArrayList<RsvModel> matchingRsvs = new ArrayList<>();
        for (RsvModel rsv : reservationTable) {
            if (rsv.getGuest().getGuestID() == guest.getGuestID()) {
                matchingRsvs.add(rsv);
            }
        }      
        return matchingRsvs;
    }
    
    public static ArrayList<RsvModel> searchRsvByRoom(RoomModel room) {
        ArrayList<RsvModel> matchingRsvs = new ArrayList<>();
        for (RsvModel rsv : reservationTable) {
            if (rsv.getRoom().getRoomID() == room.getRoomID()) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    public static ArrayList<RsvModel> searchRsvByDtArrive(Date dateArrive) {
        ArrayList<RsvModel> matchingRsvs = new ArrayList<>();
        for (RsvModel rsv : reservationTable) {
            if (rsv.getDateArrive() == dateArrive) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    public static ArrayList<RsvModel> searchRsvByDtDepart(Date dateDepart) {
        ArrayList<RsvModel> matchingRsvs = new ArrayList<>();
        for (RsvModel rsv : reservationTable) {
            if (rsv.getDateArrive() == dateDepart) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    // returns all reservations that are arriving, departing or in middle of stay on given date
    public static ArrayList<RsvModel> searchRsvByDtArriveBtwn(Date date) {
        ArrayList<RsvModel> matchingRsvs = new ArrayList<>();
        for (RsvModel rsv : reservationTable) {
            if((date.before(rsv.getDateDepart()) & date.after(rsv.getDateArrive()) || date.equals(rsv.getDateArrive()) || date.equals(rsv.getDateDepart())) == true){
            //if (rsv.getDateArrive() == dateArrive) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    //returns all reservations of a given type
    public static ArrayList<RsvModel> searchRsvByType(RsvType rsvType) {
        ArrayList<RsvModel> matchingRsvs = new ArrayList<>();
        for (RsvModel rsv : reservationTable) {
            if (rsv.getRsvType() == rsvType) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    // returns all paid reservations
    public static ArrayList<RsvModel> getAllPaid() {
        ArrayList<RsvModel> matchingRsvs = new ArrayList<>();
        for (RsvModel rsv : reservationTable) {
            if (rsv.getIsPaid() == true) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    // returns all conclded reservations
    public static ArrayList<RsvModel> getAllConcluded() {
        ArrayList<RsvModel> matchingRsvs = new ArrayList<>();
        for (RsvModel rsv : reservationTable) {
            if (rsv.getIsConcluded() == true) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }
    
    // returns all no show reservations
    public static ArrayList<RsvModel> getAllnoShow() {
        ArrayList<RsvModel> matchingRsvs = new ArrayList<>();
        for (RsvModel rsv : reservationTable) {
            if (rsv.getIsNoShow()== true) {
                matchingRsvs.add(rsv);
            }
        }
        return matchingRsvs;
    }

    public static void assignRsvRoom(int primaryKey, RoomModel room) {
        RsvModel reservation = reservationTable.get(primaryKey);
        reservation.setRoom(room);
        reservationTable.set(primaryKey, reservation);
    }

    public static void deassignRsvRoom(int primaryKey) {
        RsvModel reservation = reservationTable.get(primaryKey);
        RoomModel room = new RoomModel();
        reservation.setRoom(room);
        reservationTable.set(primaryKey, reservation);
    }

    public static void flagRsvNoShow(int primaryKey) {
        RsvModel reservation = reservationTable.get(primaryKey);
        reservation.setIsNoShow(true);
        reservationTable.set(primaryKey, reservation);
    }

    public static void flagRsvIsPaid(int primaryKey) {
        RsvModel reservation = reservationTable.get(primaryKey);
        reservation.setIsPaid(true);
        reservationTable.set(primaryKey, reservation);
    }

    public static void flagRsvIsConcluded(int primaryKey) {
        RsvModel reservation = reservationTable.get(primaryKey);
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
        rooms.removeIf(room -> room.getIsOccupied());
        return rooms;
    }
    public static List<RoomModel> rtrvOccupiedRooms(){
        List<RoomModel> rooms = new ArrayList<>(roomTable);
        rooms.removeIf(room -> !room.getIsOccupied());
        return rooms;
    }
    public static RoomModel rtrvByRsv(RsvModel queryRsv){
        // TODO: Warning 
        // - this method won't work if the unique Rsv and Room aren't linked
        RoomModel room = 
                rtrvOccupiedRooms().stream().map(
                        RoomModel::getRsv
                ).peek( // DEV Debug Method
                        rsv -> System.out.println("\tChecking: " + rsv + "\tAgainst: " + queryRsv)
                ).filter(
                        rsv -> rsv.equals(queryRsv)
                ).map(
                        RsvModel::getRoom
                ).findFirst().orElse(null);
        return room;
    }
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Rate Entity Create & Retrieval Methods">
    
    // add single rate w/ base rate
    public static void addRate(Date rateDate, Double baseRate){
        rateTable.add(new RateModel(rateDate, baseRate));
//        rateTable.sort(
//            (r1,r2) -> {
//                int result = 0;
//                if (r1.equals(r2)){
//                    System.out.println("Should have checked for existing date first");
//                }
//                else{
//                    result = r1.getRateDate().compareTo(r2.getRateDate());
//                }
//                return result;
//            }
//        );
    }
    // SDBX DEV: add single rate w/o base rate
//    public static void addEmptyRate(Date rateDate){
//        addRate(rateDate, 0.0);
//    }
    // add continuous range of rates w/ base rate
    public static void addRateRange(Date startInclusive, Date endExclusive, Double baseRate){
        Calendar c = Calendar.getInstance();
        c.setTime(startInclusive);
        Date dt = c.getTime();
        while (dt.before(endExclusive)){
            rateTable.add(new RateModel(dt, baseRate));
            c.add(Calendar.DATE, 1);
            dt = c.getTime();
        }
//        rateTable.sort(
//            (r1,r2) -> {
//                int result = 0;
//                if (r1.equals(r2)){
//                    System.out.println("Should have checked for existing date first");
//                }
//                else{
//                    result = r1.getRateDate().compareTo(r2.getRateDate());
//                }
//                return result;
//            }
//        );
    }
    // SDBX DEV: add continuous range of rates w/o base rate
//    public static void addEmptyRateRange(Date startInclusive, Date endExclusive){
//        addRateRange(startInclusive, endExclusive, 0.0);
//    }
    
    // retrieve rate by date
    public static RateModel rtrvByDate(Date date){
        RateModel rate = 
                rateTable.stream().filter(
                        rt -> rt.getRateDate().equals(date)
                ).findFirst().orElse(null);
        return rate;
    }
    
    // retrieve listRates by dateRange
    public static List<RateModel> rtrvByDateRange(Date startInclusive, Date endInclusive){
        List<RateModel> rates = new ArrayList<>(rateTable);
        rates.removeIf(rt -> rt.getRateDate().before(startInclusive));
        rates.removeIf(rt -> rt.getRateDate().after(endInclusive));
        return rates;
    }
    
    // retrieve all rates
    public static List<RateModel> rtrvAllRates(){
        List<RateModel> rates = new ArrayList<>(rateTable);
        return rates;
    }
    
//</editor-fold>
    
  

//    public static void addBllCharge(Date DateCharged, double amount, String lineDesc) {
//        BllchrgModel billing = new BllchrgModel(bllchrgTable.size() + 1, DateCharged, amount, lineDesc);
//        bllchrgTable.add(billing);
//       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    static void addBllCharge(Date DateCharged, double amount) {
//        BllchrgModel billing = new BllchrgModel(bllchrgTable.size() + 1, DateCharged, amount);
//        bllchrgTable.add(billing);
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    static void addBllCharge(Date DateCharged) {
//        BllchrgModel billing = new BllchrgModel(bllchrgTable.size() + 1, DateCharged);
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public static ArrayList<BllchrgModel> searchByReservation(RsvModel rsv) {
        ArrayList<BllchrgModel> matchingRsv = new  ArrayList<BllchrgModel>();
        for (BllchrgModel billing: bllchrgTable) {
            if (billing.getReservation().getRsvID() == rsv.getRsvID()) {
                matchingRsv.add(billing);
            }
        }
        return matchingRsv;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static ArrayList<BllchrgModel> searchByBllChrgID(BllchrgModel bllchrgID) {
        ArrayList<BllchrgModel> matchingBllChrgID = new  ArrayList<BllchrgModel>();
        for (BllchrgModel billing : bllchrgTable) {
            if (billing.getBllchrgID() == billing.getBllchrgID()) {
                matchingBllChrgID.add(billing);
            }
        }
        return matchingBllChrgID;
    }

    public static ArrayList<BllchrgModel>  searchByDateCharged(BllchrgModel DateCharged) {
        ArrayList<BllchrgModel> matchingDateCharged= new  ArrayList<BllchrgModel>();
        for (BllchrgModel billing:bllchrgTable) {
            if (billing.getDateCharged() == billing.getDateCharged()) {
                matchingDateCharged.add(billing);
            }
        }
        return matchingDateCharged;
    }
    public static ArrayList<BllchrgModel>  searchByDatePaid(BllchrgModel DatePaid) {
        ArrayList<BllchrgModel> matchingDatePaid= new  ArrayList<BllchrgModel>();
        for (BllchrgModel billing:bllchrgTable) {
            if (billing.getDatePaid() == billing.getDatePaid()) {
                matchingDatePaid.add(billing);
            }
        }
        return matchingDatePaid;
    }

    static void flagBillIsPaid(int primaryKey) {
        BllchrgModel billingPaid = bllchrgTable.get(primaryKey);
        billingPaid.setIsPaid(true);
        bllchrgTable.set(primaryKey, billingPaid);
        System.out.println("Bill is Paid.");
    }

     public List<BllchrgModel> retrieveAllBllchrgs() {
         List<BllchrgModel> bllchrgID = new ArrayList<>(bllchrgTable);
         return bllchrgID;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     //Ask For help got stuck
    List<BllchrgModel> retrieveAllPaidchrgs() {
     List<BllchrgModel> isPaidCharge = new ArrayList<>(bllchrgTable);
       isPaidCharge.removeIf(isPaidC-> isPaidC.isIsPaid());
       return isPaidCharge; 
    } 
    List<BllchrgModel> retrieveAllUnpaidchrgs() {
        List<BllchrgModel> isPaidCharge = new ArrayList<>(bllchrgTable);
       isPaidCharge.removeIf(isPaidC-> !isPaidC.isIsPaid());
       return isPaidCharge; 
    }

//<editor-fold defaultstate="collapsed" desc="Bllchrg Entity Create & Retrieval Methods">
    
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Guest Entity Create & Retrieval Methods">
    
//</editor-fold>
    
}
