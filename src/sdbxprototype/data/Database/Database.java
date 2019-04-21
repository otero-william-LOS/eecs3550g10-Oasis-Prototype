/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import static Database.Database.DatabaseType.BILLINGDATABASE;
import static Database.Database.DatabaseType.GUESTDATABASE;
import static Database.Database.DatabaseType.RATEDATABASE;
import static Database.Database.DatabaseType.RESERVATIONDATABASE;
import static Database.Database.existanceCheck;
import static Database.TestDatabase.makeMyFolder;



import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

import Models.RsvType.*;
import static Models.RsvType.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author cmason12
 */
public class Database {

    protected static ArrayList<LazyRsvModel> rsvList
            = new ArrayList<LazyRsvModel>();
    protected static ArrayList<LazyRateModel> rateList
            = new ArrayList<LazyRateModel>();
    protected static ArrayList<LazyBllchrgModel> billList
            = new ArrayList<LazyBllchrgModel>();
    protected static ArrayList<LazyGuestModel> guestList
            = new ArrayList<LazyGuestModel>();

    private final static String RSVPATH = "Database\\rsvData.csv";
    private final static String RSVTESTPATH = "TestDatabase\\rsvTestData.csv";
    private final static String RSVENTITYTESTPATH = "EntityTesting\\rsvTestData.csv";
    private final static String RSVENTITYHARDTESTPATH = "EntityHardTest\\rsvTestData.csv";

    private final static String RATEPATH = "Database\\rateData.csv";
    private final static String RATETESTPATH = "TestDatabase\\rateTestData.csv";
    private final static String RATEENTITYTESTPATH = "EntityTesting\\rateTestData.csv";
    private final static String RATEENTITYHARDTESTPATH = "EntityHardTest\\rateTestData.csv";

    private final static String BILLINGPATH = "Database\\billingData.csv";
    private final static String BILLINGTESTPATH = "TestDatabase\\billingTestData.csv";
    private final static String BILLINGENTITYTESTPATH = "EntityTesting\\billingTestData.csv";
    private final static String BILLINGENTITYHARDTESTPATH = "EntityHardTest\\billingTestData.csv";

    private final static String GUESTPATH = "Database\\guestData.csv";
    private final static String GUESTTESTPATH = "TestDatabase\\guestTestData.csv";
    private final static String GUESTENTITYTESTPATH = "EntityTesting\\guestTestData.csv";
    private final static String GUESTENTITYHARDTESTPATH = "EntityHardTest\\guestTestData.csv";

    public enum DatabaseType {
        RESERVATIONDATABASE,
        RATEDATABASE,
        BILLINGDATABASE,
        GUESTDATABASE;
    }

    public enum OperationalMode {
        NORMAL,
        DEBUG_DUMMYTESTMODE,
        DEBUG_ENTITYTEST_MODE,
        DEBUG_ENTITYHARDTEST_MODE
    }
    protected static OperationalMode _RUNMODE = OperationalMode.NORMAL;

