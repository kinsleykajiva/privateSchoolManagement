package sample.main.mPojos;

import javafx.beans.property.SimpleStringProperty;
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
    private SimpleStringProperty  __name = new SimpleStringProperty();
    private SimpleStringProperty __surname = new SimpleStringProperty();
    private SimpleStringProperty __address = new SimpleStringProperty();
    private SimpleStringProperty __dateOFBirth = new SimpleStringProperty();
    private SimpleStringProperty __town_city = new SimpleStringProperty();
    private SimpleStringProperty __country = new SimpleStringProperty();
    private SimpleStringProperty __sex = new SimpleStringProperty();
    @Id
    private SimpleStringProperty __registrationNumber = new SimpleStringProperty();
    private int  counter=0;
    /**Accounting Account number that this student has been assigned by the school accounting system<br>This value is <b>unique</b>*/
    private SimpleStringProperty AccountNumber = new SimpleStringProperty();
    private SimpleStringProperty __registrationDate = new SimpleStringProperty();

    public String get__name() {
        return __name.get();
    }

    public SimpleStringProperty  __nameProperty() {
        return __name;
    }

    public void set__name(String __name) {
        this.__name.setValue(__name);
    }

    public String get__surname() {
        return __surname.get();
    }

    public SimpleStringProperty __surnameProperty() {
        return __surname;
    }

    public void set__surname(String __surname) {
        this.__surname.set(__surname);
    }

    public String get__address() {
        return __address.get();
    }

    public SimpleStringProperty __addressProperty() {
        return __address;
    }

    public void set__address(String __address) {
        this.__address.set(__address);
    }

    public String get__dateOFBirth() {
        return __dateOFBirth.get();
    }

    public SimpleStringProperty __dateOFBirthProperty() {
        return __dateOFBirth;
    }

    public void set__dateOFBirth(String __dateOFBirth) {
        this.__dateOFBirth.set(__dateOFBirth);
    }

    public String get__town_city() {
        return __town_city.get();
    }

    public SimpleStringProperty __town_cityProperty() {
        return __town_city;
    }

    public void set__town_city(String __town_city) {
        this.__town_city.set(__town_city);
    }

    public String get__country() {
        return __country.get();
    }

    public SimpleStringProperty __countryProperty() {
        return __country;
    }

    public void set__country(String __country) {
        this.__country.set(__country);
    }

    public String get__sex() {
        return __sex.get();
    }

    public SimpleStringProperty __sexProperty() {
        return __sex;
    }

    public void set__sex(String __sex) {
        this.__sex.set(__sex);
    }

    public SimpleStringProperty __registrationNumberProperty() {
        return __registrationNumber;
    }

    private void set__registrationNumber() {
        this.__registrationNumber.set(localIDMaker());
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getAccountNumber() {
        return AccountNumber.get();
    }

    public SimpleStringProperty accountNumberProperty() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.AccountNumber.set(accountNumber);
    }

    public String get__registrationDate() {
        return __registrationDate.get();
    }

    public SimpleStringProperty __registrationDateProperty() {
        return __registrationDate;
    }

    private void set__registrationDate(){
    __registrationDate.setValue(registrationDate()+"|"+getCurrentTime());
}

    public String get__registrationNumber() {
        return __registrationNumber.get();
    }
}
