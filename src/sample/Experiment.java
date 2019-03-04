package sample;

import javafx.scene.image.Image;
import sample.resources.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Experiment {

    private Map<Material, Integer> materialMap;

    private Map<Material, Integer> neededMaterials;

    private Material raw;

    private Material dryRaw;

    private List<Image> stages;

    public List<Image> getStages() {
        return stages;
    }

    public Material getRaw() {
        return raw;
    }


    public Experiment(Map materialMap) {
        this.materialMap = new HashMap<>(materialMap);
        raw =new Material("Сырец");
        stages=new ArrayList<>();
        stages.add(new Image("/sample/images/stages/blending.png"));
        stages.add(new Image("/sample/images/stages/cutting.png"));
        stages.add(new Image("/sample/images/stages/drying.png"));
        raw.setVolume(0);
        fillNeededMaterials();

    }

    private void fillNeededMaterials(){
        neededMaterials=new HashMap<>();
        for (Object o: materialMap.keySet()){
            neededMaterials.put(new Material(((Material)o).getName()),5);
        }
    }

    public double getBrickNumber(int rawVolume){
        return (double)rawVolume/3;
    }

    //2 stage blending
    public int produceRawMaterial() {
        while (true) {
            for (Object o : materialMap.keySet()) {
                for (Object o1 : neededMaterials.keySet()) {
                    if (((Material) o).getName().equals(((Material) o1).getName())) {
                        if (materialMap.get(o) >= neededMaterials.get(o1)) {
                            materialMap.replace((Material) o, materialMap.get(o) - neededMaterials.get(o1));
                        } else {
                            System.out.println("Нехватка материала: " + ((Material) o).getName());
                            return 0;
                        }
                    }
                }
            }
            raw.setVolume(raw.getVolume()+1);
        }
    }

    //3 stage
    public void doDrying(double dryingQuality){
        dryRaw=new Material();
        dryRaw.addProperty("Качество", ""+dryingQuality);
        dryRaw.setVolume(raw.getVolume());
    }


};


