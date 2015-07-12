package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by David on 7/11/2015.
 */
public class Motor implements BasicHardware {
    final HardwareMap hardwareMap = new HardwareMap();
    private DcMotor me;

    private int speed;
    private String hw_name;
    private String common_name;


    public Motor(String hardware_name) {
        hw_name = hardware_name;
        me = hardwareMap.dcMotor.get(hw_name);

        common_name = hardware_name;
        speed = 0;
    }

    public Motor(String hardware_name, String name) {
        hw_name = hardware_name;
        common_name = name;
        speed = 0;
    }

    public String GetName() {
        return common_name;
    }

    public String GetHWName() {
        return hw_name;
    }

    public int GetSpeed() {
        return speed;
    }

    public void SetSpeed(int motor_speed) {
        speed = motor_speed;
    }

    public void SetSpeedHW() {
        me.setPower(speed);
    }
}

