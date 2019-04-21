/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import Database.LazyGuestModel;
import Models.GuestModel;
import java.util.ArrayList;

/**
 *
 * @author cmason12
 */
public class GuestEntity extends  Database.Database {

      protected static ArrayList<LazyGuestModel> guestToLazyList(ArrayList<GuestModel> inList){
          ArrayList<LazyGuestModel> list = new ArrayList<>();
          
          
          for (int i =0; i < inList.size(); i++){
              LazyGuestModel temp = new LazyGuestModel();
              
              temp.setGuestID(inList.get(i).getGuestID());
              temp.setName(inList.get(i).getName());
              temp.setCCInfo(inList.get(i).getCCInfo());
              temp.setEmail(inList.get(i).getEmail());
             
              list.add(temp);
          }
          
          return list;
      }
    //------------------Request All ------------------------------//
     public static ArrayList<LazyGuestModel> requestAll_LazyGuestData() {
        return Database.Database.getGUEST_Data();
    }
    public static ArrayList<GuestModel> requestAll_GuestData() {
        ArrayList<GuestModel> guestList = new ArrayList<>();
        ArrayList<LazyGuestModel> lazyGuestList =
                Database.Database.getGUEST_Data();

        GuestModel newGuest;
        for (int i = 0; i < lazyGuestList.size(); i++) {
            newGuest = new GuestModel();
            newGuest.setGuestID(lazyGuestList.get(i).getGuestID());
            newGuest.setName(lazyGuestList.get(i).getName());
            newGuest.setCCInfo(lazyGuestList.get(i).getCCInfo());
            newGuest.setEmail(lazyGuestList.get(i).getEmail());

            guestList.add(newGuest);
        }

        return guestList;
    }
    //------------------<END> Request All ------------------------------//   

    //----------------------Request Lists---------------------------//
    public static ArrayList<GuestModel> requestGuestNameList(String name) {
        ArrayList<GuestModel> reqList = new ArrayList<>();

        ArrayList<GuestModel> guestList = requestAll_GuestData();

        for (int i = 0; i < guestList.size(); i++) {
            if (guestList.get(i).getName().contains(name)
                    || guestList.get(i).getName().equals(name)) {
                reqList.add(guestList.get(i));
            }
        }
        return reqList;
    }

    public static ArrayList<GuestModel> requestGuestEmailList(String email) {
        ArrayList<GuestModel> reqList = new ArrayList<>();

        ArrayList<GuestModel> guestList = requestAll_GuestData();

        for (int i = 0; i < guestList.size(); i++) {
            if (guestList.get(i).getEmail().contains(email)
                    || guestList.get(i).getEmail().equals(email)) {
                reqList.add(guestList.get(i));
            }
        }
        return reqList;
    }

    //----------------------<END> Request Lists---------------------------//
    //------------------Request Single Model------------------------//
    public static GuestModel requestGuest(int guestID) {
        GuestModel guest = new GuestModel();

        ArrayList<GuestModel> guestList = requestAll_GuestData();

        for (int i = 0; i < guestList.size(); i++) {
            if (guestList.get(i).getGuestID() == guestID) {
                guest = guestList.get(i);
            }
        }
        return guest;
    }
    //------------------<END> Request Single Model------------------------//
}
