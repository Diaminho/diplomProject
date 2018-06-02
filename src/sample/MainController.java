package sample;

import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Experiment;
import sample.resources.*;

import java.io.IOException;

import static java.lang.Thread.sleep;
//import javafx.scene.text.Text;

public class MainController {

    @FXML
    private Button testButton;
    @FXML
    private Button goMainButton;
    @FXML
    private Text testTextTemp;
    @FXML
    private Text firstStageText;
    @FXML
    private TextField cementID;
    @FXML
    private TextField sandID;
    @FXML
    private TextField clayID;
    @FXML
    public static Stage primaryStage;
    @FXML
    public static Stage experStage;
    @FXML
    private Text resultExperimentID;
    @FXML
    private Canvas canvasExperiment;
    @FXML
    private Text brickQualityID;



    @FXML
    public void testButtonAction(){
        testButton.setText("Thanks!");
        testTextTemp.setText("Была нажата кнопка");
        testTextTemp.setVisible(true);

    }

    @FXML
    public void start_buttonAction(ActionEvent event) {
        primaryStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("experiment.fxml"));
            experStage=primaryStage;
            experStage.setScene(new Scene(root));
            experStage.setTitle("Создание кирпичей");
            experStage.show();
            //Experiment NewExperiment=new Experiment();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onStartExperimentButton(ActionEvent event) {
        Sand sand=new Sand((double)Integer.parseInt(sandID.getText()), 95.0, 12);
        Cement cement=new Cement((double)Integer.parseInt(cementID.getText()), 90.0, 20);
        Clay clay=new Clay((double)Integer.parseInt(clayID.getText()), 90.0);
        Water water=new Water();
        //Experiment NewExperiment=new Experiment((double)Integer.parseInt(sandID.getText()), (double)Integer.parseInt(cementID.getText()));
        Experiment NewExperiment=new Experiment(sand, cement,water, clay);
        System.out.println("SAND: "+NewExperiment.getSand().getVolume()+ ";  CEMENT: "+NewExperiment.getCement().getVolume());
        //NewExperiment.getBrickNumber();
        resultExperimentID.setText("Можно произвести: "+NewExperiment.getBrickNumber()+" единиц кирпичей");
        resultExperimentID.setVisible(true);
        //System.out.println("BRICKS: "+NewExperiment.brick);

        //GRAPHICS
        GraphicsContext gc=canvasExperiment.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasExperiment.getWidth(), canvasExperiment.getHeight());
        double height=canvasExperiment.getHeight()/5;
        //double height=canvasExperiment.getHeight();
        //double width=canvasExperiment.getWidth();
        double lineWidth=2.0;
        gc.setLineWidth(lineWidth);
        //RECTANGLE FOR SAND
        NewExperiment.DrawRectangle(gc, Color.DARKGOLDENROD, 1,height, NewExperiment.getSand().getVolume(), "песок");
        //RECTANGLE FOR CEMENT
        NewExperiment.DrawRectangle(gc, Color.GREY, 1,height*2, NewExperiment.getCement().getVolume(), "цемент");
        //RECTANGLE FOR CLAY
        NewExperiment.DrawRectangle(gc, Color.web("#b66a50"), 1,height*3, NewExperiment.getClay().getVolume(), "глина");


        //gc.setStroke(Color.DARKGOLDENROD);
        //Shadow shadow=new Shadow(1,Color.BLACK);
        //gc.setEffect(shadow);
        //gc.strokeRect( lineWidth , lineWidth+12 , height+lineWidth, height+lineWidth);
        //gc.setFill(Color.DARKGOLDENROD);
        //gc.fillText("песок", 1,10);
        //gc.fillText(NewExperiment.sand.getVolume()+"",10,41);

        //RECTANGLE FOR CEMENT
        //gc.setStroke(Color.GREY);
        //gc.setFill(Color.GREY);
        //gc.fillText("цемент", 1,1+71);
        //gc.strokeRect( lineWidth , lineWidth+height+12*2 , 40+lineWidth, 40+lineWidth);
        //gc.setFill(Color.GREY);
        //gc.fillText(NewExperiment.cement.getVolume()+"",10,98);

        //RECTANGLE FOR CLAY
        //gc.setStroke(Color.web("#b66a50"));
        //gc.setFill(Color.web("#b66a50"));
        //gc.fillText("глина", 1,1+131);
        //gc.strokeRect( lineWidth , lineWidth+height*2+12*3 , 40+lineWidth, 40+lineWidth);
        //gc.setFill(Color.GREY);
        //gc.fillText(NewExperiment.clay.getVolume()+"",10,155);


        //RECTANGLE FOR RESULT AFTER 1 STAGE


        while (NewExperiment.produceRawMaterial()==0) {
            //NewExperiment.produceBrick();
            gc.clearRect(90, height, height*3, height*3);
            //RECTANGLE FOR RAW MATERIAL AFTER 1 STAGE
            NewExperiment.DrawRectangle(gc, Color.SADDLEBROWN, 90, height*2, NewExperiment.getRaw().getVolume(), "сырье");

            brickQualityID.setText("Качество изделий:"+NewExperiment.getRaw().getQuality()+"%");
            brickQualityID.setVisible(true);
            firstStageText.setVisible(true);

        }


    }

    @FXML
    public void onGoMainButton(ActionEvent event) {
        primaryStage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            //primaryStage=primaryStage;
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Главная");
            primaryStage.show();
            //Experiment NewExperiment=new Experiment();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
