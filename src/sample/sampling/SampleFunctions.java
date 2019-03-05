package sample.sampling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SampleFunctions {

    public static List generateSample(int size, int defectsPercentage, double acceptableQuality){
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
}
