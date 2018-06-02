package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.resources.*;

public class Experiment {

    Sand sand;
    Cement cement;
    Raw raw;
    Brick brick;
    Water water;
    Clay clay;

    double neededSand;
    double neededCement;
    double neededClay;

    public void DrawRectangle(GraphicsContext gc, Color strokeColor, double x, double y, double volume, String  text) {
        double lineWidth=2;
        gc.setStroke(strokeColor);
        //Shadow shadow=new Shadow(1,Color.BLACK);
        //gc.setEffect(shadow);
        gc.strokeRect( lineWidth+x , lineWidth+y-10 , 40+lineWidth, 40+lineWidth);
        gc.setFill(strokeColor);
        gc.fillText(text, 1+x,y-12);
        gc.fillText(volume+"",   10+x,y+16);
    }

    public Experiment(Sand sand, Cement cement, Water water, Clay clay) {
        this.sand=sand;
        this.cement=cement;
        this.water=water;
        this.clay=clay;
        this.brick=new Brick(0,0);
        this.raw=new Raw(0,0);
        this.neededCement=1;
        this.neededSand=5;
        this.neededClay=4;
    }

    public double getBrickNumber(){
        double possible_brick=0;
        if (getBrickNumbersFromCement() < getBrickNumbersFromSand() && getBrickNumbersFromCement() < getBrickNumbersFromClay()) {
            possible_brick=getBrickNumbersFromCement();
        }
        else if (getBrickNumbersFromSand() < getBrickNumbersFromCement() && getBrickNumbersFromSand() < getBrickNumbersFromClay()){
            possible_brick=getBrickNumbersFromSand();
        }
        else {
            possible_brick=getBrickNumbersFromClay();
        }
        return possible_brick;
    }

    public int produceRawMaterial() {
        if (this.sand.getVolume() >= this.neededSand && this.cement.getVolume() >= this.neededCement && this.clay.getVolume()>=this.neededClay) {
            this.raw.setVolume(this.raw.getVolume()+1);
            this.sand.setVolume(this.sand.getVolume()-this.neededSand);
            this.cement.setVolume(this.cement.getVolume()-this.neededCement);
            this.clay.setVolume(this.clay.getVolume()-this.neededClay);
            this.raw.setQuality((this.sand.getQuality()+this.cement.getQuality()+this.clay.getQuality())/3);
            return 0;
        }
        else if (this.sand.getVolume() < this.neededSand) {
            System.out.println("Недостаточно песка");
            return 1;
        }
        else if (this.cement.getVolume() < this.neededCement){
            System.out.println("Недостаточно цемента");
            return 2;
        }
        else {
            System.out.println("Недостаточно глины");
            return 3;
        }
    }

    public double getBrickNumbersFromCement() {
        return Math.floor(this.cement.getVolume()/this.neededCement);
    }

    public double getBrickNumbersFromSand() {
        return Math.floor(this.sand.getVolume()/this.neededSand);
    }

    public double getBrickNumbersFromClay() {
        return Math.floor(this.clay.getVolume()/this.neededClay);
    }


    public Sand getSand() {
        return sand;
    }

    public void setSand(Sand sand) {
        this.sand = sand;
    }

    public Cement getCement() {
        return cement;
    }

    public void setCement(Cement cement) {
        this.cement = cement;
    }

    public Raw getRaw() {
        return raw;
    }

    public void setRaw(Raw raw) {
        this.raw = raw;
    }

    public Brick getBrick() {
        return brick;
    }

    public void setBrick(Brick brick) {
        this.brick = brick;
    }

    public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }

    public Clay getClay() {
        return clay;
    }

    public void setClay(Clay clay) {
        this.clay = clay;
    }
};


