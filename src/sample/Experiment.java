package sample;

import javafx.scene.image.Image;
import sample.resource.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Experiment {

    private Map<Material, Integer> materialMap;

    private Map<Material, Integer> neededMaterials;

    private Material raw;

    private Material dryRaw;

    private Material blendedRaw;

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
        stages.add(new Image("/sample/image/stage/blending.png"));
        stages.add(new Image("/sample/image/stage/cutting.png"));
        stages.add(new Image("/sample/image/stage/drying.png"));
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
    public Material produceRawMaterial() {
        //while (true) {
        int count=0;
        double avgQuality=0d;
            for (Object o : materialMap.keySet()) {
                for (Object o1 : neededMaterials.keySet()) {
                    if (((Material) o).getName().equals(((Material) o1).getName())) {
                        if (materialMap.get(o) >= neededMaterials.get(o1)) {
                            materialMap.replace((Material) o, materialMap.get(o) - neededMaterials.get(o1));
                            //Material raw=new Material();
                            //need to create new raw
                            //
                            avgQuality+=((Material)o).getAvgQuality();
                            count++;

                        } else {
                            System.out.println("Нехватка материала: " + ((Material) o).getName());
                            return null;
                        }
                    }
                }
            //}

        }
        avgQuality/=count;
        raw.addProperty("Качество",""+avgQuality);
        raw.setAvgQuality();
        raw.setVolume(raw.getVolume()+1);
        System.out.println(raw.getVolume());
        return raw;
    }

    //2 stage Blendig
    public void doBlending(double materialQuality){
        blendedRaw=new Material();
        blendedRaw.addProperty("Качество", ""+materialQuality);
        blendedRaw.setVolume(raw.getVolume());
    }

    //3 stage
    public void doDrying(double blendedQuality){
        dryRaw=new Material();
        dryRaw.addProperty("Качество", ""+blendedQuality);
        dryRaw.setVolume(raw.getVolume());
    }


};


