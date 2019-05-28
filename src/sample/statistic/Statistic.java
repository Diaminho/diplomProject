package sample.statistic;

import sample.Experiment;
import sample.resource.Brick;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistic {
    private Map<String, Double> defectsCountMap;
    private double defectBrickCount = 0;
    private int number = 0;
    private Experiment experiment;

    public Map<String, Double> getDefectsCountMap() {
        return defectsCountMap;
    }

    public void calculateBrickStat(Experiment experiment){
        defectsCountMap = new HashMap<>();
        this.experiment = experiment;
        number = experiment.getLogisticBrickList().size() / 100;
        for (String s: experiment.getLogisticBrickList().get(0).getProperties().keySet()){
            defectsCountMap.put(s,0d);
        }

        for (Brick brick: experiment.getLogisticBrickList().subList(experiment.getLogisticBrickList().size()-100, experiment.getLogisticBrickList().size())){
            for (String property:brick.getProperties().keySet()){
                if (!brick.getProperties().get(property)) {
                    defectsCountMap.replace(property, defectsCountMap.get(property) + 1);
                }
            }
            defectBrickCount += (brick.getAvgQuality()) ? 0: 1;
        }
        defectBrickCount /= 100;
        defectsCountMap.replaceAll((k, v) -> v=v/100);
    }


    public String printResult(){
        String res = "Номер партии: " + number;
        res += "\nНомер бриагады, изготовившей партию: " + (1 +(experiment.getLogisticBrickList().size() / 100) % experiment.getBrigades().size());
        res += "\nНомер партии сырья: " +  (number * 5);
        res += "\nНомер смешивающей установки, изготовившей партию: " + (1 + (experiment.getLogisticBrickList().size() / 100) % experiment.getStageQualityList().get(0).getStageToolQuality().size());
        res += "\nНомер формы: " + (1 + (experiment.getLogisticBrickList().size() / 100) % experiment.getStageQualityList().get(1).getStageToolQuality().size());
        res += "\nНомер сушильной установки: " + (1 + (experiment.getLogisticBrickList().size() / 100) % experiment.getStageQualityList().get(2).getStageToolQuality().size());
        res += "\nНомер печи: " + (1 + (experiment.getLogisticBrickList().size() / 100) % experiment.getStageQualityList().get(3).getStageToolQuality().size());
        res += "\nНомер грузовика, осущестлявшего перевозку: " + (1 + (experiment.getLogisticBrickList().size() / 100) % experiment.getStageQualityList().get(4).getStageToolQuality().size());
        res += "\n\nДоли дефектов";
        System.out.println("Доли дефектов");
        for (Object o: defectsCountMap.keySet()){
            res += "\n" + o + ": " + defectsCountMap.get(o);
            System.out.println(o+": " + defectsCountMap.get(o));
        }
        System.out.println("Дефектных изделий в партии: " + defectBrickCount);
        res += "\nДефектных изделий в партии: " + defectBrickCount;
        return res;
    }
}
