package sample.main.mDatabases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.main.mUtility.mLocalStrings.DATABASE;
import static sample.main.mUtility.mLocalStrings.DATABASE_RECORDS;

public final class DBRecords {
    private static DBRecords dbRecords = null;
    private static Connection conn = null;
    private static Statement statement =null;
    public static DBRecords getInstance() {
        if(dbRecords == null){
            dbRecords = new DBRecords();
        }
        return dbRecords;
    }

    private DBRecords() {
        createConnection();
        createTables();
    }

    private void createTables() {

    }

    private void createConnection() {
        try {
            conn = DriverManager.getConnection(DATABASE_RECORDS);
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
