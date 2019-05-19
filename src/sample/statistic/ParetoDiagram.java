package sample.statistic;

import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import sample.resource.Brick;
import sample.resource.Material;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class ParetoDiagram {
    private BarChart<String, Number> barChart;
    private LineChart<String, Number> lineChart;
    private List<Brick> brickList;
    private StackPane stackPane;

    public ParetoDiagram(StackPane stackPane, List<Brick> brickList) {
        this.brickList = brickList;
        this.stackPane = stackPane;
    }

    public BarChart<String, Number> getBarChart() {
        return barChart;
    }

    public void setBarChart(BarChart<String, Number> barChart) {
        this.barChart = barChart;
    }

    public List<Brick> getBrickList() {
        return brickList;
    }

    public void setBrickList(List<Brick> brickList) {
        this.brickList = brickList;
    }

    public void build() {
        barChart =  new BarChart<String, Number> (new CategoryAxis(), createYaxis());
        lineChart =  new LineChart<String, Number> (new CategoryAxis(), createYaxis());
        setDefaultChartProperties(barChart);
        setDefaultChartProperties(lineChart);
        lineChart.setCreateSymbols(true);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);

        //xAxis.setLabel("Дефекты");
        //yAxis.setLabel("Процентное содержание в выборке");

        Statistic statistic = new Statistic();
        statistic.calculateBrickStat(brickList);
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
        final NumberAxis axis = new NumberAxis(0, 1, 0.05);
        axis.setPrefWidth(35);
        axis.setMinorTickCount(10);

        axis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(axis) {
            @Override public String toString(Number object) {
                return String.format("%.2f", object.floatValue());
            }
        });

        return axis;
    }

}
