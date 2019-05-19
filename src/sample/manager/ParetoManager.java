package sample.manager;

import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.StackPane;
import sample.Experiment;
import sample.statistic.ParetoDiagram;

public class ParetoManager {
    private static Parent root;
    private static Experiment experiment;

    StackPane stackPaneId;

    public ParetoManager(Parent root, Experiment experiment) {
        ParetoManager.root = root;
        ParetoManager.experiment = experiment;
        init();
    }

    private void init() {
        stackPaneId = (StackPane) root.lookup("#stackPaneId");
        ParetoDiagram paretoDiagram = new ParetoDiagram(stackPaneId, experiment.getLogisticBrickList());
        paretoDiagram.build();
    }
}
