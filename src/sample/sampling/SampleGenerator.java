package sample.sampling;

import java.util.Random;

public class SampleGenerator {

    public static float[] generateSample(int n,float desiredStandardDeviation, float desiredMean){
        Random rnd=new Random();
        float[] sample=new float[n];
        for (int i=0;i<n;i++) {
            sample[i]=(float) rnd.nextGaussian()*desiredStandardDeviation+desiredMean;
        }
        return sample;
    }
}
