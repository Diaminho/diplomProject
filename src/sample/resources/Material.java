package sample.resources;

import java.util.Map;

public class Material {
    private String name;
    private Map<String, String> properties;


    public Material(){}

    public Material(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }



}
