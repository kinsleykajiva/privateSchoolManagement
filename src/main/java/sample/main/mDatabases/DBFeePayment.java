package sample.main.mDatabases;

import sample.main.mPojos.FeesPayment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sample.main.mDatabases.DBRecords.*;
import static sample.main.mUtility.mLocalStrings.DB_FAILURE;


public class DBFeePayment {
    private DBManager db = null;
    private static Connection conn = null;
    private static Statement statement = null;
    private final String TABLE = "student_fees_payment", COL_REG_NUMBER = "studentRegnumber", COL_PAYMENT_METHOD = "paymentMethod",
            COL_TRANSACTION_ID = "transactionID", COL_PAID_AMOUNT = "paidAmount", COL_DATE_PAID = "datepaid", COL_BANK = "bank",
            COL_BANK_ACCOUNT = "bankAccount", COL_PAYED_BY = "payedBy", COL_BALANCE_REMAINING = "balanceRemaining", COL_TERM_PAID = "termPaid",
            COL_DISCOUNT = "discount";

    public DBFeePayment () {
        try {
            db = DBManager.getInstance();
            conn = db.getConnection();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        creatTables();
    }

    public List<String >getStudentDetails (String regNumber) {
       List<String> response = new ArrayList<>();
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_REGISTRATION_NUMBER + " = ? ";

        try( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, regNumber);
            ResultSet rs = pstmt.executeQuery();
            if ( rs.next()) {
                response.add(rs.getString(COL_NAME));
                response.add(rs.getString(COL_SURNAME));
                response.add(rs.getString(COL_SEX));
                response.add(rs.getString(COL_GRADE));
                response.add(rs.getString(COL_CLASSNAME));
                response.add(rs.getString(COL_ADDRESS));
                response.add(rs.getString(COL_TOWN));
                response.add(rs.getString(COL_COUNTRY));
                response.add(rs.getString(COL_ACCOUNT_NUMBER));

            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return response.isEmpty()? Collections.emptyList():response;
    }
    public  List<String > getStudentFeeStatus (String regNumber , String gradeLevel ,String className) {

        final String sql = "SELECT * FROM " + TABLE + " WHERE " + COL_REG_NUMBER + " = ? ";
        final String sql_getExpectedFees = "SELECT * FROM " + TABLE + " WHERE " + TABLE + " = ? ";
        List<String> response = new ArrayList<>();
        try( PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, regNumber);
            ResultSet rs = pstmt.executeQuery();
            if (! rs.next()) {
                response.add(rs.getString(COL_PAID_AMOUNT));
                response.add(rs.getString(COL_SURNAME));
                response.add(rs.getString(COL_SEX));
                response.add(rs.getString(COL_GRADE));
                response.add(rs.getString(COL_CLASSNAME));
                response.add(rs.getString(COL_ADDRESS));
                response.add(rs.getString(COL_TOWN));
                response.add(rs.getString(COL_COUNTRY));
                response.add(rs.getString(COL_ACCOUNT_NUMBER));
            }

        } catch (SQLException e) {e.printStackTrace(); }

        return response;
    }

    public String payStudentFees (FeesPayment payment) {
        String response = "";
        final String sql = "INSERT INTO " + TABLE + "("
                + COL_REG_NUMBER + " , "
                + COL_PAYMENT_METHOD + " , "
                + COL_TRANSACTION_ID + " , "
                + COL_PAID_AMOUNT + " , "
                + COL_DATE_PAID + " , "
                + COL_BANK + " , "
                + COL_BANK_ACCOUNT + " , "
                + COL_PAYED_BY + " , "
                + COL_BALANCE_REMAINING + " , "
                + COL_TERM_PAID + " , "
                + COL_DISCOUNT + " " +
                ")VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, payment.getStudentRegnumber());
            stm.setString(2, payment.getPaymentMethod());
            stm.setString(3, payment.getTransactionID());
            stm.setString(4, payment.getPaidAmount());
            stm.setString(5, payment.getDatePaid());
            stm.setString(6, payment.getBank());
            stm.setString(7, payment.getBankAccount());
            stm.setString(8, payment.getPayedBy());
            stm.setString(9, payment.getBalanceRemaining());
            stm.setString(10, payment.getTermPaid());
            stm.setString(11, payment.getDiscount() + "");
            response = stm.executeUpdate() == 1 ? "success" : "failed";
        } catch (SQLException e) {
            e.printStackTrace();
            response = DB_FAILURE;
        }


        return response;
    }

    private void creatTables () {
        final String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_REG_NUMBER + " TEXT NOT NULL ,"
                + COL_PAYMENT_METHOD + " TEXT NOT NULL ,"
                + COL_TRANSACTION_ID + " TEXT NOT NULL ,"
                + COL_PAID_AMOUNT + " TEXT NOT NULL ,"
                + COL_DATE_PAID + " TEXT NOT NULL ,"
                + COL_BANK + " TEXT NOT NULL ,"
                + COL_BANK_ACCOUNT + " TEXT NOT NULL ,"
                + COL_PAYED_BY + " TEXT NOT NULL ,"
                + COL_BALANCE_REMAINING + " TEXT NOT NULL ,"
                + COL_TERM_PAID + " TEXT NOT NULL ,"
                + COL_DISCOUNT + " TEXT NOT NULL "
                + " );";
        try {
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
