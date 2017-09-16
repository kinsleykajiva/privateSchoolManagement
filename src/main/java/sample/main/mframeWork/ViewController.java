package sample.main.mframeWork;

import static sample.main.mUtility.mLocalStrings.getApplicationName;

public enum ViewController {

    DEFAULT_VIEW{
        @Override
        String getTitle () {
            return getApplicationName();
        }

        @Override
        String getFxmlFile () {
            return LAY_OUT + "default_view.fxml";
        }
    },
    HOME_VIEW {
        @Override
        String getTitle () {
            return "Home";
        }

        @Override
        String getFxmlFile () {
            return LAY_OUT + "main.fxml";
        }
    },
    ADD_STUDENT {
        @Override
        String getTitle () {
            return "Add New Student";
        }

        @Override
        String getFxmlFile () {
            return LAY_OUT + "add_student.fxml";
        }
    },
    STUDENT_DATA{
        @Override
        String getTitle () {
            return "Student Data";
        }

        @Override
        String getFxmlFile () {
            return LAY_OUT + "student_data.fxml";
        }
    },
    VIEW_STUDENTS {
        @Override
        String getTitle () {
            return "About Students";
        }

        @Override
        String getFxmlFile () {
            return LAY_OUT + "view_students.fxml";
        }
    },EDIT_STUDENTS{
        @Override
        String getTitle () {
            return "Edit Student";
        }

        @Override
        String getFxmlFile () {
            return LAY_OUT + "editStudent.fxml";
        }
    };




    abstract String getTitle();
    abstract String getFxmlFile();
   private final static String LAY_OUT = "/layouts/";

}
