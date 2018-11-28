package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="Test Auto.")
public class testAutonomous extends MetaAutomation {

    @Override
    public void runOpMode() {
        setupHardware();
        waitForStart();

        transform.straight();
        sleep(2000);
        transform.left();
        sleep(2000);
        transform.right();
        sleep(2000);
        transform.leftNoHit();
        sleep(2000);
        transform.rightNoHit();
        sleep(2000);

    }
}
