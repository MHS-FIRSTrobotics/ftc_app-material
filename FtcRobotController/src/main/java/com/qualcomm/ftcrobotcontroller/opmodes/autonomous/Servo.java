package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by David on 7/11/2015.
 */
public class Servo {
    final HardwareMap hardwareMap = new HardwareMap();
    private com.qualcomm.robotcore.hardware.Servo me;

    private double position;
    private String hw_name;
    private String common_name;

    public Servo(String motor_name) {
        hw_name = motor_name;
        me = hardwareMap.servo.get(hw_name);

        common_name = motor_name;
        position = 0d;
    }

    public Servo(String hardware_name, String name) {
        hw_name = hardware_name;
        common_name = name;
        position = 0d;
    }

    public String GetHWName() {
        return hw_name;
    }

    public String GetName() {
        return common_name;
    }

    public double GetPosition() {
        return position;
    }

    public void SetPosition(int motor_speed) {
        position = motor_speed;
    }

    public void SetPositionHW() {
        me.setPosition(position);
    }
}
