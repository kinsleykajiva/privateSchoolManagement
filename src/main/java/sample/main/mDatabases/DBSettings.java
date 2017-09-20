package sample.main.mDatabases;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static sample.main.mUtility.mLocalMethods.getCountriesList;

public final class DBSettings {
    private DBManager db=null;
    private Connection conn = null;
    private Statement statement =null;

    private static final  String DOCUMENT_CUSTOMS ="aboutSystem";
    public static final String TABLE_NAME = "aboutSystem", COL_SETTING_NAME = "settings_name" , COL_SETTINGS_SETING = "setting_seting";
    public static final String SETTING_CLASSNAME = "className", SETTING_GRADElEVEL = "gradeLevel",SETTING_COUNTRY = "country";
    public static final String DELIMITOR =";";
    public DBSettings(){

       /* try {*/
            db = DBManager.getInstance();
            conn = db.getConnection();

        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


       /* } catch (SQLException e) {
            e.printStackTrace();
        }*/
        createTables();

    }

    private void createTables () {
        final String  sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"  (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COL_SETTING_NAME+" TEXT NOT NULL,"
                + COL_SETTINGS_SETING +" TEXT NOT NULL"
                +");";
        try {
            statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void closeDb() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveDefaults(ArrayList<Integer> gradeLevels ,ArrayList<String> classNames,String countryName) {

        if(gradeLevels.isEmpty()){
            IntStream.range(1,8).forEach(gradeLevels::add); // will add from 1-7
        }
        if(classNames.isEmpty()){
            classNames.add("A" );
            classNames.add("B");
            classNames.add("C");
            classNames.add("D");
        }
        if(countryName.isEmpty()){
            countryName = getCountriesList(false).get(0);
        }


        String sqlgradeLevel = "INSERT INTO "+TABLE_NAME +"(" + COL_SETTING_NAME + "," + COL_SETTINGS_SETING + ") VALUES ( ?,? )" ;
        String sqlClassName = "INSERT INTO "+TABLE_NAME +"( " + COL_SETTING_NAME + "," + COL_SETTINGS_SETING + ") VALUES ( ?,? )" ;
        String sqlCountry =  "INSERT INTO "+TABLE_NAME +"( " + COL_SETTING_NAME + "," + COL_SETTINGS_SETING + ") VALUES ( ?,? )" ;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlgradeLevel);
            preparedStatement.setString(1, SETTING_GRADElEVEL);
            preparedStatement.setString(2, gradeLevels.stream().map(Object:: toString).collect(Collectors.joining(DELIMITOR)));
            preparedStatement.executeUpdate();

            preparedStatement = conn.prepareStatement(sqlClassName);
            preparedStatement.setString(1, SETTING_CLASSNAME);
            preparedStatement.setString(2, classNames.stream().map(Object:: toString).collect(Collectors.joining(DELIMITOR)));
            preparedStatement.executeUpdate();

            preparedStatement = conn.prepareStatement(sqlCountry);
            preparedStatement.setString(1, SETTING_COUNTRY);
            preparedStatement.setString(2, countryName);
            preparedStatement.executeUpdate();




        } catch (SQLException e) {
            e.getMessage();
        }
    }
    public static void upDateclassName(String newClassName){


    }
    public List<String> getGradeLevelClass (boolean isGettingGradeLevel){
        List<String> o = null;

        try{
            ResultSet resultSet = statement.executeQuery(isGettingGradeLevel?
                    "SELECT * FROM "+TABLE_NAME+ " WHERE "+COL_SETTING_NAME + " = '"+SETTING_GRADElEVEL+"'"
                    :
                    "SELECT * FROM "+TABLE_NAME+ " WHERE "+COL_SETTING_NAME + " = '"+SETTING_CLASSNAME+"'"
            );
            if (resultSet.next() ) {
                String y_= resultSet.getString(COL_SETTINGS_SETING);
                o=  new ArrayList<>(Arrays.asList(y_.split(DELIMITOR)));
            }





        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o==null? Collections.emptyList():o;

    }

}
