package sample.main.mPojos;


import javax.persistence.*;

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
    private String __sex;
    private String __classGrade_level;
    private String __class_name;

    public String get__feesPaid () {
        return __feesPaid;
    }

    public void set__feesPaid (String __feesPaid) {
        this.__feesPaid = __feesPaid;
    }

    private String __feesPaid;
    @Id
    private String __registrationNumber ;

    private int  counter=0;
    /**Accounting Account number that this student has been assigned by the school accounting system<br>This value is <b>unique</b>*/
    private String AccountNumber ;
    private String __registrationDate ;
    public String get__classGrade_level () {
        return __classGrade_level;
    }

    public String get__class_name () {
        return __class_name;
    }

    public void set__classGrade_level (String __classGrade_level) {
        this.__classGrade_level = __classGrade_level;
    }

    public void set__class_name (String __class_name) {
        this.__class_name = __class_name;
    }
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
        return __registrationNumber;
    }

    public void set__registrationNumber (String __registrationNumber) {
        this.__registrationNumber =__registrationNumber ;
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
