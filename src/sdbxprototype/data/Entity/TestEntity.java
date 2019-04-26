/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import Database.*;
import static Database.TestDatabase.makeMyFolder;
import static Entity.BllchrgEntity.requestAll_BllchrgData;
import static Entity.BllchrgEntity.requestAll_LazyBllchrgData;
import static Entity.GuestEntity.requestAll_GuestData;
import static Entity.GuestEntity.requestAll_LazyGuestData;
import static Entity.RateEntity.requestAll_LazyRateData;
import static Entity.RateEntity.requestAll_RATEDATA;
import static Entity.RsvEntity.requestAll_LazyRSVData;
import static Entity.RsvEntity.requestAll_RSVData;

import Models.BllchrgModel;
import Models.GuestModel;
import Models.RateModel;
import Models.RsvModel;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author cmason12
 */
public class TestEntity extends TestDatabase {

    //--------------------Entity Read Soft Test---------------//
    public static void testReadEntity() {
        boolean result = false;
        boolean failedOnce = false;
        Database._RUNMODE = Database.OperationalMode.DEBUG_ENTITYTEST_MODE;

       TestDatabase.writeRSVTestData(2000);
       TestDatabase.writeRATETestData(2000);
       TestDatabase.writeBllchrgTestData(2000);
       TestDatabase.writeGUESTTestData(2000);

        for (int i = 0; i < 20; i++) {
            result = rsvTestLazyToEntity();
            if (result) {
                failedOnce = true;
            }

        }
        //System.out.println("Step1(Passed):" + !failedOnce);
        for (int i = 0; i < 20; i++) {
            result = rateTestLazyToEntity();
            if (result) {
                failedOnce = true;
            }
        }
        //System.out.println("Step2(Passed):" + !failedOnce);
        for (int i = 0; i < 20; i++) {
            result = billTestLazyToEntity();
            if (result) {
                failedOnce = true;
            }
        }
        //.out.println("Step3(Passed):" + !failedOnce);
        for (int i = 0; i < 20; i++) {
            result = guestTestLazyToEntity();
            if (result) {
                failedOnce = true;
            }
        }
        //System.out.println("Step4(Passed):" + !failedOnce);
        
        Database._RUNMODE = Database.OperationalMode.NORMAL;
    }
    //--------------------<END> Entity Read Soft Test---------------//
    
