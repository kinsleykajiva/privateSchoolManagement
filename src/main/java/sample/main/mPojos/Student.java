package sample.main.mPojos;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

import static sample.main.mUtility.mLocalMethods.getCurrentTime;
import static sample.main.mUtility.mLocalMethods.localIDMaker;
import static sample.main.mUtility.mLocalMethods.registrationDate;

/**
 * This defines a student
 * */
@Entity
@Table(name = "student101")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "student")
public abstract class Student {
    private boolean selected;
    private String  __name ;
    private String __surname;
    private String __address ;
    private String __dateOFBirth;
    private String __town_city ;
    private String __country ;
    private String __sex ;
    @Id
    private String __registrationNumber ;
    private int  counter=0;
    /**Accounting Account number that this student has been assigned by the school accounting system<br>This value is <b>unique</b>*/
    private String AccountNumber ;
    private String __registrationDate ;

    public boolean isSelected () {
        return selected;
    }

    public void setSelected (boolean selected) {
        this.selected = selected;
    }

    public String get__name () {
        return __name;
    }

    public void set__name (String __name) {
        this.__name = __name;
    }

    public String get__surname () {
        return __surname;
    }

    public void set__surname (String __surname) {
        this.__surname = __surname;
    }

    public String get__address () {
        return __address;
    }

    public void set__address (String __address) {
        this.__address = __address;
    }

    public String get__dateOFBirth () {
        return __dateOFBirth;
    }

    public void set__dateOFBirth (String __dateOFBirth) {
        this.__dateOFBirth = __dateOFBirth;
    }

    public String get__town_city () {
        return __town_city;
    }

    public void set__town_city (String __town_city) {
        this.__town_city = __town_city;
    }

    public String get__country () {
        return __country;
    }

    public void set__country (String __country) {
        this.__country = __country;
    }

    public String get__sex () {
        return __sex;
    }

    public void set__sex (String __sex) {
        this.__sex = __sex;
    }

    public String get__registrationNumber () {
        set__registrationNumber ();
        return __registrationNumber;
    }

    private void set__registrationNumber () {
        this.__registrationNumber =localIDMaker() ;
    }

    public int getCounter () {
        return counter;
    }

    public void setCounter (int counter) {
        this.counter = counter;
    }

    public String getAccountNumber () {
        return AccountNumber;
    }

    public void setAccountNumber (String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String get__registrationDate () {
        set__registrationDate();
        return __registrationDate;
    }

    private void set__registrationDate () {
        this.__registrationDate = registrationDate();
    }
}
