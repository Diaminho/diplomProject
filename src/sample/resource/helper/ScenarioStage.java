package sample.resource.helper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "scenarioStage")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScenarioStage {
    List<Integer> scenarioStage = new ArrayList<>();

    public List<Integer> getScenarioStage() {
        return scenarioStage;
    }

    public void setScenarioStage(List<Integer> scenarioStage) {
        this.scenarioStage = scenarioStage;
    }
}
