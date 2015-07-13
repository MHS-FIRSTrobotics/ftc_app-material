package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.HashMap;

/**
 * Created by David on 7/9/2015.
 */
public class RobotState {
    private HashMap<String, Motor> motors;
    private HashMap<String, Servo> servos;
    private HashMap<String, BasicSensor> sensors;
    private HardwareMap hardwareMap;

    public RobotState(Motor[] robot_motors, Servo[] robot_servos) {
        hardwareMap = new HardwareMap();
        motors = new HashMap<>(robot_motors.length * 2);
        for (Motor motor : robot_motors) {
            motors.put(motor.GetName(), motor);
        }

        servos = new HashMap<>(robot_servos.length * 2);
        for (Servo servo : robot_servos) {
            servos.put(servo.GetName(), servo);
        }

        //Set sensors capacity to twice that of motors.length
        sensors = new HashMap<>(robot_motors.length * 2);
    }

    public RobotState() {
        hardwareMap = new HardwareMap();
        final int STANDARD_CAPACITY = 12; //six times two
        motors = new HashMap<>(STANDARD_CAPACITY);
        servos = new HashMap<>(STANDARD_CAPACITY);
    }

    public void AddMotor(Motor motor) {
        motors.put(motor.GetName(), motor);
    }

    public Motor GetMotor(String name) {
        return motors.get(name);
    }

    public void AddServo(Servo servo) {
        servos.put(servo.GetName(), servo);
    }

    public Servo GetServo(String name) {
        return servos.get(name);
    }

    public void AddIR_Seeker(IrSensor sensor) {
        sensors.put(sensor.GetName(), sensor);
    }

    public IrSensor GetIR_Seeker(String name) {
        return (IrSensor) sensors.get(name);
    }

    public synchronized void SyncState() {
        for (HashMap.Entry<String, BasicSensor> entry : sensors.entrySet()) {
            entry.getValue().WriteToHW();
        }

        for (HashMap.Entry<String, Motor> entry : motors.entrySet()) {
            entry.getValue().SetSpeedHW();
        }

        for (HashMap.Entry<String, Servo> entry : servos.entrySet()) {
            entry.getValue().SetPositionHW();
        }

        for (HashMap.Entry<String, BasicSensor> entry : sensors.entrySet()) {
            entry.getValue().ReadFromHW();
        }
    }


}
