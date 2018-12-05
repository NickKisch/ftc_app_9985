package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name="Pit")
public class PitAutonomous extends MetaAutomation {

    //Declare variables exclusily used for this autnomous mode only!

    @Override
    public void runOpMode() throws InterruptedException{
        //Init the autonomous
        setupHardware();
        waitForStart();

        //Add autonomous code here

        LiftDown(12);
        transform.right();
        transform.eDriveDistance(speed_NORMAL, 2,2);
        transform.straight();
        transform.eDriveDistance(-speed_NORMAL, -16.5, 10);

        transform.rightNoHit();
        transform.driveDetectBallStop(18,speed_SLOW,0);
        transform.straight();
        transform.driveDetectBallStop(7,-speed_SLOW,0);
        boolean gold = false;
        sleep(300);
        gold = colorSensor.isObjectGold();
        int exit = topExit;

        if (!gold) {
            transform.leftNoHit();
            transform.eDriveDistance(speed_NORMAL, 3, 5);
            transform.driveDetectBallStop(20, speed_SLOW, 10);
            transform.straight();
            transform.driveDetectBallStop(7, -speed_SLOW, 10);
            sleep(300);
            gold = colorSensor.isObjectGold();
            exit = middleExit;

            if (!gold) {
                transform.leftNoHit();
                transform.eDriveDistance(speed_NORMAL, 3, 10);
                transform.driveDetectBallStop(20, speed_SLOW, 10);
                transform.straight();
                transform.driveDetectBallStop(7, -speed_SLOW, 10);
                exit = bottomExit;
            }
        }

        transform.straight();
        transform.eDriveDistance(-speed_NORMAL, -15, 5);

        transform.straight();

    }

}
