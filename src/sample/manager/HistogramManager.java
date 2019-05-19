package sample.manager;

import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import sample.Experiment;

public class HistogramManager {
    static Parent root;
    Experiment experiment;
    BarChart<String, Number> barChartId;

    public HistogramManager(Parent root, Experiment experiment) {
        this.experiment = experiment;
        HistogramManager.root = root;
    }

    private void init() {
        barChartId = (BarChart<String, Number>) root.lookup("#barChartId");
    }

    public void onBuildButton() {
        
    }
}
