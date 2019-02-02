package sample.sampling;

import java.util.List;

public class SamplingControl {
    float alpha=0.05f;
    float beta=0.05f;
    double c=0.04d; // приемочное число

    public double getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getBeta() {
        return beta;
    }

    public void setBeta(float beta) {
        this.beta = beta;
    }

    public SamplingControl(float alpha, float beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    public SamplingControl() { }


    public boolean check1StepSamplingControl(List<Double> sample, double quality){
        int count=0;
        for (double item:sample){
            count+=item<quality ?  1: 0;
        }
        System.out.println("Число бракованных изделий: "+count);
        return (count/sample.size())>=(1-c) ? false : true;
    }

}
