package sample.sampling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class SampleFunctions {

    public static List<Double> generateSample(int size, int defectsPercentage, double acceptableQuality){
        Random rnd=new Random();
        List<Double> sample=new ArrayList<>();
        Double value=0d;
        //generate sample without defects
        for (int i=0;i<size;i++) {
            value=acceptableQuality + (rnd.nextDouble() * (1 - acceptableQuality));
            sample.add(value);
        }
        //addiing defects to sample
        int index=0;
        for (int i=0;i<defectsPercentage*size/100;i++){
            value=rnd.nextDouble() * (acceptableQuality);
            do {
                index = rnd.nextInt(size);
            }
            while (sample.get(index)<acceptableQuality);
            sample.set(index,value);
        }
        return  sample;
    }

    public static List<Double> generateAllDeffectLevelsPossibilities(int sampleSize, double acceptableQuality, double ac){
        List<Double> samplePos=new ArrayList<>();
        List<Double> sample=new ArrayList<>();
        for (int i=0;i<101;i++){
            sample=generateSample(sampleSize,i,acceptableQuality);
            double res=check1StepSamplingControl(sample,acceptableQuality,ac);
            samplePos.add(res);
        }
        return samplePos;
    }

    public static int check1StepSamplingControl(List<Double> sample, double quality, double ac){
        int countDef=0, size=sample.size()/10, index=0;
        Random rnd=new Random();
        List<Integer> usedItems=new ArrayList<>();
        for (int i=0;i<=size;i++){
            while (usedItems.indexOf(index)>=0){
                index=rnd.nextInt(size*10);
            }
            usedItems.add(index);
            countDef+=sample.get(index).compareTo(quality)<0 ?  1: 0;
        }
        return countDef<=ac ? 1 : 0;
    }

    public static List<Double> getAvgPossibilities(int size, double quality, double ac, double times){
        List<Double> allLevelsList=new ArrayList<>();
        List<Double> possiblitiesList= Collections.nCopies(100,0d);
        for (int i=0;i<times;i++){
            allLevelsList=generateAllDeffectLevelsPossibilities(size,quality,ac);
            possiblitiesList=summValuesOfTwoLists(possiblitiesList,allLevelsList);
        }
        //find probability
        possiblitiesList.replaceAll(x->x/times);
        return possiblitiesList;
    }

    private static List<Double> summValuesOfTwoLists(List<Double> a, List<Double> b){
        List<Double> res=new ArrayList<>();
        for (int i=0;i<a.size();i++){
            res.add(a.get(i)+b.get(i));
        }
        return res;
    }
}
