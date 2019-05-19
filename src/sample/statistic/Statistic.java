package sample.statistic;

import sample.resource.Brick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistic {
    private Map<String, Double> defectsCountMap;


    public Map<String, Double> getDefectsCountMap() {
        return defectsCountMap;
    }

    public void calculateBrickStat(List<Brick> brickList){
        defectsCountMap=new HashMap<>();

        for (String s:brickList.get(0).getProperties().keySet()){
            defectsCountMap.put(s,0d);
        }

        for (Brick brick:brickList.subList(brickList.size()-100,brickList.size())){
            for (String property:brick.getProperties().keySet()){
                if (!brick.getProperties().get(property)) {
                    defectsCountMap.replace(property, defectsCountMap.get(property) + 1);
                }
            }
        }

        defectsCountMap.replaceAll((k, v) -> v=v/100);
    }


    public void printResult(){
        System.out.println("Количество дефектов");
        for (Object o: defectsCountMap.keySet()){
            System.out.println(o+": " + defectsCountMap.get(o));
        }
    }
}
