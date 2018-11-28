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


        LiftDown(10);
        transform.right();
        transform.eDriveDistance(speed_NORMAL, 2,2);
        transform.straight();
        transform.eDriveDistance(-speed_NORMAL, -24, 10);

        transform.rightNoHit();
        transform.driveDetectBallStop(20,speed_SLOW,0);
        transform.straight();
        transform.driveDetectBallStop(5, speed_SLOW, 0);
        boolean gold = false;
        sleep(750);
        gold = colorSensor.isObjectGold();
        int exit = topExit;

        if (!gold) {
            transform.rightNoHit();
            transform.eDriveDistance(speed_SLOW, 2, 5);
            transform.driveDetectBallStop(10, speed_SLOW, 10);
            sleep(750);
            gold = colorSensor.isObjectGold();
            exit = middleExit;

            if (!gold) {
                transform.eDriveDistance(speed_SLOW, 2, 10);
                transform.driveDetectBallStop(10, speed_SLOW, 10);
                transform.eDriveDistance(speed_SLOW, 2, 10);
                transform.driveDetectBallStop(10, speed_SLOW, 10);
                exit = bottomExit;
            }
        }

        transform.straight();
        transform.eDriveDistance(-speed_NORMAL, -10, 5);

        switch(exit) {
            case topExit:
                transform.eDriveDistance(-speed_NORMAL, -2, 2);
                transform.left();
                transform.eDriveDistance(speed_NORMAL, 20, 2);
                break;

            case middleExit:
                transform.eDriveDistance(speed_NORMAL, 27, 5);
                break;

            case bottomExit:
                transform.eDriveDistance(-speed_NORMAL, -2, 2);
                transform.right();
                transform.eDriveDistance(speed_NORMAL, 20, 2);
                break;
        }

        releaseToken(300);
        //latch();

    }


}
