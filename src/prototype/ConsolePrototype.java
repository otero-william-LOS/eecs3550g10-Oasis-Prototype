/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import prototype.data.drivers.GuestDriver;
import prototype.data.drivers.RateDriver;
import prototype.data.drivers.ReservationDriver;
import prototype.data.models.GuestModel;
import prototype.data.models.ReservationModel;
import prototype.data.models.ReservationType;
import prototype.logic.schedulers.PaymentProcessing;
import prototype.logic.schedulers.RateScheduling;
import prototype.logic.schedulers.ReportGeneration;
import prototype.logic.schedulers.ReservationScheduling;

/**
 *
 * @author cmason12
 */
public class ConsolePrototype {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<ReservationModel> arrivalList = new ArrayList<>();

    public static LocalDate getDateInput() {
        LocalDate returnDate = LocalDate.MIN;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
       

        while (returnDate.equals(LocalDate.MIN)) {
             System.out.println("Enter Date With Format (MM/dd/yyyy): ");
            try {
                returnDate = LocalDate.parse(scanner.next(),
                        formatter);
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("\t\tFailed Conversion!: " + e);
            }
            
            
        }

        return returnDate;
    }

    public static String getString() {
        return scanner.nextLine();
    }

    public static int getCommand() {
        int command = 0;

        while (command == 0) {
             System.out.println("Enter Integer: ");
            try {
                command = Integer.parseInt(scanner.nextLine());

            } catch (Exception e) {
                System.out.println("\t\tFailed Conversion!: " + e);
            }
        }

        return command;
    }

    public static double getDouble() {
        double command = 0;

        while (command == 0) {
            try {
                command = Double.parseDouble(scanner.nextLine());

            } catch (Exception e) {
                System.out.println("\t\tFailed Conversion!: " + e);
            }
        }

        return command;
    }

