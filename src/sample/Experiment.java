package sample;

import javafx.scene.image.Image;
import sample.resource.Brick;
import sample.resource.Material;

import java.util.*;

public class Experiment {

    private Map<Material, Integer> materialMap;
    private Map<Material, Integer> neededMaterials;
    private List<Material> rawList=new ArrayList<>();
    private List<Material> dryRawList=new ArrayList<>();
    private List<Material> cuttedRawList=new ArrayList<>();
    private List<Material> burnedRawList=new ArrayList<>();
    private List<Brick> brickList=new ArrayList<>();

    private Map<String, Double> defects;
    private List<Double> brigades;
    private List<Double> stageQuality;

    private Map<Image, Double> stages;
    private Map<String, Image> stagesNames;

    private List<Double> defaultMaterialsQuality=new ArrayList<>();

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
        materialMap.keySet().forEach(material -> {
            defaultMaterialsQuality.add(0.9);
        });
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
    public boolean produceRawMaterial() {
        for (Object o : materialMap.keySet()) {
            for (Object o1 : neededMaterials.keySet()) {
                if (((Material) o).getName().equals(((Material) o1).getName())) {
                    if (materialMap.get(o) >= neededMaterials.get(o1)) {
                        materialMap.replace((Material) o, materialMap.get(o) - neededMaterials.get(o1));
                    } else {
                        System.out.println("Нехватка материала: " + ((Material) o).getName());
                        return false;
                    }
                }
            }
        }
        //need to create new Raw Material every time
        //
        //need to redo
        //
        //
        Double rawQuality=stageQuality.get(0);
        Double matQuality=defaultMaterialsQuality.stream().mapToDouble(Double::doubleValue).sum();
        List<Double> avgMaterialQuality=new ArrayList<>();
        for (int i=0;i<100;i++){
            avgMaterialQuality.add(matQuality);
        }
        ////
        generateQualityForMaterial(rawList,"Сырец", rawQuality, avgMaterialQuality);
        return true;
    }

    //2 stage Cutting
    public boolean doCutting(){
        //Material cuttedRaw =new Material("Нарезанный сырец");
        List<Double> materialQuality=new ArrayList<>();

        rawList.subList(0,100).forEach(material -> {
            Random random=new Random();
            Double avg=stageQuality.get(0);
            Double quality=(material.getAvgQuality()) ? random.nextDouble()*avg: random.nextDouble()*(1-avg);
            materialQuality.add(quality);
        });

        Double avgQuality=(stageQuality.get(1)+brigades.get(0))/2;
        if (materialQuality.size()<100) {
            return false;
        } else {
            generateQualityForMaterial(cuttedRawList, "Нарезанный сырец", avgQuality, materialQuality);
            return true;
        }
        //return cuttedRaw;
    }

    //3 stage
    public void doDrying(){
        Double avgQuality=stageQuality.get(2);
        List<Double> blendedQuality=new ArrayList<>();

        rawList.subList(0,100).forEach(material -> {
            Random random=new Random();
            Double avg=stageQuality.get(1);
            Double quality=(material.getAvgQuality()) ? random.nextDouble()*avg: random.nextDouble()*(1-avg);
            blendedQuality.add(quality);
        });

        generateQualityForMaterial(dryRawList,"Высушенный сырец",avgQuality,blendedQuality);
    }

    //4 stage
    public void doBurining(){
        //
        //
        Double avgQuality=stageQuality.get(3);
        //GENERATE BRICKS
        Brick brick=new Brick();
        //
    }


    ///
    private void generateQualityForMaterial(List<Material>  rawList, String rawName, double avgQuality, List<Double> qualityList){
        Material raw =new Material(rawName);
        Random rnd=new Random();
        for (int i=0;i<100;i++) {
            if (rnd.nextDouble() >= (1 - (avgQuality+qualityList.get(i))/2)) {
                raw.setAvgQuality(true);
            } else {
                raw.setAvgQuality(false);
            }
            rawList.add(raw);
        }
    }

};


