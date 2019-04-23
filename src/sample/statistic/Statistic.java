package sample.statistic;

import sample.resource.Brick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistic {

    public static void printBrickStat(List<Brick> brickList){
        Map<String,Double> defectsCountMap=new HashMap<>();

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


        printResult(defectsCountMap);

    }



    private static void printResult(Map map){
        System.out.println("Количество дефектов");
        for (Object o: map.keySet()){
            System.out.println(o+": "+map.get(o));
        }
    }
}
