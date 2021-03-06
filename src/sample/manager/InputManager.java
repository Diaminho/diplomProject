package sample.manager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Experiment;
import sample.controller.MainController;
import sample.parser.XmlParser;
import sample.resource.Material;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class InputManager {
    //Stage primaryStage;
    List<Material> materialsList;

    private static Parent root;
    private static String fileName="materials.xml";

    @FXML
    private TextField textFieldMaterialName;


    @FXML
    Stage primaryStage;

    private Experiment NewExperiment;

    public InputManager(Parent root) {
        InputManager.root = root;
        materialsList = new ArrayList<>();
        init();
        try {
            materialsList = FXCollections.observableArrayList(XmlParser.readXMLFile(fileName));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void init() {
        textFieldMaterialName = (TextField) root.lookup("#textFieldMaterialName");
    }




    @FXML
    public void onSaveInputButton() throws IOException {
        XmlParser.writeXMLFile(fileName,materialsList);
    }


    @FXML
    public void onAddMaterialButton() throws IOException {
        materialsList.add(new Material(textFieldMaterialName.getText()));
        //System.out.println(materialsList.get(materialsList.size()-1).getName());
        //materialsList.get(materialsList.size()-1).printMaterialAndProperties();

        ///
        String imagePath=getImageChooser();
        if (imagePath.compareTo("-")!=0) {
            System.out.println(imagePath);
            materialsList.get(materialsList.size() - 1).setMaterialImage(new Image(imagePath, 32, 32, false, false));
        }

    }


    /*
    @FXML
    public void onAddPropertyButton(String prop) throws IOException {
        materialsList.get(materialsList.size()-1).addProperty(prop,"");
        materialsList.get(materialsList.size()-1).printMaterialAndProperties();
    }*/


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
        fc.setTitle("Выбрать изображение");

        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Images", "*.jpg", "*.gif", "*.png","*.tiff","*.jpeg");

        fc.getExtensionFilters().add(fileExtensions);

        File file = fc.showOpenDialog(new Stage());
        if (file!=null) {
            String res=getRelativePath(file.getAbsolutePath());
            return res;
        }
        else  {
            return "-";
        }
    }

    private String getRelativePath(String path){
        String currPath=Paths.get("").toAbsolutePath().toString();
        int index=path.indexOf(currPath);
        if (index==0) {
            System.out.println();
            currPath=path.substring(currPath.length());
            if (currPath.indexOf("/src")!=0) {
                return currPath;
            }
            else {
                return currPath.substring(4);
            }
        }
        else return "-";
    }

}
