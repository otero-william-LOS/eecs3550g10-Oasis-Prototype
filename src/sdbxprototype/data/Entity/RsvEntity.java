/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import Database.LazyBllchrgModel;
import Database.LazyRsvModel;
import static Entity.EntityDatabase.creatGuestModel;
import static Entity.EntityDatabase.creatRoomModel;
import java.util.ArrayList;
import Models.*;

import java.sql.Date;

/**
 *
 * @author cmason12
 */
public class RsvEntity extends Database.Database {

    protected static ArrayList<LazyRsvModel> rsvToLazyList(ArrayList<RsvModel> tempList) {
        ArrayList<LazyRsvModel> list = new ArrayList<>();

        for (int i = 0; i < tempList.size(); i++) {
            LazyRsvModel newLzy = new LazyRsvModel();
            newLzy.setRsvID(tempList.get(i).getRsvID());
            newLzy.setDateArrive(tempList.get(i).getDateArrive());
            newLzy.setDateDepart(tempList.get(i).getDateDepart());
            newLzy.setDatePaid(tempList.get(i).getDatePaid());
            newLzy.setRsvType(tempList.get(i).getRsvType());
            if(tempList.get(i).getRoom() != null)
            newLzy.setRoom(tempList.get(i).getRoom().getRoomID());
            newLzy.setGuest(tempList.get(i).getGuest().getGuestID());
            newLzy.setIsNoShow(tempList.get(i).isIsNoShow());
            newLzy.setIsPaid(tempList.get(i).isIsPaid());
            newLzy.setIsConcluded(tempList.get(i).isIsConcluded());
            list.add(newLzy);
        }
        return list;
    }

    //-----------------------Request All data--------------------------//
    public static ArrayList<LazyRsvModel> requestAll_LazyRSVData() {

        return Database.Database.getRSV_Data();
    }

    public static ArrayList<RsvModel> requestAll_RSVData() {
        ArrayList<RsvModel> rsvList = new ArrayList<>();
        ArrayList<LazyRsvModel> lazyRsvList
                = Database.Database.getRSV_Data();
        ArrayList<LazyBllchrgModel> billList
                = Database.Database.getBllchrg_Data();

        RsvModel newRsv = new RsvModel();
        for (int i = 0; i < lazyRsvList.size(); i++) {
            newRsv = new RsvModel();
            newRsv.setRsvID(lazyRsvList.get(i).getRsvID());
            newRsv.setDateArrive(lazyRsvList.get(i).getDateArrive());
            newRsv.setDateDepart(lazyRsvList.get(i).getDateDepart());
            newRsv.setDatePaid(lazyRsvList.get(i).getDatePaid());
            newRsv.setRsvType(lazyRsvList.get(i).getRsvType());
            //createRoomModel(RsvID);
            RoomModel room = creatRoomModel(lazyRsvList.get(i), newRsv);
            room.setRsv(newRsv);
            newRsv.setRoom(room);
            //getGuestModel
            newRsv.setGuest(creatGuestModel(lazyRsvList.get(i), newRsv));
            newRsv.setIsNoShow(lazyRsvList.get(i).isIsNoShow());
            newRsv.setIsPaid(lazyRsvList.get(i).isIsPaid());
            newRsv.setIsConcluded(lazyRsvList.get(i).isIsConcluded());
            rsvList.add(newRsv);
        }

        return rsvList;
    }
    //-----------------------<End> Request All data-----------------------//

    //-------------------------Request Lists of Data --------------------//
    public static ArrayList<RsvModel> requestRsvList(Date date) {
        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (EntityDatabase.dateIsBetween(rsvList.get(i).getDateArrive(),
                    rsvList.get(i).getDateDepart(), date)) {
                filteredList.add(rsvList.get(i));
            }
        }

        return filteredList;
    }

    public static ArrayList<RsvModel> requestRsvList(Date dateStart,
            Date dateEnd) {
        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)) {
                filteredList.add(rsvList.get(i));
            }
        }

        return filteredList;
    }

    public static ArrayList<RsvModel> requestRsvIsPaidList() {
        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (rsvList.get(i).isIsPaid()) {
                filteredList.add(rsvList.get(i));
            }
        }
        return filteredList;
    }

    //Probably will need to be re written
    public static ArrayList<RsvModel> requestRsvIsPaidList(Date dateStart,
            Date dateEnd) {

        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)
                    && rsvList.get(i).isIsPaid()) {
                filteredList.add(rsvList.get(i));
            }
        }

        return filteredList;

    }

    public static ArrayList<RsvModel> requestRsvIsNotPaidList() {
        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (!rsvList.get(i).isIsPaid()) {
                filteredList.add(rsvList.get(i));
            }
        }
        return filteredList;
    }

    //Probably will need to be re written
    public static ArrayList<RsvModel> requestRsvIsNotPaidList(Date dateStart,
            Date dateEnd) {

        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)
                    && !rsvList.get(i).isIsPaid()) {
                filteredList.add(rsvList.get(i));
            }
        }

        return filteredList;

    }

    public static ArrayList<RsvModel> requestRsvIsNoShowList() {
        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (rsvList.get(i).isIsNoShow()) {
                filteredList.add(rsvList.get(i));
            }
        }
        return filteredList;
    }

    //Probably will need to be re written
    public static ArrayList<RsvModel> requestRsvIsNoShowList(Date dateStart,
            Date dateEnd) {

        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)
                    && rsvList.get(i).isIsNoShow()) {
                filteredList.add(rsvList.get(i));
            }
        }

        return filteredList;

    }

    public static ArrayList<RsvModel> requestRsvIsConcludedList() {
        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (rsvList.get(i).isIsConcluded()) {
                filteredList.add(rsvList.get(i));
            }
        }
        return filteredList;
    }

    //Probably will need to be re written
    public static ArrayList<RsvModel> requestRsvIsPaidConcluded(Date dateStart,
            Date dateEnd) {

        ArrayList<RsvModel> filteredList = new ArrayList<>();
        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (EntityDatabase.dateOverlap(rsvList.get(i).getDateArrive(),
                    rsvList.get(i).getDateDepart(), dateStart, dateEnd)
                    && rsvList.get(i).isIsPaid()) {
                filteredList.add(rsvList.get(i));
            }
        }

        return filteredList;

    }
    //-----------------------<END>Request Lists of Data ------------------//

    //-------------------------Single Model Request --------------------//
    public static RsvModel requestRSV(int rsvID) {
        RsvModel reqRsv = new RsvModel();

        ArrayList<RsvModel> rsvList = requestAll_RSVData();

        for (int i = 0; i < rsvList.size(); i++) {
            if (rsvList.get(i).getRsvID() == rsvID) {
                reqRsv = rsvList.get(i);
                i = rsvList.size() + 1;
            }
        }
        return reqRsv;
    }

    //-----------------------<END> Single Model Reques-------------------//
}
