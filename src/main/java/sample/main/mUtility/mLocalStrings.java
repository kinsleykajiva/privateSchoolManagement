package sample.main.mUtility;

import java.time.LocalDateTime;

public class mLocalStrings {
    /**The application name */
    private static final String APPLICATION_NAME = "private-School Management System";

    public static final String APPDATA_MAIN_FOLDER = System.getenv("APPDATA") + "\\"+"privateSchoolManagementSystem";
    public static final String DATABASE_FODLER = APPDATA_MAIN_FOLDER + "\\localLog";
    /**create database file for the system to use to read and make writes.<br>This is to keep the settings of the system*/
    public static final String DATABASE_SETTINGS = DATABASE_FODLER + "\\log_pvtSMS.db";
    /**database to temporary storage*/
    public static final String DATABASE_RECORDS = DATABASE_FODLER + "\\records_pvtSMS.db";
    /**create log folder for the system to use to read and make writes to the log text files */
    public static final String FILE_LOGS_FOLDER = APPDATA_MAIN_FOLDER + "\\systemlogs";


    // public static final String
    public static final String DB_SYSTEM_SETTINGS_TABLE = "settings";


    public static final int APPLICATION_MINIMUM_WIDTH = 1360;
    public static final int APPLICATION_MINIMUM_HIGHT = 700;



    /**gets the application name with the year
     * @return App Name + year*/
    public static final String getApplicationName() {
        return APPLICATION_NAME + " (" + LocalDateTime.now().getYear() + ")";
    }
}
