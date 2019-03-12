package sample.manager;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import sample.Main;
import sample.operateChar.SamplingControl;
import sample.resource.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplingChartManager {

    private Parent root;
    private List<Double> sampleList;
    private Map<Material,List<Double>> materialQualityMap;

    @FXML
    private LineChart<String, Double> chartId;

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

    public LineChart<String, java.lang.Double> getChartId() {
        return chartId;
    }

    public void setChartId(LineChart<String, java.lang.Double> chartId) {
        this.chartId = chartId;
    }

    public SamplingChartManager(Parent root, List<Double> sampleList, Map<Material, List<Double>> materialMap) {
        this.root = root;
        this.sampleList=new ArrayList<>(sampleList);
        this.materialQualityMap=new HashMap<>(materialMap);
        init();
    }

    private void init() {
        outputTextId= (Text) root.lookup("#outputTextId");
        chartId= (LineChart<String, Double>) root.lookup("#chartId");
        buildChart();
    }

    public void buildChart(){
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        //avalancheChartID;
        chartId.setTitle("Оперативная характеристика");
        chartId.setLegendVisible(false);
        chartId.getStylesheets().add(Main.class.getResource("/sample/css/samplingChart.css").toExternalForm());

        XYChart.Series<String, Double> series  = new XYChart.Series<String, java.lang.Double>();
        XYChart.Series<String, Double> sampleSeries  = new XYChart.Series<>();
        ///////TEST OPERATE CHARACTERISTICS
        SamplingControl samplingControl=new SamplingControl();
        samplingControl.setAc(3);
        int n=materialQualityMap.get(materialQualityMap.keySet().iterator().next()).size();
        System.out.println();

        for (int i=0;i<100;i++){
            samplingControl.setQ((double)i/100);
            series.getData().add(new XYChart.Data<>(""+(double)i, samplingControl.getL(n,100)));
            sampleSeries.getData().add(new XYChart.Data<>(""+(double)i,sampleList.get(i)));
            System.out.println("N: "+n+" q: "+i+" p: "+samplingControl.getL(n,100));
        }
        //////////
        chartId.getData().add(series);
        chartId.getData().add(sampleSeries);
        chartId.getXAxis().setLabel("Количество дефектной продукции в %");
        chartId.getYAxis().setLabel("Вероятность приема выборки");
        chartId.setCreateSymbols(false);
        chartId.setVisible(true);

    }

    public void onBackButton(){
        materialQualityMap.clear();
    }

}