package sample.resources;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Material {
    private String name;
    private Map<String, String> properties;
    private Image materialImage;
    private double volume;
    private double avgQuality;
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


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

    public Material(Material other) {
        this.name = new String(other.getName());
        this.properties = new HashMap<>(other.getProperties());
        this.materialImage = new Image(other.getMaterialImage().impl_getUrl());
        this.volume = other.getVolume();
        this.avgQuality = other.avgQuality;
        this.color = other.getColor();
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Double.compare(material.volume, volume) == 0 &&
                Double.compare(material.avgQuality, avgQuality) == 0 &&
                Objects.equals(name, material.name) &&
                Objects.equals(properties, material.properties) &&
                Objects.equals(materialImage, material.materialImage) &&
                Objects.equals(color, material.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, properties, materialImage, volume, avgQuality, color);
    }
}
