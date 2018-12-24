package sample.managers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
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
import java.util.List;

public class MaterialsListManager {

    private TableView<Material> materialsTableID;
    private TableColumn materialNameColumn;
    private TableColumn chooseColumn;
    private TableColumn <Material, Double> volumeColumn;

    private ObservableList<Material> materialsList= FXCollections.observableArrayList();
    private static Parent root;
    private final List<BooleanProperty> on = new ArrayList<>();
    private final List<String> value = new ArrayList<>();



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
    }


    private void init() {
        materialsTableID = (TableView<Material>) root.lookup("#materialsTableID");
        materialNameColumn =materialsTableID.getColumns().get(0);
        setMaterialNameCol();
        materialNameColumn.setEditable(false);
        chooseColumn =materialsTableID.getColumns().get(1);
        setCheckBox();
        volumeColumn =new TableColumn<Material,Double>("Количество");
        volumeColumn.setEditable(true);
        setMaterialVolume();
        materialsTableID.getColumns().set(2,volumeColumn);

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
        chooseColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Material, CheckBox>, ObservableValue<CheckBox>>() {

            @Override
            public ObservableValue<CheckBox> call(
                    TableColumn.CellDataFeatures<Material, CheckBox> arg0) {

                Material mater = arg0.getValue();
                CheckBox checkBox = new CheckBox();
                //checkBox.setSelected(false);

                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                                        Boolean old_val, Boolean new_val) {

                        checkBox.setSelected(new_val);

                    }
                });

                on.set(materialsList.indexOf(mater),checkBox.selectedProperty());
                return new SimpleObjectProperty<CheckBox>(checkBox);

            }

        });
    }


    private void setMaterialNameCol(){
        materialNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        materialNameColumn.setCellFactory(TextFieldTableCell.<Material> forTableColumn());
    }

    private void setMaterialVolume(){
        volumeColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
        volumeColumn.setCellFactory(TextFieldTableCell.<Material, Double>forTableColumn(new DoubleStringConverter()));
        //ON VOLUME EDIT
        /*volumeColumn.setOnEditCommit(
                t -> ((Material) t.getItems().get(
                        t.getTablePosition().getRow())
                ).setVolume(Double.parseDouble(t.getNewValue())));
        */


        volumeColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Material, Double> event) -> {
        final Double volume = event.getNewValue() != null ?
                event.getNewValue() : event.getOldValue();
        ((Material) event.getTableView().getItems()
                .get(event.getTablePosition().getRow())).setVolume(volume);
        materialsTableID.refresh();
        });
    }


    private void fillOnList(){
        while (on.size()<materialsList.size()){
            on.add(new SimpleBooleanProperty(false));
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


    @FXML
    public void onCloseButton(){

    }
}
