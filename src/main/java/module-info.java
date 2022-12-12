module com.polytech.smo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.polytech.smo to javafx.fxml;
    exports com.polytech.smo;
    exports com.polytech.smo.devices;
    opens com.polytech.smo.devices to javafx.fxml;
    exports com.polytech.smo.utils;
    opens com.polytech.smo.utils to javafx.fxml;
    exports com.polytech.smo.events;
    opens com.polytech.smo.events to javafx.fxml;
}