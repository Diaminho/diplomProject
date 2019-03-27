package sample.manager;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.Main;
import sample.sampling.SamplingControl;
import sample.resource.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplingChartManager {

    private Parent root;
    private List<Double> sampleList;
    private Map<Material,List<Double>> materialQualityMap;
    private SamplingControl samplingControl;

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

    public SamplingChartManager(Parent root,
                                List<Double> sampleList,
                                Map<Material, List<Double>> materialMap,
                                SamplingControl samplingControl) {
        this.root = root;
        this.sampleList=new ArrayList<>(sampleList);
        this.materialQualityMap=new HashMap<>(materialMap);
        this.samplingControl=samplingControl;
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
        int N=materialQualityMap.get(materialQualityMap.keySet().iterator().next()).size();
        Double f=0d;
        int aFlag=0, bFlag=0;
        for (int i=0;i<100;i++){
            samplingControl.setQ((double)i/100);
            f=samplingControl.getF((double)i/100);
            if (f<=1-samplingControl.getAlpha() && aFlag==0){
                String alphaS=("при alpha="+(samplingControl.getAlpha())+" q="+String.format("%.2f",(samplingControl.getQ()-0.01)));
                Label alpha=new Label(alphaS);
                alpha.setTranslateX(300);
                ((GridPane)root).getChildren().addAll(alpha);
                aFlag=1;
            }
            if (f<=samplingControl.getBeta() && bFlag==0){
                String betaS=("при beta="+samplingControl.getAlpha()+" q="+String.format("%.2f",(samplingControl.getQ()-0.01)));
                Label beta=new Label(betaS);
                beta.setTranslateX(300);
                beta.setTranslateY(30);
                ((GridPane)root).getChildren().addAll(beta);
                bFlag=1;
            }
            series.getData().add(new XYChart.Data<>(""+(double)i, f));
            sampleSeries.getData().add(new XYChart.Data<>(""+(double)i,sampleList.get(i)));
            System.out.println("q: "+(double)i/100+" p: "+f);

        }
        //

        //
        //////////
        series.setName("Расчетные данные");
        sampleSeries.setName("Сгенерированные данные");
        chartId.getData().add(series);
        chartId.getData().add(sampleSeries);
        chartId.getXAxis().setLabel("Количество дефектной продукции в %");
        chartId.getYAxis().setLabel("Вероятность приема выборки");
        chartId.setCreateSymbols(false);
        chartId.setLegendVisible(true);
        chartId.setVisible(true);

    }

    public void onBackButton(){
        materialQualityMap.clear();
    }

}
