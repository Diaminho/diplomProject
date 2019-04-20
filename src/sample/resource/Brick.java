package sample.resource;

import java.util.HashMap;
import java.util.Map;

public class Brick extends Material {
    private Map<String, Boolean> properties=new HashMap<>();
    private Map<String, Double> acceptableProperties;

    public Map<String, Boolean> getProperties() {
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
