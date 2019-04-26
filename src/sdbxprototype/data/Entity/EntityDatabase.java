/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import Database.LazyBllchrgModel;
import Database.LazyGuestModel;
import Database.LazyRateModel;
import Database.LazyRsvModel;
import Models.GuestModel;
import Models.RoomModel;
import java.util.ArrayList;
import Models.*;
import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author cmason12
 */
public class EntityDatabase extends Database.Database {

    //--------Methods to Conert LazyModel -> Model----------------------///
    protected static RsvModel createReservationModel(LazyBllchrgModel bill) {
        ArrayList<LazyRsvModel> lazyRsvList
                = Database.Database.getRSV_Data();
        RsvModel newRsv = new RsvModel();

        for (int i = 0; i < lazyRsvList.size(); i++) {
            if (lazyRsvList.get(i).getRsvID() == bill.getReservation()) {
                newRsv.setRsvID(lazyRsvList.get(i).getRsvID());
                newRsv.setDateArrive(lazyRsvList.get(i).getDateArrive());
                newRsv.setDateDepart(lazyRsvList.get(i).getDateDepart());
                newRsv.setDatePaid(lazyRsvList.get(i).getDatePaid());
                newRsv.setRsvType(lazyRsvList.get(i).getRsvType());
                //createRoomModel(RsvID);
                newRsv.setRoom(creatRoomModel(lazyRsvList.get(i), newRsv));
                //getGuestModel
                newRsv.setGuest(creatGuestModel(lazyRsvList.get(i), newRsv));
                newRsv.setIsNoShow(lazyRsvList.get(i).isIsNoShow());
                newRsv.setIsPaid(lazyRsvList.get(i).isIsPaid());
                newRsv.setIsConcluded(lazyRsvList.get(i).isIsConcluded());

                newRsv.setListBllchrg(null);
                i = lazyRsvList.size() + 1;
            }
        }

        return newRsv;
    }

    protected static RoomModel creatRoomModel(LazyRsvModel lazyRsv,
            RsvModel rsv) {
        RoomModel room = new RoomModel();
        room.setRoomID(lazyRsv.getRoom());
        room.setIsOccupied(true);
        room.setRsv(rsv);
        //room.setRsvID(rsv);
        return room;
    }

    protected static GuestModel creatGuestModel(LazyRsvModel lazyRsv,
            RsvModel rsv) {
        GuestModel newGuest = new GuestModel();

        ArrayList<LazyGuestModel> guestList
                = Database.Database.getGUEST_Data();
        for (int i = 0; i < guestList.size(); i++) {
            if (guestList.get(i).getGuestID() == lazyRsv.getGuest()) {
                // System.out.println("HERRRE");
                newGuest.setGuestID(guestList.get(i).getGuestID());
                newGuest.setName(guestList.get(i).getName());
                newGuest.setCCInfo(guestList.get(i).getCCInfo());
                newGuest.setEmail(guestList.get(i).getEmail());
                i = guestList.size() + 1;
            }
        }

        //toWrite.append("GuestID,Name,CCINFO,EMAIL,\n");
        return newGuest;
    }
    //--------<END> Methods to Conert LazyModel -> Model--------------------//

    protected static boolean dateIsBetween(Date arrival, Date depart,
            Date test) {

        return (test.after(arrival) || test.equals(arrival))
                && (test.before(depart) || test.equals(depart));

    }

    protected static boolean dateOverlap(Date arrival, Date depart,
            Date start, Date end) {

        boolean overlap = false;

        Date date = arrival;
        while (date.before(depart) || date.equals(depart)) {
            if ((date.equals(start) || date.after(start)
                    && (date.equals(end) || date.before(end)))) {
                overlap = true;
            }
        }
        return overlap;

    }

