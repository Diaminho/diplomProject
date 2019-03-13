package sample.manager;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.sampling.SamplingControl;
import sample.resource.Material;
import sample.sampling.SampleFunctions;
import sample.validator.InputSampleParamsManagerValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputSampleParamsManager {

    private static Parent root;

    @FXML
    private TextField sampleSizeId;

    @FXML
    private TextField acId;

    @FXML
    private ChoiceBox<String> choiceMaterialBoxId;

    @FXML
    private TextField sampleCountId;

    private Map<Material, List<Double>> materials;

    private List<Double> sample;

    private SamplingControl samplingControl;


    public List<Double> getSample() {
        return sample;
    }

    public Map<Material, List<Double>> getMaterials() {
        return materials;
    }

    public InputSampleParamsManager(Parent root, Map materials) {
        InputSampleParamsManager.root = root;
        this.materials=new HashMap<>(materials);
        init();
        fillChoiceBox();
    }

    private void init() {
        sampleSizeId = (TextField) root.lookup("#sampleSizeId");
        acId =(TextField) root.lookup("#acId");
        sampleCountId = (TextField) root.lookup("#sampleCountId");
        choiceMaterialBoxId = (ChoiceBox<String>) root.lookup("#choiceMaterialBoxId");
        //System.out.println("MAP: "+material);
    }

    private void fillChoiceBox(){
        for (Material m:materials.keySet()) {
            choiceMaterialBoxId.getItems().add(m.getName());
        }
    }

    public SamplingControl getSamplingControl() {
        return samplingControl;
    }

    @FXML
    public void onBackButton(){

    }

    @FXML
    public int onGenerateButton(){
        Integer size=tryToParseString(sampleSizeId.getText());
        Integer count=tryToParseString(sampleCountId.getText());
        Integer ac=tryToParseString(acId.getText());
        //
        Integer maxSize=0;
        String chosenMaterial=choiceMaterialBoxId.getSelectionModel().getSelectedItem();
        for (Material m:materials.keySet()){
            if (m.getName().compareTo(chosenMaterial)==0){
                maxSize=materials.get(m).size();
                break;
            }
        }
        //
        String error=InputSampleParamsManagerValidator.validateSize(size, maxSize);
        error=(error.compareTo("Ok")==0) ? InputSampleParamsManagerValidator.validateAc(ac, size): error;
        error=(error.compareTo("Ok")==0) ? InputSampleParamsManagerValidator.validateCount(count): error;
        if (error.compareTo("Ok")==0){
            //System.out.println("!!!!");
            //List sample=SampleFunctions.generateSample(size,10,0.8);
            samplingControl=new SamplingControl();
            samplingControl.setAc(ac);
            samplingControl.setN(size);
            sample=SampleFunctions.getAvgPossibilities(maxSize,0.8,ac, count);
            return 0;
        }
        else {
            //HERE IS ALERT
            showAlertDialog(error);
            return 1;
            //
        }
        //
    }

    private Integer tryToParseString(String s){
        Integer res=null;
        if (s!=null) {
            try{
                res=Integer.parseInt(s);
            }catch(NumberFormatException e){
                System.out.println("Неверно введено число");
            }
        }
        return res;
    }

    private void showAlertDialog(String errorString){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка ввода данных");
        alert.setContentText(errorString);
        alert.showAndWait();
    }

    ///validators


    ///
}


