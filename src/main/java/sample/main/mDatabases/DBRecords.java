package sample.main.mDatabases;

import sample.main.mPojos.PrimaryLevelStudent;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DBRecords {
    private DBManager db=null;
    private static Connection conn = null;
    private static Statement statement = null;
    private static final String TABLE_NAME = "students_data", COL_NAME = "name", COL_SURNAME = "surname", COL_SEX = "sex",
            COL_GRADE = "grade", COL_CLASSNAME = "classname", COL_ADDRESS = "address", COL_TOWN = "town", COL_COUNTRY = "country",
            COL_FEES_PAID = "paid_fees", COL_ACCOUNT_NUMBER = "account_number", COL_REGISTRATION_NUMBER = "reg_number", COL_DOB = "dob", COL_REGISTRATION_DATE = "registration_date";



    public DBRecords () {

        try {
            db = DBManager.getInstance();
            conn = db.getConnection();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createTables();
    }

    private void createTables () {
        final String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT NOT NULL ,"
                + COL_SURNAME + " TEXT NOT NULL ,"
                + COL_REGISTRATION_NUMBER + " TEXT NOT NULL ,"
                + COL_SEX + " TEXT NOT NULL ,"
                + COL_DOB + " TEXT NOT NULL ,"
                + COL_ADDRESS + " TEXT NOT NULL ,"
                + COL_TOWN + " TEXT NOT NULL ,"
                + COL_COUNTRY + " TEXT NOT NULL ,"
                + COL_FEES_PAID + " TEXT NOT NULL ,"
                + COL_CLASSNAME + " TEXT NOT NULL ,"
                + COL_GRADE + " TEXT NOT NULL , "
                + COL_REGISTRATION_DATE + " TEXT NOT NULL ,"
                + COL_ACCOUNT_NUMBER + " TEXT NOT NULL"
                + " );";
        try {
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean savePrimaryStudent (PrimaryLevelStudent student) {
        String Std = "INSERT INTO " + TABLE_NAME + "(" + COL_NAME + ", " + COL_SURNAME + ", " + COL_REGISTRATION_NUMBER
                + ", " + COL_SEX + " , " + COL_DOB + " , " + COL_ADDRESS + " , " + COL_TOWN + ", " + COL_COUNTRY
                + ", " + COL_FEES_PAID + " , " + COL_CLASSNAME + " , " + COL_GRADE + " , " + COL_REGISTRATION_DATE + " , " + COL_ACCOUNT_NUMBER + " ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(Std);
            statement.setString(1, student.get__name());
            statement.setString(2, student.get__surname());
            statement.setString(3, student.get__registrationNumber());
            statement.setString(4, student.get__sex());
            statement.setString(5, student.get__dateOFBirth());
            statement.setString(6, student.get__address());
            statement.setString(7, student.get__town_city());
            statement.setString(8, student.get__country());
            statement.setString(9, student.get__feesPaid());
            statement.setString(10, student.get__class_name());
            statement.setString(11, student.get__classGrade_level());
            statement.setString(12, student.get__registrationDate());
            statement.setString(13, student.getAccountNumber());

            return statement.executeUpdate() == 1;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRecord (String regNumber) {
        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_REGISTRATION_NUMBER + " = ?";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, regNumber);
            return stm.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PrimaryLevelStudent> getStudent () {
        List<PrimaryLevelStudent> list = new ArrayList<>();
        final String sql = "SELECT * FROM " + TABLE_NAME;
        try(ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new PrimaryLevelStudent(
                        rs.getString(COL_REGISTRATION_NUMBER),
                        false,
                        rs.getString(COL_NAME),
                        rs.getString(COL_SURNAME),
                        rs.getString(COL_ADDRESS),
                        rs.getString(COL_DOB),
                        rs.getString(COL_COUNTRY),
                        rs.getString(COL_TOWN),
                        rs.getString(COL_SEX),
                        rs.getString(COL_ACCOUNT_NUMBER),
                        rs.getString(COL_GRADE),
                        rs.getString(COL_CLASSNAME),
                        rs.getString(COL_FEES_PAID)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    public boolean updateRecord (PrimaryLevelStudent student__) {
        String update = "UPDATE " + TABLE_NAME + " SET "
                + COL_NAME + " = ? ,"
                + COL_SURNAME + " = ? ,"
                + COL_ADDRESS + " = ? ,"
                + COL_DOB + " = ? ,"
                + COL_COUNTRY + " = ? ,"
                + COL_TOWN + " = ? ,"
                + COL_SEX + " = ? ,"
                + COL_ACCOUNT_NUMBER + " = ? ,"
                + COL_GRADE + " = ? ,"
                + COL_CLASSNAME + " = ? ,"
                + COL_FEES_PAID + " = ? "
                + "WHERE " + COL_REGISTRATION_NUMBER + " = ? ";
        try {
            PreparedStatement st = conn.prepareStatement(update);
            st.setString(1, student__.get__name());
            st.setString(2, student__.get__surname());
            st.setString(3, student__.get__address());
            st.setString(4, student__.get__dateOFBirth());
            st.setString(5, student__.get__country());
            st.setString(6, student__.get__town_city());
            st.setString(7, student__.get__sex());
            st.setString(8, student__.getAccountNumber());
            st.setString(9, student__.get__classGrade_level());
            st.setString(10, student__.get__class_name());
            st.setString(11, student__.get__feesPaid());
            st.setString(12, student__.get__registrationNumber());

            return st.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


}
