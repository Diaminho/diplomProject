package sample.sampling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SampleGenerator {

    public static List generateSample(int n,float desiredStandardDeviation, float desiredMean){
        Random rnd=new Random();
        List<Double> sample=new ArrayList<>();
        Double value=0d;
        for (int i=0;i<n;i++) {
            value=rnd.nextGaussian()*desiredStandardDeviation+desiredMean;
            value=(value>1.0d) ? 1.0d : (value<0) ? 0: value;
            sample.add(value);
        }
        return sample;
    }
}
