package sample.managers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Font;
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

    public SamplingChartManager(Parent root) {
        this.root = root;
        init();
    }

    private void init() {
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

        XYChart.Series<String, Double> series  = new XYChart.Series<String, Double>();
        ///////TEST OPERATE CHARACTERISTICS
        SamplingControl samplingControl=new SamplingControl();
        samplingControl.setAc(10);
        samplingControl.setQ(0.05);
        samplingControl.setAlpha(0.05);
        int n=samplingControl.getN(500);
        System.out.println();
        System.out.println(samplingControl.getF());
        System.out.println(samplingControl.getCombination(5,2));

        for (int i=0;i<100;i++){
            samplingControl.setQ((double)i/100);
            series.getData().add(new XYChart.Data<String, Double>(""+(double)i/100, samplingControl.getF()));
        }
        //////////
        chartId.getData().add(series);
        chartId.getXAxis().setLabel("Количество дефектной продукции в %");
        chartId.getYAxis().setLabel("Вероятность приема выборки");
        chartId.setCreateSymbols(false);
        chartId.setVisible(true);

    }

    public void onBackButton(){

    }

}
