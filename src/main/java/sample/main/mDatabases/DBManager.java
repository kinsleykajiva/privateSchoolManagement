package sample.main.mDatabases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static sample.main.mUtility.mLocalStrings.DATABASE_RECORDS;

public class DBManager {
    private final static int MAX_CONNECTIONS = 8;
    private static DBManager instance =null;

    private static Connection  connections =null;
    private static int counter=0 , requestCounter=0;


    private DBManager () {
        createConnection();
    }

    public  static DBManager getInstance(){
        if(instance == null){
            synchronized (DBFees.class){
                if(instance == null){
                    instance = new DBManager();
                    counter=0;
                }
            }

        }
        return instance;
    }


    private void createConnection () {
        for (int i = 0; i < MAX_CONNECTIONS ; i++) {
            try {
                connections = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_RECORDS);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public  Connection getConnection(){
        counter++;
        if(counter == Integer.MAX_VALUE){
            counter = 0 ;
        }
        return connections;
    }


}
