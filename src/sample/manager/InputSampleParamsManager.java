package sample.manager;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import sample.Experiment;
import sample.resource.Brick;
import sample.resource.Material;
import sample.sampling.SampleFunctions;
import sample.sampling.SamplingControl;
import sample.validator.InputSampleParamsManagerValidator;

import java.util.ArrayList;
import java.util.List;

public class InputSampleParamsManager {

    private static Parent root;

    @FXML
    private TextField sampleSizeId;

    @FXML
    private TextField acId;

    @FXML
    private TextField alphaId;

    @FXML
    private TextField betaId;

    @FXML
    private ChoiceBox<String> choiceMaterialBoxId;


    private ChoiceBox<Integer> choiceStepId;

    @FXML
    private TextField sampleCountId;

    private ChoiceBox<Integer> choiceAcId;



    private List<Integer> acList = new ArrayList<>();

    private Experiment experiment;

    private List<Double> sample;

    private SamplingControl samplingControl;


    public List<Double> getSample() {
        return sample;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public InputSampleParamsManager(Parent root, Experiment experiment) {
        InputSampleParamsManager.root = root;
        this.experiment = experiment;
        init();
        fillChoiceBox();
    }

    private void init() {
        sampleSizeId = (TextField) root.lookup("#sampleSizeId");
        acId =(TextField) root.lookup("#acId");
        sampleCountId = (TextField) root.lookup("#sampleCountId");
        choiceMaterialBoxId = (ChoiceBox<String>) root.lookup("#choiceMaterialBoxId");
        alphaId = (TextField) root.lookup("#alphaId");
        betaId = (TextField) root.lookup("#betaId");
        choiceStepId = (ChoiceBox<Integer>) root.lookup("#choiceStepId");
        choiceAcId = (ChoiceBox<Integer>) root.lookup("#choiceAcId");
        //System.out.println("MAP: "+material);
    }

    private void fillChoiceBox(){

        choiceMaterialBoxId.getItems().clear();
        choiceMaterialBoxId.getItems().add("Кирпич");

        for (Material m: experiment.getMaterialMap().keySet()) {
            choiceMaterialBoxId.getItems().add(m.getName());
        }
        choiceStepId.getItems().add(1);
        choiceStepId.getItems().add(2);

        choiceStepId.getSelectionModel().selectedIndexProperty().addListener(((observable, old, newVal) -> {
            choiceAcId.getItems().clear();
            acList.clear();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < newVal.intValue() + 1; i++) {
                list.add(i + 1);
                acList.add(0);
            }
            choiceAcId.getItems().addAll(list);
        }));

        choiceAcId.getSelectionModel().selectedIndexProperty().addListener((observable, old, newVal) -> {
            if (newVal.intValue() != -1) {
                acId.setText("" + acList.get(newVal.intValue()));
            }
            else {
                acId.setText("-");
            }
        });
    }

    public SamplingControl getSamplingControl() {
        return samplingControl;
    }

    @FXML
    public void onBackButton(){

    }

    @FXML
    public int onGenerateButton(){
        Integer size = tryToParseString(sampleSizeId.getText());
        Integer count = tryToParseString(sampleCountId.getText());
        Integer ac = tryToParseString(acId.getText());
        //
        Integer maxSize = 0;
        String chosenMaterial = choiceMaterialBoxId.getSelectionModel().getSelectedItem();
        if(chosenMaterial==null){
            showAlertDialog("Не выбран материал");
            return 1;
        }
        for (Material m: experiment.getMaterialMap().keySet()){
            if (m.getName().compareTo(chosenMaterial) == 0){
                maxSize = experiment.getMaterialMap().get(m);
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
            samplingControl = new SamplingControl();
            samplingControl.setAc(ac);
            samplingControl.setN(size);
            samplingControl.setAlpha(Double.parseDouble(alphaId.getText()));
            samplingControl.setBeta(Double.parseDouble(betaId.getText()));
            sample = SampleFunctions.getAvgPossibilities(Integer.parseInt(sampleSizeId.getText()), ac, count);
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


    public void onDoSamplingControlButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Выборочный контроль");

        List<Boolean> sample = new ArrayList<>();
        List<Brick> objectList = experiment.getLogisticBrickList().subList(experiment.getLogisticBrickList().size() - 100, experiment.getLogisticBrickList().size());
        for (Brick m: objectList) {
           sample.add(m.getAvgQuality());
        }

        int chosenStep = choiceStepId.getSelectionModel().getSelectedIndex();
        int result = (chosenStep == 0) ?
                SampleFunctions.check1StepSamplingControl(sample, Integer.parseInt(sampleSizeId.getText()), acList.get(0))
                : SampleFunctions.check2StepSamplingControl(sample, Integer.parseInt(sampleSizeId.getText()), acList);
        if (result == 1) {
           alert.setHeaderText("Партия прошла выборочный контроль.");
        }
        else {
           alert.setHeaderText("Партия не прошла выборочный контроль");
        }
        alert.setContentText(alert.getContentText() + "Размер выборки: " + sampleSizeId.getText() + "\n" +
               "Количество дефектных изделий: " + SampleFunctions.count +
                "\nКоличество пройденных ступеней: " + SampleFunctions.steps);
        alert.show();
    }

    public void onSaveButton() {
        acList.set(choiceAcId.getSelectionModel().getSelectedIndex(), Integer.parseInt(acId.getText()));
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


