package sample.sampling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SampleFunctions {

    public static int count = 0;
    public static int steps = 1;

    public static List<Boolean> generateSample(int size, int defectsPercentage) {
        Random rnd=new Random();
        List<Boolean> sample=new ArrayList<>();
        //generate sample without defects
        boolean value;
        for (int i=0;i<size;i++) {
            value = true;
            sample.add(value);
        }
        //addiing defects to sample
        int index=0;
        for (int i=0;i < defectsPercentage*size / 100; i++){
            value = false;
            do {
                index = rnd.nextInt(size);
            }
            while (!sample.get(index));
            sample.set(index,value);
        }
        return  sample;
    }


    //Need analog for 2STEP
    public static List<Double> generateAllDeffectLevelsPossibilities(int sampleSize, double ac){
        List<Double> samplePos=new ArrayList<>();
        List<Boolean> sample=new ArrayList<>();
        int res;
        for (int i=0;i<101;i++){
            sample = generateSample(sampleSize * 10, i);
            res = check1StepSamplingControl(sample, sampleSize, ac);
            samplePos.add((double) res);
        }
        return samplePos;
    }

    //Need analog for 2STEP
    public static List<Double> generateAllDeffectLevelsPossibilities2(int sampleSize, List<Integer> ac, List<Integer> re){
        List<Double> samplePos=new ArrayList<>();
        List<Boolean> sample=new ArrayList<>();
        int res;
        for (int i=0;i<101;i++){
            sample = generateSample(sampleSize * 10, i);
            res = check2StepSamplingControl(sample, sampleSize, ac, re);
            samplePos.add((double) res);
        }
        return samplePos;
    }

    public static int check1StepSamplingControl(List<Boolean> sample, int size, double ac){
        int countDef = 0, index = 0;
        Random rnd = new Random();
        List<Integer> usedItems = new ArrayList<>();
        for (int i = 0; i <= size; i++){
            while (usedItems.indexOf(index) >= 0){
                index = rnd.nextInt(sample.size());
            }
            usedItems.add(index);
            countDef += !sample.get(index) ?  1: 0;
        }
        count = countDef;
        steps = 1;
        return countDef <= ac ? 1 : 0;
    }



    public static int check2StepSamplingControl(List<Boolean> sample, int size, List<Integer> acList, List<Integer> reList) {
        int result1 = check1StepSamplingControl(sample, size, acList.get(0));
        int oldCount = count;
        if (result1 == 1) {
            return 1;
        }
        else if (count <= reList.get(0)) {
            oldCount += count;
            check1StepSamplingControl(sample, size, acList.get(1));
            count = oldCount;
            steps = 2;
            if (oldCount <= acList.get(1)) {
                return 1;
            }
        }
        return 0;
    }

    public static List<Double> getAvgPossibilities(int size, double ac, double times){
        List<Double> allLevelsList = new ArrayList<>();
        List<Double> possiblitiesList = Collections.nCopies(101,0d);
        for (int i = 0; i < times; i++) {
            allLevelsList = generateAllDeffectLevelsPossibilities(size, ac);
            possiblitiesList = summValuesOfTwoLists(possiblitiesList, allLevelsList);
        }
        //find probability
        possiblitiesList.replaceAll(x -> x / times);
        return possiblitiesList;
    }

    public static List<Double> getAvgPossibilities2Step(int size, List<Integer> ac, List<Integer> re, double times){
        List<Double> allLevelsList = new ArrayList<>();
        List<Double> possiblitiesList = Collections.nCopies(101,0d);
        for (int i = 0; i < times; i++) {
            allLevelsList = generateAllDeffectLevelsPossibilities2(size, ac, re);
            possiblitiesList = summValuesOfTwoLists(possiblitiesList, allLevelsList);
        }
        //find probability
        possiblitiesList.replaceAll(x -> x / times);
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