    //-------------------Background Tasks-----------------------------------//
    protected static String getPath(DatabaseType type) {
        String path = "";
        if (null != type) {
            switch (type) {
                case RESERVATIONDATABASE:
                    // 10 + 6(Billing) Attributes
                    if (_RUNMODE == OperationalMode.NORMAL) {
                        makeMyFolder("Database");
                        path = RSVPATH;
                    } else if (_RUNMODE == OperationalMode.DEBUG_DUMMYTESTMODE) {
                        makeMyFolder("TestDatabase");
                        path = RSVTESTPATH;
                    } else if (_RUNMODE == OperationalMode.DEBUG_ENTITYTEST_MODE) {
                        makeMyFolder("EntityTesting");
                        path = RSVENTITYTESTPATH;
                    } else {
                        makeMyFolder("EntityHardTest");
                        path = RSVENTITYHARDTESTPATH;
                    }
                    break;
                case RATEDATABASE:
                    // 3 Attributes
                    if (_RUNMODE == OperationalMode.NORMAL) {
                        path = RATEPATH;
                    } else if (_RUNMODE == OperationalMode.DEBUG_DUMMYTESTMODE) {
                        makeMyFolder("Dummy_Data");
                        path = RATETESTPATH;
                    } else if (_RUNMODE == OperationalMode.DEBUG_ENTITYTEST_MODE) {
                        makeMyFolder("EntityTesting");
                        path = RATEENTITYTESTPATH;
                    } else {
                        makeMyFolder("EntityHardTest");
                        path = RATEENTITYHARDTESTPATH;
                    }
                    break;
                case BILLINGDATABASE:
                    // 7 Attributes
                    if (_RUNMODE == OperationalMode.NORMAL) {
                        path = BILLINGPATH;
                    } else if (_RUNMODE == OperationalMode.DEBUG_DUMMYTESTMODE) {
                        makeMyFolder("Dummy_Data");
                        path = BILLINGTESTPATH;
                    } else if (_RUNMODE == OperationalMode.DEBUG_ENTITYTEST_MODE) {
                        makeMyFolder("EntityTesting");
                        path = BILLINGENTITYTESTPATH;
                    } else {
                        makeMyFolder("EntityHardTest");
                        path = BILLINGENTITYHARDTESTPATH;
                    }
                    break;
                case GUESTDATABASE:
                    // 3 + list.size() Attributes
                    if (_RUNMODE == OperationalMode.NORMAL) {
                        path = GUESTPATH;
                    } else if (_RUNMODE == OperationalMode.DEBUG_DUMMYTESTMODE) {
                        makeMyFolder("Dummy_Data");
                        path = GUESTTESTPATH;
                    } else if (_RUNMODE == OperationalMode.DEBUG_ENTITYTEST_MODE) {
                        makeMyFolder("EntityTesting");
                        path = GUESTENTITYTESTPATH;
                    } else {
                        makeMyFolder("EntityHardTest");
                        path = GUESTENTITYHARDTESTPATH;
                    }
                    break;
                default:
                    break;
            }
        }
        return path;
    }

    protected static void existanceCheck(DatabaseType type) {
        try {
            String path = getPath(type);

            File fileCheck = new File(path);
            if (!fileCheck.exists()) {
                FileWriter writer = new FileWriter(fileCheck);
                writer.close();
            }
        } catch (Exception e) {
            //  MsgBox("Error");
        }
    }

    protected static void populateData(DatabaseType type) {
        try {

            //Clear each list and start from scratch //
            rsvList = new ArrayList<>();
            rateList = new ArrayList<>();
            billList = new ArrayList<>();
            guestList = new ArrayList<>();

            Scanner scanner = new Scanner(new File(getPath(type)));

            //gets rid of the first line 
            if (scanner.hasNext()) {
                scanner.next();
            }

            if (type == (DatabaseType.RESERVATIONDATABASE)) {
                // 10 + 6(Billing) Attributes
               readRSV_Data(scanner);
            } else if (type == (DatabaseType.RATEDATABASE)) {
                // 3 Attributes
                readRATE_Data(scanner);
            } else if (type == (DatabaseType.BILLINGDATABASE)) {
                // 7 Attributes
                readBllchrg_Data(scanner);

            } else if (type == (DatabaseType.GUESTDATABASE)) {
                readGUEST_Data(scanner);
            }

            scanner.close();
        } catch (Exception e) {
           // System.out.println(rsvList.size());
            //System.out.println(e);
        }

    }
    //-------------------<END> Background Tasks-----------------------------//

    //-------------------Tools -----------------------------//
    protected static Date stringToDate(String date) {
       return Date.valueOf(date);
    
    }

    protected static int getRand(int min, int max) {
        Random rand = new Random();
        return (rand.nextInt((max - min) + 1) + min);
    }

    protected static Date getTestDate() {

        LocalDate localDate = LocalDate.of(getRand(1997, 2019),
                getRand(1, 12), getRand(1, 23));
        Date sqldate = Date.valueOf(localDate);

        return sqldate;//sqldate;
    }

    //-------------------<END> Tools -----------------------------//
    //-------------------Get Data-------------------------------//
    public static ArrayList<LazyRsvModel> getRSV_Data() {
        existanceCheck(RESERVATIONDATABASE);
        populateData(RESERVATIONDATABASE);
        return rsvList;
    }

    public static ArrayList<LazyRateModel> getRATE_Data() {
        existanceCheck(RATEDATABASE);
        populateData(RATEDATABASE);
        return rateList;
    }

    public static ArrayList<LazyGuestModel> getGUEST_Data() {
        existanceCheck(GUESTDATABASE);
        populateData(GUESTDATABASE);
        return guestList;
    }

