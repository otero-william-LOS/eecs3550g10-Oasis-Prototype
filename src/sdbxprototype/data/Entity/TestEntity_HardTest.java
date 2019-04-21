/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import static Database.TestDatabase.getPathsInDirectory;
import static Database.TestDatabase.makeMyFolder;
import static Entity.BllchrgEntity.requestAll_BllchrgData;
import static Entity.BllchrgEntity.requestAll_LazyBllchrgData;
import static Entity.EntityDatabase.appendDatabase;
import static Entity.EntityDatabase.modifyDatabase;
import static Entity.GuestEntity.guestToLazyList;
import static Entity.GuestEntity.requestAll_GuestData;
import static Entity.RateEntity.rateToLazyList;
import static Entity.RateEntity.requestAll_RATEDATA;
import static Entity.RsvEntity.requestAll_RSVData;
import static Entity.RsvEntity.rsvToLazyList;

import Models.*;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cmason12
 */
public class TestEntity_HardTest extends Database.Database {

    private static int rsvIndex = 1;
    private static short roomIndex = 1;
    private static int bllIndex = 1;
    private static int guestIndex = 1;

    //----------------------------TESTS-----------------------------------//
    public static void entityHardTest(int numRuns) {
        ArrayList<RsvModel> rsvList = new ArrayList<>();
        ArrayList<RateModel> rateList = new ArrayList<>();
        ArrayList<BllchrgModel> bllList = new ArrayList<>();
        ArrayList<RoomModel> roomList = new ArrayList<>();
        ArrayList<GuestModel> guestList = new ArrayList<>();

        for (int k = 0; k < numRuns; k++) {
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

            //  Database.Database._RUNMODE
            //       = Database.Database.OperationalMode.DEBUG_ENTITYHARDTEST_MODE;
            Database.Database.rsvList = rsvToLazyList(rsvList);
            Database.Database.billList = requestAll_LazyBllchrgData(bllList);
            Database.Database.guestList = guestToLazyList(guestList);
            Database.Database.rateList = rateToLazyList(createDummyRateData());
            Database.Database.writeRSV_Data();
            Database.Database.writeBllchrg_Data();
            Database.Database.writeGUEST_Data();
            Database.Database.writeRATE_Data();

            ArrayList<RsvModel> compRsvList = new ArrayList<>();
            ArrayList<RsvModel> compRoomList = new ArrayList<>();
            ArrayList<RsvModel> compBllchrgList = new ArrayList<>();

            compRsvList = requestAll_RSVData();

            // MsgBox((ogRsvList == compRsvList)+"");
            for (int i = 0; i < bllList.size(); i++) {
                for (int j = 0; j < rsvList.size(); j++) {
                    if (bllList.get(i).getReservation().getRsvID() == rsvList.get(j).getRsvID()) {
                        ArrayList<BllchrgModel> tempModel = rsvList.get(j).getListBllchrg();
                        tempModel.add(bllList.get(i));
                        rsvList.get(j).setListBllchrg(tempModel);
                        bllList.get(i).setReservation(rsvList.get(j));
                    }
                }
            }

            for (int i = 0; i < rsvList.size(); i++) {
                if (rsvList.get(i).getRoom() != null) {
                    rsvList.get(i).getRoom().setRsv(rsvList.get(i));
                }
            }

            for (int i = 0; i < guestList.size(); i++) {
                for (int j = 0; j < rsvList.size(); j++) {
                    if (guestList.get(i).getGuestID() == rsvList.get(j).getGuest().getGuestID()) {
                        ArrayList<RsvModel> tempList =
                                guestList.get(i).getListRsv();

                        if (tempList == null) {
                            tempList = new ArrayList<>();
                        }
                        tempList.add(rsvList.get(j));
                        guestList.get(i).setListRsv(tempList);
                        rsvList.get(j).setGuest(guestList.get(i));
                    }
                }
            }

            hardTest1(rsvList, compRsvList);
            Database.Database._RUNMODE
                    = Database.Database.OperationalMode.NORMAL;
            absolutMove();

        }
    }

    public static void testNewPush() {
        RsvModel rsv = new RsvModel();
        ArrayList<RateModel> rateList = new ArrayList<>();
        ArrayList<BllchrgModel> bllList = new ArrayList<>();
        ArrayList<RoomModel> roomList = new ArrayList<>();
        GuestModel guest = new GuestModel();

        rsv = new RsvModel();
        rateList = new ArrayList<>();
        bllList = new ArrayList<>();
        roomList = new ArrayList<>();
        guestList = new ArrayList<>();

        rsvIndex = 1;
        roomIndex = 1;
        bllIndex = 1;
        guestIndex = 1;

        rateList = createDummyRateData();
        RsvModel tempRsv = createCurrentDummyRsv();
        roomList.add(tempRsv.getRoom());
        guest = (tempRsv.getGuest());

        ArrayList<BllchrgModel> tempBllList = createDummyBllList();
        for (int j = 0; j < tempBllList.size(); j++) {
            tempBllList.get(j).setReservation(tempRsv);
            bllList.add(tempBllList.get(j));
        }
        tempRsv.setListBllchrg(tempBllList);
        rsv = (tempRsv);

        appendDatabase(tempRsv);
        appendDatabase(tempRsv);
        tempRsv.setRsvID(66);
        appendDatabase(tempRsv);
        appendDatabase(rateList.get(0));
        appendDatabase(guest);

        for (int i = 0; i < tempBllList.size(); i++) {
            appendDatabase(tempBllList.get(i));
        }
    }

