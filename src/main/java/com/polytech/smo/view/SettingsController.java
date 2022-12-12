package com.polytech.smo.view;

import com.polytech.smo.SMOApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SettingsController {
    @FXML
    private TextField sourceDeviceCountLabel;
    @FXML
    private TextField bufferDeviceCountLabel;
    @FXML
    private TextField processingDeviceCountLabel;
    @FXML
    private TextField lambdaLabel;
    @FXML
    private TextField aLabel;
    @FXML
    private TextField bLabel;

    @FXML
    protected void onApplyButtonClick() {
        int sourceDevicesCount = Integer.parseInt(sourceDeviceCountLabel.getText()); // 4
        int bufferDevicesCount = Integer.parseInt(bufferDeviceCountLabel.getText()); // 4
        int processingDevicesCount = Integer.parseInt(processingDeviceCountLabel.getText()); // 4
        double lambda = Double.parseDouble(lambdaLabel.getText()); // 3.0
        double a = Double.parseDouble(aLabel.getText()); // 2.0
        double b = Double.parseDouble(bLabel.getText()); // 3.0

        SMOApplication.initializeSystemController(sourceDevicesCount, bufferDevicesCount, processingDevicesCount,
                lambda, a, b);
    }
}
