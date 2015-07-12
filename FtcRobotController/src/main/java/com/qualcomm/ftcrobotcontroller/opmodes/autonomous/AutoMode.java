package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;


public abstract class AutoMode extends OpMode {
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
        auto.RunInitLevel();
    }

    public void run() {
        if (!autoThread.isAlive() && !auto.isDone()) {
            autoThread.start();
        }
        state.SyncState();
    }

    public void done() {
        auto.RunFinishLevel();
        auto.close();
    }


}
