package prototype.logic.schedulers;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import prototype.data.drivers.ReservationDriver;
import prototype.data.models.ReservationModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import prototype.data.drivers.GuestDriver;
import prototype.data.drivers.RateDriver;
import prototype.data.drivers.RoomDriver;
import prototype.data.models.GuestModel;
import prototype.data.models.ReservationType;
import prototype.data.models.RoomModel;
import prototype.data.persistence.EntityDatabase;

public class ReservationScheduling implements Scheduler {

    //<editor-fold defaultstate="collapsed" desc="Create New Reservation">
    public static int createReservation(LocalDate dateArrive, LocalDate dateDepart) {

        // received dates. create reservation using basic contstructor
        // there will need to be logic to determine if the hotel will be full
        // over any of the requested reservation days
        // if the reservation cannot be made will return a value to main 
        // stating so, for now assume unlimited reservations can be made
//        int rsvID = ReservationDriver.createRSVCSched(dateArrive, dateDepart);
        // I have generated the new Reservation and the ID
        // send all nescessary info to payment procssing
        // I figure payment processing should assing the datePaid and 
        // listbillcharges to the reservation
        // if you can think of any reason the reservation would not be able
        // to be created please provide a method to return some value here
        // so i can deal with it appropriatley
        // imaginary payment processing call below
        // paymentProcessing.receiveRsvInfo(rsvID, dateArrive, dateDepart)
        // this should return a message saying wether or not the reservation
        // was created and info about its successful creation
        int returnID = 0;
        int occupancyCount = 0;

        for (int i = 0; dateArrive.plusDays(i).isBefore(dateDepart.plusDays(1)); i++) {
            List<ReservationModel> listOccupancy
                    = ReservationDriver.searchByDate(dateArrive.plusDays(i));

            if (listOccupancy.size() - ReservationDriver.searchByDate(dateDepart).size() < 45) {
                occupancyCount++;
            }
        }
        long diff = DAYS.between(dateArrive, dateDepart) + 1;
        if (diff == occupancyCount) {

            int testRsvID = ReservationDriver.createReservationReturnID(dateArrive,
                    dateDepart);
            ReservationDriver.modifyReservationType(
                    testRsvID, getRsvType(dateArrive, dateDepart));
            returnID = testRsvID;

            PaymentProcessing.
                    generateAccmBill(ReservationDriver.searchByID(testRsvID));

        }

        return returnID;

    }

    public static int createGuest(String name, String email, String ccInfo) {
        int newGuestID = GuestDriver.createGuestReturnID(name, email);
        GuestDriver.modifyGuestCreditCardInfo(newGuestID, ccInfo);
        GuestModel tempGuest = GuestDriver.searchByID(newGuestID);

        return newGuestID;
    }

    public static boolean attachReservationToGuest(int rsvID, int guestID) {
        ReservationModel rsv = ReservationDriver.searchByID(rsvID);
        GuestModel guest = GuestDriver.searchByID(guestID);
        boolean isModified = false;
        if ((rsv.getReservationType() != ReservationType.SIXTYADV)
                && (guest.getCCInfo().equals(""))) {
            System.out.println("Error: Missing CCInfo!");
        } else {
            ReservationDriver.attachGuest(rsvID, guestID);
            isModified = true;
        }
        return isModified;
    }

