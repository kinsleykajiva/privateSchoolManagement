package sample.main.mframeWork;

import sample.main.mPojos.PrimaryLevelStudent;
import sample.main.mPojos.Student;

import java.util.ArrayList;
import java.util.List;

public class Shared {
    public static boolean IsPrimaryStudents = true;
    public static ViewController viewController;
    public static PrimaryLevelStudent student;
    public static String [] editedRecord = new String[12];
    /**
     * Will act as a temporary storage for data fetch from the database
     */
    public static List<PrimaryLevelStudent> primaryLevelStudentsCache = new ArrayList<>();
    /**
     * will help keep track that if a recored has just been edited so that <br/>
     * we do not have to reload reading from the database directly to improve on perfomance .
     */
    public static boolean hasJustEditedRecorded =false;
}
