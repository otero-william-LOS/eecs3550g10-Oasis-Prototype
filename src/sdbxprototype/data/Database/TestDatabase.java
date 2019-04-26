/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import static Database.Database.billList;
import static Database.Database.guestList;
import static Database.Database.rateList;
import static Database.Database.rsvList;
import Models.RsvType;
import static Models.RsvType.*;


import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author cmason12
 */
public class TestDatabase extends Database {

    private static enum ModelInstance {
        RSVMODEL,
        RATEMODEL,
        BLLCHRGMODEL,
        GUESTMODEL,
        NULLMODEL
    }

    private static ModelInstance _WRITE_ERROR = ModelInstance.NULLMODEL;
    private static ModelInstance _READ_ERROR = ModelInstance.NULLMODEL;
    //Write 
    protected static ArrayList<LazyRsvModel> rsvWrittenList
            = new ArrayList();

    protected static ArrayList<LazyRateModel> rateWrittenList
            = new ArrayList();
    protected static ArrayList<LazyBllchrgModel> billWrittenList
            = new ArrayList();

    protected static ArrayList<LazyGuestModel> guestWrittenList
            = new ArrayList();

    protected static ArrayList<LazyRsvModel> generateDummyRsvData(int numRes) {

        ArrayList<LazyRsvModel> dummyList = new ArrayList<>();
        for (int i = 0; i < numRes; i++) {
            LazyRsvModel tstRsv = new LazyRsvModel();

            tstRsv.setRsvID(getRand(0, 150));
            tstRsv.setDateArrive(getTestDate());

            tstRsv.setDateDepart(getTestDate());
            tstRsv.setDatePaid(getTestDate());

            switch (getRand(1, 4)) {
                case 1:
                    tstRsv.setRsvType(PREPAID);
                    break;
                case 2:
                    tstRsv.setRsvType(SIXTYADV);
                    break;
                case 3:
                    tstRsv.setRsvType(CONVENTIONAL);
                    break;
                case 4:
                    tstRsv.setRsvType(INCENTIVE);
                    break;
                default:
                    break;

            }
            tstRsv.setRoom((short) getRand(0, 10));
            tstRsv.setGuest(getRand(1, 100));

            switch (getRand(0, 1)) {
                case 0:
                    tstRsv.setIsNoShow(true);
                    break;
                default:
                    tstRsv.setIsNoShow(false);
                    break;
            }

            switch (getRand(0, 1)) {
                case 0:
                    tstRsv.setIsPaid(true);
                    break;
                default:
                    tstRsv.setIsPaid(false);
                    break;
            }

            switch (getRand(0, 1)) {
                case 0:
                    tstRsv.setIsConcluded(true);
                    break;
                default:
                    tstRsv.setIsConcluded(false);
                    break;
            }
            dummyList.add(tstRsv);
        }
        return dummyList;
    }

    public static void writeRSVTestData(int numLines) {
        int random = 0; // (int)(Math.random() * 50 + 1);

        rsvList = generateDummyRsvData(numLines);
        rsvWrittenList = rsvList;
        Database.writeRSV_Data();

    }

    private static ArrayList<LazyBllchrgModel> generateDummyBllchrgData(int numBll) {
        ArrayList<LazyBllchrgModel> dummyBll = new ArrayList<>();

        for (int i = 0; i < numBll; i++) {
            LazyBllchrgModel tstBill = new LazyBllchrgModel();

            tstBill.setBllchrgID(getRand(0, 255));
            tstBill.setReservation(getRand(0, 255));
            tstBill.setLineDescription("LinDescription"
                    + getRand(0, 1000) + "");
            tstBill.setAmount(getRand(0, 1255));
            tstBill.setDateCharged(getTestDate());
            tstBill.setDatePaid(getTestDate());
            switch (getRand(0, 1)) {
                case 0:
                    tstBill.setIsPaid(true);
                    break;
                default:
                    tstBill.setIsPaid(false);
                    break;

            }
            dummyBll.add(tstBill);

        }
        return dummyBll;
    }

    public static void writeBllchrgTestData(int numLines) {
        int random = 0; // (int)(Math.random() * 50 + 1);

        billList = generateDummyBllchrgData(numLines);

        billWrittenList = billList;
        Database.writeBllchrg_Data();

    }

