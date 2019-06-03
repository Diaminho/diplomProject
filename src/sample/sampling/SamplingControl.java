package sample.sampling;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SamplingControl {
    //private double f;
    private List<Integer> ac;
    private List<Integer> re;
    private double q;
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

    public List<Integer> getAc() {
        return ac;
    }

    public void setAc(List<Integer> ac) {
        this.ac = ac;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    double getFact(int a){
        if (a == 0) return 1;
        return a * getFact(a-1);
    }

    public List<Integer> getRe() {
        return re;
    }

    public void setRe(List<Integer> re) {
        this.re = re;
    }

    //Puasson
    public double getF(double q, int index){
        double f=0;
        for (int i = 0; i < ac.get(index) + 1; i++) {
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


    //2step
    public double getF2Step(double q){
        double f = getF(q, 0);
        double f1=0;
        for (int i = ac.get(0).intValue() + 1; i < re.get(1) ; i ++) {
            f1 += Math.pow(n * q, i) / getFact(i) * Math.pow(e, -(n * q));
        }
        f1 *= getF(q, 1);

        f += f1;
        return f;
    }

    /*
    public double getL(double N){
        BigDecimal res=BigDecimal.ONE;
        res=res.multiply(getCombination(N,ac));
        res=res.multiply(getCombination(N-q*N,n-ac));
        res=res.divide(getCombination(N,n),5,RoundingMode.HALF_EVEN);
        return  (res.compareTo(BigDecimal.ONE)>0)? 1d: res.doubleValue();
    }*/
}
