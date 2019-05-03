/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.drivers;

import java.time.LocalDate;
import java.util.List;
import prototype.data.persistence.EntityDatabase;
import prototype.data.models.ReservationType;
import prototype.data.models.RateModel;

/**
 *
 * @author jon
 */
public class RateDriver implements DataDriver {
    
    // diff rate scalar constants
    public final static double MLTPLR_CANCEL = 1.00;
    public final static double MLTPLR_NOSHOW = 1.00;
    public final static double MLTPLR_RSVMDFY = 1.10;
    public final static double MLTPLR_PREPAID = 0.75;
    public final static double MLTPLR_SIXTYADV = 0.85;
    public final static double MLTPLR_CNVNTNL = 1.00;
    public final static double MLTPLR_INCNTV = 0.80;
    
    // create single rate w/ base rate
    public static void createRate(LocalDate localDate, double baseRate){
        EntityDatabase.RateTable.addRate(localDate, baseRate);
    }
    // create continuous range of rates w/ base rate
    public static void createRateRange(LocalDate startInclusive, LocalDate endExclusive, double baseRate){
        EntityDatabase.RateTable.addRateRange(startInclusive, endExclusive, baseRate);
    }
    
    // modify single rate w/ new base rate
    public static void modifyBaseRate(RateModel existingRate, double newBaseRate){
        if (existingRate != null) existingRate.setBaseRate(newBaseRate);
    }
    public static void modifyBaseRate(LocalDate date, double newBaseRate){
        RateModel existingRate = EntityDatabase.RateTable.retrieveRateByDate(date);
        modifyBaseRate(existingRate, newBaseRate);
    }
    
    // modify continuous range of rates w/ base rate
    public static void modifyBaseRateRange(List<RateModel> existingRates, double newBaseRate){
        existingRates.forEach(rt -> rt.setBaseRate(newBaseRate));
    }
    public static void modifyBaseRateRange(LocalDate startInclusive, LocalDate endInclusive, double newBaseRate){
        List<RateModel> existingRates = EntityDatabase.RateTable.retrieveByDateRange(startInclusive, endInclusive);
        RateDriver.modifyBaseRateRange(existingRates, newBaseRate);
    }
    
    // searchByDate
    public static RateModel searchByDate(LocalDate date){
        return EntityDatabase.RateTable.retrieveRateByDate(date);
    }
    // searchByDateRange
    public static List<RateModel> searchByDateRange(LocalDate startInclusive, LocalDate endInclusive){
        return EntityDatabase.RateTable.retrieveByDateRange(startInclusive, endInclusive);
    }
    
    // return all rates in table
    public static List<RateModel> returnAllRates(){
        return EntityDatabase.RateTable.retriveAllRates();
    }
    
    // returnReservationTypeRate
    public static double returnReservationTypeRate(RateModel rate, ReservationType type){
        // TODO: Warning 
        // - this method will require null checks
        double typeRate = 0;
        switch(type){
            case PREPAID:
                typeRate = MLTPLR_PREPAID;
                break;
            case SIXTYADV:
                typeRate = MLTPLR_SIXTYADV;
                break;
            case CONVENTIONAL:
                typeRate = MLTPLR_CNVNTNL;
                break;
            case INCENTIVE:
                typeRate = MLTPLR_INCNTV;
                break;
            default:
                System.out.println("ERROR");
                //throw new AssertionError(type.name());
        }
        return rate.getBaseRate() * typeRate;
    }
    public static double returnReservationTypeRate(LocalDate date, ReservationType type){
        RateModel rate = EntityDatabase.RateTable.retrieveRateByDate(date);
        return returnReservationTypeRate(rate, type);
    }
    
    // returnRsvModifyRate
    public static double returnReservationModifyRate(RateModel rate){
        // TODO: Warning 
        // - this method will require null checks
        return rate.getBaseRate() * MLTPLR_RSVMDFY;
    }
    public static double returnReservationModifyRate(LocalDate date){
        RateModel rate = EntityDatabase.RateTable.retrieveRateByDate(date);
        return RateDriver.returnReservationModifyRate(rate);
    }
    
    // returnRsvNoShowRate
    public static double returnNoShowRate(RateModel rate){
        // TODO: Warning 
        // - this method will require null checks
        return rate.getBaseRate() * MLTPLR_NOSHOW;
    }
    public static double returnNoShowRate(LocalDate date){
        RateModel rate = EntityDatabase.RateTable.retrieveRateByDate(date);
        return RateDriver.returnNoShowRate(rate);
    }
    
    // returnRsvCancelRate
    public static double returnCancelRate(RateModel rate){
        // TODO: Warning 
        // - this method will require null checks
        return rate.getBaseRate() * MLTPLR_CANCEL;
    }
    public static double returnCancelRate(LocalDate date){
        RateModel rate = EntityDatabase.RateTable.retrieveRateByDate(date);
        return RateDriver.returnCancelRate(rate);
    }
}
