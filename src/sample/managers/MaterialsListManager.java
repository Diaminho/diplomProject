package sample.managers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.parsers.XmlParser;
import sample.resources.Material;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaterialsListManager {

    private ListView materialsListID;
    private List<Material> materialsList;
    private static Parent root;
    private final List<BooleanProperty> on = new ArrayList<>();



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

        materialsList=new ArrayList<>();
        try {
            materialsList= XmlParser.readXMLFile("./materials.xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        fillListView();
        fillOnList();
    }

    private void fillListView(){
        for (Object o:materialsList){
            materialsListID.getItems().add(((Material)o).getName());
        }
    }

    private void fillOnList(){
        while (on.size()<materialsList.size()){
            on.add(new SimpleBooleanProperty(false));
        }
    }

    private void init() {
        materialsListID = (ListView) root.lookup("#materialsListID");
    }


    //set CheckBox for listView
    public void setCheckBox() {
        materialsListID.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(String item) {
                BooleanProperty onT=new SimpleBooleanProperty();
                onT.addListener((obs, wasSelected, isNowSelected) ->
                        System.out.println("Check box for " + item + " changed from " + wasSelected + " to " + isNowSelected)
                );

                int index=getIndexFromList(item);

                if (index>=0) {
                    on.set(index, onT);
                }
                return onT;
            }
        }));

    }

    public List getSelectedMaterials(){
        List selectedItems=new ArrayList();
        for (int i=0;i<materialsListID.getItems().size();i++){
            if (on.get(i).getValue()){
                selectedItems.add(materialsList.get(i));
            }
        }
        return selectedItems;
    }

    public int getIndexFromList(String value){
        int i=0;
        for (Object s:materialsListID.getItems()){
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
