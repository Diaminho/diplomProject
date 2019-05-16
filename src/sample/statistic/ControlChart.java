package sample.statistic;

import java.util.ArrayList;
import java.util.List;

public class ControlChart {
    double ucl = 0.1;
    double lcl = 0;
    double pAvg;

    public double getUcl() {
        return ucl;
    }

    public void setUcl(double ucl) {
        this.ucl = ucl;
    }

    public double getLcl() {
        return lcl;
    }

    public void setLcl(double lcl) {
        this.lcl = lcl;
    }

    public double getpAvg() {
        return pAvg;
    }

    public void setpAvg(double pAvg) {
        this.pAvg = pAvg;
    }

    public Double getPi(List<Boolean> list) {
        Integer defects = 0;
        for (Boolean b : list){
            if (!b) {
                defects++;
            }
        }
        return ((double) defects) / list.size();
    }

    public Double getPAvg(List<Double> pList) {
        return pList.stream().mapToDouble(f -> f).sum() / pList.size();
    }


    public List<Double> calculate(List<Boolean> list) {
        List<Double> piList=new ArrayList<>();
        double tmp = 0;
        int n = list.size() / 20;

        //calcualting pi
        for (int i = 0; i < n; i++) {
            tmp = getPi(list.subList(20 * i, 20 * (i + 1)));
            piList.add(tmp);
        }
        //calculating pAvg
        pAvg = getPAvg(piList);
        //calculating Lower and Upper borders
        return piList;
    }

}