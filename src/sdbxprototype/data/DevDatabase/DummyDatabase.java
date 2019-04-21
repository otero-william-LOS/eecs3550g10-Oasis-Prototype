/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DevDatabase;


import Models.BllchrgModel;
import Models.GuestModel;
import Models.RateModel;
import Models.RoomModel;
import Models.RsvModel;
import Models.RsvType;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cmason12
 */
public class DummyDatabase {

    private static ArrayList<RsvModel> rsvList = new ArrayList<>();
    private static ArrayList<RateModel> rateList = new ArrayList<>();
    private static ArrayList<BllchrgModel> bllList = new ArrayList<>();
    private static ArrayList<RoomModel> roomList = new ArrayList<>();
    private static ArrayList<GuestModel> guestList = new ArrayList<>();

    private static int rsvIndex = 1;
    private static short roomIndex = 1;
    private static int bllIndex = 1;
    private static int guestIndex = 1;

    protected static int getRand(int min, int max) {
        Random rand = new Random();
        return (rand.nextInt((max - min) + 1) + min);
    }

    private static RsvType getRandRsvType() {
        Random rand = new Random();
        RsvType type = null;

        switch (rand.nextInt((4 - 1) + 1) + 1) {
            case 1:
                type = RsvType.CONVENTIONAL;
                break;
            case 2:
                type = RsvType.INCENTIVE;
                break;
            case 3:
                type = RsvType.PREPAID;
                break;
            default:
                type = RsvType.SIXTYADV;
                break;
        }
        return type;
    }

    private static boolean getRandBool() {
        Random rand = new Random();

        return (rand.nextInt((1) + 1) == 0);
    }

    private static RsvModel createCurrentDummyRsv() {
        LocalDate localDate = LocalDate.now();
        Date dateArrive = Date.valueOf(localDate);
        Date dateDepart = Date.valueOf(localDate.plusDays(getRand(1, 7)));
        Date datePaid = Date.valueOf(localDate.minusDays(getRand(20, 70)));

        RsvModel newRsv = new RsvModel();
        newRsv.setRsvID(rsvIndex);
        rsvIndex++;
        newRsv.setDateArrive(dateArrive);
        newRsv.setDateDepart(dateDepart);
        newRsv.setDatePaid(datePaid);
        newRsv.setRsvType(getRandRsvType());

        RoomModel room = new RoomModel();
        room.setIsOccupied(true);
        room.setRoomID(roomIndex);
        roomIndex++;

        newRsv.setRoom(room);
        room.setRsv(newRsv);

        GuestModel guest = new GuestModel();
        guest.setGuestID(guestIndex);
        guest.setName("Name" + guestIndex);
        guest.setCCInfo("CCInfo" + guestIndex);
        guest.setEmail("exampleEmail" + guestIndex + "@gmail.com");
        guestIndex++;
        newRsv.setGuest(guest);
        newRsv.setListBllchrg(null);
        newRsv.setIsPaid(true);
        newRsv.setIsNoShow(false);
        newRsv.setIsConcluded(false);

        return newRsv;
    }

    private static RsvModel createFutureDummyRsv() {
        LocalDate localDate = LocalDate.now();
        Date dateArrive = Date.valueOf(localDate);
        Date dateDepart = Date.valueOf(localDate.plusDays(getRand(1, 7)));
        Date datePaid = Date.valueOf(localDate.minusYears(100));

        RsvModel newRsv = new RsvModel();
        newRsv.setRsvID(rsvIndex);
        rsvIndex++;
        newRsv.setDateArrive(dateArrive);
        newRsv.setDateDepart(dateDepart);
        newRsv.setDatePaid(datePaid);
        newRsv.setRsvType(getRandRsvType());

        RoomModel room = new RoomModel();
        room.setIsOccupied(false);
        room.setRoomID((short) (-1));
        roomIndex++;

        newRsv.setRoom(room);
        room.setRsv(newRsv);

        GuestModel guest = new GuestModel();
        guest.setGuestID(guestIndex);
        guest.setName("Name" + guestIndex);
        guest.setCCInfo("CCInfo" + guestIndex);
        guest.setEmail("exampleEmail" + guestIndex + "@gmail.com");
        guestIndex++;
        newRsv.setGuest(guest);
        newRsv.setListBllchrg(null);
        newRsv.setIsPaid(getRandBool());
        if (newRsv.isIsPaid()) {
            datePaid = Date.valueOf(localDate.minusDays(getRand(30, 90)));
            newRsv.setDatePaid(datePaid);
        }
        newRsv.setIsNoShow(false);
        newRsv.setIsConcluded(false);

        return newRsv;
    }