    public static ArrayList<LazyBllchrgModel> getBllchrg_Data() {
        existanceCheck(BILLINGDATABASE);
        populateData(BILLINGDATABASE);
        return billList;
    }

//-------------------Append Data-------------------------------//
    public static void appendRSV_Data(
            LazyRsvModel rsv) {
        existanceCheck(RESERVATIONDATABASE);
        populateData(RESERVATIONDATABASE);
        rsvList.add(rsv);
        writeRSV_Data();
    }

    public static void appendRATE_Data(
            LazyRateModel rate) {
        existanceCheck(RATEDATABASE);
        populateData(RATEDATABASE);
        rateList.add(rate);
        writeRATE_Data();
    }

    public static void appendGUEST_Data(
            LazyGuestModel guest) {
        existanceCheck(GUESTDATABASE);
        populateData(GUESTDATABASE);
        guestList.add(guest);
        writeGUEST_Data();
    }

    public static void appendBllchrg_Data(
            LazyBllchrgModel bill) {
        existanceCheck(BILLINGDATABASE);
        populateData(BILLINGDATABASE);
        billList.add(bill);
        writeBllchrg_Data();
    }

//-------------------<End> Append Data-------------------------------//
    //-------------------Modify Data-------------------------------//
    public static void modifyRSV_Data(
            LazyRsvModel rsv) {
        existanceCheck(RESERVATIONDATABASE);
        populateData(RESERVATIONDATABASE);

        
        System.out.println("RsvList: " + rsvList.size());
        for (int i = 0; i < rsvList.size(); i++) {
            if (rsvList.get(i).getRsvID() == rsv.getRsvID()) {
                rsvList.get(i).setDateArrive(rsv.getDateArrive());
                rsvList.get(i).setDateDepart(rsv.getDateDepart());
                rsvList.get(i).setDatePaid(rsv.getDatePaid());
                rsvList.get(i).setRsvType(rsv.getRsvType());
                rsvList.get(i).setRoom(rsv.getRoom());
                rsvList.get(i).setGuest(rsv.getGuest());
                rsvList.get(i).setIsNoShow(rsv.isIsNoShow());
                rsvList.get(i).setIsPaid(rsv.isIsPaid());
                rsvList.get(i).setIsConcluded(rsv.isIsConcluded());
            }
        }
        
        
       // System.out.println(rsvList.size() +"");
        writeRSV_Data();
    }

    public static void modifyRATE_Data(
            LazyRateModel rate) {
        existanceCheck(RATEDATABASE);
        populateData(RATEDATABASE);

        for (int i = 0; i < rateList.size(); i++) {
            if (rateList.get(i).getRateDate().equals(rate.getRateDate())) {
                rateList.get(i).setBaseRate(rate.getBaseRate());
            }
        }
        writeRATE_Data();
    }

    public static void modifyGUEST_Data(
            LazyGuestModel guest) {
        existanceCheck(GUESTDATABASE);
        populateData(GUESTDATABASE);

        for (int i = 0; i < guestList.size(); i++) {
            if (guestList.get(i).getGuestID() == guest.getGuestID()) {
                guestList.get(i).setName(guest.getName());
                guestList.get(i).setCCInfo(guest.getCCInfo());
                guestList.get(i).setEmail(guest.getEmail());
            }
        }
        writeGUEST_Data();
    }

