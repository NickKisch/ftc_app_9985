package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

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

        BallPosition mineralPosition = detectMineralPosition(10000);
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

        LiftDown(12);
        releaseLatch();
        transform.straight();
        transform.eDriveDistance(speed_NORMAL, 4, 5);
        switch (mineralPosition) {
            case left:
                break;

            case center:
            default:
                break;

            case right:
                break;
        }




        tfod.shutdown();

    }
}
