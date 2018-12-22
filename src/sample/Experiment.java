package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.resources.*;

import java.util.List;

public class Experiment {

    Sand sand;
    Cement cement;
    Raw raw;
    Brick brick;
    Water water;
    Clay clay;
    List<Material> materialsList;

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

    public Experiment(List materialsList) {
        this.materialsList=materialsList;
    }

    public double getBrickNumber(){
        return -1;
    }

    public int produceRawMaterial() {
      return -1;
    }


};


