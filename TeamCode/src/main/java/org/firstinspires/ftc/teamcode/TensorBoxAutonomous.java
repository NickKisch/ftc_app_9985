package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="TensorBox")
public class TensorBoxAutonomous extends MetaAutomation {


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
            default:
                telemetry.addData("Final Decision", "CENTER");
                break;

            case right:
                telemetry.addData("Final Decision", "RIGHT");
                break;
        }
        telemetry.update();
        transform.straight();
        LiftDown(12);
        releaseLatch();
        switch (mineralPosition) {
            case left:
                transform.setAngleAll(30);
                transform.eDriveDistance(-speed_NORMAL, -30, 5);
                break;

            case center:
            default:
                transform.straight();
                transform.eDriveDistance(-speed_NORMAL, -48, 5);
                break;

            case right:
                break;
        }

        releaseToken(800);
        transform.straight();



        tfod.shutdown();

    }
}
