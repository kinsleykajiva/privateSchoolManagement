package sample.main.mDatabases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.main.mUtility.mLocalStrings.DATABASE_RECORDS;

public class DBFees {
    private static DBFees dbFees = null;
    private static Connection conn = null;
    private static Statement statement =null;
    private static final String TABLE = "fees" , COL_NAME ="name", COL_AMOUNT = "amount", COL_WHO_PAYS ="whopays",
            COL_ACCOUNTNUMBER = "account_number" , COL_BANK = "bank" , COL_DESCRIPTION = "description" ,
    COL_DATE_OF_CREATION ="data_of_creation" , COL_FEESDATE_PERIOD = "feesdate_period" , COL_PERIOD_NAME = "period_name";




    private DBFees (){
        createConnection();
        creatTables();
    }

    private void creatTables () {
        final String sql ="CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL ,"
                + COL_AMOUNT + " TEXT NOT NULL ,"
                + COL_WHO_PAYS + " TEXT NOT NULL ,"
                + COL_ACCOUNTNUMBER + " TEXT NOT NULL ,"
                + COL_BANK + " TEXT NOT NULL ,"
                +COL_DESCRIPTION + " TEXT NOT NULL ,"
                + COL_DATE_OF_CREATION + " TEXT NOT NULL ,"
                + COL_FEESDATE_PERIOD + " TEXT NOT NULL ,"
                +COL_PERIOD_NAME + " TEXT NOT NULL "
                +" );";
        try {
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createConnection () {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE_RECORDS);
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  static DBFees getInstance(){
        if(dbFees == null){
            dbFees = new DBFees();
        }
        return dbFees;
    }
}
