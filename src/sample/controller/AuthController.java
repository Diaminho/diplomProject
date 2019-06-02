package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Experiment;

import java.io.IOException;

public class AuthController {

    @FXML
    PasswordField passwordId;

    private static Parent root;
    public static Stage primaryStage;
    private static Experiment experiment;

    public AuthController(Stage primaryStage, Experiment experiment) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/auth.fxml"));
        //MainController.primaryStage = primaryStage;
        AuthController.primaryStage=primaryStage;
        primaryStage.setTitle("Авторизация");
        AuthController.primaryStage.setScene(new Scene(root));
        AuthController.experiment = experiment;
        init();

        primaryStage.setOnHidden(e -> onClose());

        AuthController.primaryStage.show();
    }

    private void init() {
        passwordId = (PasswordField) root.lookup("#passwordId");
    }

    public AuthController() {}

    @FXML
    void onAuthButton() {
        if (passwordId.getText().equals("test")) {
            primaryStage.close();
            try {
                new SettingsController(new Stage(), experiment);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Авторизация провалена. Введен неверный пароль");
            alert.setTitle("Ошибка авторизации");
            alert.show();
        }
    }

    private void onClose() {
        System.out.println("Stage is closing");
        try {
            new MainController(new Stage(), experiment);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
