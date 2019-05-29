package sample.manager;

import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import sample.Experiment;
import sample.resource.Brick;
import sample.statistic.Statistic;

import java.util.*;

public class HistogramManager {
    static Parent root;
    Experiment experiment;
    List<Brick> brickList;

    ChoiceBox<String> objectChoiceBoxId;
    ChoiceBox<String> numberChoiceBoxId;
    BarChart<String, Number> barChartId;

    public HistogramManager(Parent root, Experiment experiment) {
        this.experiment = experiment;
        HistogramManager.root = root;
        init();
        brickList = experiment.getLogisticBrickList();
    }

    private void init() {
        barChartId = (BarChart<String, Number>) root.lookup("#barChartId");
        objectChoiceBoxId = (ChoiceBox<String>) root.lookup("#objectChoiceBoxId");
        List<String> objList = new ArrayList<>();
        objList.add("Бригады");
        objList.add("Смешивание");
        objList.add("Формовка");
        objList.add("Сушка");
        objList.add("Обжиг");
        objList.add("Логистика");
        objectChoiceBoxId.getItems().addAll(objList);

        numberChoiceBoxId = (ChoiceBox<String>) root.lookup("#numberChoiceBoxId");
        objectChoiceBoxId.valueProperty().addListener((observable, oldValue, newValue) -> {
            int size;
            int index = objectChoiceBoxId.getItems().indexOf(newValue);
            if (index == 0) {
                size = experiment.getBrigades().size();
            }
            else {
                size = experiment.getStageQualityList().get(index - 1).getStageToolQuality().size();
            }
            numberChoiceBoxId.getItems().clear();
            for (int i = 0; i < size; i++) {
                numberChoiceBoxId.getItems().add(""+ (i + 1));
            }
        });
    }

    public void onAppyFilterButton() {
        brickList = experiment.getLogisticBrickList();
        int objId = objectChoiceBoxId.getSelectionModel().getSelectedIndex();
        if (objId != -1) {
            int size = (objId == 0) ? experiment.getBrigades().size(): experiment.getStageQualityList().get(objId - 1).getStageToolQuality().size();
            brickList = experiment.getFilterBrick(numberChoiceBoxId.getSelectionModel().getSelectedIndex(), size);
        }
    }

    public void onBuildButton() {
        buildHistogram(brickList);
    }

    private void buildHistogram(List<Brick> brickList) {
        if (brickList.size() == 0) {
            return;
        }
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
        Statistic statistic = new Statistic();
        statistic.customCalculateBrickStat(brickList);
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
