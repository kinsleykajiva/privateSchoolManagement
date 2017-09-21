package sample.main.mframeWork;

import static sample.main.mUtility.mLocalStrings.getApplicationName;

public enum ViewController {

    DEFAULT_VIEW{
        @Override
        public String getTitle () {
            return getApplicationName();
        }

        @Override
        public  String getFxmlFile () {
            return LAY_OUT + "default_view.fxml";
        }
    },
    HOME_VIEW {
        @Override
        public String getTitle () {
            return "Home";
        }

        @Override
        public String getFxmlFile () {
            return LAY_OUT + "main.fxml";
        }
    },
    ADD_STUDENT {
        @Override
        public String getTitle () {
            return "Add New Student";
        }

        @Override
        public String getFxmlFile () {
            return LAY_OUT + "add_student.fxml";
        }
    },

    FEES_VIEW{
        @Override
        public String getTitle () {
            return "Fees Data";
        }

        @Override
        public String getFxmlFile () {
            return LAY_OUT + "fees_processing.fxml";
        }
    },
    TAB_CREATE_FEES{
        @Override
        public String getTitle () {
            return "Create Fees Structure";
        }

        @Override
        public String getFxmlFile () {
            return LAY_OUT + "tab_creat_fees.fxml";
        }
    },
    TAB_FEES_PAYMENT {
        @Override
        public String getTitle () {
            return "Make Fees Payment";
        }

        @Override
        public String getFxmlFile () {
            return LAY_OUT + "tab_fees_paymentfxml.fxml";
        }
    },
    TAB_VIEW_FES_LIST{
        @Override
        public String getTitle () {
            return "List of Fees Saved";
        }

        @Override
        public String getFxmlFile () {
            return LAY_OUT + "tab_view_fees.fxml";
        }
    },
    STUDENT_DATA{
        @Override
        public String getTitle () {
            return "Student Data";
        }

        @Override
        public String getFxmlFile () {
            return LAY_OUT + "student_data.fxml";
        }
    },
    VIEW_STUDENTS {
        @Override
        public String getTitle () {
            return "About Students";
        }

        @Override
        public  String getFxmlFile () {
            return LAY_OUT + "view_students.fxml";
        }
    },EDIT_STUDENTS{
        @Override
        public String getTitle () {
            return "Edit Student";
        }

        @Override
        public String getFxmlFile () {
            return LAY_OUT + "editStudent.fxml";
        }
    };




    public abstract String getTitle ();
    public abstract String getFxmlFile();
   private final static String LAY_OUT = "/layouts/";

}
