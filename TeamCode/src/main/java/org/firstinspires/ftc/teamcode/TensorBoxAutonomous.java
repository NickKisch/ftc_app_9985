package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

@Autonomous(name="TensorBox")
public class TensorBoxAutonomous extends MetaAutomation {


    @Override
    public void runOpMode(){
        setupHardware();
        initVuforia();
        initTfod();
        waitForStart();
        int mineralPosition = detectMineralPosition(10000);
        switch (mineralPosition) {
            case leftPosition:
                telemetry.addData("Final Decision", "LEFT");
                break;

            case centerPosition:
                telemetry.addData("Final Decision", "CENTER");
                break;

            case rightPosition:
                telemetry.addData("Final Decision", "RIGHT");
        }
        telemetry.update();
        sleep(2500);//Remove

        LiftDown(10);
        releaseLatch();
        transform.straight();
        transform.eDriveDistance(speed_NORMAL, 4, 5);
        switch (mineralPosition) {
            case leftPosition:
                break;

            case centerPosition:
                break;

            case rightPosition:
                break;

            default:
                break;
        }




        tfod.shutdown();

    }
}