    public static void testModify() {
        Database.Database._RUNMODE
                = Database.Database.OperationalMode.DEBUG_ENTITYHARDTEST_MODE;

        //testPush makes sure there is data to edit in testDB
        testNewPush();
        LocalDate today = LocalDate.now();
        RsvModel rsv = new RsvModel();
        rsv.setRsvID(66);
        rsv.setRoom(new RoomModel());
        rsv.getRoom().setRoomID((short) 69);
        rsv.setGuest(new GuestModel());
        rsv.getGuest().setGuestID(69);
        rsv.setDateArrive(Date.valueOf(today));
        rsv.setDateDepart(Date.valueOf(today));
        rsv.setDatePaid(Date.valueOf(today));
        rsv.setRsvType(RsvType.SIXTYADV);
        rsv.setIsNoShow(true);
        rsv.setIsPaid(true);
        rsv.setIsConcluded(true);

        ArrayList<BllchrgModel> tempList = requestAll_BllchrgData();
        BllchrgModel tempBll = tempList.get(0);
        System.out.println(tempBll.getBllchrgID() + "");
        tempBll.setLineDescription("THIS WAS A TEST");
        modifyDatabase(tempBll);

        ArrayList<RateModel> tempList2 = requestAll_RATEDATA();
        RateModel tempRate = tempList2.get(0);
        tempRate.setBaseRate(666);
        modifyDatabase(tempRate);

        ArrayList<GuestModel> tempList3 = requestAll_GuestData();
        GuestModel tempGuest = tempList3.get(0);
        tempGuest.setEmail("<THIS IS A TEST>");
        modifyDatabase(tempGuest);

        Database.Database._RUNMODE
                = Database.Database.OperationalMode.NORMAL;

    }
//----------------------------<END> TESTS-----------------------------------//

    //----------------------Data Generation Methods ------------------//
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
        Date datePaid = Date.valueOf(localDate.minusMonths(
                localDate.getMonthValue() - 1).minusDays(
                localDate.getDayOfMonth() - 1).minusYears(
                localDate.getYear() - 1));

        RsvModel newRsv = new RsvModel();
        newRsv.setRsvID(rsvIndex);
        rsvIndex++;
        newRsv.setDateArrive(dateArrive);
        newRsv.setDateDepart(dateDepart);
        newRsv.setDatePaid(datePaid);
        newRsv.setRsvType(getRandRsvType());

        RoomModel room = new RoomModel();
        room.setIsOccupied(false);
        room.setRoomID((short) (0));
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
    //------------------<END> Data Generation Methods ------------------//