    private static ArrayList<RateModel> createDummyRateData() {
        LocalDate localDate = LocalDate.now();
        ArrayList<RateModel> list = new ArrayList<>();

        for (int i = 0; i < 365; i++) {
            RateModel tempRate = new RateModel();

            tempRate.setBaseRate(getRand(80, 100));
            tempRate.setRateDate(Date.valueOf(localDate.plusDays(i)));
            list.add(tempRate);
        }
        return list;
    }

    private static ArrayList<BllchrgModel> createDummyBllList() {
        ArrayList<BllchrgModel> bllList = new ArrayList<>();
        LocalDate localDate = LocalDate.now();
        int offset = getRand(15, 35);
        Date dateCharged = Date.valueOf(localDate.minusDays(offset));
        Date datePaid = Date.valueOf(localDate.minusDays(offset - getRand(0, 14)));

        int iter = getRand(1, 10);
        for (int i = 0; i < iter; i++) {
            BllchrgModel newBll = new BllchrgModel();
            newBll.setBllchrgID(bllIndex);
            newBll.setLineDescription("Description" + bllIndex);
            bllIndex++;
            newBll.setDateCharged(dateCharged);
            newBll.setIsPaid(getRandBool());
            if (newBll.isIsPaid()) {
                newBll.setDatePaid(datePaid);
            }

            newBll.setAmount(getRand(500, 1500));
            bllList.add(newBll);
        }
        return bllList;
    }

    public DummyDatabase() {

        rsvList = new ArrayList<>();
        rateList = new ArrayList<>();
        bllList = new ArrayList<>();
        roomList = new ArrayList<>();
        guestList = new ArrayList<>();

        rsvIndex = 1;
        roomIndex = 1;
        bllIndex = 1;
        guestIndex = 1;
        for (int i = 0; i < 20; i++) {

            RsvModel tempRsv = createCurrentDummyRsv();
            roomList.add(tempRsv.getRoom());
            guestList.add(tempRsv.getGuest());

            ArrayList<BllchrgModel> tempBllList = createDummyBllList();
            for (int j = 0; j < tempBllList.size(); j++) {
                tempBllList.get(j).setReservation(tempRsv);
                bllList.add(tempBllList.get(j));
            }
            tempRsv.setListBllchrg(tempBllList);
            rsvList.add(tempRsv);
        }

        for (int i = 0; i < 20; i++) {
            RsvModel tempRsv = createFutureDummyRsv();
            guestList.add(tempRsv.getGuest());

            ArrayList<BllchrgModel> tempBllList = createDummyBllList();
            if (tempRsv.isIsPaid()) {
                for (int j = 0; j < tempBllList.size(); j++) {
                    tempBllList.get(j).setReservation(tempRsv);
                    bllList.add(tempBllList.get(j));
                }
                tempRsv.setListBllchrg(tempBllList);
            }
            rsvList.add(tempRsv);
        }
	//Creates rates for 1 year 
createDummyRateData();
    }

    public static ArrayList<RsvModel> getAllRsvData() {
        return rsvList;
    }

    ;
public static ArrayList<RateModel> getAllRateData() {
        return rateList;
    }

    ;
public static ArrayList<BllchrgModel> getAllBllchrgData() {
        return bllList;
    }

    ;
public static ArrayList<GuestModel> getAllGuestData() {
        return guestList;
    }

    public static ArrayList<RsvModel> getRsvDataBetween(Date start, Date end) {
        ArrayList<RsvModel> list = new ArrayList<>();

        for (int i = 0; i < rsvList.size(); i++) {
            if ((start.equals(rsvList.get(i).getDateArrive()) || start.after(rsvList.get(i).getDateArrive()))
                    && (end.equals(rsvList.get(i).getDateDepart()) || end.before(rsvList.get(i).getDateDepart()))) {
                list.add(rsvList.get(i));
            }
        }
        return list;
    }
    
    public static ArrayList<RsvModel> getRsvDataGetDate(Date date) {
        ArrayList<RsvModel> list = new ArrayList<>();

        for (int i = 0; i < rsvList.size(); i++) {
            if ((date.equals(rsvList.get(i).getDateArrive()) )
                    && (date.equals(rsvList.get(i).getDateDepart()) )) {
                list.add(rsvList.get(i));
            }
        }
        return list;
    }

}

