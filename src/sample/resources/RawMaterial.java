package sample.resources;

import java.util.ArrayList;
import java.util.List;

public class RawMaterial {
    String name;
    ArrayList<String> propertyName = new ArrayList<String>();
    List<Double> propertyValue = new ArrayList<Double>();

    public RawMaterial(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(ArrayList<String> propertyName) {
        this.propertyName = propertyName;
    }

    public List<Double> getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(List<Double> propertyValue) {
        this.propertyValue = propertyValue;
    }
}
