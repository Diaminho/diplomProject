package sample.manager;

import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class IshikawaManager {
    static Parent root;
    ChoiceBox<String> ishikawaChoiceBoxId;
    ImageView imageViewId;
    List<Image> ishikawaList = new ArrayList<>();

    public IshikawaManager(Parent root) {
        IshikawaManager.root = root;
        init();
    }

    private void init() {
        ishikawaChoiceBoxId = (ChoiceBox<String>) root.lookup("#ishikawaChoiceBoxId");
        imageViewId = (ImageView) root.lookup("#imageViewId");
        ishikawaChoiceBoxId.getItems().add("Цвет");
        ishikawaChoiceBoxId.getItems().add("Размер");
        ishikawaChoiceBoxId.getItems().add("Трещины");
        ishikawaChoiceBoxId.getItems().add("Структура");

        ishikawaChoiceBoxId.getSelectionModel().selectedItemProperty().addListener(((observableValue, s, t1) -> {
            int index = ishikawaChoiceBoxId.getItems().indexOf(t1);
            imageViewId.imageProperty().setValue(ishikawaList.get(index));
        }));

        ishikawaList.add(new Image("/sample/image/ishikawa/1.png"));
        ishikawaList.add(new Image("/sample/image/ishikawa/2.png"));
        ishikawaList.add(new Image("/sample/image/ishikawa/3.png"));
        ishikawaList.add(new Image("/sample/image/ishikawa/4.png"));
    }
}
