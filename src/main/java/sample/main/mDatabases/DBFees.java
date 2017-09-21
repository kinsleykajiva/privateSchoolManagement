package sample.main.mDatabases;

import sample.main.mPojos.SchoolFees;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBFees {
    private DBManager db = null;
    private static Connection conn = null;
    private static Statement statement = null;
    private static final String TABLE = "fees", COL_NAME = "name", COL_AMOUNT = "amount", COL_WHO_PAYS = "whopays",
            COL_ACCOUNTNUMBER = "account_number", COL_BANK = "bank", COL_DESCRIPTION = "description",
            COL_DATE_OF_CREATION = "data_of_creation", COL_FEESDATE_PERIOD = "feesdate_period", COL_PERIOD_NAME = "period_name";

    public DBFees () {
        try {
            db = DBManager.getInstance();
            conn = db.getConnection();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        creatTables();
    }

    public List<SchoolFees> getFeesLists () {
        List<SchoolFees> list = new ArrayList<>();
        final String sql = "SELECT * FROM " + TABLE;
        try (ResultSet rs = statement.executeQuery(sql)){
            while (rs.next()) {
                list.add(
                        new SchoolFees(
                                rs.getString(COL_NAME),
                                rs.getString(COL_AMOUNT), rs.getString(COL_BANK), rs.getString(COL_WHO_PAYS),
                                rs.getString(COL_FEESDATE_PERIOD)
                                , rs.getString(COL_PERIOD_NAME), rs.getString(COL_ACCOUNTNUMBER), rs.getString(COL_DESCRIPTION),
                                rs.getString(COL_DATE_OF_CREATION)
                                , false
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.forEach(x->{
            System.out.println(x.getFeeAmoount());
            System.out.println(x.getDateCreated());
        });

        return list.isEmpty()? Collections.emptyList():list;

    }

    public boolean saveNewFees (SchoolFees fees) {
        final String sql = "INSERT INTO " + TABLE + " ( "
                + COL_NAME + " , "
                + COL_AMOUNT + " , "
                + COL_WHO_PAYS + " , "
                + COL_ACCOUNTNUMBER + " , "
                + COL_BANK + " , "
                + COL_DESCRIPTION + " , "
                + COL_DATE_OF_CREATION + " , "
                + COL_FEESDATE_PERIOD + " , "
                + COL_PERIOD_NAME + " ) VALUES ( ?,?,?,?,?,?,?,?,? "
                + " ) ";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, fees.getFeeName());
            stm.setString(2, fees.getFeeAmoount());
            stm.setString(3, fees.getWhoPays());
            stm.setString(4, fees.getFeesAccountNoumber());
            stm.setString(5, fees.getBank());
            stm.setString(6, fees.getFeesDescription());
            stm.setString(7, fees.getDateCreated());
            stm.setString(8, fees.getFeesDatePeriod());
            stm.setString(9, fees.getFeesPeriodName());

            return stm.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    private void creatTables () {
        final String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL ,"
                + COL_AMOUNT + " TEXT NOT NULL ,"
                + COL_WHO_PAYS + " TEXT NOT NULL ,"
                + COL_ACCOUNTNUMBER + " TEXT NOT NULL ,"
                + COL_BANK + " TEXT NOT NULL ,"
                + COL_DESCRIPTION + " TEXT NOT NULL ,"
                + COL_DATE_OF_CREATION + " TEXT NOT NULL ,"
                + COL_FEESDATE_PERIOD + " TEXT NOT NULL ,"
                + COL_PERIOD_NAME + " TEXT NOT NULL "
                + " );";
        try {
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
