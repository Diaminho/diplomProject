package sample.manager;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import sample.IndexedLinkedHashMap;
import sample.parser.XmlParser;
import sample.resource.Material;
import sample.validator.MaterialsListManagerValidator;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class MaterialsListManager {

    private TableView<Material> materialsTableID;
    private TableColumn materialNameColumn;
    private TableColumn chooseColumn;
    private TableColumn volumeColumn;

    private static Parent root;
    private final List<BooleanProperty> on = new ArrayList<>();

    ///
    private IndexedLinkedHashMap<Material,Integer> materialIntegerMap=new IndexedLinkedHashMap<>();

    public MaterialsListManager(Parent root) {
        MaterialsListManager.root = root;
        init();
        try {
            for (Material mat:FXCollections.observableArrayList(XmlParser.readXMLFile("./materials.xml"))){
                materialIntegerMap.putNew(mat,0);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fillListView();
        fillOnList();
    }


    private void init() {
        materialsTableID = (TableView<Material>) root.lookup("#materialsTableID");
        materialNameColumn =materialsTableID.getColumns().get(0);
        setMaterialNameCol();
        materialNameColumn.setEditable(false);
        chooseColumn =materialsTableID.getColumns().get(1);
        setCheckBox();
        volumeColumn = materialsTableID.getColumns().get(2);
        //System.out.println("VOLUME EDIT"+volumeColumn.editableProperty());
        //materialsTableID.getColumns().set(2,volumeColumn);
        setMaterialVolume();

    }

    private void fillListView(){
        materialsTableID.getItems().addAll(materialIntegerMap.keySet());
    }

    private void setCheckBox(){
        chooseColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Material, CheckBox>, ObservableValue<CheckBox>>) arg0 -> {
            Material mater = arg0.getValue();
            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> checkBox.setSelected(new_val));
            on.set(materialIntegerMap.getIndex(mater),checkBox.selectedProperty());
            return new SimpleObjectProperty<>(checkBox);
        });
    }


    private void setMaterialNameCol(){
        materialNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        materialNameColumn.setCellFactory(TextFieldTableCell.<Material> forTableColumn());
    }

    private void setMaterialVolume(){
        //volumeColumn.setEditable(true);
        volumeColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Material, TextField>, ObservableValue<TextField>>) arg0 -> {
            Material mater = arg0.getValue();
            TextField volumeTextField =new TextField();
            volumeTextField.textProperty().addListener((ov, old_val, new_val) -> materialIntegerMap.replace(mater,Integer.parseInt(new_val)));
            return new SimpleObjectProperty<>(volumeTextField);
        });
    }


    private void fillOnList(){
        for (Object mat:materialIntegerMap.keySet()){
            on.add((new SimpleBooleanProperty(false)));
        }
    }


    public Map getSelectedMaterials(){
        Map<Material, Integer> selectedItems=new HashMap();
        Integer volume=0;
        Material material;
        String errorString;
        for (int i = 0; i< materialIntegerMap.size(); i++){
            if (on.get(i).getValue()){
                material=materialIntegerMap.getValue(i);
                volume=(Integer)materialIntegerMap.get(material);
                //validation

                //check volume
                errorString=MaterialsListManagerValidator.validateVolume(volume);
                if (errorString.compareTo("Ok")!=0){
                    showAlertDialog(errorString);
                    return null;
                    //break;
                }
                selectedItems.put(material,volume);
            }

            if (selectedItems.size()==0){
                showAlertDialog("Не выбраны материалы");
                return null;
            }
        }
        return selectedItems;
    }

    public Integer onCloseButton(){
        if (getSelectedMaterials()!=null) {
            return 0;
        }
        else {
            return 1;
        }
    }

    private void showAlertDialog(String errorString){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка ввода данных");
        alert.setContentText(errorString);
        alert.showAndWait();
    }
}