    //----------------------Log Method------------------//
    protected static boolean hardTest1(ArrayList<RsvModel> origonal, ArrayList<RsvModel> read) {
        FileWriter fileWriter = null;
        ArrayList<String> testInfo = new ArrayList<>();
        int testIteration = 0;
        String path = "";
        boolean readingError = false;

        final String PARENTFOLDER = "EntityHardTest";
        final String CHILDFOLDER = "HardTests";
        makeMyFolder(PARENTFOLDER);
        makeMyFolder(PARENTFOLDER + "\\" + CHILDFOLDER);

        testInfo = getPathsInDirectory(PARENTFOLDER + "\\" + CHILDFOLDER);

        if (testInfo.size() == 0) {
            testIteration = 1;
        } else {
            testIteration
                    = Integer.parseInt(testInfo.get(testInfo.size() - 1)) + 1;
        }
        path = PARENTFOLDER + "\\" + CHILDFOLDER + "\\" + testIteration;
        makeMyFolder(path);
        path += "\\Results.csv";

        if (origonal.size() != read.size()) {
            //  MsgBox(origonal.size() + " " + read.size());
        }
        try {
            Files.deleteIfExists(Paths.get(path));

            fileWriter = new FileWriter(path);
            // rsvWrittenList.get(0).getDateArrive();
            // fileWriter.write(rsvWrittenList);

            fileWriter.write("MODEL=LAZY,RsvID,DateArrive,DateDepart,DatePaid,");
            fileWriter.write("RsvType,RoomID,GuestID,IsNoShow,");
            fileWriter.write("IsPaid,IsConcluded,\n");

            for (int i = 0; i < origonal.size(); i++) {
                boolean isEqual = false;

                isEqual = (origonal.get(i).getRsvID() == read.get(i).getRsvID())
                        && origonal.get(i).getDateArrive().equals(read.get(i).getDateArrive())
                        && origonal.get(i).getDateDepart().equals(read.get(i).getDateDepart())
                        && origonal.get(i).getDatePaid().equals(read.get(i).getDatePaid())
                        && (origonal.get(i).getRsvType() == read.get(i).getRsvType())
                        && ((origonal.get(i).getRoom().getRoomID() == read.get(i).getRoom().getRoomID())
                        && (origonal.get(i).getRoom().getIsOccupied() == read.get(i).getRoom().getIsOccupied()))
                        && ((origonal.get(i).getGuest().getGuestID() == read.get(i).getGuest().getGuestID())
                        && (origonal.get(i).getGuest().getName().equals(read.get(i).getGuest().getName()))
                        && (origonal.get(i).getGuest().getCCInfo().equals(read.get(i).getGuest().getCCInfo()))
                        && (origonal.get(i).getGuest().getEmail().equals(read.get(i).getGuest().getEmail())))
                        && (origonal.get(i).isIsNoShow() == read.get(i).isIsNoShow())
                        && (origonal.get(i).isIsPaid() == read.get(i).isIsPaid())
                        && (origonal.get(i).isIsConcluded() == read.get(i).isIsConcluded());

                if (!isEqual) {
                    readingError = true;
                }

                fileWriter.write((isEqual) + ","
                        + (origonal.get(i).getRsvID() == read.get(i).getRsvID()) + ","
                        + origonal.get(i).getDateArrive().equals(read.get(i).getDateArrive()) + ","
                        + origonal.get(i).getDateDepart().equals(read.get(i).getDateDepart()) + ","
                        + origonal.get(i).getDatePaid().equals(read.get(i).getDatePaid()) + ","
                        + (origonal.get(i).getRsvType() == read.get(i).getRsvType())
                        + "," + ((origonal.get(i).getRoom().getRoomID() == read.get(i).getRoom().getRoomID()) && (origonal.get(i).getRoom().getIsOccupied() == read.get(i).getRoom().getIsOccupied()))
                        + "," + ((origonal.get(i).getGuest().getGuestID() == read.get(i).getGuest().getGuestID())
                        && (origonal.get(i).getGuest().getName().equals(read.get(i).getGuest().getName()))
                        && (origonal.get(i).getGuest().getCCInfo().equals(read.get(i).getGuest().getCCInfo()))
                        && (origonal.get(i).getGuest().getEmail().equals(read.get(i).getGuest().getEmail())))
                        + "," + (origonal.get(i).isIsNoShow() == read.get(i).isIsNoShow())
                        + "," + (origonal.get(i).isIsPaid() == read.get(i).isIsPaid())
                        + "," + (origonal.get(i).isIsConcluded() == read.get(i).isIsConcluded())
                        + ",\n");

            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e);
            readingError = true;
            //MsgBox("Help Mr. Robinson!!!");
        }
        return readingError;
    }

    //----------------------<END> Log Method------------------//
    //---------------File Methods--------------------------//
    private static void moveTestData() {
        int testIteration = 0;
        ArrayList<String> testInfo = new ArrayList<>();
        testInfo = getPathsInDirectory("EntityHardTest\\HardTests\\");

        if (testInfo == null) {
            testIteration = 1;
        } else {
            testIteration
                    = Integer.parseInt(testInfo.get(testInfo.size() - 1));
        }

        try {
            Path fileToMovePath
                    = (Paths.get("EntityHardTest\\guestTestData.csv"));
            Path targetPath = Paths.get("EntityHardTest\\HardTests\\" + testIteration + "\\");

            Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            //System.out.println(e);
        }

        try {
            Path fileToMovePath
                    = (Paths.get("EntityHardTest\\rsvTestData.csv"));
            Path targetPath = Paths.get("EntityHardTest\\HardTests\\" + testIteration + "\\");

            Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            //System.out.println(e);
        }

        try {
            Path fileToMovePath
                    = (Paths.get("EntityHardTest\\billingTestData.csv"));
            Path targetPath = Paths.get("EntityHardTest\\HardTests\\" + testIteration + "\\");

            Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {

        }

        try {
            Path fileToMovePath
                    = (Paths.get("EntityHardTest\\rateTestData.csv"));
            Path targetPath = Paths.get("EntityHardTest\\HardTests\\" + testIteration + "\\");

            Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {

        }

    }

    private static void absolutMove() {
        while (Files.exists(Paths.get("EntityHardTest\\rsvTestData.csv"))
                || Files.exists(Paths.get("EntityHardTest\\guestTestData.csv"))
                || Files.exists(Paths.get("EntityHardTest\\billingTestData.csv"))) {
            moveTestData();
        }
    }

    //---------------<END> File Methods--------------------------//
}