    //-------------------Append Database----------------------------//
    public static void appendDatabase(RsvModel rsv) {
        LazyRsvModel newRsv = new LazyRsvModel();

        try {
            newRsv.setRsvID(rsv.getRsvID());

            newRsv.setDateArrive(rsv.getDateArrive());
            newRsv.setDateDepart(rsv.getDateDepart());
            newRsv.setDatePaid(rsv.getDatePaid());
            newRsv.setRsvType(rsv.getRsvType());
            newRsv.setRoom(rsv.getRoom().getRoomID());
            newRsv.setGuest(rsv.getGuest().getGuestID());
            newRsv.setIsNoShow(rsv.isIsNoShow());
            newRsv.setIsPaid(rsv.isIsPaid());
            newRsv.setIsConcluded(rsv.isIsConcluded());

            Database.Database.appendRSV_Data(newRsv);
        } catch (Exception e) {
        }
    }

    public static void appendDatabase(RateModel rate) {
        LazyRateModel newRate = new LazyRateModel();

        newRate.setRateDate(rate.getRateDate());
        newRate.setBaseRate(rate.getBaseRate());

        Database.Database.appendRATE_Data(newRate);
    }

    public static void appendDatabase(BllchrgModel bill) {
        LazyBllchrgModel newBllchrg = new LazyBllchrgModel();

        newBllchrg.setBllchrgID(bill.getBllchrgID());
        newBllchrg.setReservation(bill.getReservation().getRsvID());
        newBllchrg.setLineDescription(bill.getLineDescription());
        newBllchrg.setAmount(bill.getAmount());
        newBllchrg.setDateCharged(bill.getDateCharged());
        newBllchrg.setDatePaid(bill.getDatePaid());
        newBllchrg.setIsPaid(bill.isIsPaid());

        Database.Database.appendBllchrg_Data(newBllchrg);
    }

    public static void appendDatabase(GuestModel guest) {
        LazyGuestModel newGuest = new LazyGuestModel();

        newGuest.setGuestID(guest.getGuestID());
        newGuest.setName(guest.getName());
        newGuest.setEmail(guest.getEmail());
        newGuest.setCCInfo(guest.getCCInfo());
        Database.Database.appendGUEST_Data(newGuest);
    }
//-------------------<END> Append Database----------------------------// 

//-------------------Modify Database----------------------------//
    public static void modifyDatabase(RsvModel rsv) {
        LazyRsvModel newRsv = new LazyRsvModel();
        newRsv.setRsvID(rsv.getRsvID());
        newRsv.setDateArrive(rsv.getDateArrive());
        newRsv.setDateDepart(rsv.getDateDepart());
        newRsv.setDatePaid(rsv.getDatePaid());
        newRsv.setRsvType(rsv.getRsvType());
        newRsv.setRoom(rsv.getRoom().getRoomID());
        newRsv.setGuest(rsv.getGuest().getGuestID());
        newRsv.setIsNoShow(rsv.isIsNoShow());
        newRsv.setIsPaid(rsv.isIsPaid());
        newRsv.setIsConcluded(rsv.isIsConcluded());

        Database.Database.modifyRSV_Data(newRsv);
    }

    public static void modifyDatabase(RateModel rate) {
        LazyRateModel newRate = new LazyRateModel();

        newRate.setRateDate(rate.getRateDate());
        newRate.setBaseRate(rate.getBaseRate());

        Database.Database.modifyRATE_Data(newRate);
    }

    public static void modifyDatabase(BllchrgModel bill) {
        LazyBllchrgModel newBllchrg = new LazyBllchrgModel();

        newBllchrg.setBllchrgID(bill.getBllchrgID());
        newBllchrg.setReservation(bill.getReservation().getRsvID());
        newBllchrg.setLineDescription(bill.getLineDescription());
        newBllchrg.setAmount(bill.getAmount());
        newBllchrg.setDateCharged(bill.getDateCharged());
        newBllchrg.setDatePaid(bill.getDatePaid());
        newBllchrg.setIsPaid(bill.isIsPaid());

        Database.Database.modifyBllchrg_Data(newBllchrg);
    }

    public static void modifyDatabase(GuestModel guest) {
        LazyGuestModel newGuest = new LazyGuestModel();

        newGuest.setGuestID(guest.getGuestID());
        System.out.println(guest.getGuestID() + "");
        newGuest.setName(guest.getName());
        newGuest.setEmail(guest.getEmail());
        Database.Database.modifyGUEST_Data(newGuest);
    }
//-------------------<END> Modify Database----------------------------//
}