    public static void proccessNewReservation(int rsvID) {
        ReservationModel rsv = ReservationDriver.searchByID(rsvID);

        if (rsv.getReservationType() == ReservationType.PREPAID) {
            PaymentProcessing.processAccmBill(rsv);
        }
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Modify Reservations">
    public static void modifyReservationArriveDate(int rsvID, LocalDate arriveDate) {
        ReservationDriver.modifyDateArrive(rsvID, arriveDate);
    }

    public static void modifyReservationDepartDate(int rsvID, LocalDate departDate) {
        ReservationDriver.modifyDateDepart(rsvID, departDate);
    }

    public static void modifyReservationDatePaid(int rsvID, LocalDate datePaid) {
        ReservationDriver.modifyDatePaid(rsvID, datePaid);
    }

    public static boolean modifyReservationType(int rsvID) {
        boolean changeMade = false;
        LocalDate arriveDate
                = ReservationDriver.searchByID(rsvID).getDateArrive();
        LocalDate departDate
                = ReservationDriver.searchByID(rsvID).getDateDepart();

        ReservationDriver.modifyReservationType(rsvID, getRsvType(arriveDate, departDate));

        return changeMade;
    }

    public static boolean modifyReservationRoomID(int rsvID, short roomID) {
        List<RoomModel> occupancyList = RoomDriver.returnVacantRooms();
        boolean changeMade = false;
        boolean exists = false;

        for (RoomModel room : occupancyList) {
            if (room.getRoomID() == roomID && room.isOccupied()) {
                exists = true;
            }
        }

        if (!exists) {
            //currently getting occupancy list through rsvlist
            // occupancy flag is kind of pointless
            RoomDriver.searchByReservation(rsvID).setRoomID(roomID);
            changeMade = true;
        }

        return changeMade;
    }

    public static void modifyReservationIsNoShow(int rsvID, boolean ins) {
        ReservationDriver.searchByID(rsvID).setIsNoShow(ins);
    }

    public static void modifyReservationIsPaid(int rsvID, boolean ins) {
        ReservationDriver.searchByID(rsvID).setIsPaid(ins);
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Modify Guest">

    public static void modifyGuestNameByRsvID(int rsvID, String name) {
        GuestDriver.searchByReservation(rsvID).setName(name);
    }

    public static void modifyGuestEmailByRsvID(int rsvID, String email) {
        GuestDriver.searchByReservation(rsvID).setEmail(email);
    }

    public static void modifyGuestCCInfoByRsvID(int rsvID, String ccinfo) {
        GuestDriver.searchByReservation(rsvID).setCCInfo(ccinfo);
    }

    public static void modifyGuestNameByGuestID(int guestID, String name) {
        GuestDriver.searchByID(guestID).setName(name);
    }

    public static void modifyGuestEmailByGuestID(int guestID, String email) {
        GuestDriver.searchByID(guestID).setEmail(email);
    }

    public static void modifyGuestCCInfoByGuestID(int guestID, String ccinfo) {
        GuestDriver.searchByID(guestID).setCCInfo(ccinfo);
    }
//</editor-fold>

    public static void checkOutReservation(int rsvID) {
        short roomID = ReservationDriver.searchByID(rsvID).getRoom().getRoomID();
        ReservationDriver.deattachRoom(rsvID);
        RoomDriver.deattachReservation(rsvID);
        ReservationDriver.searchByID(rsvID).setIsConcluded(true);
    }

    public static ReservationModel searchByID(int RsvID) {
        return ReservationDriver.searchByID(RsvID);
    }

    public static List<ReservationModel> searchByDateArrive(LocalDate arrv) {
        return ReservationDriver.searchByDateArrive(arrv);
    }

    public static List<ReservationModel> searchByDateDepart(LocalDate dprt) {
        return EntityDatabase.ReservationTable.retrieveByDateDepart(dprt);
    }

    public static List<ReservationModel> searchByDateInMiddle(LocalDate inclsv) {
        return ReservationDriver.searchByDateInMiddle(inclsv);
    }

    public static List<ReservationModel> searchByDate(LocalDate date) {
        return ReservationDriver.searchByDate(date);
    }

    public static List<ReservationModel> searchByGuest(int guestID) {
        return ReservationDriver.searchByGuest(guestID);
    }

    public static List<ReservationModel> searchByGuest(GuestModel guest) {
        return ReservationDriver.searchByGuest(guest);
    }

    public static ReservationModel searchByRoom(int RoomID) {
        return ReservationDriver.searchByRoom(RoomID);
    }

    public static ReservationModel searchByRoom(RoomModel room) {
        return ReservationDriver.searchByRoom(room);
    }

    public static List<ReservationModel> searchByType(ReservationType rsvType) {
        return ReservationDriver.searchByType(rsvType);
    }

    // returns reservations
    public static List<ReservationModel> returnAllReservations() {
        return ReservationDriver.returnAllReservations();
    }

    public static List<ReservationModel> returnAllNoShow() {
        return ReservationDriver.returnAllNoShow();
    }

    public static List<ReservationModel> returnAllPaid() {
        return ReservationDriver.returnAllPaid();
    }

    public static List<ReservationModel> returnAllConcluded() {
        return ReservationDriver.returnAllConcluded();
    }

    public static ArrayList<ReservationModel> getReservations() {
        // this will be used to retreive more than one reservation based on some
        // criteria. any modifications will be done in a seperate method simillar
        // to modifyReservation()

        ArrayList<ReservationModel> reservations = new ArrayList();
        return reservations;
    }

    public static void applyNowShowReservation(int rsvID, boolean ins) {
        ReservationDriver.searchByID(rsvID).setIsNoShow(ins);
    }

    public static void cancelReservation(int rsvID) {
        //three cases
        //Incentive -> Conventional: cancel more than 3 days in advance (no charge)
        // Incentive -> Conventional: cancel less than 3 days in advance (charge)
        // SixtyDayIn Advance: cancel when is Paid is false (no Charge)

        //steps 
        //Apply charge if needed
        //mark room as vacant
        // set is concluded 
        ReservationModel rsv = ReservationDriver.searchByID(rsvID);
        LocalDate dateArrive = rsv.getDateArrive();
        long diff = DAYS.between(LocalDate.now(), dateArrive) + 1;
        if ((rsv.getReservationType().equals(ReservationType.INCENTIVE)
                && (rsv.getReservationType().equals(ReservationType.CONVENTIONAL)))) {
            if (diff <= 3) {
                PaymentProcessing.processAccmBillCancel(rsv);
                ReservationDriver.deattachRoom(rsv);
                rsv.setIsConcluded(true);
            }

        } else if (rsv.getReservationType().equals(ReservationType.SIXTYADV)) {
            if (!rsv.isPaid()) {
                ReservationDriver.deattachRoom(rsv);
                //May need to negate the charge 
                rsv.setIsConcluded(true);
            }
        } else; // Do nothing

    }

    public static List<ReservationModel> getNoShowList() {
        List<ReservationModel> list
                = ReservationDriver.searchByDate(LocalDate.now().minusDays(1));
        List<ReservationModel> nowShowList = new ArrayList<>();

        for (ReservationModel rsv : list) {
            if (rsv.isNoShow()) {
                nowShowList.add(rsv);
            }
        }

        return nowShowList;
    }

    @Override
    public void openModule() {
        //  TBD; possible future dev
    }

    @Override
    public void closeModule() {
        //  TBD; possible future dev
    }

    @Override
    public void outputModuleMetrics() {
        //  TODO will help with module unit testing
    }

    @Override
    public void runModuleUserStories() {
        //  TODO will be updated as new user stories are created.
    }

    //<editor-fold defaultstate="collapsed" desc="Check Scripts">
    private static boolean incentiveTypeCheck(LocalDate start, LocalDate end) {
        boolean isAv = false;
        List<ReservationModel> rsvList = new ArrayList<>();

        long diff = DAYS.between(LocalDate.now(), start) + 1;
        int roomCount = 0;
        double avg = 0;
        if (diff < 30) {
            for (int i = 0; start.plusDays(i).isBefore(end.plusDays(1)); i++) {
                roomCount += ReservationDriver.searchByDate(start.plusDays(i)).size()
                        - ReservationDriver.searchByDateDepart(start.plusDays(i)).size();
            }

            if (diff > 0) {
                avg = roomCount / (45 * diff);
                if (avg < .6) {
                    isAv = true;
                }
            }
        }

        return isAv;
    }

    private static ReservationType getRsvType(LocalDate start, LocalDate end) {
        ReservationType rsvType = ReservationType.CONVENTIONAL;

        if (start.isAfter(LocalDate.now().plusDays(89))) {
            rsvType = (ReservationType.PREPAID);
        } else if (start.isAfter(LocalDate.now().plusDays(59))) {
            rsvType = (ReservationType.SIXTYADV);
        } else if (start.isBefore(LocalDate.now().plusDays(31))) {
            if (incentiveTypeCheck(start, end)) {
                if (incentiveTypeCheck(start, end)) {
                    rsvType = (ReservationType.INCENTIVE);
                }
            }
        }
        return rsvType;
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Test Scripts">
    public static int createReservationTest(ReservationType rsvType) {
        int returnID = 0;
        LocalDate today = LocalDate.now();
        List<ReservationType> avaliableRsvTypes = new ArrayList<>();
        int typeModifier = 0;
        switch (rsvType) {
            case PREPAID:
                typeModifier = 90;
                break;
            case INCENTIVE:
                typeModifier = 30;
                break;
            case CONVENTIONAL:
                typeModifier = 0;
                break;
            case SIXTYADV:
                typeModifier = 60;
                break;

        }

        if (true) {
            //Check For at least one occupany for duration of stay 
            int occupancyCount = 0;

            LocalDate start = today.plusDays(typeModifier);
            LocalDate end = today.plusDays(typeModifier + 7);
            for (int i = 0; start.plusDays(i).isBefore(end.plusDays(1)); i++) {
                List<ReservationModel> listOccupancy
                        = ReservationDriver.searchByDate(start.plusDays(i));

                if (listOccupancy.size() < 45) {
                    occupancyCount++;
                }
            }

            long diff = DAYS.between(start, end) + 1;

            if (diff == occupancyCount) {
                System.out.println("Is Available!");
                //Can Create Reservation

                int testRsvID = ReservationDriver.createReservationReturnID(start,
                        end);
                ReservationDriver.modifyReservationType(
                        testRsvID, ReservationType.PREPAID);
                returnID = testRsvID;

                PaymentProcessing.
                        generateAccmBill(ReservationDriver.searchByID(testRsvID));

                for (int k = 0; k < ReservationDriver.searchByID(testRsvID).getListBillCharges().size(); k++) {
                    System.out.println(ReservationDriver.searchByID(testRsvID).getListBillCharges().get(k).toString());
                }
            }

        }
        return returnID;
    }

    public static void proccessReservationPaymentTest(int testRsvID) {
        int tempGuestID = GuestDriver.createGuestReturnID("John Doe", "JohnDoe@gmail.com");
        GuestDriver.modifyGuestCreditCardInfo(tempGuestID, "<No Information Provided>");
        GuestModel tempGuest = GuestDriver.searchByID(tempGuestID);
        ReservationDriver.attachGuest(testRsvID, tempGuestID);

        //PaymentProccessing.testProccessMethod(testRsvID);
    }

    public static void multipleRsvGuestCheck() {
        LocalDate today = LocalDate.now();
        int testRsvID
                = ReservationDriver.createReservationReturnID(today,
                        today.plusDays(7));
        int testRsvID_2
                = ReservationDriver.createReservationReturnID(today,
                        today.plusDays(7));
        ReservationDriver.modifyReservationType(
                testRsvID, ReservationType.SIXTYADV);

        int tempGuestID = GuestDriver.createGuestReturnID("John Doe", "JohnDoe@gmail.com");
        GuestDriver.modifyGuestCreditCardInfo(tempGuestID, "<No Information Provided>");
        ReservationDriver.attachGuest(testRsvID, tempGuestID);
        ReservationDriver.attachGuest(testRsvID_2, tempGuestID);
        GuestDriver.attachReservation(tempGuestID, testRsvID);
        GuestDriver.attachReservation(tempGuestID, testRsvID_2);

        List<ReservationModel> rsvList
                = ReservationDriver.searchByGuest(tempGuestID);

        if (rsvList.size() == 2) {
            System.out.println(testRsvID + " = " + rsvList.get(0).getReservationID());
            System.out.println(testRsvID_2 + " = " + rsvList.get(1).getReservationID());
        }
    }

    public static void retrieveByName(String guestName) {
        GuestModel searchResult = GuestDriver.searchByName(guestName);

        if (searchResult == GuestModel.EMPTY_ENTITY) {
            System.out.println("No guest under this name.");
        }

    }

    public static void retrieveByEmail(String guestEmail) {
        GuestModel searchResult = GuestDriver.searchByEmail(guestEmail);

        if (searchResult == GuestModel.EMPTY_ENTITY) {
            System.out.println("No guest with this email address.");
        }

    }

    // Create Guest with Two Fields (Name, Email)
    public static void addGuest(String name, String email) {
        GuestDriver.createGuest(name, email);
        System.out.println("Created Guest: ");

        if (GuestDriver.searchByName(name) == GuestModel.EMPTY_ENTITY) {
            System.out.println("Error Not Found");
        } else {
            System.out.println("Guest: " + name + "Found");
        }
    }

    public static void attachGuest(ReservationModel rsv, GuestModel guest) {
        ReservationDriver.attachGuest(rsv, guest);
        System.out.println("Attached Guest To Reservation: ");

        if (GuestDriver.searchByReservation(rsv) == GuestModel.EMPTY_ENTITY) {
            System.out.println("Error Guest Not Attached");
        } else {
            System.out.println("Guest Attached");
        }
    }

    public static void attachGuest(int rsvID, GuestModel guest) {
        ReservationDriver.attachGuest(rsvID, guest);
        System.out.println("Attached Guest To Reservation ID: ");

        if (GuestDriver.searchByReservation(rsvID) == GuestModel.EMPTY_ENTITY) {
            System.out.println("Error Guest Not Attached");
        } else {
            System.out.println("Guest Attached");
        }

    }

    public static void attachGuest(int rsvID, int guestID) {
        ReservationDriver.attachGuest(rsvID, guestID);

        if (GuestDriver.searchByReservation(rsvID) == GuestModel.EMPTY_ENTITY) {
            System.out.println("Error Guest Not Attached");
        } else {
            System.out.println("Guest Attached");
        }

    }
    //</editor-fold>

    protected static int getRand(int min, int max) {
        Random rand = new Random();
        return (rand.nextInt((max - min) + 1) + min);
    }

    private static int makeDummyGuest() {
        String guestName = "";
        final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
        final int N = lexicon.length();

        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            guestName += lexicon.charAt(r.nextInt(N));
        }
        int id = GuestDriver.createGuestReturnID(guestName,
                (guestName + "gmail.com"), ("8679309"));

        return id;

    }

    private static void makeDummyOccupancies(int numRuns, LocalDate today) {
        for (int i = 0; i < numRuns + 1; i++) {

            int rsvID = createReservation(today,
                    today.plusDays(getRand(1, 7)));
            if (rsvID != 0) {
                RoomModel room = RoomModel.EMPTY_ENTITY;
                int guestID = makeDummyGuest();

                attachReservationToGuest(rsvID, guestID);
                if ((today.equals(LocalDate.now()) || today.isBefore(LocalDate.now())) && !RoomDriver.returnVacantRooms().isEmpty()) {
                    room = RoomDriver.returnVacantRooms().get(0);
                    RoomDriver.flagRoomAsOccupied(room);

                    ReservationDriver.attachRoom(rsvID,
                            i);

                    RoomDriver.attachReservation(i, rsvID);
                }

                proccessNewReservation(rsvID);

                ReservationScheduling.modifyReservationIsPaid(rsvID, false);
                PaymentProcessing.printAccmBill(ReservationDriver.searchByID(rsvID));
            }
        }
    }

    private static void makeDummyFutureReservations(int numRuns, LocalDate today) {
        for (int i = 0; i < numRuns; i++) {
            int caseType = getRand(1, 4);
            int dayModifier = 0;
            switch (caseType) {
                case 1:
                    //Prepaid
                    dayModifier = 91;
                    break;
                case 2:
                    //Sixty Adv
                    dayModifier = 61;
                    break;
                case 3:
                    //Conventional
                    dayModifier = 42;
                    break;
                default:
                    dayModifier = 15;
                    break;
            }

            int rsvID = createReservation(today.plusDays(dayModifier),
                    today.plusDays(dayModifier + getRand(1, 7)));
            if (rsvID != 0) {
                int guestID = makeDummyGuest();

                attachReservationToGuest(rsvID, guestID);
                proccessNewReservation(rsvID);

                ReservationScheduling.modifyReservationIsPaid(rsvID, false);

                //Error may have to change bill list when paid look at
                //proccessing tomorrow
            }
        }
    }

    public static void generateDummyDataSet1() {
        // under 60% occupancy for seven day period
        // 40 reservations In Advance   
        //under 27
        EntityDatabase.DevUtilities.generateRoomTableAllAvailable();
        List<String> guestNameList = new ArrayList<>();

        for (int i = 0; LocalDate.now().minusDays(50).plusDays(i).isBefore(LocalDate.now().plusDays(462)); i++) {
            RateDriver.createRate(LocalDate.now().minusDays(50).plusDays(i), 70 + getRand(0, 25));
        }

        makeDummyOccupancies(26, LocalDate.now().minusDays(getRand(1, 5)));
        for (int i = 0; LocalDate.now().plusDays(i).isBefore(LocalDate.now().plusDays(8)); i++) {
            int actualOccupancy = (26
                    - ReservationDriver.searchByDateDepart(LocalDate.now().plusDays(i)).size());

            if (actualOccupancy < 26) {
                makeDummyOccupancies((26 - actualOccupancy), LocalDate.now().plusDays(i));

            }
        }

        //Occupancy may be higher than number of rooms if you include departures
        //System.out.println(RoomDriver.returnOccupiedRooms().size() + " WH");
        makeDummyFutureReservations(40, LocalDate.now());
        ReportGeneration.writeDailyOccupancyReport();

    }

    public static void generateDummyDataSet2() {
        // over 60% occupancy for seven day period
        // 40 reservations In Advance   
        //at 35
        EntityDatabase.DevUtilities.generateRoomTableAllAvailable();
        List<String> guestNameList = new ArrayList<>();

        for (int i = 0; LocalDate.now().minusDays(50).plusDays(i).isBefore(LocalDate.now().plusDays(462)); i++) {
            RateDriver.createRate(LocalDate.now().minusDays(50).plusDays(i), 70 + getRand(0, 25));
        }

        makeDummyOccupancies(35, LocalDate.now().minusDays(getRand(1, 5)));
        for (int i = 0; LocalDate.now().plusDays(i).isBefore(LocalDate.now().plusDays(8)); i++) {
            int actualOccupancy = (35
                    - ReservationDriver.searchByDateDepart(LocalDate.now().plusDays(i)).size());

            if (actualOccupancy < 35) {
                makeDummyOccupancies((35 - actualOccupancy), LocalDate.now().plusDays(i));

            }
        }

        //Occupancy may be higher than number of rooms if you include departures
        //System.out.println(RoomDriver.returnOccupiedRooms().size() + " WH");
        makeDummyFutureReservations(40, LocalDate.now());
        ReportGeneration.writeDailyOccupancyReport();

    }

    public static void generateDummyDataSet3() {
        EntityDatabase.DevUtilities.generateRoomTableAllAvailable();
        List<String> guestNameList = new ArrayList<>();

        for (int i = 0; LocalDate.now().minusDays(50).plusDays(i).isBefore(LocalDate.now().plusDays(462)); i++) {
            RateDriver.createRate(LocalDate.now().minusDays(50).plusDays(i), 70 + getRand(0, 25));
        }

        makeDummyOccupancies(45, LocalDate.now().minusDays(getRand(1, 5)));
        for (int i = 0; LocalDate.now().plusDays(i).isBefore(LocalDate.now().plusDays(8)); i++) {
            int actualOccupancy = (45
                    - ReservationDriver.searchByDateDepart(LocalDate.now().plusDays(i)).size());

            if (actualOccupancy < 45) {
                makeDummyOccupancies((45 - actualOccupancy), LocalDate.now().plusDays(i));

            }
        }

        //Occupancy may be higher than number of rooms if you include departures
        //System.out.println(RoomDriver.returnOccupiedRooms().size() + " WH");
        makeDummyFutureReservations(40, LocalDate.now());

    }

}
