package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="Test Auto.")
public class testAutonomous extends MetaAutomation {

    @Override
    public void runOpMode() {
        setupHardware();
        waitForStart();

        transform.rightNoHit();
        transform.driveDetectBallStop(20,speed_SLOW,0);
        sleep(10000000);

    }
}
