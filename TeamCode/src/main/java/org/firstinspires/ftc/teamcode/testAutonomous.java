package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@Autonomous(name="Test Auto.")
public class testAutonomous extends MetaAutomation {

    enum GoldView {
        ONE, TWO, OUT
    }

    @Override
    public void runOpMode() {
        initVuforia();
        initTfod();
        waitForStart();

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
        sleep(10000);

        tfod.shutdown();

    }
}
