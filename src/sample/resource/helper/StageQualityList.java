package sample.resource.helper;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "stageQualityList")
@XmlAccessorType(XmlAccessType.FIELD)
public class StageQualityList {
    @XmlElement
    List<Double> stageToolQuality = new ArrayList<>();

    public List<Double> getStageToolQuality() {
        return stageToolQuality;
    }

    public void setStageToolQuality(List<Double> stageToolQuality) {
        this.stageToolQuality = stageToolQuality;
    }
}
