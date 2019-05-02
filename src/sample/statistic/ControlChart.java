package sample.statistic;

import java.util.ArrayList;
import java.util.List;

public class ControlChart {

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

    public List<Double> getULControlPoint(double pAvg, double n) {
        double upper = pAvg + 3 * Math.sqrt((pAvg * (1 - pAvg)) / n);
        double lower = pAvg - 3 * Math.sqrt((pAvg * (1 - pAvg)) / n);
        List<Double> result = new ArrayList<>();
        result.add(lower);
        result.add(upper);
        return result;
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
        double pAvg = getPAvg(piList);
        //calculating Lower and Upper borders
        List<Double> res = getULControlPoint(pAvg, n);

        return piList;
    }

}