package sample.manager;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import sample.Main;
import sample.statistic.ControlChart;

import java.util.ArrayList;
import java.util.List;

public class ControlChartManager {

    private Parent root;
    private List<Boolean> qualityList;

    @FXML
    private LineChart<String, Double> controlChartId;

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

    public ControlChartManager(Parent root, List<Boolean> sampleList) {
        this.root = root;
        this.qualityList =new ArrayList<>(sampleList);
        init();
    }

    private void init() {
        controlChartId = (LineChart<String, Double>) root.lookup("#controlChartId");
        //buildChart();
    }

    public void onBuildButton(){
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        //avalancheChartID;
        controlChartId.setTitle("Оперативная характеристика");
        controlChartId.setLegendVisible(false);
        controlChartId.getStylesheets().add(Main.class.getResource("/sample/css/samplingChart.css").toExternalForm());

        XYChart.Series<String, Double> seriesPi  = new XYChart.Series<String, java.lang.Double>();
        XYChart.Series<String, Double> seriesPAvg  = new XYChart.Series<>();
        XYChart.Series<String, Double> seriesLower  = new XYChart.Series<>();
        XYChart.Series<String, Double> seriesUpper  = new XYChart.Series<>();
        ///////TEST OPERATE CHARACTERISTICS
        int n = qualityList.size() / 20;
        ControlChart controlChart = new ControlChart();
        List <Double> pi = controlChart.calculate(qualityList);
        double pAvg = controlChart.getPAvg(pi);
        List<Double> ULlines = controlChart.getULControlPoint(pAvg, n);

        for (int i=0;i < pi.size(); i++){
            seriesPi.getData().add(new XYChart.Data<>(""+i, pi.get(i)));
            seriesPAvg.getData().add(new XYChart.Data<>(""+i, pAvg));
            seriesLower.getData().add(new XYChart.Data<>(""+i, ULlines.get(0)));
            seriesUpper.getData().add(new XYChart.Data<>(""+i, ULlines.get(1)));
        }

        controlChartId.getData().add(seriesPi);
        controlChartId.getData().add(seriesPAvg);
        controlChartId.getData().add(seriesLower);
        controlChartId.getData().add(seriesUpper);
        controlChartId.getXAxis().setLabel("Количество дефектной продукции в %");
        controlChartId.getYAxis().setLabel("Вероятность приема выборки");
        controlChartId.setCreateSymbols(false);
        controlChartId.setLegendVisible(true);
        controlChartId.setVisible(true);
    }

}

