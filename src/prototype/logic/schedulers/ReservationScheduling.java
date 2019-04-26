
package prototype.logic.schedulers;

import prototype.data.drivers.ReservationDriver;
import prototype.data.models.ReservationModel;
import java.util.ArrayList;
import java.util.Date;


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

}
