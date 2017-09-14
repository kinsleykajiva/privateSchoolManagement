package sample.main.mDatabases;

import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static sample.main.mUtility.mLocalMethods.getCountriesList;
import static sample.main.mUtility.mLocalStrings.DATABASE_SETTINGS;

public final class DBSettings {
private static DBSettings dbSettings=null;
    private static  Nitrite db=null;
    private static final  String DOCUMENT_CUSTOMS ="aboutSystem";
    private DBSettings(){
        createconnection();

    }

    public static DBSettings getInstance() {
        if (dbSettings == null) {
            dbSettings = new DBSettings();
        }
        return dbSettings;
    }
    private void createconnection() {
        db = Nitrite.builder()
                .compressed()
                .filePath(DATABASE_SETTINGS)
                .openOrCreate("user", "password");

    }

    public void saveDefaults(ArrayList<Integer> gradeLevels ,ArrayList<String> classNames,String countryName) {

        NitriteCollection collection = db.getCollection(DOCUMENT_CUSTOMS);
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

        ArrayList<Integer> grades = new ArrayList<>(Arrays.asList());
        Document customeSchoolSettings = Document.createDocument("gradeLevels",gradeLevels)
                .put("classNames",classNames)
                .put("country",countryName);
        collection.insert(customeSchoolSettings);
    }


}
