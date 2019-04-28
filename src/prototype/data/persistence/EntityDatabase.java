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
        public static void addReservation(LocalDate dateArrive, LocalDate dateDepart, LocalDate datePaid, ReservationType rsvType, boolean isNoShow, boolean isPaid, boolean isConcluded) {
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

        public static List<ReservationModel> retrieveAllReservations(){
            List<ReservationModel> rsvs = new ArrayList<>(TBL_RSV_ENTITY);
            return rsvs;
        }
        public static ReservationModel retrieveByID(int rsvID) {
            return TBL_RSV_ENTITY.stream().filter(rsv -> rsv.getReservationID() == rsvID).findFirst().orElse(null);
        }
        public static List<ReservationModel> retrieveByDateArrive(LocalDate ldArrv) {
            return TBL_RSV_ENTITY.stream().filter(
                    rsv -> rsv.getDateArrive() != null
            ).filter(
                    rsv -> rsv.getDateArrive().equals(ldArrv)
            ).collect(Collectors.toList());
        }
        public static List<ReservationModel> retrieveByDateDepart(LocalDate ldDprt) {
            return TBL_RSV_ENTITY.stream().filter(
                    rsv -> rsv.getDateDepart()!= null
            ).filter(
                    rsv -> rsv.getDateDepart().equals(ldDprt)
            ).collect(Collectors.toList());
        }
        public static List<ReservationModel> retrieveByDateInMiddle(LocalDate ldInclsv) {
            return TBL_RSV_ENTITY.stream().filter(
                    rsv -> (rsv.getDateArrive() != null && rsv.getDateDepart()!= null)
            ).filter(
                    rsv -> 
                            (ldInclsv.isBefore(rsv.getDateDepart()) && 
                                    ldInclsv.isAfter(rsv.getDateArrive()))
            ).collect(Collectors.toList());
        }
        // returns all reservations that are arriving, departing or in middle of stay on given date
        public static List<ReservationModel> retrieveByDate(LocalDate date) {
            List<ReservationModel> fltrRsvs = new ArrayList<>();
            fltrRsvs.addAll(retrieveByDateArrive(date));
            fltrRsvs.addAll(retrieveByDateDepart(date));
            fltrRsvs.addAll(retrieveByDateInMiddle(date));
            return fltrRsvs;
        }

        public static List<ReservationModel> retrieveByGuest(int guestID) {
            //return matchingRsvs
            return TBL_RSV_ENTITY.stream().filter(
                    rsv -> rsv.getGuest() != null
            ).filter(
                    rsv -> rsv.getGuest().getGuestID() == guestID
            ).collect(Collectors.toList());
        }
        public static ReservationModel retrieveByRoom(int roomID) {
            //return matchingRsv
            return TBL_RSV_ENTITY.stream().filter(
                    rsv -> rsv.getRoom()!= null
            ).filter(
                    rsv -> rsv.getRoom().getRoomID() == roomID
            ).findFirst().orElse(null);
        }
        public static List<ReservationModel> retrieveByType(ReservationType rsvType) {
            //return matchingRsvs
            return TBL_RSV_ENTITY.stream().filter(
                    rsv -> rsv.getReservationType() != null
            ).filter(
                    rsv -> rsv.getReservationType() == rsvType
            ).collect(Collectors.toList());
        }

        public static List<ReservationModel> retrieveAllPaid() {
            List<ReservationModel> matchingRsvs = new ArrayList<>(TBL_RSV_ENTITY);
            matchingRsvs.removeIf(rsv -> !rsv.isPaid());
            return matchingRsvs;
        }
        public static List<ReservationModel> retrieveAllConcluded() {
            List<ReservationModel> matchingRsvs = new ArrayList<>(TBL_RSV_ENTITY);
            matchingRsvs.removeIf(rsv -> !rsv.isConcluded());
            return matchingRsvs;
        }
        public static List<ReservationModel> retrieveAllNoShow() {
            List<ReservationModel> matchingRsvs = new ArrayList<>(TBL_RSV_ENTITY);
            matchingRsvs.removeIf(rsv -> !rsv.isConcluded());
            return matchingRsvs;
        }
    }
    //  nested class for room entity table
    public final static class RoomTable {
        // Database RoomTbl Retrieval Methods
        public static List<RoomModel> retrieveAllRooms() {
            List<RoomModel> rooms = new ArrayList<>(TBL_ROOM_ENTITY);
            return rooms;
        }
        public static RoomModel retrieveByID(int roomID) {
            return TBL_ROOM_ENTITY.stream().filter(rm -> rm.getRoomID() == roomID).findFirst().orElse(null);
        }
        public static List<RoomModel> retrieveOccupiedRooms() {
            List<RoomModel> rooms = new ArrayList<>(TBL_ROOM_ENTITY);
            rooms.removeIf((RoomModel room) -> !room.isOccupied());
            return rooms;
        }
        public static List<RoomModel> retrieveVacantRooms() {
            List<RoomModel> rooms = new ArrayList<>(TBL_ROOM_ENTITY);
            rooms.removeIf(rm -> rm.isOccupied());
            return rooms;
        }
        public static RoomModel retrieveByReservation(int rsvID) {
            return retrieveOccupiedRooms().stream().filter(
                    rm -> rm.getReservation().getReservationID() == rsvID
            ).findFirst().orElse(null);
        }
    }
    //  nested class for rate entity table
    public final static class RateTable {
        // add single rate w/ base rate
        public static void addRate(LocalDate rateDate, double baseRate) {
            TBL_RATE_ENTITY.add(new RateModel(rateDate, baseRate));
        }
        // add continuous range of rates w/ base rate
        public static void addRateRange(LocalDate startInclusive, LocalDate endExclusive, double baseRate) {
            LocalDate dt = startInclusive.plusDays(0);
            while (dt.isBefore(endExclusive)) {
                TBL_RATE_ENTITY.add(new RateModel(dt, baseRate));
                dt = dt.plusDays(1);
            }
        }

        // retrieve rates
        public static List<RateModel> retriveAllRates() {
            List<RateModel> rates = new ArrayList<>(TBL_RATE_ENTITY);
            return rates;
        }
        public static RateModel retrieveRateByDate(LocalDate date) {
            return TBL_RATE_ENTITY.stream().filter(rt -> rt.getRateDate().equals(date)).findFirst().orElse(null);
        }
        public static List<RateModel> retrieveByDateRange(LocalDate startInclusive, LocalDate endInclusive) {
            List<RateModel> rates = new ArrayList<>(TBL_RATE_ENTITY);
            rates.removeIf(rt -> rt.getRateDate().isBefore(startInclusive));
            rates.removeIf(rt -> rt.getRateDate().isAfter(endInclusive));
            return rates;
        }
    }
    //  nested class for guest entity table
    public final static class GuestTable {
        public static void addGuest(String Name, String Email, String CC_info) {
            GuestModel new_guest = new GuestModel(TBL_GUEST_ENTITY.size() + 1, Name, Email, CC_info);
            TBL_GUEST_ENTITY.add(new_guest);
        }
        public static void addGuest(String Name, String Email) {
            GuestModel new_guest = new GuestModel(TBL_GUEST_ENTITY.size() + 1, Name, Email);
            TBL_GUEST_ENTITY.add(new_guest);
        }
        public static int addGuestReturnID(String Name, String Email) {
            GuestModel new_guest = new GuestModel(TBL_GUEST_ENTITY.size() + 1, Name, Email);
            TBL_GUEST_ENTITY.add(new_guest);
            return new_guest.getGuestID();
        }

        // retrieval
        public static List<GuestModel> retrieveAllGuests() {
           return new ArrayList<>(TBL_GUEST_ENTITY);
        }
        public static GuestModel retrieveByID(int guestID) {
            return TBL_GUEST_ENTITY.stream().filter(
                    guest -> guest.getGuestID() == guestID
            ).findFirst().orElse(null);
        }
        public static GuestModel retrieveByName(String guestName) {
            return TBL_GUEST_ENTITY.stream().filter(
                    guest -> guest.getName().equals(guestName)
            ).findFirst().orElse(null);
        }
        public static GuestModel retrieveByEmail(String guestName) {
            return TBL_GUEST_ENTITY.stream().filter(
                    guest -> guest.getName().equals(guestName)
            ).findFirst().orElse(null);
        }
        public static GuestModel retrieveByReservation(int rsvID) {
            //  There must be a guest attached to a reservation
            ReservationModel rsv = ReservationTable.retrieveByID(rsvID);
            return (rsv != null) ? rsv.getGuest() : null; //return null if no rsv was found
        }
    }
    //  nested class for bllchrg entity table
    public final static class BillChargeTable {
        public static void addBillCharge(LocalDate DateCharged, double amount, String lineDesc) {
            BillChargeModel bllchrg = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, lineDesc, amount, DateCharged);
            TBL_BLLCHRG_ENTITY.add(bllchrg);
        }
        public static void addBillCharge(LocalDate DateCharged, double amount) {
            BillChargeModel bllchrg = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, "", amount, DateCharged);
            TBL_BLLCHRG_ENTITY.add(bllchrg);
        }
        public static void addBillCharge(LocalDate DateCharged) {
            BillChargeModel bllchrg = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, "", 0, DateCharged);
            TBL_BLLCHRG_ENTITY.add(bllchrg);
        }
        public static int addBillChargeReturnID(LocalDate DateCharged) {
            BillChargeModel bllchrg = new BillChargeModel(TBL_BLLCHRG_ENTITY.size() + 1, "", 0, DateCharged);
            TBL_BLLCHRG_ENTITY.add(bllchrg);
            return bllchrg.getBillChargeID();
        }

        public static List<BillChargeModel> retrieveByReservation(int rsvID) {
            List<BillChargeModel> matchingRsvID = new  ArrayList<>();
            for (BillChargeModel bllchrg: TBL_BLLCHRG_ENTITY) {
                ReservationModel rsv = bllchrg.getReservation();
                if (rsv != null && rsv.getReservationID() == rsvID) {
                    matchingRsvID.add(bllchrg);
                }
            }
            return matchingRsvID;
        }
        public static BillChargeModel retrieveByID(int bllchrgID) {
            BillChargeModel matchingBllChrgID = null;
            for (BillChargeModel bllchrg : TBL_BLLCHRG_ENTITY) {
                if (bllchrg.getBillChargeID() == bllchrgID) {
                    matchingBllChrgID = bllchrg;
                }
            }
            return matchingBllChrgID;
        }

        public static List<BillChargeModel>  retrieveByDateCharged(LocalDate dateChrged) {
            List<BillChargeModel> matchingDateCharged = new  ArrayList<>();
            for (BillChargeModel bllchrg:TBL_BLLCHRG_ENTITY) {
                LocalDate ld = bllchrg.getDateCharged();
                if (ld != null && ld.equals(dateChrged)) {
                    matchingDateCharged.add(bllchrg);
                }
            }
            return matchingDateCharged;
        }
        public static List<BillChargeModel>  retrieveByDateRangeCharged(LocalDate ldStartIn, LocalDate ldEndIn) {
            List<BillChargeModel> matchingDateCharged = new  ArrayList<>();
            for (BillChargeModel bllchrg:TBL_BLLCHRG_ENTITY) {
                LocalDate ld = bllchrg.getDateCharged();
                if (ld != null 
                        && (ld.equals(ldStartIn) || ld.isAfter(ldStartIn)) 
                        && (ld.equals(ldEndIn) || ld.isBefore(ldEndIn))) {
                    matchingDateCharged.add(bllchrg);
                }
            }
            return matchingDateCharged;
        }

        public static List<BillChargeModel>  retrieveByDatePaid(LocalDate dPaid) {
            List<BillChargeModel> matchingDatePaid= new  ArrayList<>();
            for (BillChargeModel bllchrg:TBL_BLLCHRG_ENTITY) {
                LocalDate ld = bllchrg.getDatePaid();
                if (ld != null && ld.equals(dPaid)) {
                    matchingDatePaid.add(bllchrg);
                }
            }
            return matchingDatePaid;
        }
        public static List<BillChargeModel>  retrieveByDateRangePaid(LocalDate ldStartIn, LocalDate ldEndIn) {
            List<BillChargeModel> matchingDatePaid = new  ArrayList<>();
            for (BillChargeModel bllchrg:TBL_BLLCHRG_ENTITY) {
                LocalDate ld = bllchrg.getDatePaid();
                if (ld != null 
                        && (ld.equals(ldStartIn) || ld.isAfter(ldStartIn)) 
                        && (ld.equals(ldEndIn) || ld.isBefore(ldEndIn))) {
                    matchingDatePaid.add(bllchrg);
                }
            }
            return matchingDatePaid;
        }

        public static List<BillChargeModel> retrieveAllBillCharges() {
             List<BillChargeModel> bllchrgID = new ArrayList<>(TBL_BLLCHRG_ENTITY);
             return bllchrgID;
        }
        public static List<BillChargeModel> retrieveAllPaidCharges() {
         List<BillChargeModel> paid_bllchrgs = new ArrayList<>(TBL_BLLCHRG_ENTITY);
           paid_bllchrgs.removeIf(bllchrg -> !bllchrg.isPaid());
           return paid_bllchrgs; 
        } 
        public static List<BillChargeModel> retrieveAllUnpaidCharges() {
           List<BillChargeModel> unpaid_bllchrgs = new ArrayList<>(TBL_BLLCHRG_ENTITY);
           unpaid_bllchrgs.removeIf(bllchrg-> bllchrg.isPaid());
           return unpaid_bllchrgs; 
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
