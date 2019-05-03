package prototype.logic.schedulers;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import prototype.data.drivers.ReservationDriver;
import prototype.data.models.ReservationModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import prototype.data.drivers.GuestDriver;
import prototype.data.drivers.RoomDriver;
import prototype.data.models.GuestModel;
import prototype.data.models.ReservationType;
import prototype.data.models.RoomModel;

public class ReservationScheduling implements Scheduler {

    // receive a date and dte depart
    // send these to payment processing, 
    //Creates reservation and generates BillCharge **NEEED TO ATTACH GUEST***
    // IF PREPAID NEEEED GUEST AND SEND CHARGE
    public static int createReservation(LocalDate dateArrive, LocalDate dateDepart, ReservationType rsvType) {

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

            if (listOccupancy.size() < 45) {
                occupancyCount++;
            }
        }
        long diff = DAYS.between(dateArrive, dateDepart) + 1;

        if (diff == occupancyCount) {
            if (typeAvliableCheck(rsvType, dateArrive, dateDepart)) {
                int testRsvID = ReservationDriver.createReservationReturnID(dateArrive,
                        dateDepart);
                ReservationDriver.modifyReservationType(
                        testRsvID, rsvType);
                returnID = testRsvID;

                PaymentProcessing.
                        generateAccmBill(ReservationDriver.searchByID(testRsvID));
            }
        }

        return returnID;
    }

    public static void modifyReservationArriveDate(int rsvID, LocalDate arriveDate) {
        ReservationDriver.modifyDateArrive(rsvID, arriveDate);
    }

    public static void modifyReservationDepartDate(int rsvID, LocalDate departDate) {
        ReservationDriver.modifyDateDepart(rsvID, departDate);
    }

    public static void modifyReservationDatePaid(int rsvID, LocalDate datePaid) {
        ReservationDriver.modifyDatePaid(rsvID, datePaid);
    }

    public static boolean modifyReservationType(int rsvID, ReservationType rsvType) {
        boolean changeMade = false;
        LocalDate dateArrive
                = ReservationDriver.searchByID(rsvID).getDateArrive();
        LocalDate dateDepart
                = ReservationDriver.searchByID(rsvID).getDateDepart();

        if (typeAvliableCheck(rsvType, dateArrive, dateDepart)) {
            ReservationDriver.modifyReservationType(rsvID, rsvType);
            changeMade = false;
        }

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
    
    public static ArrayList<ReservationModel> getReservations() {
        // this will be used to retreive more than one reservation based on some
        // criteria. any modifications will be done in a seperate method simillar
        // to modifyReservation()

        ArrayList<ReservationModel> reservations = new ArrayList();
        return reservations;
    }

    public static void applyNowShowReservation() {

    }

    public static void cancelReservation() {

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

    private static boolean incentiveTypeCheck(LocalDate start, LocalDate end) {
        boolean isAv = false;
        List<ReservationModel> rsvList = new ArrayList<>();

        long diff = DAYS.between(LocalDate.now(), start) + 1;
        int roomCount = 0;
        double avg = 0;
        if (diff >= 30) {
            for (int i = 0; start.plusDays(i).isBefore(end.plusDays(1)); i++) {
                roomCount += ReservationDriver.searchByDate(start.plusDays(i)).size();
            }

            avg = roomCount / (45 * diff);
            if (avg < .6) {
                isAv = true;
            }
        }

        return isAv;
    }

    private static List<ReservationType> getAvaliableRsvTypes(LocalDate start, LocalDate end) {
        List<ReservationType> rsvTypeList = new ArrayList<>();

        rsvTypeList.add(ReservationType.CONVENTIONAL);
        if (start.isAfter(LocalDate.now().plusDays(89))) {
            rsvTypeList.add(ReservationType.PREPAID);
        }
        if (start.isAfter(LocalDate.now().plusDays(59))) {
            rsvTypeList.add(ReservationType.SIXTYADV);
        }
        if (start.isBefore(LocalDate.now().plusDays(31))) {
            if (incentiveTypeCheck(start, end)) {
                rsvTypeList.add(ReservationType.INCENTIVE);
            }
        }
        return rsvTypeList;
    }

    private static boolean typeAvliableCheck(ReservationType rsvType, LocalDate start, LocalDate end) {
        List<ReservationType> avaliableRsvTypes = new ArrayList<>();
        boolean containsType = false;

        avaliableRsvTypes = getAvaliableRsvTypes(start, end);
        for (int i = 0; i < avaliableRsvTypes.size(); i++) {
            if (avaliableRsvTypes.get(i) == rsvType) {
                containsType = true;
            }

        }
        if (!containsType || rsvType == ReservationType.CONVENTIONAL) {
            // System.out.println("Type Check: Pass");
        } else {
            // System.out.println("Type Check: Fail");
        }

        return containsType;

    }

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

        if (typeAvliableCheck(rsvType, today.plusDays(typeModifier), today.plusDays(typeModifier))) {
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
                System.out.println("Is Avaliable!");
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

    public static GuestModel retrieveByName(String guestName) {
        GuestModel searchResult = GuestDriver.searchByName(guestName);
        System.out.println("No guest under this name.");

        if (searchResult == GuestModel.EMPTY_ENTITY);
        return null;
    }

    public static GuestModel retrieveByEmail(String guestEmail) {
        GuestModel searchResult = GuestDriver.searchByEmail(guestEmail);
        System.out.println("No guest with this email address.");

        if (searchResult == GuestModel.EMPTY_ENTITY);
        return null;
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

}
