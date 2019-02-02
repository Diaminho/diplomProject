package sample.managers;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import sample.parsers.XmlParser;
import sample.resources.Material;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialsListManager {

    private TableView<Material> materialsTableID;
    private TableColumn materialNameColumn;
    private TableColumn chooseColumn;
    private TableColumn volumeColumn;

    private ObservableList<Material> materialsList= FXCollections.observableArrayList();
    private static Parent root;
    private final List<BooleanProperty> on = new ArrayList<>();
    private final List<String> volume = new ArrayList<>();



    public List getMaterialsList() {
        return materialsList;
    }

    public MaterialsListManager(Parent root) {
        MaterialsListManager.root = root;
        init();
        /*
        materialsList=new ArrayList<>();
        materialsList.add(new Material("Песок"));
        materialsList.get(0).setMaterialImage(new Image("/sample/images/cement.jpg"));
        materialsList.add(new Material("Глина"));
        */

        try {

            materialsList= FXCollections.observableArrayList(XmlParser.readXMLFile("./materials.xml"));


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fillListView();
        fillOnList();
        fillVolumeList();
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

        materialsTableID.getItems().addAll(materialsList);
        //materialNameColumn.setText();
        System.out.println(""+materialsList.get(0));
        //ma
        //materialNameColumn.setitems()

    }

    private void setCheckBox(){
        //chooseColumn=new TableColumn("CheckBox");
        //chooseColumn.setMinWidth(50);
        chooseColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Material, CheckBox>, ObservableValue<CheckBox>>) arg0 -> {

            Material mater = arg0.getValue();
            CheckBox checkBox = new CheckBox();
            //checkBox.setSelected(false);

            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> checkBox.setSelected(new_val));

            on.set(materialsList.indexOf(mater),checkBox.selectedProperty());
            return new SimpleObjectProperty<>(checkBox);

        });
    }


    private void setMaterialNameCol(){
        materialNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        materialNameColumn.setCellFactory(TextFieldTableCell.<Material> forTableColumn());
    }

    private void setMaterialVolume(){
        //volumeColumn.setEditable(true);
        volumeColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Material, TextField>, ObservableValue<TextField>>)arg0 -> {

            Material mater = arg0.getValue();
            TextField volumeTextField =new TextField();
            //volumeString.setSelected(false);

            volumeTextField.textProperty().addListener((ov, old_val, new_val) -> volume.set(materialsList.indexOf(mater),new_val));

            System.out.println("VOLUME: "+volume.get(materialsList.indexOf(mater)));
            return new SimpleObjectProperty<>(volumeTextField);

        });

    }


    private void fillOnList(){
        for (Material mat: materialsList){
            on.add((new SimpleBooleanProperty(false)));
        }
    }

    private void fillVolumeList(){
        for (Material mat: materialsList){
            volume.add("0");
        }
    }


    public List getSelectedMaterials(){
        List selectedItems=new ArrayList();
        for (int i = 0; i< materialsList.size(); i++){
            if (on.get(i).getValue()){
                selectedItems.add(materialsList.get(i));
            }
        }
        return selectedItems;
    }

    public int getIndexFromList(String value){
        int i=0;
        for (Object s: materialsTableID.getItems()){
            if(((String) s).compareTo(value)==0){
                return i;
            }
            i++;
        }
        return -1;
    }

    public void onCloseButton(){
        /*for (Object o: volume){
            System.out.println(o);
        }*/
    }
}
