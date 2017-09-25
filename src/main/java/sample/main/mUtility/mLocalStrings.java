package sample.main.mUtility;

import java.time.LocalDateTime;

public class mLocalStrings {
    /**The application name */
    private static final String APPLICATION_NAME = "Private School Management System ® ";
    public static final String THEME = "Simple Efficient Management";

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
    public static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    public static final  String SETUP_ERROR = "SetUp Error";
    public static final  String DB_FAILURE = "dbFail";

    public static final  String DATABASE_ACTION = "Database Action Confirmation";
    public static final  String DATABASE_ACTION_FAILED = "Database Action Failed";
    public static final String SETTINGS_SETUP = "It Seems like you had not configured the system Correctly !";


    /**gets the application name with the year
     * @return App Name + year*/
    public static final String getApplicationName() {
        return APPLICATION_NAME + " (" + LocalDateTime.now().getYear() + ")";
    }

    public static final String [] SUGGESTION_SCHOOL_FEES_TYPES ={"Enrollment Deposit","Orientation Fee","Graduation Fee",
        "Residence Hall Reservation Fee","Double Major","Late Payment Charge","Late Pre-Registration/Registration Fee",
            "Health Services Fee","Health Insurance","Transcript Fee (per copy)","Meals",
            "emergency fund","Student emergency fund","Early childhood education services","School Bus Faire","Accommodation",
            "Developement Fee","Food","Stationary"

    };
    public static final String [] TERMS = {"Term 1","Term 2","Term 3","Term 4","Term 5"};
    public static final String [] FEES_PERIOD= {"6 months","1 year","1.5 years","2 years","2.5 years","3 years","3.5 years","4 years","4.5 years","6 years","6.5 years"};
    public static final String []  PAYMENT_METHOD ={"Bank","Cash","Mobile"};


}
