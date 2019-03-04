package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.Main;
import sample.operateChars.SamplingControl;
import sample.resources.Material;

import java.util.ArrayList;
import java.util.List;

public class SamplingChartManager {

    private Parent root;
    private List<Material> materialsList;

    @FXML
    private LineChart<String, Double> chartId;

    @FXML
    private Text outputTextId;

    private List<List<Material>> listOfmaterialsList;

    public Text getOutputTextId() {
        return outputTextId;
    }

    public void setOutputTextId(Text outputTextId) {
        this.outputTextId = outputTextId;
    }

    public List<Material> getMaterialsList() {
        return materialsList;
    }

    public void setMaterialsList(List<Material> materialsList) {
        this.materialsList = materialsList;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public LineChart<String, Double> getChartId() {
        return chartId;
    }

    public void setChartId(LineChart<String, Double> chartId) {
        this.chartId = chartId;
    }

    public SamplingChartManager(Parent root, List listOfmaterialsList) {
        this.root = root;
        this.listOfmaterialsList=new ArrayList<>(listOfmaterialsList);
        init();
    }

    private void init() {
        chartId= (LineChart<String, Double>) root.lookup("#chartId");
        outputTextId= (Text) root.lookup("#outputTextId");
        buildChart();
    }

    public void buildChart(){
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        //avalancheChartID;
        chartId.setTitle("Оперативная характеристика");
        chartId.setLegendVisible(false);
        chartId.getStylesheets().add(Main.class.getResource("/sample/css/samplingChart.css").toExternalForm());

        XYChart.Series<String, Double> series  = new XYChart.Series<String, Double>();
        ///////TEST OPERATE CHARACTERISTICS
        SamplingControl samplingControl=new SamplingControl();
        samplingControl.setAc(3);
        int n=listOfmaterialsList.get(0).size();
        System.out.println();

        for (int i=0;i<101;i++){
            samplingControl.setQ((double)i/100);
            series.getData().add(new XYChart.Data<String, Double>(""+(double)i, samplingControl.getL(n,100)));
            System.out.println("N: "+n+" q: "+i+" p: "+samplingControl.getL(n,100));
        }
        //////////
        chartId.getData().add(series);
        chartId.getXAxis().setLabel("Количество дефектной продукции в %");
        chartId.getYAxis().setLabel("Вероятность приема выборки");
        chartId.setCreateSymbols(false);
        chartId.setVisible(true);

    }

    public void onBackButton(){
        listOfmaterialsList.clear();
    }

}
