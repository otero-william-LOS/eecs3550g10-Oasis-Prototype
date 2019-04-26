/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.drivers;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import prototype.data.persistence.EntityDatabase;
import prototype.data.models.ReservationType;
import prototype.data.models.DataModel;
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
    public static void createRate(Date date, double baseRate){
//        EntityDatabase.addRate(date, baseRate);
    }
    public static void createRate(LocalDate localDate, double baseRate){
        Date dt = Date.valueOf(localDate);
        createRate(dt, baseRate);
    }
    
    // create continuous range of rates w/ base rate
    public static void createRateRange(Date startInclusive, Date endExclusive, Double baseRate){
//        EntityDatabase.addRateRange(startInclusive, endExclusive, baseRate);
    }
    public static void createRateRange(LocalDate startInclusive, LocalDate endExclusive, Double baseRate){
        Date startIn = Date.valueOf(startInclusive);
        Date endEx = Date.valueOf(endExclusive);
        createRateRange(startIn, endEx, baseRate);
    }
    
    // SDBX ONLY: create single rate w/o base rate
    // SDBX ONLY: create continuous range of rates w/o base rate
    
    // modify single rate w/ new base rate
    public static void mdfyBaseRate(RateModel existingRate, double newBaseRate){
        // TODO: Warning 
        // - this method will require null checks
        existingRate.setBaseRate(newBaseRate);
    }
    public static void mdfyBaseRate(Date date, double newBaseRate){
        RateModel existingRate = EntityDatabase.rtrvByDate(date);
        mdfyBaseRate(existingRate, newBaseRate);
    }
    public static void mdfyBaseRate(LocalDate localDate, double newBaseRate){
        Date dt = Date.valueOf(localDate);
        mdfyBaseRate(dt, newBaseRate);
    }
    
    // modify continuous range of rates w/ base rate
    public static void mdfyBaseRateRange(List<RateModel> existingRates, double newBaseRate){
        existingRates.forEach(rt -> rt.setBaseRate(newBaseRate));
    }
    public static void mdfyBaseRateRange(Date startInclusive, Date endInclusive, double newBaseRate){
        List<RateModel> existingRates = EntityDatabase.rtrvByDateRange(startInclusive, endInclusive);
        mdfyBaseRateRange(existingRates, newBaseRate);
    }
    public static void mdfyBaseRateRange(LocalDate startInclusive, LocalDate endInclusive, double newBaseRate){
        Date startIn = Date.valueOf(startInclusive);
        Date endIn = Date.valueOf(endInclusive);
        mdfyBaseRateRange(startIn, endIn, newBaseRate);
    }
    
    // searchByDate
    public static RateModel srchByDate(Date date){
        return EntityDatabase.rtrvByDate(date);
    }
    public static RateModel srchByDate(LocalDate localDate){
        Date dt = Date.valueOf(localDate);
        return EntityDatabase.rtrvByDate(dt);
    }
    
    // searchByDateRange
    public static List<RateModel> srchByDateRange(Date startInclusive, Date endInclusive){
        return EntityDatabase.rtrvByDateRange(startInclusive, endInclusive);
    }
    public static List<RateModel> srchByDateRange(LocalDate startInclusive, LocalDate endInclusive){
        Date startIn = Date.valueOf(startInclusive);
        Date endIn = Date.valueOf(endInclusive);
        return EntityDatabase.rtrvByDateRange(startIn, endIn);
    }
    
    // return all rates in table
    public static List<RateModel> rtrnAllRates(){
        return EntityDatabase.rtrvAllRates();
    }
    
    // returnRsvTypeRate
    public static double rtrnRsvTypeRate(RateModel rate, ReservationType type){
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
                System.out.println("Something fucked up, if here");
                //throw new AssertionError(type.name());
        }
        return rate.getBaseRate() * typeRate;
    }
    public static double rtrnRsvTypeRate(Date date, ReservationType type){
        RateModel rate = EntityDatabase.rtrvByDate(date);
        return rtrnRsvTypeRate(rate, type);
    }
    public static double rtrnRsvTypeRate(LocalDate localDate, ReservationType type){
        Date date = Date.valueOf(localDate);
        return rtrnRsvTypeRate(date, type);
    }
    
    // returnRsvModifyRate
    public static double rtrnRsvModifyRate(RateModel rate){
        // TODO: Warning 
        // - this method will require null checks
        return rate.getBaseRate() * MLTPLR_RSVMDFY;
    }
    public static double rtrnRsvModifyRate(Date date){
        RateModel rate = EntityDatabase.rtrvByDate(date);
        return rtrnRsvModifyRate(rate);
    }
    public static double rtrnRsvModifyRate(LocalDate localDate){
        Date date = Date.valueOf(localDate);
        return rtrnRsvModifyRate(date);
    }
    
    // returnRsvNoShowRate
    public static double rtrnRsvNoShowRate(RateModel rate){
        // TODO: Warning 
        // - this method will require null checks
        return rate.getBaseRate() * MLTPLR_NOSHOW;
    }
    public static double rtrnRsvNoShowRate(Date date){
        RateModel rate = EntityDatabase.rtrvByDate(date);
        return rtrnRsvNoShowRate(rate);
    }
    public static double rtrnRsvNoShowRate(LocalDate localDate){
        Date date = Date.valueOf(localDate);
        return rtrnRsvNoShowRate(date);
    }
    
    // returnRsvCancelRate
    public static double rtrnRsvCancelRate(RateModel rate){
        // TODO: Warning 
        // - this method will require null checks
        return rate.getBaseRate() * MLTPLR_CANCEL;
    }
    public static double rtrnRsvCancelRate(Date date){
        RateModel rate = EntityDatabase.rtrvByDate(date);
        return rtrnRsvCancelRate(rate);
    }
    public static double rtrnRsvCancelRate(LocalDate localDate){
        Date date = Date.valueOf(localDate);
        return rtrnRsvCancelRate(date);
    }
}
