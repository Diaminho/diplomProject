package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpController {
    private static Parent root;

    private static Stage primaryStage;

    Text helpId;

    public HelpController(Stage primaryStage) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/resource/fxml/help.fxml"));
        HelpController.primaryStage = primaryStage;
        primaryStage.setTitle("Справка");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        init();
    }

    public HelpController() {}


    private void init() {
        helpId = (Text) root.lookup("#helpId");
        helpId.setWrappingWidth(350d);
        helpId.setText("Данное программное обеспечение позволяет имитировать технологический процесс производства кирпича\n\n" +
                "Для перехода к модулю имитации нажмите на кнопку \"Технологический процесс\".\n\n" +
                "Для настройки технологического процесса нажмите на кнопку \"Настройки\". К этому модулю имеет доступ только преподаватель.\n\n" +
                "Разработал Пичугин Д.П. 2019");
    }
}
