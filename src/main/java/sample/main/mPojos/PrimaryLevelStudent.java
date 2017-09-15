package sample.main.mPojos;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import static sample.main.mUtility.mLocalMethods.getCurrentTime;
import static sample.main.mUtility.mLocalMethods.registrationDate;
/**This is my class using hibernate for data persistence*/
@Entity
@DiscriminatorValue("primarylevelstudent ")
public final class PrimaryLevelStudent extends Student{


    private String __classGrade_level;
    private String __class_name;
    /**Student registration process*/
    public PrimaryLevelStudent(boolean selected,String name , String surname , String address , String dateOFBirth ,
                               String country , String town_city , String sex ,  String AccountNumber , String grade_level, String className , String feesPaid  ) {

       set__name(name);
       set__feesPaid(feesPaid);
       setSelected(selected);
       set__surname(surname);
       set__address(address);
       set__dateOFBirth(dateOFBirth);
       set__town_city(town_city);
       set__country(country);
       set__sex(sex);
       setAccountNumber(AccountNumber);


       __classGrade_level = grade_level;
       __class_name = className;

    }

    public String get__classGrade_level () {
        return __classGrade_level;
    }

    public String get__class_name () {
        return __class_name;
    }



}
