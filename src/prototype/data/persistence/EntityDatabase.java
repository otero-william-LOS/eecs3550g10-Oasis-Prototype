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
import java.util.stream.Collectors;

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
    
    
    //  nested class for rsv entity table
    public final static class ReservationTable {
        
        // creates a new reservation for the first ReservationModel constructor
        public static void addReservation(LocalDate dateArrive, LocalDate dateDepart, LocalDate datePaid, ReservationType rsvType,
                RoomModel room, GuestModel guest, List<BillChargeModel> listBllchrg,
                boolean isNoShow, boolean isPaid, boolean isConcluded) {
            ReservationModel reservation = new ReservationModel(TBL_RSV_ENTITY.size() + 1, dateArrive, dateDepart, datePaid, rsvType, room, guest, listBllchrg,
                    isNoShow, isPaid, isConcluded);
            TBL_RSV_ENTITY.add(reservation);
        }
        public static void addReservation(LocalDate dateArrive, LocalDate dateDepart, LocalDate datePaid, ReservationType rsvType,
                boolean isNoShow, boolean isPaid, boolean isConcluded) {
            ReservationModel reservation = new ReservationModel(TBL_RSV_ENTITY.size() + 1, dateArrive, dateDepart, datePaid, rsvType,
                    isNoShow, isPaid, isConcluded);
            TBL_RSV_ENTITY.add(reservation);
        }
        public static void addReservation(LocalDate dateArrive, LocalDate dateDepart, ReservationType rsvType) {
            ReservationModel reservation = new ReservationModel(TBL_RSV_ENTITY.size() + 1, dateArrive, dateDepart, rsvType);
            TBL_RSV_ENTITY.add(reservation);
        }
        public static void addReservation(LocalDate dateArrive, LocalDate dateDepart) {
            ReservationModel reservation = new ReservationModel(TBL_RSV_ENTITY.size() + 1, dateArrive, dateDepart);
            TBL_RSV_ENTITY.add(reservation);
        }
        
        public static int addReservationReturnID(LocalDate dateArrive, LocalDate dateDepart) {
            int id = TBL_RSV_ENTITY.size() + 1;
            ReservationModel reservation = new ReservationModel(id, dateArrive, dateDepart);
            TBL_RSV_ENTITY.add(reservation);
            return id;
        }

        public static List<ReservationModel> searchRsvByGuest(int guestID) {
            List<ReservationModel> matchingRsvs = new ArrayList<>();
            for (ReservationModel rsv : TBL_RSV_ENTITY) {
                if (rsv.getGuest().getGuestID() == guestID) {
                    matchingRsvs.add(rsv);
                }
            }      
            return matchingRsvs;
        }
        public static ReservationModel searchRsvByRoom(int roomID) {
            ReservationModel matchingRsv = null;
            for (ReservationModel rsv : TBL_RSV_ENTITY) {
                if (rsv.getRoom().getRoomID() == roomID) {
                    matchingRsv = rsv;
                }
            }
            return matchingRsv;
        }
        public static List<ReservationModel> searchReservationByType(ReservationType rsvType) {
            List<ReservationModel> matchingRsvs = new ArrayList<>();
            for (ReservationModel rsv : TBL_RSV_ENTITY) {
                if (rsv.getReservationType() == rsvType) {
                    matchingRsvs.add(rsv);
                }
            }
            return matchingRsvs;
        }


        public static List<ReservationModel> retrieveAllReservations(){
            List<ReservationModel> rsvs = new ArrayList<>(TBL_RSV_ENTITY);
            return rsvs;
        }

        public static List<ReservationModel> retrieveReservationByDateArrive(LocalDate ldArrv) {
            return TBL_RSV_ENTITY.stream().filter(
                    rsv -> rsv.getDateArrive().equals(ldArrv)
            ).collect(Collectors.toList());
        }
        public static List<ReservationModel> retrieveReservationByDateDepart(LocalDate ldDprt) {
            return TBL_RSV_ENTITY.stream().filter(
                    rsv -> rsv.getDateDepart().equals(ldDprt)
            ).collect(Collectors.toList());
        }
        public static List<ReservationModel> retrieveReservationByDateInMiddle(LocalDate ldInclsv) {
            return TBL_RSV_ENTITY.stream().filter(
                    rsv -> 
                            (ldInclsv.isBefore(rsv.getDateDepart()) && 
                                    ldInclsv.isAfter(rsv.getDateArrive()))
            ).collect(Collectors.toList());
        }
        // returns all reservations that are arriving, departing or in middle of stay on given date
        public static List<ReservationModel> retrieveReservationByDate(LocalDate date) {
            List<ReservationModel> fltrRsvs = new ArrayList<>();
            fltrRsvs.addAll(retrieveReservationByDateArrive(date));
            fltrRsvs.addAll(retrieveReservationByDateDepart(date));
            fltrRsvs.addAll(retrieveReservationByDateInMiddle(date));
            return fltrRsvs;
        }

        public static List<ReservationModel> getAllPaid() {
            List<ReservationModel> matchingRsvs = new ArrayList<>(TBL_RSV_ENTITY);
            matchingRsvs.removeIf(rsv -> !rsv.isPaid());
            return matchingRsvs;
        }
        public static List<ReservationModel> getAllConcluded() {
            List<ReservationModel> matchingRsvs = new ArrayList<>(TBL_RSV_ENTITY);
            matchingRsvs.removeIf(rsv -> !rsv.isConcluded());
            return matchingRsvs;
        }
        public static List<ReservationModel> getAllNoShow() {
            List<ReservationModel> matchingRsvs = new ArrayList<>(TBL_RSV_ENTITY);
            matchingRsvs.removeIf(rsv -> !rsv.isConcluded());
            return matchingRsvs;
        }

        public static ReservationModel retrieveReservation(int rsvID) {
            return TBL_RSV_ENTITY.stream().filter(rsv -> rsv.getReservationID() == rsvID).findFirst().orElse(null);
        }
    
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
    
    public static void createGuest(String Name, String Email, String CC_info) {
        GuestModel createGuest = new GuestModel(TBL_GUEST_ENTITY.size() + 1, Name, Email, CC_info);
        TBL_GUEST_ENTITY.add(createGuest);
    }
        
    public static void modifyGuest(String Name, String Email, String ccInfo) {
        GuestModel modifyGuest = new GuestModel(TBL_GUEST_ENTITY.size(), Name, Email, ccInfo);
        TBL_GUEST_ENTITY.add(modifyGuest);
        TBL_GUEST_ENTITY.clear();
    }
    
    public static void modifyGuest(String Name, String Email) {
        GuestModel modifyGuest = new GuestModel(TBL_GUEST_ENTITY.size(), Name, Email);
        TBL_GUEST_ENTITY.add(modifyGuest);
        TBL_GUEST_ENTITY.clear();
    }
    
    public static ArrayList<GuestModel> searchByName(String guestName) {
        ArrayList<GuestModel> matchName = new  ArrayList<GuestModel>();
        
        for (GuestModel Name: TBL_GUEST_ENTITY) {
            if (Name.getName().equals(guestName)) {
                matchName.add(Name);
            }
        }
        return matchName;
    }
    
    public static GuestModel searchByGuest_ID(int Guest_ID) {
        List<GuestModel> matchGuest_ID = new  ArrayList<GuestModel>();
        
        for (GuestModel GuestID: TBL_GUEST_ENTITY) {
            if (GuestID.getGuestID() != GuestID.getGuestID()) {
                matchGuest_ID.add(GuestID);
            }
        }
        return null;
    }
    
    public static List<GuestModel> requestAllGuests() {
       return new ArrayList<>(TBL_GUEST_ENTITY);
    }
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