    //Main Screen 
    /*
    1.Check In 
    2.Look-Up
        -Modify Option
    3.Check Out
    4.Create Reservation
    5.Rates
        -Lookup Base Rate
        -Set Base Rate
    6. Reports
    7. Shutdown
    
     */
    private static boolean checkInProccess() {
        boolean checkedIn = false;

        int command = 0;
        int index = 0;
        String inputString = "";
        ReservationModel rsv = ReservationModel.EMPTY_ENTITY;
        List<ReservationModel> rsvList = new ArrayList<>();
        while (command != 4) {
            System.out.println("-----Check In Screen----\n"
                    + "1.Search By Reservation ID\n"
                    + "2.Search By Name\n"
                    + "3.Search By Email\n"
                    + "4.Main Screen\n");
            System.out.print(": ");
            command = getCommand();

            switch (command) {
                case 1:
                    System.out.print("\t Enter RsvID: ");
                    index = getCommand();
                    for (ReservationModel guestRsv : arrivalList) {
                        if (guestRsv.getGuest().getEmail().equals(inputString)) {
                            rsv = (guestRsv);
                        }
                    }

                    if (rsv != ReservationModel.EMPTY_ENTITY) {
                        int removeIndex = -1;

                        int count = 0;
                        for (ReservationModel checkInRsv : arrivalList) {
                            if (checkInRsv.getReservationID() == rsv.getReservationID()) {
                                removeIndex = count;
                            }
                            count++;
                        }
                        if (removeIndex != -1) {
                            arrivalList.remove(removeIndex);
                        }
                        System.out.println("Checked In!.."
                                + "Returning to main Screen...\n");
                        command = 4;
                        checkedIn = true;
                    } else {
                        System.out.println("Failed To Check In (Not Found)!.."
                                + "Returning to main Screen...\n");
                        command = 4;
                    }
                    break;
                case 3:
                    System.out.print("\t Enter Guest Email: ");
                    inputString = getString();

                    for (ReservationModel guestRsv : arrivalList) {
                        if (guestRsv.getGuest().getEmail().equals(inputString)) {
                            rsvList.add(guestRsv);
                        }
                    }
                    if (!rsvList.isEmpty()) {
                        for (ReservationModel guestRsv : rsvList) {
                            arrivalList.remove(guestRsv);
                        }
                        rsvList = new ArrayList<>();
                        System.out.println("Checked In!.."
                                + "Returning to main Screen...\n");
                        command = 4;
                        checkedIn = true;
                    } else {
                        System.out.println("Failed To Check In (Not Found)!.."
                                + "Returning to main Screen...\n");
                        command = 4;
                    }

                    break;
                case 2:
                    System.out.print("\t Enter Guest Name: ");
                    inputString = getString();

                    for (ReservationModel guestRsv : arrivalList) {
                        if (guestRsv.getGuest().getName().equals(inputString)) {
                            rsvList.add(guestRsv);
                        }
                    }
                    if (!rsvList.isEmpty()) {
                        for (ReservationModel guestRsv : rsvList) {
                            arrivalList.remove(guestRsv);
                        }
                        rsvList = new ArrayList<>();
                        System.out.println("Checked In!.."
                                + "Returning to main Screen...\n");
                        command = 4;
                        checkedIn = true;
                    } else {
                        System.out.println("Failed To Check In (Not Found)!.."
                                + "Returning to main Screen...\n");
                        command = 4;
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }

        }
        return checkedIn;
    }

    private static void modifyPrompt(ReservationModel rsv) {
        String decision = "";
        System.out.println("Modify Reservation?");
        System.out.println(rsv.toString());
        while (!decision.equals("Y") && !decision.equals("N")) {
            System.out.print("(Y/N): ");
            decision = getString();
        }

        if (decision.equals("Y")) {
            modifyProccess(rsv);
        }

    }

    private static void modifyPrompt(List<ReservationModel> rsvList) {
        String decision = "";
        ReservationModel rsv = ReservationModel.EMPTY_ENTITY;
        int index = -1;

        while (index == -1) {
            System.out.println("SelectReservation");
            int count = 1;
            for (ReservationModel resultRsv : rsvList) {
                System.out.println(count + ":\t" + resultRsv.toString());
                count++;
            }
            index = getCommand();
            if (index < 0 || index > rsvList.size() - 1) {
                index = -1;
                System.out.println("Invalid Index!");
            }

            System.out.println("Modify Reservation?");
            System.out.println(rsv.toString());
            while (!decision.equals("Y") && !decision.equals("N")) {
                System.out.print("(Y/N): ");
                decision = getString();
            }

            if (decision.equals("Y")) {
                modifyProccess(rsv);
            }

        }

    }

    private static void lookUpProccess() {
        int command = 0;
        int index = 0;
        String inputString = "";
        ReservationModel rsv = ReservationModel.EMPTY_ENTITY;
        List<ReservationModel> rsvList = new ArrayList<>();
        while (command != 6) {
            System.out.println("-----Look up Screen----\n"
                    + "1.Search By Reservation ID\n"
                    + "2.Search By Name\n"
                    + "3.Search By Email\n"
                    + "4.Search By ArriveDate\n"
                    + "5.Search By DepartDate\n"
                    + "6.Main Screen\n");
            System.out.print("Selection: ");
            command = getCommand();

            GuestModel guest = GuestModel.EMPTY_ENTITY;
            ReservationModel reservation = ReservationModel.EMPTY_ENTITY;
            LocalDate temp = LocalDate.MIN;
            switch (command) {
                case 1:
                    System.out.print("\t Enter RsvID: ");
                    index = getCommand();
                    rsv = ReservationScheduling.searchByID(index);

                    if (rsv != ReservationModel.EMPTY_ENTITY) {
                        modifyPrompt(rsv);
                        System.out.println("Complete");
                        command = 6;
                    } else {
                        System.out.println("Failed To Check In (Not Found)!.."
                                + "Returning to Main Screen...\n");
                        command = 6;
                    }
                    break;
         
                case 2:
                    System.out.print("Enter Guest Name: ");
                    inputString = getString();

                    guest = GuestModel.EMPTY_ENTITY;
                    guest = GuestDriver.searchByName(inputString);
                    rsvList = ReservationScheduling.searchByGuest(command);

                    if (guest == GuestModel.EMPTY_ENTITY) {
                        System.out.println("Not Found)");
                        command = 6;
                    } else {
                        modifyPrompt(ReservationScheduling.searchByGuest(guest));
                        System.out.println("Complete!");
                        command = 6;
                    }

                    break;
                     case 3:
                    System.out.print("\t Enter Guest Email: ");
                    inputString = getString();

                    guest = GuestModel.EMPTY_ENTITY;
                    guest = GuestDriver.searchByEmail(inputString);
                    rsvList = ReservationScheduling.searchByGuest(command);

                    if (guest == GuestModel.EMPTY_ENTITY) {
                        System.out.println("Not Found)");
                        command = 6;
                    } else {
                        modifyPrompt(ReservationScheduling.searchByGuest(guest));
                        System.out.println("Complete!");
                        command = 6;
                    }

                    break;
                case 4:
                    System.out.print("\t Enter Date Arrive: ");
                    temp = getDateInput();

                    rsvList = new ArrayList<>();
                    rsvList = ReservationScheduling.searchByDateArrive(temp);

                    if (rsvList.isEmpty()) {
                        System.out.println("Not Reservations Found Arriving On That Day");
                        command = 6;
                    } else {
                        modifyPrompt(rsvList);
                        System.out.println("Complete!");
                        command = 6;
                    }

                    break;
                case 5:
                    System.out.print("\t Enter Date Depart: ");
                    temp = getDateInput();

                    rsvList = new ArrayList<>();
                    rsvList = ReservationScheduling.searchByDateDepart(temp);

                    if (rsvList.isEmpty()) {
                        System.out.println("Not Reservations Found Departing On That Day");
                        command = 6;
                    } else {
                        modifyPrompt(rsvList);
                        System.out.println("Complete!");
                        command = 6;
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private static void modifyProccess(ReservationModel rsv) {

        int command = 0;

        while (command != 7) {
            System.out.println("-----Modify Reservation Screen----\n"
                    + "1.Edit Arrive Date\n"
                    + "2.Edit Depart Date \n"
                    + "3.Edit Arrive & Depart Date\n"
                    + "4.Edit Guest Name\n"
                    + "5.Edit Guest Email\n"
                    + "6.Edit Guest CC_Info\n"
                    + "7.Main Screen\n");
            System.out.print(": ");
            command = getCommand();

            GuestModel guest = GuestModel.EMPTY_ENTITY;
            ReservationModel reservation = ReservationModel.EMPTY_ENTITY;
            LocalDate temp = LocalDate.MIN;
            switch (command) {
                case 1:
                    System.out.println("New Arrival Date: ");
                    ReservationScheduling.modifyReservationArriveDate(rsv.getReservationID(), getDateInput());
                    PaymentProcessing.applyPenaltyCharge(rsv);
                    System.out.println("Complete!");
                    command = 7;
                    break;
                case 2:
                    System.out.println("New Depart Date: ");
                    ReservationScheduling.modifyReservationDepartDate(rsv.getReservationID(), getDateInput());
                     PaymentProcessing.applyPenaltyCharge(rsv);
                    System.out.println("Complete!");
                    command = 7;
                    break;
                case 3:
                    System.out.println("New Arrival Date: ");
                    ReservationScheduling.modifyReservationArriveDate(rsv.getReservationID(), getDateInput());
                    

                    System.out.println("New DepartDate: ");
                    ReservationScheduling.modifyReservationDepartDate(rsv.getReservationID(), getDateInput());
                     PaymentProcessing.applyPenaltyCharge(rsv);
                    System.out.println("Complete!");
                    command = 7;
                    break;
                case 4:
                    System.out.println("New Guest Name: ");
                    ReservationScheduling.modifyGuestNameByRsvID(rsv.getReservationID(), getString());
                    System.out.println("Complete!");
                    command = 7;
                    break;
                case 5:
                    System.out.println("New Guest Email: ");
                    ReservationScheduling.modifyGuestEmailByRsvID(rsv.getReservationID(), getString());
                    System.out.println("Complete!");
                    command = 7;
                    break;
                case 6:
                    System.out.println("New Guest CC_Info: ");
                    ReservationScheduling.modifyGuestCCInfoByRsvID(rsv.getReservationID(), getString());
                    System.out.println("Complete!");
                    command = 7;
                    break;
                default:
                    //nothing just break
                    break;
            }
        }

    }

    private static void checkoutReservation() {
        List<ReservationModel> rsvList
                = ReservationScheduling.searchByDateDepart(LocalDate.now());

        int selection = -1;
        int count = 1;
        if (!rsvList.isEmpty()) {
            System.out.println("-----CheckOut------\nDepartureList");
            for (ReservationModel rsv : rsvList) {
                System.out.println(count + ": " + rsv.toString());
                count++;
            }

            System.out.print("Selection: ");
            while (selection == -1) {
                selection = getCommand();

                if (selection < 0 || selection > rsvList.size() - 1) {
                    selection = -1;
                }
            }

            System.out.println("\nCheckingOut....");
            if (rsvList.get(selection).getReservationType().equals(ReservationType.CONVENTIONAL)
                    || rsvList.get(selection).getReservationType().equals(ReservationType.INCENTIVE)) {
                System.out.println("Processing Payment...");
                PaymentProcessing.printAccmBill(rsvList.get(selection));
                System.out.println(" Payment Processed!");

            }
            ReservationScheduling.
                    checkOutReservation(rsvList.get(selection).getReservationID());

            System.out.println("Printing Accomidation Bill...");
            System.out.println("Done");
        }
    }

    private static void creatReservation() {
        LocalDate dateArrive = LocalDate.MIN;
        LocalDate dateDepart = LocalDate.MIN;
        String name = "";
        String email = "";
        String ccInfo = "";

        System.out.println("-----Create Reservation-----");
        System.out.print("Start Date: ");
        dateArrive = getDateInput();
        System.out.println();
        System.out.print("Depart Date: ");
        dateDepart = getDateInput();

        System.out.println();
        int rsvID
                = ReservationScheduling.createReservation(dateArrive, dateDepart);
        int guestID = 0;
        if (rsvID != 0) {
            System.out.println("Reservation Type: "
                    + ReservationScheduling.searchByID(rsvID).getReservationType().toString());
            System.out.println("Guest Name: ");
            name = getString();

            System.out.println();
            System.out.println("Guest Email: ");
            email = getString();

            System.out.println();
            System.out.println("Guest CC_Info: ");
            if (ReservationScheduling.searchByID(rsvID).getReservationType().
                    equals(ReservationType.SIXTYADV)) {
                //Doesn't Preform Null check (Sixty Day adv dont have to put in info
                ccInfo = scanner.next();
            } else {
                ccInfo = getString();
            }
            System.out.println();
            System.out.println("Attaching Guest To Reservation...");
            guestID = ReservationScheduling.createGuest(name, email, ccInfo);
            ReservationScheduling.attachReservationToGuest(rsvID, guestID);
            System.out.println("Processing~!");
            ReservationScheduling.proccessNewReservation(rsvID);
            System.out.println("Complete!\nReservation Confirmed!\nReservation Id: " + rsvID);
            System.out.println();

        } else {
            System.out.println("No vaccancies for that interval!");
        }
    }

    private static void rateProcess() {
        System.out.println("-------Rates------ ");
        System.out.println("1. Create Rate");
        System.out.println("2. Lookup Rate");
        System.out.println("3. Lookup Rate Range");
        System.out.println("4. Modify Rate ");
        System.out.println("5. Main Screen");

        int command = 0;
        LocalDate tempDate = LocalDate.MIN;
        LocalDate rangeStart = LocalDate.MIN;
        LocalDate rangeEnd = LocalDate.MIN;
        double baseRate = 0;
        while (command != 5) {
            System.out.println("Selection: ");
            command = getCommand();

            switch (command) {
                case 1:
                    System.out.println("Create Rate Date:");
                    tempDate = getDateInput();
                    System.out.println("Set Base Rate:");
                    baseRate = getDouble();
                    RateScheduling.setRate(tempDate, baseRate);
                    System.out.println("Done!");
                    command = 5;
                    break;
                case 2:
                    System.out.println("Desired Day:");
                    tempDate = getDateInput();

                    System.out.println("Date: " + tempDate.toString()
                            + " || Rate: " + RateDriver.searchByDate(tempDate).getBaseRate() + "");
                    break;
                case 3:
                    System.out.println("Desired Start Day:");
                    rangeStart = getDateInput();
                    System.out.println("Desired End Day:");
                    rangeEnd = getDateInput();

                    for (int i = 0; rangeStart.plusDays(i).isBefore(rangeEnd.plusDays(i)); i++) {
                        System.out.println("Date: " + rangeStart.plusDays(i).toString()
                                + " || Rate: " + RateDriver.searchByDate(rangeStart.plusDays(i)).getBaseRate() + "");
                    }
                    break;
                case 4:
                    System.out.println("Desired Day:");
                    tempDate = getDateInput();
                    System.out.println("Date: " + tempDate.toString()
                            + " || Rate: " + RateDriver.searchByDate(tempDate).getBaseRate() + "");

                    System.out.println("New Base Rate:");
                    RateDriver.searchByDate(tempDate).setBaseRate(getDouble());
                    break;
                case 5:
                    //Break Condition
                    break;
                default:
                    //Nothing
                    break;
            }
        }
    }

    private static void reportProccess() {
        int command = 0;

        while (command != 7) {
            System.out.println("----Reports Screen-------");
            System.out.println("1. Print All");
            System.out.println("2. Print Daily Arrivals Report");
            System.out.println("3. Print Daily Occupancy Report");
            System.out.println("4. Print Incentive Report");
            System.out.println("5. Print Occupancy Report");
            System.out.println("6. Print Income Report");
            System.out.println("7. Print Accomidation Bill");
            System.out.println("8. Main Screen");

            System.out.println("Selection: ");
            command = getCommand();

            switch (command) {
                case 1:
                    ReportGeneration.writeDailyArrivalsReport();
                    ReportGeneration.writeOccupancyReport();
                    ReportGeneration.writeDailyOccupancyReport();
                    ReportGeneration.writeIncomeReport();
                    ReportGeneration.writeIncentiveReport();
                    command = 7;
                    break;
                case 2:
                    ReportGeneration.writeDailyArrivalsReport();
                    break;
                case 3:
                    ReportGeneration.writeDailyOccupancyReport();
                    break;
                case 4:
                    ReportGeneration.writeIncentiveReport();
                    break;
                case 5:
                    ReportGeneration.writeOccupancyReport();
                    break;
                case 6:
                    ReportGeneration.writeIncomeReport();
                    break;
                case 7:
                    System.out.println("Reservation ID:");
                    int rsvID = getCommand();
                    ReservationModel rsv = ReservationScheduling.searchByID(rsvID);
                    if (rsv == ReservationModel.EMPTY_ENTITY){
                        System.out.println("Not Found!");
                    } else {
                        PaymentProcessing.printAccmBill(rsv);
                        System.out.println("Done!");
                    }
                    
                    break;
                default:
                    break;
            }
        }
    }

    
    private static void managerProccesses(){
        int command = 0;
      

        while (command != 3) {
            System.out.println("------Manager Functions-----");
            System.out.println("1. Cancel A Reservation\n"
            + "2. Apply No Show Charges\n"
            + "3. Main Screen\n");
            System.out.println("Selection: ");
            command = getCommand();
            
              long diff = 0;
              int rsvID = 0;
              ReservationModel rsv = ReservationModel.EMPTY_ENTITY;
            switch(command){
              
                case 1:
                    //Cancel a reservation
                    //applyCancelation
                    //DAYS.between(LocalDate.now(), dateArrive) + 1;
                    System.out.println("Cancelation -> Reservation ID:");
                    rsvID = getCommand();
                    ReservationScheduling.searchByID(rsvID);
                    if (rsv == ReservationModel.EMPTY_ENTITY){
                        System.out.println("Not Found!");
                    }else {
                        ReservationScheduling.cancelReservation(rsvID);
                        System.out.println("Done!");
                    }
                    break;
                case 2: 
                    
                    if (arrivalList.isEmpty()){
                        System.out.println("Done -> No (Now Shows)'s");
                    } else {
                        for (ReservationModel ns: arrivalList){
                            ReservationScheduling.applyNowShowReservation(ns.getReservationID(), true);
                            PaymentProcessing.applyPenaltyCharge(ns);
                        }
                        //this is called on the start of the next day so preps arrival list
                        // for the next day
                        arrivalList = ReservationScheduling.searchByDateArrive(LocalDate.now());
                    }
                    
                    break;
                case 3:
                    break;
                default:
                    //Do Nothing
                    break;
            }
        }
    }
    public static void startSystem() {
        int command = 0;
        arrivalList = ReservationScheduling.searchByDateArrive(LocalDate.now());

        while (command != 8) {

            System.out.println("-----Main Screen----\n"
                    + "1.Check In \n"
                    + "2.Look-Up\n"
                    + "        -Modify Options\n"
                    + "3.Check Out\n"
                    + "4.Create Reservation\n"
                    + "5.Rates\n"
                    + "        -Lookup Base Rate\n"
                    + "        -Set Base Rate\n"
                    + "6. Reports\n"
                    + "7. Manage Functions\n"
                    + "8. Shutdown");
            System.out.println("Selection: ");
            command = getCommand();

            switch (command) {
                case 1:
                    checkInProccess();
                    break;
                case 2:
                    lookUpProccess();
                    break;
                case 3:
                    checkoutReservation();
                    break;
                case 4:
                    creatReservation();
                    break;
                case 5:
                    rateProcess();
                    break;
                case 6:
                    reportProccess();
                    break;
                case 7:
                    //Cancel Reservations 
                    //Apply No Show Penalty
                    managerProccesses();
                    break;
                case 8:
                    //Nothing
                    break;
                default:
                    System.out.println("****!Unrecognized Command!******");
                    break;
            }

        }

    }

}
