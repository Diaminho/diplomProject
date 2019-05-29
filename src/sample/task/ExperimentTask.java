package sample.task;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import sample.Experiment;
import sample.statistic.Statistic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.*;

public class ExperimentTask {
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setExecutor(PausableScheduledThreadPoolExecutor executor) {
        this.executor = executor;
    }

    private PausableScheduledThreadPoolExecutor executor = new PausableScheduledThreadPoolExecutor(4);

    public PausableScheduledThreadPoolExecutor getExecutor() {
        return executor;
    }

    public void addSuspendableTask(Runnable task, int delay, int period) {
        executor.scheduleWithFixedDelay(task, delay, period, TimeUnit.SECONDS);
    }

    public Runnable BlendingTask(Experiment experiment, Label label) {
         return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //while(flag) {
                    //while (suspendFlag) {
                    //    wait();
                    //}
                    //Thread.sleep(10000);

                if (flag) {
                    flag = experiment.produceRawMaterial();
                    System.out.println("Blending count: " + counter++ + " Thread: " + Thread.currentThread().getName());
                    Platform.runLater(() -> label.setText("" + experiment.getRawList().size()));
                    //}
                }
            }
        };
    }

    public Runnable CuttingTask(Experiment experiment, Label label){
        return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //Thread.sleep(1000);
                //while(flag) {
                    //while (suspendFlag) {
                    //    wait();
                    //}
                    //Thread.sleep(10000);
                if (flag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = experiment.doCutting();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("Cutting " + counter++ + " Thread: " + Thread.currentThread().getName());
                    Platform.runLater(() -> label.setText("" + experiment.getCuttedRawList().size()));
                    //oldCount+=1;
                    //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                }
            }
        };
    }

    public Runnable DryingTask(Experiment experiment, Label label){
        return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //Thread.sleep(1000);
                //while(flag) {
                //while (suspendFlag) {
                //    wait();
                //}
                //Thread.sleep(10000);
                if (flag) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = experiment.doDrying();
                //System.out.println(raw.getAvgQuality());
                //final String rawVolume = String.valueOf(raw.getVolume());
                //
                    System.out.println("Drying " + counter++ + " Thread: " + Thread.currentThread().getName());
                    Platform.runLater(() -> label.setText("" + experiment.getDryRawList().size()));
                //oldCount+=1;
                //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                //}
                }
            }
        };
    }

    public Runnable BurningTask(Experiment experiment, Label label){
        return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //Thread.sleep(1000);
                //while(flag) {
                //while (suspendFlag) {
                //    wait();
                //}
                //Thread.sleep(10000);
                if (flag) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = experiment.doBurning();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("Burning " + counter++ + " Thread: " + Thread.currentThread().getName());
                    Platform.runLater(() -> label.setText("" + experiment.getBrickList().size()));
                    //Statistic statistic = new Statistic();
                    //statistic.calculateBrickStat(experiment.getBrickList());
                    //statistic.printResult();
                    //oldCount+=1;
                    //Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                    //}
                }
            }
        };
    }

    public Runnable LogisticTask(Experiment experiment, TextArea textArea, Label label){
        return new Runnable() {
            private int counter = 0;
            private boolean flag = true;

            @Override
            public void run() {
                //need to fix
                //int count=oldCount;
                //Thread.sleep(1000);
                //while(flag) {
                //while (suspendFlag) {
                //    wait();
                //}
                //Thread.sleep(10000);
                if (flag) {
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    flag = experiment.doLogistic();
                    //System.out.println(raw.getAvgQuality());
                    //final String rawVolume = String.valueOf(raw.getVolume());
                    //
                    System.out.println("Logistic " + counter++ + " Thread: " + Thread.currentThread().getName());
                    Statistic statistic = new Statistic();
                    statistic.calculateBrickStat(experiment);
                    String info = statistic.printResult();
                    writeBrickInfoToFile(info);//oldCount+=1;
                    Platform.runLater(() -> textArea.setText(info));
                    Platform.runLater(() -> label.setText("" + experiment.getLogisticBrickList().size()));
                    //}
                }
            }
        };
    }




    private void writeBrickInfoToFile(String info) {
        File file = new File("result" + date + ".txt");
        try(FileWriter writer = new FileWriter(file, true))
        {
            writer.write(info);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
