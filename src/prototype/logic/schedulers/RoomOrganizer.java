/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.logic.schedulers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import prototype.data.drivers.ReservationDriver;
import prototype.data.drivers.RoomDriver;
import prototype.data.models.DataModel;
import prototype.data.models.ReservationModel;
import prototype.data.models.RoomModel;

/**
 *
 * @author jon
 */
public class RoomOrganizer implements Scheduler {
    
    //  responsibility
    public static void taskArrvlRoomAsgnmnt(final List<ReservationModel> listRsv) {
        Random rndm = new Random();
        List<RoomModel> listVacantRm = RoomDriver.returnVacantRooms();
        System.out.println("\t:Print Avbl Rooms");
        listVacantRm.forEach(x -> System.out.println("\t\t" + x));
        listRsv.stream().forEach(
                (ReservationModel rsv) -> {
                    int x = rndm.nextInt(listVacantRm.size());
                    RoomModel rm = listVacantRm.get(x);
                    System.out.println("\tLink and Remove Rndm avblRoom @Index: " + Integer.toString(x));
                    ReservationDriver.attachRoom(rsv, rm);
                    RoomDriver.attachReservation(rm, rsv);
                    RoomDriver.flagRoomAsOccupied(rm);
                    listVacantRm.remove(x);
            }
        );
    }
    public static void initDailyAutoTask() {
        LocalDate arrv = LocalDate.now();
        List<ReservationModel> listArrvRsv = ReservationDriver.searchByDateArrive(arrv);
        System.out.println("\t:Print Arrvl Rsv");
        listArrvRsv.forEach(x -> System.out.println("\t\t" + x));
        taskArrvlRoomAsgnmnt(listArrvRsv);
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
        //  TODO will be updated as new user stories are created.
    }

    @Override
    public void runModuleUserStories() {
        //  TODO will be updated as new user stories are created.
    }
    
}