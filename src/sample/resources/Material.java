package sample.resources;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Material {
    private String name;
    private Map<String, String> properties;
    private Image materialImage;
    private double volume;
    private double avgQuality;

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getAvgQuality() {
        return avgQuality;
    }

    public void setAvgQuality(){
        avgQuality=0;
        for (Object propValue:properties.keySet()){
            avgQuality+=Double.parseDouble(properties.get(propValue));
        }
        avgQuality/=properties.size();
    }

    public Material(){}

    public Material(String name){
        this.name=name;
        properties=new HashMap<>();
        //materialImage=new Image("/test");
    }


    public Image getMaterialImage() {
        return materialImage;
    }

    public void setMaterialImage(Image materialImage) {
        this.materialImage = materialImage;
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


    public void addProperty(String name, String value){
        properties.put(name,value);
    }

    public void printMaterialAndProperties(){
        System.out.println("Название материала: "+name);
        System.out.println("Свойства");
        for (String k:properties.keySet()){
            System.out.println("Название: "+k+" , Значение: "+properties.get(k));
        }
    }
}
