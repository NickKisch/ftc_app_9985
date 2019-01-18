package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Pit Hang", group="Hanging")
public class PitAutonomous extends MetaAutomation {


    @Override
    public void runOpMode(){
        //Init Stuff
        setupHardware();
        initVuforia();
        initTfod();
        waitForStart();
        //End Init Stuff

        GoldPosition mineralPosition = enhancedMineralDetection(10000);
        switch (mineralPosition) {
            case left:
                telemetry.addData("Final Decision", "LEFT");
                break;

            case center:
                telemetry.addData("Final Decision", "CENTER");
                break;

            case right:
                telemetry.addData("Final Decision", "RIGHT");
                break;

            default:
                telemetry.addData("Final Decision", "CENTER -> undecided");
                break;
        }
        telemetry.update();
        LiftDown(12);
        releaseLatch();
        switch (mineralPosition) {
            case left:
                transform.setAngleAll(35);
                transform.eDriveDistance(-speed_NORMAL, -35, 5);
                transform.straight();
                break;

            case center:
            default:
                transform.straight();
                transform.eDriveDistance(-speed_NORMAL, -40, 5); //Remove a couple of inches when switching this command to PitAutonomous [-40] -> pit
                break;

            case right:
                transform.setAngleAll(-35);
                transform.eDriveDistance(-speed_NORMAL, -37, 5);
                transform.straight();
                break;
        }

        transform.straight();



        tfod.shutdown();

    }
}
