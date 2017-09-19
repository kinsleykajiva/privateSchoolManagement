package sample.main.mUtility;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.main.mUtility.mLocalStrings.*;
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

    /**
     * Validate date format with regular expression
     * @param dd_mm_yyyy in the format dd/mm/yyyy
     * @return true valid date format, false invalid date format
     */
    public static boolean isDateValid(final String dd_mm_yyyy){
        Pattern  pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(dd_mm_yyyy);

        if(matcher.matches()){

            matcher.reset();

            if(matcher.find()){

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") &&(month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") || month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        if(day.equals("30") || day.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * example :answer = isType("10", "float"); //will return true;<br>
     *  answer = isType("1.0", "float"); //will return true;<br/>
     *  answer = isType("blah", "int"); //will return false;
     * @param testStr
     * @param type
     * @return boolean result
     */
    public static boolean isType(String testStr, String type) {
        try {
            if (type.equalsIgnoreCase("float")) {
                Float.parseFloat(testStr);
            } else if (type.equalsIgnoreCase("int")) {
                Integer.parseInt(testStr);
            } else if (type.equalsIgnoreCase("double")) {
                Double.parseDouble(testStr);
            }
            return true;
        } catch(Exception e) {
            return false;
        }

    }
    /*
    get the system users honme directory <br/>
    for example  C:\Users\Kajiva Kinsley
     */
    public static String getUserHomeDirectory() {
        String homePath = null;

        try {
            homePath = System.getProperty("user.home", "/");
        } catch (Exception e) {

            homePath = "/";
        }

        return Paths.get(homePath).toAbsolutePath().toString();
    }
    public static void main(String[] sss) {
        System.out.print(LocalDate.now().getYear());


    }

}
