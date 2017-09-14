package sample.main.mUtility;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.lang.Thread.sleep;
import static sample.main.mUtility.mLocalStrings.APPDATA_MAIN_FOLDER;
import static sample.main.mUtility.mLocalStrings.DATABASE_FODLER;
import static sample.main.mUtility.mLocalStrings.FILE_LOGS_FOLDER;
import static sample.main.mframeWork.StageManager.getStage;

public class mLocalMethods {

    private mLocalMethods() {
        new Exception("This class can not be instatiated");
    }


    /**
     * This is will keep all the files system in check every time the system is started and resumed
     * */
    public static final void createAppDataFolder() {

        File mainfolder = new File( APPDATA_MAIN_FOLDER);
//        creating a new folder for APPDATA_MAIN_FOLDER
        if (!mainfolder.exists()) {
            mainfolder.mkdir();
//            create database folder
            new File(DATABASE_FODLER).mkdir();
//            create log folder for the system to use to read and make writes
            new File(FILE_LOGS_FOLDER).mkdir();



        }


    }

    /**
     * will generate the id that are unuqi that are based on time
     *
     * @return String
     */
    public static String localIDMaker() {
        char[] alpha = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
        String currentYear = LocalDateTime.now().getYear() + "";
        currentYear = currentYear.substring(2);
        String currentMonth = LocalDateTime.now().getMonth().getValue() + "";
        String currentDayOfWeek = LocalDateTime.now().getDayOfWeek().getValue() + "";
        String currentHour = LocalDateTime.now().getHour() + "";
        currentHour = currentHour.length() == 1 ? 0 + currentHour : currentHour;
        String currentMins = LocalDateTime.now().getMinute() + "";
        currentMins = currentMins.length() == 1 ? 0 + currentMins : currentMins;
        String currentSecs = LocalDateTime.now().getSecond() + "";
        currentSecs = currentSecs.length() == 1 ? 0 + currentSecs : currentSecs;
        // 2017, AUGUST, WEDNESDAY, 21, 34, 41
        // c 17 8 3 21 34 41 x
        return alpha[randomInt(0, alpha.length - 1)] + currentYear + currentMonth + currentDayOfWeek + currentHour + currentMins + currentSecs;

    }

    /**
     * Random number generator in interger
     *
     * @param min
     * @param max
     * @return integer value
     */
    public static int randomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
/**
 * generates a password
 * @return String
 * */
    public static String passwordGenerator() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        String password = passwordGenerator.generate(8); // output ex.: lrU12fmM 75iwI90o
        return password;
    }

    /**
     * This  is  a java 8 version or also known as the joda-time notation
     *
     * @return 1424812121078
     */
    public static long getCurrentTime() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static String registrationDate(){
        return LocalDateTime.now().getYear()+"-"+
                LocalDateTime.now().getMonth()+"-"+
                LocalDateTime.now().getDayOfMonth();
    }

    /**
     * will make the current stage to a larger screen if not yet set to full screeen
     */
    public static void setLargeScreen() {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        getStage().setX(bounds.getMinX());
        getStage().setY(bounds.getMinY());
        getStage().setWidth(bounds.getWidth());
        getStage().setHeight(bounds.getHeight());
    }

    /**
     * Re-suggest a list of reg numbers
     * @return list of newer six registration number
     */
    public static List<String>  reSuggestRegNumbers(){
        List<String> listOfRegNumbers = new ArrayList<>();
        Set<String> check = new HashSet<>();
        for (int i = 0; i < 6 ; i++) {
            listOfRegNumbers.add(localIDMaker());
        }
        check.addAll(listOfRegNumbers);
        return listOfRegNumbers;
    }

    /**
     * Will get a list of countries in order from A - Z<br>
     *  if passed parameter is false then a Current country is returned so you will have to get at index 0(Zero)
     * @param isUnSelected
     * @return List of String with country names
     */
    public static List<String> getCountriesList(boolean isUnSelected){
         List<String> countriesList = new ArrayList<>();
         if(isUnSelected) {
             String[] locales = Locale.getISOCountries();
             for (String countryCode : locales) {
                 Locale obj = new Locale("", countryCode);
                 countriesList.add(obj.getDisplayCountry(Locale.ENGLISH));
             }
             Collections.sort(countriesList);
         }else{
             countriesList.add(""+Locale.getDefault().getDisplayCountry());
         }

         return countriesList;

    }

    public static void main(String[] sss) {

       // createAppDataFolder();


        System.out.print(reSuggestRegNumbers());

    }

}
