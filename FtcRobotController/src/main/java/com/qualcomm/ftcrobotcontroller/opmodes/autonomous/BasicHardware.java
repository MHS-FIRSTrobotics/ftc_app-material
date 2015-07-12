package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

/**
 * Created by David on 7/11/2015.
 */
public interface BasicHardware {
    String GetHWName();

    String GetName();

    int GetSpeed();

    void SetSpeed(int speed);
}

