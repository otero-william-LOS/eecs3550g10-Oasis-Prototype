/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.data.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import prototype.data.models.BillChargeModel;
import prototype.data.models.GuestModel;
import prototype.data.models.RateModel;
import prototype.data.models.ReservationModel;
import prototype.data.models.ReservationType;
import prototype.data.models.RoomModel;

/**
 *
 * @author jon
 */
public class DatabaseCsvFileIO {
    // protected tables for csv import, and export
    private final static List<CsvUtility.LazyRsv> TBL_LAZY_RSV = new ArrayList<>();
    private final static List<CsvUtility.LazyRoom> TBL_LAZY_ROOM = new ArrayList<>();
    private final static List<CsvUtility.LazyRate> TBL_LAZY_RATE = new ArrayList<>();
    private final static List<CsvUtility.LazyGuest> TBL_LAZY_GUEST = new ArrayList<>();
    private final static List<CsvUtility.LazyBllChrg> TBL_LAZY_BLLCHRG = new ArrayList<>();
    
    //  CSV Table FilePaths
    public final static String RSVPATH = "reservationTable.csv";
    public final static String ROOMPATH = "roomTable.csv";
    public final static String RATEPATH = "rateTable.csv";
    public final static String GUESTPATH = "guestTable.csv";
    public final static String BLLCHRGPATH = "billchargeTable.csv";
    
    protected final static void exportEntityTables(final List<ReservationModel> rsvEntities,final List<RoomModel> roomEntities,final List<RateModel> rateEntities,final List<GuestModel> guestEntities,final List<BillChargeModel> bllchrgEntities){
        // write entities
        CsvUtility.writeReservationEntityTable(rsvEntities);
        CsvUtility.writeRoomEntityTable(roomEntities);
        CsvUtility.writeRateEntityTable(rateEntities);
        CsvUtility.writeGuestEntityTable(guestEntities);
        CsvUtility.writeBillChargeEntityTable(bllchrgEntities);
    }
    protected final static void importCsvFileTables(final List<ReservationModel> rsvEntities,final List<RoomModel> roomEntities,final List<RateModel> rateEntities,final List<GuestModel> guestEntities,final List<BillChargeModel> bllchrgEntities){
        // read in csv
        CsvUtility.readReservationCsvFile();
        CsvUtility.readRoomCsvFile();
        CsvUtility.readRateCsvFile();
        CsvUtility.readGuestCsvFile();
        CsvUtility.readBillChargeCsvFile();
        
        // clear entity table, and add first pass entity from lazy persistence
        rsvEntities.clear();
        TBL_LAZY_RSV.forEach(lzRsv -> rsvEntities.add(lzRsv.getFirstPassEntity()));
        roomEntities.clear();
        TBL_LAZY_ROOM.forEach(lzRm -> roomEntities.add(lzRm.getFirstPassEntity()));
        rateEntities.clear();
        TBL_LAZY_RATE.forEach(lzRt -> rateEntities.add(lzRt.getFirstPassEntity()));
        guestEntities.clear();
        TBL_LAZY_GUEST.forEach(lzGt -> guestEntities.add(lzGt.getFirstPassEntity()));
        bllchrgEntities.clear();
        TBL_LAZY_BLLCHRG.forEach(lzBll -> bllchrgEntities.add(lzBll.getFirstPassEntity()));
        
        // second pass entity reconnection
        CsvUtility.reconnectRsvRoomEntities(rsvEntities, roomEntities);
        CsvUtility.reconnectRsvGuestEntities(rsvEntities, guestEntities);
        CsvUtility.reconnectRsvBllChrgEntities(rsvEntities, bllchrgEntities);
    }
    
