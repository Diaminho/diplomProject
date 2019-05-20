package sample.manager;

import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import sample.Experiment;
import sample.resource.Brick;
import sample.statistic.Statistic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistogramManager {
    static Parent root;
    Experiment experiment;
    BarChart<String, Number> barChartId;

    public HistogramManager(Parent root, Experiment experiment) {
        this.experiment = experiment;
        HistogramManager.root = root;
        init();
    }

    private void init() {
        barChartId = (BarChart<String, Number>) root.lookup("#barChartId");
    }

    public void onBuildButton() {
        buildHistogram();
    }

    private void buildHistogram() {
        //barChartId = new BarChart<>(new CategoryAxis(), new NumberAxis(0, 1.1, 0.05));
        barChartId.getYAxis().setAutoRanging(false);
        ((NumberAxis) barChartId.getYAxis()).setLowerBound(0);
        ((NumberAxis) barChartId.getYAxis()).setTickUnit(0.05);
        ((NumberAxis) barChartId.getYAxis()).setUpperBound(1);
        barChartId.setCategoryGap(0);
        barChartId.setBarGap(0);
        barChartId.getXAxis().setLabel("Виды дефектов");
        barChartId.getYAxis().setPrefWidth(50);
        barChartId.getYAxis().setLabel("Доля дефекта");
        barChartId.setTitle("Доли всех разновидностей дефектов в кирпичах");
        barChartId.setLegendVisible(false);
        barChartId.setAnimated(false);


        //xAxis.setLabel("Дефекты");
        //yAxis.setLabel("Процентное содержание в выборке");
        List<Brick> brickList = experiment.getLogisticBrickList();
        Statistic statistic = new Statistic();
        statistic.calculateBrickStat(brickList);
        Map<String, Double> defectMap = new HashMap<>(statistic.getDefectsCountMap());
        XYChart.Series<String, Number> seriesBar  = new XYChart.Series<>();
        defectMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(e -> {
                    seriesBar.getData().add(new XYChart.Data<>(e.getKey(), e.getValue()));
                });

        barChartId.getData().addAll(seriesBar);
    }
}
