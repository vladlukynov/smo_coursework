package com.polytech.smo;

/*
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
*/

public class SMOApplication {
    public static void main(String[] args) {
        int sourceDevicesCount = 4;
        int bufferDevicesCount = 4;
        int processingDevicesCount = 4;
        double lambda = 3.0;
        double a = 2.0;
        double b = 3.0;

        SystemController systemController = new SystemController(sourceDevicesCount, bufferDevicesCount, processingDevicesCount,
                lambda, a, b);
        for (int i = 0; i < 100; i++) {
            systemController.userClickNextButton();
        }
    }
}
