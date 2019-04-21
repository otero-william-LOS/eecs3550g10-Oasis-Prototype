/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdbxprototype.data.drivers;

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
    // create single rate w/o base rate
    // create continuous range of rates w/ base rate
    // create continuous range of rates w/o base rate
    
    // modify single rate w/ new base rate
    // variation w/ date param
    // modify continuous range of rates w/ base rate
    // variation w/ date param
    
    // returnRsvTypeRate
    // returnRsvModifyRate
    // returnRsvNoShowRate
    // returnRsvCancelRate
}