    // Private nested class for CSV Utilities
    private static final class CsvUtility{
        protected final static void writeReservationEntityTable(final List<ReservationModel> rsvEntities){
            FileWriter fileWriter;
            File file = new File(RSVPATH);
            try {
                //  File will always auto create/overwrite unless you specify otherwise
                fileWriter = new FileWriter(file); // open

                //  Header section
                fileWriter.write(LazyRsv.getCsvHeader());

                // convert to lazy persistence
                TBL_LAZY_RSV.clear();
                rsvEntities.forEach(rsv -> TBL_LAZY_RSV.add(new LazyRsv(rsv)));
                
                //  Body section
                StringBuilder csvBody = new StringBuilder();
                TBL_LAZY_RSV.forEach(lzRsv -> csvBody.append('\n').append(lzRsv));
                fileWriter.write(csvBody.toString());

                fileWriter.close(); // close
            } catch (IOException ex) {
               System.out.println("\nRsv Table Write Error!" + ex);
            }
        }
        protected final static void writeRoomEntityTable(final List<RoomModel> roomEntities){
            FileWriter fileWriter;
            File file = new File(ROOMPATH);
            try {
                //  File will always auto create/overwrite unless you specify otherwise
                fileWriter = new FileWriter(file); // open

                //  Header section
                fileWriter.write(LazyRoom.getCsvHeader());

                // convert to lazy persistence
                TBL_LAZY_ROOM.clear();
                roomEntities.forEach(rm -> TBL_LAZY_ROOM.add(new LazyRoom(rm)));
                
                //  Body section
                StringBuilder csvBody = new StringBuilder();
                TBL_LAZY_ROOM.forEach(lzRm -> csvBody.append('\n').append(lzRm));
                fileWriter.write(csvBody.toString());

                fileWriter.close(); // close
            } catch (IOException ex) {
               System.out.println("\nRoom Table Write Error!" + ex);
            }
        }
        protected final static void writeRateEntityTable(final List<RateModel> rateEntities){
            FileWriter fileWriter;
            File file = new File(RATEPATH);
            try {
                //  File will always auto create/overwrite unless you specify otherwise
                fileWriter = new FileWriter(file); // open

                //  Header section
                fileWriter.write(LazyRate.getCsvHeader());

                // convert to lazy persistence
                TBL_LAZY_RATE.clear();
                rateEntities.forEach(rate -> TBL_LAZY_RATE.add(new LazyRate(rate)));
                
                //  Body section
                StringBuilder csvBody = new StringBuilder();
                TBL_LAZY_RATE.forEach(lzRate -> csvBody.append('\n').append(lzRate));
                fileWriter.write(csvBody.toString());

                fileWriter.close(); // close
            } catch (IOException ex) {
               System.out.println("\nRate Table Write Error!" + ex);
            }
        }
        protected final static void writeGuestEntityTable(final List<GuestModel> guestEntities){
            FileWriter fileWriter;
            File file = new File(GUESTPATH);
            try {
                //  File will always auto create/overwrite unless you specify otherwise
                fileWriter = new FileWriter(file); // open

                //  Header section
                fileWriter.write(LazyGuest.getCsvHeader());

                // convert to lazy persistence
                TBL_LAZY_GUEST.clear();
                guestEntities.forEach(guest -> TBL_LAZY_GUEST.add(new LazyGuest(guest)));
                
                //  Body section
                StringBuilder csvBody = new StringBuilder();
                TBL_LAZY_GUEST.forEach(lzGuest -> csvBody.append('\n').append(lzGuest));
                fileWriter.write(csvBody.toString());

                fileWriter.close(); // close
            } catch (IOException ex) {
               System.out.println("\nGuest Table Write Error!" + ex);
            }
        }
        protected final static void writeBillChargeEntityTable(final List<BillChargeModel> bllchrgEntities){
            FileWriter fileWriter;
            File file = new File(BLLCHRGPATH);
            try {
                //  File will always auto create/overwrite unless you specify otherwise
                fileWriter = new FileWriter(file); // open

                //  Header section
                fileWriter.write(LazyBllChrg.getCsvHeader());

                // convert to lazy persistence
                TBL_LAZY_BLLCHRG.clear();
                bllchrgEntities.forEach(bllchrg -> TBL_LAZY_BLLCHRG.add(new LazyBllChrg(bllchrg)));
                
                //  Body section
                StringBuilder csvBody = new StringBuilder();
                TBL_LAZY_BLLCHRG.forEach(lzBllChrg -> csvBody.append('\n').append(lzBllChrg));
                fileWriter.write(csvBody.toString());

                fileWriter.close(); // close
            } catch (IOException ex) {
               System.out.println("\nBillCharge Table Write Error!" + ex);
            }
        }

