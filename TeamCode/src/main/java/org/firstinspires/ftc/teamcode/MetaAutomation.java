package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

abstract public class MetaAutomation extends LinearOpMode {
    //Constants for autonomous

    //Driving Constants
    public static final double speed_FULL   = 1;
    public static final double speed_NORMAL = 0.5;
    public static final double speed_SLOW   = 0.1;

    //Servo Turn Constants
    public static final double turnLeft     = 1;
    public static final double turnStraight = 0.5;
    public static final double turnRight    = 0;

    //Motor Turn Constants
    public static final double speedTurn_FULL   = 0;
    public static final double speedTurn_NORMAL = 0;
    public static final double speedTurn_SLOW   = 0;

    //Timeout Constants
    public static final double tout_SHORT  = 3;
    public static final double tout_MEDIUM = 5;
    public static final double tout_LONG   = 10;

    //Vars for later

    //Time counting variable for timeout
    private ElapsedTime runtime = new ElapsedTime();

    //Declare motors and sensors
    HardwarePushbot_Nick robot = new HardwarePushbot_Nick();
    SensorStartup sensors = new SensorStartup();

    //Declare inner classes

    /*
    *  Functions for autnomouns
    *  this is the stuff that makes autonomous work
    *
    */

    public void setupHardware(){
        //Start autonomous
        robot.init(hardwareMap);
        sensors.init(hardwareMap);
    }

}
