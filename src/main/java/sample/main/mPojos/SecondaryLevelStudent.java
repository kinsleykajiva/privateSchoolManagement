package sample.main.mPojos;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("secondarylevelstudent ")
public final class SecondaryLevelStudent extends Student {
    private SimpleStringProperty __classForm_level = new SimpleStringProperty();
    private SimpleStringProperty __class_name = new SimpleStringProperty();
    /**
     * Use student national ID
     * not required as it can be substituted with registration id
     * */
    private SimpleStringProperty __nationalID;



}
