package sample.manager;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.Main;
import sample.sampling.SamplingControl;
import sample.resource.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplingChartManager {

    private Parent root;
    private List<Double> sampleList;
    private Map<Material, Double> materialQualityMap;
    private SamplingControl samplingControl;
    private int steps;

    @FXML
    private LineChart<String, Double> controlChartId;

    @FXML
    private Text outputTextId;

    public Text getOutputTextId() {
        return outputTextId;
    }

    public void setOutputTextId(Text outputTextId) {
        this.outputTextId = outputTextId;
    }

    public List<Double> getSampleList() {
        return sampleList;
    }

    public void setSampleList(List<Double> sampleList) {
        this.sampleList = sampleList;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public LineChart<String, java.lang.Double> getControlChartId() {
        return controlChartId;
    }

    public void setControlChartId(LineChart<String, java.lang.Double> controlChartId) {
        this.controlChartId = controlChartId;
    }

    public SamplingChartManager(Parent root,
                                List<Double> sampleList,
                                Map<Material, Double> materialMap,
                                SamplingControl samplingControl,
                                int steps) {
        this.root = root;
        this.sampleList = new ArrayList<>(sampleList);
        this.materialQualityMap = new HashMap<>(materialMap);
        this.samplingControl = samplingControl;
        this.steps = steps;
        init();
    }

    private void init() {
        outputTextId= (Text) root.lookup("#outputTextId");
        controlChartId = (LineChart<String, Double>) root.lookup("#controlChartId");
        buildChart();
    }

    public void buildChart(){
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        //avalancheChartID;
        controlChartId.setTitle("Оперативная характеристика");
        controlChartId.setLegendVisible(false);
        controlChartId.getStylesheets().add(Main.class.getResource("/sample/css/samplingChart.css").toExternalForm());

        XYChart.Series<String, Double> series  = new XYChart.Series<String, java.lang.Double>();
        XYChart.Series<String, Double> sampleSeries  = new XYChart.Series<>();
        ///////TEST OPERATE CHARACTERISTICS
        Double f=0d;
        int aFlag=0, bFlag=0;
        for (int i=0;i<100;i++){
            samplingControl.setQ((double)i/100);
            f = (steps == 1) ? samplingControl.getF((double)i/100, 0): samplingControl.getF2Step((double)i/100);
            if (f<=1-samplingControl.getAlpha() && aFlag==0){
                String alphaS=("при alpha="+(samplingControl.getAlpha())+" q="+String.format("%.2f",(samplingControl.getQ()-0.01)));
                Label alpha=new Label(alphaS);
                alpha.setTranslateX(300);
                ((GridPane)root).getChildren().addAll(alpha);
                aFlag=1;
            }
            if (f<=samplingControl.getBeta() && bFlag==0){
                String betaS=("при beta="+samplingControl.getAlpha()+" q="+String.format("%.2f",(samplingControl.getQ()-0.01)));
                Label beta=new Label(betaS);
                beta.setTranslateX(300);
                beta.setTranslateY(30);
                ((GridPane)root).getChildren().addAll(beta);
                bFlag=1;
            }
            series.getData().add(new XYChart.Data<>(""+(double)i, f));
            sampleSeries.getData().add(new XYChart.Data<>(""+(double)i,sampleList.get(i)));
            System.out.println("q: "+(double)i/100+" p: "+f);

        }
        //

        //
        //////////
        series.setName("Расчетные данные");
        sampleSeries.setName("Сгенерированные данные");
        controlChartId.getData().add(series);
        controlChartId.getData().add(sampleSeries);
        controlChartId.getXAxis().setLabel("Доля дефектной продукции в %");
        controlChartId.getYAxis().setLabel("Вероятность приема выборки");
        controlChartId.setCreateSymbols(false);
        controlChartId.setLegendVisible(true);
        controlChartId.setVisible(true);

    }

    public void onBackButton(){
        materialQualityMap.clear();
    }

}
