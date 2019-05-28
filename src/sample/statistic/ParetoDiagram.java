package sample.statistic;

import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import sample.Experiment;
import sample.resource.Brick;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParetoDiagram {
    private BarChart<String, Number> barChart;
    private LineChart<String, Number> lineChart;
    private Experiment experiment;
    private StackPane stackPane;

    public ParetoDiagram(StackPane stackPane, Experiment experiment) {
        this.experiment = experiment;
        this.stackPane = stackPane;
    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }

    public void setBarChart(BarChart<String, Number> barChart) {
        this.barChart = barChart;
    }

    public void build() {
        barChart =  new BarChart<String, Number> (new CategoryAxis(), createYaxis());
        lineChart =  new LineChart<String, Number> (new CategoryAxis(), createYaxis());
        setDefaultChartProperties(barChart);
        setDefaultChartProperties(lineChart);
        lineChart.setCreateSymbols(true);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);
        barChart.getXAxis().setLabel("Виды дефектов");
        barChart.getYAxis().setLabel("Доля дефекта");
        barChart.setTitle("Доли всех разновидностей дефектов");

        lineChart.getXAxis().setLabel("Виды дефектов");
        lineChart.getYAxis().setLabel("Доля дефекта");
        lineChart.setTitle("Доли всех разновидностей дефектов");

        //xAxis.setLabel("Дефекты");
        //yAxis.setLabel("Процентное содержание в выборке");

        Statistic statistic = new Statistic();
        statistic.calculateBrickStat(experiment);
        Map<String, Double> defectMap = new HashMap<>(statistic.getDefectsCountMap());
        XYChart.Series<String, Number> series  = new XYChart.Series<>();
        XYChart.Series<String, Number> seriesBar  = new XYChart.Series<>();
        defectMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(e -> {
                    if (series.getData().size() != 0) {
                        series.getData().add(new XYChart.Data<>(e.getKey(), series.getData().get(series.getData().size()-1).getYValue().doubleValue() + e.getValue()));
                    }
                    else {
                        series.getData().add(new XYChart.Data<>(e.getKey(), e.getValue()));
                    }
                    seriesBar.getData().add(new XYChart.Data<>(e.getKey(), e.getValue()));
                });
        double oldSumDef = series.getData().get(series.getData().size() - 1).getYValue().doubleValue();
        series.getData().stream().forEach(e -> {
            e.setYValue( e.getYValue().doubleValue() / oldSumDef);
        });

        seriesBar.getData().stream().forEach(e -> {
            e.setYValue( e.getYValue().doubleValue() / oldSumDef);
        });
        lineChart.getData().addAll(series);
        barChart.getData().addAll(seriesBar);

        layerCharts(barChart, lineChart);
    }


    private void layerCharts(final XYChart<String, Number> ... charts) {
        for (int i = 1; i < charts.length; i++) {
            configureOverlayChart(charts[i]);
        }
        stackPane.getChildren().addAll(charts);
    }

    private void configureOverlayChart(final XYChart<String, Number> chart) {
        chart.setAlternativeRowFillVisible(false);
        chart.setAlternativeColumnFillVisible(false);
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.getXAxis().setVisible(false);
        chart.getYAxis().setVisible(false);

        chart.getStylesheets().addAll(getClass().getResource("/sample/css/paretoChart.css").toExternalForm());
    }

    private void setDefaultChartProperties(final XYChart<String, Number> chart) {
        chart.setLegendVisible(false);
        chart.setAnimated(false);
    }

    private NumberAxis createYaxis() {
        final NumberAxis axis = new NumberAxis(0, 1.1, 0.05);
        axis.setPrefWidth(65);
        axis.setMinorTickCount(10);

        axis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(axis) {
            @Override public String toString(Number object) {
                return String.format("%.2f", object.floatValue());
            }
        });

        return axis;
    }

}
