package sample.resource.helper;

import javafx.util.Pair;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "configureQuality")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigureQuality {
    String description;
    Double value;

    public ConfigureQuality() {
    }

    public ConfigureQuality(String description, Double value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
