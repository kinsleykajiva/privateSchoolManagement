package sample.main.mDatabases;

import sample.main.mPojos.SchoolFees;

import java.sql.*;
import java.util.*;

import static sample.main.mUtility.mLocalMethods.rangeMaker;
import static sample.main.mUtility.mLocalStrings.DB_FAILURE;

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


    public Map<String, String> getStudentFeesExpected (String gradeLevel) {
        // All Students , Grade 1 , Grade 1-2
        int ogGradeLevel = Integer.parseInt(gradeLevel);
        List<String> grads__ = new DBSettings().getGradeLevelClass(true);
        final int[] ranges = rangeMaker(gradeLevel, grads__);
        gradeLevel = "Grade " + gradeLevel;
        Map<String, String> data = new HashMap<>();
        final String sqlAll = "SELECT " + COL_AMOUNT + " ," + COL_WHO_PAYS + " ,  SUM(" + COL_AMOUNT + ") FROM " + TABLE
                + " WHERE " + COL_WHO_PAYS + " = ?";
        final String sqlGrade = "SELECT * FROM " + TABLE + " ";
        final String Col_sum_response = "sum(" + COL_AMOUNT + ")";
        double feeAdder = 0.0;
        try (PreparedStatement pstmtAll = conn.prepareStatement(sqlAll)) {

            pstmtAll.setString(1, "All Students");
            ResultSet rs = pstmtAll.executeQuery();
            if (rs.next()) {
                feeAdder += Double.parseDouble(rs.getString(Col_sum_response));
            }
            ResultSet rs2 = conn.prepareStatement(sqlGrade).executeQuery();
            while (rs2.next()) {
                String whopays__ = rs2.getString(COL_WHO_PAYS);
                int[] r = rangeMaker(whopays__, grads__);


                System.out.println("xxxxxxx:  "+r[0] + r[1]);
                if(r[0] == ogGradeLevel && r[1] == ogGradeLevel){
                    feeAdder += Double.parseDouble(rs2.getString(COL_AMOUNT));
                }
                if(r[0]> ogGradeLevel && ogGradeLevel<=r[1]){
                     System.out.println("add" + rs2.getString(COL_AMOUNT));
                    feeAdder += Double.parseDouble(rs2.getString(COL_AMOUNT));
                }
                //System.out.println(ranges[0] != ranges[1] ? "Grade " + ranges[0] + "-" + ranges[1] : "Grade " + ranges[0]);
                if (whopays__.equals(ranges[0] != ranges[1] ? "Grade " + ranges[0] + "-" + ranges[1] : "Grade " + ranges[0])) {

                    feeAdder += Double.parseDouble(rs2.getString(COL_AMOUNT));

                }
            }
            System.out.println(feeAdder + "::::");


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return data;

    }

    public List<SchoolFees> getFeesLists () {
        List<SchoolFees> list = new ArrayList<>();
        final String sql = "SELECT * FROM " + TABLE;
        try (ResultSet rs = statement.executeQuery(sql)) {
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


        return list.isEmpty() ? Collections.emptyList() : list;

    }

    public String deletRecord (String key) {
        String response;
        final String sql = "DELETE FROM " + TABLE + " WHERE " + COL_NAME + " = ?";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, key);
            response = stm.executeUpdate() == 1 ? "success" : "failed";

        } catch (SQLException e) {
            e.printStackTrace();
            response = DB_FAILURE;
        }
        return response;
    }

    public String updateRecord (String[] oldReferences, SchoolFees fees) {
        String response;
        final String sql = "UPDATE " + TABLE + " SET "
                + COL_NAME + " = ? , "
                + COL_AMOUNT + " = ? , "
                + COL_WHO_PAYS + " = ? , "
                + COL_ACCOUNTNUMBER + " = ? , "
                + COL_BANK + " = ? , "
                + COL_DESCRIPTION + " = ? , "
                + COL_DATE_OF_CREATION + " = ? , "
                + COL_FEESDATE_PERIOD + " = ? , "
                + COL_PERIOD_NAME + " = ? WHERE " + COL_NAME + " = ? ";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, fees.getFeeName());
            stm.setString(2, fees.getFeeAmoount());
            stm.setString(3, fees.getWhoPays());
            stm.setString(4, fees.getFeesAccountNoumber());
            stm.setString(5, fees.getBank());
            stm.setString(6, fees.getFeesDescription());
            stm.setString(7, fees.getDateCreated());
            stm.setString(8, fees.getFeesDatePeriod());
            stm.setString(9, fees.getFeesPeriodName());
            stm.setString(10, oldReferences[0]);
            response = stm.executeUpdate() == 1 ? "success" : "failed";
        } catch (SQLException e) {
            e.printStackTrace();
            response = DB_FAILURE;
        }
        return response;
    }

    public String saveNewFees (SchoolFees fees) {
        String response = "exists";
        final String check = "SELECT " + COL_NAME + " FROM  " + TABLE + " WHERE " + COL_NAME + " = ? ";

        boolean recordAdded = false;
        try {
            PreparedStatement pstmt = conn.prepareStatement(check);
            pstmt.setString(1, fees.getFeeName());
            ResultSet rs = pstmt.executeQuery();
            if (! rs.next()) {
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

                try (PreparedStatement stm = conn.prepareStatement(sql)) {
                    stm.setString(1, fees.getFeeName());
                    stm.setString(2, fees.getFeeAmoount());
                    stm.setString(3, fees.getWhoPays());
                    stm.setString(4, fees.getFeesAccountNoumber());
                    stm.setString(5, fees.getBank());
                    stm.setString(6, fees.getFeesDescription());
                    stm.setString(7, fees.getDateCreated());
                    stm.setString(8, fees.getFeesDatePeriod());
                    stm.setString(9, fees.getFeesPeriodName());

                    recordAdded = stm.executeUpdate() == 1;
                    response = recordAdded ? "success" : "failed";


                } catch (SQLException e) {
                    e.printStackTrace();
                    response = DB_FAILURE;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            response = DB_FAILURE;
        }

        return response;
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
