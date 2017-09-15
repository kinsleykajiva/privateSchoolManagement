package sample.main.mDatabases;

import sample.main.mPojos.PrimaryLevelStudent;

import java.sql.*;

import static sample.main.mUtility.mLocalStrings.DATABASE_RECORDS;

public final class DBRecords {
    private static DBRecords dbRecords = null;
    private static Connection conn = null;
    private static Statement statement =null;
    private static final String TABLE_NAME = "students_data",COL_NAME = "name",COL_SURNAME = "surname",COL_SEX="sex",
            COL_GRADE="grade",COL_CLASSNAME="classname",COL_ADDRESS = "address", COL_TOWN = "town" , COL_COUNTRY = "country",
    COL_FEES_PAID = "paid_fees", COL_REGISTRATION_NUMBER = "reg_number" , COL_DOB = "dob", COL_REGISTRATION_DATE="registration_date";

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
            String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME + " ("+
                    " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +COL_NAME + " TEXT NOT NULL ,"
                    + COL_SURNAME + " TEXT NOT NULL ,"
                    + COL_REGISTRATION_NUMBER + " TEXT NOT NULL ,"
                    + COL_SEX +" TEXT NOT NULL ,"
                    + COL_DOB + " TEXT NOT NULL ,"
                    + COL_ADDRESS + " TEXT NOT NULL ,"
                    + COL_TOWN + " TEXT NOT NULL ,"
                    + COL_COUNTRY + " TEXT NOT NULL ,"
                    + COL_FEES_PAID + " TEXT NOT NULL ,"
                    + COL_CLASSNAME + " TEXT NOT NULL ,"
                    + COL_GRADE + " TEXT NOT NULL , "
                    +COL_REGISTRATION_DATE + " TEXT NOT NULL"
                    + " );";
        try {
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
public void savePrimaryStudent(PrimaryLevelStudent student){
        String Std = "INSERT INTO "+TABLE_NAME +"("+COL_NAME+", "+COL_SURNAME+", "+COL_REGISTRATION_NUMBER
                +", "+COL_SEX +" , "+COL_DOB+" , "+ COL_ADDRESS+" , "+COL_TOWN + ", "+COL_COUNTRY
                +", " +COL_FEES_PAID+" , "+COL_CLASSNAME+" , "+COL_GRADE+" , "+COL_REGISTRATION_DATE+") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    try {
        PreparedStatement statement = conn.prepareStatement(Std);
        statement.setString(1,student.get__name());
        statement.setString(2,student.get__surname());
        statement.setString(3,student.get__registrationNumber());
        statement.setString(4,student.get__sex());
        statement.setString(5,student.get__dateOFBirth());
        statement.setString(6,student.get__address());
        statement.setString(7,student.get__town_city());
        statement.setString(8,student.get__country());
        statement.setString(9,student.get__feesPaid());
        statement.setString(10,student.get__class_name());
        statement.setString(11,student.get__classGrade_level());
        statement.setString(12,student.get__registrationDate());
        statement.executeUpdate();


    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    private void createConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:"+DATABASE_RECORDS);
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
