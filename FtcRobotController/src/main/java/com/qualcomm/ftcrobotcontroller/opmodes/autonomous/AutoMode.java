package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by David on 7/9/2015.
 */
public abstract class AutoMode {
    public Autonomous auto;
    public RobotState state;
    private Thread autoThread;

    public AutoMode() {
        autoThread = new Thread(auto);
    }

    public AutoMode(Autonomous newAuto) {
        state = new RobotState();
        auto = newAuto;
        autoThread = new Thread(auto);
    }

    public void init() {
        auto.start();
    }

    public void run() {
        if (!autoThread.isAlive() && !auto.isDone()) {
            autoThread.start();
        }
        state.SyncState();
    }

    public void done() {
        auto.RunLevel(0);
        auto.close();
    }


}
