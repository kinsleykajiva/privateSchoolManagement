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
            return LAY_OUT + "main.fxml";
        }
    },
    HOME_VIEW {
        @Override
        String getTitle () {
            return "Home";
        }

        @Override
        String getFxmlFile () {
            return LAY_OUT + "home.fxml";
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
    };




    abstract String getTitle();
    abstract String getFxmlFile();
   private final static String LAY_OUT = "/layouts/";
}
