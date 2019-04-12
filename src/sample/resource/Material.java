package sample.resource;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Material {
    String name;
    Image materialImage;
    boolean avgQuality;
    Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean getAvgQuality() {
        return avgQuality;
    }

    public void setAvgQuality(boolean avgQuality){
        this.avgQuality=avgQuality;
    }

    public Material(){ avgQuality=true;}

    public Material(String name){
        this.name=name;
        avgQuality=true;
        //materialImage=new Image("/test");
    }

    public Material(Material other) {
        this.name = new String(other.getName());
        this.materialImage = new Image(other.getMaterialImage().impl_getUrl());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return  material.avgQuality==avgQuality &&
                Objects.equals(name, material.name) &&
                Objects.equals(materialImage, material.materialImage) &&
                Objects.equals(color, material.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, materialImage, avgQuality, color);
    }
}
