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
    private List<Material> rawList=new ArrayList<>();
    private List<Material> dryRawList=new ArrayList<>();
    private List<Material> cuttedRawList=new ArrayList<>();

    private Map<String, Double> defects;
    private List<Double> brigades;
    private List<Double> stageQuality;

    private Map<Image, Double> stages;
    private Map<String, Image> stagesNames;

    public List<Double> getStageQuality() {
        return stageQuality;
    }

    public Map<Material, Integer> getMaterialMap() {
        return materialMap;
    }

    public void setMaterialMap(Map<Material, Integer> materialMap) {
        this.materialMap = materialMap;
    }

    public void setStageQuality(List<Double> stageQuality) {
        this.stageQuality = stageQuality;
    }

    public List<Double> getBrigades() {
        return brigades;
    }

    public void setBrigades(List<Double> brigades) {
        this.brigades = brigades;
    }

    public Map<Image, Double> getStages() {
        return stages;
    }

    public List<Material> getRawList() {
        return rawList;
    }

    public void setRawList(List<Material> rawList) {
        this.rawList = rawList;
    }

    public List<Material> getDryRawList() {
        return dryRawList;
    }

    public void setDryRawList(List<Material> dryRawList) {
        this.dryRawList = dryRawList;
    }

    public List<Material> getCuttedRawList() {
        return cuttedRawList;
    }

    public void setCuttedRawList(List<Material> cuttedRawList) {
        this.cuttedRawList = cuttedRawList;
    }

    public Experiment() {
        init();
    }

    public Experiment(Map materialMap) {
        this.materialMap = new HashMap<>(materialMap);
        //raw =new ArrayList<>();
        init();
        fillNeededMaterials();
        //
    }

    private void init(){
        stages=new HashMap<>();
        stages.put(new Image("/sample/image/stage/blending.png"),1d);
        stages.put(new Image("/sample/image/stage/cutting/cutting.png"), 1d);
        stages.put(new Image("/sample/image/stage/drying.png"), 1d);

        //
        stagesNames=new HashMap<>();
        stagesNames.put("Blending", new Image("/sample/image/stage/blending.png"));
        stagesNames.put("Cutting",new Image("/sample/image/stage/cutting/cutting.png"));
        stagesNames.put("Drying", new Image("/sample/image/stage/drying.png"));
        //raw.setVolume(0);

        //fill default stageQuality
        stageQuality=new ArrayList<>();
        for (int i=0;i<stagesNames.size();i++) {
            stageQuality.add(0.95);
        }
        //

        //fill default brigadeQuality
        brigades=new ArrayList<>();
        brigades.add(0.9);
        brigades.add(0.9);


        //fill default defects for bricks
        defects=new HashMap<>();
    }

    public void fillNeededMaterials(){
        neededMaterials=new HashMap<>();
        for (Object o: materialMap.keySet()){
            neededMaterials.put(new Material(((Material)o).getName()),5);
        }
    }

    public Image findImageByMaterialName(String name){
        return stagesNames.get(name);
    }

    public double getBrickNumber(int rawVolume){
        return (double)rawVolume/3;
    }

    //2 stage blending
    public Material produceRawMaterial() {
        int count=0;
        Material raw=new Material("Сырец");
        double avgQuality=0d;
        for (Object o : materialMap.keySet()) {
            for (Object o1 : neededMaterials.keySet()) {
                if (((Material) o).getName().equals(((Material) o1).getName())) {
                    if (materialMap.get(o) >= neededMaterials.get(o1)) {
                        materialMap.replace((Material) o, materialMap.get(o) - neededMaterials.get(o1));
                        if(!((Material)o).getAvgQuality()) {
                            raw.setAvgQuality(false);
                            return raw;
                        }
                        count++;
                    } else {
                        System.out.println("Нехватка материала: " + ((Material) o).getName());
                        return null;
                    }
                }
            }
        }
        avgQuality/=count;
        //need to create new Raw Material every time
        //
        //need to redo
        Double rawQuality=stageQuality.get(0);
        //generate
        if(new java.util.Random().nextDouble()>=(1-rawQuality)) {
            raw.setAvgQuality(true);
        }
        else {
            raw.setAvgQuality(false);
        }
        System.out.println("afs: "+raw.getAvgQuality());
        //raw.setVolume(raw.getVolume()+1);
        //System.out.println();
        rawList.add(raw);
        return raw;
    }

    //2 stage Blendig
    public Material doCutting(double materialQuality){
        Material cuttedRaw =new Material("Нарезанный сырец");
        Double avgQuality=(stageQuality.get(1)+brigades.get(0))/2;
        if(new java.util.Random().nextDouble()>=(1-avgQuality)) {
            cuttedRaw.setAvgQuality(true);
        }
        else {
            cuttedRaw.setAvgQuality(false);
        }
        cuttedRawList.add(cuttedRaw);
        return cuttedRaw;
    }

    //3 stage
    public void doDrying(double blendedQuality){
        Material dryRaw=new Material();
        dryRaw.setAvgQuality(true);

        dryRawList.add(dryRaw);
    }

};


