
package prototype.logic.schedulers;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import prototype.data.drivers.ReservationDriver;
import prototype.data.models.ReservationModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import prototype.data.drivers.GuestDriver;
import prototype.data.models.GuestModel;
import prototype.data.models.ReservationType;


public class ReservationScheduling implements Scheduler {    
    // receive a date and dte depart
    // send these to payment processing, 
    public static String createReservation(Date dateArrive, Date dateDepart){
            
        
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
        String returnstring = "fuck yeah";
        return returnstring;
    }
    
    public static void modifyReservation(ReservationModel reservation){
    // probably wont receive RsvModels but rather ID's not too sure yet
    }
    
    public static ArrayList<ReservationModel> getReservations(){
    // this will be used to retreive more than one reservation based on some
    // criteria. any modifications will be done in a seperate method simillar
    // to modifyReservation()
        
        
        ArrayList<ReservationModel> reservations = new ArrayList();
        return reservations;
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
    
    
  private static List<ReservationType> getAvaliableRsvTypes(LocalDate start) {
        List<ReservationType> rsvTypeList = new ArrayList<>();

        rsvTypeList.add(ReservationType.CONVENTIONAL);
        if (start.isAfter(LocalDate.now().plusDays(89))) {
            rsvTypeList.add(ReservationType.PREPAID);
        }
        if (start.isAfter(LocalDate.now().plusDays(59))) {
            rsvTypeList.add(ReservationType.SIXTYADV);
        }
        if (start.isBefore(LocalDate.now().plusDays(31))) {
            rsvTypeList.add(ReservationType.INCENTIVE);
        }
        return rsvTypeList;
    }

    private static boolean typeAvliableCheck(ReservationType rsvType, LocalDate start) {
        List<ReservationType> avaliableRsvTypes = new ArrayList<>();
        boolean containsType = false;

        avaliableRsvTypes = getAvaliableRsvTypes(start);
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

    public static void createReservationTest(ReservationType rsvType) {
        // 4 Cases 
        // 90 days adv, 60 days adv, incentive <= 30 adv, conventional any time
        LocalDate today = LocalDate.now();
        List<ReservationType> avaliableRsvTypes = new ArrayList<>();
        int typeModifier = 0;
        switch (rsvType){
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


        if (typeAvliableCheck(rsvType, today.plusDays(typeModifier))) {
            //Check For at least one occupany for duration of stay 
            int occupancyCount = 0;
            
            LocalDate start = today.plusDays(typeModifier);
            LocalDate end = today.plusDays(typeModifier + 7);
            for (int i = 0; start.plusDays(i).isBefore(end.plusDays(1)); i++) {
                List<ReservationModel>  listOccupancy
                        = ReservationDriver.searchByDate(start.plusDays(i));
                
                if (listOccupancy.size() < 45) occupancyCount++;
            }

            long diff = DAYS.between(start, end)+1;
     
            if(diff == occupancyCount){
                System.out.println("Is Avliable!");
                //Can Create Reservation
                
        int testRsvID = ReservationDriver.createReservationReturnID(start,
                        end);
        ReservationDriver.modifyReservationType(
                testRsvID, ReservationType.PREPAID);
        
        
              //  PaymentProcessing.
                   //     generateAccmBill(ReservationDriver.searchByID(testRsvID));
            }

        }
        
    }
    
    public static void proccessReservationPaymentTest(int testRsvID) {
         int tempGuestID = GuestDriver.createGuestReturnID("John Doe", "JohnDoe@gmail.com");
        GuestDriver.modifyGuestCreditCardInfo(tempGuestID, "<No Information Provided>");
        GuestModel tempGuest = GuestDriver.searchByID(tempGuestID);
        ReservationDriver.attachGuest(testRsvID, tempGuestID);
        
        //PaymentProccessing.testProccessMethod(testRsvID);
    }

}
