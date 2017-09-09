package sample.main.mDatabases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.main.mUtility.mLocalStrings.DATABASE;
import static sample.main.mUtility.mLocalStrings.DATABASE_FODLER;

public final class DBSettings {
private static DBSettings dbSettings=null;
    private static Connection conn = null;
    private static Statement statement =null;
    private DBSettings(){
        createconnection();
        createDatabase();
        createTable();
    }

    public static DBSettings getInstance() {
        if (dbSettings == null) {
            dbSettings = new DBSettings();
        }
        return dbSettings;
    }
    private void createconnection() {
        try {
            conn = DriverManager.getConnection(DATABASE);
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createDatabase() {
        
    }

    private void createTable() {
        
        
    }
}