        protected final static void readReservationCsvFile(){
                Scanner scnr;
                File csvFileTable = new File(RSVPATH);
            try {
                scnr = new Scanner(csvFileTable); //open 
                scnr.useDelimiter("\n");
                // read in csvHeader
                String csvHeader = (scnr.hasNext()) ? scnr.next() : "\t\tError reading rsv csvHeader";
                System.out.println("\t\tRead in csvHeader: " + csvHeader);
                // read in csvRows, and create new lazy persistence objs
                TBL_LAZY_RSV.clear();
                while (scnr.hasNext()) {
                    System.out.println("\t\tRead in rsv csvRow");
                    String csvRow = scnr.next();
                    TBL_LAZY_RSV.add(new LazyRsv(csvRow));
                }
                scnr.close(); //close
            } catch (FileNotFoundException ex) {
               System.out.println("\nRsv Table Read Error!" + ex);
            }
        }
        protected final static void readRoomCsvFile(){
                Scanner scnr;
                File csvFileTable = new File(ROOMPATH);
            try {
                scnr = new Scanner(csvFileTable); //open 
                scnr.useDelimiter("\n");
                // read in csvHeader
                String csvHeader = (scnr.hasNext()) ? scnr.next() : "\t\tError reading room csvHeader";
                System.out.println("\t\tRead in csvHeader: " + csvHeader);
                // read in csvRows, and create new lazy persistence 
                TBL_LAZY_ROOM.clear();
                while (scnr.hasNext()) {
                    System.out.println("\t\tRead in room csvRow");
                    String csvRow = scnr.next();
                    TBL_LAZY_ROOM.add(new LazyRoom(csvRow));
                }
                scnr.close(); //close
            } catch (FileNotFoundException ex) {
               System.out.println("\nRoom Table Read Error!" + ex);
            }
        }
        protected final static void readRateCsvFile(){
                Scanner scnr;
                File csvFileTable = new File(RATEPATH);
            try {
                scnr = new Scanner(csvFileTable); //open 
                scnr.useDelimiter("\n");
                // read in csvHeader
                String csvHeader = (scnr.hasNext()) ? scnr.next() : "\t\tError reading rate csvHeader";
                System.out.println("\t\tRead in csvHeader: " + csvHeader);
                // read in csvRows, and create new lazy persistence 
                TBL_LAZY_RATE.clear();
                while (scnr.hasNext()) {
                    System.out.println("\t\tRead in rate csvRow");
                    String csvRow = scnr.next();
                    TBL_LAZY_RATE.add(new LazyRate(csvRow));
                }
                scnr.close(); //close
            } catch (FileNotFoundException ex) {
               System.out.println("\nRate Table Read Error!" + ex);
            }
        }
        protected final static void readGuestCsvFile(){
                Scanner scnr;
                File csvFileTable = new File(GUESTPATH);
            try {
                scnr = new Scanner(csvFileTable); //open 
                scnr.useDelimiter("\n");
                // read in csvHeader
                String csvHeader = (scnr.hasNext()) ? scnr.next() : "\t\tError reading guest csvHeader";
                System.out.println("\t\tRead in csvHeader: " + csvHeader);
                // read in csvRows, and create new lazy persistence 
                TBL_LAZY_GUEST.clear();
                while (scnr.hasNext()) {
                    System.out.println("\t\tRead in guest csvRow");
                    String csvRow = scnr.next();
                    TBL_LAZY_GUEST.add(new LazyGuest(csvRow));
                }
                scnr.close(); //close
            } catch (FileNotFoundException ex) {
               System.out.println("\n Table Read Error!" + ex);
            }
        }
        protected final static void readBillChargeCsvFile(){
                Scanner scnr;
                File csvFileTable = new File(BLLCHRGPATH);
            try {
                scnr = new Scanner(csvFileTable); //open 
                scnr.useDelimiter("\n");
                // read in csvHeader
                String csvHeader = (scnr.hasNext()) ? scnr.next() : "\t\tError reading  csvHeader";
                System.out.println("\t\tRead in csvHeader: " + csvHeader);
                // read in csvRows, and create new lazy persistence 
                TBL_LAZY_BLLCHRG.clear();
                while (scnr.hasNext()) {
                    System.out.println("\t\tRead in  csvRow");
                    String csvRow = scnr.next();
                    TBL_LAZY_BLLCHRG.add(new LazyBllChrg(csvRow));
                }
                scnr.close(); //close
            } catch (FileNotFoundException ex) {
               System.out.println("\n Table Read Error!" + ex);
            }
        }

