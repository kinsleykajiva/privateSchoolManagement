package sample.main.mDatabases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBListener extends Thread {
    private DBManager db = null;
    private static Connection conn = null;
    private static Statement statement = null;
    public  DBListener(){
        db = DBManager.getInstance();
        conn = db.getConnection();
        try {
            statement = conn.createStatement();
            statement.execute("LISTEN mymessage");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run () {
        super.run();
       while (true) {
            try {
                Statement stm = conn.createStatement();
                //ResultSet = stm.executeQuery("SELECT 1");
                


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
