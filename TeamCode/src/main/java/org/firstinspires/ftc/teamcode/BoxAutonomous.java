package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="Box")
public class BoxAutonomous extends MetaAutomation {

    //Declare variables exclusily used for this autnomous mode only!

    @Override
    public void runOpMode() throws InterruptedException{
        //Init the autonomous
        setupHardware();
        waitForStart();

        //Add autonomous code here

        LiftDown(6);
        transform.straight();
        transform.eDriveDistance(speed_NORMAL, 2, 2);

        transform.right();
        transform.eDriveDistance(speed_NORMAL, 24, 5);

        transform.setAngleAll(turn_HalfLeft);
        transform.driveDetectBallStop(20, speed_NORMAL, 0);
        boolean gold = colorSensor.isObjectGold();
        telemetry.addData("Is Gold", gold);
        telemetry.update();
        sleep(10000);


    }

}