        protected final static void reconnectRsvRoomEntities(final List<ReservationModel> rsvEntities,final List<RoomModel> roomEntities){
            List<LazyRsv> fltrList = new ArrayList<>(TBL_LAZY_RSV);
            fltrList.removeIf(lzRsv -> (lzRsv.roomID == null || lzRsv.roomID.isEmpty()));
            fltrList.forEach(
                    lzRsv -> {
                        int rsvID = Integer.parseInt(lzRsv.rsvID);
                        ReservationModel rsv = 
                                rsvEntities.stream().filter(
                                        x -> x.getReservationID() == rsvID
                                ).findFirst().get();
                        short roomID = Short.parseShort(lzRsv.roomID);
                        RoomModel rm = 
                                roomEntities.stream().filter(
                                        x -> x.getRoomID() == roomID
                                ).findFirst().get();
                        rsv.setRoom(rm);
                        rm.setReservation(rsv);
                    }
            );
        }
        protected final static void reconnectRsvGuestEntities(final List<ReservationModel> rsvEntities,final List<GuestModel> guestEntities){
            List<LazyRsv> fltrList = new ArrayList<>(TBL_LAZY_RSV);
            fltrList.removeIf(lzRsv -> (lzRsv.guestID == null || lzRsv.guestID.isEmpty()));
            fltrList.forEach(
                    lzRsv -> {
                        int rsvID = Integer.parseInt(lzRsv.rsvID);
                        ReservationModel rsv = 
                                rsvEntities.stream().filter(
                                        x -> x.getReservationID() == rsvID
                                ).findFirst().get();
                        int guestID = Integer.parseInt(lzRsv.guestID);
                        GuestModel guest = 
                                guestEntities.stream().filter(
                                        x -> x.getGuestID() == guestID
                                ).findFirst().get();
                        rsv.setGuest(guest);
                        guest.getListRsv().add(rsv);
                    }
            );
        }
        protected final static void reconnectRsvBllChrgEntities(final List<ReservationModel> rsvEntities,final List<BillChargeModel> bllchrgEntities){
            List<LazyBllChrg> fltrList = new ArrayList<>(TBL_LAZY_BLLCHRG);
            fltrList.removeIf(lzBllchrg -> (lzBllchrg.rsvID == null || lzBllchrg.rsvID.isEmpty()));
            fltrList.forEach(
                    lzBllchrg -> {
                        int bllchrgID = Integer.parseInt(lzBllchrg.bllchrgID);
                        BillChargeModel bllchrg = 
                                bllchrgEntities.stream().filter(
                                        x -> x.getBillChargeID() == bllchrgID
                                ).findFirst().get();
                        int rsvID = Integer.parseInt(lzBllchrg.rsvID);
                        ReservationModel rsv = 
                                rsvEntities.stream().filter(
                                        x -> x.getReservationID() == rsvID
                                ).findFirst().get();
                        bllchrg.setReservation(rsv);
                        rsv.getListBillCharges().add(bllchrg);
                    }
            );
        }
        
        //  lazy string-only version of reservationModel
        protected final static class LazyRsv {
            public String rsvID = "";
            public String dateArrive = "";
            public String dateDepart = "";
            public String datePaid = "";
            public String rsvType = "";
            public String roomID = "";
            public String guestID = "";
            public String isNoShow = "";
            public String isPaid = "";
            public String isConcluded = "";
            
