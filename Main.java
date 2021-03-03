//package com.timezone;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;

public class Main {
    final static String[] timezones = {"GMT", "UTC", "ECT", "EET", "ART", "EAT", "MET", "NET", "PLT", "IST", "BST", "VST", "CTT", "JST", "JST", "ACT", "AET", "SST", "NST", "MIT", "HST", "AST", "PST", "PNT", "MST", "CST", "EST", "IET", "PRT", "CNT", "AGT", "BET", "CAT"};

    static Scanner input = new Scanner(System.in);

    //        convert timezones array to List
    static List<String> timeZonesList = Arrays.asList(timezones);

    public static void main(String[] args) {
        System.out.println("Welcome to timezone checker");
        showMenu();
    }
    
    public static void showMenu(){
        System.out.println("\t1.Check timezone\n\t2.Exit");
        String choice = input.nextLine();

        while (!choice.equals("1") && !choice.equals("2")){
            System.out.println("Enter correct choice");
            System.out.println("\t1.Check timezone\n\t2.Exit");
            choice = input.nextLine();
        }

        switch (choice){
            case "1":
                compareTimeZones();
                break;
            case "2":
                System.exit(0);
                break;
            default:
                showMenu();
        }
    }

    public static void compareTimeZones(){
        //        get starting timezone
        boolean correctStarting = false;
        String starting = null;

        while (!correctStarting){
            System.out.print("Starting time zone: ");
            starting = input.nextLine();

//            check if entered timezone is in list
            if (checkIfTimezoneIncluded(starting)) {
                correctStarting = true;
            } else {
                System.out.println("Your timezone is not included. Please another time zone");
            }
        }

        //        get starting timezone
        boolean correctEnding = false;
        String ending = null;

        while (!correctEnding){
            System.out.print("Ending time zone: ");
            ending = input.nextLine();

//            check if entered timezone is in list
            if (checkIfTimezoneIncluded(ending)) {
                correctEnding = true;
            } else {
                System.out.println("Your timezone is not included. Please use another time zone");
            }
        }

        String startingZone =  getZoneId(starting);
        String endingZone = getZoneId(ending);

        System.out.println(startingZone);
        System.out.println(endingZone);

        if (startingZone.equals(null)){
            System.out.println("Starting timezone did not match any timezones");
            showMenu();
        }

        if (endingZone.equals(null)){
            System.out.println("Starting timezone did not match any timezones");
            showMenu();
        }

        ZoneId zone1 = ZoneId.of(startingZone);
        ZoneId zone2 = ZoneId.of(endingZone);
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime panamaDateTime = ZonedDateTime.of(dateTime, zone1);
        ZonedDateTime taipeiDateTime = panamaDateTime.withZoneSameInstant(zone2);

        System.out.println("Starting timezone: " + startingZone);
        System.out.println("Ending timezone: " + endingZone);
        int[] time = splitToComponentTimes(taipeiDateTime.getOffset().getTotalSeconds());
        String output = "";
        if (time[0] != 0){
            output += "+" + time[0] + " hours";
        }

        if (time[1] != 0){
            output += " " + time[1] + " minutes";
        }

        if (time[2] != 0){
            output += " " + time[2] + " seconds";
        }

        if (output.equals("")){
            output = "0 hours";
        }

        System.out.println("Difference between two time zones is = " + output);


//        show the menu again
        showMenu();
    }

    public static int[] splitToComponentTimes(long seconds)
    {
        int hours = (int) seconds / 3600;
        int remainder = (int) seconds - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours , mins , secs};
        return ints;
    }

    public static String getZoneId(String shortName){
        final String[] zoneString = {null};
        ZoneId.getAvailableZoneIds().forEach(zone -> {
            ZoneId id = ZoneId.of(zone);
            String zoneShort = id.getDisplayName(TextStyle.SHORT, Locale.US);
            System.out.println(zoneShort);
            if (zoneShort.equals(shortName)) {
                zoneString[0] =  zone;
            }
        });
        assert false;
        return zoneString[0];
    }

    public static boolean checkIfTimezoneIncluded(String timezone){
        return timeZonesList.contains(timezone);
    }
}
