package sample.main.mUtility;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.main.mUtility.mLocalStrings.*;
import static sample.main.mframeWork.StageManager.getStage;

public class mLocalMethods {

    private mLocalMethods () {
        new Exception("This class can not be instatiated");
    }


    /**
     * This is will keep all the files system in check every time the system is started and resumed
     */
    public static final void createAppDataFolder () {

        File mainfolder = new File(APPDATA_MAIN_FOLDER);
//        creating a new folder for APPDATA_MAIN_FOLDER
        if (! mainfolder.exists()) {
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
    public static String localIDMaker () {
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


    public static String localTransactionIDMaker () {
        char[] alpha = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
        String currentYear = LocalDateTime.now().getYear() + "";
        currentYear = currentYear.substring(2);
        String currentMonth = LocalDateTime.now().getMonth().getValue() + "";
        String currentDayOfWeek = LocalDateTime.now().getDayOfWeek() + "";
        currentDayOfWeek = currentDayOfWeek.substring(0, currentDayOfWeek.length() - (currentDayOfWeek.length() - 1));
        String currentHour = LocalDateTime.now().getHour() + "";
        currentHour = currentHour.length() == 1 ? 0 + currentHour : currentHour;
        String currentMins = LocalDateTime.now().getMinute() + "";
        currentMins = currentMins.length() == 1 ? 0 + currentMins : currentMins;
        String currentSecs = LocalDateTime.now().getSecond() + "";
        currentSecs = currentSecs.length() == 1 ? 0 + currentSecs : currentSecs;
        // psm x 41 34 21 w 3 8 17
        return ("psm-" + alpha[randomInt(0, alpha.length - 1)] + currentSecs + currentMins + currentHour + currentDayOfWeek + currentMonth + currentYear).toUpperCase();

    }

    /**
     * Random number generator in interger
     *
     * @param min
     * @param max
     * @return integer value
     */
    public static int randomInt (int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * generates a password
     *
     * @return String
     */
    public static String passwordGenerator () {
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
    public static long getCurrentTime () {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String registrationDate () {
        return LocalDateTime.now().getYear() + "-" +
                LocalDateTime.now().getMonth() + "-" +
                LocalDateTime.now().getDayOfMonth();
    }

    /**
     * will make the current stage to a larger screen if not yet set to full screeen
     */
    public static void setLargeScreen () {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        getStage().setX(bounds.getMinX());
        getStage().setY(bounds.getMinY());
        getStage().setWidth(bounds.getWidth());
        getStage().setHeight(bounds.getHeight());
    }

    /**
     * Re-suggest a list of reg numbers
     *
     * @return list of newer six registration number
     */
    public static List<String> reSuggestRegNumbers () {
        List<String> listOfRegNumbers = new ArrayList<>();
        Set<String> check = new HashSet<>();
        for (int i = 0; i < 6; i++) {
            listOfRegNumbers.add(localIDMaker());
        }
        check.addAll(listOfRegNumbers);
        return listOfRegNumbers;
    }

    /**
     * Will get a list of countries in order from A - Z<br>
     * if passed parameter is false then a Current country is returned so you will have to get at index 0(Zero)
     *
     * @param isUnSelected
     * @return List of String with country names
     */
    public static List<String> getCountriesList (boolean isUnSelected) {
        List<String> countriesList = new ArrayList<>();
        if (isUnSelected) {
            String[] locales = Locale.getISOCountries();
            for (String countryCode : locales) {
                Locale obj = new Locale("", countryCode);
                countriesList.add(obj.getDisplayCountry(Locale.ENGLISH));
            }
            Collections.sort(countriesList);
        } else {
            countriesList.add("" + Locale.getDefault().getDisplayCountry());
        }

        return countriesList;

    }

    /**
     * Validate date format with regular expression
     *
     * @param dd_mm_yyyy in the format dd/mm/yyyy
     * @return true valid date format, false invalid date format
     */
    public static boolean isDateValid (final String dd_mm_yyyy) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(dd_mm_yyyy);

        if (matcher.matches()) {

            matcher.reset();

            if (matcher.find()) {

                String day = matcher.group(1);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") && (month.equals("4") || month.equals("6") || month.equals("9") ||
                        month.equals("11") || month.equals("04") || month.equals("06") || month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if (year % 4 == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * example :answer = isType("10", "float"); //will return true;<br>
     * answer = isType("1.0", "float"); //will return true;<br/>
     * answer = isType("blah", "int"); //will return false;
     *
     * @param testStr
     * @param type
     * @return boolean result
     */
    public static boolean isType (String testStr, String type) {
        try {
            if (type.equalsIgnoreCase("float")) {
                Float.parseFloat(testStr);
            } else if (type.equalsIgnoreCase("int")) {
                Integer.parseInt(testStr);
            } else if (type.equalsIgnoreCase("double")) {
                Double.parseDouble(testStr);
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /*
    get the system users honme directory <br/>
    for example  C:\Users\Kajiva Kinsley
     */
    public static String getUserHomeDirectory () {
        String homePath = null;

        try {
            homePath = System.getProperty("user.home", "/");
        } catch (Exception e) {

            homePath = "/";
        }

        return Paths.get(homePath).toAbsolutePath().toString();
    }

    public static String getSaltString () {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    /**
     * Create a list of all student
     *
     * @param studentList
     * @return [All Students, 1, 2, 3, 4, 5, 6, 7, 1-2, 1-3, 1-4, 1-5, 1-6, 2-3, 2-4, 2-5, 2-6, 2-7, 3-4, 3-5, 3-6, 3-7, 4-5, 4-6, 4-7, 5-6, 5-7, 6-7]
     */
    public static List<String> studentRange (List<String> studentList) {

        for (int i = 0; i < studentList.size(); i++) {
            studentList.set(i, "Grade " + studentList.get(i));
        }

        java.util.List<String> v = new ArrayList<>(studentList);
        for (int i = 0; i < studentList.size(); i++) {

            for (int j = (i + 1); j < studentList.size(); j++) {
                String stage = studentList.get(i) + "-" + (j + 1);
                stage = stage.equals("1-7") ? "All Students" : stage;
                v.add(stage);
            }
        }
        v.remove("All Students");
        v.add(0, "All Students");
        return v;
    }

    public static int[] rangeMaker (String s,List<String> allGradeLevel) {

        if(s.equals("All Students")){
            return new int[]{
                    Integer.parseInt(allGradeLevel.get(0)),
                    Integer.parseInt(allGradeLevel.get(allGradeLevel.size()-1))
            };
        }

        if(s.contains("-")) {

            s = s.replace("Grade ", "");
            String[] q = s.split("-");
            int min = Integer.parseInt(q[0]);
            int  max = Integer.parseInt(q[1]);

            return new int[]{min, max};
        }else{
            s = s.replace("Grade ", "");
            return new int[]{Integer.parseInt(s), Integer.parseInt(s)} ;
        }
    }

    public static void main (String[] sss) {
        System.out.print(
                rangeMaker("Grade 1",new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7")))[0]+"\n"+
                rangeMaker("Grade 1",new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7")))[1]
               /* rangeMaker("Grade 1-2"),new ArrayList<>(Arrays.asList("1","2","3","4"))*/
        );

       // System.out.print(localTransactionIDMaker());
       /* DBManager.getInstance();

        DBSettings db=new DBSettings();
        db.saveDefaults(new ArrayList<>(),new ArrayList<>(),"Zimbabw");
        System.out.print(db.getGradeLevelClass(true));
*/

    }

}
