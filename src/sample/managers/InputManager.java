package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Experiment;
import sample.MyStage;
import sample.controllers.InputPropController;
import sample.controllers.MainController;
import sample.resources.Material;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InputManager {
    //Stage primaryStage;
    List<Material> materialsList;


    private static Parent root;


    @FXML
    private TextField textFieldPropName;
    @FXML
    private TextField textFieldMaterialName;


    @FXML
    Stage primaryStage;

    private Experiment NewExperiment;

    public InputManager(Parent root) {
        InputManager.root = root;
        materialsList=new ArrayList<>();
        init();
    }



    private void init() {
        textFieldPropName = (TextField) root.lookup("#textFieldPropName");
        textFieldMaterialName = (TextField) root.lookup("#textFieldMaterialName");
    }




    @FXML
    public void onSaveInputButton() throws IOException {

    }


    @FXML
    public void onAddMaterialButton() throws IOException {
        materialsList.add(new Material(textFieldMaterialName.getText()));
        //System.out.println(materialsList.get(materialsList.size()-1).getName());
        materialsList.get(materialsList.size()-1).printMaterialAndProperties();

        ///
        String imagePath=getImageChooser();
        if (imagePath.compareTo("-")!=0) {
            System.out.println(imagePath);
            materialsList.get(materialsList.size() - 1).setMaterialImage(new Image(imagePath, 32, 32, false, false));
        }

    }


    @FXML
    public void onAddPropertyButton(String prop) throws IOException {
        materialsList.get(materialsList.size()-1).addProperty(prop,"");
        materialsList.get(materialsList.size()-1).printMaterialAndProperties();
    }


    //////////
    //public int searchMaterial()

    @FXML
    public void onGoToMenuButton() {
        try {
            new MainController(new Stage());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    private String getImageChooser(){
        FileChooser fc=new FileChooser();
        fc.setTitle("Open Image");

        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Images", "*.jpg", "*.gif", "*.png","*.tiff","*.jpeg");

        fc.getExtensionFilters().add(fileExtensions);

        File file = fc.showOpenDialog(new Stage());
        if (file!=null) {
            return file.getAbsolutePath();
        }
        else  {
            return "-";
        }
    }

}