    public static void modifyBllchrg_Data(
            LazyBllchrgModel guest) {
        existanceCheck(BILLINGDATABASE);
        populateData(BILLINGDATABASE);

        for (int i = 0; i < billList.size(); i++) {
            if (billList.get(i).getBllchrgID() == guest.getBllchrgID()) {
                billList.get(i).setReservation(guest.getReservation());
                billList.get(i).setLineDescription(guest.getLineDescription());
                billList.get(i).setAmount(guest.getAmount());
                billList.get(i).setAmount(guest.getAmount());
                billList.get(i).setDateCharged(guest.getDateCharged());
                billList.get(i).setDatePaid(guest.getDatePaid());
                billList.get(i).setIsPaid(guest.isIsPaid());
            }
        }
        writeBllchrg_Data();
    }
    //-------------------<END> Modify Data-------------------------------//

//-------------------Read Data-------------------------------//
        public static void readRSV_Data(Scanner scanner) {
        //16 Attributes
        int index = 0;

        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String temp;
            temp = scanner.next();

            LazyRsvModel rsv = new LazyRsvModel();
            while (temp.contains(",")) {

                String attr = "";
                for (int i = 0; i < temp.length(); i++) {
                    if (temp.charAt(i) == ',') {
                        attr = temp.substring(0, i);
                        temp = temp.substring(i + 1, temp.length());
                        i = temp.length() + 1;

                    }

                }

                //--------Determine what attribute you are on---------//
                switch (index) {

                    case 0:
                        rsv.setRsvID(Integer.parseInt(attr));
                        break;
                    case 1:
                        rsv.setDateArrive(stringToDate(attr));

                    case 2:

                        rsv.setDateDepart(stringToDate(attr));
                        break;
                    case 3:
                        //MsgBox("DatePaid:" + attr);
                        rsv.setDatePaid(stringToDate(attr));
                        break;
                    case 4:

                        if (attr.equals("PREPAID")) {
                            rsv.setRsvType(PREPAID);
                        } else if (attr.equals("SIXTYADV")) {
                            rsv.setRsvType(SIXTYADV);
                        } else if (attr.equals("CONVENTIONAL")) {
                            rsv.setRsvType(CONVENTIONAL);
                        } else if (attr.equals("INCENTIVE")) {
                            rsv.setRsvType(INCENTIVE);
                        } else {
                            rsv.setRsvType(null);
                        }

                        break;
                    case 5:
                        
                        rsv.setRoom(Short.parseShort(attr));
                        
                        break;
                    case 6:
                        rsv.setGuest(Integer.parseInt(attr));
                        break;
                    case 7:
                        if (attr.equals("TRUE")) {
                            rsv.setIsNoShow(true);
                        } else {
                            rsv.setIsNoShow(false);
                        }

                        break;
                    case 8:
                        if (attr.equals("TRUE")) {
                            rsv.setIsPaid(true);
                        } else {
                            rsv.setIsPaid(false);
                        }

                        break;
                    case 9:
                        if (attr.equals("TRUE")) {
                            rsv.setIsConcluded(true);
                        } else {
                            rsv.setIsConcluded(false);
                        }

                        break;

                    default:
                        index = -1;
                        break;
                }

                index += 1;
                if (index == 10) {
                    index = 0;
                }
            }
            rsvList.add(rsv);
        }
    }

    public static void readRATE_Data(Scanner scanner) {
        //16 Attributes
        int index = 0;
        while (scanner.hasNext()) {
            String temp;
            temp = scanner.next();

            LazyRateModel rate = new LazyRateModel();
            while (temp.contains(",")) {

                String attr = "";
                for (int i = 0; i < temp.length(); i++) {
                    if (temp.charAt(i) == ',') {
                        attr = temp.substring(0, i);
                        //Cut out attr from string and skip comma//
                        // MsgBox(attr);

                        temp = temp.substring(i + 1, temp.length());
                        i = temp.length() + 1;

                    }

                }

                //--------Determine what attribute you are on---------//
                switch (index) {

                    case 0:
                        rate.setRateDate(stringToDate(attr));
                        break;
                    case 1:
                        rate.setBaseRate(Double.parseDouble(attr));
                        break;

                    default:
                        break;
                }

                index += 1;
                if (index == 2) {
                    temp = "";
                    index = 0;
                }
            }
            rateList.add(rate);
        }
    }

    public static void readGUEST_Data(Scanner scanner) {
        //16 Attributes
        int index = 0;
        while (scanner.hasNext()) {
            String temp;
            temp = scanner.next();

            LazyGuestModel guest = new LazyGuestModel();
            while (temp.contains(",")) {

                String attr = "";
                for (int i = 0; i < temp.length(); i++) {
                    if (temp.charAt(i) == ',') {
                        attr = temp.substring(0, i);
                        //Cut out attr from string and skip comma//
                        // MsgBox(attr);

                        temp = temp.substring(i + 1, temp.length());
                        i = temp.length() + 1;

                    }

                }

                //--------Determine what attribute you are on---------//
                switch (index) {
                    case 0:
                        guest.setGuestID(Integer.parseInt(attr));
                        break;
                    case 1:
                        guest.setName(attr);
                        break;
                    case 2:
                        guest.setCCInfo(attr);
                        break;
                    case 3:
                        guest.setEmail(attr);
                        break;
                    default:
                        break;
                }

                index += 1;
                if (index == 4) {
                    temp = "";
                    index = 0;
                }
            }
            guestList.add(guest);
        }
    }

    public static void readBllchrg_Data(Scanner scanner) {
        int index = 0;

        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String temp;
            temp = scanner.next();

            LazyBllchrgModel bill = new LazyBllchrgModel();
            // MsgBox(temp);
            while (temp.contains(",")) {

                String attr = "";
                for (int i = 0; i < temp.length(); i++) {
                    if (temp.charAt(i) == ',') {
                        attr = temp.substring(0, i);

                        temp = temp.substring(i + 1, temp.length());
                        i = temp.length() + 1;

                    }

                }

                //--------Determine what attribute you are on---------//
                switch (index) {
                    case 0:
                        //MsgBox("BllchrgID: " + attr);
                        bill.setBllchrgID(Integer.parseInt(attr));
                        break;
                    case 1:
                        // MsgBox("Reservation: " + attr);
                        bill.setReservation(Integer.parseInt(attr));
                        break;
                    case 2:
                        //MsgBox("LD: " + attr);
                        bill.setLineDescription(attr);
                        break;
                    case 3:
                        // MsgBox("Amount: " + attr);
                        bill.setAmount(Double.parseDouble(attr));
                        break;
                    case 4:
                        //   MsgBox("DateCharged: " + attr);
                        bill.setDateCharged(stringToDate(attr));
                        break;
                    case 5:
                        //    MsgBox("DatePaid: " + attr);
                        bill.setDatePaid(stringToDate(attr));
                        break;
                    case 6:
                        if (attr.equals("TRUE")) {
                            bill.setIsPaid(true);
                        } else {
                            bill.setIsPaid(false);
                        }
                        break;
                    default:
                        break;
                }

                index += 1;
                if (index == 7) {
                    index = 0;
                    temp = "";
                }

            }
            billList.add(bill);
        }
    }
