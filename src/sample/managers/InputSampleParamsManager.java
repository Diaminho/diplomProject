package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import sample.resources.Material;
import sample.sampling.SampleFunctions;

import java.util.List;
import java.util.Map;

public class InputSampleParamsManager {

    private static Parent root;

    @FXML
    private TextField sampleSizeId;

    @FXML
    private TextField acId;

    @FXML
    private TextField sampleCountId;

    private Map<Material, Long> materials;


    public InputSampleParamsManager(Parent root, Map materials) {
        InputSampleParamsManager.root = root;
        this.materials=materials;
        init();
    }

    private void init() {
        sampleSizeId = (TextField) root.lookup("#sampleSizeId");
        acId =(TextField) root.lookup("#acId");
        sampleCountId = (TextField) root.lookup("#sampleCountId");
        //System.out.println("MAP: "+materials);
    }


    @FXML
    public void onBackButton(){

    }

    @FXML
    public void onGenerateButton(){
        Integer size=tryToParseString(sampleSizeId.getText());
        Integer count=tryToParseString(sampleCountId.getText());
        Integer ac=tryToParseString(acId.getText());
        if (size!=null && count!=null && ac!=null){
            //System.out.println("!!!!");
            //List sample=SampleFunctions.generateSample(size,10,0.8);
            List<Double> sample=SampleFunctions.getAvgPossibilities(size*10,0.8,ac, count);
            for (Object o:sample){
                System.out.println(o);
            }
        }
        //
        System.out.println();
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
}


