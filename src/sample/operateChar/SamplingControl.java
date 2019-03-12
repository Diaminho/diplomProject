package sample.operateChar;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SamplingControl {
    //private double f;
    private double ac;
    private double q;
    private double aql=0.02;
    private double lq=0.05;
    private static double e=2.7182818284;
    private double alpha;
    private double beta;
    private int n;

    public SamplingControl() { }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getAc() {
        return ac;
    }

    public void setAc(double ac) {
        this.ac = ac;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }


    double getFact(int a){
        if (a == 0) return 1;
        return a * getFact(a-1);
    }


    //Puasson
    public double getF(){
        double f=0;
        for (int i = 0; i < ac+1; i++) {
            f += Math.pow(n * q, i) / getFact(i) * Math.pow(e, -(n * q));
        }
        return f;
    }

    public BigDecimal getCombination(double n, double k) {
        BigDecimal ret = BigDecimal.ONE;
        for (int i = 0; i < k; i++) {
            ret = ret.multiply((BigDecimal.valueOf(n-i))
                    .divide(BigDecimal.valueOf(i+1),5, RoundingMode.HALF_EVEN));
        }
        return ret;
    }

    public double getL(double N, double n){
        BigDecimal res=BigDecimal.ONE;
        res=res.multiply(getCombination(N,ac));
        res=res.multiply(getCombination(N-q*N,n-ac));
        res=res.divide(getCombination(N,n),5,RoundingMode.HALF_EVEN);
        return  (res.compareTo(BigDecimal.ONE)>0)? 1d: res.doubleValue();
    }
}
