package sample.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Brick extends Material {
    private Map<String, Boolean> properties = new HashMap<>();
    private Map<String, Double> acceptableProperties;
    private List<Integer> brigadeAndStagesToolsList = new ArrayList<>();


    public List<Integer> getBrigadeAndStagesToolsList() {
        return brigadeAndStagesToolsList;
    }

    public void setBrigadeAndStagesToolsList(List<Integer> brigadeAndStagesToolsList) {
        this.brigadeAndStagesToolsList = brigadeAndStagesToolsList;
    }

    public Map<String, Boolean> getProperties() {
        return properties;
    }

    public boolean getAvgQuality() {
        return avgQuality;
    }

    public void calculateAvgQuality(){
        boolean avgQuality = true;
        for (String s:properties.keySet()){
            avgQuality = avgQuality & properties.get(s);
        }
        this.avgQuality = avgQuality;
    }

    public void setProperties(Map<String, Boolean> properties) {
        this.properties = properties;
    }

    public Map<String, Double> getAcceptableProperties() {
        return acceptableProperties;
    }

    public void setAcceptableProperties(Map<String, Double> acceptableProperties) {
        this.acceptableProperties = acceptableProperties;
    }
}
