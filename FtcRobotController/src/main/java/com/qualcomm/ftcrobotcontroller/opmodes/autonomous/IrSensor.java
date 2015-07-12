package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;

/**
 * Created by David on 7/11/2015.
 */
public class IrSensor implements BasicSensor {
    final HardwareMap hardwareMap = new HardwareMap();
    private IrSeekerSensor me;

    private String hw_name;
    private String common_name;
    private double angle;
    private boolean updated;
    private IrSeekerSensor.Mode temp_mode;
    private IrSeekerSensor.Mode mode;
    private double strength;
    private boolean signal;

    public IrSensor(final String motor_name) {
        hw_name = motor_name;
        common_name = motor_name;
        me = hardwareMap.irSeekerSensor.get(hw_name);
    }

    public IrSensor(final String hardware_name, final String name) {
        hw_name = hardware_name;
        common_name = name;
        me = hardwareMap.irSeekerSensor.get(hw_name);
    }

    public void WriteToHW() {
        hardwareMap.irSeekerSensor.get(hw_name).setMode(temp_mode);
        mode = temp_mode;
    }

    public String GetName() {
        return common_name;
    }

    public String GetHWName() {
        return hw_name;
    }

    public boolean IsNew() {
        return updated;
    }

    public void ReadFromHW() {

        mode = me.getMode();
        angle = me.getAngle();
        strength = me.getStrength();
        signal = me.signalDetected();
        updated = true;
    }

    public double Read() {
        updated = false;
        return angle;
    }

    public IrSeekerSensor.Mode GetMode() {
        return mode;
    }

    public double GetStrength() {
        return strength;
    }

    public boolean HasSignal() {
        return signal;
    }

    public double GetAngle() {
        return Read();
    }

    public void Write(IrSeekerSensor.Mode new_mode) {
        temp_mode = new_mode;
    }

}