            public LazyRsv(final ReservationModel rsv){
                this.rsvID = Integer.toString(rsv.getReservationID());
                this.dateArrive = 
                        (rsv.getDateArrive() != null && !rsv.getDateArrive().equals(LocalDate.MIN)) 
                        ? rsv.getDateArrive().toString() : "";
                this.dateDepart = 
                        (rsv.getDateDepart() != null && !rsv.getDateDepart().equals(LocalDate.MIN)) 
                        ? rsv.getDateDepart().toString() : "";
                this.datePaid = 
                        (rsv.getDatePaid() != null && !rsv.getDatePaid().equals(LocalDate.MIN)) 
                        ? rsv.getDatePaid().toString() : "";
                this.rsvType = (rsv.getReservationType() != null) ? rsv.getReservationType().name() : "";
                this.roomID = 
                        (rsv.getRoom() != null && !rsv.getRoom().equals(RoomModel.EMPTY_ENTITY)) 
                        ? Integer.toString(rsv.getRoom().getRoomID()) : "";
                this.guestID = 
                        (rsv.getGuest() != null && !rsv.getGuest().equals(GuestModel.EMPTY_ENTITY)) 
                        ? Integer.toString(rsv.getGuest().getGuestID()) : "";
                this.isNoShow = Boolean.toString(rsv.isNoShow());
                this.isPaid = Boolean.toString(rsv.isPaid());
                this.isConcluded = Boolean.toString(rsv.isConcluded());
            }
            public LazyRsv(final String csvRow){
                String[] tknzdCsvRow = csvRow.split(",", -1);
                int numTokens = tknzdCsvRow.length;
                for(int index = 0; index < numTokens; index++){
                    String token = tknzdCsvRow[index];
                    //--------Determine what attribute you are on---------//
                    switch (index) {
                        case 0:
                            this.rsvID = token;
                            break;
                        case 1:
                            this.dateArrive = token;
                            break;
                        case 2:
                            this.dateDepart = token;
                            break;
                        case 3:
                            this.datePaid = token;
                            break;
                        case 4:
                            this.rsvType = token;
                            break;
                        case 5:
                            this.roomID = token;
                            break;
                        case 6:
                            this.guestID = token;
                            break;
                        case 7:
                            this.isNoShow = token;
                            break;
                        case 8:
                            this.isPaid = token;
                            break;
                        case 9:
                            this.isConcluded = token;
                            break;
                        default:
                            System.out.println("Oh no! Messed up reading a rsvCsvRow!");
                            break;
                    }
                }
            }
            
            //  Conversion from Lazy to Entity w/o relations
            public ReservationModel getFirstPassEntity(){
                ReservationModel rsv = new ReservationModel();
                rsv.setReservationID(Integer.parseInt(this.rsvID));
                if (this.dateArrive != null && !this.dateArrive.isEmpty())
                    rsv.setDateArrive(LocalDate.parse(this.dateArrive));
                if (this.dateDepart != null && !this.dateDepart.isEmpty())
                    rsv.setDateDepart(LocalDate.parse(this.dateDepart));
                if (this.datePaid != null && !this.datePaid.isEmpty())
                    rsv.setDatePaid(LocalDate.parse(this.datePaid));
                if (this.rsvType != null && !this.rsvType.isEmpty())
                    rsv.setReservationType(Enum.valueOf(ReservationType.class, this.rsvType));
                rsv.setIsNoShow(Boolean.valueOf(this.isNoShow));
                rsv.setIsPaid(Boolean.valueOf(this.isPaid));
                rsv.setIsConcluded(Boolean.valueOf(this.isConcluded));
                return rsv;
            }
            public final static String getCsvHeader(){
                StringBuilder csvHeader = new StringBuilder();
                csvHeader.append("ReservationID,DateArrive,DateDepart,DatePaid,");
                csvHeader.append("ReservationType,RoomID,GuestID,");
                csvHeader.append("IsNoShow,IsPaid,IsConcluded");
                return csvHeader.toString();
            }

