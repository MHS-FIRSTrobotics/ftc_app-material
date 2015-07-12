package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.AutoMode;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.Autonomous;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.BasicLevels;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.IrSensor;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.Motor;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.RobotState;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.RunLevel;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.Servo;


public class ExampleAutoMode extends AutoMode {
    public ExampleAutoMode() {

        // Put your RunLevels here
        RunLevel[] levels = new RunLevel[]{
                new BasicLevels.InitLevel(),
                new BasicLevels.Level1(),
                new BasicLevels.Level2(),
                new BasicLevels.Level3(),

                // An example of disabling a level

                //new BasicLevels.Level4(),

                new BasicLevels.StopLevel()
        };

        // Tell RobotState about your motors to use
        state = new RobotState(new Motor[]{
                new Motor("motor_1", "left_front-drive"),
                new Motor("motor_2", "right_front-drive"),
                new Motor("motor_3", "left_rear-drive"),
                new Motor("motor_4", "right_rear-drive")
        }, new Servo[]{
                new Servo("servo_1", "doer_of_something_of_the_kingdom_of_foo")
        });

        state.AddIR_Seeker(new IrSensor("ir_1", "main_IR"));

        // Build the autonomous object
        auto = new Autonomous(levels, state);
         
        /* Optional
        // Set the states to their defaults
        runner.SetInitState(0);
        runner.SetSafeState(levels.length - 1);
        runner.SetFinishState(levels.length - 1);
        */

        //TODO: write your constructor here
    }

    @Override
    public void init() {
        super.init();
        //TODO: write your init here
    }


    @Override
    public void run() {
        //This runs the main Autonomous thread and specifies the default behavior
        super.run();

        //TODO: write anything else you want
    }

    //Runs when the OpMode finishes
    @Override
    public void done() {
        super.done();

    }
}

