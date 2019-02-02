package sample;

import javafx.scene.image.Image;
import sample.resources.*;

import java.util.ArrayList;
import java.util.List;

public class Experiment {

    private List<Material> materialsList;

    private List<Material> neededMaterials;

    private Material raw;

    private List<Image> stages;

    public List<Image> getStages() {
        return stages;
    }

    public Material getRaw() {
        return raw;
    }


    public Experiment(List materialsList) {
        this.materialsList = materialsList;
        raw =new Material("Сырец");
        stages=new ArrayList<>();
        stages.add(new Image("/sample/images/stages/blending.png"));
        stages.add(new Image("/sample/images/stages/cutting.png"));
        raw.setVolume(0);
        fillNeededMaterials();

    }

    private void fillNeededMaterials(){
        neededMaterials=new ArrayList<>();
        for (Object o: materialsList){
            neededMaterials.add(new Material(((Material)o).getName()));
            neededMaterials.get(neededMaterials.size()-1).setVolume(5d);
        }
    }

    public double getBrickNumber(int rawVolume){
        return (double)rawVolume/3;
    }

    public int produceRawMaterial() {
        while (true) {
            for (Object o : materialsList) {
                if (((Material)o).getVolume() >= neededMaterials.get(materialsList.indexOf(o)).getVolume()) {
                    ((Material)o).setVolume(((Material)o).getVolume() - neededMaterials.get(materialsList.indexOf(o)).getVolume());
                } else {
                    System.out.println("Нехватка материала: " + ((Material) o).getName());
                    return -1;
                }
            }
            raw.setVolume(raw.getVolume()+1);
        }

    }


};


