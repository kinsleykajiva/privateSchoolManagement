package sample.main.mPojos;

import javafx.beans.property.SimpleStringProperty;

import static sample.main.mUtility.mLocalMethods.getCurrentTime;
import static sample.main.mUtility.mLocalMethods.registrationDate;

public final class PrimaryLevelStudent extends Student{
    private SimpleStringProperty __classGrade_level;
    private SimpleStringProperty __class_name;
    /**Student registration process*/
    public PrimaryLevelStudent(String name , String surname , String address , String dateOFBirth , String townCity,
                               String country , String town_city , String sex ,  String AccountNumber,String grade_level, String className  ) {
       set__name(name);
       set__surname(surname);
       set__address(address);
       set__dateOFBirth(dateOFBirth);
       set__town_city(town_city);
       set__country(country);
       set__sex(sex);
       setAccountNumber(AccountNumber);
       __classGrade_level.setValue(grade_level);
       __class_name.setValue(className);

    }
    /**Student with basic data */
    public PrimaryLevelStudent(String name ,String surname ,String registrationID) {
        set__name(name);
        set__surname(surname);
        super.__registrationNumberProperty().setValue(registrationID);

    }

    public String get__classGrade_level() {
        return __classGrade_level.get();
    }

    public SimpleStringProperty __classGrade_levelProperty() {
        return __classGrade_level;
    }

    public String get__class_name() {
        return __class_name.get();
    }

    public SimpleStringProperty __class_nameProperty() {
        return __class_name;
    }
}