    //------------------Log Methods------------------------------//
    private static boolean rsvTestLazyToEntity() {
        ArrayList<RsvModel> rsvInList = new ArrayList<>();
        ArrayList<LazyRsvModel> baseList = new ArrayList<>();
        rsvInList = requestAll_RSVData();
        baseList = requestAll_LazyRSVData();

        FileWriter fileWriter = null;
        ArrayList<String> testInfo = new ArrayList<>();
        int testIteration = 0;
        String path = "";
        boolean readingError = false;

        final String PARENTFOLDER = "EntityTesting";
        final String CHILDFOLDER = "RSV_TestData";
        makeMyFolder(PARENTFOLDER);
        makeMyFolder(PARENTFOLDER + "\\" + CHILDFOLDER);


        if (testInfo.size() == 0) {
            testIteration = 1;
        } else {
            testIteration
                    = Integer.parseInt(testInfo.get(testInfo.size() - 1)) + 1;
        }
        path = PARENTFOLDER +  "\\" + CHILDFOLDER + "\\" + testIteration;
        makeMyFolder(path);
        path += "\\Log.csv";

        if (rsvInList.size() != baseList.size()) {
            //MsgBox(rsvInList.size() + " " + baseList.size());
        }
        try {
            Files.deleteIfExists(Paths.get(path));

            fileWriter = new FileWriter(path);
            // rsvWrittenList.get(0).getDateArrive();
            // fileWriter.write(rsvWrittenList);

            fileWriter.write("ENTITY=LAZY,DateArrive,DateDepart,DatePaid,");
            fileWriter.write("RsvType,RoomID,GuestID,Bllchrg, IsNoShow,");
            fileWriter.write("IsPaid,IsConcluded,\n");

            for (int i = 0; i < rsvInList.size(); i++) {
                boolean isEqual = false;

                isEqual = rsvInList.get(i).getDateArrive().equals(baseList.get(i).getDateArrive())
                        && rsvInList.get(i).getDateDepart().equals(baseList.get(i).getDateDepart())
                        && rsvInList.get(i).getDatePaid().equals(baseList.get(i).getDatePaid())
                        && (rsvInList.get(i).getRsvType() == baseList.get(i).getRsvType())
                        && (rsvInList.get(i).isIsNoShow() == baseList.get(i).isIsNoShow())
                        && (rsvInList.get(i).isIsPaid() == baseList.get(i).isIsPaid())
                        && (rsvInList.get(i).isIsConcluded() == baseList.get(i).isIsConcluded());

                if (!isEqual) {
                    readingError = true;
                }

                fileWriter.write((isEqual) + ","
                        + rsvInList.get(i).getDateArrive().equals(baseList.get(i).getDateArrive()) + ","
                        + rsvInList.get(i).getDateDepart().equals(baseList.get(i).getDateDepart()) + ","
                        + rsvInList.get(i).getDatePaid().equals(baseList.get(i).getDatePaid()) + ","
                        + (rsvInList.get(i).getRsvType() == baseList.get(i).getRsvType())
                        + "," + null + "," + null + "," + null
                        + "," + (rsvInList.get(i).isIsNoShow() == baseList.get(i).isIsNoShow())
                        + "," + (rsvInList.get(i).isIsPaid() == baseList.get(i).isIsPaid())
                        + "," + (rsvInList.get(i).isIsConcluded() == baseList.get(i).isIsConcluded())
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

    private static boolean rateTestLazyToEntity() {
        ArrayList<RateModel> rateInList = new ArrayList<>();
        ArrayList<LazyRateModel> baseList = new ArrayList<>();
        rateInList = requestAll_RATEDATA();
        baseList = requestAll_LazyRateData();

        FileWriter fileWriter = null;
        ArrayList<String> testInfo = new ArrayList<>();
        int testIteration = 0;
        String path = "";
        boolean readingError = false;

        final String PARENTFOLDER = "EntityTesting";
        final String CHILDFOLDER = "RATE_TestData";
        makeMyFolder(PARENTFOLDER);
        makeMyFolder(PARENTFOLDER + "\\" + CHILDFOLDER);
     

        if (testInfo.size() == 0) {
            testIteration = 1;
        } else {
            testIteration = Integer.parseInt(testInfo.get(testInfo.size() - 1)) + 1;
        }

        path = PARENTFOLDER + "\\" + CHILDFOLDER + "\\" + testIteration;
        makeMyFolder(path);
        path += "\\Log.csv";
        try {
            Files.deleteIfExists(Paths.get(path));

            fileWriter = new FileWriter(path);
            fileWriter.write("ENTITY=LAZY,RateDate,BaseRate,\n");

            for (int i = 0; i < rateInList.size(); i++) {
                boolean isEqual = false;

                isEqual = (rateInList.get(i).getRateDate().equals(baseList.get(i).getRateDate())
                        && (rateInList.get(i).getBaseRate() == baseList.get(i).getBaseRate()));

                if (!isEqual) {
                    readingError = true;
                }

                fileWriter.write((isEqual) + ","
                        + (rateInList.get(i).getRateDate().equals(baseList.get(i).getRateDate()) + ","
                        + (rateInList.get(i).getBaseRate() == baseList.get(i).getBaseRate())) + ",\n");

            }
            fileWriter.close();
        } catch (Exception e) {
           // MsgBox("Crash!!!!");
            System.out.println("Size of List Written != Size of List Read In");
            readingError = true;
        }

        return readingError;
    }

    private static boolean billTestLazyToEntity() {
        ArrayList<BllchrgModel> billInList = new ArrayList<>();
        ArrayList<LazyBllchrgModel> baseList = new ArrayList<>();
        billInList = requestAll_BllchrgData();
        baseList = requestAll_LazyBllchrgData();

        FileWriter fileWriter = null;
        ArrayList<String> testInfo = new ArrayList<>();
        int testIteration = 0;
        String path = "";
        boolean readingError = false;

        final String PARENTFOLDER = "EntityTesting";
        final String CHILDFOLDER = "BLLCHRG_TestData";
        makeMyFolder(PARENTFOLDER);
        makeMyFolder(PARENTFOLDER + "\\" + CHILDFOLDER);
     

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


            fileWriter.write("ENTITY=LAZY,BllchrgID,Reservation,");
            fileWriter.write("LineDescription,Amount,DateCharged,");
            fileWriter.write("DatePaid,isPaid,\n");

            for (int i = 0; i < billInList.size(); i++) {
                boolean isEqual = false;

                isEqual = (billInList.get(i).getBllchrgID()
                        == baseList.get(i).getBllchrgID())
                        && billInList.get(i).getLineDescription().equals(
                                baseList.get(i).getLineDescription())
                        && (billInList.get(i).getAmount()
                        == baseList.get(i).getAmount())
                        && billInList.get(i).getDateCharged().equals(
                                baseList.get(i).getDateCharged())
                        && billInList.get(i).getDatePaid().equals(
                                baseList.get(i).getDatePaid())
                        && (billInList.get(i).isIsPaid()
                        == baseList.get(i).isIsPaid());

                if (!isEqual) {
                    readingError = true;
                }

                fileWriter.write((isEqual) + ","
                        + (billInList.get(i).getBllchrgID()
                        == baseList.get(i).getBllchrgID()) + ","
                        + null + ","
                        + billInList.get(i).getLineDescription().equals(
                                baseList.get(i).getLineDescription()) + ","
                        + (billInList.get(i).getAmount()
                        == baseList.get(i).getAmount()) + ","
                        + billInList.get(i).getDateCharged().equals(
                                baseList.get(i).getDateCharged()) + ","
                        + billInList.get(i).getDatePaid().equals(
                                baseList.get(i).getDatePaid()) + ","
                        + (billInList.get(i).isIsPaid()
                        == baseList.get(i).isIsPaid()) + ",\n");

            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Size of List Written != Size of List Read In");
            readingError = true;
        }
        return readingError;
    }

    private static boolean guestTestLazyToEntity() {
        ArrayList<GuestModel> guestInList = new ArrayList<>();
        ArrayList<LazyGuestModel> baseList = new ArrayList<>();
        guestInList = requestAll_GuestData();
        baseList = requestAll_LazyGuestData();

        FileWriter fileWriter = null;
        ArrayList<String> testInfo = new ArrayList<>();
        int testIteration = 0;
        String path = "";
        boolean readingError = false;

        
        final String PARENTFOLDER = "EntityTesting";
        final String CHILDFOLDER = "GUEST_TestData";
        makeMyFolder(PARENTFOLDER);
        makeMyFolder(PARENTFOLDER + "\\" + CHILDFOLDER);
        

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

            fileWriter.write("WRITE=READ,Guest,Name,CCINFO,EMAIL,\n");
            for (int i = 0; i < guestInList.size(); i++) {
                boolean isEqual = false;
                isEqual = (guestInList.get(i).getGuestID() == baseList.get(i).getGuestID())
                        && (guestInList.get(i).getName().equals(baseList.get(i).getName()))
                        && (guestInList.get(i).getCCInfo().equals(baseList.get(i).getCCInfo()))
                        && (guestInList.get(i).getEmail().equals(baseList.get(i).getEmail()));
                if (!isEqual) {
                    readingError = true;
                }

                fileWriter.write((isEqual) + ","
                        + (guestInList.get(i).getGuestID() == baseList.get(i).getGuestID()) + ","
                        + (guestInList.get(i).getName().equals(baseList.get(i).getName())) + ","
                        + (guestInList.get(i).getCCInfo().equals(baseList.get(i).getCCInfo())) + ","
                        + (guestInList.get(i).getEmail().equals(baseList.get(i).getEmail())) + ",\n");

            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Size of List Written != Size of List Read In");
            readingError = true;
        }

        return readingError;
    }
    
        //------------------<END> Log Methods------------------------------//

}
