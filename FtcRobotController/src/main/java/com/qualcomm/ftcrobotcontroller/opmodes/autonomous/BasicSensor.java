package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.robotcore.hardware.IrSeekerSensor;

public interface BasicSensor {
    String GetHWName();

    String GetName();

    double Read();

    void Write(IrSeekerSensor.Mode mode);

    void ReadFromHW();

    void WriteToHW();

    boolean IsNew();
}
