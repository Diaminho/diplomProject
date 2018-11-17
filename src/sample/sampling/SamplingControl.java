package sample.sampling;

public class SamplingControl {
    float alpha;
    float beta;
    int c; // приемочное число

    public int getC() {
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


    public boolean check1StepSamplingControl(float[] sample, float quality){
        int count=0;
        for (float item:sample){
            count+=item<quality ?  1: 0;
        }
        return count>c ? false : true;
    }

}
