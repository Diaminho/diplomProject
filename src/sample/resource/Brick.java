package sample.resource;

import java.util.Map;

public class Brick extends Material {
    private Map<String, Double> properties;
    private Map<String, Double> acceptableProperties;

    public Map<String, Double> getProperties() {
        return properties;
    }

    public boolean getAvgQuality() {
        return avgQuality;
    }

    public void calculateAvgQuality(){
        for (String s:properties.keySet()){
            properties.get(s);
        }

        this.avgQuality=avgQuality;
    }

}
