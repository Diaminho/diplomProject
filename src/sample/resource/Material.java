package sample.resource;

import javafx.scene.image.Image;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement(name = "material")
@XmlType(name="Material")
@XmlAccessorType(XmlAccessType.FIELD)
public class Material {
    String name;
    @XmlTransient
    Image materialImage;
    String materialPath;
    @XmlTransient
    boolean avgQuality;

    public String getMaterialPath() {
        return materialPath;
    }

    public void setMaterialPath(String materialPath) {
        this.materialPath = materialPath;
    }

    public boolean isAvgQuality() {
        return avgQuality;
    }

    public boolean getAvgQuality() {
        return avgQuality;
    }

    public void setAvgQuality(boolean avgQuality){
        this.avgQuality = avgQuality;
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
        this.materialPath = other.getMaterialImage().impl_getUrl();
        this.avgQuality = other.avgQuality;
    }

    public Image getMaterialImage() {
        return materialImage;
    }

    public void setMaterialImage(Image materialImage) {
        this.materialImage = materialImage;
        this.materialPath = materialImage.impl_getUrl();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return  material.avgQuality == avgQuality &&
                Objects.equals(name, material.name) &&
                Objects.equals(materialImage, material.materialImage) &&
                Objects.equals(materialPath, material.materialPath);
    }*/

    /*@Override
    public int hashCode() {
        return Objects.hash(name, materialImage, avgQuality, materialPath);
    }*/
}
