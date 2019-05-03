/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.logic.schedulers;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import prototype.data.persistence.EntityDatabase;
import prototype.data.drivers.RateDriver;
import prototype.data.models.RateModel;

/**
 *
 * @author jon
 */
public class RateScheduling implements Scheduler {

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
    
    public static String setRate(LocalDate day, double rate){
        String returnString = ""; // return this shit to the output boxi  think will be there i nthe gui
        RateModel currentRate = RateDriver.searchByDate(day);
        
        if(day.isBefore(LocalDate.now())){
            returnString = "Cant modify rates before today";
            return returnString;
        }
        
        // check to see if the rate for this day was already set
        if(!(currentRate.getRateDate().equals(LocalDate.MIN))){
            // rate already exists so modify its values
            RateDriver.modifyBaseRate(currentRate, rate);
            returnString = "The base rate for " + day.toString() + " was modified to " + rate;
        }
        else{
            // rate does not exist so add a new one
            RateDriver.createRate(day, rate);
            returnString = "The base rate for " + day.toString() + " was set to " + rate;
        }
                           
        return returnString;
    }
    
    public static String setRateRange(LocalDate startDate, LocalDate endDate, double rate){
    
        String returnString = ""; // return this shit to the output boxi  think will be there i nthe gui
        LocalDate currentDay = startDate;
        RateModel currentRate = new RateModel();
        
        if(startDate.isBefore(LocalDate.now())){
            returnString = "Cant modify rates before today";
            return returnString;
        }
        
        if(endDate.isBefore(startDate)){
            returnString = "Cant have an end date before start date";
            return returnString;
        }
        
        // iterate trhough the date range, modifying or creating dates as needed
        while(!currentDay.equals(endDate.plusDays(1))){
            currentRate = RateDriver.searchByDate(currentDay);
            // check to see if the rate for this day was already set
            if(!(currentRate.getRateDate().equals(LocalDate.MIN))){
                // rate already exists so modify its values
                RateDriver.modifyBaseRate(currentRate, rate);
            }
            else{
                // rate does not exist so add a new one
                RateDriver.createRate(currentDay, rate);
            }
            
            // advance to the next day
            currentDay = currentDay.plusDays(1);
        }

        returnString = "The rates for " + startDate.toString() + " through " + endDate.toString() + " have been set to " + rate;
        return returnString;
    }
    
}
