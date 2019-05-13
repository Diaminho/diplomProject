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
    private List<Brick> brickList=new ArrayList<>();
    private Integer counter=0;

    private List<Double> brigades;
    private List<List<Double>> stageQuality;

    private Map<Image, Double> stages;
    private Map<String, Image> stagesNames;
    private List<Integer> countList=new ArrayList<>();

    private Map<Material, Double> defaultMaterialsQuality=new HashMap<>();
    private Map<String, List<Double>> stagesInfluenceMap = new HashMap<>();
    private List<List<Integer>> scenarioStagesList = new ArrayList<>();
    private List<Integer> scenarioBrigadesList = new ArrayList<>();

    private double acceptableQuality = 0.8;

    public double getAcceptableQuality() {
        return acceptableQuality;
    }

    public Map<String, List<Double>> getStagesInfluenceMap() {
        return stagesInfluenceMap;
    }

    public void setStagesInfluenceMap(Map<String, List<Double>> stagesInfluenceMap) {
        this.stagesInfluenceMap = stagesInfluenceMap;
    }

    public Map<Material, Integer> getNeededMaterials() {
        return neededMaterials;
    }

    public void setNeededMaterials(Map<Material, Integer> neededMaterials) {
        this.neededMaterials = neededMaterials;
    }

    public List<Integer> getScenarioBrigadesList() {
        return scenarioBrigadesList;
    }

    public void setScenarioBrigadesList(List<Integer> scenarioBrigadesList) {
        this.scenarioBrigadesList = scenarioBrigadesList;
    }

    public List<List<Integer>> getScenarioStagesList() {
        return scenarioStagesList;
    }

    public void setScenarioStagesList(List<List<Integer>> scenarioStagesList) {
        this.scenarioStagesList = scenarioStagesList;
    }

    public Map<Material, Double> getDefaultMaterialsQuality() {
        return defaultMaterialsQuality;
    }

    public void setDefaultMaterialsQuality(Map<Material, Double> defaultMaterialsQuality) {
        this.defaultMaterialsQuality = defaultMaterialsQuality;
    }

    public List<List<Double>> getStageQuality() {
        return stageQuality;
    }

    public Map<Material, Integer> getMaterialMap() {
        return materialMap;
    }

    public void setMaterialMap(Map<Material, Integer> materialMap) {
        this.materialMap = materialMap;
    }

    public void setStageQuality(List<List<Double>> stageQuality) {
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

    public List<Brick> getBrickList() {
        return brickList;
    }

    public Experiment() {
        init();
    }

    public Experiment(Map materialMap) {
        this.materialMap = new HashMap<>(materialMap);
        materialMap.keySet().forEach(material -> {
            defaultMaterialsQuality.put((Material) material, 0.9);
        });
        //raw =new ArrayList<>();
        init();
        fillNeededMaterials();
        //
    }

    public void calculatInfluenceForStages() {
        List<Double> blendingInfluenceList = new ArrayList<>();
        for (Double d: stageQuality.get(0)) {
            blendingInfluenceList.add((1 - d) / 2);
        }
        stagesInfluenceMap.put("Смешивание", blendingInfluenceList);

        List<Double> cuttingInfluenceList = new ArrayList<>();
        for (Double d: stageQuality.get(1)) {
            cuttingInfluenceList.add((1 - d) / 4);
        }
        stagesInfluenceMap.put("Формовка", cuttingInfluenceList);

        List<Double> dryingInfluenceList = new ArrayList<>();
        for (Double d: stageQuality.get(2)) {
            dryingInfluenceList.add((1 - d) / 2);
        }
        stagesInfluenceMap.put("Сушка", dryingInfluenceList);

        List<Double> brickInfluenceList = new ArrayList<>();
        for (Double d: stageQuality.get(3)) {
            brickInfluenceList.add((1 - d) / 2);
            brickInfluenceList.add((1 - d) / 2);
            brickInfluenceList.add((1 - d) / 2);
            brickInfluenceList.add(1 - d);
        }
        stagesInfluenceMap.put("Обжиг", brickInfluenceList);
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
        stagesNames.put("Burning",new Image("/sample/image/stage/burning.png"));
        //raw.setVolume(0);

        int ii=0;
        while (ii<stagesNames.size()){
            countList.add(0);
            ii++;
        }

        //fill default stageQuality
        stageQuality=new ArrayList<>();
        for (int i = 0;i < stagesNames.size(); i++) {
            List<Double> list = new ArrayList<>();
            list.add(0.9d);
            stageQuality.add(list);
        }
        //

        //fill default brigadeQuality
        brigades=new ArrayList<>();
        brigades.add(0.9);
        brigades.add(0.9);

        scenarioBrigadesList.add(-1);
        scenarioBrigadesList.add(-1);


        /*for (Material m: materialMap.keySet()) {
            defaultMaterialsQuality.put(m, 0.9);
        }*/

        calculatInfluenceForStages();

        for (List<Double> listD: stageQuality) {
            List<Integer> stagesInfulenceList = new ArrayList<>();
            for (Double d: listD) {
                stagesInfulenceList.add(-1);
            }
            scenarioStagesList.add(stagesInfulenceList);
            //stagesInfluenceMapadd(0d);
        }
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
        int toolIndex = ((rawList.size() / 100) % stageQuality.get(0).size());
        checkScenario(0, toolIndex);
        for (Object o : materialMap.keySet()) {
            for (Object o1 : neededMaterials.keySet()) {
                if (((Material) o).getName().equals(((Material) o1).getName())) {
                    if (materialMap.get(o) >= neededMaterials.get(o1)) {
                        materialMap.replace((Material) o, materialMap.get(o) - neededMaterials.get(o1));
                    } else {
                        System.out.println("Нехватка материала: " + ((Material) o).getName());
                        countList.set(0, countList.get(0) + 1);
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
        Double rawQuality = stageQuality.get(0).get(toolIndex);
        Double matQuality = defaultMaterialsQuality.values().stream().mapToDouble(i->i).sum()  / defaultMaterialsQuality.size();
        //Double matQuality=defaultMaterialsQuality.stream().mapToDouble(Double::doubleValue).sum();
        List<Double> avgMaterialQuality=new ArrayList<>();
        for (int i = 0;i < 100;i++){
            avgMaterialQuality.add(matQuality);
        }
        ////
        generateQualityForMaterial(rawList,"Сырец", rawQuality, avgMaterialQuality);
        return true;
    }

    //2 stage Cutting
    public boolean doCutting(){
        int toolIndex = ((cuttedRawList.size() / 100) % stageQuality.get(1).size());
        checkScenario(1, toolIndex);
        int brigadeIndex = ((cuttedRawList.size() / 100) % brigades.size());
        if (scenarioBrigadesList.get(brigadeIndex) != -1) {
            if (scenarioBrigadesList.get(brigadeIndex) <= (cuttedRawList.size() / (100 * brigades.size()) + (cuttedRawList.size() % (100 * (brigadeIndex + 2))) / 100)) {
                brigades.set(brigadeIndex, 0.3d);
            }
        }
        //Material cuttedRaw =new Material("Нарезанный сырец");
        List<Double> rawQuality = new ArrayList<>();
        int countCutting = countList.get(1);

        rawList.subList(100 * countCutting,100 * (countCutting + 1)).forEach(material -> {
            Random random = new Random();
            Double quality = (material.getAvgQuality()) ? acceptableQuality + random.nextDouble() * (1 - acceptableQuality): random.nextDouble() * acceptableQuality;
            rawQuality.add(quality);
        });

        Double avgQuality = (stageQuality.get(1).get(0) + brigades.get(brigadeIndex)) / 2;
        if (rawQuality.size() < 100) {
            System.out.println("Нехватка материала: " + cuttedRawList.get(0).getName());
            return false;
        } else {
            generateQualityForMaterial(cuttedRawList, "Нарезанный сырец", avgQuality, rawQuality);
            countList.set(1,++countCutting);
            return true;
        }
        //return cuttedRaw;
    }

    //3 stage
    public boolean doDrying() {
        int toolIndex = ((dryRawList.size() / 100) % stageQuality.get(2).size());
        checkScenario(2, toolIndex);
        Double avgQuality = stageQuality.get(2).get(0);
        List<Double> cuttedQuality = new ArrayList<>();
        int counterDrying = countList.get(2);
        if (cuttedRawList.size() >= 100) {
            cuttedRawList.subList(100 * counterDrying, 100 * (counterDrying+1)).forEach(material -> {
                Random random = new Random();
                Double quality = (material.getAvgQuality()) ? acceptableQuality + random.nextDouble() * (1 - acceptableQuality) : random.nextDouble() * acceptableQuality;
                cuttedQuality.add(quality);
            });

            generateQualityForMaterial(dryRawList, "Высушенный сырец", avgQuality, cuttedQuality);
            countList.set(2,++counterDrying);
            return true;
        }
        else {
            System.out.println("Нехватка материала: " + cuttedRawList.get(0).getName());
            return false;
        }
    }

    //4 stage
    public boolean doBurning() {
        int toolIndex = ((brickList.size() / 100) % stageQuality.get(3).size());
        checkScenario(3, toolIndex);
        Double avgQuality = stageQuality.get(3).get(0);
        Random random = new Random();
        Boolean quality;
        int counterBrick = countList.get(3);
        for (int i = 100 * (counterBrick); i < 100 * (counterBrick + 1); i++) {
            Brick brick = new Brick();
            brick.getProperties().put("Цвет", rawList.get(i).getAvgQuality());
            //brick.getProperties().put("Цвет", rawList.get(i).getAvgQuality());
            brick.getProperties().put("Размеры", cuttedRawList.get(i).getAvgQuality());
            brick.getProperties().put("Трещины", dryRawList.get(i).getAvgQuality());
            quality = (1 - random.nextDouble()) < avgQuality;
            brick.getProperties().put("Структура", quality);
            //
            brickList.add(brick);
        }
        countList.set(3, ++counterBrick);
        return true;
    }


    ///
    private void generateQualityForMaterial(List<Material>  rawList, String rawName, double avgQuality, List<Double> qualityList){
        Random rnd=new Random();
        for (int i = 0; i < 100; i++) {
            Material raw = new Material(rawName);
            if (rnd.nextDouble() >= (1 - (avgQuality + qualityList.get(i)) / 2)) {
                raw.setAvgQuality(true);
            } else {
                raw.setAvgQuality(false);
            }
            rawList.add(raw);
        }
    }

    public Material findMaterialByName(String name, Set<Material> materialSet) {
        for (Material m: materialSet) {
            if (m.getName().compareTo(name) == 0) {
                return m;
            }
        }
        return null;
    }


    private boolean checkScenario(int stageId, int toolId) {
        int stage = -1;
        List<Integer> stagesTools = scenarioStagesList.get(stageId);
        switch (stageId) {
            case  (0):
                stage = rawList.size();
                break;
            case (1):
                stage = cuttedRawList.size();
                break;
            case (2):
                stage = dryRawList.size();
                break;
            case (3):
                stage = brickList.size();
                break;
            default:
                break;
        }

        if (stagesTools.get(toolId) >= 0) {
            int count = stage / (100 *  stagesTools.size()) + stage % (100 * (toolId + 1));
            if (count >= stagesTools.get(toolId)) {
                stageQuality.get(stageId).set(toolId, 0.3d);
                //calculatInfluenceForStages();
                return true;
            }
        }
        return false;
    }
};