    private static ArrayList<LazyGuestModel> generateDummyGuestData(int numGuests) {
        ArrayList<LazyGuestModel> dummyGuests = new ArrayList<>();
        for (int i = 0; i < numGuests; i++) {
            LazyGuestModel tstGuest = new LazyGuestModel();

            tstGuest.setGuestID(getRand(1, 255));
            tstGuest.setName("Name" + getRand(1, 255) + "");
            tstGuest.setCCInfo("CCINFO" + getRand(1, 255) + "");
            tstGuest.setEmail("randomCustomer" + getRand(1, 255)
                    + "@GMAIL.COM");

            dummyGuests.add(tstGuest);
        }
        return dummyGuests;
    }

    public static void writeGUESTTestData(int numLines) {
        int random = 0; // (int)(Math.random() * 50 + 1);

        guestList = generateDummyGuestData(numLines);

        guestWrittenList = guestList;
        Database.writeGUEST_Data();

    }

    private static ArrayList<LazyRateModel> generateDummyRateData(int numRates) {
        ArrayList<LazyRateModel> dummyRates = new ArrayList<>();

        for (int i = 0; i < numRates; i++) {
            LazyRateModel tstRate = new LazyRateModel();

            tstRate.setRateDate(getTestDate());
            tstRate.setBaseRate(getRand(0, 255));

            dummyRates.add(tstRate);
        }
        return dummyRates;
    }

    public static void writeRATETestData(int numLines) {
        int random = 0; // (int)(Math.random() * 50 + 1);

        rateList = generateDummyRateData(numLines);

        rateWrittenList = rateList;
        Database.writeRATE_Data();

    }

