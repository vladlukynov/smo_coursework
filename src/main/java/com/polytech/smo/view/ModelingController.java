package com.polytech.smo.view;

import com.polytech.smo.SMOApplication;
import com.polytech.smo.table.TableBuffer;
import com.polytech.smo.table.TableEvent;
import com.polytech.smo.table.TableProcessingDevice;
import com.polytech.smo.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModelingController {
    @FXML
    private TableView<TableEvent> eventTable;
    @FXML
    private TableColumn<TableEvent, Double> eventColumn;
    @FXML
    private TableColumn<TableEvent, String> eventTimeColumn;
    @FXML
    private TableView<TableBuffer> bufferTable;
    @FXML
    private TableColumn<TableBuffer, Integer> bufferNum;
    @FXML
    private TableColumn<TableBuffer, String> bufferState;
    @FXML
    private TableView<TableProcessingDevice> processingDeviceTable;
    @FXML
    private TableColumn<TableProcessingDevice, Integer> processingDeviceNum;
    @FXML
    private TableColumn<TableProcessingDevice, String> processingDeviceState;
    @FXML
    private TableColumn<TableProcessingDevice, Double> startTime;
    @FXML
    private TableColumn<TableProcessingDevice, Double> endTime;
    public static final ObservableList<TableEvent> tableEvents = FXCollections.observableArrayList();
    public static final ObservableList<TableBuffer> tableBuffers = FXCollections.observableArrayList();
    public static final ObservableList<TableProcessingDevice> tableProcessingDevices = FXCollections.observableArrayList();

    @FXML
    protected void initialize() {
        eventColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("eventTime"));
        eventTable.setItems(tableEvents);

        bufferNum.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
        bufferState.setCellValueFactory(new PropertyValueFactory<>("state"));
        bufferTable.setItems(tableBuffers);

        processingDeviceNum.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
        processingDeviceState.setCellValueFactory(new PropertyValueFactory<>("state"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        processingDeviceTable.setItems(tableProcessingDevices);
    }

    @FXML
    protected void onNextStepButtonClick() {
        SMOApplication.systemController.userClickNextButton();
        tableEvents.sort(Utils.tableEventComparator);
        tableBuffers.sort(Utils.tableBufferComparator);
        tableProcessingDevices.sort(Utils.tableProcessingDeviceComparator);

        if (tableEvents.size() >= 15) {
            tableEvents.remove(0, tableEvents.size() - 15 );
        }
    }
}
