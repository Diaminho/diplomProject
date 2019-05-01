package sample.task;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class SuspendableTask extends Task {
    boolean suspendFlag = false;

    final Object lock = new Object();
    @Override
    protected Void call() throws Exception {
        return null;
    }
        /*    //need to fix
            //int count=oldCount;
            boolean flag=true;
            while(flag) {
                Thread.sleep(10000);
                flag = experiment.produceRawMaterial();
                System.out.println("Blending");
                Platform.runLater(() -> label.setText(""+experiment.getRawList().size()));
                synchronized (this) {
                    while (suspendFlag) {
                        wait();
                    }
                }
            }
            return null;
        }*/

    public boolean isSuspendFlag() {
        return suspendFlag;
    }

    public void setSuspendFlag(boolean suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public synchronized void suspend() {
        suspendFlag = true;
    }

    public synchronized void resume() {
        suspendFlag = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }
}
