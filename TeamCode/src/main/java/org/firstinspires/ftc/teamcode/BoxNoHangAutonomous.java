package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Box noHang", group="NO Hanging")
public class BoxNoHangAutonomous extends MetaAutomation {


    @Override
    public void runOpMode(){
        //Init Stuff
        setupHardware();
        initVuforia();
        initTfod();
        waitForStart();
        //End Init Stuff

        GoldPosition mineralPosition = detectMineralPosition(10000);
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
        transform.straight();
        releaseLatch();
        switch (mineralPosition) {
            case left:
                transform.setAngleAll(35);
                transform.eDriveDistance(-speed_NORMAL, -35, 5);
                transform.straight();
                transform.eDriveDistance(-speed_NORMAL, -18, 5); // remove under
                transform.setAngleAll(-42);
                transform.eDriveDistance(-speed_FULL, -24,5);
                break;

            case center:
            default:
                transform.straight();
                transform.eDriveDistance(-speed_NORMAL, -64, 5); //Remove a couple of inches when switching this command to PitAutonomous [-40] -> pit
                break;

            case right:
                transform.setAngleAll(-35);
                transform.eDriveDistance(-speed_NORMAL, -37, 5);
                transform.straight();
                transform.eDriveDistance(-speed_NORMAL, -18, 5); // remove under
                transform.setAngleAll(42);
                transform.eDriveDistance(-speed_FULL, -18,5);
                break;
        }

        releaseToken(1000);
        transform.straight();



        tfod.shutdown();

    }
}
