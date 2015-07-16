package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceManager;
import com.qualcomm.robotcore.hardware.HardwareFactory;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.mock.MockDeviceManager;
import com.qualcomm.robotcore.hardware.mock.MockHardwareFactory;
import com.qualcomm.robotcore.util.SerialNumber;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class AutonomousTest extends TestCase {
    private RobotState state;
    private Autonomous auto;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        HardwareFactory hwMockFactory = buildMockHardware();

        state = new RobotState(
              new Motor[] {
                      new Motor("lefty", "red")
              }, null
        );

        auto =new Autonomous(5, state);
    }

    @Test
    public void testRunLevel() throws Exception {
        assertEquals(0, auto.runLevel(3));
    }

    @Test
    public void testRunInitLevel() throws Exception {
        assertEquals(0, auto.runInitLevel());
    }

    @Test
    public void testRunFinishLevel() throws Exception {
        assertEquals(0, auto.runFinishLevel());
    }

    @Test
    public void testRun() throws Exception {
        auto.run();
        assertEquals(true, auto.isDone());
    }

    @Test
    public void testIsValidState() throws Exception {
        assertEquals(true, auto.isValidState());
    }

    @Test
    public void testIsValidState1() throws Exception {
        assertEquals(true, auto.isValidState(2));
    }

    private MockHardwareFactory buildMockHardware() throws RobotCoreException, InterruptedException {
        DeviceManager dm = new MockDeviceManager(null, null);
        DcMotorController mc = dm.createUsbDcMotorController(new SerialNumber("MC"));
        DcMotorController mc2 = dm.createUsbDcMotorController(new SerialNumber("MC2"));
        ServoController sc = dm.createUsbServoController(new SerialNumber("SC"));

        HardwareMap hwMap = new HardwareMap();
        hwMap.dcMotor.put("left", new DcMotor(mc, 1));
        hwMap.dcMotor.put("right", new DcMotor(mc, 2));
        hwMap.dcMotor.put("flag", new DcMotor(mc2, 1));
        hwMap.dcMotor.put("arm", new DcMotor(mc2, 2));
        hwMap.servo.put("a", new com.qualcomm.robotcore.hardware.Servo(sc, 1));
        hwMap.servo.put("b", new com.qualcomm.robotcore.hardware.Servo(sc, 6));

        return new MockHardwareFactory(hwMap);
    }
}