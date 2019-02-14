package sample.operateChars;

import java.math.BigDecimal;
import java.math.BigInteger;
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

    /*
    public double getP(double N, double n){
        BigDecimal res=BigDecimal.ZERO;
        BigDecimal tmp=BigDecimal.ZERO;
        //for (int i=0;i<ac;i++) {
            tmp= getCombination(N, ac).multiply(getCombination((N - N * q), n - ac));
            tmp=tmp.divide(getCombination(N,n), RoundingMode.HALF_EVEN);
            res = res.add(tmp);
        //}
        return res.doubleValue();
    }
    */


    /*
    public double getN(double N, double n, double k){
        BigDecimal res=BigDecimal.ZERO;
        BigDecimal tmp=BigDecimal.ZERO;
        //for (int i=0;i<ac;i++) {
        tmp= getCombination(n, k).multiply(BigDecimal.valueOf(Math.pow(q,k))).multiply(BigDecimal.valueOf(Math.pow((1-q),n-k)));
        res = res.add(tmp);
        //}
        return res.doubleValue();
    }*/

    double getFact(int a){
        if (a == 0) return 1;
        return a * getFact(a-1);
    }

    public int getN(double N){
        n=(int)N/10;
        double f=0;
        while (f<=1-alpha || f>=1 || f<0.0001) {
            f=getF();
            System.out.println("n: "+n);
            n-=1;
        }

        return n;
    }

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
                    .divide(BigDecimal.valueOf(i+1),RoundingMode.HALF_EVEN));
        }
        return ret;
    }
}