    public static void makeMyFolder(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public static ArrayList<String> getPathsInDirectory(String path) {
        ArrayList<String> pathsFound = new ArrayList<>();

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                    pathsFound.add(listOfFiles[i].getName());
                }
            }
        } else {
            pathsFound = null;
        }

        return pathsFound;
    }

    private static void writeToReadRsvTest(int numLines) {
        ArrayList<LazyRsvModel> rsvReadList = new ArrayList<>();
        // Generate List -> csv
        // store list 
        //read in Compare
        rsvWrittenList = new ArrayList();
        Database._RUNMODE = Database.OperationalMode.DEBUG_DUMMYTESTMODE;
        try {
            writeRSVTestData(numLines);
        } catch (Exception e) {
            _WRITE_ERROR = ModelInstance.RSVMODEL;
        }

        if (_WRITE_ERROR == ModelInstance.NULLMODEL) {
            rsvReadList = Database.getRSV_Data();
            boolean readingError = false;
            readingError = writeRsvComp_Data(rsvReadList);
            if (readingError) {
                _READ_ERROR = ModelInstance.RSVMODEL;
            }
            //MsgBox((rsvWrittenList == rsvReadList) + "");
        }

        Database._RUNMODE = Database.OperationalMode.NORMAL;
    }

    private static void writeToReadRateTest(int numLines) {
        ArrayList<LazyRateModel> rateReadList = new ArrayList<>();
        // Generate List -> csv
        // store list 
        //read in Compare
        rsvWrittenList = new ArrayList();
        Database._RUNMODE = Database.OperationalMode.DEBUG_DUMMYTESTMODE;
        try {
            writeRATETestData(numLines);
        } catch (Exception e) {
            _WRITE_ERROR = ModelInstance.RATEMODEL;
        }

        if (_WRITE_ERROR == ModelInstance.NULLMODEL) {
            rateReadList = Database.getRATE_Data();
            boolean readingError = false;
            readingError = writeRateComp_Data(rateReadList);
            if (readingError) {
                _READ_ERROR = ModelInstance.RATEMODEL;
            }
        }

        Database._RUNMODE = Database.OperationalMode.NORMAL;
    }

    private static void writeToReadBllchrgTest(int numLines) {
        ArrayList<LazyBllchrgModel> billReadList = new ArrayList<>();
        // Generate List -> csv
        // store list 
        //read in Compare
        billWrittenList = new ArrayList();
        Database._RUNMODE = Database.OperationalMode.DEBUG_DUMMYTESTMODE;
        try {
            writeBllchrgTestData(numLines);
        } catch (Exception e) {
            _WRITE_ERROR = ModelInstance.BLLCHRGMODEL;
        }

        if (_WRITE_ERROR == ModelInstance.NULLMODEL) {
            billReadList = Database.getBllchrg_Data();
            boolean readingError = false;
            readingError = writeBllchrgComp_Data(billReadList);
            if (readingError) {
                _READ_ERROR = ModelInstance.BLLCHRGMODEL;
            }
        }

        Database._RUNMODE = Database.OperationalMode.NORMAL;
    }

    private static void writeToReadGuestTest(int numLines) {
        ArrayList<LazyGuestModel> guestReadList = new ArrayList<>();
        // Generate List -> csv
        // store list 
        //read in Compare
        guestWrittenList = new ArrayList();
        Database._RUNMODE = Database.OperationalMode.DEBUG_DUMMYTESTMODE;
        try {
            writeGUESTTestData(numLines);
        } catch (Exception e) {
            _WRITE_ERROR = ModelInstance.GUESTMODEL;
        }

        if (_WRITE_ERROR == ModelInstance.NULLMODEL) {
            guestReadList = Database.getGUEST_Data();
            boolean readingError = false;
            readingError = writeGuestComp_Data(guestReadList);
            if (readingError) {
                _READ_ERROR = ModelInstance.GUESTMODEL;
            }
        }

        Database._RUNMODE = Database.OperationalMode.NORMAL;
    }

    //ReadWrite Test
    public static String writeToReadTest(int numLines) {
        String status = "COMPLETE";
        _WRITE_ERROR = ModelInstance.NULLMODEL;
        _READ_ERROR = ModelInstance.NULLMODEL;
        writeToReadRsvTest(numLines);

        if ((_WRITE_ERROR == _READ_ERROR)
                && ModelInstance.NULLMODEL == _WRITE_ERROR) {
            writeToReadRateTest(numLines);
        }

        if ((_WRITE_ERROR == _READ_ERROR)
                && ModelInstance.NULLMODEL == _WRITE_ERROR) {
            writeToReadBllchrgTest(numLines);
        }

        if ((_WRITE_ERROR == _READ_ERROR)
                && ModelInstance.NULLMODEL == _WRITE_ERROR) {
            writeToReadGuestTest(numLines);
        }

        switch (_WRITE_ERROR) {
            case RSVMODEL:
                status = "ERROR: Write RSVModel";
                break;
            case RATEMODEL:
                status = "ERROR: Write RATEModel";
                break;
            case BLLCHRGMODEL:
                status = "ERROR: Write BLLCHRGModel";
                break;
            case GUESTMODEL:
                status = "ERROR: Write GUESTModel";
                break;
            default:
                status = "COMPLETE";
                break;
        }

        switch (_READ_ERROR) {
            case RSVMODEL:
                status = "ERROR: Write RSVModel";
                break;
            case RATEMODEL:
                status = "ERROR: Write RATEModel";
                break;
            case BLLCHRGMODEL:
                status = "ERROR: Write BLLCHRGModel";
                break;
            case GUESTMODEL:
                status = "ERROR: Write GUESTModel";
                break;
            default:
                break;
        }

        return status;
    }

    private static String testRsvType(RsvType rsvType1, RsvType rsvType2) {
        String input1 = "";
        String input2 = "";

        switch (rsvType1) {
            case PREPAID:
                input1 = "prepaid";
                break;
            case SIXTYADV:
                input1 = "sixtyadv";
                break;
            case CONVENTIONAL:
                input1 = "conv";
                break;
            case INCENTIVE:
                input1 = "incent";
                break;
            default:
                break;
        }

        switch (rsvType2) {
            case PREPAID:
                input2 = "prepaid";
                break;
            case SIXTYADV:
                input2 = "sixtyadv";
                break;
            case CONVENTIONAL:
                input2 = "conv";
                break;
            case INCENTIVE:
                input2 = "incent";
                break;
            default:
                break;
        }

        return ("1:" + input1 + " " + "2" + input2);
    }

    //Test Comp
    protected static boolean writeRsvComp_Data(ArrayList<LazyRsvModel> rsvReadList) {
        FileWriter fileWriter = null;
        ArrayList<String> testInfo = new ArrayList<>();
        int testIteration = 0;
        String path = "";
        boolean readingError = false;

        final String PARENTFOLDER = "TestData";
        final String CHILDFOLDER = "RSV_TestData";
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
        path += "\\Log.csv";

        if (rsvWrittenList.size() != rsvReadList.size()) {
           // MsgBox(rsvWrittenList.size() + " " + rsvReadList.size());
        }
        try {
            Files.deleteIfExists(Paths.get(path));

            fileWriter = new FileWriter(path);
            // rsvWrittenList.get(0).getDateArrive();
            // fileWriter.write(rsvWrittenList);

            fileWriter.write("WRITE=READ,DateArrive,DateDepart,DatePaid,");
            fileWriter.write("RsvType,RoomID,GuestID,IsNoShow,");
            fileWriter.write("IsPaid,IsConcluded,\n");

            for (int i = 0; i < rsvWrittenList.size(); i++) {
                boolean isEqual = false;

                isEqual = rsvWrittenList.get(i).getDateArrive().equals(rsvReadList.get(i).getDateArrive())
                        && rsvWrittenList.get(i).getDateDepart().equals(rsvReadList.get(i).getDateDepart())
                        && rsvWrittenList.get(i).getDatePaid().equals(rsvReadList.get(i).getDatePaid())
                        && (rsvWrittenList.get(i).getRsvType() == rsvReadList.get(i).getRsvType())
                        && (rsvWrittenList.get(i).getRoom() == rsvReadList.get(i).getRoom())
                        && (rsvWrittenList.get(i).getGuest() == rsvReadList.get(i).getGuest())
                        && (rsvWrittenList.get(i).isIsNoShow() == rsvReadList.get(i).isIsNoShow())
                        && (rsvWrittenList.get(i).isIsPaid() == rsvReadList.get(i).isIsPaid())
                        && (rsvWrittenList.get(i).isIsConcluded() == rsvReadList.get(i).isIsConcluded());

                if (!isEqual) {
                    readingError = true;
                }

                fileWriter.write((isEqual) + ","
                        + rsvWrittenList.get(i).getDateArrive().equals(rsvReadList.get(i).getDateArrive()) + ","
                        + rsvWrittenList.get(i).getDateDepart().equals(rsvReadList.get(i).getDateDepart()) + ","
                        + rsvWrittenList.get(i).getDatePaid().equals(rsvReadList.get(i).getDatePaid()) + ","
                        + (rsvWrittenList.get(i).getRsvType() == rsvReadList.get(i).getRsvType())
                        + "," + (rsvWrittenList.get(i).getRoom() == rsvReadList.get(i).getRoom())
                        + "," + (rsvWrittenList.get(i).getGuest() == rsvReadList.get(i).getGuest())
                        + "," + (rsvWrittenList.get(i).isIsNoShow() == rsvReadList.get(i).isIsNoShow())
                        + "," + (rsvWrittenList.get(i).isIsPaid() == rsvReadList.get(i).isIsPaid())
                        + "," + (rsvWrittenList.get(i).isIsConcluded() == rsvReadList.get(i).isIsConcluded())
                        + ",\n");

            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Size of List Written != Size of List Read In");
            readingError = true;
            //MsgBox("Help Mr. Robinson!!!");
        }
        return readingError;
    }

    protected static boolean writeRateComp_Data(ArrayList<LazyRateModel> rateReadList) {
        FileWriter fileWriter = null;
        ArrayList<String> testInfo = new ArrayList<>();
        int testIteration = 0;
        String path = "";
        boolean readingError = false;

        final String PARENTFOLDER = "TestData";
        final String CHILDFOLDER = "RATE_TestData";
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
        path += "\\Log.csv";

        try {
            Files.deleteIfExists(Paths.get(path));

            fileWriter = new FileWriter(path);
            // rsvWrittenList.get(0).getDateArrive();
            // fileWriter.write(rsvWrittenList);

            fileWriter.write("WRITE=READ,RateDate,BaseRate,\n");

            for (int i = 0; i < rateWrittenList.size(); i++) {
                boolean isEqual = false;

                isEqual = (rateWrittenList.get(i).getRateDate().equals(rateReadList.get(i).getRateDate())
                        && (rateWrittenList.get(i).getBaseRate() == rateReadList.get(i).getBaseRate()));

                if (!isEqual) {
                    readingError = true;
                }

                fileWriter.write((isEqual) + ","
                        + (rateWrittenList.get(i).getRateDate().equals(rateReadList.get(i).getRateDate()) + ","
                        + (rateWrittenList.get(i).getBaseRate() == rateReadList.get(i).getBaseRate())) + ",\n");

            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Size of List Written != Size of List Read In");
            readingError = true;
            //MsgBox("Help Mr. Robinson!!!");
        }
        return readingError;
    }

    protected static boolean writeBllchrgComp_Data(ArrayList<LazyBllchrgModel> billReadList) {
        FileWriter fileWriter = null;
        ArrayList<String> testInfo = new ArrayList<>();
        int testIteration = 0;
        String path = "";
        boolean readingError = false;

        final String PARENTFOLDER = "TestData";
        final String CHILDFOLDER = "BLLCHRG_TestData";
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
        path += "\\Log.csv";

        try {
            Files.deleteIfExists(Paths.get(path));

            fileWriter = new FileWriter(path);
            // rsvWrittenList.get(0).getDateArrive();
            // fileWriter.write(rsvWrittenList);

            fileWriter.write("Write=Read,BllchrgID,Reservation,");
            fileWriter.write("LineDescription,Amount,DateCharged,");
            fileWriter.write("DatePaid,isPaid,\n");

            for (int i = 0; i < billWrittenList.size(); i++) {
                boolean isEqual = false;

                isEqual = (billWrittenList.get(i).getBllchrgID()
                        == billReadList.get(i).getBllchrgID())
                        && (billWrittenList.get(i).getReservation()
                        == billReadList.get(i).getReservation())
                        && billWrittenList.get(i).getLineDescription().equals(
                                billReadList.get(i).getLineDescription())
                        && (billWrittenList.get(i).getAmount()
                        == billReadList.get(i).getAmount())
                        && billWrittenList.get(i).getDateCharged().equals(
                                billReadList.get(i).getDateCharged())
                        && billWrittenList.get(i).getDatePaid().equals(
                                billReadList.get(i).getDatePaid())
                        && (billWrittenList.get(i).isIsPaid()
                        == billReadList.get(i).isIsPaid());

                if (!isEqual) {
                    readingError = true;
                }

                fileWriter.write((isEqual) + ","
                        + (billWrittenList.get(i).getBllchrgID()
                        == billReadList.get(i).getBllchrgID()) + ","
                        + (billWrittenList.get(i).getReservation()
                        == billReadList.get(i).getReservation()) + ","
                        + billWrittenList.get(i).getLineDescription().equals(
                                billReadList.get(i).getLineDescription()) + ","
                        + (billWrittenList.get(i).getAmount()
                        == billReadList.get(i).getAmount()) + ","
                        + billWrittenList.get(i).getDateCharged().equals(
                                billReadList.get(i).getDateCharged()) + ","
                        + billWrittenList.get(i).getDatePaid().equals(
                                billReadList.get(i).getDatePaid()) + ","
                        + (billWrittenList.get(i).isIsPaid()
                        == billReadList.get(i).isIsPaid()) + ",\n");

            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Size of List Written != Size of List Read In");
            readingError = true;
            //MsgBox("Help Mr. Robinson!!!");
        }
        return readingError;
    }

    protected static boolean writeGuestComp_Data(ArrayList<LazyGuestModel> guestReadList) {
        FileWriter fileWriter = null;
        ArrayList<String> testInfo = new ArrayList<>();
        int testIteration = 0;
        String path = "";
        boolean readingError = false;

        final String PARENTFOLDER = "TestData";
        final String CHILDFOLDER = "GUEST_TestData";
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
        path += "\\Log.csv";

        try {
            Files.deleteIfExists(Paths.get(path));

            fileWriter = new FileWriter(path);
            // rsvWrittenList.get(0).getDateArrive();
            // fileWriter.write(rsvWrittenList);

            fileWriter.write("WRITE=READ,GuestID,Name,CCINFO,EMAIL,\n");
            for (int i = 0; i < guestWrittenList.size(); i++) {
                boolean isEqual = false;

                isEqual = (guestWrittenList.get(i).getGuestID() == guestReadList.get(i).getGuestID())
                        && (guestWrittenList.get(i).getName().equals(guestReadList.get(i).getName()))
                        && (guestWrittenList.get(i).getCCInfo().equals(guestReadList.get(i).getCCInfo()))
                        && (guestWrittenList.get(i).getEmail().equals(guestReadList.get(i).getEmail()));

                if (!isEqual) {
                    readingError = true;
                }

                fileWriter.write((isEqual) + ","
                        + (guestWrittenList.get(i).getGuestID() == guestReadList.get(i).getGuestID()) + ","
                        + (guestWrittenList.get(i).getName().equals(guestReadList.get(i).getName())) + ","
                        + (guestWrittenList.get(i).getCCInfo().equals(guestReadList.get(i).getCCInfo())) + ","
                        + (guestWrittenList.get(i).getEmail().equals(guestReadList.get(i).getEmail())) + ",\n");

            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Size of List Written != Size of List Read In");
            readingError = true;
            //MsgBox("Help Mr. Robinson!!!");
        }
        return readingError;
    }

}