//-------------------<END> Read Data-------------------------------//

    //-------------------Write Data-------------------------------//
    protected static void writeRSV_Data() {
        
         FileWriter fileWriter = null;
        File fileToDelete = new File((getPath(RESERVATIONDATABASE)));
        //Path fileToDeletePath = Paths.get(("BllchrgTest.csv"));
        try {
            //Files.deleteIfExists(fileToDeletePath);
            fileToDelete.delete();
            
            fileWriter = new FileWriter(getPath(RESERVATIONDATABASE));
            

            //  fileWriter = new FileWriter("writeCheck.csv");
            StringBuilder toWrite = new StringBuilder();
            toWrite.append("RsvID,DateArrive,DateDepart,DatePaid,");
            toWrite.append("RsvType,RoomID,GuestID,");
            toWrite.append("IsNoShow,IsPaid,IsConcluded").append(",\n");
            fileWriter.write(toWrite.toString());

            for (int i = 0; i < rsvList.size(); i++) {
                toWrite = new StringBuilder();

                //toWrite += Attr1 + ", " + Att2 + "," +....+"Attr16 + "\n";
                toWrite.append(rsvList.get(i).getRsvID()).append(",");
                toWrite.append(rsvList.get(i).getDateArrive()
                        .toString()).append(",");
                toWrite.append(rsvList.get(i).getDateDepart()
                        .toString()).append(",");
                toWrite.append(rsvList.get(i).getDatePaid()
                        .toString()).append(",");

                if (rsvList.get(i).getRsvType() == PREPAID) {
                    toWrite.append("PREPAID,");
                } else if (rsvList.get(i).getRsvType() == SIXTYADV) {
                    toWrite.append("SIXTYADV,");
                } else if (rsvList.get(i).getRsvType() == CONVENTIONAL) {
                    toWrite.append("CONVENTIONAL,");
                } else if (rsvList.get(i).getRsvType() == INCENTIVE) {
                    toWrite.append("INCENTIVE,");
                } else {
                    toWrite.append("NULL,");
                }

                toWrite.append(rsvList.get(i).getRoom()).append(",");
                toWrite.append(rsvList.get(i).getGuest()).append(",");

                if (rsvList.get(i).isIsNoShow()) {
                    toWrite.append("TRUE,");
                } else {
                    toWrite.append("FALSE,");
                }

                if (rsvList.get(i).isIsPaid()) {
                    toWrite.append("TRUE,");
                } else {
                    toWrite.append("FALSE,");
                }

                if (rsvList.get(i).isIsConcluded()) {
                    toWrite.append("TRUE,").append("\n");
                } else {
                    toWrite.append("FALSE,").append("\n");
                }

                fileWriter.write(toWrite.toString());
            }

            fileWriter.close();
        } catch (Exception e) {
           // System.out.println("RSV Write Error!\n" + e);
        }

    }

    protected static void writeRATE_Data() {
        FileWriter fileWriter = null;
        Path fileToDeletePath = Paths.get(getPath(RATEDATABASE));
        //Path fileToDeletePath = Paths.get(("writeRate.csv));
        try {
            Files.deleteIfExists(fileToDeletePath);

            fileWriter = new FileWriter(getPath(RATEDATABASE));
            //fileWriter = new FileWriter(("writeRate.csv"));
            StringBuilder toWrite = new StringBuilder();
            toWrite.append("RateDate,BaseRate,\n");
            fileWriter.write(toWrite.toString());

            for (int i = 0; i < rateList.size(); i++) {
                toWrite = new StringBuilder();

                //toWrite += Attr1 + ", " + Att2 + "," +....+"Attr16 + "\n";
                toWrite.append(rateList.get(i).getRateDate()).append(",");
                toWrite.append(rateList.get(i).getBaseRate()).append(",\n");

                fileWriter.write(toWrite.toString());
            }

            fileWriter.close();
        } catch (Exception e) {
        }

    }

    protected static void writeGUEST_Data() {
        FileWriter fileWriter = null;
        Path fileToDeletePath = Paths.get(getPath(GUESTDATABASE));
        // Path fileToDeletePath = Paths.get(("writeGuest.csv"));
        try {
            Files.deleteIfExists(fileToDeletePath);

            fileWriter = new FileWriter(getPath(GUESTDATABASE));
            //fileWriter = new FileWriter(("writeGuest.csv"));

            StringBuilder toWrite = new StringBuilder();
            toWrite.append("GuestID,Name,CCINFO,EMAIL,\n");
            fileWriter.write(toWrite.toString());

            for (int i = 0; i < guestList.size(); i++) {
                toWrite = new StringBuilder();

                //toWrite += Attr1 + ", " + Att2 + "," +....+"Attr16 + "\n";
                toWrite.append(guestList.get(i).getGuestID()).append(",");
                toWrite.append(guestList.get(i).getName()).append(",");
                toWrite.append(guestList.get(i).getCCInfo()).append(",");
                toWrite.append(guestList.get(i).getEmail()).append(",\n");

                fileWriter.write(toWrite.toString());
            }

            fileWriter.close();
        } catch (Exception e) {
        }

    }

    protected static void writeBllchrg_Data() {
        FileWriter fileWriter = null;
        File fileToDelete = new File((getPath(BILLINGDATABASE)));
        //Path fileToDeletePath = Paths.get(("BllchrgTest.csv"));
        try {
            //Files.deleteIfExists(fileToDeletePath);
            fileToDelete.delete();
            
            fileWriter = new FileWriter(getPath(BILLINGDATABASE));
          
            
            //fileWriter = new FileWriter(("BllchrgTest.csv"));
            StringBuilder toWrite = new StringBuilder();
            toWrite.append("BllchrgID,Reservation,LineDescription,");
            toWrite.append("Amount,DateCharged,DatePaid,");
            toWrite.append("isPaid,\n");
            fileWriter.write(toWrite.toString());

            for (int i = 0; i < billList.size(); i++) {
                toWrite = new StringBuilder();

                //toWrite += Attr1 + ", " + Att2 + "," +....+"Attr16 + "\n";
                toWrite.append(billList.get(i).getBllchrgID()).append(",");
                toWrite.append(billList.get(i).getReservation()).append(",");
                toWrite.append(billList.get(i).getLineDescription()).
                        append(",");
                toWrite.append(billList.get(i).getAmount()).append(",");
                toWrite.append(billList.get(i).getDateCharged()).append(",");
                toWrite.append(billList.get(i).getDatePaid()).append(",");
                if (billList.get(i).isIsPaid()) {
                    toWrite.append("TRUE").append(",\n");
                } else {
                    toWrite.append("FALSE").append(",\n");
                }

                fileWriter.write(toWrite.toString());
            }

            fileWriter.close();
        } catch (Exception e) {
            
            //System.out.println(e);
        }

    }
//-------------------<END> Write Data-------------------------------//   

}