            @Override
            public int hashCode() {
                int hash = 5;
                hash += hash * 19 + rsvID.hashCode();
                return hash;
            }
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof LazyRsv)) {
                    return false;
                }        
                LazyRsv other = (LazyRsv) obj;        
                return (this.rsvID.equals(other.rsvID));
            }
            @Override
            public String toString() {
                String csvRow = rsvID;
                csvRow += ",";
                csvRow += dateArrive;
                csvRow += ",";
                csvRow += dateDepart;
                csvRow += ",";
                csvRow += datePaid;
                csvRow += ",";
                csvRow += rsvType;
                csvRow += ",";
                csvRow += roomID;
                csvRow += ",";
                csvRow += guestID;
                csvRow += ",";
                csvRow += isNoShow;
                csvRow += ",";
                csvRow += isPaid;
                csvRow += ",";
                csvRow += isConcluded;
                return csvRow;
            }
        }
        //  lazy string-only version of roomModel
        protected final static class LazyRoom {
            public String roomID = "";
            public String isOccupied = "";
            
            public LazyRoom(final RoomModel rm){
                this.roomID = Short.toString(rm.getRoomID());
                this.isOccupied = Boolean.toString(rm.isOccupied());
            }
            public LazyRoom(final String csvRow){
                String[] tknzdCsvRow = csvRow.split(",", -1);
                int numTokens = tknzdCsvRow.length;
                for(int index = 0; index < numTokens; index++){
                    String token = tknzdCsvRow[index];
                    //--------Determine what attribute you are on---------//
                    switch (index) {
                        case 0:
                            this.roomID = token;
                            break;
                        case 1:
                            this.isOccupied = token;
                            break;
                        default:
                            System.out.println("Oh no! Messed up reading a roomCsvRow!");
                            break;
                    }
                }
            }
            
            public RoomModel getFirstPassEntity(){
                RoomModel rm = new RoomModel();
                rm.setRoomID(Short.parseShort(this.roomID));
                rm.setIsOccupied(Boolean.valueOf(this.isOccupied));
                return rm;
            }
            public final static String getCsvHeader(){
                StringBuilder csvHeader = new StringBuilder();
                csvHeader.append("RoomID,IsOccupied");
                return csvHeader.toString();
            }

            @Override
            public int hashCode() {
                int hash = 7;
                hash += hash * 17 + roomID.hashCode();
                return hash;
            }
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof LazyRoom)) {
                    return false;
                }
                LazyRoom other = (LazyRoom) obj;
                return (this.roomID.equals(other.roomID));
            }
            @Override
            public String toString() {
                String csvRow = roomID;
                csvRow += ",";
                csvRow += isOccupied;
                return  csvRow;
            }
        }
        //  lazy string-only version of rateModel
        protected final static class LazyRate{
            public String rateDate = "";
            public String baseRate = "";
            
            public LazyRate(final RateModel rt){
                this.rateDate = rt.getRateDate().toString();
                this.baseRate = Double.toString(rt.getBaseRate());
            }
            public LazyRate(final String csvRow){
                String[] tknzdCsvRow = csvRow.split(",", -1);
                int numTokens = tknzdCsvRow.length;
                for(int index = 0; index < numTokens; index++){
                    String token = tknzdCsvRow[index];
                    //--------Determine what attribute you are on---------//
                    switch (index) {
                        case 0:
                            this.rateDate = token;
                            break;
                        case 1:
                            this.baseRate = token;
                            break;
                        default:
                            System.out.println("Oh no! Messed up reading a rateCsvRow!");
                            break;
                    }
                }
            }
            
            public RateModel getFirstPassEntity(){
                RateModel rt = new RateModel();
                rt.setRateDate(LocalDate.parse(rateDate));
                rt.setBaseRate(Double.parseDouble(baseRate));
                return rt;
            }
            public final static String getCsvHeader(){
                StringBuilder csvHeader = new StringBuilder();
                csvHeader.append("RateDate,BaseRate");
                return csvHeader.toString();
            }
    
            // Overrides
            @Override
            public int hashCode() {
                int hash = 3;
                hash += hash * 11 + rateDate.hashCode();
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof LazyRate)) {
                    return false;
                }
                LazyRate other = (LazyRate) obj;
                return (this.rateDate.equals(other.rateDate));
            }

            @Override
            public String toString() {
                String csvRow = rateDate;
                csvRow += ",";
                csvRow += baseRate;
                return  csvRow;
            }
        }
        //  lazy string-only version of guestModel
        protected final static class LazyGuest{
            public String guestID = "";
            public String name = "";
            public String ccInfo = "";
            public String email = "";
            
            public LazyGuest(final GuestModel guest){
                this.guestID = Integer.toString(guest.getGuestID());
                this.name = guest.getName();
                this.ccInfo = guest.getCCInfo();
                this.email = guest.getEmail();
            }
            public LazyGuest(final String csvRow){
                String[] tknzdCsvRow = csvRow.split(",", -1);
                int numTokens = tknzdCsvRow.length;
                for(int index = 0; index < numTokens; index++){
                    String token = tknzdCsvRow[index];
                    //--------Determine what attribute you are on---------//
                    switch (index) {
                        case 0:
                            this.guestID = token;
                            break;
                        case 1:
                            this.name = token;
                            break;
                        case 2:
                            this.ccInfo = token;
                            break;
                        case 3:
                            this.email = token;
                            break;
                        default:
                            break;
                    }
                }
            }
            
            public GuestModel getFirstPassEntity(){
                GuestModel guest = new GuestModel();
                guest.setGuestID(Integer.parseInt(guestID));
                guest.setName(name);
                guest.setCCInfo(ccInfo);
                guest.setEmail(email);
                return guest;
            }
            public final static String getCsvHeader(){
                StringBuilder csvHeader = new StringBuilder();
                csvHeader.append("GuestID,Name,CCInfo,Email");
                return csvHeader.toString();
            }
    
            // Overrides
            @Override
            public int hashCode() {
                int hash = 1;
                hash += hash * 23 + guestID.hashCode();
                return hash;
            }
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof LazyGuest)) {
                    return false;
                }        
                LazyGuest other = (LazyGuest) obj;        
                return (this.guestID.equals(other.guestID));
            }
            @Override
            public String toString() {
                String csvRow = guestID;
                csvRow += ",";
                csvRow += name;
                csvRow += ",";
                csvRow += email;
                csvRow += ",";
                csvRow += ccInfo;
                return  csvRow;
            }
        }
        //  lazy string-only version of bllchrgModel
        protected final static class LazyBllChrg{
            public String bllchrgID = "";
            public String rsvID = "";
            public String lineDesc = "";
            public String amount = "";
            public String dateCharged = "";
            public String datePaid = "";
            public String isPaid = "";
            
            public LazyBllChrg(final BillChargeModel bllchrg){
                this.bllchrgID = Integer.toString(bllchrg.getBillChargeID());
                this.rsvID = 
                        (bllchrg.getReservation() != null 
                        && !bllchrg.getReservation().equals(ReservationModel.EMPTY_ENTITY)) 
                        ? Integer.toString(bllchrg.getReservation().getReservationID()) : "";
                this.lineDesc = bllchrg.getLineDescription();
                this.amount = Double.toString(bllchrg.getAmount());
                this.dateCharged = 
                        (bllchrg.getDateCharged() != null && !bllchrg.getDateCharged().equals(LocalDate.MIN)) 
                        ? bllchrg.getDateCharged().toString() : "";
                this.datePaid = 
                        (bllchrg.getDatePaid() != null && !bllchrg.getDatePaid().equals(LocalDate.MIN)) 
                        ? bllchrg.getDatePaid().toString() : "";
                this.isPaid = Boolean.toString(bllchrg.isPaid());
            }
            public LazyBllChrg(final String csvRow){
                String[] tknzdCsvRow = csvRow.split(",", -1);
                int numTokens = tknzdCsvRow.length;
                for(int index = 0; index < numTokens; index++){
                    String token = tknzdCsvRow[index];
                    //--------Determine what attribute you are on---------//
                    switch (index) {
                        case 0:
                            this.bllchrgID = token;
                            break;
                        case 1:
                            this.rsvID =  token;
                            break;
                        case 2:
                            this.lineDesc = token;
                            break;
                        case 3:
                            this.amount = token;
                            break;
                        case 4:
                            this.dateCharged = token;
                            break;
                        case 5:
                            this.datePaid = token;
                            break;
                        case 6:
                            this.isPaid = token;
                            break;
                        default:
                            break;
                    }
                }
            }

            public BillChargeModel getFirstPassEntity(){
                BillChargeModel bllchrg = new BillChargeModel();
                    bllchrg.setBillChargeID(Integer.parseInt(bllchrgID));
                    bllchrg.setLineDescription(lineDesc);
                    bllchrg.setAmount(Double.parseDouble(amount));
                    bllchrg.setDateCharged(LocalDate.parse(dateCharged));
                    bllchrg.setDatePaid(LocalDate.parse(datePaid));
                    bllchrg.setIsPaid(Boolean.valueOf(isPaid));
                return bllchrg;
            }
            public final static String getCsvHeader(){
                StringBuilder csvHeader = new StringBuilder();
                csvHeader.append("BillChargeID,ReservationID,LineDescription,");
                csvHeader.append("Amount,DateCharged,DatePaid,IsPaid");
                return csvHeader.toString();
            }
    
            // Overrides
            @Override
            public int hashCode() {
                int hash = 2;
                hash += hash * 13 + bllchrgID.hashCode();
                return hash;
            }
            @Override
            public boolean equals(Object obj) {
                if (!(obj instanceof LazyBllChrg)) {
                    return false;
                }        
                LazyBllChrg other = (LazyBllChrg) obj;        
                return (this.bllchrgID.equals(other.bllchrgID));
            }
            @Override
            public String toString() {
                String info = bllchrgID;
                info += ",";
                info += rsvID;
                info += ",";
                info += lineDesc;
                info += ",";
                info += amount;
                info += ",";
                info += dateCharged;
                info += ",";
                info += datePaid;
                info += ",";
                info += isPaid;
                return  info;
            }
        }
    }
}